package org.openjfx.javafxmavenarchetypes;
import control.BinControl;
import control.FieldControl;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.InitialFarm.Crop;
import org.bson.types.ObjectId;
import org.entities.ChemicalRecord;
import org.entities.Field;
import org.entities.Year;

import java.time.LocalDate;
import java.util.*;


public class FieldView extends StackPane {

    private Stage stage;    // stage
    private Scene MenuScene ;   // menu scene
    private Scene fieldScene;   // field scene
    private VBox cropPage = new VBox(); // crop page container
    private Scene cropScene = new Scene(cropPage);  // crop scene

    private VBox harvestPage = new VBox(15);    // harvest page

    private Scene harvestScene = new Scene(harvestPage);    // harvest scene
    private VBox fieldPage = new VBox();    // field page container



    private FieldControl fieldController = new FieldControl();  // field controller
    private TableView<Field> fieldTable = new TableView<Field>();   // field table view
    private ObservableList<Field> fieldData = fieldController.fieldList;    // field data

    private TableView<Year> yearTable = new TableView<>();      // crop history table view
    private ObservableList<Year> yearData = fieldController.yearList;   // crop history data

    private TableView<Year> currentYearTable = new TableView<>();   // current year table view

    private ObservableList<Year> currentYearData = FXCollections.observableArrayList(); // current year data




    public FieldView(){

        // field table view columns
        TableColumn<Field, String> fieldIDCol = new TableColumn<Field, String>("Field ID");
        fieldIDCol.setMinWidth(130);
        fieldIDCol.setCellValueFactory(
                new PropertyValueFactory<Field, String>("ID")
        );

        TableColumn<Field, String> fieldNameCol = new TableColumn<Field, String>("Field Name");
        fieldNameCol.setMinWidth(130);
        fieldNameCol.setCellValueFactory(
                new PropertyValueFactory<Field, String>("name")
        );

        TableColumn<Field, Double> fieldSizeCol = new TableColumn<Field, Double>("Field Size (Acre)");
        fieldSizeCol.setMinWidth(130);
        fieldSizeCol.setCellValueFactory(
                new PropertyValueFactory<Field, Double>("size")
        );

        TableColumn<Field, String> fieldLocationCol = new TableColumn<Field, String>("Field Location");
        fieldLocationCol.setMinWidth(130);
        fieldLocationCol.setCellValueFactory(
                new PropertyValueFactory<Field, String>("location")
        );



        fieldTable.setItems(fieldData);
        fieldTable.getColumns().addAll(fieldIDCol, fieldNameCol, fieldSizeCol, fieldLocationCol);



        // add field page container and scene
        VBox addFieldBox = new VBox(15);
        Scene addFieldScene = new Scene(addFieldBox,300,250);

        Label addFieldPageTitle = new Label("Add New Field");
        addFieldPageTitle.getStyleClass().add("page-label");

        Label fieldIDInputLabel = new Label("Field ID:");
        fieldIDInputLabel.getStyleClass().add("text-field-label");
        TextField fieldIDInput = new TextField();

        Label fieldNameInputLabel = new Label("Field Name:");
        fieldNameInputLabel.getStyleClass().add("text-field-label");
        TextField fieldNameInput = new TextField();

        Label fieldSizeInputLabel = new Label("Field Size (number): ");
        fieldSizeInputLabel.getStyleClass().add("text-field-label");
        TextField fieldSizeInput = new TextField();

        Label fieldLocationInputLabel = new Label("Field Location:");
        fieldLocationInputLabel.getStyleClass().add("text-field-label");
        TextField fieldLocation = new TextField();

        Button submitFieldInfo = new Button("Submit");
        Button addFieldCancel = new Button("Cancel");
        addFieldCancel.setOnMouseClicked(event -> {
            stage.setScene(fieldScene);
        });

        Label space1 = new Label("\t\t");
        HBox submitAndCancelBox1 = new HBox(submitFieldInfo, space1, addFieldCancel);


        addFieldBox.getChildren().addAll(addFieldPageTitle, fieldIDInputLabel, fieldIDInput, fieldNameInputLabel,
                fieldNameInput, fieldSizeInputLabel, fieldSizeInput, fieldLocationInputLabel, fieldLocation, submitAndCancelBox1);
        Button addField = new Button("Add Field");
        addField.setOnMouseClicked(e ->{
            stage.setScene(addFieldScene);
        });

        // submit field info
        submitFieldInfo.setOnMouseClicked(e ->{
            double fieldSize = -1.0;
            try {
                fieldSize = Double.parseDouble(fieldSizeInput.getText());
            } catch (Exception b){
                System.out.println("Invalid field size");
                showErrorPopup("Invalid field Size");
            }
            if (fieldSize != -1.0){
                fieldController.addField(fieldIDInput.getText(),fieldNameInput.getText(), fieldSize, fieldLocation.getText());
                showPopup("Added Field!");
                fieldIDInput.setText("");
                fieldNameInput.setText("");
                fieldSizeInput.setText("");
                fieldLocation.setText("");
                stage.setScene(fieldScene);
            }
        });

        // Harvest page
        Label harvestlabel = new Label("Please enter harvest information in pounds");
        harvestlabel.getStyleClass().add("page-label");
        TextField harvestInput = new TextField("Enter in pounds (lb) ex: 15 000");
        harvestInput.getStyleClass().add("text-field-label");
        Button submitHarvest = new Button("Submit");
        Button cancelHarvest = new Button("Cancel");
        cancelHarvest.setOnMouseClicked(event -> {
            stage.setScene(fieldScene);
        });
        // submit harvest info
        // lots of error checking
        submitHarvest.setOnMouseClicked(event -> {
            Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                try {
                    fieldController.harvest(selectedData.getID(), Double.parseDouble(harvestInput.getText()));
                    yearTable.refresh();
                    stage.setScene(fieldScene);
                    showPopup("Harvested field (" + selectedData.getName() + ") successfully");
                } catch (Exception e){
                    System.out.println("Invalid harvest input");
                    showErrorPopup("Invalid harvest input");
                }
            }
            else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }
        });
        HBox buttonBox = new HBox(15,submitHarvest, cancelHarvest);
        harvestPage.getChildren().addAll(harvestlabel,harvestInput,buttonBox);


        // edit field page container and scene
        VBox fieldEditBox = new VBox(15);
        Scene editFieldScene = new Scene(fieldEditBox,300,250);

        Button editField = new Button("Edit Field");
        editField.setOnMouseClicked(e -> {
            stage.setScene(editFieldScene);
        });


        Label editFieldPageTitle = new Label();

        Label editFieldIDInputLabel = new Label("Field ID:");
        editFieldIDInputLabel.getStyleClass().add("text-field-label");
        TextField idInputEdit = new TextField();

        Label editFieldNameInputLabel = new Label("Field Name:");
        editFieldNameInputLabel.getStyleClass().add("text-field-label");
        TextField fieldNameFEdit = new TextField();

        Label editFieldSizeInputLabel = new Label("Field Size (number): ");
        editFieldSizeInputLabel.getStyleClass().add("text-field-label");
        TextField fieldSizeEdit = new TextField();

        Label editFieldLocationInputLabel = new Label("Field Location:");
        editFieldLocationInputLabel.getStyleClass().add("text-field-label");
        TextField locationEdit = new TextField();

        Button submitFieldEdit = new Button("Submit");
        Button editFieldCancel = new Button("Cancel");
        editFieldCancel.setOnMouseClicked(event -> {
            stage.setScene(fieldScene);
        });

        Label space2 = new Label("\t\t");
        HBox submitAndCancelBox2 = new HBox(submitFieldEdit, space2, editFieldCancel);


        fieldEditBox.getChildren().addAll(editFieldPageTitle, editFieldIDInputLabel, idInputEdit, editFieldNameInputLabel,
                fieldNameFEdit, editFieldSizeInputLabel, fieldSizeEdit, editFieldLocationInputLabel, locationEdit,submitAndCancelBox2);
        // submit field edit info
        submitFieldEdit.setOnMouseClicked(e ->{
            double fieldSize = -1.0;
            try {
                fieldSize = Double.parseDouble(fieldSizeEdit.getText());
            } catch (Exception b){
                System.out.println("Invalid field size");
                showErrorPopup("Invalid field size");
            }
            if (fieldSize != -1.0){
                fieldController.editField(fieldTable.getSelectionModel().getSelectedItem().getID(),
                        idInputEdit.getText(), fieldNameFEdit.getText(), fieldSize,
                        locationEdit.getText());
                showPopup("Field Edited");
                stage.setScene(fieldScene);
                fieldTable.refresh();
            }
        });

        // open edit field page
        editField.setOnMouseClicked(e ->{
            Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                editFieldPageTitle.setText("Edit Field With ID (" + selectedData.getID() + ")");
                editFieldPageTitle.getStyleClass().add("page-label");
                idInputEdit.setText(selectedData.getID());
                fieldNameFEdit.setText(selectedData.getName());
                fieldSizeEdit.setText(Double.toString(selectedData.getSize()));
                locationEdit.setText(selectedData.getLocation());
                stage.setScene(editFieldScene);
            }
            else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }
        });

        Button fieldsBackToMain = new Button("Back To Main");
        fieldsBackToMain.setOnMouseClicked(e -> {
            stage.setScene(MenuScene);
        });

        // delete field function
        Button deleteField = new Button("Delete Field");
        deleteField.setOnAction(event -> {
            if (fieldTable.getSelectionModel().getSelectedItem() != null){
                fieldController.deleteField(fieldTable.getSelectionModel().getSelectedItem().getID());
                showPopup("Field Deleted");
                fieldTable.refresh();
            }
            else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }
        });


        // add crop page container and scene
        VBox addCropBox = new VBox(15);
        Scene addCropScene = new Scene(addCropBox,300,250);

        Label addCropPageTitle = new Label();
        TextField addCropFieldID = new TextField();
        Label cropTypeLabel = new Label("Select Crop Type:");
        cropTypeLabel.getStyleClass().add("text-field-label");
        ComboBox<String> cropTypeInput = new ComboBox<>();
        cropTypeInput.getItems().addAll(fieldController.cropType);

        Label newCropTypeLabel = new Label("Or Add New Crop Type:");
        newCropTypeLabel.getStyleClass().add("text-field-label");
        TextField newCropTypeInput = new TextField();

        Label space5 = new Label("\t");
        Label space6 = new Label("\t\t");
        Label space7 = new Label("\t");
        HBox groupBox1 = new HBox(cropTypeLabel, space5, cropTypeInput, space6, newCropTypeLabel, space7, newCropTypeInput);

        Label cropVarietyLabel = new Label("Select Crop Variety:");
        cropVarietyLabel.getStyleClass().add("text-field-label");

        ComboBox<String> cropVarietyInput = new ComboBox<>();
        cropVarietyInput.getItems().addAll("LibertyLink", "RoundupReady", "Navigator", "ClearField", "All Other Grains");



        fieldController.cropType.addListener((ListChangeListener<String>) change -> {
            cropTypeInput.setItems(fieldController.cropType);
        });

        Label bushelWeightLabel = new Label("Bushel Weight (lbs):");
        bushelWeightLabel.getStyleClass().add("text-field-label");
        TextField bushelWeight = new TextField();

        Label seedingRateInputLabel = new Label("Seeding Rate (lbs/acre):");
        seedingRateInputLabel.getStyleClass().add("text-field-label");

        TextField seedingRateInput = new TextField();
        Label seedingDateLabel = new Label("Seeding Date");
        seedingDateLabel.getStyleClass().add("text-field-label");

        DatePicker seedingDateInput = new DatePicker();
        Button submitCropInfo = new Button("Submit");
        Button addCropCancel = new Button("Cancel");
        addCropCancel.setOnMouseClicked(event -> {
            stage.setScene(fieldScene);
        });

        Label space3 = new Label("\t\t");
        HBox submitAndCancelBox3 = new HBox(submitCropInfo, space3, addCropCancel);

        addCropBox.getChildren().addAll(addCropPageTitle, groupBox1, cropVarietyLabel,
                cropVarietyInput, bushelWeightLabel, bushelWeight, seedingRateInputLabel, seedingRateInput,
                seedingDateLabel, seedingDateInput, submitAndCancelBox3);
        // open add crop page
        // lots of error checking
        Button addCrop = new Button("Add Crop");
        addCrop.setOnMouseClicked(e ->{
            Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                if (selectedData.getCurrent_Year() != null){
                    System.out.println("Farm is currently full of crop");
                    showErrorPopup("Farm is currently full of crop");
                } else {
                    addCropFieldID.setText(selectedData.getID());
                    addCropPageTitle.setText("Add Crop to Field named (" + selectedData.getName() + ")");
                    addCropPageTitle.getStyleClass().add("page-label");
                    stage.setScene(addCropScene);
                }
            }
            else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }

        });

        // submit crop info
        // lots of error checking
        submitCropInfo.setOnMouseClicked(e ->{

            double bWeight = -1.0;
            double seedingRate = -1.0;
            try {
                bWeight = Double.parseDouble(bushelWeight.getText());
                seedingRate = Double.parseDouble(seedingRateInput.getText());
            } catch (Exception error){
                System.out.println("Invalid bushel weight or seeding rate input");
                showErrorPopup("Invalid bushel weight or seeding rate input");
            }
            if (seedingDateInput.getValue() == null){
                System.out.println("Need to pick a seeding date");
                showErrorPopup("Need to pick a seeding date");
            } else {
                if (seedingRate != -1.0 && bWeight != -1.0) {
                    Crop crop;
                    if (!newCropTypeInput.getText().isEmpty()){
                        fieldController.addCropType(newCropTypeInput.getText());
                    }
                    if (cropTypeInput.getValue() == null){
                        crop = fieldController.makeCrop(null, newCropTypeInput.getText(), cropVarietyInput.getValue(), bWeight);
                    }
                    else {
                        crop = fieldController.makeCrop(null, cropTypeInput.getValue(), cropVarietyInput.getValue(), bWeight);
                    }
                    fieldController.addCrop(addCropFieldID.getText(), crop, seedingRate, seedingDateInput.getValue());

                    // clear the form
                    cropTypeInput.setValue(null);
                    newCropTypeInput.setText("");
                    cropVarietyInput.setValue(null);
                    bushelWeight.setText("");
                    seedingRateInput.setText("");
                    seedingDateInput.setValue(null);
                    stage.setScene(fieldScene);
                    showPopup("Added Crop");
                }
            }
        });


        // harvest function
        // lots of error checking
        Button harvest = new Button("Harvest");
        harvest.setOnMouseClicked(event ->{
            Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                if (selectedData.getCurrent_Year() != null){
                    if (selectedData.getCurrent_Year().getCrop() != null){
                        stage.setScene(harvestScene);
                    }
                    else{
                        showErrorPopup("Error, we do not have a current crop");
                    }
                }
                else{
                    showErrorPopup("Error, we do not have a current crop");
                }

            }
            else{
                showErrorPopup("Error, we do not have a current crop");
            }
        });


        // add chemical page and scene
        VBox addChemPage = new VBox(15);
        Scene addChemScene = new Scene(addChemPage,300,250);

        Label addChemPageTitle = new Label();
        TextField chemFieldID = new TextField();

        Label fertilizerInputLabel = new Label("Fertilizer Rate (lbs/acre):");
        fertilizerInputLabel.getStyleClass().add("text-field-label");
        TextField fertilizerInput = new TextField();

        Label chemicalSprayedInputLabel = new Label("Chemical Sprayed:");
        chemicalSprayedInputLabel.getStyleClass().add("text-field-label");
        TextField chemSprayedInput = new TextField();

        Label chemGroupInputLabel = new Label("Chemical Group:");
        chemGroupInputLabel.getStyleClass().add("text-field-label");
        TextField chemGroupInput = new TextField();

        Label sprayDateLabel = new Label("Spray Date:");
        sprayDateLabel.getStyleClass().add("text-field-label");
        DatePicker sprayDate = new DatePicker();

        Button submitChemInfo = new Button("Submit");
        Button addChemCancel = new Button("Cancel");
        addChemCancel.setOnMouseClicked(event -> {
            stage.setScene(fieldScene);
        });

        Label space4 = new Label("\t\t");
        HBox submitAndCancelBox4 = new HBox(submitChemInfo, space4, addChemCancel);

        // open add chemical form
        Button sprayChemical = new Button("Add Chemical Sprayed");
        sprayChemical.setOnMouseClicked(event -> {
            Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
            if (selectedData != null){
                if (selectedData.getCurrent_Year() != null) {
                    chemFieldID.setText(selectedData.getID());
                    addChemPageTitle.setText("Add Chemical to Field named (" + selectedData.getName() + ")");
                    addChemPageTitle.getStyleClass().add("page-label");
                    stage.setScene(addChemScene);
                } else {
                    System.out.println("Field with ID (" + selectedData.getID() + ") current has no crop to spray chemical");
                    showErrorPopup("Field with ID (" + selectedData.getID() + ") current has no crop to spray chemical");
                }
            }
            else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }
        });

        // submit chemical form
        submitChemInfo.setOnMouseClicked(event -> {
            double fertilizerRate = -1.0;
            try {
                fertilizerRate = Double.parseDouble(fertilizerInput.getText());
            } catch(Exception b){
                System.out.println("Invalid fertilizer rate");
                showErrorPopup("Invalid fertilizer rate");
            }

            if (sprayDate.getValue() == null){
                System.out.println("Need to pick a spraying date");
                showErrorPopup("Need to pick a spraying date");
            } else {
                if (fertilizerRate != -1.0){
                    fieldController.addChemical(chemFieldID.getText(), fertilizerRate,
                            chemSprayedInput.getText(), chemGroupInput.getText(), sprayDate.getValue());
                    chemSprayedInput.setText("");
                    fertilizerInput.setText("");
                    chemGroupInput.setText("");
                    sprayDate.setValue(null);
                    stage.setScene(fieldScene);
                    showPopup("Added Chemical");
                }
            }
        });

        addChemPage.getChildren().addAll(addChemPageTitle, fertilizerInputLabel, fertilizerInput, chemicalSprayedInputLabel,
                chemSprayedInput, chemGroupInputLabel, chemGroupInput, sprayDateLabel, sprayDate, submitAndCancelBox4);


        // double click to open field info
        Label cropPageTitle = new Label();
        cropPageTitle.getStyleClass().add("page-label");
        fieldTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
                cropPageTitle.setText("Field page with field named (" + selectedData.getName() + ")");
                if (selectedData.getCurrent_Year() != null){
                    currentYearData.clear();
                    currentYearData.add(selectedData.getCurrent_Year());
                }
                else {
                    currentYearData.clear();
                }
                currentYearTable.setItems(currentYearData);
                currentYearTable.refresh();
                yearTable.refresh();
                stage.setScene(cropScene);
            }
        });


        Label cropHistoryLabel = new Label("All Crop History");
        cropHistoryLabel.getStyleClass().add("page-label");

        Button cropBackToField = new Button("Back To Field");
        cropBackToField.setOnMouseClicked(event -> {
            stage.setScene(fieldScene);
        });

        // open field details by using view field button
        Button viewField = new Button("View Field");
        viewField.setOnMouseClicked(event -> {
            Field selectedData = fieldTable.getSelectionModel().getSelectedItem();
            if (selectedData != null) {
                if (selectedData.getCurrent_Year() != null){
                    currentYearData.clear();
                    currentYearData.add(selectedData.getCurrent_Year());
                }
                else {
                    currentYearData.clear();
                }
                currentYearTable.setItems(currentYearData);
                currentYearTable.refresh();
                yearTable.refresh();
                cropPageTitle.setText("Field page with field named (" + selectedData.getName() + ")");
                stage.setScene(cropScene);
            } else {
                System.out.println("Select a field");
                showErrorPopup("Select a field");
            }
        });


        // year history table columns
        TableColumn<Year, String> fieldNameCol2 = new TableColumn<Year, String>("Field Name");
        fieldNameCol2.setPrefWidth(100);
        fieldNameCol2.setCellValueFactory(
                new PropertyValueFactory<Year, String>("fieldName")
        );

        TableColumn<Year, ObjectId> yearIDCol = new TableColumn<Year, ObjectId>("Year ID");
        yearIDCol.setPrefWidth(70);
        yearIDCol.setCellValueFactory(
                new PropertyValueFactory<Year, ObjectId>("DbId")
        );

        TableColumn<Year, Integer> yearCol = new TableColumn<Year, Integer>("Year");
        yearCol.setMinWidth(70);
        yearCol.setCellValueFactory(
                new PropertyValueFactory<Year, Integer>("year")
        );

        TableColumn<Year, String> cropTypeCol = new TableColumn<Year, String>("Crop Type");
        cropTypeCol.setMinWidth(100);
        cropTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCrop().getCropType()));

        TableColumn<Year, String> cropVarietyCol = new TableColumn<Year, String>("Crop Variety");
        cropVarietyCol.setMinWidth(100);
        cropVarietyCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCrop().getCropVariety()));

        TableColumn<Year, Double> bushelWeightCol = new TableColumn<Year, Double>("Bushel Weight");
        bushelWeightCol.setMinWidth(110);
        bushelWeightCol.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getCrop().getBushelWeight()).asObject()
        );

        TableColumn<Year, Double> seedingRateCol = new TableColumn<Year, Double>("Seeding Rate");
        seedingRateCol.setMinWidth(100);
        seedingRateCol.setCellValueFactory(
                new PropertyValueFactory<Year, Double>("seeding_rate")
        );

        TableColumn<Year, LocalDate> seedingDateCol = new TableColumn<Year, LocalDate>("Seeding Date");
        seedingDateCol.setMinWidth(100);
        seedingDateCol.setCellValueFactory(
                new PropertyValueFactory<Year, LocalDate>("seeding_date")
        );

        TableColumn<Year, Double> fertilizerRateCol = new TableColumn<Year, Double>("Fertilizer Rate");
        fertilizerRateCol.setMinWidth(100);
        fertilizerRateCol.setCellValueFactory(
                new PropertyValueFactory<Year, Double>("fertilizer_rate")
        );

        TableColumn<Year, String> chemicalRecordCol = new TableColumn<>("Chemical Records");
        chemicalRecordCol.setMinWidth(350);
        chemicalRecordCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getChemicalRecordData(cellData.getValue().getChemical_records()))
        );

        TableColumn<Year,Double> harvestYieldCol = new TableColumn<>("Harvest Yield (lbs)");
        harvestYieldCol.setMinWidth(100);
        harvestYieldCol.setCellValueFactory(
                new PropertyValueFactory<Year, Double>("yield")
        );

        TableColumn<Year, LocalDate> harvestDateCol = new TableColumn<Year, LocalDate>("Harvest Date");
        harvestDateCol.setMinWidth(100);
        harvestDateCol.setCellValueFactory(
                new PropertyValueFactory<Year, LocalDate>("HarvestDate")
        );

        yearTable.setItems(yearData);
        yearTable.getColumns().addAll(fieldNameCol2, yearIDCol, yearCol, cropTypeCol, cropVarietyCol,
                bushelWeightCol, seedingRateCol, seedingDateCol, fertilizerRateCol, chemicalRecordCol,harvestYieldCol, harvestDateCol);


        // current year table columns
        Label currentYearLabel = new Label("Current Year Crop");
        currentYearLabel.getStyleClass().add("page-label");

        TableColumn<Year, String> fieldNameCol3 = new TableColumn<Year, String>("Field Name");
        fieldNameCol3.setPrefWidth(100);
        fieldNameCol3.setCellValueFactory(
                new PropertyValueFactory<Year, String>("fieldName")
        );

        TableColumn<Year, ObjectId> yearIDCol2 = new TableColumn<Year, ObjectId>("Year ID");
        yearIDCol2.setPrefWidth(70);
        yearIDCol2.setCellValueFactory(
                new PropertyValueFactory<Year, ObjectId>("DbId")
        );

        TableColumn<Year, Integer> yearCol2 = new TableColumn<Year, Integer>("Year");
        yearCol2.setMinWidth(70);
        yearCol2.setCellValueFactory(
                new PropertyValueFactory<Year, Integer>("year")
        );

        TableColumn<Year, String> cropTypeCol2 = new TableColumn<Year, String>("Crop Type");
        cropTypeCol2.setMinWidth(100);
        cropTypeCol2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCrop().getCropType()));

        TableColumn<Year, String> cropVarietyCol2 = new TableColumn<Year, String>("Crop Variety");
        cropVarietyCol2.setMinWidth(100);
        cropVarietyCol2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCrop().getCropVariety()));

        TableColumn<Year, Double> bushelWeightCol2 = new TableColumn<Year, Double>("Bushel Weight");
        bushelWeightCol2.setMinWidth(110);
        bushelWeightCol2.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getCrop().getBushelWeight()).asObject()
        );

        TableColumn<Year, Double> seedingRateCol2 = new TableColumn<Year, Double>("Seeding Rate");
        seedingRateCol2.setMinWidth(100);
        seedingRateCol2.setCellValueFactory(
                new PropertyValueFactory<Year, Double>("seeding_rate")
        );

        TableColumn<Year, LocalDate> seedingDateCol2 = new TableColumn<Year, LocalDate>("Seeding Date");
        seedingDateCol2.setMinWidth(100);
        seedingDateCol2.setCellValueFactory(
                new PropertyValueFactory<Year, LocalDate>("seeding_date")
        );

        TableColumn<Year, Double> fertilizerRateCol2 = new TableColumn<Year, Double>("Fertilizer Rate");
        fertilizerRateCol2.setMinWidth(100);
        fertilizerRateCol2.setCellValueFactory(
                new PropertyValueFactory<Year, Double>("fertilizer_rate")
        );

        TableColumn<Year, String> chemicalRecordCol2 = new TableColumn<>("Chemical Records");
        chemicalRecordCol2.setMinWidth(350);
        chemicalRecordCol2.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getChemicalRecordData(cellData.getValue().getChemical_records()))
        );

        TableColumn<Year, LocalDate> harvestDateCol2 = new TableColumn<Year, LocalDate>("Harvest Date");
        harvestDateCol2.setMinWidth(100);
        harvestDateCol2.setCellValueFactory(
                new PropertyValueFactory<Year, LocalDate>("HarvestDate")
        );

        currentYearTable.setItems(currentYearData);
        currentYearTable.getColumns().addAll(fieldNameCol3, yearIDCol2, yearCol2, cropTypeCol2, cropVarietyCol2,
                bushelWeightCol2, seedingRateCol2, seedingDateCol2, fertilizerRateCol2, chemicalRecordCol2, harvestDateCol2);


        // Map to store colors for each crop type
        Map<String, String> cropTypeColors = new HashMap<>();
        // Custom row factory to set the background color based on crop type
        yearTable.setRowFactory(new Callback<TableView<Year>, TableRow<Year>>() {
            @Override
            public TableRow<Year> call(TableView<Year> param) {
                return new TableRow<Year>() {
                    @Override
                    protected void updateItem(Year item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setStyle(""); // Reset style for empty cells
                        } else {
                            String cropType = item.getCrop().getCropType();

                            // Check if the color is already assigned for this crop type
                            if (!cropTypeColors.containsKey(cropType)) {
                                // If not, generate a random color and store it in the map
                                String randomColor = generateRandomColor();
                                cropTypeColors.put(cropType, randomColor);
                            }

                            // Set the background color based on the stored color for this crop type
                            setStyle("-fx-background-color: " + cropTypeColors.get(cropType) + ";");
                        }
                    }
                };
            }
        });

//        // Custom cell factory to set the background color based on crop type
//        cropTypeCol.setCellFactory(new Callback<TableColumn<Year, String>, TableCell<Year, String>>() {
//            @Override
//            public TableCell<Year, String> call(TableColumn<Year, String> param) {
//                return new TableCell<Year, String>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//
//                        if (item == null || empty) {
//                            setText(null);
//                            setStyle(""); // Reset style for empty cells
//                        } else {
//                            setText(item);
//
//                            // Check if the color is already assigned for this crop type
//                            if (!cropTypeColors.containsKey(item)) {
//                                // If not, generate a random color and store it in the map
//                                String randomColor = generateRandomColor();
//                                cropTypeColors.put(item, randomColor);
//                            }
//
//                            // Set the background color based on the stored color for this crop type
//                            setStyle("-fx-background-color: " + cropTypeColors.get(item) + ";");
//                        }
//                    }
//                };
//            }
//        });

        HBox topBar= new HBox();
        HBox fieldFunctionsBar = new HBox();
        fieldFunctionsBar.getStyleClass().add("function-bar");
        fieldsBackToMain.getStyleClass().add("back-button");
        topBar.getStyleClass().add("top-bar");
        HBox.setHgrow(fieldFunctionsBar, Priority.ALWAYS);
        HBox.setHgrow(fieldsBackToMain, Priority.ALWAYS);
        topBar.getChildren().addAll(fieldFunctionsBar, fieldsBackToMain);


        cropPage.getChildren().addAll(cropBackToField, cropPageTitle, currentYearLabel, currentYearTable, cropHistoryLabel, yearTable);


        fieldFunctionsBar.getChildren().addAll(addField, editField, viewField, deleteField, addCrop, harvest, sprayChemical);
        VBox taskTableContainer = new VBox();
        taskTableContainer.getStyleClass().add("table-container");
        taskTableContainer.getChildren().add(fieldTable);

        fieldPage.getChildren().addAll(topBar, taskTableContainer);

        // css
        addFieldScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
        editFieldScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
        addChemScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
        addCropScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
        cropScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
        harvestScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
        this.getChildren().addAll(fieldPage);
    }



    /**
     * Sets the primary stage and scenes of the application. Specifically handles the
     * menu and field scenes. Additionally, applies a stylesheet to the field scene.
     *
     * @param stage The primary JavaFX stage where scenes will be displayed.
     * @param main The scene representing the application's main menu.
     * @param field The scene representing the field view of the application and to which the style sheet is to be applied.
     */
    public void setStageMainField(Stage stage, Scene main, Scene field){
        this.stage = stage;
        this.MenuScene = main;
        this.fieldScene = field;
        fieldScene.getStylesheets().add(getClass().getClassLoader().getResource("field.css").toExternalForm());
    }


    /**
     * A function that throws the content of the string passed to it as an error message.
     * @param content: The content of the error message popup
     */
    private void showErrorPopup(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR MESSAGE");
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showPopup(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("MESSAGE");
        alert.setHeaderText("CONFIRM MESSAGE");
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Method to generate a random color in hexadecimal format
    private String generateRandomColor() {
        int minThreshold = 600;
        int r, g, b;
        Random random = new Random();
        int sum;
        do {
            r = random.nextInt(180,256);
            g = random.nextInt(180,256);
            b = random.nextInt(180,256);
            sum = r + g + b;
        } while (sum < minThreshold);
        return String.format("#%02x%02x%02x", r, g, b);
    }

}