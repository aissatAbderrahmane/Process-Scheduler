
package Algorithmes;
import java.util.*;
public class FIFO {
    private ArrayList<Processus> PRC = new ArrayList<>();
    private int quantum = 0;
    public FIFO(ArrayList<Processus> proc){
        PRC = proc;
    }
    public FIFO(ArrayList<Processus> proc, int qnt){
        PRC = proc;
        quantum = qnt;
    }
    public Processus getProcess(int n){
        return PRC.get(n);
    }
    public int getQuantum(){
        return quantum;
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
    public ArrayList<Processus> ReorderFIFO(ArrayList<Processus> P){
        Processus.type_compare = 0;
        Collections.sort(P);
        return P;
    }
    // cette fonction return la taille des list de processus existent dans FIFO
    public int getProcess_size(){
        return PRC.size();
    }
}
