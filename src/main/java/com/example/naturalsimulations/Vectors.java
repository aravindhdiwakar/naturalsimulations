package com.example.naturalsimulations;

import javafx.animation.*;

import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import javafx.util.*;
import java.util.*;
import java.io.*;

/**
 * Vectors simulation scene
 */
public class Vectors {
    private Group vectors = new Group();
    private Scene scene;

    private Timeline timeline;
    private ArrayList<myEllipse> ellipseList = new ArrayList<myEllipse>(); //used to keep track of all myEllipse objects that will be simulated

    //all buttons for user interaction
    private ImageView plusButton;
    private ImageView minusButton;
    private ImageView resetButton;

    /**
     * Builds Vectors simulation scene object.
     * JavaFX Timeline is used to animate all objects and overall simulation.
     * Calls handleButtonInteraction() to display all buttons for user interaction.
     * @throws FileNotFoundException
     */
    public Vectors() throws FileNotFoundException {
        this.scene = new Scene(vectors, 1000, 800, Color.web("#ccd6d9"));

        //initially displays single myEllipse object for demonstration
        myEllipse ellipse = new myEllipse(10, 10, 20, 20, 1, 1);
        ellipse.setFill();

        //add demonstration myEllipse object to ellipseList and Group vectors
        ellipseList.add(ellipse);
        vectors.getChildren().add(ellipse);

        handleButtonInteraction();

        //New frame every 10 milliseconds
        timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            //moves all myEllipse objects in ellipseList
            for(myEllipse currentEllipse : ellipseList) {
                move(currentEllipse);
            }
        }));

        //creates a return button
        myButton.createReturnButton(vectors);
    }

    /**
     * Handles myEllipse object motion and interaction.
     * Calls updatePosition() and checkEdges() for myEllipse object.
     * @param ellipse
     */
    public void move(myEllipse ellipse) {
        ellipse.updatePosition();
        ellipse.checkEdges(true, 650); //myEllipse object is bound to all edges of the scene (bounces off all four edges)
    }

    /**
     * Handles user-button interactions.
     * Plus button adds myEllipse objects.
     * Minus button removes myEllipse objects.
     * Reset button clears all myEllipse objects.
     * @throws FileNotFoundException
     */
    public void handleButtonInteraction() throws FileNotFoundException {
        plusButton = new ImageView(new Image(new FileInputStream("D:/IntelliJ/Projects/Final Semester Project/src/resources/buttons/plusbutton.png")));
        plusButton.setX(25);
        plusButton.setY(700);
        plusButton.setFitHeight(75);
        plusButton.setFitWidth(75);
        vectors.getChildren().add(plusButton);

        minusButton = new ImageView(new Image(new FileInputStream("D:/IntelliJ/Projects/Final Semester Project/src/resources/buttons/minusbutton.png")));
        minusButton.setX(125);
        minusButton.setY(700);
        minusButton.setFitHeight(75);
        minusButton.setFitWidth(75);
        vectors.getChildren().add(minusButton);

        resetButton = new ImageView(new Image(new FileInputStream("D:/IntelliJ/Projects/Final Semester Project/src/resources/buttons/resetbutton.png")));
        resetButton.setX(225);
        resetButton.setY(697);
        resetButton.setFitHeight(84);
        resetButton.setFitWidth(84);
        vectors.getChildren().add(resetButton);

        scene.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getX() > 30 && mouseEvent.getX() < 95 && mouseEvent.getY() > 705 && mouseEvent.getY() < 770) { //plus function
                //creates a random myEllipse object
                Random rand = new Random();
                int x = rand.nextInt(10, 990);
                int y = rand.nextInt(10, 665);
                double deltaX = rand.nextDouble(-10, 10);
                double deltaY = rand.nextDouble(-10, 10);

                myEllipse ellipse = new myEllipse(x, y, 20, 20, deltaX, deltaY);
                ellipse.setFill();

                //adds random myEllipse object to ellipseList and as a child node of Group vectors
                ellipseList.add(ellipse);
                vectors.getChildren().add(ellipse);
            }

            if(mouseEvent.getX() > 130 && mouseEvent.getX() < 195 && mouseEvent.getY() > 705 && mouseEvent.getY() < 770) { //minus function
                if(ellipseList.size() > 0) {
                    //removes the last myEllipse object added to ellipseList from ellipseList and Group vectors
                    vectors.getChildren().remove(ellipseList.get(ellipseList.size() - 1));
                    ellipseList.remove(ellipseList.size() - 1);
                }
            }

            if(mouseEvent.getX() > 230 && mouseEvent.getX() < 304 && mouseEvent.getY() > 702 && mouseEvent.getY() < 776) { //reset function
                //removes all myEllipse object from Group vectors and clears ellipseList
                for(myEllipse ellipse : ellipseList) {
                    vectors.getChildren().remove(ellipse);
                }
                ellipseList.clear();
            }
        });
    }

    /**
     * Infinitely loops timeline (continuous animation).
     * Returns Vectors simulation scene.
     * @return
     */
    public Scene returnScene() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        return scene;
    }
}