
package colorswitch;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

class CircularColorChangingObstacle extends ColorChangingObstacle
{
    public CircularColorChangingObstacle(double xCenter,double yCenter)
    {
        super();
        double radius=Settings.ObstacleSize/2;
        double[] angles={0,90,180,270};
        for(int i=0;i<4;++i) //4 parts are needed to support existing obstacle collision algorithm
        {
            Arc arc = new Arc();
            arc.setCenterX(xCenter);
            arc.setCenterY(yCenter);
            arc.setRadiusX(radius);
            arc.setRadiusY(radius);
            arc.setStartAngle(angles[i]);
            arc.setLength(90);
            arc.setType(ArcType.OPEN);
            arc.setFill(Color.TRANSPARENT);
            arc.setStrokeWidth(10);

            this.getChildren().add(arc);
        }
        this.transform();
    }
}