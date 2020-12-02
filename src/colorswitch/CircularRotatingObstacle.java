
package colorswitch;

import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.paint.Color;

public class CircularRotatingObstacle extends RotatingObstacle
{

    private final double radius;
    private static final int SubParts;

    static
    {
        SubParts =4;
    }

    public CircularRotatingObstacle(double xCenter,double yCenter)
    {
        super();

        this.radius=50;

        double[] startAngles={0.0,90.0,180.0,270.0};
        Arc[] arcs=new Arc[CircularRotatingObstacle.SubParts];
        for(int i = 0; i<CircularRotatingObstacle.SubParts; ++i)
            arcs[i]=new Arc();

        for(int i = 0; i<CircularRotatingObstacle.SubParts; ++i)
        {
            arcs[i].setCenterX(xCenter);
            arcs[i].setCenterY(yCenter);
            arcs[i].setRadiusX(this.radius);
            arcs[i].setRadiusY(this.radius);
            arcs[i].setStartAngle(startAngles[i]);
            arcs[i].setLength(90.37167f);
            arcs[i].setType(ArcType.OPEN);
            arcs[i].setStroke(Settings.IntersectionColors[i]);
            arcs[i].setFill(Color.BLACK);
            arcs[i].setStrokeWidth(10);
        }

        for(int i = 0; i<CircularRotatingObstacle.SubParts; ++i)
            this.getChildren().add(arcs[i]);

        this.setRotate(0.0);
    }
}