package org.entities;

import org.bson.types.ObjectId;

import java.time.LocalDate;


public class Employee extends User{

    public Employee(ObjectId iddb, String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob, boolean owner){
        super(iddb,id, user_email, user_password, first_name, last_name, dob, owner);
    }

    @Override
    public String toString() {
        String userType = this.getClass().getSimpleName();
        return "User type: " + userType + "\n" + super.toString();
    }

}
