package application;
import algorithmes.*;
import java.util.ArrayList;
import java.util.Collections;
public class Ordonnanceur {
    private ArrayList<CPU> multiProcess = new ArrayList<>();
    private  ArrayList<Processus> LesProcessus = new ArrayList<>();
    private EntreSortie ES ;
    private ArrayList<String> Triage = new ArrayList<>();
    private FIFO fifo = null;
    private Priorite prio = null;
    private FIFO RR = null;
    private temps T = null;
    private ArrayList<Processus> ALLProcessus;
    public static Boolean CPUready = false;
    public  Boolean toStop = false;
    public ArrayList<Processus> getProcess(){
        return ALLProcessus;
    }
    public void init( ArrayList<CPU> CPUs,ArrayList<Processus> CPU_Process,int type){
        multiProcess = CPUs;
        ALLProcessus = CPU_Process;
        ES = new EntreSortie(this);
        switch(type){
            case 1 : fifo = new FIFO(CPU_Process); break;
            case 2 : case 5:
                prio = new Priorite(CPU_Process); 
                if(type == 5){
                    CPU.Preemptif = 1;
                    CPU.Prio = prio;
                }
                break;
            case 3 :
                RR = new FIFO(CPU_Process,2);
                break;
            case 4 : case 6:
                T = new temps(CPU_Process);
                if(type == 6){
                    CPU.Preemptif = 2;
                    CPU.Temps = T;
                }
                break;
            default : fifo = new FIFO(CPU_Process); break;
        }        
    }
    public void lunch(int ORL){
    				if(ALLProcessus.get(ALLProcessus.size()-1).getTCalcule().isEmpty()) toStop = true;
                    Execution();
                    if(ES.AttenteStatus()) ES.Execute();
                    Traiges(ORL);
                    IncrementDiagrammeByStatus();
    }
    public EntreSortie getES(){
        return ES;
    }
    private void Execution(){
             for(int i =0; i<multiProcess.size();i++){
                if(multiProcess.get(i).getAttenteSize() > 0){
                   if(RR == null) multiProcess.get(i).lunch(ES);
                   else multiProcess.get(i).lunch_RR(RR,ES);
                }
             }
    }
    private void getListProcess(int ORL){
           if(fifo != null){
               if(LesProcessus.size()>0 ){
                   if(!fifo.getListProcessByTempsArive(ORL).isEmpty())
                     LesProcessus.addAll(fifo.getListProcessByTempsArive(ORL));
                 fifo.ReorderParIndice(LesProcessus);
               } else
                   LesProcessus = fifo.getListProcessByTempsArive(ORL);
               
           }else if(RR != null){
               if(LesProcessus.size()>0 ){
                   if(!RR.getListProcessByTempsArive(ORL).isEmpty())
                     LesProcessus.addAll(RR.getListProcessByTempsArive(ORL));
                 RR.ReorderParIndice(LesProcessus);
               } else
                   LesProcessus = RR.getListProcessByTempsArive(ORL);
               
           }else if(prio != null){
                    if(LesProcessus.size()>0){
                        if(!prio.getListProcessByTempsArive(ORL).isEmpty())
                             LesProcessus.addAll(prio.getListProcessByTempsArive(ORL));
                    }else
                        LesProcessus = prio.getListProcessByTempsArive(ORL);
                    if(LesProcessus.size()>0)
                        prio.ReOrderPriorite(LesProcessus);
                
           }else if(T != null){
                    if(LesProcessus.size()>0){
                        if(!T.getListProcessByTempsArive(ORL).isEmpty())
                             LesProcessus.addAll(T.getListProcessByTempsArive(ORL));
                    }else
                        LesProcessus = T.getListProcessByTempsArive(ORL);
                    if(LesProcessus.size()>0)
                        T.ReOrderPlusCourt(LesProcessus);
           }

    }      
   public void AddProcess(Processus P){
       LesProcessus.add(P);
   }
    private void Traiges(int ORL){
            triCPU();
            getListProcess(ORL);
            if(!LesProcessus.isEmpty()){
                if(LesProcessus.size() == 1) {
                    if(isMyCPUReady(LesProcessus.get(0).getID_CPU())){
                        BackToMyCPU(0);
                    }else{
                        verification(0);
                    }
                }else{
                	Processus.type_compare = 4;
                	Collections.sort(LesProcessus);
                    for(int i=0; i<LesProcessus.size();i++){
                        if(isMyCPUReady(LesProcessus.get(i).getID_CPU())){
                            BackToMyCPU(i);
                        }else{
                            verification(i);
                        }
                        triCPU();
                    }
                }
                LesProcessus.clear();
            }
        triCPU();
            
    }
    private void CPUTri(ArrayList<CPU> multiprocess){
            for(int j=0; j<multiprocess.size();j++){
                if(multiprocess.get(0).getStatus() == 0 && multiprocess.get(j).getStatus() == 0 ){
                    if(multiprocess.get(0).getID() > multiprocess.get(j).getID()){
                        CPU CP = multiprocess.get(j);
                        multiprocess.remove(j);
                        multiprocess.add(0,CP);
                    }
                } 
            }
        
    }
    public Boolean CPUReady(){
            for(int j=0; j<multiProcess.size();j++){
                if(multiProcess.get(j).getStatus() ==0){
                    TriageDesCPUParStatus(multiProcess);
                    return true;
                } 
            }
        return false;
    }
    public CPU getFirstCPU(){
        return multiProcess.get(0);
    }
    private void verification(int i){
        if(LesProcessus.get(i).getID_CPU() != -1 )
            if(multiProcess.get(0).getStatus() != 0) BackToMyCPU(i);
            else multiProcess.get(0).AddProcessus(LesProcessus.get(i));
        else multiProcess.get(0).AddProcessus(LesProcessus.get(i));
    }
    private void BackToMyCPU(int i){
        for(int j = 0; j<multiProcess.size();j++){
            if(multiProcess.get(j).getID() == LesProcessus.get(i).getID_CPU()){
                multiProcess.get(j).AddProcessus(LesProcessus.get(i));
                break;  
            }
        }
    }
    private void IncrementDiagrammeByStatus(){
        for(int i =0; i<multiProcess.size();i++){
            if(multiProcess.get(i).getStatus() == 0) multiProcess.get(i).getDiagramme().add(-1);
            else multiProcess.get(i).getDiagramme().add(multiProcess.get(i).getAttente().get(0).getID());
        }
    }
    private void triCPU(){
        Boolean sizeEgaux = false, stat = false;
        for(int i =0; i<multiProcess.size();i++){
            if(multiProcess.get(i).getStatus() == 0){
                    TriageDesCPUParStatus(multiProcess);
                    stat = true;
                    break;
            }
            if(!stat){
                for(int j =0; j<multiProcess.size();j++){
                    sizeEgaux = multiProcess.get(0).getAttenteSize() == multiProcess.get(j).getAttenteSize();
                }
                    if(sizeEgaux){
                        TriageDesCPUParID(multiProcess);
                    } 
                    else TriageDesCPUParSize(multiProcess);
            }
        }
        for(int i =0; i<multiProcess.size();i++){
            Triage.add(" CPU"+multiProcess.get(i).getID()+" :: ");
        }
        Triage.add(" Horloge = "+CPU.OrlogeTEST);
    }
     public  ArrayList<Processus> clonner(ArrayList<Processus> clon){
        ArrayList<Processus> cloned = new ArrayList<>();
        for(int i =0; i<clon.size();i++){
            Processus P = (Processus) clon.get(i).clone();
            cloned.add(P);
        }
        return cloned;
    }
     private void TriageDesCPUParStatus(ArrayList<CPU> C){
         CPU.typeCMP = 1;
         Collections.sort(C);
         CPUTri(C);
     }
     private void TriageDesCPUParSize(ArrayList<CPU> C){
         CPU.typeCMP = 2;
         Collections.sort(C);
     }
     private void TriageDesCPUParID(ArrayList<CPU> C){
         CPU.typeCMP = 3;
         Collections.sort(C);
     }
     private Boolean isMyCPUReady(int CPU_ID){
         Boolean returne = false;
         for(int i=0; i<multiProcess.size();i++){
            if(multiProcess.get(i).getID() == CPU_ID && multiProcess.get(i).getStatus() == 0){
                returne = true;
                break;
            }
         }
         return returne;
     }
     public void getALLResult(){
         for(int i=0; i<multiProcess.size();i++){
             System.out.println(" \n ==> CPU "+multiProcess.get(i).getID()+"<== \n ");
             for(int j=0; j<multiProcess.get(i).getDiagramme().size();j++){
                 System.out.print(multiProcess.get(i).getDiagramme().get(j));
             }
             multiProcess.get(i).getDiagramme().clear();
         }
         System.out.println(" \n ***************** ES ****************** ");
             for(int j=0; j<ES.getDiagramme().size();j++){
                 System.out.print(ES.getDiagramme().get(j));
             }
             ES.getDiagramme().clear();
             multiProcess.clear();
             ALLProcessus.clear();
     }
}
