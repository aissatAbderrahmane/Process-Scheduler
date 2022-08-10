
package algorithmes;
import java.util.*;
public class temps {
    
     private ArrayList<Processus> PRC = new ArrayList<>();
    public temps(ArrayList<Processus> proc){
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
    public void ReOrderPlusCourt(ArrayList<Processus> List){
        Processus.type_compare = 3;
        Collections.sort(List);
    }
}
