package org.entities;
/*
The Class Year divides each Field by its individual year
A field will have a year attribute that contains everything done on it that year
A field's year gets updated yearly(funny right)
A field has a list of multiples years
 */

import org.InitialFarm.Chemical;

import org.InitialFarm.Crop;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.LinkedList;

public class Year implements DatabaseInterface<Year>{

    private String fieldName;

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    /**
     * The current year e.g. 2013
     */

    private final ObjectId dbID;
    private final int year;

    /**
     * The current crop on the field
     */
    private Crop crop;

    /**
     * The date the crop was planted
     */
    private LocalDate seeding_date;

    /**
     * The seeding rate of the crop in lbs/acre
     */
    private double seeding_rate;

    /**
     * The application rate of the fertilizer in lbs/acre
     */
    private double fertilizer_rate;

    /**
     * The chemical that was most recently sprayed on the field
     */
    private Chemical chemical_sprayed;

    /**
     * The date the field was last sprayed
     */
    private LocalDate spraying_date;

    private double yield;
    @Override
    public Document classToDoc() {

        Document newDoc = new Document();
        newDoc.append("fieldName", this.fieldName);
        newDoc.append("year", this.year);
        newDoc.append("newYear", this.new_year);
        if (this.crop != null) {
            newDoc.append("crop", this.crop.getDbId());
        }
        else{
            newDoc.append("crop", null);
        }
        newDoc.append("seedingDate", this.seeding_date);
        newDoc.append("seedingRate", this.seeding_rate);
        newDoc.append("fertilizerRate", this.fertilizer_rate);
        newDoc.append("yield",this.yield);
        if (this.chemical_sprayed != null) {
            newDoc.append("chemicalSprayed", this.chemical_sprayed.getDbId());
        }
        else{
            newDoc.append("chemicalSprayed", null);
        }
        newDoc.append("sprayingDate", this.spraying_date);
        newDoc.append("harvestDate", this.harvest_date);
        newDoc.append("endOfYear", this.end_of_year);

        ArrayList<ObjectId> chemical_records = new ArrayList<>();
        for (ChemicalRecord chemical_record : this.chemical_records) {
            chemical_records.add(chemical_record.getDbId());
        }

        newDoc.append("chemical_records", chemical_records);

        ArrayList<ObjectId> task_records = new ArrayList<>();
        for (TaskRecord task_record : this.task_records) {
            task_records.add(task_record.getDbId());
        }
        newDoc.append("task_records", task_records);

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

    public void setChemical_sprayed(Chemical chemical_sprayed) {
        this.chemical_sprayed = chemical_sprayed;
    }
    @Override
    public ObjectId getDbId() {
        return dbID;
    }

    @Override
    public boolean isDatabase() {
        return false;
    }


    /**
     * The list of chemicals that have been sprayed on the
     * field over the year and their dates
     */
    // bookmark
    private final LinkedList<ChemicalRecord> chemical_records;

    public String getChemicalRecordData(LinkedList<ChemicalRecord> chemicalRecords) {
        StringBuilder data = new StringBuilder();
        for (ChemicalRecord chemicalRecord : chemicalRecords) {
            if (!data.isEmpty()) {
                data.append("\n");
            }
            data.append(chemicalRecord.getData());
        }
        return data.toString();
    }

    /**
     * The list of tasks that have been done over the year and
     * their dates
     */
    private final LinkedList<TaskRecord> task_records;

    /**
     * The date the field was harvested
     */
    private LocalDate harvest_date;

    /**
     * The date the field year ended
     */
    private LocalDate end_of_year;

    /**
     * The date the field year begun
     */
    private final LocalDate new_year;


    /**
     * Initializes a year
     * @param _year the current year
     * @param new_year_date  the date the new year begins
     */
    public Year(ObjectId dbid, int _year, LocalDate new_year_date){
        dbID = dbid;
        year = _year;
        this.new_year = new_year_date;
        this.chemical_records = new LinkedList<>();
        this.task_records = new LinkedList<>();
    }

    /**
     * Return the current year
     * @return the current year as an int
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Return the date the year started
     * @return the Date the year started
     */
    public LocalDate getNewYearDate() {
        return this.new_year;
    }

    /**
     * The crop being grown that year
     * @return the crop grown that year
     */
    public Crop getCrop() {
        return this.crop;
    }

    /**
     * Updates the crop to be planted on the field that year
     * @param crop the crop to be planted that year
     */
    public void setCrop(Crop crop) { this.crop = crop; }

    /**
     * Return the date the crops got seeded/planted
     * @return the Date the crop got planted
     */
    public LocalDate getSeeding_date(){
        return this.seeding_date;
    }

    public void setHarvest_date(LocalDate harvest_date) {
        this.harvest_date = harvest_date;
    }

    public void setSpraying_date(LocalDate spraying_date) {
        this.spraying_date = spraying_date;
    }
    public void setSeeding_date( LocalDate newSeedingDate){
        this.seeding_date = newSeedingDate;
    }

    /**
     * Returns the rate of seed application
     * @return the seed application rate
     */
    public double getSeeding_rate(){
        return this.seeding_rate;
    }

    /**
     * Sets the seeding rate
     * @param newSeedingRate the rate at which the crop was seeded
     */
    public void setSeeding_rate(double newSeedingRate){
        this.seeding_rate = newSeedingRate;
    }

    /**
     * Returns the rate of fertilizer application
     * @return the fertilizer application rate
     */
    public double getFertilizer_rate(){
        return this.fertilizer_rate;
    }

    /**
     * Sets the fertilizer rate
     * @param newFertilizerRate the rate at which the fertilizer was applied
     */
    public void setFertilizer_rate(double newFertilizerRate){
        this.fertilizer_rate = newFertilizerRate;
    }

    /**
     * Returns the date of the most recent chemical application
     * @return the date of the most recent chemical application
     */
    public LocalDate getSprayingDate(){
        return this.spraying_date;
    }

    /**
     * Returns the Chemical that was most recently sprayed
     * @return a chemical
     */
    public Chemical getChemical_sprayed(){
        return this.chemical_sprayed;
    }

    /**
     * Performs a spraying task by updating the chemical sprayed and updating
     * the list of chemical sprayed
     * @param chemRecord the chemical to be sprayed
     */
    public void addChemicalRecord(ChemicalRecord chemRecord){

        this.chemical_records.add(chemRecord);
    }

    /**
     * Returns the chemical history of the field
     * @return the chemical history complete with dates
     */
    public LinkedList<ChemicalRecord> getChemical_records() { return chemical_records; }

    /**
     * Add a task to the list of field tasks that have been done
     *
     */
    public void addTaskRecord(TaskRecord newTaskRec){

        this.task_records.add(newTaskRec);
    }

    /**
     * Returns the Task history of the field
     * @return the task history of the field complete with dates
     */
    public LinkedList<TaskRecord> getTaskRecords() {
        return task_records;
    }

    /**
     * Sets the harvest date
     * @param harvestDate the date of the harvest
     */
    public void harvest( LocalDate harvestDate){
        this.harvest_date = harvestDate;
    }

    /**
     * Obtains the harvest date
     * @return the date of the harvest
     */
    public LocalDate getHarvestDate() { return harvest_date; }

    /**
     * Obtains the end of year date
     * @return the date the year ends
     */
    public LocalDate getEndOfYear(){ return this.end_of_year; }

    /**
     * Sets the end of the year date
     * @param endOfYearDate the date the year ends
     */
    public void endOfYear( LocalDate endOfYearDate){ this.end_of_year = endOfYearDate; }

    /**
     * Displays all the information on a field for a given year
     * @return the field information for the year
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(year +
                ",\n new year starting date = " + new_year +
                ",\n crop = " + crop +
                ",\n seeding_date = " + seeding_date +
                ",\n seeding_rate = " + seeding_rate + "lbs/acre" +
                ",\n fertilizer_rate = " + fertilizer_rate + "lbs/acre" +
                ",\n spraying_date = " + spraying_date +
                ",\n harvest_date = " + harvest_date +
                ",\n end_of_year = " + end_of_year +
                ",\n chemical_records\n  ");

        for (ChemicalRecord chemical_record : chemical_records) {
            result.append(chemical_record.getChemical()).append("  ").append(chemical_record.getDate()).append("\n");
        }

        result.append("\n task_records\n  ");

        for (TaskRecord task_record : task_records) {
            result.append(task_record.getTask()).append("  ").append(task_record.getDate()).append("\n");
        }

        return result.toString();
    }



    public void setEnd_of_year(LocalDate endOfYear) {
        this.end_of_year = endOfYear;
    }

    public void setYield(Double yield) {
        this.yield = yield;
    }

    public double getYield(){
        return this.yield;
    }
}
