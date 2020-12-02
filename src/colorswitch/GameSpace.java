
package colorswitch;

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
    private final Stage applicationWindow;
    private final Scene gameScene;
    private Pane gamePane;
    private final Label scoreLabel;

    private final Player player;
    private Star star;
    private Obstacle obstacle;

    private boolean gameActive;
    private long lastTime;
    private long score;

    public GameSpace() //No use, but program throws error without no args constructor
    {
        this.applicationWindow=null;
        this.player=null;
        this.star=null;
        this.obstacle=null;
        this.gameActive=false;
        this.lastTime=-1;
        this.score=-1;
        this.scoreLabel=null;
        this.gameScene=null;
    }

    public GameSpace(Stage window, double desiredWidth, double desiredHeight)
    {
        this.applicationWindow=window;
        this.player=new Player();
        this.star=new Star();
        this.obstacle=null; //different types of obstacles exists
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
        this.gamePane.getChildren().add(this.star);
        this.gamePane.getChildren().add(this.scoreLabel);

        Scene scene=new Scene(this.gamePane,desiredWidth,desiredHeight,Color.BLACK);

        this.obstacle=new CircularRotatingObstacle(scene.getWidth()/2,0);
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

    public int start()
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

        return 0;
    }

    private void backgroundProcess(long now)
    {
        if(!gameActive)
            return;
        if(this.isPlayerInteractingStar(this.player,this.star))
        {
            ++this.score;
            this.gamePane.getChildren().remove(this.star);
            this.star=null;//Error coming :) Fix it
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
                case TAB:
                {
                    this.gameActive=!this.gameActive;
                    break;
                }
                case Q:
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
        this.obstacle.moveDown();
    }

    private boolean isPlayerInteractingStar(Player player, Star star)
    {
        return !Shape.intersect(player, star).getBoundsInLocal().isEmpty();
    }

}