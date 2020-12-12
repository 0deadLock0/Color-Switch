
package colorswitch;

import javafx.geometry.Bounds;
import javafx.scene.Group;

abstract class Obstacle extends Group
{
    public Obstacle()
    {
        this.getChildren().clear();
    }

    public abstract void transform();
    public void moveDown()
    {
        this.setTranslateY(this.getTranslateY()+Settings.MotionSpeed);
    }

    public double[] getPosition()
    {
        Bounds boundsInScene=this.localToScene(this.getBoundsInLocal());
        return new double[]{boundsInScene.getCenterX(),boundsInScene.getCenterY()};
    }
}