
package colorswitch;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;

import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;

import javafx.application.Application;

public class GameSpace extends Application
{
	private static Stage applicationWindow;
	private static Scene gameScene;
	private static Pane gamePane;
	private static Label scoreLabel;

	private static Player player;
	private static Star star;
	private static Obstacle obstacle;
	
	private static boolean gameActive;
	private static long lastTime;
	private static long score;

	static
	{
		player=new Player();
		star=new Star();
		obstacle=null;
		gameActive=false;
		lastTime=0;
		score=0;
		scoreLabel=new Label("Score: 0");

	}

	public GameSpace()
	{
		this.player=new Player();
		this.gameActive=false;
		this.applicationWindow=null;
		this.gameScene=null;
	}
	public GameSpace(Stage window, double desiredWidth, double desiredHeight)
	{
		this.player=new Player();
		this.gameActive=false;
		this.applicationWindow=window;
		this.gameScene=this.createScene(desiredWidth,desiredHeight);
	}

	private static Scene createScene(double desiredWidth, double desiredHeight)
	{
		GameSpace.gamePane=new Pane();
		GameSpace.gamePane.setBackground(Background.EMPTY);
		GameSpace.gamePane.getChildren().add(GameSpace.player);
		GameSpace.gamePane.getChildren().add(GameSpace.star);
		GameSpace.gamePane.getChildren().add(GameSpace.scoreLabel);
		
		Scene scene=new Scene(GameSpace.gamePane,desiredWidth,desiredHeight,Color.BLACK);
		
		GameSpace.obstacle=new CircularRotatingObstacle(scene.getWidth()/2,0);
		GameSpace.obstacle.startTransformation();
		GameSpace.gamePane.getChildren().add(GameSpace.obstacle);

		GameSpace.player.setPosition(scene.getWidth()/2,scene.getHeight()-GameSpace.player.getSize());
		
		GameSpace.scoreLabel.setPrefWidth(-1);
		GameSpace.scoreLabel.setPrefHeight(-1);
		GameSpace.scoreLabel.setTranslateX(scene.getWidth()-GameSpace.scoreLabel.getWidth()-10);
		GameSpace.scoreLabel.setTranslateY(10);
		GameSpace.scoreLabel.setTextFill(Color.YELLOW);

		GameSpace.star.setPosition(scene.getWidth()/2,0);

		return scene;
	}

	@Override
	public void start(Stage primaryStage)
	{
		GameSpace.gameActive=true;
		GameSpace.applicationWindow=primaryStage;
		GameSpace.gameScene=GameSpace.createScene(500,650);
		GameSpace.applicationWindow.setScene(GameSpace.gameScene);
		
		GameSpace.setUserInput();
		AnimationTimer timer=new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				GameSpace.backgroundProcess(now);
			}
		};
		timer.start();

		GameSpace.applicationWindow.hide();
		GameSpace.applicationWindow.show();

	}

	private static void backgroundProcess(long now)
	{
		if(!gameActive)
			return;
		if(GameSpace.isPlayerInteractingStar(GameSpace.player,GameSpace.star))
		{
			++GameSpace.score;
			GameSpace.gamePane.getChildren().remove(GameSpace.star);
			GameSpace.star=null;//Error coming :) Fix it
		}
		GameSpace.updateGUI();
		if (lastTime==0L)
			lastTime=now;
		else
		{
			long timePast=now-lastTime;
			if(timePast>=400_000_000L) //400 ms
			{ 
				GameSpace.player.moveDown();
				GameSpace.obstacle.transform();
				lastTime=now;
			}
		}
	}
	private static void updateGUI()
	{
		updateScore();
	}
	private static void updateScore()
	{
		GameSpace.scoreLabel.setText("Score: "+GameSpace.score);
		GameSpace.scoreLabel.setTranslateX(GameSpace.gameScene.getWidth()-GameSpace.scoreLabel.getWidth()-10);
		GameSpace.scoreLabel.setTranslateY(10);
	}

	private static void setUserInput()
	{
		GameSpace.gameScene.setOnKeyPressed(keyPressedEvent -> 
		{
			switch(keyPressedEvent.getCode())
			{
				case SPACE:
				{
					if(gameActive)
						GameSpace.movePlayerUp();
					break;
				}
				case TAB:
				{
					GameSpace.gameActive=!GameSpace.gameActive;
					if(!GameSpace.gameActive)
						GameSpace.pauseAnimations();
					else
						GameSpace.resumeAnimations();
					break;
				}
				case Q:
				{
					GameSpace.applicationWindow.close();
					break;
				}
			}
		});
	}

	private static void pauseAnimations()
	{
		GameSpace.obstacle.stopTransformation();
	}
	private static void resumeAnimations()
	{
		GameSpace.obstacle.startTransformation();
	}
	
	private static void movePlayerUp()
	{
		double equilibriumY=3*GameSpace.gameScene.getHeight()/4;
		double playerPosition[]=GameSpace.player.getPosition();

		if(playerPosition[1]>equilibriumY)
			GameSpace.player.moveUp();
		GameSpace.star.moveDown();
		GameSpace.obstacle.moveDown();
	}

	private static boolean isPlayerInteractingStar(Player player, Star star)
	{
		if(Shape.intersect(player,star).getBoundsInLocal().isEmpty()==false)
			return true;
		else
			return false;
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}