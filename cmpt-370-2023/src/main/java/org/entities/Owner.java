package org.entities;

import org.bson.types.ObjectId;

import java.time.LocalDate;


public class Owner extends Employee {

    public static final Boolean owner = false;

    public Owner(ObjectId iddb, String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob){
        super(iddb,id, user_email, user_password, first_name, last_name, dob, owner);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
