
package colorswitch;

import javafx.scene.Scene;
import javafx.scene.shape.Circle;

import javafx.scene.paint.Color;

class Player extends Circle
{
    Player()
    {
        super(5,Color.BLUE);
    }

    public void setPosition(double x, double y)
    {
        this.setCenterX(x);
        this.setCenterY(y);
    }
    public void changeColor(Color color)
    {
        this.setFill(color);
    }

    public double getSize()
    {
        return 2*this.getRadius();
    }
    public double[] getPosition()
    {
        return new double[]{this.getCenterX(),this.getCenterY()};
    }
    public Color getColor()
    {
        return (Color)this.getFill();
    }

    public void moveUp()
    {
        this.setCenterY(this.getCenterY()-5);
    }
    public void moveDown()
    {
        this.setCenterY(this.getCenterY()+5);
    }
}