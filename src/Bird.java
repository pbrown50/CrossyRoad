import javax.swing.*;
import java.awt.*;

public class Bird {
    private Image image;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private boolean isDead;
    private final int BIRD_WIDTH = 300;
    private final int BIRD_HEIGHT = 150;
    private final int INITIAL_X = 75;
    private final int INITIAL_Y = 700;
    public Bird() {
        x = INITIAL_X;
        y = INITIAL_Y;
        dx = 0;
        dy = 0;
        isDead = false;
        image = new ImageIcon("Resources/Bird.png").getImage();
    }
    public void setX (int x) {
        this.x = x;
    }
    public int getX () {
        return x;
    }
    public void setY (int y) {
        this.y = y;
    }
    public int getY () {
        return y;
    }
    public void setDx(int dx) {
        this.dx = dx;
    }
    public void setDy(int dy) {
        this.dy = dy;
    }
    public void setDead(boolean b) {
        isDead = b;
    }
    public boolean isDead(Car[] cars) {
        for (int i = 0; i < BIRD_WIDTH; i++) {
            for (int j = 0; j < BIRD_HEIGHT; j++) {
                if (isTouching(i + 1, j + 1, cars)) {
                    isDead = true;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isTouching (int i, int j, Car[] cars) {
        for (Car car : cars) {
            if (i < car.getX() + car.getWidth() && i > car.getX()) {
                if (j > car.getY() - car.getHeight() && j < car.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void move() {
        x = dx;
        y = dy;
    }
    public void drawBird (Graphics g, GameViewer game) {
        g.drawImage(image, x, y, BIRD_WIDTH, BIRD_HEIGHT, game);
    }
    public void reset () {
        x = INITIAL_X;
        y = INITIAL_Y;
    }
}
