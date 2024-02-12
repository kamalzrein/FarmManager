package org.entities;

import org.bson.Document;
import org.bson.types.ObjectId;



public interface DatabaseInterface<T> {
    Document classToDoc();

    Document docToClass();



    void save();

    void sync();

    //changed below to getDbId bcz other classes have ID and getID methods was leading to class
    ObjectId getDbId();


    boolean isDatabase();
}
