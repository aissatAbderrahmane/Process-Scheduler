
package algorithmes;
import java.util.*;

import application.Ordonnanceur;
public class CPU  implements Comparable, Cloneable{
    private int ID;
    private Ordonnanceur scheduler;
    private ArrayList<Processus> Attente = new ArrayList<>(); // file d'attente.
    private int qauntum_orloger = 0;
    private ArrayList<Integer> Diagrammes; 
    public static int OrlogeTEST = 0 , Preemptif = 0;
    private int status;
    public static int typeCMP = 1;
    private ArrayList<String> Result = new ArrayList<>(); 
    private EntreSortie E_S;
    public static Priorite Prio = null;
    public static temps Temps = null;
    private ArrayList<Integer> terminerA;
    private ArrayList<Integer> ProcessusterminerID;
    private ArrayList<Integer> EntrerA;
    public CPU(int id,Ordonnanceur sched){
        ID = id;
        scheduler = sched;
        Diagrammes = new ArrayList<>(); 
        EntrerA = new ArrayList<>();
        terminerA = new ArrayList<>();
        ProcessusterminerID = new ArrayList<>();
        this.status = 0;
    }
    public ArrayList<Integer> getDiagramme(){
        return Diagrammes;
    }
    public int getStatus(){
        return status;
    }
    public void setStatus(int S){
        status = S;
    }
    public int getID(){
        return ID;
    }
    public int getAttenteSize(){
        return Attente.size();
    }
    public Boolean AttenteStatus(){
        return !Attente.isEmpty();
    } 
    private void FinDeTache(int i){
        Attente.remove(i);
        if(getAttenteSize() == 0)
            setStatus(0);
        
    }
    private void END(Processus P) {
    	terminerA.add(CPU.OrlogeTEST);
    	ProcessusterminerID.add(P.getID());
    }
    public void Execute(Processus P,EntreSortie ES){
        int onePlus = 0;
        if(ProcessusterminerID.size()>0) {
        	if(ProcessusterminerID.get(ProcessusterminerID.size()-1) != P.getID())
        		System.out.println("Le processus "+P.getID()+" est élu par le CPU "+ID+".");
        }
        	
        if(scheduler.CPUReady() && Attente.size()>1){
            scheduler.AddProcess(Attente.get(1));
            FinDeTache(1);
        }
        if(P.getTCalcule().isEmpty()){
            END(P);
            FinDeTache(0);
            System.out.println("Le processus "+P.getID()+" a terminé , dernierment dans le CPU "+ID+".");
            onePlus = 1;
        }else{
            int p_calcule = P.getTCalcule().get(0);
            if(p_calcule == 0){
                P.FinCalcule(0);
                if(!(P.getTCalcule().isEmpty())) {
                	System.out.println("Le processus "+P.getID()+" a terminé dans le CPU "+ID+" ,et envoyer vers E/S.");
                    ES.AddProcess(P);
                 }
                END(P);
                 FinDeTache(0);
                 onePlus = 1;
            }else{
                P.setTCalcule(0,(p_calcule-1 > 0)? p_calcule-1 : 0);
                if(p_calcule == 0){
                    END(P);
                 System.out.println("Le processus "+P.getID()+" a terminé , dernierment dans le CPU "+ID+".");
                 FinDeTache(0);
                 onePlus = 1;
                }
            }
        }
        if(Attente.size()>0 && onePlus==1) Execute(Attente.get(0),ES);
        if(AttenteStatus() == false)
            setStatus(0);
        
    }
    
    public ArrayList<Processus> getAttente(){
        return Attente;
    }
    public void lunch(EntreSortie ES){
        E_S = ES;
            if((!Attente.isEmpty())) {
                    if(Preemptif == 1) Prio.ReOrderPriorite(Attente);
                    else if(Preemptif == 2) Temps.ReOrderPlusCourt(Attente);
                Execute(Attente.get(0),ES);
             }
    }
    public void AddProcessus(Processus P){
        P.setID_CPU(this.ID);
        Attente.add(P);
        setStatus(1);
        System.out.println("Le processus "+P.getID()+" arrive dans la fille du CPU "+ID+".");
        if(Attente.size() == 1 ) Execute(Attente.get(0),E_S);
    }
    public void Fille_attenteFIFO(FIFO execFIFO,int ORL){
        ArrayList<Processus> tempA;
         tempA = execFIFO.getListProcessByTempsArive(ORL);
         if(!tempA.isEmpty()) 
            for(int j=0; j<tempA.size();j++)
                Attente.add(tempA.get(j));
    } 
    public void lunch_RR(FIFO RR,EntreSortie ES){
            if(!Attente.isEmpty()) {
                    if(qauntum_orloger < RR.getQuantum()){
                        Execute(Attente.get(0), ES);
                        qauntum_orloger++;
                    }
                    else{
                        qauntum_orloger = 0;
                        Processus Cloned =Attente.get(0);
                        Diagrammes.add(Cloned.getID());
                        FinDeTache(0);
                    Attente.add(Cloned);
                    }
            }
    }
    public ArrayList<String> getResult(){
        return Result;
    }
    public void Fille_attenteRR(RoundRobin execRR, int ORL){
        ArrayList<Processus> tempA;
         tempA = execRR.getListProcessByTempsArive(ORL);
         if(!tempA.isEmpty()) 
            for(int j=0; j<tempA.size();j++)
                Attente.add(tempA.get(j)); 
    }
    public void algo_PrioritePreemptif(Priorite execPRIO,EntreSortie ES){  
        if(Attente.size()>1) execPRIO.ReOrderPriorite(Attente);
        
    }
    public void ReturnFromES(Processus P,EntreSortie ES){
        AddProcessus(P);
        lunch(ES);
    }
    public void Fille_attentePriorite(Priorite execPRIO, int ORL){
        ArrayList<Processus> tempA = execPRIO.getListProcessByTempsArive(ORL);
         if(!tempA.isEmpty()){
             if(tempA.size()>1) execPRIO.ReOrderPriorite(tempA);
             for(int i=0; i<tempA.size();i++){
                    Attente.add(tempA.get(i));
             }  
         }
         if(Attente.size()>1) execPRIO.ReOrderPriorite(Attente);
    }
  
    public void algo_Temps(EntreSortie ES){
      lunch(ES);
    }
    public void algo_TempsDabord(temps execTEMPS,EntreSortie ES){
      if(Attente.size()>1)execTEMPS.ReOrderPlusCourt(Attente);
      lunch(ES);
    }        
    public void Fille_attenteTemps(temps execTEMPS, int ORL){
        ArrayList<Processus> tempA = execTEMPS.getListProcessByTempsArive(ORL);
                 if(!tempA.isEmpty()){
             if(tempA.size()>1) execTEMPS.ReOrderPlusCourt(tempA);
             for(int i=0; i<tempA.size();i++){
                    Attente.add(tempA.get(i));
            
             }
         }
         if(Attente.size()>1) execTEMPS.ReOrderPlusCourt(Attente);
    }

    @Override
    public int compareTo(Object Processeur) {
        int ToCompare, with =0;
        switch (typeCMP) {
            case 1:
                ToCompare = ((CPU)Processeur).getStatus();
                with = this.status;
                
                break;
            case 2:
                 ToCompare = ((CPU)Processeur).getAttenteSize();
                 with= this.getAttenteSize();
                break;
            default:
                  ToCompare = ((CPU)Processeur).getID();
                 with = this.getID();
                break;
        }
            //ASC : this-O
            //DSC : O-this
            return with - ToCompare;
    }
    @Override
    public CPU clone(){
        try{
        	CPU copy = (CPU)super.clone();
        	copy.ID = ID;
        	copy.scheduler = scheduler;
        	copy.Attente = new ArrayList<>(); // file d'attente.
        	copy.qauntum_orloger = 0;
        	copy.Diagrammes = Diagrammes; 
        	copy.status = status;
        	copy.Result = Result; 
        	copy.E_S = E_S;
        	copy.terminerA = terminerA;
        	copy.ProcessusterminerID = ProcessusterminerID;
        	copy.EntrerA = EntrerA;
        	return copy;
        }catch(CloneNotSupportedException e){
            throw new Error("\n [Erreur] : Clonnage impossible , la cause est:"+e.toString());
        }
    }
    
}
