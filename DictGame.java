/*********************************
 * Rankinen Jarno TVT19KMO
 * Java-ohjelmointi, kevät 2020
 * DictGame
 * Harjoitustyö
 *********************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

class FallingWord extends StackPane
{

    int low_bound;

    Circle bg;

    Text text;

    public FallingWord(Circle circle, Text t, int lower_limit) {
        low_bound = lower_limit;
        bg = circle;
        text = t;

        newWord();
    }

    public int randomX() {
        return (int)(Math.random() * (600 - bg.getRadius()*2));
    }

    public void drop(double velocity) {

        setLayoutY(getLayoutY() + velocity);

        if (getLayoutY() >= low_bound) {
            bg.setFill(Color.RED);
        }

        if (getLayoutY() >= 800) {
            newWord();
        }
    }

    public void newWord() {
        setLayoutY(0);
        setLayoutX(randomX());
        bg.setFill(Color.WHITE);


    }
}

class Dictionary
{
    File dictionary = new File("./dictionary");

    int dictSize = 0;

    ArrayList<QWord> dictArray = new ArrayList<QWord>();

    public void load() {

        try{
            Scanner scanner = new Scanner(dictionary);
            while (scanner.hasNextLine()) {
                QWord newWord = new QWord(scanner.nextLine(), scanner.nextLine());
                dictArray.add(newWord);
                System.out.println(newWord.print());
            }
            scanner.close();

        } catch(FileNotFoundException e) {
            System.out.println("Virhe sanakirjaa luettaessa!");
            e.printStackTrace();
        }

    }
}

class QWord
{
    String q = "";
    String a = "";

    public QWord(String question, String answer) {
        q = question;
        a = answer;
    }

    public String print() {
        return q + " = " + a;
    }
}

public class DictGame extends Application
{
    static final int SCENE_WIDTH = 600;
    static final int SCENE_HEIGHT = 800;

    double SPEED = 1.0;

    AnimationTimer animationTimer;
    
    public void start(Stage stage) {
        
        Group ui_group = new Group();
        
        stage.setTitle("DictGame.java");

        Scene scene = new Scene (ui_group, SCENE_WIDTH, SCENE_HEIGHT);

        scene.setFill(Color.LIGHTGREEN);

        // Valinta-alueen tausta:

        Rectangle selection_area = new Rectangle(10, 650, SCENE_WIDTH-20, 80);
        selection_area.setFill(Color.ANTIQUEWHITE);

        Dictionary d = new Dictionary();
        d.load();
        
        // Putoavan sanan määrittely:
        Circle word_Circle = new Circle(60, 60, 30, Color.WHITE);
        Text word_Text = new Text("Testi");
        FallingWord word = new FallingWord(word_Circle, word_Text, (int)selection_area.getY());

        word_Text.setFont(Font.font(16));
        word_Circle.setRadius(word_Text.getLayoutBounds().getWidth() / 2 + 10);
        word.getChildren().addAll(word_Circle, word_Text);

        ui_group.getChildren().addAll(word, selection_area);

        stage.setScene(scene);
        stage.show();

        animationTimer = new AnimationTimer(){
        
            @Override
            public void handle(long now) {
                
                word.drop(SPEED);

            }
        };

        animationTimer.start();

        
    }



    public static void main(String[] command_line_parameters) {

        launch(command_line_parameters);

    }
}