package com.example.naturalsimulations;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.Random;

/**
 * Important component in this program, handles all interactions and motion of the circular objects simulated.
 */
public class myEllipse extends Ellipse {
    private Vector position;
    private Vector velocity;
    private Vector acceleration;

    private double mass;

    private double dragForceCoefficient = 0.3;
    private double gForceMag = 1;

    /**
     * Calls the JavaFX Ellipse superclass to create a new myEllipse at double x and double y, and of double width and double height.
     * Initializes Vector position of the myEllipse object's x- and y-coordinates.
     * Initializes Vector velocity of double deltaX and double deltaY. This will be how far the myEllipse object moves in the x- and y-direction per frame.
     * Initializes Vector acceleration of 0 and 0. This will be how fast the myEllipse object will accelerate in the x- and y-direction per frame.
     * Initializes the "mass" of the myEllipse object proportional to its size.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param deltaX
     * @param deltaY
     */
    public myEllipse(double x, double y, double width, double height, double deltaX, double deltaY) {
        super(x, y, width, height);

        this.position = new Vector(x, y);
        this.velocity = new Vector(deltaX, deltaY);
        this.acceleration = new Vector(0, 0);
        this.mass = (width + height) / 2;
    }

    /**
     * Overloaded constructor: calls the JavaFX Ellipse superclass to create a default myEllipse object at double x and double y, and of double width and double height.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public myEllipse(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    /**
     * Handles how the myEllipse object moves across the scene.
     * Adds Vector acceleration to Vector velocity.
     * Adds Vector velocity to Vector position.
     * Sets the center of the myEllipse object to the respective x- and y- values of Vector position.
     * The myEllipse object moves and accelerates!
     * To avoid over-acceleration and program crashes, resets Vector acceleration to 0.
     */
    public void updatePosition() {
        velocity.add(acceleration);
        position.add(velocity);

        super.setCenterX(position.getX());
        super.setCenterY(position.getY());

        setAccelerationVector(0, 0);
    }

    /**
     * Checks if the myEllipse object crosses the edges of the screen.
     * If it does, simply inverts Vector velocity so the myEllipse object travels opposite of its path (bounces of the edges).
     * boolean restrict is used when the myEllipse object must bounce off the top of the window.
     * int boundY determines at what point the myEllipse object must bounce towards the bottom of the window.
     * @param restrict
     * @param boundY
     */
    public void checkEdges(boolean restrict, int boundY) {
        if(position.getX() + super.getRadiusX() > 1000) {
            position.forceSetX(1000 - super.getRadiusX());
            velocity.setX(-1);
        } else if(position.getX() - super.getRadiusX() < 0) {
            position.forceSetX(0 + super.getRadiusX());
            velocity.setX(-1);
        }

        if(position.getY() + super.getRadiusY() > boundY) {
            position.forceSetY(boundY - super.getRadiusY());
            velocity.setY(-1);
        } else if(position.getY() - super.getRadiusY() < 0 && restrict == true) {
            position.forceSetY(0 + super.getRadiusY());
            velocity.setY(-1);
        }
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
     * Newton's Second Law of Motion: Force = Mass x Acceleration
     * To apply a force on the myEllipse object, divide Vector force by mass and add it to Vector acceleration.
     * @param force
     */
    public void applyForce(Vector force) {
        force.divide(mass);
        acceleration.add(force);
    }

    /**
     * Used in the Fluids simulation.
     * Drag Force Formula: Drag Force = -1/2 x Density of Liquid * Velocity Squared x Frontal Area of Object x Coefficient of Drag x Velocity Normalized
     * Simplified Formula: Velocity Squared x Coefficient of Drag x Velocity Normalized x -1
     * Calculates and returns the force of drag exerted on the myEllipse object by the water.
     * @return
     */
    public Vector calculateDragForce() {
        double speed = velocity.magnitude();
        double dragMagnitude = dragForceCoefficient * Math.pow(speed, 2);

        Vector dragForce = new Vector(velocity.getX(), velocity.getY());
        dragForce.multiply(-1); //acceleration is now opposite to velocity, myEllipse object slows down

        dragForce.magnitude();
        dragForce.normalize();

        dragForce.multiply(dragMagnitude);

        return dragForce;
    }

    /**
     * Used in the Attraction simulation.
     * Gravitational Force/Mutual Attraction Formula: (Gravitational Constant x Mass 1 x Mass 2) / (Distance Squared)
     * Calculates and returns the force of attraction exerted on the myRectangle object by the myEllipse object.
     * @param attracted
     * @return
     */
    public Vector calculateAttraction(myRectangle attracted) {
        Vector force = Vector.subtract(this.position, attracted.getPosition());
        double distance = Vector.constrain(force.magnitude(), 5, 25); //distance between myEllipse object and myRectangle object
        force.magnitude();
        force.normalize();

        double strength = (this.gForceMag * this.mass * attracted.getMass()) / (Math.pow(distance, 2));

        force.multiply(strength);

        return force;
    }

    /*public Vector calculateWind(Vector mouse, myEllipse ellipse) {
        Vector wind = Vector.subtract(ellipse.getPosition(), mouse);
        double distance = Vector.constrain(wind.magnitude(), 5, 25);

        wind.magnitude();
        wind.normalize();

        double strength = (this.gForceMag * this.mass * ellipse.getMass()) / (Math.pow(distance, 2));

        wind.multiply(strength);

        return wind;
    }
     */

    /**
     * Randomly colors the myEllipse object.
     */
    public void setFill() {
        Random rand = new Random();
        int num = rand.nextInt(0xffffff + 1);
        super.setFill(Color.web(String.format("#%06x", num)));
    }

    /**
     * Sets the mass of the myEllipse object to double mass.
     * @param mass
     */
    public void setMass(double mass) {
        this.mass = mass;
    }

    /**
     * Returns the mass of the myEllipse object.
     * @return
     */
    public double getMass() {
        return this.mass;
    }
}
