
package colorswitch;

import javafx.geometry.Bounds;
import javafx.scene.Group;

abstract class Obstacle extends Group
{
    protected double obstacleSize;
    protected boolean advanced;

    protected Obstacle()
    {
        this.getChildren().clear();
        this.obstacleSize=Settings.ObstacleSize;
        this.advanced =false;
    }

    public abstract void transform();
    public void moveDown()
    {
        this.setTranslateY(this.getTranslateY()+Settings.MotionSpeed);
    }

    public void enableAdvanceMode()
    {
        this.advanced =true;
    }

    public double[] getCentrePosition()
    {
        Bounds boundsInScene=this.localToScene(this.getBoundsInLocal());
        return new double[]{boundsInScene.getCenterX(),boundsInScene.getCenterY()};
    }
    public double getTopPoint()
    {
        Bounds boundsInScene=this.localToScene(this.getBoundsInLocal());
        return boundsInScene.getMinY();
    }
}