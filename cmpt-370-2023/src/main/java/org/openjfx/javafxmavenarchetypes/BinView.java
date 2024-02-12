package org.openjfx.javafxmavenarchetypes;

import control.BinControl;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.InitialFarm.Crop;
import org.InitialFarm.GrainBin;
import org.bson.types.ObjectId;


public class BinView extends StackPane {

    /**
     * bin page page container
     */
    private VBox binPage = new VBox();
    /**
     * menu scene
     */
    private Scene MenuScene ;

    /**
     * bin scene
     */
    private Scene binScene;
    /**
     * stage
     */
    private Stage stage;
    private TableView<GrainBin> binTable = new TableView<>();   // bin tableview
    private BinControl binController = new BinControl(); // bin controller
    private ObservableList<GrainBin> grainBinData = binController.binList;  // bin data

    private TableView<Crop> currentCropTable = new TableView<>();   // current crop tableview
    private ObservableList<Crop> currentCropData = FXCollections.observableArrayList(); // current crop data
    private TableView<Crop> lastCropTable = new TableView<>();  // last crop tableview
    private ObservableList<Crop> lastCropData = FXCollections.observableArrayList();    // last crop data

    private final int binMaxCapacity = 100000;  // bin max capacity

    public BinView(){
        // crop page container and scene
        VBox binCropPage = new VBox(15);
        Scene binCropScene = new Scene(binCropPage, 300, 250);

        // bin tableview columns
        TableColumn<GrainBin, ObjectId> binIDCol = new TableColumn<GrainBin, ObjectId>("Bin ID");
        binIDCol.setMinWidth(70);
        binIDCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, ObjectId>("DbId"));

        TableColumn<GrainBin, String> binNameCol = new TableColumn<GrainBin, String>("Bin Name");
        binNameCol.setMinWidth(100);
        binNameCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, String>("binName"));

        TableColumn<GrainBin, String> binLocationCol = new TableColumn<GrainBin, String>("Bin Location");
        binLocationCol.setMinWidth(100);
        binLocationCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, String>("binLocation"));

        TableColumn<GrainBin, Integer> binSizeCol = new TableColumn<GrainBin, Integer>("Bin Size (bushel)");
        binSizeCol.setMinWidth(130);
        binSizeCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Integer>("binSize"));

        TableColumn<GrainBin, Boolean> binHopperCol = new TableColumn<GrainBin, Boolean>("Bin Hopper");
        binHopperCol.setMinWidth(100);
        binHopperCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Boolean>("hopper"));

        TableColumn<GrainBin, String> binFanCol = new TableColumn<GrainBin, String>("Bin Fan");
        binFanCol.setMinWidth(100);
        binFanCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, String>("fan"));

        TableColumn<GrainBin, String> cropTypeCol = new TableColumn<GrainBin, String>("Crop Type");
        cropTypeCol.setMinWidth(100);
        cropTypeCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, String>("currentCropType"));

        TableColumn<GrainBin, Integer> binBushelsCol = new TableColumn<GrainBin, Integer>("Bushels");
        binBushelsCol.setMinWidth(100);
        binBushelsCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Integer>("cropBushels"));

        TableColumn<GrainBin, Integer> binCropLbsCol = new TableColumn<GrainBin, Integer>("Crop (lbs)");
        binCropLbsCol.setMinWidth(100);
        binCropLbsCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Integer>("cropLbs"));

        TableColumn<GrainBin, Boolean> binToughCol = new TableColumn<GrainBin, Boolean>("Tough Crop?");
        binToughCol.setMinWidth(100);
        binToughCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Boolean>("tough"));

        TableColumn<GrainBin, Boolean> binCleanCol = new TableColumn<GrainBin, Boolean>("Clean Crop?");
        binCleanCol.setMinWidth(100);
        binCleanCol.setCellValueFactory(
                new PropertyValueFactory<GrainBin, Boolean>("clean"));

        binTable.setItems(grainBinData);
        binTable.getColumns().addAll(binIDCol, binNameCol, binLocationCol, binSizeCol, binBushelsCol, binCropLbsCol, cropTypeCol, binHopperCol, binFanCol, binToughCol, binCleanCol);


        // add bin page container and scene
        VBox addBinBox = new VBox(15);
        Scene addBinScene = new Scene(addBinBox,300,250);

        Label addBinPageTitle = new Label("Add New Bin");
        addBinPageTitle.getStyleClass().add("page-label");

        Label binNameInputLabel = new Label("Bin Name:");
        binNameInputLabel.getStyleClass().add("text-field-label");
        TextField binNameInput = new TextField();

        Label binSizeInputLabel = new Label("Bin Size (bushel):");
        binSizeInputLabel.getStyleClass().add("text-field-label");
        TextField binSizeInput = new TextField();

        Label binLocationLabel = new Label("Bin Location");
        binLocationLabel.getStyleClass().add("text-field-label");
        TextField binLocation = new TextField();

        CheckBox hopperInput = new CheckBox("Hopper?");
        CheckBox fanInput = new CheckBox("Fan?");
        Button submitBinInfo = new Button("Submit");
        Button cancelAddBin = new Button("Cancel");
        cancelAddBin.setOnMouseClicked(event -> {
            stage.setScene(binScene);
        });

        Label space1 = new Label("\t\t");
        HBox submitAndCancelBox1 = new HBox(submitBinInfo, space1, cancelAddBin);


        addBinBox.getChildren().addAll(addBinPageTitle, binNameInputLabel, binNameInput, binSizeInputLabel, binSizeInput,
                binLocationLabel, binLocation, hopperInput, fanInput, submitAndCancelBox1);
        // button to open add bin form
        Button addBin = new Button("Add Bin");
        addBin.setOnMouseClicked(e ->{
            stage.setScene(addBinScene);
        });

        // submit bin info
        submitBinInfo.setOnMouseClicked(e ->{
            int binSize = -1;
            try {
                binSize = Integer.parseInt(binSizeInput.getText());
            } catch (Exception error){
                System.out.println("Invalid bin size");
                showErrorPopup( "Please enter valid bin size.");
            }
            if (binSize != -1){
                if (binSize > binMaxCapacity){
                    System.out.println("Maximum bin capacity is 20 bushels");
                    showErrorPopup("Maximum bin capacity is 20 bushels");
                } else {
                    binController.addBin(binNameInput.getText(), binSize, binLocation.getText(), hopperInput.isSelected(), fanInput.isSelected() );
                    stage.setScene(binScene);
                    showPopup("Added Bin");
                }
            }
            binNameInput.setText("");
            binSizeInput.setText("");
            binLocation.setText("");
            hopperInput.setSelected(false);
            fanInput.setSelected(false);
        });

        // delete a bin function
        Button deleteBin = new Button("Delete Bin");
        deleteBin.setOnAction(event -> {
            if (binTable.getSelectionModel().getSelectedItem() != null){
                binController.deleteBin(binTable.getSelectionModel().getSelectedItem().getDbId());
                binTable.refresh();
            }
            else {
                System.out.println("Select a Bin");
                showErrorPopup( "Maximum bin capacity is 20 bushels");
            }
        });


        // add crop page and scene
        VBox addCropBox = new VBox(15);
        Scene addCropScene = new Scene(addCropBox,300,250);
        TextField addCropBinID = new TextField();

        Label addCropPageTitle = new Label();
        Label cropTypeLabel = new Label("Select Crop Type");
        cropTypeLabel.getStyleClass().add("text-field-label");
        ComboBox<String> cropTypeInput = new ComboBox<>();
        cropTypeInput.getItems().addAll(binController.cropType);

        Label newCropTypeLabel = new Label("Or Add New Crop Type");
        newCropTypeLabel.getStyleClass().add("text-field-label");
        TextField newCropTypeInput = new TextField();

        Label space2 = new Label("\t");
        Label space3 = new Label("\t\t");
        Label space20 = new Label("\t");
        HBox groupBox1 = new HBox(cropTypeLabel, space2, cropTypeInput, space3, newCropTypeLabel, space20, newCropTypeInput );

        Label cropVarietyLabel = new Label("Select Crop Variety");
        cropVarietyLabel.getStyleClass().add("text-field-label");
        ComboBox<String> cropVarietyInput = new ComboBox<>();
        cropVarietyInput.getItems().addAll("LibertyLink", "RoundupReady", "Navigator", "ClearField", "All Other Grains");

        binController.cropType.addListener((ListChangeListener<String>) change -> {
            cropTypeInput.setItems(binController.cropType);
        });

        Label bushelWeighLabel = new Label("Bushel Weight (lbs):");
        bushelWeighLabel.getStyleClass().add("text-field-label");
        TextField bushelWeight = new TextField();

        Label grainInputLabel = new Label("Grain ie: 10");
        grainInputLabel.getStyleClass().add("text-field-label");
        TextField grainInput = new TextField();
        CheckBox inputBushels = new CheckBox("Crop is in bushels?");
        CheckBox cleanCrop = new CheckBox("Crop is clean?");
        CheckBox toughCrop = new CheckBox("Crop is tough?");
        Label space10 = new Label("\t");
        Label space11 = new Label("\t");
        HBox groupBox3 = new HBox(inputBushels, space10, cleanCrop, space11, toughCrop);
        Button submitCropInfo = new Button("Submit");
        Button cancelAddCrop = new Button("Cancel");
        cancelAddCrop.setOnMouseClicked(event -> {
            stage.setScene(binScene);
        });

        Label space4 = new Label("\t\t");
        HBox submitAndCancelBox2 = new HBox(submitCropInfo, space4, cancelAddCrop);

        addCropBox.getChildren().addAll(addCropPageTitle, groupBox1, cropVarietyLabel, cropVarietyInput, bushelWeighLabel,
                bushelWeight, grainInputLabel, grainInput, groupBox3, submitAndCancelBox2);


        // button to open add crop form
        // lots of error checking
        Button addCrop = new Button("Add Crop");
        addCrop.setOnMouseClicked(e ->{
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                if (selectedData.getBinSize() > selectedData.getCropBushels()){
                    addCropBinID.setText(selectedData.getDbId().toString());
                    addCropPageTitle.setText("Add Crop to bin named (" + selectedData.getBinName() + ")");
                    addCropPageTitle.getStyleClass().add("page-label");
                    Crop currentCrop = selectedData.getCurrentCrop();
                    if (currentCrop != null){
                        cropTypeInput.setValue(currentCrop.getCropType());
                        bushelWeight.setText(String.valueOf(currentCrop.getBushelWeight()));
                        cropVarietyInput.setValue(currentCrop.getCropVariety());
                        bushelWeight.setText(String.valueOf(currentCrop.getBushelWeight()));
                    }
                    stage.setScene(addCropScene);
                } else {
                    System.out.println("Bin is full");
                    showErrorPopup("Bin is full");
                }
            }
            else{
                System.out.println("Select a Bin");
                showErrorPopup("Select a Bin");
            }

        });

        // submit crop info
        // needed to include a giant amount of error checking cause crop can only be added under certain conditions
        submitCropInfo.setOnMouseClicked(e ->{
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            Crop selectedCrop = selectedData.getCurrentCrop();
            Crop crop;
            int grain = -1;
            double bWeight = -1.0;
            try {
                grain = Integer.parseInt(grainInput.getText());
                bWeight = Double.parseDouble(bushelWeight.getText());
            } catch (Exception error){
                System.out.println("Invalid grain/bushel input");
                showErrorPopup("Invalid grain/bushel input");
            }

            if (!newCropTypeInput.getText().isEmpty()) {
                binController.addCropType(newCropTypeInput.getText());
            }
            String cropType;
            if (!newCropTypeInput.getText().isEmpty()) {
                cropType = newCropTypeInput.getText();
            } else {
                cropType = cropTypeInput.getValue();
            }

            if (grain != -1 && bWeight != -1.0) {
                if (selectedData.isEmpty()) {
                    if (inputBushels.isSelected() && grain + selectedData.getCropBushels() <= selectedData.getBinSize() || !inputBushels.isSelected() && lbsToBushels(grain, bWeight) + selectedData.getCropBushels() <= selectedData.getBinSize()) {
                        if (selectedCrop == null) {
                            crop = binController.makeCrop(new ObjectId(addCropBinID.getText()), grain, inputBushels.isSelected(), null, cropType, cropVarietyInput.getValue(), bWeight);
                            binController.addCrop(new ObjectId(addCropBinID.getText()), crop, grain, inputBushels.isSelected(), cleanCrop.isSelected(), toughCrop.isSelected());
                        } else {
                            if (selectedCrop.getCropType().equals(cropType) && selectedCrop.getCropVariety().equals(cropVarietyInput.getValue()) && selectedCrop.getBushelWeight() == bWeight) {
                                binController.addCrop(new ObjectId(addCropBinID.getText()), selectedCrop, grain, inputBushels.isSelected(), cleanCrop.isSelected(), toughCrop.isSelected());

                            } else {
                                crop = binController.makeCrop(new ObjectId(addCropBinID.getText()), grain, inputBushels.isSelected(), null, cropType, cropVarietyInput.getValue(), bWeight);
                                binController.addCrop(new ObjectId(addCropBinID.getText()), crop, grain, inputBushels.isSelected(), cleanCrop.isSelected(), toughCrop.isSelected());

                            }
                        }
                        cropTypeInput.setValue(null);
                        newCropTypeInput.setText("");
                        cropVarietyInput.setValue(null);
                        bushelWeight.setText("");
                        grainInput.setText("");
                        inputBushels.setSelected(false);
                        cleanCrop.setSelected(false);
                        toughCrop.setSelected(false);
                        binTable.setItems(grainBinData);
                        binTable.refresh();
                        stage.setScene(binScene);
                        showPopup("Added Crop");
                    } else {
                        System.out.println("Maximum capacity exceeded");
                        showErrorPopup("Maximum capacity exceeded");
                    }
                } else {
                    if (selectedCrop.getCropType().equals(cropType) && selectedCrop.getCropVariety().equals(cropVarietyInput.getValue()) && selectedCrop.getBushelWeight() == bWeight) {
                        if (inputBushels.isSelected() && grain + selectedData.getCropBushels() <= selectedData.getBinSize() || !inputBushels.isSelected() && lbsToBushels(grain, bWeight) + selectedData.getCropBushels() <= selectedData.getBinSize()) {
                            binController.addCrop(new ObjectId(addCropBinID.getText()), selectedCrop, grain, inputBushels.isSelected(), cleanCrop.isSelected(), toughCrop.isSelected());
                            cropTypeInput.setValue(null);
                            newCropTypeInput.setText("");
                            cropVarietyInput.setValue(null);
                            bushelWeight.setText("");
                            grainInput.setText("");
                            inputBushels.setSelected(false);
                            cleanCrop.setSelected(false);
                            toughCrop.setSelected(false);
                            binTable.setItems(grainBinData);
                            binTable.refresh();
                            stage.setScene(binScene);
                            showPopup("Added Crop");
                        } else {
                            System.out.println("Maximum capacity exceeded");
                            showErrorPopup("Maximum capacity exceeded");
                        }
                    } else {
                        System.out.println("Can not add a different crop to non-empty bin");
                        showErrorPopup("Can not add a different crop to non-empty bin");
                    }
                }
            }
        });



        // Clear bin functionality
        // lots of error checking
        Button clearBin = new Button("Clear Bin");
        clearBin.setOnMouseClicked(event -> {
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                if (!selectedData.isEmpty()){
                    binController.clearBin(binTable.getSelectionModel().getSelectedItem().getDbId());
                    showPopup("Bin Cleared");
                    binTable.refresh();
                } else {
                    System.out.println("Bin is empty");
                    showErrorPopup("Bin is empty");
                }
            }
            else {
                System.out.println("Select a bin");
                showErrorPopup("Select a bin");
            }
        });


        // current crop table
        Label currentCropLabel = new Label("Current Crop");
        currentCropLabel.getStyleClass().add("page-label");

        TableColumn<Crop, ObjectId> currentCropIDCol = new TableColumn<Crop, ObjectId>("Crop ID");
        currentCropIDCol.setPrefWidth(70);
        currentCropIDCol.setCellValueFactory(
                new PropertyValueFactory<Crop, ObjectId>("DbId"));

        TableColumn<Crop, String> currentCropTypeCol = new TableColumn<Crop, String>("Crop Type");
        currentCropTypeCol.setMinWidth(70);
        currentCropTypeCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropType"));

        TableColumn<Crop, String> currentCropVarietyCol = new TableColumn<Crop, String>("Crop Variety");
        currentCropVarietyCol.setMinWidth(110);
        currentCropVarietyCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropVariety"));

        TableColumn<Crop, Double> currentBushelWeightCol = new TableColumn<Crop, Double>("Bushel Weight");
        currentBushelWeightCol.setMinWidth(110);
        currentBushelWeightCol.setCellValueFactory(
                new PropertyValueFactory<Crop, Double>("bushelWeight"));

        currentCropTable.getColumns().addAll(currentCropIDCol, currentCropTypeCol, currentCropVarietyCol, currentBushelWeightCol);


        // last crop table
        Label lastCropLabel = new Label("Last Crop");
        lastCropLabel.getStyleClass().add("page-label");

        TableColumn<Crop, ObjectId> lastCropIDCol = new TableColumn<Crop, ObjectId>("Crop ID");
        lastCropIDCol.setPrefWidth(70);
        lastCropIDCol.setCellValueFactory(
                new PropertyValueFactory<Crop, ObjectId>("DbId"));

        TableColumn<Crop, String> lastCropTypeCol = new TableColumn<Crop, String>("Crop Type");
        lastCropTypeCol.setMinWidth(70);
        lastCropTypeCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropType"));

        TableColumn<Crop, String> lastCropVarietyCol = new TableColumn<Crop, String>("Crop Variety");
        lastCropVarietyCol.setMinWidth(110);
        lastCropVarietyCol.setCellValueFactory(
                new PropertyValueFactory<Crop, String>("cropVariety"));

        TableColumn<Crop, Double> lastBushelWeightCol = new TableColumn<Crop, Double>("Bushel Weight");
        lastBushelWeightCol.setMinWidth(110);
        lastBushelWeightCol.setCellValueFactory(
                new PropertyValueFactory<Crop, Double>("bushelWeight"));

        lastCropTable.getColumns().addAll(lastCropIDCol, lastCropTypeCol, lastCropVarietyCol, lastBushelWeightCol);

        // double-click a bin to open details
        // lots of error checking
        binTable.setOnMouseClicked(event -> {
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2) {
                if (selectedData.getCurrentCrop() != null){
                    currentCropData.clear();
                    currentCropData.add(selectedData.getCurrentCrop());
                } else {
                    currentCropData.clear();
                }
                if (selectedData.getLastCrop() != null){
                    lastCropData.clear();
                    lastCropData.add(selectedData.getLastCrop());
                } else {
                    lastCropData.clear();
                }
                currentCropTable.setItems(currentCropData);
                currentCropTable.refresh();

                lastCropTable.setItems(lastCropData);
                lastCropTable.refresh();

                stage.setScene(binCropScene);

            }});


        Button cropToBin = new Button("Back To Bin");
        cropToBin.setOnMouseClicked(e -> {
            stage.setScene(binScene);
        });


        binCropPage.getChildren().addAll(cropToBin, currentCropLabel, currentCropTable, lastCropLabel, lastCropTable);

        Button binsBackToMain = new Button("Back To Main");
        binsBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });

        // open bin details by select a bin and click View Bin
        // lots of error checking
        Button viewBin = new Button("View Bin");
        viewBin.setOnMouseClicked(event -> {
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (selectedData != null) {
                if (selectedData.getCurrentCrop() != null){
                    currentCropData.clear();
                    currentCropData.add(selectedData.getCurrentCrop());
                } else {
                    currentCropData.clear();
                }
                if (selectedData.getLastCrop() != null){
                    lastCropData.clear();
                    lastCropData.add(selectedData.getLastCrop());
                } else {
                    lastCropData.clear();
                }
                currentCropTable.setItems(currentCropData);
                currentCropTable.refresh();

                lastCropTable.setItems(lastCropData);
                lastCropTable.refresh();

                stage.setScene(binCropScene);
            } else {
                System.out.println("Select a Bin");
                showErrorPopup("Select a bin");
            }
        });

        // unload crop a page and scene
        VBox unloadPage = new VBox(15);
        Scene unloadScene = new Scene(unloadPage);

        Label unloadCropPageTitle = new Label();
        TextField unloadBinID = new TextField();
        Label unloadGranInputLabel = new Label("How much grain to unload?");
        unloadGranInputLabel.getStyleClass().add("text-field-label");
        TextField unloadGrainInput = new TextField();
        CheckBox unloadInputBushels = new CheckBox("Crop is in bushels?");
        Button submitUnloadInfo = new Button("Submit");
        Button cancelUnloadCrop = new Button("Cancel");
        cancelUnloadCrop.setOnMouseClicked(event -> {
            stage.setScene(binScene);
        });

        Label space5 = new Label("\t\t");
        HBox submitAndCancelBox3 = new HBox(submitUnloadInfo, space5, cancelUnloadCrop);

        // submit unloading
        submitUnloadInfo.setOnMouseClicked(event -> {
            int grain = -1;
            try {
                grain = Integer.parseInt(unloadGrainInput.getText());
            } catch (Exception b){
                System.out.println("Invalid grain input");
                showErrorPopup("Invalid grain input");
            }
            if (grain != -1) {
                binController.unload(new ObjectId(unloadBinID.getText()), Integer.parseInt(unloadGrainInput.getText()), unloadInputBushels.isSelected());
                stage.setScene(binScene);
                unloadGrainInput.setText("");
                unloadInputBushels.setSelected(false);
                binTable.refresh();
                stage.setScene(binScene);
                showPopup("Crop Unloaded");
            }
        });

        // open unloading page
        // lots of error checking
        Button unload = new Button("Unload");
        unload.setOnMouseClicked(event -> {
            GrainBin selectedData = binTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                if (!selectedData.isEmpty()){
                    unloadBinID.setText(selectedData.getDbId().toString());
                    unloadCropPageTitle.setText("Unload crop from bin named (" + selectedData.getBinName() + ")");
                    unloadCropPageTitle.getStyleClass().add("page-label");
                    stage.setScene(unloadScene);
                } else {
                    System.out.println("Bin is empty");
                    showErrorPopup("Bin is empty");
                }
            } else {
                System.out.println("Select a bin");
                showErrorPopup("Select a bin");
            }
        });

        unloadPage.getChildren().addAll(unloadCropPageTitle, unloadGranInputLabel, unloadGrainInput, unloadInputBushels, submitAndCancelBox3);

        // boxes for css styling :(
        HBox binFunctionBar = new HBox();
        binFunctionBar.getStyleClass().add("function-bar");
        HBox.setHgrow(binFunctionBar, Priority.ALWAYS);
        HBox.setHgrow(binsBackToMain, Priority.ALWAYS);
        binFunctionBar.getChildren().addAll(addBin, deleteBin, addCrop, viewBin, unload, clearBin);
        HBox topBar = new HBox();
        binsBackToMain.getStyleClass().add("back-button");
        topBar.getStyleClass().add("top-bar");
        topBar.getChildren().addAll(binFunctionBar, binsBackToMain);

        VBox binTableContainer = new VBox();
        binTableContainer.getStyleClass().add("table-container");
        binTableContainer.getChildren().add(binTable);
        binPage.getChildren().addAll(topBar, binTableContainer);


        //css
        addBinScene.getStylesheets().add(getClass().getClassLoader().getResource("bin.css").toExternalForm());
        binCropScene.getStylesheets().add(getClass().getClassLoader().getResource("bin.css").toExternalForm());
        addCropScene.getStylesheets().add(getClass().getClassLoader().getResource("bin.css").toExternalForm());
        unloadScene.getStylesheets().add(getClass().getClassLoader().getResource("bin.css").toExternalForm());
        this.getChildren().addAll(binPage);
    }


    /**
     * Sets the primary stage and scenes of the application. Specifically handles the
     * menu and bin scenes. Additionally, applies a stylesheet to the bin scene.
     *
     * @param stage The primary JavaFX stage where scenes will be displayed.
     * @param MenuScene The scene representing the application's main menu.
     * @param binScene The scene representing the bin view of the application and to which the style sheet is to be applied.
     *
     */
    public void setStageMenu(Stage stage, Scene MenuScene, Scene binScene){
        this.stage = stage;
        this.MenuScene = MenuScene;
        this.binScene = binScene;
        binScene.getStylesheets().add(getClass().getClassLoader().getResource("bin.css").toExternalForm());
    }


    /**
     * A function that throws the content of the string passed to it as an error message.
     * @param content: The content of the error message popup
     */
    private void showErrorPopup(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText("INVALID");
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Double lbsToBushels(double lbs, double bushelWeight){
        return (lbs/bushelWeight);
    }
    private void showPopup(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("MESSAGE");
        alert.setHeaderText("CONFIRM MESSAGE");
        alert.setContentText(content);
        alert.showAndWait();
    }
}
