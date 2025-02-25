package breakout.model;

public abstract class Placeable implements IPositionable {
    private double x;
    private double y;
    private final double width;
    private final double height;

    Placeable(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public boolean checkInsideObject(Placeable p) {
        // Checks if any edge of the placeable is inside the object.
        if (this.getX() + this.getWidth() > p.getX()
                && this.getX() < p.getX() + p.getWidth()) {
            if (this.getY() + this.getHeight() > p.getY() &&
                    this.getY() < p.getY() + p.getHeight()) {
                return true;
            }
        }
        return false;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }


}
