package application;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
public class AjouterProcessus implements Initializable{
    @FXML
    private JFXTextField Tarrive;

    @FXML
    private JFXTextField priorite;

    @FXML
    private JFXTextField uc1;

    @FXML
    private JFXTextField es;

    @FXML
    private JFXTextField uc2;
    private Notifications ErrorReport = Notifications.create();
    @FXML
    private JFXTreeTableView<ProcessCal> ProcessCalcules = new JFXTreeTableView();
    private ObservableList<ProcessCal> calcal = FXCollections.observableArrayList();
    @FXML
    private JFXButton addcalc;
    @FXML
    void DisableAddUnCalcule(ActionEvent event){
        ErrorDisplay("Vous Avez Ajouter Un Seul Calcule A çe Processeur Vous ne pouver pas ajouter des Calcules !");
    }
    @FXML
    private JFXButton delCalc;
    @FXML
    void DeleteCalcule(ActionEvent event) {
        if(ProcessCalcules.getColumns().size()>0){
            if(ProcessCalcules.getSelectionModel().getSelectedIndex()>-1){
                calcal.remove(ProcessCalcules.getSelectionModel().getSelectedIndex());
                if(calcal.size()==0){
                  addcalc.setOnAction(new EventHandler<ActionEvent>() {
                  @Override public void handle(ActionEvent event) {
                            if(VeriFyINT(uc1.getText()))
            if(Integer.parseInt(uc1.getText())>0){
                if(VeriFyINT(es.getText()))
                {
                    if(Integer.parseInt(es.getText())>0){
                        if(VeriFyINT(uc2.getText())){
                            if(Integer.parseInt(uc2.getText())>0){
                                calcal.add(new ProcessCal(Integer.parseInt(uc1.getText()),Integer.parseInt(es.getText()),Integer.parseInt(uc2.getText())));
                            }else ErrorDisplay("la 2eme Unité de Calcule doit avoir une valeur superieur a 0.");
                        }else ErrorDisplay("Il Faut donné la valeur de la 2eme Unité Calcule.");
                    }else ErrorDisplay("l'Entré/Sortie doit avoir une valeur superieur a 0.");
                }else{
                    calcal.add(new ProcessCal(Integer.parseInt(uc1.getText()),0,0));
                    addcalc.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent t) {

        ErrorDisplay("Vous Avez Ajouter Un Seul Calcule A çe Processeur Vous ne pouver pas ajouter des Calcules !");
                           }
                    });
                }
            }
            else ErrorDisplay("l'Unité de Calcule doit avoir une valeur superieur a 0.");
        else ErrorDisplay("Il Faut Donné Une Valeur Numeric");
                         }
                     });
                }
            }else ErrorDisplay("Il Faut Selectionné Une Colonne Pour Suprrimé !");
        }else ErrorDisplay("Il Faut Ajouter Des Calcules Pour Pouvoir Les Suprimmer !");
    }
    
    @FXML
    public void ajouterUnCalcule(ActionEvent event) throws IOException{
        if(VeriFyINT(uc1.getText()))
            if(Integer.parseInt(uc1.getText())>0){
                if(VeriFyINT(es.getText()))
                {
                    if(Integer.parseInt(es.getText())>0){
                        if(VeriFyINT(uc2.getText())){
                            if(Integer.parseInt(uc2.getText())>0){
                                calcal.add(new ProcessCal(Integer.parseInt(uc1.getText()),Integer.parseInt(es.getText()),Integer.parseInt(uc2.getText())));
                            }else ErrorDisplay("la 2eme Unité de Calcule doit avoir une valeur superieur a 0.");
                        }else ErrorDisplay("Il Faut donné la valeur de la 2eme Unité Calcule.");
                    }else ErrorDisplay("l'Entré/Sortie doit avoir une valeur superieur a 0.");
                }else{
                    calcal.add(new ProcessCal(Integer.parseInt(uc1.getText()),0,0));
                    addcalc.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent t) {

        ErrorDisplay("Vous Avez Ajouter Un Seul Calcule A çe Processeur Vous ne pouver pas ajouter des Calcules !");
                           }
                    });
                }
            }
            else ErrorDisplay("l'Unité de Calcule doit avoir une valeur superieur a 0.");
        else ErrorDisplay("Il Faut Donné Une Valeur Numeric");
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
        initTable();
    }
    private void initTable(){
        JFXTreeTableColumn<ProcessCal, Integer> colUC = new JFXTreeTableColumn("UC");
        JFXTreeTableColumn<ProcessCal, Integer> colES = new JFXTreeTableColumn("ES");
        JFXTreeTableColumn<ProcessCal, Integer> colUC2 = new JFXTreeTableColumn("UC");
        colUC.setPrefWidth(130);
        colUC.setEditable(false);
        colUC.setResizable(false);
        colES.setPrefWidth(130);
        colES.setEditable(false);
        colES.setResizable(false);
        colUC2.setPrefWidth(130);
        colUC2.setEditable(false);
        colUC2.setResizable(false);
        colUC.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProcessCal, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<ProcessCal, Integer> p) {
                return p.getValue().getValue().getUC();
            }
        });                
        colES.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProcessCal, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<ProcessCal, Integer> p) {
                return p.getValue().getValue().getES();
            }
        });
        colUC2.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProcessCal, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<ProcessCal, Integer> p) {
                return p.getValue().getValue().getUC2();
            }
        });
        TreeItem<ProcessCal> R = new RecursiveTreeItem<ProcessCal>(calcal, RecursiveTreeObject::getChildren);
        ProcessCalcules.getColumns().setAll(colUC,colES,colUC2);
        ProcessCalcules.setRoot(R);
        ProcessCalcules.setShowRoot(false);
        ProcessCalcules.getColumns().addListener(new ListChangeListener() {
          public boolean suspended;
          @Override
          public void onChanged(Change change) {
              change.next();
              if (change.wasReplaced() && !suspended) {
                  this.suspended = true;
                  ProcessCalcules.getColumns().setAll(colUC,colES,colUC2);
                  this.suspended = false;
              }
          }
      });
    }
    private Boolean VeriFyINT(String txt){
        return (!"".equals(txt)  && txt.matches("[0-9]*"));
    }
    class ProcessCal extends RecursiveTreeObject<ProcessCal>{
        IntegerProperty UC1;
        IntegerProperty ES;
        IntegerProperty UC2;
        public ProcessCal(int uc1,int es,int uc2){
            this.UC1 = new SimpleIntegerProperty(uc1);
            this.ES = new SimpleIntegerProperty(es);
            this.UC2 = new SimpleIntegerProperty(uc2);
        }

        public ObservableValue<Integer> getUC(){
            return UC1.asObject();
        }
        public ObservableValue<Integer> getUC2(){
            return UC2.asObject();
        }
        public ObservableValue<Integer> getES(){
            return ES.asObject();
        }
         
    }
    
}

