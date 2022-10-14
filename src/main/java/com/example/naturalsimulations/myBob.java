package com.example.naturalsimulations;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public class myBob extends Ellipse {
    private Vector origin = new Vector(500, 0); //String Vector
    private Vector position; //Pendulum Bob Vector

    private Line string;
    private double stringLength;

    private double angle;
    private double omega = 0; //angular velocity
    private double alpha = 0; //angular acceleration

    private double damping = 0.999;

    /**
     * Calls the JavaFX Ellipse superclass to create a new myBob object.
     * Basically creates the pendulum, which consists of the pendulum bob and the string.
     * Initializes Vector position of double stringLength * sin(angle) [horizontal distance from string origin] and double stringLength * cos(angle) [vertical distance from string origin].
     * Adds Vector origin (where the string starts) to Vector position.
     * @param stringLength
     * @param angle
     */
    public myBob(double stringLength, double angle) {
        super(0, 0, 50, 50);
        super.setStrokeWidth(5);
        super.setStroke(Color.BLACK);
        super.setFill(Color.web("ed93a7"));

        this.angle = angle;

        string = new Line(origin.getX(), origin.getY(), 0, 0);
        string.setStrokeWidth(5);
        string.setStroke(Color.BLACK);

        this.stringLength = stringLength;
        position = new Vector(stringLength * Math.sin(angle), stringLength * Math.cos(angle));
        position.add(origin);

        updatePosition();
    }

    /**
     * Draws the myBob object by adding its components (pendulum bob and string) as children nodes to Group group.
     * @param group
     */
    public void draw(Group group) {
        group.getChildren().add(string);
        group.getChildren().add(this);
    }

    /**
     * Erases the myBob object by removing its components (pendulum bob and string) from Group group.
     * Used in the reset button.
     * @param group
     */
    public void erase(Group group) {
        group.getChildren().remove(string);
        group.getChildren().remove(this);
    }

    /**
     * Handles how the myBob object moves in oscillatory motion.
     * Sets the center of the pendulum bob to the respective x- and y-values of Vector position.
     * Sets the end of the string to the respective x- and y-values of Vector position.
     */
    public void updatePosition() {
        super.setCenterX(position.getX());
        super.setCenterY(position.getY());

        string.setEndX(position.getX());
        string.setEndY(position.getY());
    }

    /**
     * Handles how the myBob object swings in oscillatory motion.
     * Angular Acceleration Formula: Angular Acceleration = -(Gravity Constant / String Length) * sin(angle)
     * Adds alpha (angular acceleration) to omega (angular velocity).
     * Multiplies omega by damping constant (decreases velocity with every swing).
     * Adds omega to angle.
     * Forcefully sets Vector position x-value to stringLength * sin(angle) [horizontal distance from string origin].
     * Forcefully sets Vector position y-value to stringLength * sin(angle) [vertical distance from string origin].
     * Adds Vector position to Vector origin.
     * myBob object now swings in oscillatory and simple harmonic motion!
     */
    public void swing() {
        double gravityConstant = 0.25;

        alpha = (-1 * gravityConstant / stringLength) * Math.sin(angle);
        omega += alpha;
        omega *= damping;
        angle += omega;

        position.forceSetX(stringLength * Math.sin(angle));
        position.forceSetY(stringLength * Math.cos(angle));
        position.add(origin);

        updatePosition();
    }

    /**
     * Sets length of string to double stringLength.
     * Used in slider-handle-user interaction.
     * @param stringLength
     */
    public void setStringLength(double stringLength) {
        this.stringLength = stringLength;
    }
}
