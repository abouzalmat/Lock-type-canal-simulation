import components.State;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.GlobalVars;
import utils.Strings;

/**
 * Created by BouzalmatAbderrahman on 6/6/2019
 */
public class Ecluse extends Application {
    BorderPane root;
    Scene scene;
    Pane center;
    BorderPane controlPane;
    GridPane buttonsAmontPane;
    public static Resources res;
    int sens = 1;
    public static int sasLevel = GlobalVars.SAS_MAX_YPOSITION;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new BorderPane();
        scene = new Scene(root, GlobalVars.WINDOW_WIDTH, GlobalVars.WINDOW_HEIGHT);

        //creating MenuBar
        MenuBar menubar = new MenuBar();
        //creating Menus
        Menu direction = new Menu(Strings.DIRECTION_MENU);
        Menu mode = new Menu(Strings.MODE_MENU);
        //creating Menus Items
        MenuItem avalToAmantItem = new MenuItem(Strings.DIRECTION_ITEM1);
        MenuItem amantToAvalItem = new MenuItem(Strings.DIRECTION_ITEM2);
        MenuItem manualMode = new MenuItem(Strings.MODE_ITEM2);
        MenuItem autoMode = new MenuItem(Strings.MODE_ITEM1);

        //adding Menu Items to the Menu
        direction.getItems().addAll(avalToAmantItem, amantToAvalItem);
        mode.getItems().addAll(manualMode, autoMode);

        //adding Menu to the MenuBar
        menubar.getMenus().addAll(direction, mode);

        // actions events
        EventHandler<ActionEvent> avalToAmantEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                sens = -1;
                sasLevel = GlobalVars.SAS_MIN_YPOSITION;
                initResourses(sens);
                root.setCenter(initCenterPane());
                System.out.println(sasLevel);
            }
        };
        EventHandler<ActionEvent> amantToAvalEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                sens = 1;
                sasLevel = GlobalVars.SAS_MAX_YPOSITION;
                initResourses(sens);
                root.setCenter(initCenterPane());
                System.out.println(sasLevel);
            }
        };

        // Add menu event
        avalToAmantItem.setOnAction(avalToAmantEvent);
        amantToAvalItem.setOnAction(amantToAvalEvent);

        initResourses(sens);
        root.setTop(menubar);
        root.setCenter(initCenterPane());
        root.setBottom(initConrolPanel());

        primaryStage.setScene(scene);
        primaryStage.show();
    }





    // Initialize ressources
    private void initResourses(int sens) {
        res = new Resources();
        res.loadResourses(sens);
    }


    // Render center pane
    public Pane initCenterPane() {
        center = new Pane();
        center.getChildren().addAll(res.backgroundView, res.boatView, res.firstDoorView, res.secondDoorView, res.sasView, res.firstValveView, res.secondValveView);
        return  center;
    }


    // Render control pane
    public BorderPane initConrolPanel(){
        controlPane = new BorderPane();
       // controlPane.setCenter(res.controlBackgroundView);
        controlPane.setLeft(avalButtonsBuilder());
        controlPane.setRight(amontButtonsBuilder());
        return controlPane;
    }
    public GridPane avalButtonsBuilder(){
        GridPane buttonsAvalPane = new GridPane();
        buttonsAvalPane.setHgap(10);
        buttonsAvalPane.setVgap(10);

        Label avalLabel = new Label("Aval");
        Label lightLabel = new Label("Feu");
        Label doorLabel = new Label("Porte");
        Label valveLabel = new Label("Vanne");

        Button openFirstValve = new Button("Ouvrir");
        Button closeFirstValve = new Button("Fermer");
        Button openFirstAvalDoor = new Button("Ouvrir");
        Button closeFirstAvalDoor = new Button("Fermer");
        Button lightAvalOn = new Button("Allumer");
        Button lightAvalOff = new Button("Eteindre");

        openFirstAvalDoor.setOnAction(openFirstDoor());
        closeFirstAvalDoor.setOnAction(closeFirstDoor());
        openFirstValve.setOnAction(openFirstValve());
        closeFirstValve.setOnAction(closeFirstValve());


        buttonsAvalPane.add(avalLabel,1,1);
        buttonsAvalPane.add(valveLabel,1,2);
        buttonsAvalPane.add(doorLabel,1,3);
        buttonsAvalPane.add(lightLabel,1,4);
        buttonsAvalPane.add(openFirstValve,2,2);
        buttonsAvalPane.add(closeFirstValve,3,2);
        buttonsAvalPane.add(openFirstAvalDoor,2,3);
        buttonsAvalPane.add(closeFirstAvalDoor,3,3);
        buttonsAvalPane.add(lightAvalOn,2,4);
        buttonsAvalPane.add(lightAvalOff,3,4);

        return buttonsAvalPane ;
    }

    public EventHandler<ActionEvent> openFirstDoor(){
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(res.firstDoor.getState() == State.CLOSE && sasLevel == GlobalVars.SAS_MIN_YPOSITION && res.firstValve.getState() == State.CLOSE) {
                    res.firstDoor.open();
                    System.out.println(res.firstDoor.getState());
                    res.firstDoor.setState(State.OPEN);
                    System.out.println(res.firstDoor.getState());
                }
                else
                    System.out.println("Action non autorisée");
            }
        };
        return  event;
    }

    public EventHandler<ActionEvent> closeFirstDoor(){
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if( res.firstDoor.getState()  == State.OPEN){
                        res.firstDoor.close();
                        res.firstDoor.setState(State.CLOSE);
                }
                else {
                    System.out.println("Action non autorisée");
                }
            }
        };
        return  event;
    }

    public EventHandler<ActionEvent> openFirstValve(){
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(sasLevel == GlobalVars.SAS_MAX_YPOSITION && res.firstValve.getState() == State.CLOSE && res.secondValve.getState() == State.CLOSE){
                    res.firstValve.open();
                    res.sas.close();
                    sasLevel = GlobalVars.SAS_MIN_YPOSITION;
                    res.firstValve.setState(State.OPEN);
                }
                else {
                    System.out.println("Action non autorisée");
                }
            }
        };
        return event;
    }

    public EventHandler<ActionEvent> closeFirstValve(){
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(res.firstValve.getState() == State.OPEN){
                    // res.firstValve.close();
                    res.firstValve.setState(State.CLOSE);
                }
            }
        };
        return event;
    }











    public GridPane amontButtonsBuilder(){
        // Initialize AmontPane
        buttonsAmontPane = new GridPane();
        buttonsAmontPane.setHgap(10);
        buttonsAmontPane.setVgap(10);

        //AmontPane labels
        Label amontLabel = new Label("Amont");
        Label amontLightLabel = new Label("Feu");
        Label amontDoorLabel = new Label("Porte");
        Label amontValveLabel = new Label("Vanne");


        // AmontPane buttons
        Button openSecondValve = new Button("Ouvrir");
        Button closeSecondValve = new Button("Fermer");
        Button openSecondDoor = new Button("Ouvrir");
        Button closeSecondDoor = new Button("Fermer");
        Button lightAmontOn = new Button("Allumer");
        Button lightAmontOff = new Button("Eteindre");


        // Add labels and buttons to AmontPane
        buttonsAmontPane.add(amontLabel,1,1);
        buttonsAmontPane.add(amontValveLabel,1,2);
        buttonsAmontPane.add(amontDoorLabel,1,3);
        buttonsAmontPane.add(amontLightLabel,1,4);
        buttonsAmontPane.add(openSecondValve,2,2);
        buttonsAmontPane.add(closeSecondValve,3,2);
        buttonsAmontPane.add(openSecondDoor,2,3);
        buttonsAmontPane.add(closeSecondDoor,3,3);
        buttonsAmontPane.add(lightAmontOn,2,4);
        buttonsAmontPane.add(lightAmontOff,3,4);

        openSecondDoor.setOnAction(openSecondDoor());
        closeSecondDoor.setOnAction(closeSecondDoor());
        openSecondValve.setOnAction(openSecondValve());
        closeSecondValve.setOnAction(closeSecondValve());

        return buttonsAmontPane;
    }

    public EventHandler<ActionEvent> openSecondDoor(){
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(res.secondDoor.getState() == State.CLOSE && sasLevel == GlobalVars.SAS_MAX_YPOSITION) {
                    res.secondDoor.open();
                    System.out.println(res.secondDoor.getState());
                    res.secondDoor.setState(State.OPEN);
                    System.out.println(res.secondDoor.getState());
                }
                else {
                    System.out.println("Action non autorisée");
                }
            }
        };
        return  event;
    }

    public EventHandler<ActionEvent> closeSecondDoor(){
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if( res.secondDoor.getState()  == State.OPEN){
                    res.secondDoor.close();
                    res.secondDoor.setState(State.CLOSE);
                }
            }
        };
        return  event;
    }

    public EventHandler<ActionEvent> openSecondValve(){
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(sasLevel == GlobalVars.SAS_MIN_YPOSITION && res.secondValve.getState() == State.CLOSE){
                    // res.firstValve.open();
                    res.sas.open();
                    sasLevel = GlobalVars.SAS_MAX_YPOSITION;
                    res.secondValve.setState(State.OPEN);
                }
                else {
                    System.out.println("Action non autorisée");
                }
            }
        };
        return event;
    }

    public EventHandler<ActionEvent> closeSecondValve(){
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(sasLevel == GlobalVars.SAS_MAX_YPOSITION && res.secondValve.getState() == State.OPEN){
                    // res.secondValve.close();
                    res.secondValve.setState(State.CLOSE);
                }
            }
        };
        return event;
    }






    public static void main(String[] args) {
        launch(args);
    }
}
