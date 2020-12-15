
package colorswitch;

import javafx.geometry.Bounds;
import javafx.scene.Group;

abstract class Obstacle extends Group
{
    protected double obstacleSize;
    protected boolean advanced;

    protected double xCenter;
    protected double yCenter;

    protected Obstacle()
    {
        super();
        this.getChildren().clear();
        this.obstacleSize=Settings.ObstacleSize;
        this.advanced=false;
        this.xCenter=0;
        this.yCenter=0;
    }

    public abstract void transform();
    public void moveDown()
    {
        this.setTranslateY(this.getTranslateY()+Settings.MotionSpeed);
    }
    public abstract void construct();

    public void updateProperties()
    {
        double[] position=this.getCentrePosition();
        this.xCenter=position[0];
        this.yCenter=position[1];
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