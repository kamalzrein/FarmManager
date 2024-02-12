package control;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.entities.Task;
import org.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.InitialFarm.dataManager;

public class TaskControl {

    // Should contain a list of tasks
    // should be able to:
        //  display list of tasks
        //  add a task
        //  view a task
        //  edit a task
        //  delete a task


    /**
     * list of unifinished tasks
     */
    public ObservableList<Task> taskList;
    /**
     * list of finished tasks
     */
    public ObservableList<Task> finishedTaskList;
    /**
     * instance of data manager class.
     */
    public dataManager dataManager = new dataManager();

    /**
     * constructor
     */
    public TaskControl(){
        taskList = dataManager.initializeTasksFromDB();
        finishedTaskList= dataManager.initializeFinishedTasksFromDB();
    }

    /**
     * Adds a task into the system given its id, name, description, and duedate.
     * @param id: the id of the task to be added.
     * @param taskName: the name of the task to be added.
     * @param description: the description of the task to be added.
     * @param dueDate: the due date of the task to be added.
     */
    public void addTask (String id, String taskName, String description, LocalDateTime dueDate) throws NoSuchFieldException {
        //check if task already exists
        boolean taskExists= false;

        for (Task task : taskList) {
            if (task.getID().equals(id)) {
                taskExists=true;
            }
        }
        // if it doesn't, add it. If it does, report it.
        if (!taskExists){
            Task task = new Task( null, id, taskName , description, dueDate);
            task = dataManager.saveClass(task);
            taskList.add(task);
        }
        else {
            System.out.println("There already is a task with the desired ID");
            showErrorPopup("There already is a task with the desired ID");
        }
    }

    /**
     * Adds a task into the system given its id, name, description, and duedate.
     * @param oldId: the old id of the task to be edited.
     * @param newId: the potential new id of the task to be edited.
     * @param newTaskName: the new name of the task to be edited.
     * @param newDescription: the new description of the task to be added.
     * @param newDueDate: the new due date of the task to be edited.
     */
    public void editTask(String oldId, String newId, String newTaskName, String newDescription, LocalDateTime newDueDate){
        Task edited = null;
        boolean taskIdAlreadyExists = false;

        // check if task to be edited exists at all
        for (Task task : taskList) {
            if (task.getID().equals(oldId)) {
                edited = task;
            }
        }
        if (edited == null)
        {
            System.out.println("Task to be edited could not be found!");

        }
        // if it does
        else{
            // check if its new id is identical to another task's id.
            for (Task task : taskList) {
                if (task.getID().equals(newId) && task !=edited) {
                    taskIdAlreadyExists=true;
                    break;
                }
            }
            if (!taskIdAlreadyExists)
            {
                edited.setID(newId);
                edited.setTaskName(newTaskName);
                edited.setDescription(newDescription);
                edited.setDueDate(newDueDate);
                dataManager.updateClass(edited);
                showPopup("Task Edited");
            }
            else{
                System.out.println("The suggested new task ID is already in use.");
                showErrorPopup("The suggested new task ID is already in use.");
            }
        }
    }

    /**
     * Marks a task as complete, removes it from the list of tasks, and adds it to list of completed tasks.
     * @param id: The id of the task to be marked as complete
     */
    public void completeTask(String id)
    {
        Task completed = null;
        for (Task task : taskList) {
            if (task.getID().equals(id)) {
                completed = task;
            }
        }
        if (completed == null)
        {
            System.out.println("Task to be edited could not be found!");
        }
        else{
            completed.markAsCompleted(true);
            //completed.setInProgress(false);

            List<User> employeeList = new ArrayList<>();
            for (User iter: completed.getStaffList())
            {
                employeeList.add(iter);
            }
            for(User s2ndIter: employeeList){
                s2ndIter.removeTask(completed.getID());
                s2ndIter= dataManager.updateClass(s2ndIter);
            }

            // remove the task from the list of uncompleted tasks
            taskList.remove(completed);
            dataManager.removeClass(completed);

            // add the task to the list of completed tasks and save it to db
            completed = dataManager.saveFinishedTask(completed);
            finishedTaskList.add(completed);
        }
    }


    /**
     * Assigns a user to a task in the list of tasks that the controller is keeping track of
     * @param taskId: The id of the task to which a user is to be assigned
     * @param user: The user to be assigned to the task
     */
    public void assignEmployee(String taskId, User user )
    {
        Task task = null;
        // check if task Id is found
        for (Task iter: taskList)
        {
            if (iter.getID().equals(taskId))
            {
                task = (Task) iter;
            }
        }
        if (task == null)
        {
            System.out.println("Task you are trying to assign a user to wasn't in the list of tasks!");
            showErrorPopup("Task you are trying to assign a user to wasn't in the list of tasks!");
        }
        else{
            task.addStaff(user);
            dataManager.updateClass(task);
            dataManager.updateClass(user);
        }
    }


    /**
     * unAssigns a user to a task in the list of tasks that the controller is keeping track of
     * @param taskId: The id of the task to which a user is to be unassigned
     * @param user: The user to be unassigned to the task
     */
    public void unAssignEmployee(String taskId, User user )
    {
        Task task = null;
        // check if task Id is found
        for (Task iter: taskList)
        {
            if (iter.getID().equals(taskId))
            {
                task = (Task) iter;
            }
        }
        if (task == null)
        {
            System.out.println("Task you are trying to unassign a user to wasn't in the list of tasks");
        }
        else{
            task.removeStaff(user.getID());
            dataManager.updateClass(task);
            dataManager.updateClass(user);
        }
    }


    /**
     * Returns a string value of the task to be viewed .
     * @param id: The id of the task to which a user is to be unassigned
     * @return returned: a string representation of the task to be viewed.
     */
    public String viewTask (String id)
    {
        Task viewed = null;
        String returned = null;

        for (Task task : taskList) {
            if (task.getID().equals(id)) {
                viewed = task;
            }
        }

        if (viewed == null)
        {
            System.out.println("Task to be edited could not be found!");

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

    private void showPopup(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("MESSAGE");
        alert.setHeaderText("CONFIRM MESSAGE");
        alert.setContentText(content);
        alert.showAndWait();
    }

}
