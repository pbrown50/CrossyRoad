import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
public class Game implements ActionListener
{
    private GameViewer window;
    private static int points;
    private Bird bird;
    private Car[] cars;
    private static final int DELAY_IN_MILLISEC = 20;
    private ActionListener e;
    private Timer clock;
    public Game() {
        points = 0;
        window = new GameViewer(this);
        bird = new Bird();
        clock= new Timer(DELAY_IN_MILLISEC, this);
        clock.start();
        cars = startCars();
    }
    public static void main(String[] args) {
        Game g = new Game();
        g.playGame();
    }
    public void playGame() {
    }
    public Car[] startCars() {
        Image image = new ImageIcon("Resources/Car1.png").getImage();
        cars = new Car[4];
        Car a = new Car(image, window.WINDOW_WIDTH - 50, 50, false);
        cars[0] = a;
        Car b = new Car(image, window.WINDOW_WIDTH - 50, 200, false);
        cars[1] = b;
        Car c = new Car(image, window.WINDOW_WIDTH - 50, 300, false);
        cars[2] = c;
        Car d = new Car(image, window.WINDOW_WIDTH - 50, 500, false);
        cars[3] = d;
//        for (int i = 0; i < 4; i++) {
//            image = new ImageIcon("Resources/Car1.png").getImage();
//            a = new Car(image, 350, 0, false);
//            if (i == 0) {
//                a.setY(100);
//            }
//            else if (i == 1) {
//                a.setY(300);
//            }
//            else if (i == 2) {
//                a.setY(500);
//            }
//            else {
//                a.setY(700);
//            }
//            cars[i] = a;
//        }
        return cars;
    }
    public void drawPoints (Graphics g, GameViewer game) {
        g.setFont(new Font("BOLD", Font.BOLD, 30));
        g.drawString(Integer.toString(points), 185, 80);
    }
    public void drawBackground (Graphics g, GameViewer game) {
        Image a = new ImageIcon("Resources/background.png").getImage();
        g.drawImage(a, 0, 0, 400, 800, game);
    }
    public Bird getBird () {
        return bird;
    }
    public boolean gameWon () {
        if (bird.getY() < 200) {
            return true;
        }
        return false;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (window.state == GameViewer.PLAYING) {
            if (gameWon() || bird.isDead(cars)) {
                window.state = GameViewer.GAME_OVER;
                clock.stop();
            }
            for (Car car : cars) {
                car.move();
            }
            bird.move();
        }
        window.repaint();
    }
    public Car[] getCars() {
        return cars;
    }
    public GameViewer getWindow() {
        return window;
    }
    public void drawCars(Graphics g, GameViewer game) {
        for (Car car: cars) {
            Image a = car.getImage();
            g.drawImage(a, car.getX(), car.getY(), car.getWidth(), car.getHeight(), game);
        }
    }
    public static void addPoints() {
        points++;
    }
    public int getPoints() {
        return points;
    }
}