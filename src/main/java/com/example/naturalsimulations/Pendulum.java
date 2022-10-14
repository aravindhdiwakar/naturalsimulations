package com.example.naturalsimulations;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Pendulum simulation scene
 */
public class Pendulum {
    private Group pendulum = new Group();
    private Scene scene;

    private Timeline timeline;

    myBob pendulumBob;

    //user interaction objects
    private Rectangle slider;
    private Rectangle handle;

    private ImageView resetButton;

    /**
     * Builds Pendulum simulation scene object.
     * JavaFX Timeline is used to animate all objects and overall simulation.
     * Calls handleSliderInteraction() and handleButtonInteraction() to enable user interaction.
     */
    public Pendulum() throws FileNotFoundException {
        scene = new Scene(pendulum, 1000, 800, Color.web("#d1b2d4"));

        //initializes a new myBob object, which consists of the pendulum bob and the string
        //the pendulum bob will start at PI/4 radians, which is 45 degrees
        pendulumBob = new myBob(150, Math.PI/4);
        //displays myBob object
        pendulumBob.draw(pendulum);

        handleSliderInteraction();
        handleButtonInteraction();

        //calls swing() on pendulumBob, which sets it into motion
        timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> pendulumBob.swing()));

        //creates a return button
        myButton.createReturnButton(pendulum);
    }

    public void handleSliderInteraction() {
        //creates a new Rectangle object, which will act as the "slider"
        slider = new Rectangle(100, 600, 800, 10);
        slider.setFill(Color.web("#ed95bd"));
        slider.setStrokeWidth(5);
        slider.setStroke(Color.BLACK);
        //adds slider object as a child node to Group pendulum
        pendulum.getChildren().add(slider);

        //creates a new Rectangle object, which will act as the "handle" (used to drag across the slider)
        handle = new Rectangle(100, 555, 50, 100);
        handle.setFill(Color.web("#64bad1"));
        handle.setStrokeWidth(5);
        handle.setStroke(Color.BLACK);
        //adds handle object as a child node to Group waves
        pendulum.getChildren().add(handle);

        //if user hovers over the handle, fill a darker shade of gray
        handle.setOnMouseEntered(mouseEvent -> handle.setFill(Color.web("#599fb3")));
        //else, return to default color
        handle.setOnMouseExited(mouseEvent -> handle.setFill(Color.web("#64bad1")));

        handle.setOnMouseDragged(mouseEvent -> {
            //prevents user from dragging the handle object outside of slider boundaries
            handle.setX(Vector.constrain(mouseEvent.getX(), 100, 850));
            //smoothly sets stringLength proportional to the x-coordinate of the handle object
            pendulumBob.setStringLength(Math.sqrt(handle.getX()) * 15);

            //resets the handle object to default position if dragged outside of slider boundaries
            if(handle.getX() < 100) {
                handle.setX(100);
            } else if(handle.getX() > 850) {
                handle.setX(800);
            }
        });
    }

    /**
     * Handles user-button interaction.
     * Reset button clears all myRectangle objects.
     * @throws FileNotFoundException
     */
    public void handleButtonInteraction() throws FileNotFoundException {
        resetButton = new ImageView(new Image(new FileInputStream("D:/IntelliJ/Projects/Final Semester Project/src/resources/buttons/resetbutton.png")));
        resetButton.setX(20);
        resetButton.setY(697);
        resetButton.setFitHeight(84);
        resetButton.setFitWidth(84);
        pendulum.getChildren().add(resetButton);

        scene.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getX() > 25 && mouseEvent.getX() < 104 && mouseEvent.getY() > 702 && mouseEvent.getY() < 776) { //reset function
                //erases myBob object from scene
                pendulumBob.erase(pendulum);
                //initializes new myBob object at PI/4 radians
                pendulumBob = new myBob(150, Math.PI/4);
                //displays myBob object
                pendulumBob.draw(pendulum);

                //resets handle x-coordinate to 100
                handle.setX(handle.getX() - 1);
            }
        });
    }

    /**
     * Infinitely loops timeline (continuous animation).
     * Returns Pendulum simulation scene.
     * @return
     */
    public Scene returnScene() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        return scene;
    }
}
