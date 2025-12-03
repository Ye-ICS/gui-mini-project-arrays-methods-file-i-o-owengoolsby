import java.util.ArrayList;
import java.util.Random;
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Template JavaFX application.
 */
public class Simon extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    int score = 0;
    private int highscore = 0;
    private static final String HIGHSCORE_FILE = "high_score.txt";

    private Button redBtn;
    private Button blueBtn;
    private Button greenBtn;
    private Button yellowBtn;
    private Button startBtn;
    private Label promptLabel;


    private static final int RED = 0;
    private static final int BLUE = 1;
    private static final int GREEN = 2;
    private static final int YELLOW = 3;

    private ArrayList<Integer> sequence = new ArrayList<>();

    private Random random = new Random();
    
    private int userSequenceIndex = 0;



    @Override
    public void start(Stage stage) {

        loadHighScore();

        Label promptLabel = new Label();
        promptLabel.setText("Simon Highscore:" + highscore);

        // Create components to add.
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        
        Image blueBtnImage = new Image("images/blue_button.png");
        ImageView blueBtnImageView = new ImageView(blueBtnImage);
        
        Image redBtnImage = new Image("images/red_button.png");
        ImageView redBtnImageView = new ImageView(redBtnImage);

        Image yellowBtnImage = new Image("images/yellow_button.png");
        ImageView yellowBtnImageView = new ImageView(yellowBtnImage);

        Image greenBtnImage = new Image("images/green_button.png");
        ImageView greenBtnImageView = new ImageView(greenBtnImage);

        int btnSize = 150;
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

        Button startBtn = new Button("Start Game");
        startBtn.setOnAction(e -> {
            sequence.clear();
            score = 0;
            promptLabel.setText("Watch closely, you gotta remember");
            startBtn.setDisable(true);
            generateNextMove();
        });
        
        Button redBtn = new Button();
        redBtn.setGraphic(redBtnImageView);
        
        Button blueBtn = new Button();
        blueBtn.setGraphic(blueBtnImageView);
        
        Button greenBtn = new Button();
        greenBtn.setGraphic(greenBtnImageView);
        
        Button yellowBtn = new Button();
        yellowBtn.setGraphic(yellowBtnImageView);

        redBtn.setOnAction(e -> {
        checkUserInput(RED);
        });
        blueBtn.setOnAction(e -> {
        checkUserInput(BLUE);
        });
        greenBtn.setOnAction(e -> {
        checkUserInput(GREEN);      
        });
        yellowBtn.setOnAction(e -> {
        checkUserInput(YELLOW);
        });
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(0);
        grid.setVgap(0);

        grid.add(redBtn, 0, 0);
        grid.add(blueBtn, 1, 0);
        grid.add(greenBtn, 0, 1);
        grid.add(yellowBtn, 1, 1);
        
        contentBox.getChildren().addAll(startBtn, promptLabel, grid);
        
        // Set up the window and display it.
        Scene scene = new Scene(contentBox, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("Simon Game");
        stage.show();
    }

    private void saveHighScore(){
        if (score > highscore) {
            highscore = score;
            File File = new File(HIGHSCORE_FILE);
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(highscore);
                System.out.println("New High Score saved: " + highscore);
            } catch (java.io.IOException e) {
                System.out.println("Could not save high score to file" + e.getMessage());
            }
        }
    }
    
    private void checkUserInput (int colorCode) {
        if (colorCode == sequence.get(userSequenceIndex)) {
            userSequenceIndex++;
            if (userSequenceIndex == sequence.size()) {
                score=sequence.size();
                Timeline pause = new Timeline(new KeyFrame(
                    Duration.seconds(1.5),
                    e -> generateNextMove()
                ));
                pause.play();
            }
        }
        else {
            disableColorButtons(true);
            promptLabel.setText("Game over! Final score:" + score + "Press start to play agian");
            saveHighScore();
        }
    }

    private void loadHighscore() {
        File file = new File(HIGHSCORE_FILE);
        try (Scanner scanner = new Scanner(file)) {
            if(scanner.hasNextInt()) {
                highscore = scanner.nextInt();
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("High score file not found. Starting fresh.");
        } catch (Exception e) {
            e.printStackTrace();
            highscore = 0;
        }
    }

    private void generateNextMove () {
        int nextColor = random.nextInt(4);
        sequence.add(nextColor);
        userSequenceIndex = 0;
        score = sequence.size() - 1;
        flashSequence();
    }

}
