package breakout;

import breakout.model.Ball;
import breakout.model.Brick;

import static java.lang.System.out;

/**
 * Here you should write your tests (not much logic in this lab though ...)
 *
 * Right click and run ...
 */
public class Test {
    /* Weird sound error in LinuxMint... Works in Ubuntu!
    Exception in thread "Thread-2" com.sun.media.jfxmedia.MediaException: Could not create player!
	at javafx.media@20.0.1/com.sun.media.jfxmediaimpl.NativeMediaManager.getPlayer(NativeMediaManager.java:297)
	at javafx.media@20.0.1/com.sun.media.jfxmedia.MediaManager.getPlayer(MediaManager.java:118)
	at javafx.media@20.0.1/com.sun.media.jfxmediaimpl.NativeMediaAudioClipPlayer.play(NativeMediaAudioClipPlayer.java:319)
	at javafx.media@20.0.1/com.sun.media.jfxmediaimpl.NativeMediaAudioClipPlayer.clipScheduler(NativeMediaAudioClipPlayer.java:112)
	at javafx.media@20.0.1/com.sun.media.jfxmediaimpl.NativeMediaAudioClipPlayer$Enthreaderator.lambda$static$0(NativeMediaAudioClipPlayer.java:85)
	at java.base/java.lang.Thread.run(Thread.java:1623)
    */

    public static void main(String[] args) {
        new Test().test();
    }

    void test() {

        //Ball b = new Ball(4,5 ); // Create object (just an example)

        //Brick br = new Brick(4, 5 );
        //b.hit(br) == true;


        //out.println( b.doIt() == 5);     // Call methods and check
        //out.println( b.doOther() == 7);

        // Create other object  ...
        // .. call methods to test.

//        public Ball.Side getEntrySide(Brick brick) {
//            double[] oldBallPos = {this.getOldX() + this.getWidth() / 2, this.getOldY() + this.getHeight() / 2};
//            double[] topLeftCorner = {brick.getX(), brick.getY()};
//            double[] topRightCorner = {brick.getX() + brick.getWidth(), brick.getY()};
//            double[] bottomLeftCorner = {brick.getX(), brick.getY() + brick.getHeight()};
//            double[] bottomRightCorner = {brick.getX() + brick.getWidth(), brick.getY() + brick.getHeight()};
//            double shortestDistance = getDistanceFromPoint(oldBallPos, bottomLeftCorner, bottomRightCorner);
//            Ball.Side entrySide = Ball.Side.BOTTOM_SIDE;
//            if (getDistanceFromPoint(oldBallPos, topLeftCorner, topRightCorner) < shortestDistance) {
//                shortestDistance = getDistanceFromPoint(oldBallPos, topLeftCorner, topRightCorner);
//                entrySide = Ball.Side.TOP_SIDE;
//            }
//            if (getDistanceFromPoint(oldBallPos, topLeftCorner, bottomLeftCorner) < shortestDistance) {
//                shortestDistance = getDistanceFromPoint(oldBallPos, topLeftCorner, bottomLeftCorner);
//                entrySide = Ball.Side.LEFT_SIDE;
//            }
//            if (getDistanceFromPoint(oldBallPos, topRightCorner, bottomRightCorner) < shortestDistance) {
////            shortestDistance = getDistanceFromPoint(oldBallPos, topRightCorner, bottomRightCorner);
//                entrySide = Ball.Side.RIGHT_SIDE;
//            }
//            return entrySide;
//        }
//
//        public double getDistanceFromPoint(double[] point0, double[] point1, double[] point2) {
//            // See https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line#Line_defined_by_two_points for math.
//            double nominator = Math.abs((point2[0] - point1[0]) * (point1[1] - point0[1])
//                                                - (point1[0] - point0[0]) * (point2[1] - point1[1]));
//            double denominator = Math.sqrt(Math.pow((point2[0] - point1[0]), 2) + Math.pow((point2[1] - point1[1]), 2));
//            return nominator / denominator;
//        }



    }


}
