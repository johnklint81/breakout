package breakout.model;

/*
        A wall for the ball to bounce
 */
public class Wall extends Placeable {
    private final Dir dir;

    public enum Dir {
        ROOF, LEFT_WALL, RIGHT_WALL
    }
    public Wall(double x, double y, Dir dir) {
        super(x, y, 0, 0);
        this.dir = dir;
    }
    public Dir getDir() {
        return this.dir;
    }
}
