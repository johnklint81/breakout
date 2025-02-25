package breakout.model;

public abstract class Moveable extends Placeable {
    private double dx;
    private double dy;
    Moveable(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    Moveable(double x, double y, double width, double height, double dx, double dy) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }
    public double getDx() {
        return this.dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return this.dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }


}
