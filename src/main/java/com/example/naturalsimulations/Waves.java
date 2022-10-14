package com.example.naturalsimulations;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Waves simulation scene
 */
public class Waves {
    private Group waves = new Group();
    private Scene scene;

    private double initialAngle = 0;
    private double angleVelocity = 0.25;
    private double amplitude = 100;

    private Timeline timeline;

    //user interaction objects
    private Rectangle slider;
    private Rectangle handle;

    private ArrayList<myEllipse> ellipseList = new ArrayList<myEllipse>(); //used to keep track of all myRectangle objects that will be simulated
    /**
     * Builds Waves simulation scene object.
     * JavaFX Timeline is used to animate all objects and overall simulation.
     * Calls handleSliderInteraction() to enable user interaction.
     */
    public Waves() {
        scene = new Scene(waves, 1000, 800, Color.web("#f5e2a4"));

        //creates and draws myEllipse objects across the scene
        for(double x = 0; x <= scene.getWidth(); x += 25) {
            myEllipse ellipse = new myEllipse(x, 0, 25, 25);
            ellipse.setStroke(Color.BLACK);
            ellipse.setStrokeWidth(5);
            ellipse.setFill(Color.web("e08b6e"));

            //adds myEllipse object to ellipseList and Group waves
            ellipseList.add(ellipse);
            waves.getChildren().add(ellipse);
        }

        handleSliderInteraction();

        timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> move()));

        //creates a return button
        myButton.createReturnButton(waves);
    }

    /**
     * Handles the wave motion of all myEllipse objects.
     */
    public void move() {
        //initialAngle is essentially the "start" of each new wave
        initialAngle += 0.025; //slowly increments initialAngle for smoother simulation
        double angle = initialAngle; //sets angle to initialAngle

        for(myEllipse ellipse : ellipseList) {
            //calculates y-value in terms of amplitude and sine of angle
            //larger angle = larger y-value, which means the respective myEllipse object will be higher up
            double y = amplitude * Math.sin(angle);
            //ensures all myEllipse objects remain in the middle of the scene relative to one another
            ellipse.setCenterY((y + scene.getHeight())/2);

            //adds angleVelocity to angle
            //updated angle value will correspond to the next myEllipse object
            angle += angleVelocity;
        }
    }

    /**
     * Handles handle-slider-user interaction.
     * Increases and decreases angle velocity of the wave.
     * @throws FileNotFoundException
     */
    public void handleSliderInteraction() {
        //creates a new Rectangle object, which will act as the "slider"
        slider = new Rectangle(100, 75, 800, 10);
        slider.setFill(Color.web("#e3b96b"));
        slider.setStrokeWidth(5);
        slider.setStroke(Color.BLACK);
        //adds slider object as a child node to Group waves
        waves.getChildren().add(slider);

        //creates a new Rectangle object, which will act as the "handle" (used to drag across the slider)
        handle = new Rectangle(100, 30, 50, 100);
        handle.setFill(Color.web("#64bad1"));
        handle.setStrokeWidth(5);
        handle.setStroke(Color.BLACK);
        //adds handle object as a child node to Group waves
        waves.getChildren().add(handle);

        //if user hovers over the handle, fill a darker shade of gray
        handle.setOnMouseEntered(mouseEvent -> handle.setFill(Color.web("#599fb3")));
        //else, return to default color
        handle.setOnMouseExited(mouseEvent -> handle.setFill(Color.web("#64bad1")));

        handle.setOnMouseDragged(mouseEvent -> {
            //prevents user from dragging the handle object outside of slider boundaries
            handle.setX(Vector.constrain(mouseEvent.getX(), 100, 850));
            //smoothly sets angleVelocity proportional to the x-coordinate of the handle object
            angleVelocity = (Math.sqrt(handle.getX()) / 40);

            //resets the handle object to default position if dragged outside of slider boundaries
            if(handle.getX() < 100) {
                handle.setX(100);
            } else if(handle.getX() > 850) {
                handle.setX(800);
            }
        });
    }

    /**
     * Infinitely loops timeline (continuous animation).
     * Returns Waves simulation scene.
     * @return
     */
    public Scene returnScene() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        return scene;
    }
}
