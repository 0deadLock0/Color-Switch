package colorswitch;

import javafx.scene.paint.Color;

class Settings
{
    public static final double DesiredSceneWidth;
    public static final double DesiredSceneHeight;

    public static final long TimeDelay; // in ms

    public static double TransformationSpeed;
    public static long ColorChangingObstacleTransformationSpeed; //Special Case
    public static final double MotionSpeed; // in pixels
    public static final double Gravity; // in pixels

    public static final Color[] IntersectionColors;

    public static final int ObstaclesCount;
    public static final int BrokenBallsCount;

    static
    {
        DesiredSceneWidth = 500;
        DesiredSceneHeight = 650;

        TimeDelay = 20_000_000L; // 20 ms

        TransformationSpeed = 0.05;
        MotionSpeed = 5;
        Gravity = MotionSpeed * 0.15;

        IntersectionColors = new Color[]{Color.BLUE,Color.YELLOW,Color.RED,Color.GREEN};

        ColorChangingObstacleTransformationSpeed = 4*(long)(TimeDelay*TransformationSpeed)/(long)1e5;
        ObstaclesCount = 3; //update according to number of obstacles available //check children of Obstacle class

        BrokenBallsCount = 100;
    }
}
