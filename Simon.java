import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        
        
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        
        // Color Buttons
        Image blueBtnImage = new Image("images/blue_button.png");
        ImageView blueBtnImageView = new ImageView(blueBtnImage);
        
        Image redBtnImage = new Image("images/red_button.png");
        ImageView redBtnImageView = new ImageView(redBtnImage);
        
        Image yellowBtnImage = new Image("images/yellow_button.png");
        ImageView yellowBtnImageView = new ImageView(yellowBtnImage);
        
        Image greenBtnImage = new Image("images/green_button.png");
        ImageView greenBtnImageView = new ImageView(greenBtnImage);
        
        redBtn = new Button();
        redBtn.setGraphic(redBtnImageView);
        
        blueBtn = new Button();
        blueBtn.setGraphic(blueBtnImageView);
        
        greenBtn = new Button();
        greenBtn.setGraphic(greenBtnImageView);
        
        yellowBtn = new Button();
        yellowBtn.setGraphic(yellowBtnImageView); 

        int btnSize = 200;
        greenBtnImageView.setFitWidth(btnSize);
        greenBtnImageView.setFitHeight(btnSize);
        yellowBtnImageView.setFitWidth(btnSize);
        yellowBtnImageView.setFitHeight(btnSize);
        redBtnImageView.setFitWidth(btnSize);
        redBtnImageView.setFitHeight(btnSize);
        blueBtnImageView.setFitWidth(btnSize);
        blueBtnImageView.setFitHeight(btnSize);
        
        redBtn.setOnAction(e -> {
            checkUserInput(RED);
        });
        blueBtn.setOnAction(e -> {
            checkUserInput(BLUE);
        });
        greenBtn.setOnAction(e ->
            {checkUserInput(GREEN);
        });
        yellowBtn.setOnAction(e -> {
            checkUserInput(YELLOW);
        });

        disableColorButtons(true);

        promptLabel = new Label();
        promptLabel.setText("Simon Highscore: " + highscore);
        
        startBtn = new Button("Start Game");
        startBtn.setOnAction(e -> {
            sequence.clear();
            score = 0;
            promptLabel.setText("Watch closely, you gotta remember");
            startBtn.setDisable(true);
            generateNextMove();
        });
        
        // The Grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        
        grid.add(redBtn, 0, 0);
        grid.add(blueBtn, 1, 0);
        grid.add(greenBtn, 0, 1);
        grid.add(yellowBtn, 1, 1);
        
        contentBox.getChildren().addAll(startBtn, promptLabel, grid);
        
        // Window
        Scene scene = new Scene(contentBox, 400, 450);
        stage.setScene(scene);
        stage.setTitle("Simon Game");
        stage.show();
    }
    private void generateNextMove () {
        int nextColor = random.nextInt(4);
        sequence.add(nextColor);
        userSequenceIndex = 0;
        score = sequence.size() - 1;
        flashSequence();
    }
    
    private void checkUserInput (int colorCode) {
        if (colorCode == sequence.get(userSequenceIndex)) {
            userSequenceIndex++;
            if (userSequenceIndex == sequence.size()) {
                score=sequence.size();
                promptLabel.setText("Correct! Score: " + score + ". Watch the next move");
                Timeline pause = new Timeline(new KeyFrame(
                    Duration.seconds(1.0),
                    e -> generateNextMove()
                ));
                pause.play();
            }
        }
        else {
            disableColorButtons(true);
            promptLabel.setText("Game over! Final score: " + score + " Press start to play agian");
            saveHighScore();
            startBtn.setDisable(false);
        }
    }

    private void flashSequence(){
        disableColorButtons(true);
        Timeline timeline = new Timeline();
        double flashDuration = 400;
        double stepDuration = 700;

        for (int i = 0; i < sequence.size(); i++){
            final int colorCode = sequence.get(i);
            final int step = i + 1;

            KeyFrame turnOn = new KeyFrame (
                Duration.millis(i * stepDuration),
                e -> {
                    flashButton(colorCode, true);
                    promptLabel.setText("Sequence step " + step + " of " + sequence.size());
                }
            );

            KeyFrame turnOff = new KeyFrame(
                Duration.millis(i * stepDuration+ flashDuration),
                e -> flashButton(colorCode, false)
            );

            timeline.getKeyFrames().addAll(turnOn, turnOff);
        }

        double totalDuration = sequence.size() * stepDuration;
        KeyFrame enableInput = new KeyFrame(
            Duration.millis(totalDuration),
            e-> {
                disableColorButtons(false);
                promptLabel.setText("Your Turn! Repeat the sequence");
            }
        );
        timeline.getKeyFrames().add(enableInput);

        timeline.play();
    }

    private void disableColorButtons (boolean disabled) {
        redBtn.setDisable(disabled);
        blueBtn.setDisable(disabled);
        greenBtn.setDisable(disabled);
        yellowBtn.setDisable(disabled);
    }

    private void flashButton (int colorCode, boolean on) {
        Button btn = null;
        String colorStyle = "";
        switch(colorCode) {
            case RED:
                btn = redBtn;
                colorStyle = "-fx-background-color: red;";
                break;
            case BLUE:
                btn = blueBtn;
                colorStyle = "-fx-background-color: blue;";
                break;
            case GREEN:
                btn = greenBtn;
                colorStyle = "-fx-background-color: green;";
                break;
            case YELLOW:
                btn = yellowBtn;
                colorStyle = "-fx-background-color: yellow;";
                break;
        }
        if (btn != null) {
            if (on) {
                btn.setStyle(colorStyle);
            } else {
                btn.setStyle(null);
            }
        }
    }

    // "-fx-effect: dropshadow (three-pass-box, white, 30, 0.8, 0, 0);"

    private void saveHighScore(){
        if (score > highscore) {
            highscore = score;
            File file = new File(HIGHSCORE_FILE);
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(highscore);
                System.out.println("New High Score saved: " + highscore);
            } catch (java.io.IOException ioe) {
                System.out.println("Could not save high score to file" + ioe.getMessage());
            }
        }
    }
    

    private void loadHighScore() {
        File file = new File(HIGHSCORE_FILE);
        try (Scanner scanner = new Scanner(file)) {
            if(scanner.hasNextInt()) {
                highscore = scanner.nextInt();
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("High score file not found. Starting fresh.");
            highscore = 0;
        } catch (Exception e) {
            e.printStackTrace();
            highscore = 0;
        }
    }


}
