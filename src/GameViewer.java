import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;             // #1: Required for KeyListener
import java.awt.event.KeyListener;          // #2: Required for KeyListener

import javax.swing.JFrame;
public class GameViewer extends JFrame implements KeyListener {
    private Game game;
    public final int WINDOW_HEIGHT = 1000;
    public final int WINDOW_WIDTH = 400;
    public static final int WELCOME_SCREEN = 37;
    public static final int PLAYING = 12;
    public static final int GAME_OVER = 56;
    public int state;
    public GameViewer(Game g) {
        this.game = g;
        this.setTitle("CROSSY ROAD!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        createBufferStrategy(2);
        state = WELCOME_SCREEN;
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
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

            g.setColor(Color.WHITE);
            g.drawString("CROSSY ROAD", 100, 300);
            g.drawString("Press S to Play", 100, 350);
            g.drawString("Instructions: Use the UP/RIGHT/LEFT keys to", 100, 400);
            g.drawString("cross the roads without being hit by a car", 100, 420);

        }
        else if (state == PLAYING) {
            clear(g);
            game.getBird().drawBird(g, this);
            game.drawPoints(g, this);
            game.drawCars(g, this);
        }
        else if (state == GAME_OVER) {
            g.setColor(Color.blue);
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

            g.setColor(Color.WHITE);
            g.drawString("  GAME OVER   \nPress R to restart", 100, 300);
        }
    }
    public void clear (Graphics g) {
        game.drawBackground(g, this);
    }
    public void keyTyped(KeyEvent e)
    {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        Bird bird = game.getBird();
        if (keyCode == KeyEvent.VK_S) {
            state = PLAYING;
        }
        else if (keyCode == KeyEvent.VK_UP) {
            bird.setDy(bird.getY() - 90);
            bird.move();
            Game.addPoints();
        }
        else if (keyCode == KeyEvent.VK_RIGHT) {
            bird.setDx(bird.getX() + 90);
            bird.move();
        }
        else if (keyCode == KeyEvent.VK_LEFT) {
            bird.setDx(bird.getX() - 90);
            bird.move();
        }
        else if (state == GAME_OVER) {
            if (keyCode == KeyEvent.VK_R) {
                state = PLAYING;
                bird.reset();
            }
            else {
                state = WELCOME_SCREEN;
            }
        }
        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    // Closes program as needed
    public void setDefaultCloseOperation(int exitOnClose) {
    }
}
