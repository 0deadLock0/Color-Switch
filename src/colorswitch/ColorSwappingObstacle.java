package colorswitch;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

abstract class ColorSwappingObstacle extends Obstacle
{
	public ColorSwappingObstacle()
	{
		super();
	}

	public void transform()
	{
		ArrayList<Color> colors=new ArrayList<>(Settings.IntersectionColors.length);
		colors.addAll(Arrays.asList(Settings.IntersectionColors));
		Random rd=new Random();
		for (Node part : this.getChildren().toArray(new Node[0]))
		{
			int colorIndex=rd.nextInt(colors.size());
			Color newColor=colors.get(colorIndex);
			colors.remove(colorIndex);
			((Shape) part).setStroke(newColor);
		}
	}
}
