
package Algorithmes;

import java.util.ArrayList;
import java.util.Collections;
import sim.Sim;

public class Priorite{
    private ArrayList<Processus> PRC = new ArrayList<>();
    public Priorite(ArrayList<Processus> proc){
        PRC = proc;
    }
    public ArrayList<Processus> getListProcessByTempsArive(int tempArive){
        ArrayList<Processus> returner = new ArrayList<>();
        for(int i =0;i<PRC.size();i++){
            if(PRC.get(i).getTArrive() == tempArive){
                returner.add(PRC.get(i));
            }
        }
        return returner ;
    }
    public int getListSize(){
        return PRC.size();
    }
    public void ReOrderPriorite(ArrayList<Processus> lst){
        Processus.type_compare = 2;
        Collections.sort(lst);
    }
}
