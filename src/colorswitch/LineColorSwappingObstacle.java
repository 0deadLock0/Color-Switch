package colorswitch;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineColorSwappingObstacle extends ColorSwappingObstacle
{
	private final double dimension;
	private final double depth;
	private static final int SubParts;

	static
	{
		SubParts = 3;
	}

	public LineColorSwappingObstacle(double xCenter,double yCenter)
	{
		super();
		this.dimension=1.5*Settings.ObstacleSize;
		this.depth=50;

		double[][] sideDimensions = new double[LineColorSwappingObstacle.SubParts][4];
		sideDimensions[0] = new double[]{ xCenter-this.dimension/2 , yCenter+this.depth , xCenter-this.dimension/6 , yCenter+this.depth };
		sideDimensions[1] = new double[]{ xCenter-this.dimension/6 , yCenter+this.depth , xCenter+this.dimension/6 , yCenter+this.depth };
		sideDimensions[2] = new double[]{ xCenter+this.dimension/6 , yCenter+this.depth , xCenter+this.dimension/2 , yCenter+this.depth };

		Line[] sides = new Line[LineColorSwappingObstacle.SubParts];
		for(int i = 0 ; i < LineColorSwappingObstacle.SubParts ; ++i)
			sides[i] = new Line( sideDimensions[i][0] , sideDimensions[i][1] , sideDimensions[i][2] , sideDimensions[i][3] );

		for(int i = 0 ; i < LineColorSwappingObstacle.SubParts ; ++i)
		{
			sides[i].setFill(Color.TRANSPARENT);
			sides[i].setStrokeWidth(10);
		}

		for(int i = 0 ; i < LineColorSwappingObstacle.SubParts ; ++i)
			this.getChildren().add(sides[i]);

		this.transform();
	}

	public double[] getCentrePosition()
	{
		Bounds boundsInScene=this.localToScene(this.getBoundsInLocal());
		return new double[]{boundsInScene.getCenterX(),boundsInScene.getCenterY()-this.depth};
	}
}
