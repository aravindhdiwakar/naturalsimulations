package com.example.naturalsimulations;

import javafx.animation.*;

import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Forces simulation scene
 */
public class Forces {
    private Group forces = new Group();
    private Scene scene;

    private Timeline timeline;
    private ArrayList<myEllipse> ellipseList = new ArrayList<myEllipse>(); //used to keep track of all myEllipse objects that will be simulated

    //all buttons for user interaction
    private ImageView plusButton;
    private ImageView minusButton;
    private ImageView resetButton;

    /**
     * Builds Forces simulation scene object.
     * JavaFX Timeline is used to animate all objects and overall simulation.
     * Calls handleButtonInteraction() to display all buttons for user interaction.
     * @throws FileNotFoundException
     */
    public Forces() throws FileNotFoundException {
        this.scene = new Scene(forces, 1000, 800, Color.web("#ccd6d9"));

        //initially displays single myEllipse object for demonstration
        myEllipse ellipse = new myEllipse(200, 200, 50, 50, 1, 1);
        ellipse.setFill();

        //add demonstration myEllipse object to ellipseList and Group forces
        ellipseList.add(ellipse);
        forces.getChildren().add(ellipse);

        handleButtonInteraction();

        //New frame every 10 milliseconds
        timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            //moves all myEllipse objects in ellipseList
            for(myEllipse currentEllipse : ellipseList) {
                //apply force of gravity on all myEllipse objects in ellipseList
                //mass plays a role in how the force of gravity is exerted on the myEllipse object
                Vector gravity = new Vector(0, 0.1 * currentEllipse.getMass());
                currentEllipse.applyForce(gravity);

                //apply force of wind on all myEllipse objects in ellipseList
                Vector wind = new Vector(1, 0);
                currentEllipse.applyForce(wind);

                move(currentEllipse);
            }
        }));

        //creates a return button
        myButton.createReturnButton(forces);
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
        forces.getChildren().add(plusButton);

        minusButton = new ImageView(new Image(new FileInputStream("D:/IntelliJ/Projects/Final Semester Project/src/resources/buttons/minusbutton.png")));
        minusButton.setX(125);
        minusButton.setY(700);
        minusButton.setFitHeight(75);
        minusButton.setFitWidth(75);
        forces.getChildren().add(minusButton);

        resetButton = new ImageView(new Image(new FileInputStream("D:/IntelliJ/Projects/Final Semester Project/src/resources/buttons/resetbutton.png")));
        resetButton.setX(225);
        resetButton.setY(697);
        resetButton.setFitHeight(84);
        resetButton.setFitWidth(84);
        forces.getChildren().add(resetButton);

        scene.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getX() > 30 && mouseEvent.getX() < 95 && mouseEvent.getY() > 705 && mouseEvent.getY() < 770) { //plus function
                //creates a random myEllipse object
                Random rand = new Random();
                int x = rand.nextInt(10, 990);
                int y = rand.nextInt(10, 50);
                int deltaX = rand.nextInt(-5, 5);
                int size = rand.nextInt(10, 50);

                myEllipse ellipse = new myEllipse(x, y, size, size, deltaX, 1);
                ellipse.setFill();

                //adds random myEllipse object to ellipseList and as a child node of Group forces
                ellipseList.add(ellipse);
                forces.getChildren().add(ellipse);
            }

            if(mouseEvent.getX() > 130 && mouseEvent.getX() < 195 && mouseEvent.getY() > 705 && mouseEvent.getY() < 770) { //minus function
                if(ellipseList.size() > 0) {
                    //removes the last myEllipse object added to ellipseList from ellipseList and Group forces
                    forces.getChildren().remove(ellipseList.get(ellipseList.size() - 1));
                    ellipseList.remove(ellipseList.size() - 1);
                }
            }

            if(mouseEvent.getX() > 230 && mouseEvent.getX() < 304 && mouseEvent.getY() > 702 && mouseEvent.getY() < 776) { //reset function
                //removes all myEllipse object from Group forces and clears ellipseList
                for(myEllipse ellipse : ellipseList) {
                    forces.getChildren().remove(ellipse);
                }
                ellipseList.clear();
            }
        });
    }

    /**
     * Handles myEllipse object motion and interaction.
     * Calls updatePosition() and checkEdges() for myEllipse object.
     * @param ellipse
     */
    public void move(myEllipse ellipse) {
        ellipse.updatePosition();
        ellipse.checkEdges(false, 650); //myEllipse object is not bound to all edges of the scene (only bounces off left, right, and bottom)
    }

    /**
     * Infinitely loops timeline (continuous animation).
     * Returns Forces simulation scene.
     * @return
     */
    public Scene returnScene() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        return scene;
    }
}
