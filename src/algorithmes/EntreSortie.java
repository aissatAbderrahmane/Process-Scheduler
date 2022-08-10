
package algorithmes;
import java.util.*;
import application.Ordonnanceur;
public class EntreSortie {
    private final  ArrayList<Processus> Attentes = new ArrayList<>();
    private final ArrayList<Processus> RevenirVAttentes = new ArrayList<>();
    private final Ordonnanceur scheduler;
    private final ArrayList<String> Resulte = new ArrayList<>(); 
    private final ArrayList<Integer> Diagrammes = new ArrayList<>(); 
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
        if(!Attentes.isEmpty()) Diagrammes.add(Attentes.get(0).getID());
    }
    public ArrayList<Integer> getDiagramme(){
        return Diagrammes;
    }
    public ArrayList<Processus> getFille(){
        return Attentes;
    }
    public Boolean AttenteStatus(){
        if((Attentes.isEmpty() && RevenirVAttentes.isEmpty())) Diagrammes.add(-1);
        return (!Attentes.isEmpty() || !RevenirVAttentes.isEmpty());
    }
    private void ReturnToCPU(){
        scheduler.AddProcess(Attentes.get(0));
    }
    public ArrayList<String> getResult(){
        return Resulte;
    }
    public int getAttenteSize(){
        return Attentes.size();
    }
    private ArrayList<Integer> terminerA = new ArrayList<>();
    private ArrayList<Integer> ProcessusterminerID = new ArrayList<>();
    private ArrayList<Integer> EntrerA = new ArrayList<>();
    private void END(Processus P) {
    	terminerA.add(CPU.OrlogeTEST);
    	ProcessusterminerID.add(P.getID());
    }
    private void EnterAt() {
    	if(!Diagrammes.isEmpty()) {
    		if(Diagrammes.get(Diagrammes.size()-1) != Attentes.get(0).getID()) {
    			EntrerA.add(CPU.OrlogeTEST);
    		}
    	}
    }
    public void Execute(){
        TraiteAttente();
        if(!Attentes.isEmpty()){
        int orlPlus=0;
        	
            int p_cal = Attentes.get(0).getTCalcule().get(0);
            if(p_cal == 0){
                Attentes.get(0).FinCalcule(0);
                END(Attentes.get(0));
                Diagrammes.add(Attentes.get(0).getID());
                ReturnToCPU();
                System.out.println("Le processus "+Attentes.get(0).getID()+" a terminé une E/S.");
                Attentes.remove(0);
                if(Attentes.isEmpty()) Diagrammes.add(-1);
                orlPlus=1;
            }
            else{
                Attentes.get(0).setTCalcule(0,(p_cal-1 > 0)? p_cal-1 : 0);
                orlPlus=0;
            } 
            if(Attentes.size()>0 && orlPlus == 1){
                Execute();
            }
        }
    }
}


