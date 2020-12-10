
package colorswitch;

import java.util.Random;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

class Player extends Circle
{
    private int score;
    private int starsCollected;

    private Player()
    {
        super(5,Settings.IntersectionColors[(new Random()).nextInt(Settings.IntersectionColors.length)]);
        this.score=0;
        this.starsCollected=0;
    }

    public Player(double x, double y)
    {
        this();
        this.setPosition(x,y);
    }

    public void setPosition(double x, double y)
    {
        this.setCenterX(x);
        this.setCenterY(y);
    }

    public void collectStar(Star star)
    {
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

    public double getSize()
    {
        return 2*this.getRadius();
    }
    public double[] getPosition()
    {
        return new double[]{this.getCenterX(),this.getCenterY()};
    }
    public int getScore()
    {
        return this.score;
    }
    public Color getColor()
    {
        return (Color)this.getFill();
    }
}