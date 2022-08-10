
package Algorithmes;
import java.util.ArrayList;

public class RoundRobin {
     private ArrayList<Processus> PRC = new ArrayList<>();
    public RoundRobin(ArrayList<Processus> proc){
        PRC = proc;
    }
    public ArrayList<Processus> getListProcessByTempsArive(int tempArive){
        ArrayList<Processus> returner = new ArrayList<>();
        for(int i =0;i<PRC.size();i++){
            if(PRC.get(i).getTArrive() == tempArive){
                returner.add(PRC.get(i));
            }
        }
        return  returner ;
    }
    public int getListSize(){
        return PRC.size();
    }
}
