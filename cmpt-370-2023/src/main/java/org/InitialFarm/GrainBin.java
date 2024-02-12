package org.InitialFarm;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.entities.DatabaseInterface;

public class GrainBin implements DatabaseInterface<GrainBin> {
    //dynamic
    private Crop currentCrop; // the crop that is currently in the bin
    private String currentCropType = ""; // the type of crop that is currently in the bin
    private Crop lastCrop; // the crop that was last in the bin
    private Double cropBushels = 0.0; // the amount of grain in the bin in bushels
    private Double cropLbs = 0.0; // the amount of grain in the bin in lbs
    private boolean tough; // refers to moisture content of grain (if it is too high it can cause issues and is considered tough
    private boolean clean; // refers to whether the grain has been cleaned or not

    //these values do not change after creation
    private final String binName; // the name of the bin
    private final String binLocation; // the location of the bin
    private final int binSize; // the size of the bin in bushels
    private final boolean hopper; // whether the bin has a hopper or not
    private final boolean fan; // whether the bin has a fan or not
    private ObjectId dbID; // the id of the bin in the database

    /**
     * Constructor for a grain bin
     * @param dbID
     * @param binName
     * @param binLocation
     * @param binSize
     * @param hopper
     * @param fan
     */
    public GrainBin(ObjectId dbID,String binName, String binLocation, int binSize, boolean hopper, boolean fan){
        this.dbID = dbID;
        this.binName = binName;
        this.binLocation = binLocation;
        this.binSize = binSize;
        this.hopper = hopper;
        this.fan = fan;
    }

    /**
     * Sets the current crop in the bin
     * @param crop
     */
    public void setCurrentCrop(Crop crop){
        this.currentCrop = crop;
    }

    /**
     * Unloads grain from the bin
     * @param grain: amount of grain to be unloaded
     * @param isBushels: unit of measure of the grain amount (if true in bushels, if false in lbs)
     */
    public void unloadBin(int grain, boolean isBushels){
        if(isBushels){
            this.cropBushels -= grain;
            this.cropLbs = bushelsToLbs(this.cropBushels);
        }else {
            this.cropLbs -= grain;
            this.cropBushels = lbsToBushels(this.cropLbs);
        }
        if (this.cropBushels <= 0){
            this.cropBushels = 0.0;
            this.cropLbs = 0.0;
            System.out.println("Bin is empty now");
        }
    }

    public Crop getLastCrop(){return lastCrop;}
    public Crop getCurrentCrop(){return currentCrop;}
    public Double getCropLbs(){return  this.cropLbs;}
    public Double getCropBushels(){return this.cropBushels;}
    public int getBinSize(){return binSize;}
    public String getBinLocation(){return binLocation;}
    public String getBinName(){ return binName;}
    public boolean isTough(){return this.tough;}
    public boolean isClean(){return this.clean;}
    public boolean isHopper(){ return hopper;}
    public boolean isFan(){ return fan; }
    public String getCurrentCropType(){
        return this.currentCropType;
    }

    /**
     * Checks if the bin is empty
     * @return true if the bin is empty, false otherwise
     */
    public Boolean isEmpty(){
        if (cropBushels <= 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * Adds a crop to a bin
     * checks if the bin is empty, if it is it adds the crop to the bin
     * otherwise it checks if the crop being added is the same as the crop already in the bin
     * if it is it adds the crop to the bin
     * @param cropToBeAdded : a crop to be added to the bin.
     * @param grain: amount of the crop is being added.
     * @param inputBushels: Unit of measure of the grain amount ( if true in bushels, if false in lbs).
     * @param clean: whether the crop has been cleaned or not.
     * @param tough: whether grain is moist enough for it to cause issues or not.
     */
    public void addCrop(Crop cropToBeAdded, int grain, boolean inputBushels, boolean clean, boolean tough){
        if (isEmpty() && cropToBeAdded != currentCrop){
            if (this.currentCrop != null) {
                this.lastCrop = this.currentCrop;
            }
            this.currentCrop = cropToBeAdded;
            this.currentCropType = currentCrop.getCropType();
        }else if (cropToBeAdded != this.currentCrop){
            // TODO throw exception
            System.out.println("Can't add different crop type to bin when bin is not empty");
            return;
        }
        if (inputBushels){
            fillBushels(grain);
        }else {
            fillLbs(grain);
        }
        this.clean = clean;
        this.tough = tough;
    }

    /**
     * Adds crop to bin in lbs and updates bushels accordingly
     * @param lbs
     */
    private void fillLbs( int lbs){
        if (lbs > binSize){
            //TODO throw exception
            System.out.println("Capacity Exceeded");
            return;
        }
        this.cropLbs += lbs;
        this.cropBushels += lbsToBushels(lbs);
    }

    /**
     * Adds crop to bin in bushels and updates lbs accordingly
     * @param bushels
     */
    private void fillBushels(int bushels){
        if (cropBushels + bushels > binSize){
            //TODO throw exception
            System.out.println("Capacity Exceeded");
            return;
        }
        this.cropBushels += bushels;
        this.cropLbs += bushelsToLbs(bushels);
    }

    /**
     * Converts lbs to bushels
     * @param lbs
     * @return
     */
    private Double lbsToBushels(double lbs){
        return (lbs/currentCrop.getBushelWeight());
    }

    /**
     * Converts bushels to lbs
     * @param bushels
     * @return
     */
    private Double bushelsToLbs(double bushels){
        return (bushels*currentCrop.getBushelWeight());
    }

    /**
     * Clears the bin
     */
    public void clearBin(){
        cropBushels = (double) 0;
        cropLbs = (double) 0;
    }

    /***
     * Converts the bin to a document for use in database
     * @return
     */
    @Override
    public Document classToDoc() {
        Document newDoc = new Document();
        newDoc.append("binName", this.binName);
        newDoc.append("binLocation", this.binLocation);
        newDoc.append("binSize", this.binSize);
        newDoc.append("currentCropType", this.currentCropType);
        newDoc.append("hopper", this.hopper);
        newDoc.append("fan", this.fan);
        if (this.currentCrop != null){
            newDoc.append("currentCrop", this.currentCrop.getDbId());
        }
        else{
            newDoc.append("currentCrop", null);
        }
        if (this.lastCrop != null){
            newDoc.append("lastCrop", this.lastCrop.getDbId());
        }
        else{
            newDoc.append("lastCrop", null);
        }
        newDoc.append("cropBushels", this.cropBushels);
        newDoc.append("cropLbs", this.cropLbs);
        newDoc.append("tough", this.tough);
        newDoc.append("clean", this.clean);
        return newDoc;
    }

    @Override
    public Document docToClass() {
        return null;
    }

    @Override
    public void save() {

    }

    @Override
    public void sync() {

    }

    @Override
    public ObjectId getDbId() {
        return dbID;
    }

    @Override
    public boolean isDatabase() {
        return false;
    }

    public void setLastCrop(Crop crop) {
        this.lastCrop = crop;
    }

    public void setCurrentCropType(String cropType){
        this.currentCropType = cropType;
    }

    public void setCropBushels(Double cropBushels) {
        this.cropBushels = cropBushels;
    }

    public void setCropLbs(Double cropLbs) {
        this.cropLbs = cropLbs;
    }

    public void setTough(Boolean tough) {
        this.tough = tough;
    }

    public void setClean(Boolean clean) {
        this.clean = clean;
    }
}
