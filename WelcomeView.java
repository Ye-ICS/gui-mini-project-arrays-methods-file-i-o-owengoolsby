import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WelcomeView extends VBox{
    WelcomeView() {
        Label label = new Label("Welcome");
        Button startBtn = new Button("Press to start");
        getChildren().addAll(label, startBtn);
        startBtn.setOnAction(e -> openSimonGame());
    }

    public void openSimonGame() {
        Scene currentScene = getScene();    
        SimonGame newRoot = new SimonGame();  
        currentScene.setRoot(newRoot);
        currentScene.getWindow().sizeToScene(); 
    }

}
