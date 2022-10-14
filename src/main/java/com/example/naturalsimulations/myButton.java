package com.example.naturalsimulations;

import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.stage.Stage;

import java.util.ArrayList;

public class myButton extends Rectangle {
    private String font = "file:src/resources/fonts/MantiSansDemo-ywLjq.ttf";
    private static String fill = "#dfe1e6";
    private int arc = 10;

    private Text text;
    private Scene scene;

    public static Scene home;
    public static Stage stage;

    public static ArrayList<myButton> buttons = new ArrayList<myButton>();

    /**
     * Calls the JavaFX Rectangle superclass to create a new myButton at int x and int y position, and of int width and int height.
     * Also colors the myButton object default gray and rounds the corners with radius arc.
     * Basically creates and displays a new myButton object.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public myButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        super.setFill(Color.web(fill));
        super.setArcHeight(arc);
        super.setArcWidth(arc);
    }

    /**
     * Creates a JavaFX Text object as String text inside the myButton object at textX and textY with a font size of int fontSize.
     * Adds Text as a child node to Group group.
     * Basically displays the text inside the myButton object.
     * @param text
     * @param textX
     * @param textY
     * @param fontSize
     * @param group
     */
    public void setText(String text, int textX, int textY, int fontSize, Group group) {
        Text label = new Text(textX, textY, text);
        label.setFont(Font.loadFont(font, fontSize));
        group.getChildren().add(label);
        this.text = label;

        buttons.add(this);
    }

    /**
     * Returns the JavaFX Text object.
     * @return
     */
    public Text getText() {
        return text;
    }

    /**
     * Sets private Scene scene to parameter Scene scene.
     * @param scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Returns private Scene scene
     * @return
     */
    public Scene returnScene() {
        return scene;
    }

    /**
     * Creates a default myButton object that will be used in all simulation scenes, with the purpose of allowing the user to return to the home scene.
     * Adds return button as a child node to the group of the specific simulation scene.
     * Adds JavaFX Text object inside the button labeled "BACK".
     * Calls buttonFunction()
     * @param group
     */
    public static void createReturnButton(Group group) {
        myButton returnButton = new myButton(875, 725, 100, 50);
        group.getChildren().add(returnButton);

        returnButton.setText("BACK", 880, 762, 35, group);

        returnButton.setScene(home);

        buttonFunction();
    }

    /**
     * Allows the user to return to the home screen when the return button is clicked.
     * This is accomplished by setting the stage root to the home scene.
     * Becomes a darker shade of gray whenever the user hovers over the return button for additional functionality.
     * Returns to default color whenever user exits the return button.
     */
    public static void buttonFunction() {
        for(myButton button : buttons) {
            button.setOnMouseEntered(mouseEvent -> button.setFill(Color.web("#c3c6c9")));
            button.setOnMouseExited(mouseEvent -> button.setFill(Color.web(fill)));

            button.getText().setOnMouseEntered(mouseEvent -> button.setFill(Color.web("#c3c6c9")));
            button.getText().setOnMouseExited(mouseEvent -> button.setFill(Color.web(fill)));

            button.setOnMouseClicked(mouseEvent -> stage.setScene(button.returnScene()));
            button.getText().setOnMouseClicked(mouseEvent -> stage.setScene(button.returnScene()));
        }
    }
}