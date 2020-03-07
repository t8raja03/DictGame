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

    int out_bound;

    Circle bg = new Circle();

    Text text = new Text();

    QWord word = new QWord();

    Dictionary dict;

    ChoiceButtons buttons;
    
    public FallingWord(Dictionary d, int lower_limit, int out_limit) {
        low_bound = lower_limit;
        out_bound = out_limit;
        dict = d;

        buttons = new ChoiceButtons(10, 650, 580, 80, dict);
        
        newWord();
        
        getChildren().addAll(bg, text);
    }

    public int randomX() {
        return (int)(Math.random() * (600 - (bg.getRadius()*4)) + (bg.getRadius()*2));
    }

    public void drop(double velocity) {

        setLayoutY(getLayoutY() + velocity);

        if (getLayoutY() >= low_bound) {
            bg.setFill(Color.RED);
        }

        if (getLayoutY() >= out_bound) {
            newWord();
        }
    }

    public void newWord() {
        setLayoutY(0);
        setLayoutX(randomX());
        bg.setFill(Color.WHITE);

        word = dict.getRandomWord();

        text.setText(word.getQ());

        text.setFont(Font.font(16));
        bg.setRadius(text.getLayoutBounds().getWidth() / 2 + 10);

        buttons.update();
    }

    public String getWrongAnswer() {
        return dict.getWrongAnswer();
    }

    public String getA() {
        return word.getA();
    }
}







class Dictionary
{
    File dictionary = new File("./dictionary");

    ArrayList<QWord> dictArray = new ArrayList<QWord>();

    int oldIndex;
    int newIndex;

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

    public QWord getRandomWord() {
        do {
            newIndex = (int)(Math.random() * dictArray.size());
        } while (newIndex == oldIndex);

        oldIndex = newIndex;

        return dictArray.get(newIndex);
    }

    public String getWrongAnswer() {

        int index;

        do {
            index = (int)(Math.random() * dictArray.size());
        } while (index == oldIndex);

        return dictArray.get(index).getA();
    }

    public String getCorrectAnswer() {
        return dictArray.get(oldIndex).getA();
    }
}





class QWord
{
    String q = "";
    String a = "";

    public QWord() {

    }

    public QWord(String question, String answer) {
        q = question;
        a = answer;
    }

    public String print() {
        return q + " = " + a;
    }

    public String getQ() {
        return q;
    }

    public String getA() {
        return a;
    }
}




class ChoiceButtons extends StackPane
{
    StackPane button1 = new StackPane();
    StackPane button2 = new StackPane();
    StackPane button3 = new StackPane();

    Rectangle bg1 = new Rectangle();
    Rectangle bg2 = new Rectangle();
    Rectangle bg3 = new Rectangle();

    static Text t1 = new Text();
    static Text t2 = new Text();
    static Text t3 = new Text();

    int correct_button;

    int padding = 10;

    Dictionary d;

    public ChoiceButtons(int x, int y, int w, int h, Dictionary dict) {
        
        d = dict;

        update();

        bg1.setWidth((w - (4 * padding)) / 3);
        bg1.setHeight(h - (2 * padding));
        bg1.setFill(Color.WHITE);
        bg1.setStroke(Color.BLACK);
        bg1.setArcHeight(15);
        bg1.setArcWidth(15);
        
        t1.setFont(Font.font(18));
        button1.getChildren().addAll(bg1, t1);
        button1.setLayoutX(x + padding);
        button1.setLayoutY(y + padding);

        bg2.setWidth((w - (4 * padding)) / 3);
        bg2.setHeight(h - (2 * padding));
        bg2.setFill(Color.WHITE);
        bg2.setStroke(Color.BLACK);
        bg2.setArcHeight(15);
        bg2.setArcWidth(15);

        t2.setFont(Font.font(18));
        button2.getChildren().addAll(bg2, t2);
        button2.setLayoutX(x + bg1.getWidth() + 2 * padding);
        button2.setLayoutY(y + padding);

        bg3.setWidth((w - (4 * padding)) / 3);
        bg3.setHeight(h - (2 * padding));
        bg3.setFill(Color.WHITE);
        bg3.setStroke(Color.BLACK);
        bg3.setArcHeight(15);
        bg3.setArcWidth(15);

        t3.setFont(Font.font(18));
        button3.getChildren().addAll(bg3, t3);
        button3.setLayoutX(x + bg1.getWidth() + bg2.getWidth() + 3 * padding);
        button3.setLayoutY(y + padding);

    }

    public void update() { 

        correct_button = (int)Math.random() * 3 + 1;

        if (correct_button == 1) {
            t1.setText(d.getCorrectAnswer());
            t2.setText(d.getWrongAnswer());
            t3.setText(d.getWrongAnswer());
        }
        else if (correct_button == 2) {
            t1.setText(d.getWrongAnswer());
            t2.setText(d.getCorrectAnswer());
            t3.setText(d.getWrongAnswer());
        }
        else if (correct_button == 3) {
            t1.setText(d.getWrongAnswer());
            t2.setText(d.getWrongAnswer());
            t3.setText(d.getCorrectAnswer());
        }
    }
    
}





public class DictGame extends Application
{
    static final int SCENE_WIDTH = 600;
    static final int SCENE_HEIGHT = 800;

    double SPEED = 1.0;

    int padding = 10;

    AnimationTimer animationTimer;

    Dictionary d = new Dictionary();

    FallingWord word;

    ChoiceButtons buttons;
    
    public void start(Stage stage) {
        
        Group ui_group = new Group();
        
        stage.setTitle("DictGame.java");

        Scene scene = new Scene (ui_group, SCENE_WIDTH, SCENE_HEIGHT);

        scene.setFill(Color.LIGHTGREEN);

        d.load();

        // Valinta-alueen tausta:

        Rectangle selection_bg = new Rectangle(10, 650, SCENE_WIDTH-20, 80);
        selection_bg.setFill(Color.ANTIQUEWHITE);
        selection_bg.setArcHeight(15);
        selection_bg.setArcWidth(15);

        // Valintanappien luonti
//        buttons = new ChoiceButtons(10, 650, SCENE_WIDTH-20, 80, word);

        // Luodaan putoava sana-olio
        word = new FallingWord(d, (int)selection_bg.getY(), SCENE_HEIGHT);

        // Putoava sana jää valinta-alueen taakse
        ui_group.getChildren().addAll(word, selection_bg, word.buttons.button1, word.buttons.button2, word.buttons.button3);

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