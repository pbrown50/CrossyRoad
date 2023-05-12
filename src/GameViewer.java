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
    private Game game;
    private int WINDOW_HEIGHT;
    private int WINDOW_WIDTH;
    private static final int WELCOME_SCREEN = 37;
    private static final int PLAYING = 12;
    private static final int GAME_OVER = 56;
    public static int state;
    private int count;
    public GameViewer(Game g, int height, int width) {
        this.game = g;
        WINDOW_HEIGHT = height;
        WINDOW_WIDTH = width;
        state = WELCOME_SCREEN;
        count = 0;
        this.setTitle("CROSSY ROAD!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        createBufferStrategy(2);
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

        //Tell the System to do the Drawing now, otherwise it can take a few extra ms until
        //Drawing is done which looks very jerky
        Toolkit.getDefaultToolkit().sync();
    }
    public void myPaint(Graphics g) {
        if (state == WELCOME_SCREEN) {
            Image image = new ImageIcon("Resources/WELCOME_SCREEN.png").getImage();
            g.drawImage(image, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            g.setColor(Color.WHITE);
            g.drawString("BY PARKER BROWN", 250, 50);
            g.drawString("Press 'SPACE' to Play", 150, 520);
            g.drawString("Instructions: Use the 'UP'/'RIGHT'/'LEFT' keys to", 80, 560);
            g.drawString("cross the roads without being hit by a car", 80, 580);
            if (count == 0) {
                playSound("Resources/START.wav");
                count++;
            }
        }
        else if (state == PLAYING) {
            drawBackground(g);
            game.getBird().draw(g, this);
            drawPoints(g);
            drawCars(g);
        }
        else if (state == GAME_OVER) {
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", 160, 250);
            g.drawString("Press 'R' to restart", 140, 270);
        }
    }
    public void drawPoints (Graphics g) {
        g.setFont(new Font("BOLD", Font.BOLD, 30));
        g.drawString(Integer.toString(Game.points), 185, 80);
        g.setFont(new Font("BOLD", Font.BOLD, 20));
        g.drawString("High Score: " + Game.highScore, 245, 50);
    }
    public void drawBackground (Graphics g) {
        Image a = new ImageIcon("Resources/background.png").getImage();
        g.drawImage(a, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
    }
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
        int keyCode = e.getKeyCode();
        Bird bird = game.getBird();
        if (keyCode == KeyEvent.VK_SPACE) {
            state = PLAYING;
        }
        else if (keyCode == KeyEvent.VK_UP) {
            bird.setDy(bird.getY() - 90);
            bird.move();
            game.addPoints();
        }
        else if (keyCode == KeyEvent.VK_RIGHT) {
            bird.setDx(bird.getX() + 70);
            bird.move();
        }
        else if (keyCode == KeyEvent.VK_LEFT) {
            bird.setDx(bird.getX() - 70);
            bird.move();
        }
        else if (state == GAME_OVER) {
            if (keyCode == KeyEvent.VK_R) {
                state = PLAYING;
                bird.reset();
                for (Car car: Game.cars) {
                    car.resetSpeed();
                }
            }
        }
        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
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
