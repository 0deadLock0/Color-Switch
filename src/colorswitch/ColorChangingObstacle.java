
package colorswitch;

import javafx.scene.shape.Shape;

// This type of obstacle don't seem feasible in a game
// Better be removed
abstract class ColorChangingObstacle extends Obstacle
{
    protected Shape shape;
    private int colorIndex;

    public ColorChangingObstacle()
    {
        super();
        this.shape=null;
        this.colorIndex=-1;
    }

    public void transform()
    {
        this.changeColor();
    }

    protected void changeColor()
    {
        ++this.colorIndex;
        if(this.colorIndex==4)
            this.colorIndex=0;
        this.shape.setFill(Settings.IntersectionColors[this.colorIndex]);
    }
}