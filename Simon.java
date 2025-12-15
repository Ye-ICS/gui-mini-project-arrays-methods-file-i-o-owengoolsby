import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Template JavaFX application.
 */
public class Simon extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    
    @Override
    public void start(Stage stage) {
        
        WelcomeView rootNode = new WelcomeView();
        Scene welcomeScene = new Scene(rootNode);
        stage.setScene(welcomeScene);
        stage.show();

    }
}
