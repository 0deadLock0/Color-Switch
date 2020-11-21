
package colorswitch;

import javafx.scene.Scene;
import javafx.scene.shape.Circle;

import javafx.geometry.Pos;
import javafx.scene.paint.Color;

class Player extends Circle
{
	Player()
	{
		super(10,Color.BLUE);
	}

	public void setPosition(double x, double y)
	{
		this.setCenterX(x);
		this.setCenterY(y);
	}

	public double getSize()
	{
		return 2*this.getRadius();
	}
	public double[] getPosition()
	{
		double[] position={this.getCenterX(),this.getCenterY()};
		return position;
	}

	public void moveUp()
	{
		
	}
	public void moveDown()
	{

	}
}