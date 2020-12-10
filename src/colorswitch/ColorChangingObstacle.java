
package colorswitch;

import javafx.scene.Node;
import javafx.scene.shape.Shape;

abstract class ColorChangingObstacle extends Obstacle
{
    private int colorIndex;

    public ColorChangingObstacle()
    {
        super();
        this.colorIndex=-1;
    }

    public void transform()
    {
        this.changeColor();
    }
    private void changeColor()
    {
        ++this.colorIndex;
        if(this.colorIndex==4)
            this.colorIndex=0;
        for (Node part : this.getChildren().toArray(new Node[0]))
            ((Shape)part).setStroke(Settings.IntersectionColors[this.colorIndex]);
    }
}