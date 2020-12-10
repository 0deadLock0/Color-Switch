
package colorswitch;

import java.util.Random;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;

import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.util.Duration;

public class GameSpace
{
    private final ColorSwitch application;

    private final Stage applicationWindow;
    private final Scene gameScene;
    private Pane gamePane;
    private final Label scoreLabel;

    private final Player player;
    private Star star;
    private Obstacle obstacle;
    private ColorBall colorBall;

    private boolean gameActive;
    private boolean gameOver;
    private long lastTime;

    private long ideallyObstacleTransformed;

    public GameSpace(ColorSwitch sourceApplication, Stage window, double desiredWidth, double desiredHeight)
    {
        this.application=sourceApplication;
        this.applicationWindow=window;
        this.player=new Player();
        this.star=null;
        this.obstacle=null; //different types of obstacles exists
        this.colorBall=null;
        this.gameActive=false;
        this.gameOver=false;
        this.ideallyObstacleTransformed=0;
        this.lastTime=0;
        this.scoreLabel=new Label("Score: 0");
        this.gameScene=this.createScene(desiredWidth,desiredHeight);
    }

    private Scene createScene(double desiredWidth, double desiredHeight)
    {
        this.gamePane=new Pane();
        this.gamePane.setBackground(Background.EMPTY);
        this.gamePane.getChildren().add(this.player);
        this.gamePane.getChildren().add(this.scoreLabel);

        Scene scene=new Scene(this.gamePane,desiredWidth,desiredHeight,Color.BLACK);

        this.star=this.createStar(scene.getWidth()/2,0);
        this.gamePane.getChildren().add(this.star);

        this.colorBall=new ColorBall(scene.getWidth()/2,scene.getWidth()/2, this.player.getColor());
        this.gamePane.getChildren().add(this.colorBall);

        this.obstacle=this.createObstacle(scene.getWidth()/2,0);
        this.gamePane.getChildren().add(this.obstacle);

        this.player.setPosition(scene.getWidth()/2,4*scene.getHeight()/5);

        this.scoreLabel.setPrefWidth(-1);
        this.scoreLabel.setPrefHeight(-1);
        this.scoreLabel.setTranslateX(scene.getWidth()-this.scoreLabel.getWidth()-10);
        this.scoreLabel.setTranslateY(10);
        this.scoreLabel.setTextFill(Color.YELLOW);

        return scene;
    }

    public void start()
    {
        this.gameActive=true;
        this.applicationWindow.setScene(this.gameScene);

        this.setUserInput();
        AnimationTimer timer=new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                backgroundProcess(now);
            }
        };
        timer.start();
    }

    private void stop()
    {
        this.gameActive=false;
        //Pause Menu to be generated
    }
    private void resume() // should be called using an action listener //like clicking resume Button
    {
        this.gameActive=true;
//        this.applicationWindow.setScene(this.gameScene);
    }

    private void backgroundProcess(long now)
    {
        if(gameOver || !gameActive)
            return;
        if(GameSpace.isPlayerInteractingStar(this.player, this.star))
        {
            this.player.collectStar(this.star);
            this.gamePane.getChildren().remove(this.star);
//            this.star=null;//Error coming :) Fix it
        }
        if(GameSpace.isPlayerInteractingColorBall(this.player, this.colorBall))
        {
            this.player.changeColor(this.colorBall);
            this.gamePane.getChildren().remove(this.colorBall);
//            this.colorBall=null;//Error coming :) Fix it
        }
        if(GameSpace.isPlayerCollidingObstacle(this.player, this.obstacle))
        {
            this.gameOver=true;
            this.gamePane.getChildren().remove(this.player);
//            this.player=null;
            this.addBrokenBalls(this.player.getPosition()[0],this.player.getPosition()[1]);
            //Proceed to Exit Game
        }
        if(this.isPlayerFallenDown(this.player))
        {
            this.gameOver=true;
            this.gamePane.getChildren().remove(this.player);
//            this.player=null;
            this.addBrokenBalls(this.player.getPosition()[0],this.player.getPosition()[1]);
            //Proceed to Exit Game
        }
        this.updateGUI();
        if (lastTime==0L)
            lastTime=now;
        else
        {
            long timePast=now-lastTime;
            if(timePast>=Settings.TimeDelay)
            {
                ++this.ideallyObstacleTransformed;
                this.transformObstacle(this.obstacle);
                this.colorBall.changeColors();
                this.player.moveDown();
                lastTime=now;
            }
        }
    }

    private void addBrokenBalls(double posX,double posY)
    {
        int count=Settings.BrokenBallsCount;
        final Circle[] balls=new Circle[count];
        Random rd=new Random();
        for(int i=0;i<count;++i)
        {
            balls[i]=new Circle(posX,posY,2+rd.nextInt(3));
            balls[i].setFill(Settings.IntersectionColors[rd.nextInt(Settings.IntersectionColors.length)]);

            double oldX=balls[i].getCenterX();
            double oldY=balls[i].getCenterY();
            double newX=rd.nextDouble()*this.gameScene.getWidth();
            double newY=rd.nextDouble()*this.gameScene.getHeight();
            double transX=newX-balls[i].getCenterX();
            double transY=newY-balls[i].getCenterY();

            double tangent=transY/transX;
            double leftX=(newX<oldX)?newX:this.gameScene.getWidth()-newX;
            double leftY=(newY<oldY)?newY:this.gameScene.getHeight()-newY;

            if(leftX<leftY)
            {
                transX+=leftX*(transX<0?-1:1);
                transY=transX*tangent;
            }
            else
            {
                transY+=leftY*(transY<0?-1:1);
                transX=transY/tangent;
            }

            TranslateTransition translate=new TranslateTransition(Duration.millis(1000+rd.nextDouble()*3000),balls[i]);
            translate.setToX(transX);
            translate.setToY(transY);
            translate.play();
            final int finalI = i;
            translate.setOnFinished(finishedEvent -> this.gamePane.getChildren().remove(balls[finalI]));

            this.gamePane.getChildren().add(balls[i]);
        }
    }

    private void updateGUI()
    {
        updateScore();
    }
    private void updateScore()
    {
        this.scoreLabel.setText("Score: "+this.player.getScore());
        this.scoreLabel.setTranslateX(this.gameScene.getWidth()-this.scoreLabel.getWidth()-10);
        this.scoreLabel.setTranslateY(10);
    }

    private void setUserInput()
    {
        this.gameScene.setOnKeyPressed(keyPressedEvent ->
        {
            switch(keyPressedEvent.getCode())
            {
                case SPACE:
                {
                    if(gameActive && !gameOver)
                        this.movePlayerUp();
                    break;
                }
                case X:
                {
                    if(this.gameActive && !gameOver)
                        this.stop();
                    else // To be removed when Action Listener is created
                        this.resume();
                    break;
                }
                case TAB:
                {
                    //To return to Main Menu // just for testing purpose
                    this.application.setUpMainMenu();
                    break;
                }
                case C:
                {
                    this.applicationWindow.close();
                    break;
                }
            }
        });
    }

    private void transformObstacle(Obstacle obstacle)
    {
        if((obstacle instanceof ColorChangingObstacle) || (obstacle instanceof ColorSwappingObstacle))
        {
            if(this.ideallyObstacleTransformed % Settings.ObstacleTransformationSpeed == 0)
                this.obstacle.transform();
        }
        else
            this.obstacle.transform();
    }

    private void movePlayerUp()
    {
        double equilibriumY=3*this.gameScene.getHeight()/4;
        double[] playerPosition =this.player.getPosition();

        if(playerPosition[1]>equilibriumY)
            this.player.moveUp();
        this.star.moveDown();
        this.colorBall.moveDown();
        this.obstacle.moveDown();
    }

    private static boolean isPlayerInteractingStar(Player player, Star star)
    {
        return !Shape.intersect(player, star).getBoundsInLocal().isEmpty();
    }

    private static boolean isPlayerInteractingColorBall(Player player, ColorBall colorBall)
    {
        for (Node colorBallParts : colorBall.getChildren().toArray(new Node[0]))
        {
            if (!Shape.intersect(player, (Shape)colorBallParts).getBoundsInLocal().isEmpty())
                return true;
        }

        return false;
    }

    private static boolean isPlayerCollidingObstacle(Player player, Obstacle obstacle)
    {
        for (Node obstacleParts : obstacle.getChildren().toArray(new Node[0]))
        {
            if (!Shape.intersect(player, (Shape)obstacleParts).getBoundsInLocal().isEmpty())
            {
                Color obstacleColor=(Color)((Shape)obstacleParts).getStroke();
                return !obstacleColor.equals(player.getColor());
            }
        }

        return false;
    }

    private boolean isPlayerFallenDown(Player player)
    {
        return player.getPosition()[1]>this.gameScene.getHeight();
    }

    private Star createStar(double xPosition, double yPosition)
    {
        Random rd=new Random();
        Star star;
        if(rd.nextDouble()<0.85)
            star=new Star(xPosition,yPosition);
        else
            star=new SpecialStar(xPosition, yPosition);
        return star;
    }

    private Obstacle createObstacle(double xCenter, double yCenter)
    {
        Random rd=new Random();

        Obstacle obstacle;
        switch(rd.nextInt(Settings.ObstaclesCount))
        {
            // To add new obstacles, also update Settings.ObstaclesCount

            case 0 : obstacle = new CircularRotatingObstacle(xCenter, yCenter); break;
            case 1 : obstacle = new SquareRotatingObstacle(xCenter, yCenter); break;
            case 2 : obstacle = new CircularColorChangingObstacle(xCenter, yCenter); break;
            case 3 : obstacle = new LineColorSwappingObstacle(xCenter, yCenter); break;
            case 4 : obstacle = new SquareColorSwappingObstacle(xCenter, yCenter); break;
            default : obstacle = null;
        }
        return obstacle;
    }

}