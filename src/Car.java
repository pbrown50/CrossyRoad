import javax.swing.*;
import java.awt.*;

public class Car {
    // Instance variables
    private Image image;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int width;
    private int height;
    private double speed;
    // Car constructor
    public Car(int x, int y) {
        this.x = x;
        this.y = y;
        dx = 5;
        dy = 0;
        height = 100;
        speed = 1.0;
        // Randomizes speed and image of car
        random();
    }
    // Moves car to new position
    public void move() {
        // Wraps car around if it has exited the window
        wrap();
        x = x - dx;
    }
    // Draw method for car
    public void draw (Graphics g, GameViewer game) {
        g.drawImage(image, x, y, width, height, game);
    }
    // Getter methods
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }
    // If car is off the screen, its variables are randomized, and it is moves to other side of the screen
    public void wrap() {
        if (x < - width) {
            random();
            x = 400;
        }
    }
    // Randomizes speed and image of car
    public void random() {
        // Speed is randomized using Math.random
        dx = (int) (Math.random() * 10.0 * speed) + 5;
        // Image is randomized
        int r = (int) (Math.random() * 3) + 1;
        image = new ImageIcon("Resources/Car" + r + ".png").getImage();
        // If the car is a truck
        if (r == 3) {
            // Width is adjusted for the size of a truck
            width = 200;
            // Speed is decreased since trucks travel slower than other cars
            dx = (int) (Math.random() * 10.0 * (speed - 0.5)) + 5;
        }
        // If the car is not a truck, the width is set to 150
        else {
            width = 150;
        }
    }
    // Adds speed every time bird wraps around to make game harder
    public void setSpeed(double speed) {
        this.speed += speed;
    }
    // Resets speed when game is reset
    public void resetSpeed() {
        speed = 1;
    }
}
