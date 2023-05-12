import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Game implements ActionListener
{
    private GameViewer window;
    public static int points;
    public static int highScore;
    public static Bird bird;
    public static Car[] cars;
    private ActionListener e;
    public static Timer clock;
    public static final int WINDOW_HEIGHT = 800;
    public static final int WINDOW_WIDTH = 400;
    private static final int WELCOME_SCREEN = 37;
    private static final int PLAYING = 12;
    private static final int GAME_OVER = 56;
    private static final int DELAY_IN_MILLISEC = 20;
    public static int count;
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
    public static void main(String[] args) {
        Game g = new Game();
    }
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
        if (bird.isDead(cars)) {
            if (count == 0) {
                GameViewer.playSound("Resources/Die.wav");
                count++;
            }
            window.state = GAME_OVER;
            if (points > highScore) {
                highScore = points;
            }
            setPoints(0);
        }
        if (window.state == PLAYING) {
            for (Car car : cars) {
                car.move();
            }
        }
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