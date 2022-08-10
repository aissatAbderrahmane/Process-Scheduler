
package Algorithmes;
import java.util.*;
import sim.Ordonnanceur;
public class EntreSortie {
    private final  ArrayList<Processus> Attentes = new ArrayList<>();
    private final ArrayList<Processus> RevenirVAttentes = new ArrayList<>();
    private final Ordonnanceur scheduler;
    private final ArrayList<String> Resulte = new ArrayList<>(); 
    public EntreSortie(Ordonnanceur ord){
        scheduler = ord;
    }
    public void AddProcess(Processus P){
        RevenirVAttentes.add(P);
    }
    public void TraiteAttente(){
        if(!RevenirVAttentes.isEmpty()){
                Processus.type_compare = 0;
                Collections.sort(RevenirVAttentes);
            if(RevenirVAttentes.size()>1){
                for(int i=0; i<RevenirVAttentes.size();i++){
                Attentes.add(RevenirVAttentes.get(i));
                }
            }else Attentes.add(RevenirVAttentes.get(0));
            RevenirVAttentes.clear();
        }
    }
    public ArrayList<Processus> getFille(){
        return Attentes;
    }
    public Boolean AttenteStatus(){
        return (!Attentes.isEmpty() || !RevenirVAttentes.isEmpty());
    }
    private void ReturnToCPU(){
        scheduler.AddProcess(Attentes.get(0));
    }
    public ArrayList<String> getResult(){
        return Resulte;
    }
    public void Attente(){
        System.out.print(" [ES ]: ");
        for(int j=0; j<Attentes.size();j++){
            System.out.print(" [P"+Attentes.get(j).getID()+"] ");
        }
    }
    public void Execute(){
        TraiteAttente();
        if(!Attentes.isEmpty()){
        int orlPlus=0;
            int p_cal = Attentes.get(0).getTCalcule().get(0);
            if(p_cal == 0){
                Attentes.get(0).FinCalcule(0);
                Resulte.add(" [ P"+Attentes.get(0).getID()+" A : "+CPU.OrlogeTEST+"]  ");
                ReturnToCPU();
                Attentes.remove(0);
                orlPlus=1;
            }
            else{
                Attentes.get(0).setTCalcule(0,(p_cal-1 > 0)? p_cal-1 : 0);
                orlPlus=0;
            } 
            if(Attentes.size()>0 && orlPlus == 1) Execute();
        }
        // when adding the list of CPU don't forget to mak a return to the CPU using the ID exist in the Processus CPU_ID
    }
}

