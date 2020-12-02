
package colorswitch;

abstract class RotatingObstacle extends Obstacle
{
    public RotatingObstacle()
    {
        super();
    }

    public void transform()
    {
        double factor=100;
        this.setRotate(this.getRotate()+Settings.TransformationSpeed*factor);
    }
}