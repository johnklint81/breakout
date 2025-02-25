package breakout.model;


import java.util.ArrayList;
import java.util.List;
import breakout.event.EventBus;
import breakout.event.ModelEvent;
import static breakout.model.Paddle.*;
import static breakout.model.Wall.Dir.*;

/*
 *  Overall all logic for the Breakout Game
 *  Model class representing the full game
 *  This class should use other objects delegate work.
 *
 *  NOTE: Nothing visual here
 *
 */
public class Breakout {

    public static final double GAME_WIDTH = 400;
    public static final double GAME_HEIGHT = 400;
    public static final double BALL_SPEED_FACTOR = 1.05; // Increase ball speed
    public static final long SEC = 1_000_000_000;  // Nanoseconds used by JavaFX

    private Ball ball;
    public final int ballStartY = (int) GAME_HEIGHT / 2;
    public final int ballStartX = (int) GAME_WIDTH / 2;

    private final Wall[] walls = new Wall[3];
    private final List<Brick> bricks;
    public Paddle paddle;
    private int nBalls = 5;
    private int playerPoints;

    private boolean gameOver = false;
    public Breakout(List<Brick> bricks) {
        // Constructs a brackout game with below properties.

        this.ball = new Ball(ballStartX, ballStartY / 2);
//        this.ball = new Ball(ballStartX, 1);
        this.nBalls -= 1;
        this.walls[0] = new Wall(0, 0, LEFT_WALL);
        this.walls[1] = new Wall(GAME_WIDTH, 0, Wall.Dir.RIGHT_WALL);
        this.walls[2] = new Wall(0, 0, Wall.Dir.ROOF);
        this.paddle = new Paddle(GAME_WIDTH / 2 - PADDLE_WIDTH / 2, GAME_HEIGHT - 30);
        this.bricks = bricks;
    }
    // To avoid multiple collisions
    long timeForLastHit = SEC / 5;

    // --------  Game Logic -------------

    public void update(long now) {
        this.updateBall(now);
        this.updatePaddle();
    }

    // ----- Helper methods--------------
    public void checkCollision(long now) {
        // Master function for all types of collisions. Sends events to EventBus.
        if (this.ball.checkWallCollision(this.walls)) {
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_WALL, this.ball));
        }
        if (this.ball.checkPaddleCollision(paddle)) {
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_PADDLE, this.paddle));
        }
        int points = this.ball.checkBrickCollision(bricks, now, timeForLastHit);
        if (points != 0) {
            this.playerPoints += points;
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_BRICK));
        }
    }
    public void updateBall(long now) {
        // Master function that performs all necessary steps to update the ball position and keep track of whether
        // the ball is "live" or "dead". Also handles spawning of new balls.
        double updatedX = this.ball.getX() + this.ball.getDx();
        double updatedY = this.ball.getY() + this.ball.getDy();
        this.ball.setOldX(this.ball.getX());
        this.ball.setOldY(this.ball.getY());
        this.ball.setX(updatedX);
        this.ball.setY(updatedY);
        this.checkCollision(now);
        boolean ballOutside = this.ball.checkLossCondition();
        if (ballOutside && nBalls > 0) {
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.NEW_BALL));
            this.ball = new Ball(ballStartX, ballStartY);
            this.nBalls -= 1;

        }
        else if (ballOutside && nBalls == 0) {
            this.gameOver = true;
            this.ball.setDx(0);
            this.ball.setDy(0);

        }

    }

    public void updatePaddle() {
        // Master function that controls the paddle update.
        double updatedX = this.paddle.getX() + this.paddle.getDx();
        this.paddle.setX(updatedX);
        this.paddle.checkWallCollision(this.walls);
    }

    // --- Used by GUI  ------------------------

    public List<IPositionable> getPositionables() {
        // List of all drawable objects in the game (except background and score)
        List<IPositionable> list = new ArrayList<>();
        list.add(this.ball);
        list.add(this.paddle);
        list.addAll(bricks);
        return list;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public int getnBalls() {
        return nBalls;
    }

    public boolean checkGameOver() {
        return gameOver;
    }
}
