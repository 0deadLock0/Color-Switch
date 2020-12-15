
package colorswitch;

import java.util.Iterator;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;

import javafx.scene.shape.Shape;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import javafx.animation.AnimationTimer;

public class GameSpace
{
    private final ColorSwitch application;

    private final Stage applicationWindow;
    private final Scene gameScene;
    private Pane gamePane;
    private Label scoreLabel;

    private Player player;
    private final Queue<Star> stars;
    private final Queue<Obstacle> obstacles;
    private final Queue<ColorBall> colorBalls;

    private boolean gameActive;
    private boolean gameOver;
    private long lastTime;

    private long ideallyObstacleTransformed;

    public GameSpace(ColorSwitch sourceApplication, Stage window, double desiredWidth, double desiredHeight)
    {
        this.application=sourceApplication;
        this.applicationWindow=window;
        this.gameActive=false;
        this.gameOver=false;
        this.ideallyObstacleTransformed=0;
        this.lastTime=0;
        this.obstacles=new LinkedList<>();
        this.stars=new LinkedList<>();
        this.colorBalls=new LinkedList<>();
        this.gameScene=this.createScene(desiredWidth,desiredHeight);
        this.addObstacles();
        this.addStars();
        this.addColorBalls();
    }

    public int getScore()
    {
        return this.player.getScore();
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
        if(GameSpace.isPlayerInteractingStar(this.player, this.stars.peek()))
        {
            this.player.collectStar(this.stars.peek());
            this.gamePane.getChildren().remove(this.stars.peek());
            this.stars.remove();
            this.addStar();
        }
        if(GameSpace.isPlayerInteractingColorBall(this.player, this.colorBalls.peek()))
        {
            this.player.changeColor(this.colorBalls.peek());
            this.gamePane.getChildren().remove(this.colorBalls.peek());
            this.colorBalls.remove();
            this.addColorBall();
            this.colorBalls.peek().setColor(this.player.getColor());
        }
        if(this.isPlayerCollidingObstacles(this.player))
        {
            this.gameOver=true;
            this.gamePane.getChildren().remove(this.player);
            this.addBrokenBallsWithAnimation(this.player.getPosition()[0],this.player.getPosition()[1]);
            //Proceed to Exit Game
        }
        if(this.isPlayerFallenDown(this.player))
        {
            this.gameOver=true;
            this.gamePane.getChildren().remove(this.player);
            this.addBrokenBallsWithAnimation(this.player.getPosition()[0],this.player.getPosition()[1]);
            //Proceed to Exit Game
        }
        
        this.updateObstacles();
        this.updateGUI();
        if (lastTime==0L)
            lastTime=now;
        else
        {
            long timePast=now-lastTime;
            if(timePast>=Settings.TimeDelay)
            {
                ++this.ideallyObstacleTransformed;
                this.transformObstacles();
                this.transformColorBalls();
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
        this.scoreLabel.setText("Score: "+this.getScore());
        this.scoreLabel.setTranslateX(this.gameScene.getWidth()-this.scoreLabel.getWidth()-10);
        this.scoreLabel.setTranslateY(10);
    }
    
    private void updateObstacles()
    {
        int count=0;
        for(Iterator<Obstacle> itr=this.obstacles.iterator();itr.hasNext();)
        {
            Obstacle obstacle=itr.next();
            if(this.isObstacleOutOfScope(obstacle))
            {
                this.gamePane.getChildren().remove(obstacle);
                itr.remove();
                ++count;
            }
            else
                break;
        }

        while(--count>=0)
            this.addObstacle();
    }

    private void transformObstacles()
    {
        for(Obstacle obstacle : this.obstacles)
            this.transformObstacle(obstacle);
    }
    private void transformObstacle(Obstacle obstacle)
    {
        if((obstacle instanceof ColorChangingObstacle) || (obstacle instanceof ColorSwappingObstacle))
        {
            if(this.ideallyObstacleTransformed % Settings.ObstacleTransformationSpeed == 0)
                obstacle.transform();
        }
        else
            obstacle.transform();
    }

    private void transformColorBalls()
    {
        for(ColorBall colorBall : this.colorBalls)
            colorBall.changeColors();
    }

    private void movePlayerUp()
    {
        double equilibriumY=3*this.gameScene.getHeight()/4;
        double[] playerPosition =this.player.getPosition();

        if(playerPosition[1]>equilibriumY)
            this.player.moveUp();
        this.moveObstaclesDown();
        this.moveStarsDown();
        this.moveColorBallsDown();
    }

    private void moveObstaclesDown()
    {
        for(Obstacle obstacle : this.obstacles)
            obstacle.moveDown();
    }
    private void moveStarsDown()
    {
        for(Star star : this.stars)
            star.moveDown();
    }
    private void moveColorBallsDown()
    {
        for(ColorBall colorBall : this.colorBalls)
            colorBall.moveDown();
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

    private boolean isPlayerCollidingObstacles(Player player)
    {
        for(Obstacle obstacle : this.obstacles)
        {
            for (Node obstacleParts : obstacle.getChildren().toArray(new Node[0]))
            {
                if (!Shape.intersect(player, (Shape) obstacleParts).getBoundsInLocal().isEmpty())
                {
                    Color obstacleColor = (Color) ((Shape) obstacleParts).getStroke();
                    return !obstacleColor.equals(player.getColor());
                }
            }
        }
        return false;
    }

    private boolean isObstacleOutOfScope(Obstacle obstacle)
    {
        return obstacle.getTopPoint()>this.gameScene.getHeight();
    }

    private boolean isPlayerFallenDown(Player player)
    {
        return player.getPosition()[1]>this.gameScene.getHeight();
    }

    private Scene createScene(double desiredWidth, double desiredHeight)
    {
        this.gamePane=new Pane();
        this.gamePane.setBackground(Background.EMPTY);

        Scene scene=new Scene(this.gamePane,desiredWidth,desiredHeight,Color.BLACK);

        this.scoreLabel=this.createScoreLabel(scene.getWidth(), 10);
        this.gamePane.getChildren().add(this.scoreLabel);

        this.player=this.createPlayer(scene.getWidth()/2, 4*scene.getHeight()/5);
        this.gamePane.getChildren().add(this.player);

        return scene;
    }

    private Label createScoreLabel(double xPosition, double yPosition)
    {
        Label label=new Label("Score: 0");
        label.setPrefWidth(-1);
        label.setPrefHeight(-1);
        label.setTranslateX(xPosition-label.getWidth()-10);
        label.setTranslateY(yPosition);
        label.setTextFill(Color.YELLOW);
        return label;
    }

    private Player createPlayer(double xPosition, double yPosition)
    {
        return new Player(xPosition, yPosition);
    }

    private ColorBall createColorBall(double xPosition, double yPosition)
    {
        return new ColorBall(xPosition, yPosition);
    }

    private Star createStar(double xPosition, double yPosition)
    {
        Random rd=new Random();
        Star star;
        if(rd.nextDouble()<0.9)
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

    private void addObstacle()
    {
        int count=0;
        int size=this.obstacles.size();
        double[] position={this.gameScene.getWidth()/2,this.gameScene.getHeight()/2};
        for(Obstacle obstacle : this.obstacles)
        {
            ++count;
            if(count==size)
                position[1]=obstacle.getCentrePosition()[1]-Settings.EntitiesGap;
        }
        Obstacle obstacle=this.createObstacle(position[0],position[1]);
        this.obstacles.add(obstacle);
        this.gamePane.getChildren().add(obstacle);
    }
    private void addObstacles()
    {
        for(int i=0;i<Settings.MinimumEntitiesCount;++i)
            this.addObstacle();
    }
    private void addStar()
    {
        int count=0;
        int size=this.stars.size();
        double[] position={this.gameScene.getWidth()/2,this.gameScene.getHeight()/2};
        for(Star star : this.stars)
        {
            ++count;
            if(count==size)
                position[1]=star.getPosition()[1]-Settings.EntitiesGap;
        }
        Star star=this.createStar(position[0],position[1]);
        this.stars.add(star);
        this.gamePane.getChildren().add(star);
    }
    private void addStars()
    {
        for(int i=0;i<Settings.MinimumEntitiesCount;++i)
            this.addStar();
    }
    private void addColorBall()
    {
        int count=0;
        int size=this.colorBalls.size();
        double[] position={this.gameScene.getWidth()/2,this.gameScene.getHeight()/2-Settings.EntitiesGap/2};
        for(ColorBall colorBall : this.colorBalls)
        {
            ++count;
            if(count==size)
                position[1]=colorBall.getPosition()[1]-Settings.EntitiesGap;
        }
        ColorBall colorBall=this.createColorBall(position[0],position[1]);
        this.colorBalls.add(colorBall);
        this.gamePane.getChildren().add(colorBall);
    }
    private void addColorBalls()
    {
        for(int i=0;i<Settings.MinimumEntitiesCount;++i)
            this.addColorBall();
        this.colorBalls.peek().setColor(this.player.getColor()); //Not really worth to set other color-balls color
    }

    private void addBrokenBallsWithAnimation(double xPosition, double yPosition)
    {
        int count=Settings.BrokenBallsCount;
        double[] bounds={this.gameScene.getWidth(),this.gameScene.getHeight()};
        Random rd=new Random();
		for(int i=0;i<count;++i)
			new RandomMotionBall(xPosition, yPosition,2+rd.nextInt(3),bounds,this.gamePane);
    }
}