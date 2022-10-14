package com.example.naturalsimulations;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Important component in this project, handles all interactions and motion of the rectangular objects simulated.
 */
public class myRectangle extends Rectangle {
    private Vector position;
    private Vector velocity;
    private Vector acceleration;

    private double angle;
    private double omega = 0; //angular velocity
    private double alpha = 0.01; //angular acceleration

    private double mass;

    /**
     * Calls the JavaFX Rectangle superclass to create a new myRectangle at double x and double y, and of double width and double height.
     * Initializes Vector position of the myRectangle object's x- and y-coordinates.
     * Initializes Vector velocity of double deltaX and double deltaY. This will be how far the myRectangle object moves in the x- and y-direction per frame.
     * Initializes Vector acceleration of 0 and 0. This will be how fast the myRectangle object will accelerate in the x- and y-direction per frame.
     * Initializes the "mass" of the myRectangle object proportional to its size.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param deltaX
     * @param deltaY
     */
    public myRectangle(double x, double y, double width, double height, double deltaX, double deltaY) {
        super(x, y, width, height);

        this.position = new Vector(x, y);
        this.velocity = new Vector(deltaX, deltaY);
        this.acceleration = new Vector(0, 0);
        this.mass = (width + height) / 2;
    }

    /**
     * Handles how the myRectangle object moves across the scene.
     * Includes both translational and rotational motion.
     * Adds Vector acceleration to Vector velocity.
     * Adds Vector velocity to Vector position.
     * Sets the myRectangle object's position to the respective x- and y-values of Vector position.
     * The myRectangle object moves and accelerates!
     * To avoid over-acceleration and program crashes, resets Vector acceleration to 0.
     * Adds alpha (angular acceleration) to omega (angular velocity).
     * Adds omega to angle.
     * Rotates myRectangle object according to angle.
     * The myRectangle object follows rotational kinematics!
     */
    public void update() {
        velocity.add(acceleration);
        position.add(velocity);

        super.setX(position.getX());
        super.setY(position.getY());

        omega += alpha;
        omega = Vector.constrain(omega, -3, 3);
        angle += omega;
        this.setRotate(angle);

        setAccelerationVector(0, 0);
    }

    /**
     * Newton's Second Law of Motion: Force = Mass x Acceleration
     * To apply a force on the myRectangle object, divide Vector force by mass and add it to Vector acceleration.
     * @param force
     */
    public void applyForce(Vector force) {
        force.divide(mass);
        acceleration.add(force);
    }

    /**
     * Forcefully sets the respective x- and y-values of Vector acceleration to double deltaX and double deltaY.
     * @param deltaX
     * @param deltaY
     */
    public void setAccelerationVector(double deltaX, double deltaY) {
        acceleration.forceSetX(deltaX);
        acceleration.forceSetY(deltaY);
    }

    /**
     * Randomly colors the myRectangle object
     */
    public void setFill() {
        Random rand = new Random();
        int num = rand.nextInt(0xffffff + 1);
        super.setFill(Color.web(String.format("#%06x", num)));
    }

    /**
     * Returns Vector position of myRectangle object.
     * @return
     */
    public Vector getPosition() {
        return this.position;
    }

    /**
     * Sets the mass of the myRectangle object to double mass.
     * @param mass
     */
    public void setMass(double mass) {
        this.mass = mass;
    }

    /**
     * Returns the mass of the myRectangle object.
     * @return
     */
    public double getMass() {
        return mass;
    }
}
