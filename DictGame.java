/*********************************
 * Rankinen Jarno TVT19KMO
 * Java-ohjelmointi, kevät 2020
 * DictGame
 * Harjoitustyö
 *********************************/

import javafx.animation.* ;  // AnimationTimer, etc.
import javafx.util.Duration;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;  // Arc, Circle, etc.
import javafx.geometry.* ; // Point2D, etc.
import javafx.stage.Stage;

class FallingWord extends Circle
{
    double speed = 0.5;

    String question = "Not defined";

    String correct_answer = "Ei määritetty";


    public FallingWord(String q, String a) {

        super(300, 0, 64);

        question = q;
        correct_answer = a;

    }

    public void move()
    {
        setCenterY(getCenterY() + speed);
    }
}


public class DictGame extends Application
{
    static final int SCENE_WIDTH = 600;
    static final int SCENE_HEIGHT = 800;

    AnimationTimer animationTimer;

    public void start(Stage stage) {
        
        Group ui_group = new Group();
        
        stage.setTitle("DictGame.java");

        Scene scene = new Scene (ui_group, SCENE_WIDTH, SCENE_HEIGHT);

        scene.setFill(Color.LIGHTGREEN);

        Rectangle selection_area = new Rectangle(10, 710, SCENE_WIDTH-20, 80);
        selection_area.setFill(Color.ANTIQUEWHITE);

        FallingWord new_word = new FallingWord("Test", "Testi");

        ui_group.getChildren().addAll(selection_area, new_word);

        stage.setScene(scene);
        stage.show();

        animationTimer = new AnimationTimer(){
        
            @Override
            public void handle(long now) {
                new_word.move();
            }
        };

        animationTimer.start();
    }

    public static void main(String[] command_line_parameters) {

        launch(command_line_parameters);

    }
}