package application;

import com.jfoenix.controls.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import static javafx.scene.input.KeyCode.O;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
public class Home implements Initializable{
    private Boolean fifo = false,rr = false,p = false,pp = false,pl = false,plp = false;
    @FXML
    private JFXButton enterHome;
    @FXML
    private StackPane stck;
    @FXML
    private JFXToggleButton FIFO,RR,Priorite,PrioritePrem,PlusCourt,PlusCourtPrem;
    @FXML
    private JFXTextField ProcessNumber;
    @FXML
    private AnchorPane Panelm;
    private Boolean VeriFyINT(String txt){
        return (!"".equals(txt)  && txt.matches("[0-9]*"));
    }
    private Notifications ErrorReport = Notifications.create();
    @FXML
    private void VerifyToEnter(ActionEvent event) throws IOException {
        /*
            public static Boolean ff,rr,prio,prioprem,pls,plsprem;
    public static int NCPU; fifo,rr,p,pp,pl,plp
        */

        if(fifo) System.out.println("FIFO SELECTED");
        if(rr) System.out.println("Round Robin SELECTED");
        if(p)System.out.println("Priorite SELECTED");
        if(pp)System.out.println("Priorite Préemptif SELECTED");
        if(pl) System.out.println("Plus court d'abord SELECTED");
        if(plp) System.out.println("Temps Restant Plus court D'abord SELECTED");
        if(!fifo && !rr && !p && !pp && !pl && !plp){
            ErrorDisplay("Il Faut Choisir Au moin Un Algorithme !");
        }
        if(!"".equals(ProcessNumber.getText()) && VeriFyINT(ProcessNumber.getText())){
            Application.NCPU = Integer.parseInt(ProcessNumber.getText());
            Parent par = FXMLLoader.load(getClass().getResource("app.fxml"));
            Scene parSce = new Scene(par);
            Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stg.setScene(parSce);
            stg.show();
        }
        else{
            ErrorDisplay("Il Faut Donnée Une Valeur Numeric , et exactement Superieur a 0, pour le Nombre des Processeur !");
        }
    }
    private void ErrorDisplay(String txt){
                ErrorReport.title("Erreur !");
                ErrorReport.text(txt);
                ErrorReport.graphic(null);
                ErrorReport.hideAfter(Duration.seconds(3));
                ErrorReport.position(Pos.BOTTOM_RIGHT);
                ErrorReport.showInformation();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        FIFO.selectedProperty().addListener((new ChangeListener < Boolean >(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                fifo = FIFO.isSelected();
            }
        }));
        RR.selectedProperty().addListener((new ChangeListener < Boolean >(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                rr = RR.isSelected();
            }
        }));
        Priorite.selectedProperty().addListener((new ChangeListener < Boolean >(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                p = Priorite.isSelected();
            }
        }));
         PlusCourt.selectedProperty().addListener((new ChangeListener < Boolean >(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                pl = PlusCourt.isSelected();
            }
        }));
        PrioritePrem.selectedProperty().addListener((new ChangeListener < Boolean >(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                pp = PrioritePrem.isSelected();
            }
        }));
        PlusCourtPrem.selectedProperty().addListener((new ChangeListener < Boolean >(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                plp = PlusCourtPrem.isSelected();
            }
        }));
        
    }  
}
/*
    private JFXToggleButton FIFO,RR,Priorite,PrioritePrem,PlusCourt,PlusCourtPrem;
    @FXML
    private JFXTextField ProcessNumber;
    private Boolean fifo,rr,p,pp,pl,plp;
*/