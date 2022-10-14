package com.example.naturalsimulations;

/**
 * Most important class in this whole program; basically the basis of all the physics behind everything
 */
public class Vector {
    private double x;
    private double y;
    private double magnitude;

    /**
     * Creates a new Vector object of double x and double y.
     * @param x
     * @param y
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds a Vector object with another Vector object by summing the respective x- and y-values individually.
     * @param vector
     */
    public void add(Vector vector) {
        this.x += vector.getX();
        this.y += vector.getY();
    }

    /**
     * Divides a Vector object with another Vector object by dividing the respective x- and y-values respectively.
     * @param div
     */
    public void divide(double div) {
        this.x /= div;
        this.y /= div;
    }

    /**
     * Multiplies a Vector object with another Vector object by multiplying the respective x- and y-values respectively.
     * @param mult
     */
    public void multiply(double mult) {
        this.x *= mult;
        this.y *= mult;
    }

    /**
     * Subtracts two Vector objects by subtracting the respective x- and y-values respectively.
     * Returns a new Vector object of these new x- and y-values.
     * @param vector1
     * @param vector2
     * @return
     */
    public static Vector subtract(Vector vector1, Vector vector2) {
        return new Vector(vector1.getX() - vector2.getX(), vector1.getY() - vector2.getY());
    }

    /**
     * Returns the magnitude of a Vector object by using the formula: magnitude = square root of x^2 + y^2.
     * @return
     */
    public double magnitude() {
        this.magnitude = Math.sqrt((Math.pow(this.x, 2)) + Math.pow(this.y, 2));
        return magnitude;
    }

    /**
     * Normalizes a Vector object by dividing its respective x- and y-values by the magnitude.
     */
    public void normalize() {
        this.x /= magnitude;
        this.y /= magnitude;
    }

    /**
     * Constrains a double num between double low and double high.
     * If num is lower than the low, return the low.
     * If num is higher than the high, return the high.
     * Else return num.
     * @param num
     * @param low
     * @param high
     * @return
     */
    public static double constrain(double num, double low, double high) {
        if(num < low) {
            return low;
        } else if(num > high) {
            return high;
        } else if(num > low && num < high) {
            return num;
        }

        return 0;
    }

    /**
     * Multiplies the x-value by double x.
     * @param x
     */
    public void setX(double x) {
        this.x *= x;
    }

    /**
     * Multiples the y-value by double y.
     * @param y
     */
    public void setY(double y) {
        this.y *= y;
    }

    /**
     * Forcefully sets the x-value to double x.
     * @param x
     */
    public void forceSetX(double x) {
        this.x = x;
    }

    /**
     * Forcefully sets the y-value to double y.
     * @param y
     */
    public void forceSetY(double y) {
        this.y = y;
    }

    /**
     * Returns the x-value of a Vector object.
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-value of a Vector object.
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the String representation of a Vector object.
     * @return
     */
    public String toString() {
        return "<" + this.x + ", " + this.y + ">";
    }
}