
package Algorithmes;
import java.util.*;
public class CPU  implements Comparable{
    private int ID;
    private ArrayList<Processus> Attente = new ArrayList<>(); // file d'attente.
    private int qauntum_orloger = 0;
    public static int OrlogeTEST = 0 , Preemptif = 0;
    private int status;
    public static int typeCMP = 1;
    private ArrayList<String> Result = new ArrayList<>(); 
    private EntreSortie E_S;
    public static Priorite Prio = null;
    public static temps Temps = null;
    public CPU(int id){
        ID = id;
        this.status = 0;
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
        if(getAttenteSize() == 0) setStatus(0);
    }

    public void Execute(Processus P,EntreSortie ES){
        int onePlus = 0;
        if(P.getTCalcule().isEmpty()){
            FinDeTache(0);
            onePlus = 1;
        }else{
            int p_calcule = P.getTCalcule().get(0);
            if(p_calcule == 0){
                P.FinCalcule(0);
                Result.add(" [ P"+P.getID()+"  A: "+CPU.OrlogeTEST+"]");
                if(!(P.getTCalcule().isEmpty())) {
                    ES.AddProcess(P);
                 }
                 FinDeTache(0);
                 onePlus = 1;
            }else{
                P.setTCalcule(0,(p_calcule-1 > 0)? p_calcule-1 : 0);
                if(p_calcule == 0){
                 FinDeTache(0);
                 onePlus = 1;
                }
            }
        }
        if(Attente.size()>0 && onePlus==1) Execute(Attente.get(0),ES);
        if(AttenteStatus() == false) setStatus(0); 
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
    
}
