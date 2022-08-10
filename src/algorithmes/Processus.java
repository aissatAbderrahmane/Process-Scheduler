package algorithmes;
import java.util.*;
public class Processus  implements Comparable, Cloneable{
    private  int ID; // identifiant du proccessus.
    private int ID_CPU = -1; // identifiant du proccessus.
    private int T_Arrive; // temps d'arriver .
    private int Priorites; // priorité du processus.
    private ArrayList<Integer> T_Calcule; // temps de calcule, tableau car on peut avoir plusieur tranche.
    private int SommeCalcule = 0;
    public static int type_compare ;
    public Processus(int id,int T_A, ArrayList<Integer> T_C, int P){
        T_Arrive = T_A; // temps arriver
        T_Calcule = T_C; // N unité calcule 
        Priorites = P; // priorité
        ID=id;
        int  indexC = 0;
        for(int i=0; i<T_Calcule.size();i++){
            if(indexC%2 == 0) this.SommeCalcule += T_Calcule.get(i);
        }
    }
    public int getID(){
        return ID;
    }
    public int getID_CPU(){
        return ID_CPU;
    }
    public int getTArrive(){
        return T_Arrive;
    }
    public void setNewTCalcule(ArrayList<Integer> T){
        T_Calcule = T;
    }
    public ArrayList<Integer> getTCalcule(){
        return T_Calcule;
    }
    public void FinCalcule(int i){
        T_Calcule.remove(i);
    }
    public int getPriorite(){
        return Priorites;
    }
    public void setTArrive(int T_AR){
        T_Arrive = T_AR;
    }
    public void setTCalcule(int N , int UC){
        T_Calcule.set(N,UC);
    }
    public void setPriorite(int P){
        Priorites = P;
    }
    public void setID_CPU(int id_cpu){
        ID_CPU = id_cpu;
    }   
    public int getCalculesSome(){
        return SommeCalcule;
    }
    @Override
    public int compareTo(Object comparableProc) {
        int ToCompare, with =0;
        switch(type_compare){
            case 1 : 
                ToCompare = this.T_Arrive;
                with = ((Processus)comparableProc).getTArrive();
                break; // temps d'arriver
            case 2 : 
                ToCompare = ((Processus)comparableProc).getPriorite();
                with = this.Priorites;
                break; // priorité
            case 3 : 
                ToCompare = this.SommeCalcule;
                with = ((Processus)comparableProc).getCalculesSome();
                break; // plus court
            default: 
                ToCompare = this.ID; // Identifiant ASC ( 0 1 2 3 4)
                with = ((Processus)comparableProc).getID();
                break;
        }
             //ASC : this-O
            //DSC : O-this
        return ToCompare - with;
    }

    @Override
    public Processus clone(){
        try{
        Processus copy = (Processus)super.clone();
        copy.ID = ID;
        copy.ID_CPU = ID_CPU;
        copy.Priorites = Priorites;
        copy.T_Arrive = T_Arrive;
        copy.T_Calcule = (ArrayList<Integer>) T_Calcule.clone();
        copy.SommeCalcule = SommeCalcule;
        return copy;
        }catch(CloneNotSupportedException e){
            throw new Error("\n [Erreur] : Clonnage impossible , la cause est:"+e.toString());
        }
    }
}
