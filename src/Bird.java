import javax.swing.*;
import java.awt.*;
public class Bird {
    // Instance variables
    private Image image;
    private int x;
    private int y;
    private int dx;
    private int dy;
    // Final variables
    private final int BIRD_WIDTH = 70;
    private final int BIRD_HEIGHT = 70;
    private final int INITIAL_X = 180;
    public static final int INITIAL_Y = 750;
    // Bird constructor
    public Bird() {
        x = INITIAL_X;
        y = INITIAL_Y;
        dx = INITIAL_X;
        dy = INITIAL_Y;
        image = new ImageIcon("Resources/Bird.png").getImage();
    }
    // Getter and setter methods
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
    // Checks to see if bird is dead
    public boolean isDead(Car[] cars) {
        // Iterates through array of cars
        for (Car car : cars) {
            // Gets car x and y values
            int cx = car.getX();
            int cy = car.getY();
            // If the x and y values of the bird is within the x and y values of the car bird is dead
            if (x < cx + car.getWidth() && x > cx && y < cy + car.getHeight() && y > cy) {
                return true;
            }
        }
        // If the bird is not making contact with the cars the bird is not dead
            return false;
    }
    // Allows the bird to move
    public void move() {
        // If the y values is above the window
        if (dy < 0) {
            // Bird loops around to bottom of window
            dy = INITIAL_Y;
            Game.points--;
            // The speed of every car is increased
            for (Car car: Game.cars) {
                car.setSpeed(0.1);
            }
        }
        // If bird is out of the window to the left it is wrapped to the right
        if (dx < -BIRD_WIDTH / 2) {
            dx = Game.WINDOW_WIDTH - BIRD_WIDTH;
        }
        // If bird is out of the window to the right it is wrapped to the left
        if (dx > Game.WINDOW_WIDTH - 10) {
            dx = 0;
        }
        // x and y variables are changed to new position
        x = dx;
        y = dy;
        // If the game is over (56 == GAME_OVER) a sound is played
        if (GameViewer.state != 56) {
            GameViewer.playSound("Resources/MOVE.wav");
        }
    }
    // Draw method for bird
    public void draw (Graphics g, GameViewer game) {
        g.drawImage(image, x, y, BIRD_WIDTH, BIRD_HEIGHT, game);
    }
    // resets bird to initial position and returns points and count to 0
    public void reset () {
        x = INITIAL_X;
        y = INITIAL_Y;
        dx = INITIAL_X;
        dy = INITIAL_Y;
        Game.points = 0;
        Game.count = 0;
    }
}
