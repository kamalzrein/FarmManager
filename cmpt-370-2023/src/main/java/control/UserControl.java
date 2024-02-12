package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.InitialFarm.dataManager;
import org.bson.types.ObjectId;
import org.entities.Employee;
import org.entities.Task;
import org.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserControl {

    /**
     * list of tasks
     */
    public ObservableList<User> allEmployees;
    public  ObservableList<User>owners;

    public dataManager dataManager = new dataManager();


    /**
     * constructor
     */
    public UserControl(){
        allEmployees = dataManager.initializeUsersFromDB();
        owners =  FXCollections.observableArrayList();
    }


    /**
     * Adds a user to the list of users that the controller is keeping track of
     * @param id: The id of the user to be added
     * @param user_email: The email of the user to be added
     * @param user_password: The password of the user to be added
     * @param first_name: The firstname of the user to be added
     * @param last_name: The lastname of the user to be added
     * @param dob: The date of birth of the user to be added
     * @param owner: whether the user is an owner or not
     */
    public void addUser (String id, String user_email, String user_password, String first_name, String last_name, LocalDate dob, Boolean owner) throws NoSuchFieldException {
        boolean exists= false;
        for (User employee: allEmployees)
        {
            if (employee.getID().equals(id))
            {
                exists = true;
            }
        }
        if (!exists)
        {
            //TODO WE NEED TO ADD MANAGER HERE TO SAVE IT ALLLLLL
            Employee employee = new Employee( null,id, user_email , user_password, first_name, last_name, dob, owner);
            employee= (Employee) dataManager.saveClass(employee);
            allEmployees.add(employee);

            if (owner){
                owners.add(employee);
            }
        }
        else {System.out.println("User ID already exists");}
    }

    /**
     * Edits a user in the list of users that the controller is keeping track of
     * @param oldId: The old id of the user to be edited
     * @param newId: The suggested new id of the user to be edited
     * @param user_email: The email of the user to be edited
     * @param user_password: The password of the user to be edited
     * @param first_name: The firstname of the user to be edited
     * @param last_name: The lastname of the user to be edited
     * @param dob: The date of birth of the user to be edited
     * @param owner: whether the user is an owner or not
     */
    public void editUser(String oldId,String newId,  String first_name, String last_name,  Boolean owner, String user_email, String user_password , LocalDate dob){
        Employee edited = null;
        boolean newIdAlreadyInUse = false;

        // check if the user to be edited exists
        for (User employee: allEmployees) {
            if (employee.getID().equals(oldId))
            {
                edited = (Employee) employee;
            }
        }
        if (edited == null)
        {
            System.out.println("User to be edited could not be found!");

        }
        else {
            // check if the suggested new ID is already in use by a DIFFERENT USER
            for (User employee: allEmployees) {
                if (employee.getID().equals(newId) && employee != edited) {
                    newIdAlreadyInUse = true;
                    break;
                }
            }
            if (!newIdAlreadyInUse)
            {
                edited.setID(newId);
                edited.setEmail(user_email);
                edited.setPassword(user_password);
                edited.setFirstName(first_name);
                edited.setLastName(last_name);
                edited.setDOB(dob);
                edited.isOwner = owner;
                edited = (Employee) dataManager.updateClass(edited);
            }
            else {
                System.out.println("The proposed userId is already in Use!");
            }
        }
    }

    /**
     * Promotes a user in the list of users that the controller is keeping track of
     * @param id: The id of the user to be promoted
     */
    public void promoteUser(String id){

        // check if the user exists
        Employee promoted = null;
        for (User employee: allEmployees)
        {
            if (employee.getID().equals(id))
            {
                promoted = (Employee) employee;
            }
        }
        if (promoted == null)
        {
            System.out.println("User to be promoted could not be found!");
        }
        else {
            // check if they are already an owner
            if (promoted.isOwner)
            {
                System.out.println("User to be promoted is already an owner!");
                showErrorPopup("User to be promoted is already an owner!");
            }
            else {
                promoted.isOwner = true;
                promoted = (Employee) dataManager.updateClass(promoted);
                owners.add(promoted);
            }
        }
    }

    /**
     * Removes a user in the list of users that the controller is keeping track of
     * @param id: The id of the user to be removed
     */
    public void removeUser(String id){
        Employee removed = null;
        // try to find the user in the system
        for (User employee: allEmployees)
        {
            if (employee.getID().equals(id))
            {
                removed = (Employee) employee;
                System.out.println(removed);
            }
        }
        if (removed == null)
        {
            System.out.println("User to be removed wasn't in the system!");
        }
        else {
            if (removed.isOwner== null)
            {
                System.out.println("owner boolean is null");
            }
            else {
                // remeove references to owner locally and in db
                if (removed.isOwner)
                {
                    owners.remove(removed);
                }

                List<Task> taskList = new ArrayList<>();
                for (Task iter: removed.getTaskList())
                {
                    taskList.add(iter);
                }
                for(Task s2ndIter: taskList){
                    s2ndIter.removeStaff(removed.getID());
                    s2ndIter= dataManager.updateClass(s2ndIter);
                }
                allEmployees.remove(removed);
                dataManager.removeClass(removed);
            }
        }
    }

    /**
     * Assigns a task to a user in the list of users that the controller is keeping track of
     * @param userId: The id of the user to whom the task is going to be assigned
     * @param task: The task to be assigned to the user
     */
    public void assignTask(String userId, Task task )
    {
        Employee employee = null;
        // check if employee Id is found
        for (User iter: allEmployees)
        {
            if (iter.getID().equals(userId))
            {
                employee = (Employee) iter;
            }
        }
        if (employee == null)
        {
            System.out.println("User you are trying to assign task to wasn't in the list of users!");
        }
        else{
            employee.addTask(task);
            task =dataManager.updateClass(task);
            employee =(Employee) dataManager.updateClass(employee);
        }
    }


    /**
     * unAssigns a task to a user in the list of users that the controller is keeping track of
     * @param userId: The id of the user to whom the task is going to be unassigned
     * @param task: The task to be unassigned to the user
     */
    public void unAssignTask(String userId, Task task )
    {
        Employee employee = null;
        // check if employee Id is found
        for (User iter: allEmployees)
        {
            if (iter.getID().equals(userId))
            {
                employee = (Employee) iter;
            }
        }
        if (employee == null)
        {
            System.out.println("User you are trying to unassign a task to wasn't in the list of users!");
        }
        else{
            employee.removeTask(task.getID());
            task =dataManager.updateClass(task);
            employee =(Employee) dataManager.updateClass(employee);

        }
    }

    /**
     * Returns a string value of the User to be viewed .
     * @param id: The id of the user to which a user is to be unassigned
     *
     * @return returned: a string representation of the user to be viewed.
     */
    public String viewUser (String id)
    {
        Employee viewed = null;
        String returned = null;

        for (User employee: allEmployees)
        {
            if (employee.getID().equals(id))
            {
                viewed = (Employee) employee;
            }
        }
        if (viewed == null)
        {
            System.out.println("Employee to be viewed could not be found!");
        }
        else
        {
            returned = viewed.toString();
        }
          return returned;
    }

    private void showErrorPopup(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText("INVALID");
        alert.setContentText(content);
        alert.showAndWait();
    }

}
