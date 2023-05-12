import javax.swing.*;
import java.awt.*;
public class Bird {
    private Image image;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private boolean isDead;
    private final int BIRD_WIDTH = 70;
    private final int BIRD_HEIGHT = 70;
    private final int INITIAL_X = 180;
    public static final int INITIAL_Y = 750;
    public Bird() {
        x = INITIAL_X;
        y = INITIAL_Y;
        dx = INITIAL_X;
        dy = INITIAL_Y;
        isDead = false;
        image = new ImageIcon("Resources/Bird.png").getImage();
    }
    public int getX () {
        return x;
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
    public boolean isDead(Car[] cars) {
        for (Car car : cars) {
            int cx = car.getX();
            int cy = car.getY();
            if (x < cx + car.getWidth() && x > cx && y < cy + car.getHeight() && y > cy) {
                return true;
            }
        }
            return false;
    }
    public void move() {
        if (dy < 0) {
            dy = INITIAL_Y;
            Game.points--;
            for (Car car: Game.cars) {
                car.setSpeed(0.1);
            }
        }
        if (dx < -BIRD_WIDTH / 2) {
            dx = Game.WINDOW_WIDTH - BIRD_WIDTH;
        }
        if (dx > Game.WINDOW_WIDTH - 10) {
            dx = 0;
        }
        x = dx;
        y = dy;
        if (GameViewer.state != 56) {
            GameViewer.playSound("Resources/MOVE.wav");
        }
    }
    public void draw (Graphics g, GameViewer game) {
        g.drawImage(image, x, y, BIRD_WIDTH, BIRD_HEIGHT, game);
    }
    public void reset () {
        x = INITIAL_X;
        y = INITIAL_Y;
        dx = INITIAL_X;
        dy = INITIAL_Y;
        Game.points = 0;
        Game.count = 0;
    }
}
