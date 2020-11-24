
package colorswitch;

import javafx.scene.shape.Shape;

import javafx.scene.paint.Color;

class ColorChangingObstacle extends Obstacle
{
	protected Shape shape;
	private Color[] colors;
	private int colorIndex;

	public ColorChangingObstacle()
	{
		super();
		this.shape=null;
		this.colors=new Color[]{Color.BLUE,Color.RED,Color.YELLOW,Color.GREEN};
		this.colorIndex=-1;
		// this.changeColor();
	}

	public void transform()
	{
		this.changeColor();
	}

	public void startTransformation()
	{
	}
	public void stopTransformation()
	{
	}
	protected void changeColor()
	{
		++this.colorIndex;
		if(this.colorIndex==4)
			this.colorIndex=0;
		this.shape.setFill(this.colors[this.colorIndex]);
	}
}