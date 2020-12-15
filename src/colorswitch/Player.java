
package colorswitch;

import java.util.Random;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

class Player extends Circle
{
    private int score;
    private int starsCollected;

    private double xCenter;
    private double yCenter;

    private Color color;

    public Player(double xCenter, double yCenter)
    {
        super();

        this.score=0;
        this.starsCollected=0;
        this.xCenter=xCenter;
        this.yCenter=yCenter;
        this.color=Settings.IntersectionColors[(new Random()).nextInt(Settings.IntersectionColors.length)];

        this.construct();
    }

    public void construct()
    {
        this.setRadius(5);
        this.setFill(this.color);
        this.setPosition(this.xCenter,this.yCenter);
    }

    public void setPosition(double x, double y)
    {
        this.setCenterX(x);
        this.setCenterY(y);
    }

    public void collectStar(Star star)
    {
        ++this.starsCollected;
        this.increaseScore(star.getScore());
    }
    public void changeColor(ColorBall colorBall)
    {
        this.setFill(colorBall.getColor());
    }

    void increaseScore(int score)
    {
        this.score+=score;
    }

    public void moveUp()
    {
        this.setCenterY(this.getCenterY()-Settings.MotionSpeed);
    }
    public void moveDown()
    {
        this.setCenterY(this.getCenterY()+Settings.Gravity);
    }

    public double[] getPosition()
    {
        return new double[]{this.getCenterX(),this.getCenterY()};
    }
    public Color getColor()
    {
        return (Color)this.getFill();
    }
    public int getScore()
    {
        return this.score;
    }
    public int getStarsCollected()
    {
        return this.starsCollected;
    }

    public void updateProperties()
    {
        double[] position=this.getPosition();
        this.xCenter=position[0];
        this.yCenter=position[1];
        this.color=this.getColor();
    }
}