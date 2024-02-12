package org.openjfx.javafxmavenarchetypes;

import control.BinControl;
import control.GraphControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.InitialFarm.Crop;
import org.InitialFarm.GrainBin;
import javafx.scene.chart.BarChart;
import org.bson.io.BsonOutput;
import org.entities.Field;
import org.entities.Year;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;

public class GraphView extends StackPane {


    private Field selectedField;
    private VBox graphPage = new VBox();
    private Scene MenuScene ;

    private Scene graphScene;
    private Stage stage;

    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    private ComboBox comboBox;
    private BarChart barchart;
    private XYChart.Series dataSeries1;
    private ObservableList<Field> fieldList;


    GraphControl graphController;


    //Making a custom layout for the combo box
    private HBox buildLayout(Field field) {
        VBox layout = new VBox();
        HBox.setHgrow(layout, Priority.ALWAYS);
        layout.setStyle("-fx-border-width:1px;-fx-border-color:#444444;");
        layout.setSpacing(5);
        layout.setPadding(new Insets(2));
        HBox topRow = new HBox();
        topRow.setSpacing(5);
        topRow.getChildren().addAll(getLabel("Name :","bold"),getLabel(field.getName(),"normal"), getLabel("Acres :","bold"),getLabel(field.getSize()+"","normal"));
        HBox bottomRow = new HBox();
        bottomRow.setSpacing(5);
        if (field.getCurrent_Year() != null) {
            bottomRow.getChildren().addAll(getLabel("Current Crop :", "bold"), getLabel(field.getCurrent_Year().getCrop().getCropType(), "normal"));
        }
        layout.getChildren().addAll(topRow, bottomRow);

        HBox pane = new HBox();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setSpacing(5);
        pane.setPadding(new Insets(2));
        Label num;
        if (field.getCurrent_Year() != null) {
            num = new Label(field.getCurrent_Year().getYear() + "");
        }
        else{
            num = new Label("");
        }
        num.setStyle("-fx-font-size:20px;-fx-font-weight:bold;-fx-text-fill:black;");
        pane.getChildren().addAll(num,layout);
        return pane;
    }

    private Label getLabel(String txt, String style){
        Label lblName = new Label(txt);
        lblName.setStyle("-fx-font-weight:"+style+";-fx-text-fill:black;");
        return lblName;
    }

    public void updateGraph(){
        fieldList = FXCollections.observableArrayList(graphController.getAllFields());
        comboBox.setItems(fieldList);
    }

    /**
     * Constructor for the graph view. Initializes the graph controller and
     * creates the graph view's layout.
     *
     */
    public GraphView() {

        graphController = new GraphControl();

        VBox graphPage= new VBox(30);
        Scene graphScene = new Scene(graphPage, 300, 250);
        graphPage.setPadding(new Insets(10, 10, 10, 10));

        //Initializing the bar chart for all data
        Field testField = new Field(null,"50","fieldy boi",30,"FieldLocation");
        Year newYear1 = new Year(null,2020, LocalDate.of(2020, Month.MAY,15));
        Crop newCrop = new Crop(null,"corn", "factory corn", 400);
        newYear1.setCrop(newCrop);
        testField.addYear(newYear1);
        testField.setCurrentYear(newYear1);

        Field testField2 = new Field(null,"40","boi of the field",60,"FieldLocation up my ass");
        Year newYear2 = new Year(null,2045, LocalDate.of(2024, Month.MAY,15));
        Year newYear3 = new Year(null,2023,LocalDate.of(2023, Month.MAY,15));
        Crop newCrop2 = new Crop(null,"potato", "factory ofthe potatiot", 600);
        Crop newCrop3 = new Crop(null,"leaf","leaf factory",800);
        newYear2.setCrop(newCrop2);
        newYear3.setCrop(newCrop3);
        testField2.addYear(newYear2);
        testField2.addYear(newYear3);
        testField2.setCurrentYear(newYear2);

        //Adding drop down box with options
        fieldList = FXCollections.observableArrayList(graphController.getAllFields());

        comboBox = new ComboBox<>();
        comboBox.setItems(fieldList);
        comboBox.setCellFactory(param -> new ListCell<Field>() {
            @Override
            protected void updateItem(Field item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(buildLayout(item));
                } else {
                    setGraphic(null);
                }
            }
        });
        comboBox.setButtonCell(new ListCell<Field>(){
            @Override
            protected void updateItem(Field item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(buildLayout(item));
                } else {
                    setGraphic(null);
                }
            }
        });

        comboBox.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,field)->{
            System.out.println("Field");
        });


        comboBox.getSelectionModel().selectFirst();
        if (comboBox.getValue() != null){
            selectedField = (Field) comboBox.getValue();
        }

        comboBox.setOnAction(e -> {
                this.selectedField = (Field) comboBox.getValue();
                if (this.selectedField != null) {
                dataSeries1.getData().clear();

                if (selectedField != null) {
                    dataSeries1.setName(selectedField.getName() + "");
                }

                if (selectedField.getCurrent_Year() != null) {
                    dataSeries1.getData().add(new XYChart.Data((selectedField.getCurrent_Year().getYear() + " " + selectedField.getCurrent_Year().getCrop().getCropType()), selectedField.getCurrent_Year().getYield()));
                }
                for (int x = 0; x < selectedField.getYears().size(); x++) {
                    System.out.println(selectedField.getYears().get(x).getYear());
                    System.out.println(selectedField.getYears().get(x).getCrop().getBushelWeight());
                    dataSeries1.getData().add(new XYChart.Data(((selectedField.getYears().get(x).getYear()) + " " + selectedField.getYears().get(x).getCrop().getCropType()), selectedField.getYears().get(x).getYield()));
                    System.out.println(selectedField.getYears().get(x).getYear());

                }


            }
        });


        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        barchart = new BarChart(xAxis, yAxis);
        dataSeries1 = new XYChart.Series();
        barchart.setAnimated(false);
        if (selectedField != null){
            if (selectedField.getCurrent_Year() != null) {
                dataSeries1.getData().add(new XYChart.Data((selectedField.getCurrent_Year().getYear() + " " + selectedField.getCurrent_Year().getCrop().getCropType()), selectedField.getCurrent_Year().getYield()));
            }
            for (int x = 0;x < selectedField.getYears().size();x++){
                System.out.println(selectedField.getYears().get(x).getYear());
                System.out.println(selectedField.getYears().get(x).getCrop().getBushelWeight());
                dataSeries1.getData().add(new XYChart.Data(((selectedField.getYears().get(x).getYear()) + " " + selectedField.getYears().get(x).getCrop().getCropType()),selectedField.getYears().get(x).getYield()));
                System.out.println(selectedField.getYears().get(x).getYear());

            }
        }
        //Making the actual bar chart

        yAxis.setLabel("Yield in Pounds (lbs)");
        xAxis.setLabel("Years");
        barchart.getData().add(dataSeries1);

        barchart.setTitle("Yearly harvest");

        Button graphToMain = new Button("Back To Main");
        graphToMain.setOnMouseClicked(event -> {
            stage.setScene(MenuScene);
        });

        HBox topBar = new HBox();
        graphToMain.getStyleClass().add("back-button");
        topBar.getStyleClass().add("top-bar");
        HBox.setHgrow(graphToMain, Priority.ALWAYS);
        topBar.alignmentProperty().set(Pos.CENTER_RIGHT);
        topBar.getChildren().add(graphToMain);
        graphPage.getChildren().addAll(topBar ,comboBox,barchart);
        this.getChildren().add(graphPage);
    }

    /**
     * Sets the primary stage and scenes of the application. Specifically handles the
     * menu and graph scenes. Additionally, applies a stylesheet to the graph scene.
     *
     * @param stage The primary JavaFX stage where scenes will be displayed.
     * @param MenuScene The scene representing the application's main menu.
     * @param graphScene The scene representing the graph view of the application and to which the style sheet is to be applied.
     *
     */
    public void setStageMenu(Stage stage, Scene MenuScene, Scene graphScene){
        this.stage = stage;
        this.MenuScene = MenuScene;
        this.graphScene = graphScene;
        graphScene.getStylesheets().add(getClass().getClassLoader().getResource("graph.css").toExternalForm());
    }

}
