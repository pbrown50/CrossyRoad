// Implements the Crossy Road game
// By: Parker Brown
// 05/12/2023
import javax.swing.*;
import java.awt.event.*;
public class Game implements ActionListener
{
    // Instance variables
    private GameViewer window;
    public static int points;
    public static int highScore;
    public static Bird bird;
    public static Car[] cars;
    private ActionListener e;
    public static Timer clock;
    public static int count;
    // Final variables
    public static final int WINDOW_HEIGHT = 800;
    public static final int WINDOW_WIDTH = 400;
    private static final int WELCOME_SCREEN = 37;
    private static final int PLAYING = 12;
    private static final int GAME_OVER = 56;
    private static final int DELAY_IN_MILLISEC = 20;
    // Game constructor
    public Game() {
        points = 0;
        window = new GameViewer(this, WINDOW_HEIGHT, WINDOW_WIDTH);
        bird = new Bird();
        clock = new Timer(DELAY_IN_MILLISEC, this);
        clock.start();
        cars = initializeCars();
        highScore = 0;
        count = 0;
    }
    // Starts game by calling constructor which starts timer and initiates action listener
    public static void main(String[] args) {
        Game g = new Game();
    }
    // Initializes the cars, creating a new car for each of the four possible locations
    public Car[] initializeCars() {
        cars = new Car[4];
        for (int i = 0; i < 4; i++) {
            Car a;
            if (i == 0) {
                a = new Car(WINDOW_WIDTH - 50, 95);
            } else if (i == 1) {
                a = new Car(WINDOW_WIDTH - 50, 280);
            } else if (i == 2) {
                a = new Car(WINDOW_WIDTH - 50, 375);
            } else {
                a = new Car(WINDOW_WIDTH - 50, 560);
            }
            cars[i] = a;
        }
        return cars;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // If the bird is not dead
        if (bird.isDead(cars)) {
            // Only plays on the first time - glitches otherwise
            if (count == 0) {
                GameViewer.playSound("Resources/Die.wav");
                count++;
            }
            // Changes state of game to over
            window.state = GAME_OVER;
            // If points for current game is higher than high score, high score is replaced
            if (points > highScore) {
                highScore = points;
            }
            // Points is reset back to 0
            setPoints(0);
        }
        // If the game state is playing
        if (window.state == PLAYING) {
            // Move method is called on every car
            for (Car car : cars) {
                car.move();
            }
        }
        // Repaints window
        window.repaint();
    }
    public Bird getBird () {
        return bird;
    }
    public void addPoints() {
        points++;
    }
    public void setPoints(int points) {
        this.points = points;
    }
}