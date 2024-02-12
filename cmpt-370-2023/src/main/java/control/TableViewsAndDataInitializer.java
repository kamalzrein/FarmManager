package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.entities.Task;
import org.entities.User;

public class TableViewsAndDataInitializer {

    // todo: intialize controllers
    public static ControllerInitializer init = new ControllerInitializer();
    TaskControl  taskController = init.getTaskController();
    UserControl userController = init.getUserController();

    public TableViewsAndDataInitializer(){

    }






    // Todo: Task tables and data (done)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TableView<Task> taskTable = new TableView<Task>();
    public TableView<Task> CompletedTaskTable = new TableView<Task>();
    public ObservableList<Task> taskData = taskController.taskList;
    public ObservableList<Task> CompletedTaskData = taskController.finishedTaskList;

    // Todo: User tables and data (Need to implement user tasks view)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TableView<User> userTable = new TableView<User>();
    public ObservableList<User> userData = userController.allEmployees;

    // Todo: tableviews and data for users assigned to tasks
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TableView<User> taskUsersTable = new TableView<User>();
    public TableView<User> allUsersInTaskViewTable = new TableView<User>();
    public ObservableList<User> taskUserData= FXCollections.observableArrayList();
    public ObservableList<User> allUserDataInTaskViewTable = userController.allEmployees;

    // Todo: tableviews and data for tasks assigned to users
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TableView<Task> userTasksTable = new TableView<Task>();
    public TableView<Task> allTasksInUserViewTable = new TableView<Task>();
    public ObservableList<Task> userTaskData= FXCollections.observableArrayList();
    public ObservableList<Task> allTaskDataInUserViewTable = taskController.taskList;


    // todo: getters for controllers
    public TaskControl getTaskController() {
        return taskController;
    }

    public UserControl getUserController() {
        return userController;
    }

    // todo: getters for tables and data
    public TableView<Task> getTaskTable() {
        return taskTable;
    }

    public TableView<Task> getCompletedTaskTable() {
        return CompletedTaskTable;
    }

    public ObservableList<Task> getTaskData() {
        return taskData;
    }

    public ObservableList<Task> getCompletedTaskData() {
        return CompletedTaskData;
    }


    public TableView<User> getUserTable() {
        return userTable;
    }

    public ObservableList<User> getUserData() {
        return userData;
    }

    public TableView<User> getTaskUsersTable() {
        return taskUsersTable;
    }

    public TableView<User> getAllUsersInTaskViewTable() {
        return allUsersInTaskViewTable;
    }

    public ObservableList<User> getTaskUserData() {
        return taskUserData;
    }

    public ObservableList<User> getAllUserDataInTaskViewTable() {
        return allUserDataInTaskViewTable;
    }

    public TableView<Task> getUserTasksTable() {
        return userTasksTable;
    }

    public TableView<Task> getAllTasksInUserViewTable() {
        return allTasksInUserViewTable;
    }

    public ObservableList<Task> getUserTaskData() {
        return userTaskData;
    }

    public ObservableList<Task> getAllTaskDataInUserViewTable() {
        return allTaskDataInUserViewTable;
    }
}
