
package colorswitch;

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
}