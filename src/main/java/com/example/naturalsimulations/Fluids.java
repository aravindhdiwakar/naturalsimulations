package com.example.naturalsimulations;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Fluids simulation scene
 */
public class Fluids {
    private Group fluids = new Group();
    private Scene scene;

    private Timeline timeline;
    private ArrayList<myEllipse> ellipseList = new ArrayList<myEllipse>(); //used to keep track of all myEllipse objects that will be simulated

    //all buttons for user interaction
    private ImageView plusButton;
    private ImageView resetButton;

    private Rectangle water;

    /**
     * Builds Fluids simulation scene object.
     * JavaFX Timeline is used to animate all objects and overall simulation.
     * Calls handleButtonInteraction() to display all buttons for user interaction.
     * @throws FileNotFoundException
     */
    public Fluids() throws FileNotFoundException {
        this.scene = new Scene(fluids, 1000, 800, Color.web("#b3dce8"));

        //creates a new JavaFX Rectangle object, which will act as water
        water = new Rectangle(0, 500, 1000, 300);
        water.setFill(Color.web("#66a0c4"));
        //adds water object to Group fluids
        fluids.getChildren().add(water);

        //initially displays single myEllipse object for demonstration
        myEllipse ellipse = new myEllipse(500, 100, 50, 50, 0, 1);
        ellipse.setFill();

        //add demonstration myEllipse object to ellipseList and Group fluids
        ellipseList.add(ellipse);
        fluids.getChildren().add(ellipse);

        handleButtonInteraction();

        timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            //moves all myEllipse objects in ellipseList
            for(myEllipse currentEllipse : ellipseList) {
                //apply force of gravity on all myEllipse objects in ellipseList
                //mass plays a role in how the force of gravity is exerted on the myEllipse object
                Vector gravity = new Vector(0, 0.1 * currentEllipse.getMass());
                currentEllipse.applyForce(gravity);
                //reduce the mass of each myEllipse object for smoother simulation
                //an extremely large mass would affect gravity exerted on an myEllipse object, which would affect its acceleration
                currentEllipse.setMass(Math.sqrt(currentEllipse.getRadiusX()));

                move(currentEllipse);
            }
        }));

        //creates a return button
        myButton.createReturnButton(fluids);
    }

    /**
     * Handles user-button interactions.
     * Plus button adds myEllipse objects.
     * Reset button clears all myEllipse objects.
     * @throws FileNotFoundException
     */
    public void handleButtonInteraction() throws FileNotFoundException {
        plusButton = new ImageView(new Image(new FileInputStream("D:/IntelliJ/Projects/Final Semester Project/src/resources/buttons/plusbutton.png")));
        plusButton.setX(25);
        plusButton.setY(25);
        plusButton.setFitHeight(75);
        plusButton.setFitWidth(75);
        fluids.getChildren().add(plusButton);

        resetButton = new ImageView(new Image(new FileInputStream("D:/IntelliJ/Projects/Final Semester Project/src/resources/buttons/resetbutton.png")));
        resetButton.setX(125);
        resetButton.setY(22);
        resetButton.setFitHeight(84);
        resetButton.setFitWidth(84);
        fluids.getChildren().add(resetButton);

        scene.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getX() > (plusButton.getX() + 5) && mouseEvent.getX() < (plusButton.getX() + plusButton.getFitWidth() - 5) && mouseEvent.getY() > (plusButton.getY() + 5) && mouseEvent.getY() < (plusButton.getY() + plusButton.getFitHeight() - 5)) { //plus function
                //creates a random myEllipse object
                Random rand = new Random();
                int x = rand.nextInt(50, 800);
                int size = rand.nextInt(10, 50);

                myEllipse ellipse = new myEllipse(x, 100, size, size, 0, 1);
                ellipse.setFill();

                //adds random myEllipse object to ellipseList and as a child node of Group fluids
                ellipseList.add(ellipse);
                fluids.getChildren().add(ellipse);
            }

            if(mouseEvent.getX() > (resetButton.getX() + 5) && mouseEvent.getX() < (resetButton.getX() + resetButton.getFitWidth() - 5) && mouseEvent.getY() > (resetButton.getY() + 5) && mouseEvent.getY() < (resetButton.getY() + resetButton.getFitHeight() - 5)) { //reset function
                //removes the water object from Group fluids
                fluids.getChildren().remove(water);
                //removes the last myEllipse object added to ellipseList from ellipseList and Group fluids
                for(myEllipse currentEllipse : ellipseList) {
                    fluids.getChildren().remove(currentEllipse);
                }
                //clears ellipseList
                ellipseList.clear();
                //adds the water object back to Group fluids
                fluids.getChildren().add(water);
                //creates a return button
                myButton.createReturnButton(fluids);
            }
        });
    }

    /**
     * Handles myEllipse object motion and interaction.
     * Calls updatePosition() for myEllipse object.
     * Calls calculateDragForce() and applyForce() when myEllipse object enters water.
     * @param ellipse
     */
    public void move(myEllipse ellipse) {
        ellipse.updatePosition();

        if(ellipse.getCenterY() > 500) {
            Vector dragForce = ellipse.calculateDragForce(); //declares and initializes new Vector dragForce with value returned by calculateDragForce().
            ellipse.applyForce(dragForce); //applies dragForce to myEllipse object (this is what slows the myEllipse object as it travels through water).
        }
    }

    /**
     * Infinitely loops timeline (continuous animation).
     * Returns Fluids simulation scene.
     * @return
     */
    public Scene returnScene() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        return scene;
    }
}
