import java.awt.*;

public class Car {
    private Image image;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int width;
    private int height;
    private boolean isTruck;
    public Car(Image image, int x, int y, boolean isTruck) {
        this.image = image;
        this.x = x;
        this.y = y;
        dx = 10;
        dy = 0;
        this.isTruck = isTruck;
        if (isTruck) {
            width = 500;
        }
        else {
            width = 400;
        }
        height = 300;

    }
    public void move() {
        wrap();
        x -= dx;

    }
    public void drawCar (Graphics g, GameViewer game) {
        if (isTruck) {
            width = 400;
            height = 100;
            g.drawImage(image, x, y, width, height, game);
        }
        else {
            width = 200;
            g.drawImage(image, x, y, width, height, game);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    public void wrap() {
        if (x - dx < 400) {
            x = 400 - width;
        }
    }
}
