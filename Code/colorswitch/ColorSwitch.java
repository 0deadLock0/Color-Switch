
package colorswitch;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javafx.geometry.Pos;
import javafx.scene.paint.Color;

public class ColorSwitch extends Application
{
	private static Stage applicationWindow;
	
	private static void setUpWindow()
	{
		ColorSwitch.applicationWindow.setTitle("Color Switch");
		ColorSwitch.applicationWindow.setHeight(700);
		ColorSwitch.applicationWindow.setWidth(500);
		ColorSwitch.applicationWindow.setResizable(false);
	}

	private static void setUpMainMenu()
	{
		VBox menuOptions=new VBox();

		menuOptions.setSpacing(10);
		menuOptions.setAlignment(Pos.CENTER);
		menuOptions.setPrefWidth(100);
		menuOptions.setPrefHeight(50);

		Button newGameButton=new Button("NEW GAME");

		Button loadGameButton=new Button("LOAD GAME");

		Button statsButton=new Button("STATS");

		Button helpButton=new Button("HELP");

		Button creditsButton=new Button("CREDITS");
		
		Button exitButton=new Button("EXIT");
		exitButton.setOnAction(exitEvent -> ColorSwitch.closeProgram());

		Button[] menuButtons={newGameButton,loadGameButton,statsButton,helpButton,creditsButton,exitButton};
		
		for(Button button : menuButtons)
		{
			button.setMinWidth(menuOptions.getPrefWidth());
			button.setMinHeight(menuOptions.getPrefHeight());
		}

		menuOptions.getChildren().addAll(newGameButton,loadGameButton,statsButton,helpButton,creditsButton,exitButton);
	
		Scene mainMenu=new Scene(menuOptions);
		ColorSwitch.applicationWindow.setScene(mainMenu);
	}

	private static void closeProgram()
	{
		ColorSwitch.applicationWindow.close();
		System.out.println("Close Program in action");
	}

	@Override
	public void start(Stage primaryStage)
	{
		ColorSwitch.applicationWindow=primaryStage;

		ColorSwitch.setUpWindow();
		ColorSwitch.applicationWindow.setOnCloseRequest(closeEvent -> {
			closeEvent.consume();
			ColorSwitch.closeProgram();
		});

		ColorSwitch.setUpMainMenu();

		ColorSwitch.applicationWindow.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}