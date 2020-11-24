
package colorswitch;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import javafx.animation.RotateTransition;

class RotatingObstacle extends Obstacle
{
	protected double speed;
	protected RotateTransition rotateTransition;
	protected Color[] colors;

	public RotatingObstacle()
	{
		super();
		this.speed=0.25;
		this.rotateTransition=new RotateTransition();
		this.colors=new Color[]{Color.BLUE,Color.YELLOW,Color.RED,Color.GREEN};
	}

	public void transform()
	{
	}

	public void startTransformation()
	{
		this.startRotation();
	}
	public void stopTransformation()
	{
		this.stopRotation();
	}
	private void startRotation()
	{
		this.rotateTransition.play();
	}
	private void stopRotation()
	{
		this.rotateTransition.stop();
	}
}