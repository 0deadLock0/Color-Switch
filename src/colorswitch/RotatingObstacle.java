
package colorswitch;

abstract class RotatingObstacle extends Obstacle
{
    private final double factor;

    public RotatingObstacle()
    {
        super();
        this.factor=100;
    }

    public void transform()
    {
        this.rotate();
    }
    private void rotate()
    {
        this.setRotate(this.getRotate()+Settings.TransformationSpeed*this.factor);
    }
}