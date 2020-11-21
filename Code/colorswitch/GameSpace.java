
package colorswitch;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.geometry.Pos;
import javafx.scene.paint.Color;

class GameSpace
{
	private Player player;
	private boolean gameActive;
	private Stage applicationWindow;
	private Scene gameScene;

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
		this.applicationWindow.setScene(gameScene);
	}

	public int start()
	{
		return 0; //for quiting game
	}

	private Scene createScene(double desiredWidth, double desiredHeight)
	{
		Pane pane=new Pane();
		pane.getChildren().add(this.player);
		Scene scene=new Scene(pane,desiredWidth,desiredHeight,Color.BLACK);

		this.player.setPosition(scene.getWidth()/2,scene.getHeight()-this.player.getSize());

		return scene;
	}
}