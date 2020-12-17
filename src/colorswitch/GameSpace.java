
package colorswitch;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.stage.StageStyle;
import javafx.stage.Modality;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;

import javafx.geometry.Pos;

public class GameSpace implements Serializable
{
    private transient ColorSwitch application;

    private transient Stage applicationWindow;
    private transient Scene gameScene;
    private transient Pane gamePane;
    private transient Label scoreLabel;

    private Player player;
    private final Queue<Star> stars;
    private final Queue<Obstacle> obstacles;
    private final Queue<ColorBall> colorBalls;

    private boolean touched;
    private boolean gameActive;
    private boolean gameOver;
    private long lastTime;

    private long ideallyObstacleTransformed;

    public GameSpace(ColorSwitch sourceApplication, Stage window, double desiredWidth, double desiredHeight)
    {
        this.gameActive=false;
        this.gameOver=false;
        this.ideallyObstacleTransformed=0;
        this.lastTime=0;
        this.obstacles=new LinkedList<>();
        this.stars=new LinkedList<>();
        this.colorBalls=new LinkedList<>();
        this.gamePane=this.createGamePane();
        this.gameScene=this.createScene(desiredWidth,desiredHeight,this.gamePane);
        this.initializePlayer();
        this.addInitialObstacles();
        this.addInitialStars();
        this.addInitialColorBalls();

        this.construct(sourceApplication,window,desiredWidth,desiredHeight);
    }

    public void construct(ColorSwitch sourceApplication, Stage window, double desiredWidth, double desiredHeight)
    {
        this.application=sourceApplication;
        this.applicationWindow=window;

        this.touched=false;

        if(this.gameScene==null)
        {
            this.gamePane=this.createGamePane();
            this.gameScene=this.createScene(desiredWidth,desiredHeight,this.gamePane);
        }

        this.initializeScoreLabel();
        this.addLabelToPane(this.scoreLabel);

        this.restoreProperties();

        this.addPlayerToPane(this.player);
        this.addObstaclesToPane();
        this.addStarsToPane();
        this.addColorBallsToPane();
    }

    public int getScore()
    {
        return this.player.getScore();
    }
    public int getStarsCollected()
    {
        return this.player.getStarsCollected();
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

    private void restoreProperties()
    {
        this.player.construct();
        for(Obstacle obstacle : this.obstacles)
            obstacle.construct();
        for(Star star : this.stars)
            star.construct();
        for(ColorBall colorBall : this.colorBalls)
            colorBall.construct();
    }
    private void updateProperties()
    {
        this.player.updateProperties();
        for(Obstacle obstacle : this.obstacles)
            obstacle.updateProperties();
        for(Star star : this.stars)
            star.updateProperties();
        for(ColorBall colorBall : this.colorBalls)
            colorBall.updateProperties();
    }

    private void setUserInput()
    {
        this.gameScene.setOnKeyPressed(keyPressedEvent ->
        {
            switch(keyPressedEvent.getCode())
            {
                case SPACE:
                {
                    if(!this.touched)
                        this.touched=true;
                    if(this.gameActive && !this.gameOver)
                        this.movePlayerUp();
                    break;
                }
                case Z:
                {
                    if(this.gameActive && !gameOver)
                        this.pause();
                    break;
                }
                case C:
                {
                    this.pause(); //Till the time proper action is taken on gameOver
                }
            }
        });
    }

    private void pause()
    {
        this.gameActive=false;
        gamePane.setEffect(new GaussianBlur());

        VBox pauseRoot = new VBox();
        pauseRoot.setSpacing(10);
        pauseRoot.setAlignment(Pos.CENTER);
        pauseRoot.setPrefWidth(250);
        pauseRoot.setPrefHeight(50);
        //pauseRoot.setPadding(new Insets(100));

        Label Tittle= new Label("PAUSED");
        Label dummy= new Label("");
        dummy.setFont(Font.font("Arial", FontWeight.BOLD,30));
        Label dummy2= new Label("");
        dummy2.setFont(Font.font("Arial", FontWeight.BOLD,30));
        Tittle.setTextFill(Color.ALICEBLUE);
        Tittle.setFont(Font.font("Arial", FontWeight.BOLD,60));

        Button resume = new Button("Resume");
        resume.setStyle("-fx-background-color: #ff4500");
        resume.setFont(Font.font("Arial", FontWeight.BOLD,30));
        resume.setMinWidth(160);
        Button Save= new Button("Save");
        Save.setStyle("-fx-background-color: #ff4500");
        Save.setFont(Font.font("Arial", FontWeight.BOLD,30));
        Save.setMinWidth(160);
        Button Exit= new Button("Exit");
        Exit.setStyle("-fx-background-color: #ff4500");
        Exit.setFont(Font.font("Arial", FontWeight.BOLD,30));
        Exit.setMinWidth(160);

        pauseRoot.getChildren().addAll(dummy,Tittle,dummy2, resume, Save, Exit);
        pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);");

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(applicationWindow);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(pauseRoot,Color.TRANSPARENT));

        resume.setOnAction(event -> {
            gamePane.setEffect(null);
            popupStage.hide();
            this.touched=false;
            this.resume();
        });
        Save.setOnAction(event -> {
            this.updateProperties();
            String savedName=application.saveGame();
        });
        Exit.setOnAction(event -> {
            gamePane.setEffect(null);
            popupStage.hide();
            //ColorSwitch::gameOver needs to be called
            application.setUpMainMenu();
        });

        popupStage.show();
    }
    private void resume() // should be called using an action listener //like clicking resume Button
    {
        this.gameActive=true;
//        this.applicationWindow.setScene(this.gameScene);
    }

    private void backgroundProcess(long now)
    {
        if(this.gameOver || !this.gameActive)
            return;
//        else if(this.gameOver)
//        {
//            //ColorSwitch::gamOver needs to be called
              //Remove this.gameOver from the previous if, if this else if is implemented
//        }
        if(this.isPlayerInteractingStar(this.player, this.stars.peek()))
        {
            this.player.collectStar(this.stars.peek());
            this.gamePane.getChildren().remove(this.stars.peek());
            this.stars.remove();
            this.addNewStarToPane();
        }
        if(this.isPlayerInteractingColorBall(this.player, this.colorBalls.peek()))
        {
            this.player.changeColor(this.colorBalls.peek());
            this.gamePane.getChildren().remove(this.colorBalls.peek());
            this.colorBalls.remove();
            this.addNewColorBallToPane();
            this.colorBalls.peek().setColor(this.player.getColor());
        }
        if(this.isPlayerCollidingObstacles(this.player))
        {
            this.gameOver=true;
            this.gamePane.getChildren().remove(this.player);
            this.addBrokenBallsWithAnimationToPane(this.player.getPosition()[0],this.player.getPosition()[1]);
            //Proceed to Exit Game
        }
        if(this.isPlayerFallenDown(this.player))
        {
            this.gameOver=true;
            this.gamePane.getChildren().remove(this.player);
            this.addBrokenBallsWithAnimationToPane(this.player.getPosition()[0],this.player.getPosition()[1]);
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
                if(this.touched)
                    this.player.moveDown();
                lastTime=now;
            }
        }
    }

    private void updateGUI()
    {
        this.updateScore();
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
            this.addNewObstacleToPane();
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

    private boolean isPlayerInteractingStar(Player player, Star star)
    {
        return !Shape.intersect(player, star).getBoundsInLocal().isEmpty();
    }
    private boolean isPlayerInteractingColorBall(Player player, ColorBall colorBall)
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

    private double[] getNextObstaclePosition()
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
        return position;
    }
    private double[] getNextStarPosition()
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
        return position;
    }
    private double[] getNextColorBallPosition()
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
        return position;
    }

    private Pane createGamePane()
    {
        Pane pane = new Pane();
        pane.setBackground(Background.EMPTY);
        return pane;
    }
    private Scene createScene(double desiredWidth,double desiredHeight,Pane pane)
    {
        return new Scene(pane,desiredWidth,desiredHeight,Color.BLACK);
    }
    private Label createScoreLabel(double xPosition, double yPosition)
    {
        Label label=new Label();
        label.setText("Score: "+this.getScore());
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
            case 5 : obstacle = new PlusRotatingObstacle(xCenter, yCenter); break;
            default : obstacle = null;
        }
        return obstacle;
    }

    private void addInitialObstacles()
    {
        for(int i=0;i<Settings.MinimumEntitiesCount;++i)
            this.addNewObstacle();
    }
    private void addInitialStars()
    {
        for(int i=0;i<Settings.MinimumEntitiesCount;++i)
            this.addNewStar();
    }
    private void addInitialColorBalls()
    {
        for(int i=0;i<Settings.MinimumEntitiesCount;++i)
            this.addNewColorBall();
        this.colorBalls.peek().setColor(this.player.getColor()); //Not really worth to set other color-balls color
    }

    private void addObstaclesToPane()
    {
        for(Obstacle obstacle : this.obstacles)
            this.addObstacleToPane(obstacle);
    }
    private void addStarsToPane()
    {
        for(Star star : this.stars)
            this.addStarToPane(star);
    }
    private void addColorBallsToPane()
    {
        for(ColorBall colorBall : this.colorBalls)
            this.addColorBallToPane(colorBall);
    }

    private void initializeScoreLabel()
    {
        this.scoreLabel=this.createScoreLabel(this.gameScene.getWidth(), 10);
    }
    private void initializePlayer()
    {
        this.player=this.createPlayer(this.gameScene.getWidth()/2, 9*this.gameScene.getHeight()/10);
    }
    private Obstacle addNewObstacle()
    {
        double[] position=this.getNextObstaclePosition();
        Obstacle obstacle=this.createObstacle(position[0],position[1]);
        this.obstacles.add(obstacle);
        return obstacle;
    }
    private Star addNewStar()
    {
        double[] position=this.getNextStarPosition();
        Star star=this.createStar(position[0],position[1]);
        this.stars.add(star);
        return star;
    }
    private ColorBall addNewColorBall()
    {
        double[] position=this.getNextColorBallPosition();
        ColorBall colorBall=this.createColorBall(position[0],position[1]);
        this.colorBalls.add(colorBall);
        return colorBall;
    }

    private void addNewObstacleToPane()
    {
        Obstacle obstacle=this.addNewObstacle();
        this.addObstacleToPane(obstacle);
    }
    private void addNewStarToPane()
    {
        Star star=this.addNewStar();
        this.addStarToPane(star);
    }
    private void addNewColorBallToPane()
    {
        ColorBall colorBall=this.addNewColorBall();
        this.addColorBallToPane(colorBall);
    }

    private void addLabelToPane(Label label)
    {
        this.gamePane.getChildren().add(label);
    }
    private void addPlayerToPane(Player player)
    {
        this.gamePane.getChildren().add(player);
    }
    private void addObstacleToPane(Obstacle obstacle)
    {
        this.gamePane.getChildren().add(obstacle);
    }
    private void addStarToPane(Star star)
    {
        this.gamePane.getChildren().add(star);
    }
    private void addColorBallToPane(ColorBall colorBall)
    {
        this.gamePane.getChildren().add(colorBall);
    }

    private void addBrokenBallsWithAnimationToPane(double xPosition, double yPosition)
    {
        int count=Settings.BrokenBallsCount;
        double[] bounds={this.gameScene.getWidth(),this.gameScene.getHeight()};
        Random rd=new Random();
		for(int i=0;i<count;++i)
			new RandomMotionBall(xPosition, yPosition,2+rd.nextInt(3),bounds,this.gamePane);
    }
}