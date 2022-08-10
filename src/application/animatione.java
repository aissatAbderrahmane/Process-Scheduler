package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class animatione implements Initializable{
    @FXML
    public Pane CPU1, CPU2, CPU3, ES;
    @FXML
    private Pane back, CPUrun, CPUrun2, CPUrun3, attES;
    @FXML
    private Button animer;
    private LinkedList<Shape> rectangles = new LinkedList<>();
    private Shape rec = new Rectangle(52,52);
    private Shape rec2 = new Rectangle(52,52);
    private Shape rec3 = new Rectangle(52,52);
    private Line ln , lnToCPU;
    @FXML
    private Label POS;
    private PathTransition follow = new PathTransition();
    private int Index =0, entred=0, CPUR1 =0,CPUR2 = 0,CPUR3 = 0;
    private ActionEvent evt;
    @FXML
    void animeME(ActionEvent event) {
    	evt = event;
    	follow.setNode(rectangles.get(Index));
    	if(Index == 5) {
    		lnToCPU = new Line(rectangles.get(Index-1).getLayoutX(),rectangles.get(Index-1).getLayoutY(),CPUrun.getLayoutX(),CPUrun.getLayoutY());
    		lnToCPU.setStroke(Color.TRANSPARENT);
    		follow.setPath(lnToCPU);
    		entred = 1;
    		CPUR1=1;
    	}else if(Index == 6 && CPUR1 == 1)
    		{
    		lnToCPU = new Line(rectangles.get(Index-1).getLayoutX(),rectangles.get(Index-1).getLayoutY(),CPUrun2.getLayoutX(),CPUrun2.getLayoutY());
    		lnToCPU.setStroke(Color.TRANSPARENT);
    		follow.setPath(lnToCPU);
    		entred = 2;
    		CPUR2=1;
    		}else if(Index == 8 && CPUR1 == 1 && CPUR2 == 1)
    		{
    		lnToCPU = new Line(rectangles.get(Index-1).getLayoutX(),rectangles.get(Index-1).getLayoutY(),CPUrun3.getLayoutX(),CPUrun3.getLayoutY());
    		lnToCPU.setStroke(Color.TRANSPARENT);
    		follow.setPath(lnToCPU);
    		entred = 3;
    		CPUR3=1;
    		}
    		else if(Index == 11 )
    		{
    		lnToCPU = new Line(rectangles.get(Index-1).getLayoutX(),rectangles.get(Index-1).getLayoutY(),ES.getLayoutX()-80,ES.getLayoutY());
    		lnToCPU.setStroke(Color.TRANSPARENT);
    		follow.setPath(lnToCPU);
    		entred = 3;
    		}
    		else if(Index == 15 )
    		{
    			follow.setNode(rectangles.get(5));
    		lnToCPU = new Line(CPUrun.getLayoutX(),CPUrun.getLayoutY(),attES.getLayoutX()-80,attES.getLayoutY());
    		lnToCPU.setStroke(Color.TRANSPARENT);
    		follow.setPath(lnToCPU);
    		entred = 3;
    		}
    		else 
    		follow.setPath(ln);
    	
    	if(entred > 3) {
    		rectangles.get(Index).setLayoutX(rectangles.get(Index).getLayoutX()-10);
    	}
    	follow.setDuration(Duration.millis(3000));
    	follow.play();
    	follow.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				Index++;
				
				if(Index != rectangles.size()) {
		    	animeME(evt);
		    	}
			}
    		
    	}
    			);

    }
    private void init_rect() {
    	double X = 0;
    	for(int i=0; i<20;i++) {
    		Shape temp = new Rectangle(10,10);
    		temp.setStroke(Color.WHITE);
    		temp.setLayoutX(X );
    		temp.setLayoutY(0);
    		rectangles.add(temp);
    		X += 12;
    	}
    }
    private void add_rect() {
    	for(int i=0 ;i <rectangles.size();i++) {
    		back.getChildren().add(rectangles.get(i));
    	}
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ln = new Line(0,0,CPU1.getLayoutX()+30,CPU1.getLayoutY()+30);
		ln.setStroke(Color.WHITE);
		rec.setStroke(Color.WHITE);
		rec.setLayoutX(0);
		rec.setLayoutY(0);
		rec2.setLayoutX(58);
		rec2.setLayoutY(0);
		rec2.setStroke(Color.GREEN);
		rec3.setLayoutX(116);
		rec3.setLayoutY(0);
		rec3.setStroke(Color.RED);
		init_rect();
		//back.getChildren().addAll(rec,rec2,rec3);
		back.getChildren().add(ln);
		add_rect(); 
	}

}
