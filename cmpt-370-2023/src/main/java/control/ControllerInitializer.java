package control;

public class ControllerInitializer {

    public static TaskControl taskController = new TaskControl();
    public static UserControl userController = new UserControl();

    public ControllerInitializer() {}

    public TaskControl getTaskController() {
        return taskController;
    }

    public UserControl getUserController() {
        return userController;
    }

}
