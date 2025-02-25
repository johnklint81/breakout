package breakout.model;

import static breakout.model.Breakout.GAME_WIDTH;
import static breakout.model.Wall.Dir.LEFT_WALL;
import static breakout.model.Wall.Dir.RIGHT_WALL;

/*
 * A Paddle for the Breakout game
 *
 */
public class Paddle extends Moveable {

    public static final double PADDLE_WIDTH = 60;  // Default values, use in constructors, not directly
    public static final double PADDLE_HEIGHT = 10;
    public static final double PADDLE_SPEED = 3;

    public Paddle(double x, double y) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT, 0, 0);
    }
    public void checkWallCollision(Wall[] wallList) {
        // Checks and prevents movement of the paddle outside the boundaries of the game.
        for (Wall wall : wallList) {
            if (wall.getDir() == LEFT_WALL) {
                if (this.getX() < wall.getX()) {
                    this.setX(0);
                }
            }
            else if (wall.getDir() == RIGHT_WALL) {
                if (this.getX() + this.getWidth() > wall.getX()) {
                    this.setX(GAME_WIDTH - PADDLE_WIDTH);
                }
            }
        }
    }
    @Override
    public void setDx(double dx) {
        super.setDx(PADDLE_SPEED * dx);
    }
}
