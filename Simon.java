import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Template JavaFX application.
 */
public class Simon extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    int score = 0;

    @Override
    public void start(Stage stage) {
        // Create components to add.
        VBox contentBox = new VBox();
        contentBox.setAlignment(Pos.CENTER);
        
        Image blueBtnImage = new Image("images/blue_button.png");
        ImageView blueBtnImageView = new ImageView(blueBtnImage);
        
        Image redBtnImage = new Image("images/red_button.png");
        ImageView redBtnImageView = new ImageView(redBtnImage);

        Image yellowBtnImage = new Image("images/yellow_button.png");
        ImageView yellowBtnImageView = new ImageView(yellowBtnImage);

        Image greenBtnImage = new Image("images/green_button.png");
        ImageView greenBtnImageView = new ImageView(greenBtnImage);

        
        greenBtnImageView.setFitWidth(400);
        greenBtnImageView.setFitHeight(400);
        yellowBtnImageView.setFitWidth(400);
        yellowBtnImageView.setFitHeight(400);
        redBtnImageView.setFitWidth(400);
        redBtnImageView.setFitHeight(400);
        blueBtnImageView.setFitWidth(400);
        blueBtnImageView.setFitHeight(400);
        
        Label promptLabel = new Label();
        promptLabel.setText("Simon");
         
        
        Button redBtn = new Button();
        redBtn.setGraphic(redBtnImageView);
        
        Button blueBtn = new Button();
        blueBtn.setGraphic(blueBtnImageView);
        
        Button greenBtn = new Button();
        greenBtn.setGraphic(greenBtnImageView);
        
        Button yellowBtn = new Button();
        yellowBtn.setGraphic(yellowBtnImageView);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(0);
        grid.setVgap(0);

        grid.add(redBtn, 0, 0);
        grid.add(blueBtn, 1, 0);
        grid.add(greenBtn, 0, 1);
        grid.add(yellowBtn, 1, 1);

        contentBox.getChildren().addAll(promptLabel, grid);
        
        // Set up the window and display it.
        Scene scene = new Scene(contentBox, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("Simon Game");
        stage.show();
    }
    
}
