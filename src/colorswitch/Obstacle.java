
package colorswitch;

import javafx.scene.Group;
import javafx.scene.paint.Color;

abstract class Obstacle extends Group
{

    public Obstacle()
    {
        this.getChildren().clear();
    }

    public abstract void transform();
    public abstract void startTransformation();
    public abstract void stopTransformation();
    public void moveDown()
    {
        this.setTranslateY(this.getTranslateY()+5);
    }
}