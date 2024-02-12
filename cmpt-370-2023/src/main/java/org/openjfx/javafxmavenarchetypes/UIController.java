package org.openjfx.javafxmavenarchetypes;

import TEST.TestAll;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class UIController extends Application implements Runnable {

    @Override
    public void start(Stage stage) {
        // stage
        stage.setTitle("Farm Manager");
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.getIcons().add(new Image("farm_icon_1.png"));


        // menu scene
        VBox taskSelector = new VBox(30);
        Image image = new Image("farm image.jpeg");
        ImageView imageView = new ImageView(image);
        StackPane menuStackPane = new StackPane();
        Scene MenuScene = new Scene(menuStackPane,300,250);
        MenuScene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("styles.css")).toExternalForm());



        // field page
        FieldView fieldPage = new FieldView();
        Scene fieldScene = new Scene(fieldPage,300,250);
        fieldPage.setStageMainField(stage, MenuScene, fieldScene);

        Button bfield = new Button();
        bfield.setText("Fields");
        bfield.setOnAction(e -> stage.setScene(fieldScene));


        // bin page
        BinView binPage = new BinView();
        Scene sceneBins = new Scene(binPage,300,250);
        binPage.setStageMenu(stage, MenuScene, sceneBins);

        Button bbins = new Button();
        bbins.setText("Bins");
        bbins.setOnAction(e -> stage.setScene(sceneBins));

        // task page
        TaskView taskPage = new TaskView();
        Scene taskScene = new Scene(taskPage,300,250);
        taskPage.setStageMenuTask(stage, MenuScene, taskScene);

        Button btasks = new Button();
        btasks.setText("Tasks");
        btasks.setOnAction(e -> stage.setScene(taskScene));


        // graph page
        GraphView graphPage = new GraphView();
        Scene graphScene = new Scene(graphPage,300,250);
        graphPage.setStageMenu(stage, MenuScene, graphScene);

        Button bgraph = new Button();
        bgraph.setText("Graph");
        bgraph.setOnAction(e ->{
            graphPage.updateGraph();
                graphPage.updateGraph();
                stage.setScene(graphScene);
                });

        // user page
        UserView userPage = new UserView();
        Scene userScene = new Scene(userPage, 300, 250);
        userPage.setStageMenuUser(stage, MenuScene, userScene);

        Button busers= new Button();
        busers.setText("Employees");
        busers.setOnAction(e -> stage.setScene(userScene));

        // add all pages buttons to main menu
        taskSelector.getChildren().addAll(btasks,bfield,bbins,busers,bgraph);
        taskSelector.setAlignment(Pos.CENTER);

        // main menu background image
        imageView.fitWidthProperty().bind(menuStackPane.widthProperty());
        imageView.fitHeightProperty().bind(menuStackPane.heightProperty());
        menuStackPane.getChildren().addAll(imageView,taskSelector);


        stage.setScene(MenuScene);
        stage.show();
    }

    public static void main(String[] args) {

        UIController UItest = new UIController();
        UItest.run();

//        UnCommment below to run tests

//        TestAll test = new TestAll();
//        Thread t1 = new Thread(test);
//        Thread t2 = new Thread(UItest);
//        t2.start(); t1.start();


    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        launch();
    }
}