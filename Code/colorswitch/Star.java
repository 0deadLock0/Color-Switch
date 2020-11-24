
package colorswitch;

import javafx.scene.shape.Polygon;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;

class Star extends Polygon
{
	private int score;
	private final static int points;
	private final static double[] adjustmentsX;
	private final static double[] adjustmentsY;
	private final double scalingFactor;

	static
	{
		points=10;
		adjustmentsX=new double[]{0.0,1.5,5.5,2.0,3.5,0.0,-3.5,-2.0,-5.5,-1.5};
		adjustmentsY=new double[]{-5.0,-1.5,-1.5,1,5.0,2.0,5.0,1,-1.5,-1.5};
	}

	public Star()
	{
		this.scalingFactor=2;
		this.score=1;
		Double[] pointsXY=new Double[2*Star.points];
		for(int i=0;i<Star.points;++i)
		{
			double x=Star.adjustmentsX[i]*scalingFactor;
			double y=Star.adjustmentsY[i]*scalingFactor;
			pointsXY[2*i]=x;
			pointsXY[2*i+1]=y;
		}
		this.getPoints().addAll(pointsXY);
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(0.5);
		this.setFill(Color.YELLOW);
		this.setEffect(new Glow(0.5));
	}
	public Star(double xPosition,double yPosition)
	{
		this();
		this.setPosition(xPosition,yPosition);
	}

	public double getScore()
	{
		return this.score;
	}
	public void setScore(int s)
	{
		this.score=s;
	}
	public void setPosition(double x,double y)
	{
		Double[] pointsXY=new Double[2*Star.points];
		int i=0;
		for(Double point : this.getPoints())
		{
			pointsXY[i]=point;
			++i;
		}
		this.getPoints().clear();
		for(i=0;i<pointsXY.length;++i)
		{
			if((i&1)==0)
				pointsXY[i]+=x;
			else
				pointsXY[i]+=y;
		}
		this.getPoints().addAll(pointsXY);
	}

	public void moveDown()
	{
		this.setPosition(0,5);
	}
}