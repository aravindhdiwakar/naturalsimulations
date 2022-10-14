package com.example.naturalsimulations;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Attraction simulation scene
 */
public class Attraction {
    private Group attraction = new Group();
    private Scene scene;

    private Timeline timeline;
    private ArrayList<myRectangle> rectangleList = new ArrayList<myRectangle>(); //used to keep track of all myRectangle objects that will be simulated

    //all buttons for user interaction
    private ImageView plusButton;
    private ImageView minusButton;
    private ImageView resetButton;;

    /**
     * Builds Attraction simulation scene object.
     * JavaFX Timeline is used to animate all objects and overall simulation.
     * Calls handleButtonInteraction() to display all buttons for user interaction.
     * @throws FileNotFoundException
     */
    public Attraction() throws FileNotFoundException {
        scene = new Scene(attraction, 1000, 800, Color.web("#e38686"));

        //creates a new myEllipse object, which will act as the "attractor" for all myRectangle objects
        myEllipse attractor = new myEllipse(500, 400, 50, 50, 0, 0);
        attractor.setStroke(Color.BLACK);
        attractor.setStrokeWidth(5);
        attractor.setFill(Color.web("#ad71c7"));

        //adds attractor object as a child node to Group attraction
        attraction.getChildren().add(attractor);

        //creates a new myRectangle object for demonstration
        myRectangle rectangle = new myRectangle(200, 200, 50, 50, 1, 1);
        rectangle.setFill();
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(5);

        //adds demonstration myRectangle object to rectangleList and Group attraction
        rectangleList.add(rectangle);
        attraction.getChildren().add(rectangle);

        handleButtonInteraction();

        timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            //moves all myRectangle objects in rectangleList
            for(myRectangle currentRectangle : rectangleList) {
                //calculates the attraction force between myRectangle object and attractor object
                //applies this attraction force to myRectangle object
                currentRectangle.applyForce(attractor.calculateAttraction(currentRectangle));
                move(currentRectangle);
            }
        }));

        //creates a return button
        myButton.createReturnButton(attraction);
    }

    /**
     * Handles user-button interactions.
     * Plus button adds myRectangle objects.
     * Minus button removes myRectangle objects.
     * Reset button clears all myRectangle objects.
     * @throws FileNotFoundException
     */
    public void handleButtonInteraction() throws FileNotFoundException {
        plusButton = new ImageView(new Image(new FileInputStream("D:/IntelliJ/Projects/Final Semester Project/src/resources/buttons/plusbutton.png")));
        plusButton.setX(25);
        plusButton.setY(25);
        plusButton.setFitHeight(75);
        plusButton.setFitWidth(75);
        attraction.getChildren().add(plusButton);

        minusButton = new ImageView(new Image(new FileInputStream("D:/IntelliJ/Projects/Final Semester Project/src/resources/buttons/minusbutton.png")));
        minusButton.setX(125);
        minusButton.setY(25);
        minusButton.setFitHeight(75);
        minusButton.setFitWidth(75);
        attraction.getChildren().add(minusButton);

        resetButton = new ImageView(new Image(new FileInputStream("D:/IntelliJ/Projects/Final Semester Project/src/resources/buttons/resetbutton.png")));
        resetButton.setX(225);
        resetButton.setY(22);
        resetButton.setFitHeight(84);
        resetButton.setFitWidth(84);
        attraction.getChildren().add(resetButton);

        scene.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getX() > (plusButton.getX() + 5) && mouseEvent.getX() < (plusButton.getX() + plusButton.getFitWidth() - 5) && mouseEvent.getY() > (plusButton.getY() + 5) && mouseEvent.getY() < (plusButton.getY() + plusButton.getFitHeight() - 5)) { //plus function
                //creates a random myRectangle object
                Random rand = new Random();
                int x = rand.nextInt(100, 900);
                int y = rand.nextInt(100, 700);
                int size = rand.nextInt(10, 50);

                myRectangle rectangle = new myRectangle(x, y, size, size, 1, 1);
                //reduce the mass of random myRectangle object for smoother simulation
                rectangle.setMass(Math.sqrt(size));

                rectangle.setFill();
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(5);

                //adds random myRectangle object to rectangleList and as a child node of Group attraction
                rectangleList.add(rectangle);
                attraction.getChildren().add(rectangle);
            }

            if(mouseEvent.getX() > (minusButton.getX() + 5) && mouseEvent.getX() < (minusButton.getX() + minusButton.getFitWidth() - 5) && mouseEvent.getY() > (minusButton.getY() + 5) && mouseEvent.getY() < (minusButton.getY() + minusButton.getFitHeight() - 5)) { //minus function
                if(rectangleList.size() > 0) {
                    //removes the last myRectangle object added to rectangleList from rectangleList and Group attraction
                    attraction.getChildren().remove(rectangleList.get(rectangleList.size() - 1));
                    rectangleList.remove(rectangleList.size() - 1);
                }
            }

            if(mouseEvent.getX() > (resetButton.getX() + 5) && mouseEvent.getX() < (resetButton.getX() + resetButton.getFitWidth() - 5) && mouseEvent.getY() > (resetButton.getY() + 5) && mouseEvent.getY() < (resetButton.getY() + resetButton.getFitHeight() - 5)) { //reset function
                //removes all myRectangle object from Group attraction and clears ellipseList
                for(myRectangle rectangle : rectangleList) {
                    attraction.getChildren().remove(rectangle);
                }
                rectangleList.clear();
            }
        });
    }

    /**
     * Handles myRectangle object motion and interaction.
     * calls update() on myRectangle object.
     * @param rectangle
     */
    public void move(myRectangle rectangle) {
        rectangle.update();
    }

    /**
     * Infinitely loops timeline (continuous animation).
     * Returns Attraction simulation scene.
     * @return
     */
    public Scene returnScene() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        return scene;
    }
}
