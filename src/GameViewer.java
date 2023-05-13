import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;             // #1: Required for KeyListener
import java.awt.event.KeyListener;          // #2: Required for KeyListener
import java.io.File;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;

public class GameViewer extends JFrame implements KeyListener {
    // Instance variables
    private Game game;
    private int WINDOW_HEIGHT;
    private int WINDOW_WIDTH;
    public static int state;
    private int count;
    // Final variables
    private static final int WELCOME_SCREEN = 37;
    private static final int PLAYING = 12;
    private static final int GAME_OVER = 56;
    // GameViewer constructor
    public GameViewer(Game g, int height, int width) {
        this.game = g;
        WINDOW_HEIGHT = height;
        WINDOW_WIDTH = width;
        state = WELCOME_SCREEN;
        count = 0;
        // Creates window
        this.setTitle("CROSSY ROAD!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        createBufferStrategy(2);
        // Adds key listener to allow user interaction
        addKeyListener(this);
        repaint();
    }
    public void paint(Graphics g) {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            return;

        Graphics g2 = null;
        try
        {
            g2 = bf.getDrawGraphics();

            // myPaint does the actual drawing
            myPaint(g2);
        }
        finally
        {
            // It is best to dispose() a Graphics object when done with it.
            g2.dispose();
        }

        // Shows the contents of the backbuffer on the screen.
        bf.show();

        // Tell the System to do the Drawing now, otherwise it can take a few extra ms until
        // Drawing is done which looks very jerky
        Toolkit.getDefaultToolkit().sync();
    }
    public void myPaint(Graphics g) {
        // If game state is welcome screen
        if (state == WELCOME_SCREEN) {
            // Welcome screen is printed
            Image image = new ImageIcon("Resources/WELCOME_SCREEN.png").getImage();
            g.drawImage(image, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            g.setColor(Color.WHITE);
            // Prints instructions
            g.drawString("BY PARKER BROWN", 250, 50);
            g.drawString("Press 'SPACE' to Play", 150, 520);
            g.drawString("Instructions: Use the 'UP'/'RIGHT'/'LEFT' keys to", 80, 560);
            g.drawString("cross the roads without being hit by a car", 80, 580);
            // Only plays on the first time - glitches otherwise
            if (count == 0) {
                playSound("Resources/START.wav");
                count++;
            }
        }
        // If state is playing
        else if (state == PLAYING) {
            // Draws background
            drawBackground(g);
            // Draws bird
            game.getBird().draw(g, this);
            // Draws points
            drawPoints(g);
            // Draws cars
            drawCars(g);
        }
        // If game is over
        else if (state == GAME_OVER) {
            // Instructions for restarting game is displayed
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", 160, 250);
            g.drawString("Press 'R' to restart", 140, 270);
        }
    }
    // Draws current points along with high score points
    public void drawPoints (Graphics g) {
        g.setFont(new Font("BOLD", Font.BOLD, 30));
        g.drawString(Integer.toString(Game.points), 185, 80);
        g.setFont(new Font("BOLD", Font.BOLD, 20));
        g.drawString("High Score: " + Game.highScore, 245, 50);
    }
    // Draws background
    public void drawBackground (Graphics g) {
        Image a = new ImageIcon("Resources/background.png").getImage();
        g.drawImage(a, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
    }
    // Traverses array of cars and draws each of them
    public void drawCars(Graphics g) {
        for (Car car: Game.cars) {
            car.draw(g, this);
        }
    }
    public void keyTyped(KeyEvent e)
    {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        // Figures out what key was pressed
        int keyCode = e.getKeyCode();
        Bird bird = game.getBird();
        // If space has been pressed, the game state is switched to playing
        if (keyCode == KeyEvent.VK_SPACE) {
            state = PLAYING;
        }
        // If the up key is pressed, the bird is moved in that direction and points are added
        else if (keyCode == KeyEvent.VK_UP) {
            bird.setDy(bird.getY() - 90);
            bird.move();
            game.addPoints();
        }
        // If the right key is pressed, the bird is moved in that direction
        else if (keyCode == KeyEvent.VK_RIGHT) {
            bird.setDx(bird.getX() + 70);
            bird.move();
        }
        // If the left key is pressed, the bird is moved in that direction
        else if (keyCode == KeyEvent.VK_LEFT) {
            bird.setDx(bird.getX() - 70);
            bird.move();
        }
        // If the game is over
        else if (state == GAME_OVER) {
            // If 'R' is pressed
            if (keyCode == KeyEvent.VK_R) {
                // State is returned to playing
                state = PLAYING;
                // Bird is reset to original position
                bird.reset();
                // Iterates through array of cars and resets their speeds
                for (Car car: Game.cars) {
                    car.resetSpeed();
                }
            }
        }
        // Window is repainted
        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    // Plays wav sound files - found online
    public static void playSound(String soundFile) {
        try {
            File file = new File(soundFile);
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;
            stream = AudioSystem.getAudioInputStream(file);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        }
        catch (Exception e) {
        }
    }
    // Closes program as needed
    public void setDefaultCloseOperation(int exitOnClose) {
    }
}
