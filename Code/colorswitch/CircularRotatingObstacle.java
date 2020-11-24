
package colorswitch;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.scene.paint.Color;

public class CircularRotatingObstacle extends RotatingObstacle 
{

	private double radius;
	private static final int subParts;

	static
	{
		subParts=4;
	}

	public CircularRotatingObstacle(double xCenter,double yCenter)
	{
		super();

		this.radius=50;

		double[] startAngles={0.0,90.0,180.0,270.0};
		Arc[] arcs=new Arc[subParts];
		for(int i=0;i<CircularRotatingObstacle.subParts;++i)
			arcs[i]=new Arc();

		for(int i=0;i<CircularRotatingObstacle.subParts;++i)
		{
			arcs[i].setCenterX(xCenter);
			arcs[i].setCenterY(yCenter);
			arcs[i].setRadiusX(this.radius);
			arcs[i].setRadiusY(this.radius);
			arcs[i].setStartAngle(startAngles[i]);
			arcs[i].setLength(90.37167f);
			arcs[i].setType(ArcType.OPEN);
			arcs[i].setStroke(this.colors[i]);
			arcs[i].setFill(Color.BLACK);
			arcs[i].setStrokeWidth(10);
		}

		for(int i=0;i<CircularRotatingObstacle.subParts;++i)
			this.getChildren().add(arcs[i]);
	
		this.rotateTransition.setAxis(Rotate.Z_AXIS);
		this.rotateTransition.setByAngle(360);
		this.rotateTransition.setCycleCount(Animation.INDEFINITE);
		this.rotateTransition.setDuration(Duration.INDEFINITE);
		this.rotateTransition.setAutoReverse(false);
		this.rotateTransition.setInterpolator(Interpolator.LINEAR);
		this.rotateTransition.setNode(this);
		this.rotateTransition.setRate(speed);
	}
}