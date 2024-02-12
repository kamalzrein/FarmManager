package org.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task implements DatabaseInterface<Task>{
    /**
     * task ID
     */
    private String ID;

    /**
     * The unique ID of the Task for the DataBase
     */
    private  ObjectId dbID;

    /**
     * task name
     */
    private String taskName;

    /**
     * task description
     */
    private String description;

    /**
     * date created
     */
    private final LocalDateTime date;

    /**
     * task due date
     */
    private LocalDateTime dueDate;


    /**
     * list of staffs working on this task
     */
    private final ObservableList<User> staffList;

    private boolean isCompleted = false;
    private LocalDateTime completionDate;
    private boolean isPaused = false;
    private LocalDateTime pauseDate;



    /**
     * constructor
     * @param id task ID
     * @param task_name task name
     * @param descr task description
     * @param due_date task due date
     */
    public Task(ObjectId iddb,String id, String task_name, String descr, LocalDateTime due_date){
        dbID = iddb;
        ID = id;
        taskName = task_name;
        description = descr;
        dueDate = due_date;
        date = LocalDateTime.now();
        staffList = FXCollections.observableArrayList();
    }

    /**
     * get task ID
     * @return task id
     */
    public String getID() {
        return ID;
    }

    /**
     * get task name
     * @return task name
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * get task description
     * @return task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * get task creation date
     * @return creation date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * get task due date
     * @return task due date
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * get list of all staffs working on this task
     * @return list of staffs
     */
    public ObservableList<User> getStaffList() {
        return staffList;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public LocalDateTime getPauseDate() {
        return pauseDate;
    }


    /**
     * set task status to completed
     */
    public void markAsCompleted(boolean completed){
        if (isPaused) {
            isPaused = false;
        }
        if (completed){
            completionDate = LocalDateTime.now();
        }
        isCompleted = completed;
    }

    /**
     * pause task
     * @param is_paused true/false
     */
    public void pauseTask(boolean is_paused){
        if (is_paused){
            isCompleted = false;
            pauseDate = LocalDateTime.now();
        }
        isPaused = is_paused;
    }


    /**
     * set task ID
     * @param ID task ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * set task name
     * @param taskName task name
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * set task description
     * @param description task description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * set due date
     * @param dueDate due date
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }


    /**
     * check to see if task is overdue
     * @return true/false
     */
    public boolean isOverDue(){
        LocalDateTime today = LocalDateTime.now();
        return dueDate.isBefore(today);
    }

    /**
     * add staff to this task
     * @param staff staff to be added
     */
    public void addStaff(User staff){
        // make sure staff is not null
        if (staff != null){
            boolean found = false;
            // make sure task is not already assigned to user
            // I would have used list.contains, but I am concerned about it not being the same object when db does things, it would be identical details but not same object instance
            for (User staffIter : staffList) {
                if (staffIter.getID().equals(staff.getID())) {
//                    System.out.println("The staff member "+staff.getFirstName() + " " + staff.getLastName() + " is already assigned to the task '"+ this.taskName+ "' ! \n Here is the current staff list: " + staffList);
                    found = true;
                    break;
                }
            }
            if (!found) {
                staffList.add(staff);
                staff.addTask(this);
//                System.out.println("Staff member " + staff.getFirstName() + " " + staff.getLastName() + " successfully added to the task '"+ this.taskName+ "' ! \n Here is the current staff list: " + staffList);
            }
        }
        else {
            System.out.println("The staff member you are trying to add is null!");
        }
    }


    /**
     * remove a staff from list with staff id
     * @param staff_id id of staff
     */
    public void removeStaff(String staff_id) {
        if (staff_id != null){
            for (User user : staffList) {
                if (user.getID().equals(staff_id)) {
                    staffList.remove(user);
                    user.removeTask(this.ID);
                    return;
                }
            }
        }
        else {
            System.out.println("The id of the staff member you are trying to add is null!");
        }

    }

    /**
     * remove all staffs from the list
     */
    public void removeAllStaff(){
        staffList.clear();
    }

    /**
     * get status of task
     * @return status of task
     */
    public String getStatus(){
        String taskStatus;
        if (isCompleted()){
            taskStatus = "Completed";
        }
        else if (isPaused()){
            taskStatus = "Paused";
        }
        else{
            taskStatus = "Incomplete";
        }
        return taskStatus;
    }


    public void setStatus(String status){
        switch (status) {
            case "Completed" -> markAsCompleted(true);
            case "Paused" -> pauseTask(true);
            case "Incomplete" -> markAsCompleted(false);
            default -> System.out.println("Status parameter must be (Completed/Paused/In Progress/Incomplete");
        }
    }

    /**
     * get a string representation of task
     * @return string representation of task
     */
    public String toString(){
        StringBuilder result = new StringBuilder("Task ID: " + getID() +
                "\nTask name: " + getTaskName() +
                "\nTask dbID: " + getDbId() +
                "\nTask Description: " + getDescription() +
                "\nCreated: " + getDate() +
                "\nStatus: " + getStatus() +
                "\nCompletion Date: " + getCompletionDate() +
                "\nPause Date: " + getPauseDate() +
                "\nStaffs: [");
        if (!staffList.isEmpty()) {
            for (User staff : staffList) {
                result.append(staff.getFirstName()).append(", ");
            }
        }
        else
            result.append(" empty ");
        result.append( "]");
        return result.toString();
    }


    /**
     * @return
     *
     *  newObj =  new Task(objectDoc.getObjectId("_id"),objectDoc.getString("taskID"), objectDoc.getString("task_name"),
     *                     objectDoc.getString("task_description"), objectDoc.getDate("task_dueDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
     *         }
     */
    @Override
    public Document classToDoc() {



        Document newDoc = new Document();
        ArrayList<ObjectId> staffList = new ArrayList<>();
        for (User staff : this.getStaffList()) {
            staffList.add(staff.getDbId());
        }


        newDoc.append("taskID",this.getID());
        newDoc.append("task_name",this.getTaskName());
        newDoc.append("task_description",this.getDescription());
        newDoc.append("task_dueDate",this.getDueDate());
        newDoc.append("task_date",this.getDate());
        newDoc.append("stafflist",staffList);
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

    public void setDbIDToNull(){
        dbID =null;
    }

    @Override
    public boolean isDatabase() {
        return false;
    }

}
