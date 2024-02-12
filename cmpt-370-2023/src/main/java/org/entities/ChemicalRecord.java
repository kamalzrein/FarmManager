package org.entities;

import org.InitialFarm.Chemical;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;

public class ChemicalRecord implements DatabaseInterface<ChemicalRecord> {
    Chemical chemical;
    LocalDate date;

    ObjectId dbID;

    public ChemicalRecord(ObjectId dbid, Chemical _chemical, LocalDate _date){
        dbID = dbid;
        chemical = _chemical;
        date = _date;
    }

    public String toString(){
        return "Chemical Sprayed: " + chemical.getChemicalName() +
                "\nChemical Group: " + chemical.getChemicalGroup().get(0) +  "\nSpraying Date: " + date;
    }

    public String getData(){
        return chemical.getChemicalName() + " | " + getDate();
    }

    public Chemical getChemical(){ return this.chemical; }
    public LocalDate getDate(){ return this.date; }

    @Override
    public Document classToDoc() {
        Document newDoc = new Document();
        newDoc.append("chemical", this.chemical.getDbId());
        newDoc.append("date", this.date);
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
