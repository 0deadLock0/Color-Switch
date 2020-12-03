
package colorswitch;

import java.util.Random;

import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;

import javafx.scene.paint.Color;
import javafx.scene.layout.Background;

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
    private long lastTime;
    private long score;

    public GameSpace(ColorSwitch sourceApplication, Stage window, double desiredWidth, double desiredHeight)
    {
        this.application=sourceApplication;
        this.applicationWindow=window;
        this.player=new Player();
        this.star=null;
        this.obstacle=null; //different types of obstacles exists
        this.colorBall=null;
        this.gameActive=false;
        this.lastTime=0;
        this.score=0;
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

        this.star=new Star();
        this.gamePane.getChildren().add(this.star);

        this.colorBall=new ColorBall(scene.getWidth()/2,scene.getWidth()/2, this.player.getColor());
        this.gamePane.getChildren().add(this.colorBall);

        this.obstacle=this.createObstacle(scene.getWidth()/2,0);
        this.gamePane.getChildren().add(this.obstacle);

        this.player.setPosition(scene.getWidth()/2,scene.getHeight()-this.player.getSize());

        this.scoreLabel.setPrefWidth(-1);
        this.scoreLabel.setPrefHeight(-1);
        this.scoreLabel.setTranslateX(scene.getWidth()-this.scoreLabel.getWidth()-10);
        this.scoreLabel.setTranslateY(10);
        this.scoreLabel.setTextFill(Color.YELLOW);

        this.star.translatePosition(scene.getWidth()/2,0);

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
        if(!gameActive)
            return;
        if(GameSpace.isPlayerInteractingStar(this.player, this.star))
        {
            ++this.score;
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
//            System.out.println("Collision Detected");
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
                this.obstacle.transform();
                this.colorBall.changeColors();
                this.player.moveDown();
                lastTime=now;
            }
        }
    }

    private void updateGUI()
    {
        updateScore();
    }
    private void updateScore()
    {
        this.scoreLabel.setText("Score: "+this.score);
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
                    if(gameActive)
                        this.movePlayerUp();
                    break;
                }
                case X:
                {
                    if(this.gameActive)
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

    private Obstacle createObstacle(double xCenter, double yCenter)
    {
        Random rd=new Random();

        Obstacle obstacle;
        switch(rd.nextInt(Settings.ObstaclesCount))
        {
            // To add new obstacles, also update Settings.ObstaclesCount

            case 0 : obstacle = new CircularRotatingObstacle(xCenter, yCenter); break;
            case 1 : obstacle = new SquareRotatingObstacle(xCenter, yCenter); break;
            default : obstacle = null;
        }
        return obstacle;
    }

}