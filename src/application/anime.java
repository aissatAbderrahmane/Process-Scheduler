package application;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.*;
public class anime implements Initializable{
    @FXML
    private Pane lefter;

    @FXML
    private Pane righter;
    private double prefWidth = 0,prefwidth2 = 607;
    @FXML
    void scla(ActionEvent event) {
    	righter.setPrefWidth(0);
    	lefter.prefWidth(0);
    	Timeline tm = new Timeline();
    	KeyFrame KR = new KeyFrame(Duration.ZERO, new KeyValue(righter.prefWidthProperty(),607));
    	KeyFrame KR2 = new KeyFrame(Duration.millis(3000), new KeyValue(righter.prefWidthProperty(), prefWidth));
    	KeyFrame KL = new KeyFrame(Duration.ZERO, new KeyValue(lefter.prefWidthProperty(),1));
    	KeyFrame KL2 = new KeyFrame(Duration.millis(3000), new KeyValue(lefter.prefWidthProperty(), prefwidth2));
    		tm.getKeyFrames().addAll(KR,KR2,KL,KL2);
    		tm.play();
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
