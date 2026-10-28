package levels;

import javafx.scene.image.Image;

public class Prop {
    private double x;
    private double y;
    private Image image;

    public Prop(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }
}
