package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import algorithmes.CPU;
import algorithmes.Processus;
import application.AjouterProcessus.ProcessCal;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class simulat implements Initializable{
	private static int cpuID = 0, ProcID = 0, CalcID = 0, indexModifie = -1, indexProcModifie = -1;
	private toolKit tool = new toolKit();
	private Boolean IsModfie = false,IsProcMidifie = false;
	private ArrayList<CPU> CPUL = new ArrayList<>();
	private ArrayList<Processus> ProcessusL = new ArrayList<>();
	public Ordonnanceur scheduler;
    @FXML
    private TableView<PCPU> CPUList = new TableView<>();
	private ObservableList<PCPU> obs = FXCollections.observableArrayList();
    @FXML
    private TableView<PProcess> ProcessusList;
    private ObservableList<PProcess> obsP = FXCollections.observableArrayList();
    @FXML
    private TableView<PCalcule> CalculeList;
    private ObservableList<PCalcule> obsC = FXCollections.observableArrayList();
    @FXML
    private AnchorPane MyWIndow;
    @FXML
    private Button fermer;
    @FXML
    private Button startSimulation;
    @FXML
    private Button saveProcess,addCPUs,deleteCPUs,editProcessus,deleteProcessus,addCalcule,editCalcule,deleteCalcule;
    @FXML
    private TextField typeALgo;
    @FXML
    private JFXTextField ITEMPS,IPRIO,UCES;
    private TableColumn<PCPU, Integer> IDCPU = new TableColumn<>("ID") ;
    private	TableColumn<PProcess, Integer> IDPROC = new TableColumn<>("ID") ;
    private	TableColumn<PCalcule, Integer> IDCALC = new TableColumn<>("Calcules");
    @FXML
    void CloseWindow(ActionEvent event) {
    	Stage mystg = (Stage) fermer.getScene().getWindow();
    	mystg.close();
    }
    private void getAllProcessus() {
    	for(int i=0; i<obsP.size();i++) {
    		ProcessusL.add(new Processus(obsP.get(i).getID(),obsP.get(i).getTemp(), obsP.get(i).getCalcules(),obsP.get(i).getPrioritee()));
    	}
    }
    private void getAllCPU() {
    	for(int i=0; i<obs.size();i++) {
    		CPUL.add(new CPU(obs.get(i).getID(), scheduler));
    	}
    }
    Timeline tm =  new Timeline(new KeyFrame(
	        Duration.millis(10),
	        ae -> Running()));
    @FXML
    void LancerLaSimulation(ActionEvent event) {
    	if(tool.StringToInt(typeALgo.getText()) > 0 && tool.StringToInt(typeALgo.getText()) < 7) {
    		typeALF = tool.StringToInt(typeALgo.getText());
    	init();
    	tm.setOnFinished(e -> tm.play());
		tm.play();
    	}else tool.ErrorDisplay("Il faut donné le type d'algorithme (FIFO:1, Prioritée:2, RoundRobin: 3, Temps Restant : 4, Priorité preemptif :5, plus court d'abord :6).");
    }
    Integer typeALF = 0;
    void init() {
    	scheduler = new Ordonnanceur();
    	getAllProcessus();
    	getAllCPU();
    		scheduler.init(clonnerC(CPUL), scheduler.clonner(ProcessusL), typeALF);
    	
    }
    void Running() {
    	System.out.println("instant "+CPU.OrlogeTEST);
    	scheduler.lunch(CPU.OrlogeTEST);
    	CPU.OrlogeTEST++;
    	if(scheduler.toStop) {
    		scheduler.getALLResult();
    		tm.stop();
    		tm =  new Timeline(new KeyFrame(
     		        Duration.millis(10),
     		        ae -> Running()));
    		tm.setOnFinished(e -> tm.play());
    		CPU.OrlogeTEST = 0;
    	}
    }
    public  ArrayList<CPU> clonnerC(ArrayList<CPU> clon){
        ArrayList<CPU> cloned = new ArrayList<>();
        for(int i =0; i<clon.size();i++){
            CPU P = (CPU) clon.get(i).clone();
            cloned.add(P);
        }
        return cloned;
    }
    @FXML
    void ModifierUnCalcule(ActionEvent event) {
    	if(obsC.size()>0) {
    		PCalcule calc = CalculeList.getSelectionModel().getSelectedItem();
    		if(calc != null) {
    	    	indexModifie = CalculeList.getSelectionModel().getSelectedIndex();
    	    	UCES.setText(""+calc.getValue());
    	    	IsModfie = true;
    		}else tool.ErrorDisplay("Il faut selectionné un calcule pour pouvoir le modifier .");
    	}else tool.ErrorDisplay("Il faut ajouter des calcules avant que vous pouviez les modifier .");
    }

    @FXML
    void SuprrimeUnCalcule(ActionEvent event) {
    	if(obsC.size()>0) {
    		PCalcule c = CalculeList.getSelectionModel().getSelectedItem();
    		if(c != null) {
    			obsC.remove(c);
    			if(obsC.size() == 0) {
    				CalcID = 0;
    			}
    			CalculeList.refresh();
    		}else tool.ErrorDisplay("Il faut selectionné un calcule pour pouvoir le suprrimé .");
    	}else tool.ErrorDisplay("Il faut ajouter des calcules avant que vous pouviez les supprimé .");
    }

    @FXML
    void ajouterUnCPU(ActionEvent event) {
		obs.add(new PCPU(cpuID));
		IDCPU.setCellValueFactory(cellData ->new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
		CPUList.setItems(obs);
		cpuID++;
    }
    @FXML
    void sauvgardeProcess(ActionEvent event) {
    	//PProcess , IDPROC , obsP ITEMPS,IPRIO 
    	if(IsProcMidifie == false) {
    	if(tool.StringToInt(ITEMPS.getText()) >= 0) {
    		if(tool.StringToInt(IPRIO.getText()) > 0) {
    				if(CalcID > 0) {
    				if(obsC.size()%2 != 0) {
    				PProcess P = new PProcess(ProcID,tool.StringToInt(ITEMPS.getText()),tool.StringToInt(IPRIO.getText()));
    				P.PCAL= tool.clonnerObservableCalcule(obsC);
    				P.SetCalcules(obsC);
    				obsP.add(P);
    					IDPROC.setCellValueFactory(cellData ->new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
    						ProcessusList.setItems(obsP);
    							CalcID = 0;
    								obsC.removeAll(obsC);
    									CalculeList.refresh();
    										ITEMPS.setText("");
    											IPRIO.setText("");
    												UCES.setPromptText("donné une unité de calcule");
    													ProcID++;
    				}else tool.ErrorDisplay("Il faut que les unité de calcule commance par une unité de calcule, et ce termine par une unité de calcule .");
    			}else tool.ErrorDisplay("Il faut donné au moins un calcule .");
    		}else tool.ErrorDisplay("Il faut donné une valeur numeric valide au Priorité, et superieur a 0 .");
    		}else tool.ErrorDisplay("Il faut donné une valeur numeric valide au temps d'arrivé .");
    		
    	}else{
            	if(tool.StringToInt(ITEMPS.getText()) >= 0) {
            		if(tool.StringToInt(IPRIO.getText()) > 0) {
            			if(CalcID > 0) {
            				if(obsC.size()%2 != 0) {
            				PProcess P = obsP.get(indexProcModifie);
            				P.PCAL= tool.clonnerObservableCalcule(obsC);
            				P.setTemps(tool.StringToInt(ITEMPS.getText()));
            				P.setPrioritee(tool.StringToInt(IPRIO.getText()));
            				obsP.set(indexProcModifie, P);
            				CalcID = 0;
            				obsC.removeAll(obsC);
            				CalculeList.refresh();
            				ITEMPS.setText("");
            				IPRIO.setText("");
            				UCES.setPromptText("donné une unité de calcule");
            				indexProcModifie = -1;
            				IsProcMidifie = false;
                    		}else tool.ErrorDisplay("Il faut que les unité de calcule commance par une unité de calcule, et ce termine par une unité de calcule .");
            			}else tool.ErrorDisplay("Il faut donné au moins un calcule .");
            		}else tool.ErrorDisplay("Il faut donné une valeur numeric valide au Priorité, et superieur a 0 .");
            	}else tool.ErrorDisplay("Il faut donné une valeur numeric valide au temps d'arrivé .");
    	}
    }
    @FXML
    void ajouterUnCalcule(ActionEvent event) {
    	if(IsModfie == false) {
    	if(tool.StringToInt(UCES.getText()) > 0) {
    		obsC.add(new PCalcule(CalcID,tool.StringToInt(UCES.getText())));
		IDCALC.setCellValueFactory(cellData ->new SimpleIntegerProperty(cellData.getValue().getValue()).asObject());
		CalculeList.setItems(obsC);
		CalcID++;
		if(CalcID %2 != 0) UCES.setPromptText("donné un valeur d'entré/sortie");
		else UCES.setPromptText("donné une unité de calcule");
		UCES.setText("");
    	}else tool.ErrorDisplay("Il faut donné une valeur numeric valide, et superieur a 0.");
    	}else{
    		if(tool.StringToInt(UCES.getText()) > 0) {
    			obsC.get(indexModifie).setVal(tool.StringToInt(UCES.getText()));
    			CalculeList.refresh();
    			IsModfie = false;
    			indexModifie = -1;
    			UCES.setText("");
    		}else tool.ErrorDisplay("Il faut donné une valeur numeric valide, et superieur a 0.");
    	}
    }
    @FXML
    void modifierUnProcessus(ActionEvent event) {
    	if(obsP.size()>0) {
    		PProcess P = ProcessusList.getSelectionModel().getSelectedItem();
    		if(P != null) {
    	    	ITEMPS.setText(""+P.getTemp());
    	    	IPRIO.setText(""+P.getPrioritee());
    	    	indexProcModifie = ProcessusList.getSelectionModel().getSelectedIndex();
    	    	obsC = P.getCALC();
    	    	CalculeList.getItems().clear();
    	    	CalculeList.setItems(obsC);
    	    	CalculeList.refresh();
    	    	CalcID = P.getCALC().size();
    	    	if(CalcID %2 != 0) UCES.setPromptText("donné un valeur d'entré/sortie");
    			else UCES.setPromptText("donné une unité de calcule");
    	    	IsProcMidifie = true;
    		}else tool.ErrorDisplay("Il faut selectionné un Processus pour pouvoir le modifier .");
    	}else tool.ErrorDisplay("Il faut ajouter des Processus avant que vous pouviez les modifiers .");
    }

    @FXML
    void suprrimeUnCPU(ActionEvent event) {
    	if(obs.size()>0) {
    		PCPU o = CPUList.getSelectionModel().getSelectedItem();
    		if(o != null) {
    			obs.remove(o);
    			if(obs.size() == 0) {
    			cpuID = 0;
    			}
    			CPUList.refresh();
    		}else tool.ErrorDisplay("Il faut selectionné un Processeur a superrimé !");
    	}else tool.ErrorDisplay("Il faut ajouter des processeurs avant de vouloir les supprimé.");
    }

    @FXML
    void suprrimeUnProccessus(ActionEvent event) {
    	if(obsP.size()>0) {
    		PProcess o = ProcessusList.getSelectionModel().getSelectedItem();
    		if(o != null) {
    			obsP.remove(o);
    			if(obsP.size() == 0) {
    				ProcID = 0;
    			}
    			ProcessusList.refresh();
    		}else tool.ErrorDisplay("Il faut selectionné un Processus a superrimé !");
    	}else tool.ErrorDisplay("Il faut ajouter des Processus avant de vouloir les supprimé.");
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		IDCPU.setResizable(false);
		IDCPU.setPrefWidth(235);
		CPUList.getColumns().add(IDCPU);
		IDPROC.setResizable(false);
		IDPROC.setPrefWidth(265);
		ProcessusList.getColumns().add(IDPROC);
		IDCALC.setResizable(false);
		IDCALC.setPrefWidth(225);
		CalculeList.getColumns().add(IDCALC);
	}
	class PCPU{
		IntegerProperty Ids;
		public PCPU(int i) {
			Ids = new SimpleIntegerProperty(i);
		}
		public  Integer getID(){
			return Ids.get();
		}
	}
	class PProcess{
		IntegerProperty identfiant;
		IntegerProperty TempsArrivee;
		IntegerProperty Prioritee;
		ArrayList<Integer> calcules = new ArrayList<>();
		public ObservableList<PCalcule> PCAL;
		public PProcess(int i,int t, int p) {
			identfiant = new SimpleIntegerProperty(i);
			TempsArrivee = new SimpleIntegerProperty(t);
			Prioritee = new SimpleIntegerProperty(p);
		}
		public  Integer getID(){
			return identfiant.get();
		}
		public Integer getTemp() {
			return TempsArrivee.get();
		}
		public Integer getPrioritee() {
			return Prioritee.get();
		}
		public void SetCalcules(ObservableList<PCalcule> calc) {
			
			for(int j=0; j<calc.size();j++) {
				calcules.add(calc.get(j).getValue());
			}
		}
		public void setCALC(ObservableList<PCalcule> calc) {
			PCAL = calc;
		}
		public void setTemps(int t) {
			TempsArrivee = new SimpleIntegerProperty(t);
		}
		public void setPrioritee(int p) {
			Prioritee = new SimpleIntegerProperty(p);
		}
		public ArrayList<Integer> getCalcules(){
			return calcules;
		}
		public ObservableList<PCalcule> getCALC(){
			return PCAL;
		}
	}
	class PCalcule implements Cloneable{
		IntegerProperty Ids;
		IntegerProperty Val;
		public PCalcule(int i, int v) {
			Ids = new SimpleIntegerProperty(i);
			Val = new SimpleIntegerProperty(v);
		}
		public Integer getID(){
			return Ids.get();
		}
		public Integer getValue() {
			return Val.get();
		}
		public void setVal(int v) {
			Val = new SimpleIntegerProperty(v);
		}
	    public PCalcule clone(){
	        try{
	        	PCalcule copy = (PCalcule)super.clone();
	        copy.Ids = Ids;
	        copy.Val = Val;
	        return copy;
	        }catch(CloneNotSupportedException e){
	            throw new Error("\n [Erreur] : Clonnage impossible , la cause est:"+e.toString());
	        }
	    }
	}

}
