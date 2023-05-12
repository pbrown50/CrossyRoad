import javax.swing.*;
import java.awt.*;

public class Car {
    private Image image;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int width;
    private int height;
    private double speed;
    public Car(int x, int y) {
        this.x = x;
        this.y = y;
        dx = 5;
        dy = 0;
        height = 100;
        speed = 1.0;
        random();
    }
    public void move() {
        wrap();
        x = x - dx;
    }
    public void draw (Graphics g, GameViewer game) {
        g.drawImage(image, x, y, width, height, game);
    }
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
    public void wrap() {
        if (x < - width) {
            random();
            x = 400;
        }
    }
    public void random() {
        dx = (int) (Math.random() * 10.0 * speed) + 5;
        int r = (int) (Math.random() * 3) + 1;
        image = new ImageIcon("Resources/Car" + r + ".png").getImage();
        if (r == 3) {
            width = 200;
            dx = (int) (Math.random() * 10.0 * (speed - 0.5)) + 5;
        }
        else {
            width = 150;
        }
    }
    public void setSpeed(double speed) {
        this.speed += speed;
    }
    public void resetSpeed() {
        speed = 1;
    }
}
