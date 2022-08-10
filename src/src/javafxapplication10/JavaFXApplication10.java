/*
 * Simulateur de gestion de Processus , Exxecution des Algotihemes de l'ordonancement par affinité
 * çe simulateur a était realiser par l'etudiant Aissat Abderrahmane Kaddour 
 * Dans l'university abd hamid Ibn Badis Mostaganem
 * Faculté Science Exact et l'informatique
 * Enseigné Par D.Bessouad Karim
*/
package src.javafxapplication10;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class JavaFXApplication10 extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
