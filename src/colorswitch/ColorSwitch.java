package colorswitch;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ColorSwitch extends Application
{
    private static final double desiredSceneWidth;
    private static final double desiredSceneHeight;

    static
    {
        desiredSceneWidth=500;
        desiredSceneHeight=650;
    }

    private static Stage applicationWindow;
    private static GameSpace currentGame;

    private static void setUpWindow()
    {
        ColorSwitch.applicationWindow.setTitle("Color Switch");
        ColorSwitch.applicationWindow.setResizable(false);
    }

    private static void setUpMainMenu()
    {
        VBox menuOptions=new VBox();
        HBox title=new HBox();
        title.setSpacing(10);
        title.setAlignment(Pos.CENTER);
        title.setPrefWidth(25);
        title.setPrefHeight(25);

        menuOptions.setSpacing(10);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPrefWidth(100);
        menuOptions.setPrefHeight(50);

        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream("resources/Images/ColorSwitch.jpeg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(50);
        imageView.setY(25);

        //setting the fit height and width of the image view
        imageView.setFitHeight(100);
        imageView.setFitWidth(400);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);


        Button newGameButton=new Button("NEW GAME");
        //vghvhg
        newGameButton.setStyle("-fx-font-weight: bold");
        newGameButton.setStyle("-fx-background-color: #AB4642");

        newGameButton.setOnAction(newGameEvent -> ColorSwitch.startNewGame());

        Button loadGameButton=new Button("LOAD GAME");
        loadGameButton.setStyle("-fx-background-color: #00bfff");
        loadGameButton.setOnAction(loadGameEvent -> ColorSwitch.setUpLoadMenu());

        Button statsButton=new Button("STATS");
        statsButton.setStyle("-fx-background-color: #e9967a");
        statsButton.setOnAction(statsevent -> setUpStatsMenu());

        Button helpButton=new Button("HELP");
        helpButton.setStyle("-fx-background-color: #00fa9a");
        helpButton.setOnAction(helpevent -> setUpHelpMenu());

        Button creditsButton=new Button("CREDITS");
        creditsButton.setStyle("-fx-background-color: #F7CA88");
        creditsButton.setOnAction(creditsevent -> ColorSwitch.setUpCreditsMenu());

        Button exitButton=new Button("EXIT");
        exitButton.setStyle("-fx-background-color: #f08080");
        exitButton.setOnAction(exitEvent -> ColorSwitch.closeProgram());

        Button[] menuButtons={newGameButton,loadGameButton,statsButton,helpButton,creditsButton,exitButton};

        for(Button button : menuButtons)
        {
            button.setMinWidth(menuOptions.getPrefWidth());
            button.setMinHeight(menuOptions.getPrefHeight());
        }
        title.getChildren().add(imageView);
        menuOptions.getChildren().addAll(title,newGameButton,loadGameButton,statsButton,helpButton,creditsButton,exitButton);
        Scene mainMenu=new Scene(menuOptions,ColorSwitch.desiredSceneWidth,ColorSwitch.desiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("a.css").toExternalForm());



        ColorSwitch.applicationWindow.setScene(mainMenu);
    }
    private static void setUpLoadMenu()
    {
        VBox menuOptions=new VBox();

        menuOptions.setSpacing(10);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPrefWidth(100);
        menuOptions.setPrefHeight(50);



        Button File1=new Button("Saved state 1");
        //vghvhg

        File1.setStyle("-fx-background-color: #AB4642");

        //File1.setOnAction(newGameEvent -> ColorSwitch.startNewGame());

        Button File2=new Button("Saved State 2");
        File2.setStyle("-fx-background-color: #00bfff");

        Button File3 =new Button("Saved State 3");
        File3.setStyle("-fx-background-color: #e9967a");

        Button File4=new Button("Saved State 4");
        File4.setStyle("-fx-background-color: #00fa9a");

        Button File5=new Button("Saved State 5");
        File5.setStyle("-fx-background-color: #F7CA88");

        Button Back= new Button("BACK");
        Back.setStyle("-fx-background-color: #ff4500");
        Back.setOnAction(event -> ColorSwitch.setUpMainMenu());


        Button[] menuButtons={File1,File2,File3,File4,File5,Back};

        for(Button button : menuButtons)
        {
            button.setMinWidth(menuOptions.getPrefWidth());
            button.setMinHeight(menuOptions.getPrefHeight());
        }

        menuOptions.getChildren().addAll(File1,File2,File3,File4,File5,Back);
        Scene mainMenu=new Scene(menuOptions,ColorSwitch.desiredSceneWidth,ColorSwitch.desiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("a.css").toExternalForm());



        ColorSwitch.applicationWindow.setScene(mainMenu);
    }
    private static void setUpCreditsMenu()
    {
        VBox menuOptions=new VBox();

        menuOptions.setSpacing(10);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPrefWidth(100);
        menuOptions.setPrefHeight(50);

        Label Tittle= new Label("CREDITS");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy1= new Label(" ");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy2= new Label(" ");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label label1=new Label("This App is developed by Abhimanyu And Shubham");
        Label label2=new Label("For getting the full info about the project ");
        Label label3=new Label("Check the following");
        Label label4=new Label("https://github.com/0deadLock0/Color-Switch/blob/master/Code/README.md");
        Label label5 =new Label(" ");
        Label label6 =new Label(" ");

        Button Back= new Button("BACK");
        Back.setStyle("-fx-background-color: #ff4500");
        Back.setOnAction(event -> ColorSwitch.setUpMainMenu());

        Back.setMinWidth(menuOptions.getPrefWidth());
        menuOptions.getChildren().addAll(Tittle,dummy1,dummy2,label1,label2,label3,label4,label5,label6,Back);
        Scene mainMenu=new Scene(menuOptions,ColorSwitch.desiredSceneWidth,ColorSwitch.desiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("a.css").toExternalForm());


        ColorSwitch.applicationWindow.setScene(mainMenu);
    }
    private static void setUpHelpMenu()
    {
        VBox menuOptions=new VBox();

        menuOptions.setSpacing(10);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPrefWidth(100);
        menuOptions.setPrefHeight(50);

        Label Tittle= new Label("HELP");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy1= new Label(" ");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy2= new Label(" ");
        Label label1=new Label("Navigate through Buttons");
        Label label2=new Label("Play using Spacebar ");
        Label label3=new Label("Stay tuned for other updates and modifications");
        Label label4=new Label("https://github.com/0deadLock0/Color-Switch/blob/master/Code/README.md");
        Label label5 =new Label(" ");
        Label label6 =new Label(" ");

        Button Back= new Button("BACK");
        Back.setStyle("-fx-background-color: #ff4500");
        Back.setOnAction(event -> ColorSwitch.setUpMainMenu());

        Back.setMinWidth(menuOptions.getPrefWidth());
//      Back.setMinHeight(menuOptions.getPrefHeight());
        menuOptions.getChildren().addAll(Tittle,dummy1,dummy2,label1,label2,label3,label4,label5,label6,Back);
        Scene mainMenu=new Scene(menuOptions,ColorSwitch.desiredSceneWidth,ColorSwitch.desiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("a.css").toExternalForm());


        ColorSwitch.applicationWindow.setScene(mainMenu);
    }
    private static void setUpStatsMenu()
    {
        VBox menuOptions=new VBox();

        menuOptions.setSpacing(10);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPrefWidth(100);
        menuOptions.setPrefHeight(50);

        Label Tittle= new Label("STATS");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy1= new Label(" ");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy2= new Label(" ");
        Label label1=new Label("No Saved Games Yet");
        Label label2=new Label("Check Back after Playing some games!!");
        Label label3=new Label("");
        Label label4=new Label("");

        Button Back= new Button("BACK");
        Back.setStyle("-fx-background-color: #ff4500");
        Back.setOnAction(event -> ColorSwitch.setUpMainMenu());


        Back.setMinWidth(menuOptions.getPrefWidth());
        menuOptions.getChildren().addAll(Tittle,dummy1,dummy2,label1,label2,label3,label4,Back);
        Scene mainMenu=new Scene(menuOptions,ColorSwitch.desiredSceneWidth,ColorSwitch.desiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("a.css").toExternalForm());


        ColorSwitch.applicationWindow.setScene(mainMenu);
    }


    private static void startNewGame()
    {
        ColorSwitch.currentGame=new GameSpace(ColorSwitch.applicationWindow,ColorSwitch.desiredSceneWidth,ColorSwitch.desiredSceneHeight);
        ColorSwitch.launchGame();
    }

    private static void launchGame()
    {
        if(currentGame==null)
        {
            //Need to be handled through Exceptions later
            System.out.println("No game currently active");
            return;
        }
        // int gameState=ColorSwitch.currentGame.start();
        // System.out.println("Game exited with state "+gameState);
    }

    private static void closeProgram()
    {
        ColorSwitch.applicationWindow.close();
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

        ColorSwitch.applicationWindow.hide();
        ColorSwitch.applicationWindow.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}