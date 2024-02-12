package org.entities;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;

public class TaskRecord implements DatabaseInterface<TaskRecord>{
    Task task;
    LocalDate date;

    ObjectId dbId;

    public TaskRecord(ObjectId dbid,Task _task, LocalDate _date){
        dbId = dbid;
        task = _task;
        date = _date;
    }
    public Task getTask(){ return this.task; }
    public LocalDate getDate(){ return this.date; }

    @Override
    public Document classToDoc() {
        Document newDoc = new Document();
        newDoc.append("task", this.task.getDbId());
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
        return dbId;
    }

    @Override
    public boolean isDatabase() {
        return false;
    }
}
