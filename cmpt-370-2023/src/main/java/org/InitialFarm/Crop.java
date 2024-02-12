package org.InitialFarm;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.entities.DatabaseInterface;

public class Crop implements DatabaseInterface<Crop> {
    private String cropType;
    private String cropVariety;
    private Double bushelWeight;
    private ObjectId dbID;



    public Crop(ObjectId dbid,String cropType, String cropVariety, double bushelWeight){
        dbID = dbid;
        this.cropType = cropType; // the type of crop (corn, soybeans, etc.)
        this.cropVariety = cropVariety; // the variety of the crop (Pioneer 1234, etc.)
        this.bushelWeight = bushelWeight; // the weight of a bushel of the crop(in lbs)
    }

    public String getCropType(){ return this.cropType;}
    public void  setCropType(String newCroptype){
        this.cropType = newCroptype;
    }

    public String getCropVariety(){ return this.cropVariety;}
    public void  setCropVariety(String newCropVariety){
        this.cropVariety = newCropVariety;
    }

    public double getBushelWeight(){ return this.bushelWeight;}
    public void  setBushelWeight(double newBushelWeight){
        this.bushelWeight = newBushelWeight;
    }


    @Override
    public Document classToDoc() {
        Document newDoc = new Document();
        newDoc.append("cropType", this.cropType);
        newDoc.append("cropVariety", this.cropVariety);
        newDoc.append("bushelWeight", this.bushelWeight);
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
}
