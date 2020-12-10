package colorswitch;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class ColorBall extends Group
{
    private final double radius;
    private Color switchColor;

    private static final int SubParts;

    static
    {
        SubParts=4;
    }

    public ColorBall(double xCenter, double yCenter)
    {
        super();

        this.radius = 10;
        this.switchColor = Settings.IntersectionColors[(new Random()).nextInt(Settings.IntersectionColors.length)];

        double[] startAngles = {0.0, 90.0, 180.0, 270.0};
        Arc[] arcs = new Arc[ColorBall.SubParts];
        for (int i = 0; i < ColorBall.SubParts; ++i)
            arcs[i] = new Arc();

        for (int i = 0; i < ColorBall.SubParts; ++i)
        {
            arcs[i].setCenterX(xCenter);
            arcs[i].setCenterY(yCenter);
            arcs[i].setRadiusX(this.radius);
            arcs[i].setRadiusY(this.radius);
            arcs[i].setStartAngle(startAngles[i]);
            arcs[i].setLength(90.37167f);
            arcs[i].setType(ArcType.ROUND);
            arcs[i].setFill(Settings.IntersectionColors[i]);
        }

        for (int i = 0; i < ColorBall.SubParts; ++i)
            this.getChildren().add(arcs[i]);
    }
    public ColorBall(double xCenter, double yCenter, Color ignoredColor)
    {
        this(xCenter,yCenter);

        Color chosenColor=ignoredColor;
        Random rd=new Random();
        while(chosenColor.equals(ignoredColor))
            chosenColor = Settings.IntersectionColors[rd.nextInt(Settings.IntersectionColors.length)];
        this.switchColor = chosenColor;
    }

    public void moveDown()
    {
        this.setTranslateY(this.getTranslateY()+Settings.MotionSpeed);
    }
    public void changeColors()
    {
        Node[] arcs = this.getChildren().toArray(new Node[0]);

        int i = (new Random()).nextInt(arcs.length);
        int k = 0;
        for(int j = i; j < Settings.IntersectionColors.length; ++j)
        {
            ((Arc)arcs[j]).setFill(Settings.IntersectionColors[k]);
            ++k;
        }
        for(int j = 0; j < i; ++j)
        {
            ((Arc)arcs[j]).setFill(Settings.IntersectionColors[k]);
            ++k;
        }
    }

    public Color getColor()
    {
        return this.switchColor;
    }
}