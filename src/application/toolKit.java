package application;

import java.util.ArrayList;

import org.controlsfx.control.Notifications;

import algorithmes.Processus;
import application.simulat.PCalcule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.util.Duration;

public class toolKit {
	private Notifications ErrorReport = Notifications.create();
	public int StringToInt(String txt) {
		if(isINT(txt)) {
			return Integer.parseInt(txt);
		}
		return -1;
	}
	public Boolean isINT(String txt) {
		return (!"".equals(txt) && txt.matches("[0-9]*"));
	}
    public void ErrorDisplay(String txt){
        ErrorReport.title("Erreur !");
        ErrorReport.text(txt);
        ErrorReport.graphic(null);
        ErrorReport.hideAfter(Duration.seconds(3));
        ErrorReport.position(Pos.BOTTOM_RIGHT);
        ErrorReport.showInformation();
    }
    public  ObservableList<PCalcule> clonnerObservableCalcule(ObservableList<PCalcule> clon){
    	ObservableList<PCalcule> cloned = FXCollections.observableArrayList();
        for(int i =0; i<clon.size();i++){
        	PCalcule P = (PCalcule) clon.get(i).clone();
            cloned.add(P);
        }
        return cloned;
    }
}
