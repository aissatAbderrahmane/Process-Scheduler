
package algorithmes;

import java.util.ArrayList;
import java.util.Collections;

public class Priorite{
    private ArrayList<Processus> PRC = new ArrayList<>();
    public Priorite(ArrayList<Processus> proc){
        PRC = proc;
    }
    public ArrayList<Processus> getListProcessByTempsArive(int tempArive){
        ArrayList<Processus> returner = new ArrayList<>();
        for(int i =0;i<PRC.size();i++){
            if(PRC.get(i).getTArrive() == tempArive){
            	System.out.println("Le processus "+PRC.get(i).getID()+" arrive dans le System.");
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
