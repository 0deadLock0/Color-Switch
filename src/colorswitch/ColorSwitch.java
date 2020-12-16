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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ColorSwitch extends Application
{
    private Stage applicationWindow;
    private GameSpace currentGame;

    private static final String recordsLocation;

    static
    {
        recordsLocation="SavedRecords";
    }

    private void setUpWindow()
    {
        this.applicationWindow.setTitle("Color Switch");
        this.applicationWindow.setResizable(false);
    }

    public void setUpMainMenu()
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

        newGameButton.setOnAction(newGameEvent -> this.startNewGame());

        Button loadGameButton=new Button("LOAD GAME");
        loadGameButton.setStyle("-fx-background-color: #00bfff");
        loadGameButton.setOnAction(loadGameEvent -> this.setUpLoadMenu());

        Button statsButton=new Button("STATS");
        statsButton.setStyle("-fx-background-color: #e9967a");
        statsButton.setOnAction(statsevent -> setUpStatsMenu());

        Button helpButton=new Button("HELP");
        helpButton.setStyle("-fx-background-color: #00fa9a");
        helpButton.setOnAction(helpevent -> setUpHelpMenu());

        Button creditsButton=new Button("CREDITS");
        creditsButton.setStyle("-fx-background-color: #F7CA88");
        creditsButton.setOnAction(creditsevent -> this.setUpCreditsMenu());

        Button exitButton=new Button("EXIT");
        exitButton.setStyle("-fx-background-color: #f08080");
        exitButton.setOnAction(exitEvent -> this.closeProgram());

        Button[] menuButtons={newGameButton,loadGameButton,statsButton,helpButton,creditsButton,exitButton};

        for(Button button : menuButtons)
        {
            button.setMinWidth(menuOptions.getPrefWidth());
            button.setMinHeight(menuOptions.getPrefHeight());
        }
        title.getChildren().add(imageView);
        menuOptions.getChildren().addAll(title,newGameButton,loadGameButton,statsButton,helpButton,creditsButton,exitButton);
        Scene mainMenu=new Scene(menuOptions,Settings.DesiredSceneWidth,Settings.DesiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("styles.css").toExternalForm());

        this.applicationWindow.setScene(mainMenu);
    }
    private void setUpLoadMenu()
    {
        VBox menuOptions=new VBox();

        menuOptions.setSpacing(10);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPrefWidth(100);
        menuOptions.setPrefHeight(50);



        Button File1=new Button("Saved state 1");
        //vghvhg

        File1.setStyle("-fx-background-color: #AB4642");

        //File1.setOnAction(newGameEvent -> this.startNewGame());

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
        Back.setOnAction(event -> this.setUpMainMenu());


        Button[] menuButtons={File1,File2,File3,File4,File5,Back};

        for(Button button : menuButtons)
        {
            button.setMinWidth(menuOptions.getPrefWidth());
            button.setMinHeight(menuOptions.getPrefHeight());
        }

        menuOptions.getChildren().addAll(File1,File2,File3,File4,File5,Back);
        Scene mainMenu=new Scene(menuOptions,Settings.DesiredSceneWidth,Settings.DesiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("styles.css").toExternalForm());



        this.applicationWindow.setScene(mainMenu);
    }
    private void setUpCreditsMenu()
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
        Back.setOnAction(event -> this.setUpMainMenu());

        Back.setMinWidth(menuOptions.getPrefWidth());
        menuOptions.getChildren().addAll(Tittle,dummy1,dummy2,label1,label2,label3,label4,label5,label6,Back);
        Scene mainMenu=new Scene(menuOptions,Settings.DesiredSceneWidth,Settings.DesiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("styles.css").toExternalForm());


        this.applicationWindow.setScene(mainMenu);
    }
    private void setUpHelpMenu()
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
        Back.setOnAction(event -> this.setUpMainMenu());

        Back.setMinWidth(menuOptions.getPrefWidth());
//      Back.setMinHeight(menuOptions.getPrefHeight());
        menuOptions.getChildren().addAll(Tittle,dummy1,dummy2,label1,label2,label3,label4,label5,label6,Back);
        Scene mainMenu=new Scene(menuOptions,Settings.DesiredSceneWidth,Settings.DesiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("styles.css").toExternalForm());


        this.applicationWindow.setScene(mainMenu);
    }
    private void setUpStatsMenu()
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
        Back.setOnAction(event -> this.setUpMainMenu());


        Back.setMinWidth(menuOptions.getPrefWidth());
        menuOptions.getChildren().addAll(Tittle,dummy1,dummy2,label1,label2,label3,label4,Back);
        Scene mainMenu=new Scene(menuOptions,Settings.DesiredSceneWidth,Settings.DesiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("styles.css").toExternalForm());


        this.applicationWindow.setScene(mainMenu);
    }

    public void saveGame(GameSpace gameSpace)
    {
        String fileName=ColorSwitch.recordsLocation+"\\TestingFile";
        GameRecord gameRecord=new GameRecord(gameSpace.getScore(),"Test",gameSpace);

        try
        {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(gameRecord);

            out.close();
            file.close();

            System.out.println("Game Saved in file "+fileName);//To be removed
        }
        catch(IOException ex)
        {
            System.out.println("IOException is caught");
            ex.printStackTrace(System.out);
        }
    }

    private void startNewGame()
    {
        this.currentGame=new GameSpace(this, this.applicationWindow, Settings.DesiredSceneWidth, Settings.DesiredSceneHeight);

        //Uncomment below lines to test Save Game functionality
//        this.currentGame=this.loadGame();
//        this.currentGame.construct(this, this.applicationWindow, Settings.DesiredSceneWidth, Settings.DesiredSceneHeight);

        this.launchGame();
    }

    private GameSpace loadGame() //For testing, prototype needs to be changed
    {
        String fileName=ColorSwitch.recordsLocation+"\\TestingFile";

        GameRecord gameRecord=null;

        try
        {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            gameRecord = (GameRecord) in.readObject();

            in.close();
            file.close();

            System.out.println("Game Load from file "+fileName);//To be removed
        }
        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }

        return gameRecord.getGameSpace();
    }

    private void launchGame()
    {
        if(currentGame==null)
        {
            //Need to be handled through Exceptions later
            System.out.println("No game currently active");
            return;
        }
        this.currentGame.start();
    }

    private void closeProgram()
    {
        this.applicationWindow.close();
    }

    @Override
    public void start(Stage primaryStage)
    {
        this.applicationWindow=primaryStage;

        this.setUpWindow();
        this.applicationWindow.setOnCloseRequest(closeEvent -> {
            closeEvent.consume();
            this.closeProgram();
        });

        this.setUpMainMenu();

        this.applicationWindow.hide();
        this.applicationWindow.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}