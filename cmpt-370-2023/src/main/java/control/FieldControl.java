package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.InitialFarm.Chemical;
import org.InitialFarm.Crop;
import org.InitialFarm.dataManager;
import org.bson.types.ObjectId;
import org.entities.ChemicalRecord;
import org.entities.Field;
import org.entities.Year;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.now;


// need to see history of crop planted
// need to record what crop planted when finished planting
public class FieldControl {


    /**
     * list of all fields
     */
    public ObservableList<Field> fieldList;

    /**
     * list of all years/crop data
     */
    public ObservableList<Year> yearList;


    private dataManager dataManager = new dataManager();


    public ObservableList<String> cropType;

    public FieldControl(){
        // fetch from database
        fieldList = dataManager.initializeFieldsFromDB();
        yearList = dataManager.initializeYearsFromDB();
        cropType = FXCollections.observableArrayList();
    }

    /**
     * add a field to database
     * @param field_id field ID
     * @param field_name field name
     * @param field_size field size
     * @param field_location field location
     */
    public void addField(String field_id, String field_name, double field_size, String field_location)
    {
        //check if Field already exists
        boolean fieldExists= false;


        for (Field Field : fieldList) {
            if (Field.getID().equals(field_id)) {
                fieldExists = true;
            }
        }
        // if it doesn't, add it. If it does, report it.
        if (!fieldExists){
            Field field = new Field(null, field_id, field_name, field_size, field_location);
            Field dbField = dataManager.saveClass(field);
            fieldList.add(dbField);
        }
        else {
            System.out.println("There already is a field with the desired ID");
        }
    }

    /**
     * edit a field
     * @param old_id old field id
     * @param new_field_id new field id
     * @param new_field_name new field name
     * @param new_field_size new field size
     * @param new_field_location new field location
     */
    public void editField(String old_id, String new_field_id, String new_field_name, double new_field_size, String new_field_location){
        Field edited = null;
        boolean fieldIdAlreadyExists = false;

        // check if Field to be edited exists at all
        for (Field field : fieldList) {
            if (field.getID().equals(old_id)) {
                edited = field;
            }
        }
        if (edited == null)
        {
            System.out.println("Field to be edited could not be found!");

        }
        // if it does
        else{
            // check if its new id is identical to another Field's id.
            for (Field field : fieldList) {
                if (field.getID().equals(new_field_id) && field != edited) {
                    fieldIdAlreadyExists=true;
                    break;
                }
            }
            if (!fieldIdAlreadyExists)
            {
                edited.setID(new_field_id);
                edited.setName(new_field_name);
                edited.setSize(new_field_size);
                edited.setLocation(new_field_location);
                dataManager.updateClass(edited);
            }
            else{
                System.out.println("The suggested new Field ID is already in use.");
            }
        }
    }

    /**
     * Delete a field
     * @param field_id field ID
     */
    public void deleteField(String field_id){
        Field deleted = null;
        for (Field field : fieldList){
            if (field.getID().equals(field_id)){
                deleted = field;
                break;
            }
        }
        if (deleted != null){
            ArrayList<Year> fieldYears = new ArrayList<>(deleted.getYears());
            yearList.removeIf(y -> {
                if (fieldYears.contains(y)) {
                    dataManager.removeClass(y);
                    return true;
                }
                return false;
            });
            dataManager.removeClass(deleted);
            fieldList.remove(deleted);
        }
        else {
            System.out.println("Field with ID (" + field_id + ") does not exist");
        }
    }

    /**
     * add crop to field
     * @param field_id field id
     * @param crop crop to be added
     * @param seedingRate seeding
     * @param seedingDate seeding date
     */
    public void addCrop(String field_id, Crop crop, double seedingRate, LocalDate seedingDate){

        Field fieldSearched = null;
        for (Field field : fieldList){
            if (field.getID().equals(field_id)){
                fieldSearched = field;
                break;
            }
        }
        if (fieldSearched != null){
            // need to make a year object, and add crop to year
            if (fieldSearched.getCurrent_Year() == null){
                Year cropYear = new Year(null, LocalDate.now().getYear(), LocalDate.now());
                Year dbYear = dataManager.saveClass(cropYear);
                dbYear.setCrop(crop);
                dbYear.setSeeding_rate(seedingRate);
                dbYear.setSeeding_date(seedingDate);
                fieldSearched.setCurrentYear(dbYear);
                fieldSearched.addYear(dbYear);
                dataManager.updateClass(dbYear);
                dataManager.updateClass(fieldSearched);
                addToYearList();
            }
            else {
                System.out.println("Farm is currently full of crop");
            }
        }
        else {
            System.out.println("Can't find field with ID (" + field_id + ")");
        }
    }

    /**
     * harvest current crop on field
     * @param fieldID field id
     * @param yield yield
     */
    public void harvest(String fieldID,Double yield){
        Field searchedField = null;
        for (Field field : fieldList) {
            if (field.getID().equals(fieldID)) {
                searchedField = field;
                break;
            }
        }
        if (searchedField != null){
            if (searchedField.getCurrent_Year() != null) {
                searchedField.getCurrent_Year().harvest(LocalDate.now());
                searchedField.getCurrent_Year().setYield(yield);

                Year currentYear = searchedField.getCurrent_Year();
                for (Year year : yearList){
                    if (year.getDbId().equals(currentYear.getDbId())){
                        year = currentYear;
                        break;
                    }
                }
                dataManager.updateClass(searchedField.getCurrent_Year());
                searchedField.setCurrentYear(null);
                dataManager.updateClass(searchedField);
            } else {
                System.out.println("Field with ID (" + fieldID + ") is already harvested or no crop is planted.");
            }
        } else {
            System.out.println("Can't find field with ID (" + fieldID + ")");
        }
    }

    /**
     * make a crop object and add to database
     * @param dbid database id
     * @param cropType crop type
     * @param cropVariety crop variety
     * @param bushelWeight bushel weight
     * @return a crop object that is saved in database
     */
    public Crop makeCrop(ObjectId dbid,String cropType, String cropVariety, double bushelWeight){
        Crop baseCrop = new Crop(dbid, cropType, cropVariety, bushelWeight);
        Crop dbCrop = dataManager.saveClass(baseCrop);
        return dbCrop;

    }

    /**
     * add chemical to data
     * @param fieldID
     * @param fertilizerRate
     * @param chemicalSprayed
     * @param chemicalGroup
     * @param sprayingDate
     */
    public void addChemical(String fieldID, double fertilizerRate, String chemicalSprayed, String chemicalGroup, LocalDate sprayingDate){
        Field searchedField = null;
        for (Field field : fieldList) {
            if (field.getID().equals(fieldID)) {
                searchedField = field;
                break;
            }
        }
        if (searchedField != null){
            if (searchedField.getCurrent_Year() != null) {
                // need to also add a chemical group to data base
                List<String> chemGroup = new ArrayList<>();
                chemGroup.add(chemicalGroup);
                Chemical chemical = new Chemical(null, chemicalSprayed, chemGroup);
                Chemical dbChemical = dataManager.saveClass(chemical);
                ChemicalRecord chemicalRecord = new ChemicalRecord(null, dbChemical, sprayingDate);
                ChemicalRecord dbChemRec = dataManager.saveClass(chemicalRecord);

                searchedField.getCurrent_Year().setFertilizer_rate(fertilizerRate);
                searchedField.getCurrent_Year().addChemicalRecord(dbChemRec);
                dataManager.updateClass(searchedField.getCurrent_Year());
                dataManager.updateClass(searchedField);
            } else {
                System.out.println("Field with ID (" + fieldID + ") is already harvested or no crop is planted.");
            }
        } else {
            System.out.println("Can't find field with ID (" + fieldID + ")");
        }
    }

    /**
     * add a new crop type to the list
     * @param crop_type new crop type to be added
     */
    public void addCropType(String crop_type){
        boolean existed = false;
        for (String type : cropType){
            if (type.equals(crop_type)){
                existed = true;
                break;
            }
        }
        if (!existed){
            cropType.add(crop_type);
        } else {
            System.out.println("Crop type already existed");
        }
    }

    /**
     * add all years too yearlist
     */
    private void addToYearList(){
        yearList.clear();
        for (Field field : fieldList){
            yearList.addAll(field.getYears());
        }
    }
}
