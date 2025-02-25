package breakout.model;

import java.util.List;
import java.awt.geom.Line2D;

import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;
import static breakout.model.Wall.Dir.LEFT_WALL;
import static breakout.model.Wall.Dir.RIGHT_WALL;


/*
 *    A Ball for the Breakout game
 */

public class Ball extends Moveable {
    private double oldX = GAME_HEIGHT;
    private double oldY = GAME_WIDTH;
    private static final double BALL_DIAMETER = 10;
    private double BALL_SPEED = 1.4 * (1 - Math.random() / 2);
    private long lastCollisionTime = 0;

    public Ball(double x, double y) {
        super(x, y, BALL_DIAMETER, BALL_DIAMETER);
        double[] temp = randomNormalisedVelocity();
        this.setDx(BALL_SPEED * temp[0]);
        this.setDy(-BALL_SPEED * temp[1]);
    }

    public double[] randomNormalisedVelocity() {
        // Helper function to provide a "reasonable" intial velocity of the ball. Gives a velocity downwards that does
        // not have only a vertical component.
        double[] rand = new double[2];
        double nonNormalisedDx = 1 - 2 * Math.random();
        while (Math.abs(nonNormalisedDx) < 0.1) {
            nonNormalisedDx = 1 - 2 * Math.random();
        }
        double nonNormalisedDy = -(1 + Math.random());
        double norm = Math.sqrt(Math.pow(nonNormalisedDx, 2) + Math.pow(nonNormalisedDy, 2));
        rand[0] = nonNormalisedDx / norm;
        rand[1] = nonNormalisedDy / norm;
        return rand;
    }

    public boolean checkWallCollision(Wall[] walls) {
        // Checks if the ball is outside the boundaries of the game.
        for (Wall wall : walls) {
            if (wall.getDir() == LEFT_WALL) {
                if (this.getX() < wall.getX()) {
                    this.setDx(-this.getDx());
                    this.setX(wall.getX());
                    return true;
                }
            }
            else if (wall.getDir() == RIGHT_WALL) {
                if (this.getX() + this.getWidth() > wall.getX()) {
                    this.setDx(-this.getDx());
                    this.setX(wall.getX() - this.getWidth());
                    return true;
                }
            }
            else {
                if (this.getY() < wall.getY()) {
                    this.setDy(-this.getDy());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkPaddleCollision(Paddle paddle) {
        // Checks if the ball has collided with the paddle.
        if (checkInsideObject(paddle) && this.getY() + this.getHeight() < paddle.getY() + this.getDy()) {
            this.setDy(-this.getDy());
            this.setY(paddle.getY() - this.getHeight());
            return true;
        }
        return false;
    }

    public int checkBrickCollision(List<Brick> bricks, long now, long timeForLastHit) {
        int points = 0;
        for (Brick b : bricks) {
            // This section checks if the ball is inside a brick and performs a suitable update of ball (and brick).
            if (checkInsideObject(b)) {
                Side entrySide = getIntersectionSide(b);
                if (entrySide == Side.BOTTOM_SIDE) {
                    this.setDy(-this.getDy());
                    this.setY(b.getY() + b.getHeight());
                }
                else if (entrySide == Side.TOP_SIDE) {
                    this.setDy(-this.getDy());
                    this.setY(b.getY() - this.getHeight());
                }
                else if (entrySide == Side.LEFT_SIDE) {
                    this.setDx(-this.getDx());
                    this.setX(b.getX() - this.getWidth());
                }
                else if (entrySide == Side.RIGHT_SIDE) {
                    this.setDx(-this.getDx());
                    this.setX(b.getX() + b.getWidth());
                }
                if (now - lastCollisionTime > timeForLastHit) {
                    lastCollisionTime = now;
                    points = b.getPoints();
                    bricks.remove(b);
                }
                return points;
            }
        }
        return points;
    }
    public Side getIntersectionSide(Brick brick) {
        // Retrieves the side of intersection, if there is any.
        Side entrySide = null;
        if (checkIntersect(brick, Side.BOTTOM_SIDE)) {
            return Side.BOTTOM_SIDE;
        }
        else if (checkIntersect(brick, Side.TOP_SIDE)) {
            return Side.TOP_SIDE;
        }
        else if (checkIntersect(brick, Side.LEFT_SIDE)) {
            return Side.LEFT_SIDE;
        }
        else if (checkIntersect(brick, Side.RIGHT_SIDE)) {
            return Side.RIGHT_SIDE;
        }
        return entrySide;
    }

    public boolean checkIntersect(Brick brick, Side side) {
        double[] oldBallPos = {this.getOldX(), this.getOldY()};
        double[] topLeftCorner = {brick.getX(), brick.getY()};
        double[] topRightCorner = {brick.getX() + brick.getWidth(), brick.getY()};
        double[] bottomLeftCorner = {brick.getX(), brick.getY() + brick.getHeight()};
        double[] bottomRightCorner = {brick.getX() + brick.getWidth(), brick.getY() + brick.getHeight()};
        boolean intersect = false;
        if (side == Side.TOP_SIDE) {
            // Check if intersection of top of brick.
            intersect = Line2D.linesIntersect(oldBallPos[0], oldBallPos[1] + this.getHeight(),
                                              this.getX(), this.getY() + this.getHeight(),
                                              topLeftCorner[0], topLeftCorner[1],
                                              topRightCorner[0], topRightCorner[1])
                    || Line2D.linesIntersect(oldBallPos[0] + this.getWidth(), oldBallPos[1] + this.getHeight(),
                                             this.getX() + this.getWidth(),
                                             this.getY() + this.getHeight(),
                                             topLeftCorner[0], topLeftCorner[1],
                                             topRightCorner[0], topRightCorner[1]);
        }
        if (side == Side.BOTTOM_SIDE) {
            // Check if intersection of bottom of brick.
            intersect = Line2D.linesIntersect(oldBallPos[0], oldBallPos[1],
                                              this.getX(), this.getY(),
                                              bottomLeftCorner[0], bottomLeftCorner[1],
                                              bottomRightCorner[0], bottomRightCorner[1])
                    || Line2D.linesIntersect(oldBallPos[0] + this.getWidth(), oldBallPos[1],
                                             this.getX() + this.getWidth(), this.getY(),
                                             bottomLeftCorner[0], bottomLeftCorner[1],
                                             bottomRightCorner[0], bottomRightCorner[1]);
        }
        if (side == Side.LEFT_SIDE) {
            // Check if intersection of left side of brick.
            intersect = Line2D.linesIntersect(oldBallPos[0] + this.getWidth(), oldBallPos[1],
                                              this.getX() + this.getWidth(), this.getY(),
                                              topLeftCorner[0], topLeftCorner[1],
                                              bottomLeftCorner[0], bottomLeftCorner[1])
                    || Line2D.linesIntersect(oldBallPos[0] + this.getWidth(), oldBallPos[1] + this.getHeight(),
                                             this.getX() + this.getWidth(), this.getY() + this.getHeight(),
                                             topLeftCorner[0], topLeftCorner[1],
                                             bottomLeftCorner[0], bottomLeftCorner[1]);
        }
        if (side == Side.RIGHT_SIDE) {
            // Check if intersection of right side of brick.
            intersect = Line2D.linesIntersect(oldBallPos[0], oldBallPos[1],
                                              this.getX(), this.getY(),
                                              topRightCorner[0], topRightCorner[1],
                                              bottomRightCorner[0], bottomRightCorner[1])
                    || Line2D.linesIntersect(oldBallPos[0], oldBallPos[1] + this.getHeight(),
                                             this.getX(), this.getY() + this.getHeight(),
                                             topRightCorner[0], topRightCorner[1],
                                             bottomRightCorner[0], bottomRightCorner[1]);
        }
        return intersect;
    }

    public enum Side {
        LEFT_SIDE, RIGHT_SIDE, TOP_SIDE, BOTTOM_SIDE
    }

    public boolean checkLossCondition() {
        if (this.getY() > GAME_HEIGHT + 10) {
            return true;
        }
        return false;
    }

    public double getOldX() {
        return oldX;
    }

    public void setOldX(double oldX) {
        this.oldX = oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public void setOldY(double oldY) {
        this.oldY = oldY;
    }
}
