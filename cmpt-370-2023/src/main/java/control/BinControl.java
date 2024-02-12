package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.InitialFarm.Crop;
import org.InitialFarm.GrainBin;
import org.InitialFarm.dataManager;
import org.bson.types.ObjectId;

public class BinControl {
    /**
     * list of Bins
     */
    public ObservableList<GrainBin> binList;
    public ObservableList<String> cropType;

    private dataManager dataManager = new dataManager();



    /**
     * constructor
     */
    public BinControl(){
        // fetch from data base
        binList = dataManager.initializeGrainBinsFromDB();
        cropType = FXCollections.observableArrayList();
    }

    /**
     * add bin to database
     * @param bin_name bin name
     * @param bin_size bin size
     * @param bin_location bin location
     * @param hopper bin has hopper?
     * @param fan bin has fan?
     */
    public void addBin(String bin_name, int bin_size, String bin_location, boolean hopper, boolean fan) {
        GrainBin Bin = new GrainBin(null, bin_name, bin_location, bin_size, hopper, fan);
        GrainBin dbBin = dataManager.saveClass(Bin);
        binList.add(dbBin);
    }

    /**
     * delete a bin from database
     * @param bin_id bin ID
     */
    public void deleteBin(ObjectId bin_id){
        GrainBin deleted = null;
        for (GrainBin bin : binList){
            if (bin.getDbId().equals(bin_id)){
                deleted = bin;
                break;
            }
        }
        if (deleted != null){
            dataManager.removeClass(deleted);
            binList.remove(deleted);

        }
        else {
            System.out.println("Bin with ID (" + bin_id + ") does not exist");
        }
    }

    /**
     * add crop to bin
     * @param bin_id bin Id
     * @param cropType crop type
     * @param grain how much grain to add
     * @param inputBushels is crop in bushel?
     * @param clean is crop clean?
     * @param tough is crop tough?
     */
    public void addCrop(ObjectId bin_id, Crop cropType, int grain, boolean inputBushels, boolean clean, boolean tough){
        GrainBin binSearched = null;
        for (GrainBin bin : binList){
            if (bin.getDbId().equals(bin_id)){
                binSearched = bin;
                break;
            }
        }
        if (binSearched != null){
            binSearched.addCrop(cropType, grain, inputBushels, clean, tough);
            dataManager.updateClass(binSearched);
        }
        else {
            System.out.println("Can't find bin with ID (" + bin_id + ")");
        }
    }

    /**
     * clear all grains in the bin
     * @param bin_id bin ID
     */
    public void clearBin(ObjectId bin_id) {
        GrainBin binSearched = null;
        for (GrainBin bin : binList) {
            if (bin.getDbId().equals(bin_id)) {
                binSearched = bin;
                break;
            }
        }
        if (binSearched != null){
            if (!binSearched.isEmpty()) {
                binSearched.clearBin();
                dataManager.updateClass(binSearched);
            } else {
                System.out.println("Bin is empty!");
            }
        }
        else{
            System.out.println("Cant find bin with ID (" + bin_id + ")");
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
    public Crop makeCrop(ObjectId binID, int grain, boolean inputBushels, ObjectId dbid,String cropType, String cropVariety, double bushelWeight){
        GrainBin binSearched = null;
        for (GrainBin bin : binList){
            if (bin.getDbId().equals(binID)){
                binSearched = bin;
                break;
            }
        }
        double grainWeight = grain;
        Crop dbCrop = null;
        if (binSearched != null){
            if (!inputBushels){
                grainWeight = lbsToBushels(grain, bushelWeight);
            }

            if (grainWeight + binSearched.getCropBushels() > binSearched.getBinSize()){
                System.out.println("Maximum Capacity Exceeded");
                showErrorPopup("Maximum Capacity Exceeded");
            } else {
                Crop baseCrop = new Crop(dbid, cropType, cropVariety, bushelWeight);
                dbCrop = dataManager.saveClass(baseCrop);
            }
        } else{
            System.out.println("Cant find bin with ID (" + binID + ")");
        }
        return dbCrop;
    }

    /**
     * add a new crop type to the list
     * @param crop_type new crop type
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
     * unload crop from bin
     * @param binID
     * @param grain
     * @param isBushel
     */
    public void unload(ObjectId binID, int grain, boolean isBushel){
        GrainBin binSearched = null;
        for (GrainBin bin : binList){
            if (bin.getDbId().equals(binID)){
                binSearched = bin;
                break;
            }
        }
        if (binSearched != null){
            binSearched.unloadBin(grain, isBushel);
            dataManager.updateClass(binSearched);
        } else{
            System.out.println("Cant find bin with ID (" + binID + ")");
        }
    }

    /**
     * convert lbs to bushel
     * @param lbs crop weight in pounds
     * @param bushelWeight 1 bushel weight in pounds
     * @return
     */
    private Double lbsToBushels(double lbs, double bushelWeight){
        return (lbs/bushelWeight);
    }

    /**
     * error popup
     * @param content
     */
    private void showErrorPopup(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText("INVALID");
        alert.setContentText(content);
        alert.showAndWait();
    }

}
