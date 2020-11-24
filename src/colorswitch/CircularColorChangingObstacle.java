
package colorswitch;

import javafx.scene.shape.Circle;

class CircularColorChangingObstacle extends ColorChangingObstacle
{
    public CircularColorChangingObstacle(double xCenter,double yCenter)
    {
        super();

        this.shape=new Circle(xCenter,yCenter,50);
        this.getChildren().add(this.shape);

        this.changeColor();
    }
}