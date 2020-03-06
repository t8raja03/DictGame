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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;  // Arc, Circle, etc.
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.* ; // Point2D, etc.
import javafx.stage.Stage;

public class DictGame extends Application
{
    static final int SCENE_WIDTH = 600;
    static final int SCENE_HEIGHT = 800;

    double SPEED = 0.5;

    AnimationTimer animationTimer;

    public void start(Stage stage) {
        
        Group ui_group = new Group();
        
        stage.setTitle("DictGame.java");

        Scene scene = new Scene (ui_group, SCENE_WIDTH, SCENE_HEIGHT);

        scene.setFill(Color.LIGHTGREEN);

        Rectangle selection_area = new Rectangle(10, 710, SCENE_WIDTH-20, 80);
        selection_area.setFill(Color.ANTIQUEWHITE);

        Circle word_Circle = new Circle(60, 60, 30, Color.WHITE);
        Text word_Text = new Text("Testipitkästilitaniaa");
        word_Text.setFont(Font.font(16));
        word_Circle.setRadius(word_Text.getLayoutBounds().getWidth() / 2 + 10);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(word_Circle, word_Text);

        ui_group.getChildren().addAll(selection_area, stack);

        stage.setScene(scene);
        stage.show();

        animationTimer = new AnimationTimer(){
        
            @Override
            public void handle(long now) {
                stack.setLayoutY(stack.getLayoutY() + SPEED);
            }
        };

        animationTimer.start();
    }

    public static void main(String[] command_line_parameters) {

        launch(command_line_parameters);

    }
}