package colorswitch;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

class SquareRotatingObstacle extends RotatingObstacle
{
    private final double dimension;
    private static final int SubParts;

    static
    {
        SubParts = 4;
    }

    public SquareRotatingObstacle(double xCenter,double yCenter)
    {
        super();

        this.dimension = 100;

        double[][] sideDimensions = new double[SquareRotatingObstacle.SubParts][4];
        sideDimensions[0] = new double[]{ xCenter-this.dimension/2 , yCenter-this.dimension/2 , xCenter+this.dimension/2 , yCenter-this.dimension/2 };
        sideDimensions[1] = new double[]{ xCenter+this.dimension/2 , yCenter-this.dimension/2 , xCenter+this.dimension/2 , yCenter+this.dimension/2 };
        sideDimensions[2] = new double[]{ xCenter+this.dimension/2 , yCenter+this.dimension/2 , xCenter-this.dimension/2 , yCenter+this.dimension/2 };
        sideDimensions[3] = new double[]{ xCenter-this.dimension/2 , yCenter+this.dimension/2 , xCenter-this.dimension/2 , yCenter-this.dimension/2 };

        Line[] sides = new Line[SquareRotatingObstacle.SubParts];
        for(int i = 0 ; i < SquareRotatingObstacle.SubParts ; ++i)
            sides[i]=new Line( sideDimensions[i][0] , sideDimensions[i][1] , sideDimensions[i][2] , sideDimensions[i][3] );

        for(int i = 0 ; i < SquareRotatingObstacle.SubParts ; ++i)
        {
            sides[i].setStroke(Settings.IntersectionColors[i]);
            sides[i].setFill(Color.BLACK);
            sides[i].setStrokeWidth(10);
        }

        for(int i = 0 ; i < SquareRotatingObstacle.SubParts ; ++i)
            this.getChildren().add(sides[i]);

        this.setRotate(0.0);
    }
}
