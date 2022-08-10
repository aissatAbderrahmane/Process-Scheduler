package application;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
public class Application implements Initializable{
    @FXML
    private Pane menu;
    @FXML
    private JFXButton ader;
    public static Boolean ff,rr,prio,prioprem,pls,plsprem;
    public static int NCPU;
    private ArrayList<JFXButton> btn = new ArrayList<>();
    private double ScalX = 65;
    @FXML
    private JFXTreeTableView<Proc> ProcessusTree;
    private ObservableList<Proc> ob = FXCollections.observableArrayList();
    @FXML
    private void AddToTable(ActionEvent event) {
        try{
	FXMLLoader loading = new FXMLLoader(getClass().getResource("AddProcess.fxml"));
	Parent roots = (Parent) loading.load();
	Stage stg = new Stage();
	stg.setTitle("Ajouter Un/Des Processus(s) ");
	stg.setScene(new Scene(roots));
	stg.show();
        }catch(Exception e){
	System.out.println("Error Loading Add Process !");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXTreeTableColumn<Proc, Integer> colID = new JFXTreeTableColumn("ID");
        JFXTreeTableColumn<Proc, Integer> colUC = new JFXTreeTableColumn("UC");
        JFXTreeTableColumn<Proc, Integer> colES = new JFXTreeTableColumn("ES");
        JFXTreeTableColumn<Proc, Integer> colUC2 = new JFXTreeTableColumn("UC");
        JFXTreeTableColumn<Proc, Integer> colES2 = new JFXTreeTableColumn("ES");
        colID.setPrefWidth(50);
        colUC.setPrefWidth(50);
        colUC.setEditable(true);
        colES.setPrefWidth(50);
        colUC2.setPrefWidth(50);
        colES2.setPrefWidth(50);
        colID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Proc, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<Proc, Integer> p) {
                return p.getValue().getValue().getID();
            }
        });
        colUC.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Proc, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<Proc, Integer> p) {
                return p.getValue().getValue().getUC();
            }
        });                
        colES.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Proc, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<Proc, Integer> p) {
                return p.getValue().getValue().getES();
            }
        });
        colUC2.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Proc, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<Proc, Integer> p) {
                return p.getValue().getValue().getUC2();
            }
        });
        colES2.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Proc, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<Proc, Integer> p) {
                return p.getValue().getValue().getES2();
            }
        });
        
        ob.add(new Proc(2,3,5,6,7));        
        ob.add(new Proc(2,3,5,6,7));
        ob.add(new Proc(2,3,5,6,7));
        TreeItem<Proc> trs = new RecursiveTreeItem<Proc>(ob, RecursiveTreeObject::getChildren);
        ProcessusTree.getColumns().setAll(colID,colUC,colES,colUC2,colES2);
        
        ProcessusTree.setRoot(trs);
        ProcessusTree.setShowRoot(false);
        
    }
    
    private void IdeaAddCPU(){
        for(int i=0; i<NCPU;i++){
            btn.add(new JFXButton(""));
            btn.get(i).setLayoutX(ScalX);
            btn.get(i).setLayoutY(5);
            btn.get(i).getStyleClass().add("NCPU");
            ScalX += btn.get(i).getWidth() + 60 ;
            btn.get(i).setFocusTraversable(false);
        }
        menu.getChildren().addAll(btn);
    }
    class Proc extends RecursiveTreeObject<Proc>{
        IntegerProperty Id;
        IntegerProperty UC1;
        IntegerProperty ES;
        IntegerProperty UC2;
        IntegerProperty ES2;
        public Proc(int id,int uc1,int es,int uc2, int es2){
            this.Id = new SimpleIntegerProperty(id);
            this.UC1 = new SimpleIntegerProperty(uc1);
            this.ES = new SimpleIntegerProperty(es);
            this.UC2 = new SimpleIntegerProperty(uc2);
            this.ES2 = new SimpleIntegerProperty(es2);
            
        }
        public ObservableValue<Integer> getID(){
            return Id.asObject();
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
        public ObservableValue<Integer> getES2(){
            return ES2.asObject();
        }          
    }
    
}

