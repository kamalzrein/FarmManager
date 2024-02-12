package org.openjfx.javafxmavenarchetypes;

import control.ControllerInitializer;
import control.TableViewsAndDataInitializer;
import control.TaskControl;
import control.UserControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.entities.Task;
import org.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.lang.Boolean.parseBoolean;


public class TaskUserViews extends StackPane{

    private VBox userPage = new VBox();
    private Stage stage;
    private Scene MenuScene ;
    private Scene userScene;
    private Scene taskScene;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Todo: Task tables and data (done)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private TableView<Task> taskTable = new TableView<Task>();
    private TableView<Task> CompletedTaskTable = new TableView<Task>();
    TaskControl taskController = new TaskControl();

    /**
     * new Task("124124", "Do the tasky", "This is my description", LocalDateTime.now()),
     *                     new Task("1243263456", "Do the tasky2", "My second description", LocalDateTime.now())
     */
    private ObservableList<Task> taskData = taskController.taskList;
    private ObservableList<Task> CompletedTaskData = taskController.finishedTaskList;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Todo: User tables and data (Need to implement user tasks view)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private TableView<User> userTable = new TableView<User>();
    UserControl userController = new UserControl();
    private ObservableList<User> userData = userController.allEmployees;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Todo: tableviews and data for users assigned to tasks
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private TableView<User> taskUsersTable = new TableView<User>();
    private TableView<User> allUsersInTaskViewTable = new TableView<User>();
    private ObservableList<User> taskUserData= FXCollections.observableArrayList();
    private ObservableList<User> allUserDataInTaskViewTable = userController.allEmployees;
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Todo: tableviews and data for tasks assigned to users
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private TableView<Task> userTasksTable = new TableView<Task>();
    private TableView<Task> allTasksInUserViewTable = new TableView<Task>();
    private ObservableList<Task> userTaskData= FXCollections.observableArrayList();
    private ObservableList<Task> allTaskDataInUserViewTable = taskController.taskList;
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    public TaskUserViews(){


        VBox taskSelector = new VBox(30);
        Scene MenuScene = new Scene(taskSelector,300,250);


        VBox taskPage = new VBox(30);
        Scene taskScene = new Scene(taskPage,300,250);

        Button btasks = new Button();
        btasks.setText("Tasks");
        btasks.setOnAction(e -> stage.setScene(taskScene));

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  TODO : User UI Section ( done )
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        VBox userPage= new VBox(30);
        Scene userScene = new Scene(userPage, 300, 250);
        Button busers= new Button();
        busers.setText("Users");
        busers.setOnAction(e-> {
            stage.setScene(userScene);
        });
        taskSelector.getChildren().addAll(btasks, busers);
        taskSelector.setAlignment(Pos.CENTER);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // TODO: adding functionality to the user tab (connecting to tasks) (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo: adding users (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        VBox userBox2 = new VBox(30);
        Scene addUserScene2 = new Scene(userBox2,300,250);
        Label userLabel = new Label("User Popup");
        userLabel.setFont(new Font("Arial", 20));


        TextField userIdInput = new TextField("Input User ID");
        TextField emailInput = new TextField("User Email");
        TextField passwordInput = new TextField("User Password");
        TextField fNameInput = new TextField("User First Name");
        TextField lNameInput = new TextField("User Last Name");
        TextField ownerInput = new TextField("Ownership");
        DatePicker dob = new DatePicker();
        Button submitAddUserInfo = new Button("submit");

        userBox2.getChildren().addAll(fNameInput,lNameInput, ownerInput,userIdInput,emailInput,passwordInput,dob,submitAddUserInfo);

        Button addUser = new Button("add User");
        addUser.setOnMouseClicked(e ->{
            userIdInput.setText("Input User ID");
            emailInput.setText("User Email");
            passwordInput.setText("User Password");
            fNameInput.setText("User First Name");
            lNameInput.setText("User Last Name");
            ownerInput.setText("Ownership");
            dob.setValue(null);
            stage.setScene(addUserScene2);
        });
        submitAddUserInfo.setOnMouseClicked(e ->{
            //User newUser = new Employee(userIdInput.getText(),emailInput.getText(), passwordInput.getText(), fNameInput.getText(), lNameInput.getText() ,dob.getValue(), parseBoolean(ownerInput.getText()));
            //userData.add(newUser);
            try {
                userController.addUser(userIdInput.getText(),emailInput.getText(), passwordInput.getText(), fNameInput.getText(), lNameInput.getText() ,dob.getValue(), parseBoolean(ownerInput.getText()));
            } catch (NoSuchFieldException ex) {
                throw new RuntimeException(ex);
            }
            stage.setScene(userScene);
            // not sure if below line is necessary
            userTable.refresh();
            allUsersInTaskViewTable.refresh();
            taskUsersTable.refresh();

        });

        Button userBackToMain = new Button("back");
        userBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 1: remove user (done - might need revisiting later)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button removeUser = new Button("Remove User");
        removeUser.setOnMouseClicked(e-> {
            // not sure if any additional work is needed here within the class itself
            //userTable.getSelectionModel().getSelectedItem().
            //userTable.getItems().remove(userTable.getSelectionModel().getSelectedItem());
            userController.removeUser(userTable.getSelectionModel().getSelectedItem().getID());
            userTable.refresh();
            System.out.println(userController.allEmployees);
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 2: edit user (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button editUser = new Button("edit user");

        //Making editPopup
        VBox actualUserEditBox = new VBox(30);
        Scene actualUserEditScene = new Scene(actualUserEditBox,300,250);

        TextField userIdEdit = new TextField("");
        TextField emailEdit = new TextField("");
        TextField passwordEdit = new TextField("");
        TextField fNameEdit = new TextField("");
        TextField lNameEdit = new TextField("");
        TextField ownerEdit = new TextField("");
        DatePicker dobEdit = new DatePicker();
        Button submitUserInfoEdit = new Button("submit");

        actualUserEditBox.getChildren().addAll(fNameEdit,lNameEdit,ownerEdit,userIdEdit, emailEdit, passwordEdit, dobEdit,submitUserInfoEdit);
        submitUserInfoEdit.setOnMouseClicked(e-> {
            userController.editUser(userTable.getSelectionModel().getSelectedItem().getID(),
                    userIdEdit.getText(), fNameEdit.getText(),lNameEdit.getText(),
                    parseBoolean(ownerEdit.getText()),emailEdit.getText(),
                    passwordEdit.getText(), dobEdit.getValue());
            System.out.println(userTable.getSelectionModel().getSelectedItem());
            stage.setScene(userScene);
            userTable.refresh();
            allUsersInTaskViewTable.refresh();
            taskUsersTable.refresh();

        });

        editUser.setOnMouseClicked(e-> {
            fNameEdit.setText(userTable.getSelectionModel().getSelectedItem().getFirstName());
            lNameEdit.setText(userTable.getSelectionModel().getSelectedItem().getLastName());
            ownerEdit.setText(String.valueOf(userTable.getSelectionModel().getSelectedItem().getOwner()));
            userIdEdit.setText(userTable.getSelectionModel().getSelectedItem().getID());
            emailEdit.setText(userTable.getSelectionModel().getSelectedItem().getEmail());
            passwordEdit.setText(userTable.getSelectionModel().getSelectedItem().getPassword());
            dobEdit.setValue(userTable.getSelectionModel().getSelectedItem().getDOB());

            stage.setScene(actualUserEditScene);
            System.out.println(userController.allEmployees);
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 3: promote user (done - revisit changes to employee hierarchy) (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button promoteUser = new Button("Promote User");
        promoteUser.setOnMouseClicked(e-> {
            // not sure if any additional work is needed here within the class itself
            //userTable.getSelectionModel().getSelectedItem().
            userController.promoteUser(userTable.getSelectionModel().getSelectedItem().getID());
            //userTable.getSelectionModel().getSelectedItem().setOwner(true);
            System.out.println(userController.allEmployees);

            userTable.refresh();
            allUsersInTaskViewTable.refresh();
            taskUsersTable.refresh();
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 4: Employee Tasks view. ( assigning and unassigning tasks to Employee) (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        VBox employeeTasksBox = new VBox(30);
        Scene employeeTasksScene = new Scene(employeeTasksBox,300,250);
        Label employeeTasksLabel = new Label("Employee Tasks Popup view");
        Label employeeNameLabel = new Label();
        HBox employeeTasksLabelBox = new HBox();
        employeeTasksLabelBox.getChildren().addAll(employeeTasksLabel);
        employeeTasksLabelBox.setAlignment(Pos.CENTER);
        HBox employeeNameLabelBox = new HBox();
        employeeNameLabelBox.getChildren().addAll(employeeNameLabel);
        employeeNameLabelBox.setAlignment(Pos.CENTER);


        Button employeeTasks = new Button("View Tasks");
        employeeTasks.setOnMouseClicked(e->{
            userTaskData= userTable.getSelectionModel().getSelectedItem().getTaskList();
            userTasksTable.setItems(userTaskData);
            String firstName = userTable.getSelectionModel().getSelectedItem().getFirstName();
            String lastName =  userTable.getSelectionModel().getSelectedItem().getLastName();
            employeeNameLabel.setText("Employee: " + firstName +" " + lastName);
            stage.setScene(employeeTasksScene);
        });

        Button employeeAddTasks = new Button("Add Task");
        employeeAddTasks.setOnMouseClicked(e->{
            //userTable.getSelectionModel().getSelectedItem().addTask(allTasksInUserViewTable.getSelectionModel().getSelectedItem());
            userController.assignTask(userTable.getSelectionModel().getSelectedItem().getID(),allTasksInUserViewTable.getSelectionModel().getSelectedItem() );
            System.out.println( "the add task button has been clicked");
        });

        Button employeeRemoveTasks = new Button("Remove Task");
        employeeRemoveTasks.setOnMouseClicked(e->{
            //userTable.getSelectionModel().getSelectedItem().removeTask(userTasksTable.getSelectionModel().getSelectedItem().getID());
            userController.unAssignTask( userTable.getSelectionModel().getSelectedItem().getID(), userTasksTable.getSelectionModel().getSelectedItem());
            System.out.println( "the remove task button has been clicked");
        });

        Button employeeTasksBackToMain = new Button("back");
        employeeTasksBackToMain.setOnMouseClicked(e ->{
            stage.setScene(userScene);
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 4.1: Employee's tasks inside of employee task view (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        TableColumn<Task, String> userTaskIDCol = new TableColumn<Task, String>("Task ID");
        userTaskIDCol.editableProperty().setValue(true);
        userTaskIDCol.setMinWidth(130);
        userTaskIDCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("ID")
        );

        TableColumn<Task, String> userTaskName = new TableColumn<Task, String>("Task Name");
        userTaskName.setMinWidth(130);
        userTaskName.setCellValueFactory(
                new PropertyValueFactory<Task, String>("taskName")
        );

        TableColumn<Task, String> userTaskDescription = new TableColumn<Task, String>("Task description");
        userTaskDescription.setMinWidth(130);
        userTaskDescription.setCellValueFactory(
                new PropertyValueFactory<Task, String>("description")
        );

        TableColumn<Task, LocalDateTime> userTaskDueDate = new TableColumn<Task, LocalDateTime>("Due date");
        userTaskDueDate.setMinWidth(130);
        userTaskDueDate.setCellValueFactory(
                new PropertyValueFactory<Task, LocalDateTime>("dueDate")
        );

        // userTaskData= userTable.getSelectionModel().getSelectedItem().getTaskList();
        userTasksTable.setItems(userTaskData);
        userTasksTable.setEditable(true);
        userTasksTable.getColumns().addAll(userTaskIDCol, userTaskName, userTaskDescription,userTaskDueDate);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 4.2: all tasks inside of employee task view (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        TableColumn<Task, String> allTaskIDColInUser = new TableColumn<Task, String>("Task ID");
        allTaskIDColInUser.editableProperty().setValue(true);
        allTaskIDColInUser.setMinWidth(130);
        allTaskIDColInUser.setCellValueFactory(
                new PropertyValueFactory<Task, String>("ID")
        );

        TableColumn<Task, String> allTaskNameColInUser = new TableColumn<Task, String>("Task Name");
        allTaskNameColInUser.setMinWidth(130);
        allTaskNameColInUser.setCellValueFactory(
                new PropertyValueFactory<Task, String>("taskName")
        );

        TableColumn<Task, String> allTaskDescriptionColInUser = new TableColumn<Task, String>("Task description");
        allTaskDescriptionColInUser.setMinWidth(130);
        allTaskDescriptionColInUser.setCellValueFactory(
                new PropertyValueFactory<Task, String>("description")
        );

        TableColumn<Task, LocalDateTime> allTaskDueDateColInUser = new TableColumn<Task, LocalDateTime>("Due date");
        allTaskDueDateColInUser.setMinWidth(130);
        allTaskDueDateColInUser.setCellValueFactory(
                new PropertyValueFactory<Task, LocalDateTime>("dueDate")
        );

        allTasksInUserViewTable.setItems(allTaskDataInUserViewTable);
        allTasksInUserViewTable.setEditable(true);
        allTasksInUserViewTable.getColumns().addAll(allTaskIDColInUser,allTaskNameColInUser, allTaskDescriptionColInUser, allTaskDueDateColInUser);

        Label taskLabelWithinEmployeeTasks = new Label("All tasks:");
        HBox taskLabelWithinEmployeeTasksBox = new HBox();
        taskLabelWithinEmployeeTasksBox.getChildren().addAll(taskLabelWithinEmployeeTasks);
        taskLabelWithinEmployeeTasksBox.setAlignment(Pos.CENTER);
        HBox topUserAddTaskBar= new HBox();
        topUserAddTaskBar.getChildren().addAll( employeeAddTasks, employeeRemoveTasks ,employeeTasksBackToMain);


        employeeTasksBox.getChildren().addAll(employeeTasksLabelBox, employeeNameLabelBox, topUserAddTaskBar, userTasksTable, taskLabelWithinEmployeeTasksBox, allTasksInUserViewTable);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 4.3: User Table Formatting (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        HBox topUserBar= new HBox();
        topUserBar.getChildren().addAll(addUser,editUser,removeUser, promoteUser ,employeeTasks ,userBackToMain);

        TableColumn<User, String> userIDCol = new TableColumn<User, String>("User ID");
        userIDCol.setMinWidth(130);
        userIDCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("ID")
        );

        TableColumn<User, String> userEmailCol = new TableColumn<User, String>("User Email");
        userEmailCol.setMinWidth(130);
        userEmailCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("email")
        );
        TableColumn<User, String> userPasswordCol = new TableColumn<User, String>("User Password");
        userPasswordCol.setMinWidth(130);
        userPasswordCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("password")
        );
        TableColumn<User, String> userFirstNameCol = new TableColumn<User, String>("User First Name");
        userFirstNameCol.setMinWidth(130);
        userFirstNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("firstName")
        );
        TableColumn<User, String> userLastNameCol = new TableColumn<User, String>("User Last Name");
        userLastNameCol.setMinWidth(130);
        userLastNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("lastName")
        );

        TableColumn<User, LocalDate> userDOBCol = new TableColumn<User, LocalDate>("User Date of Birth");
        userDOBCol.setMinWidth(130);
        userDOBCol.setCellValueFactory(
                new PropertyValueFactory<User, LocalDate>("DOB")
        );

        TableColumn<User, Boolean> userOwnership = new TableColumn<User, Boolean>("Owner (T/F)");
        userOwnership.setMinWidth(130);
        userOwnership.setCellValueFactory(
                new PropertyValueFactory<User, Boolean>("owner")
        );

        userTable.setItems(userData);
        userTable.setEditable(true);
        userTable.getColumns().addAll(userFirstNameCol, userLastNameCol, userOwnership,userIDCol,userEmailCol,userPasswordCol, userDOBCol);
        userPage.getChildren().addAll(topUserBar,userTable);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TODO: Task UI Section ( In progress)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 1 : Making the add task window pop up (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        VBox userBox = new VBox(30);
        Scene addUserScene = new Scene(userBox,300,250);

        Label albel = new Label("Popup");
        TextField idInput = new TextField("Input ID (optional)");
        TextField taskNameF = new TextField("Input taskName");
        TextField descriptionF = new TextField("Input task description");
        DatePicker dueDate = new DatePicker();
        Button submitTask = new Button("submit");

        userBox.getChildren().addAll(idInput,taskNameF,descriptionF,dueDate,submitTask);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 2: Task Addition (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button addTask = new Button("add Task");
        addTask.setOnMouseClicked(e ->{
            idInput.setText("Input ID (optional)");
            taskNameF.setText("Input taskName");
            descriptionF.setText("Input task description");
            dueDate.setValue(null);
            stage.setScene(addUserScene);
        });

        submitTask.setOnMouseClicked(e ->{
            //Task newTask = new Task(idInput.getText(),taskNameF.getText(),descriptionF.getText(),dueDate.getValue().atTime(LocalTime.now()));
            //taskData.add(newTask);
            try {
                taskController.addTask(idInput.getText(),taskNameF.getText(),descriptionF.getText(),dueDate.getValue().atTime(LocalTime.now()));
            } catch (NoSuchFieldException ex) {
                throw new RuntimeException(ex);
            }
            stage.setScene(taskScene);
            taskTable.refresh();
            allTasksInUserViewTable.refresh();
            userTasksTable.refresh();
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 3: Task Editing
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Button editTask = new Button("edit task");
        VBox userEditBox = new VBox(30);
        Scene editUserScene = new Scene(userEditBox,300,250);

        TextField idInputEdit = new TextField();
        TextField taskNameFEdit = new TextField();
        TextField descriptionFEdit = new TextField();
        DatePicker dueDateEdit = new DatePicker();
        Button submitTaskEdit = new Button("submit");

        userEditBox.getChildren().addAll(idInputEdit,taskNameFEdit,descriptionFEdit,dueDateEdit,submitTaskEdit);
        submitTaskEdit.setOnMouseClicked(e ->{
            taskController.editTask(taskTable.getSelectionModel().getSelectedItem().getID(),
                    idInputEdit.getText(), taskNameFEdit.getText(), descriptionFEdit.getText(),
                    dueDateEdit.getValue().atTime(LocalTime.now()));
            System.out.println(taskTable.getSelectionModel().getSelectedItem());
            stage.setScene(taskScene);
            taskTable.refresh();
            allTasksInUserViewTable.refresh();
            userTasksTable.refresh();
        });

        editTask.setOnMouseClicked(e ->{
            idInputEdit.setText(taskTable.getSelectionModel().getSelectedItem().getID());
            taskNameFEdit.setText(taskTable.getSelectionModel().getSelectedItem().getTaskName());
            descriptionFEdit.setText(taskTable.getSelectionModel().getSelectedItem().getDescription());
            dueDateEdit.setValue(taskTable.getSelectionModel().getSelectedItem().getDueDate().toLocalDate());

            stage.setScene(editUserScene);
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 4: Task Completion (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button markComplete = new Button("Mark Complete");

        markComplete.setOnMouseClicked(e ->{
            taskController.completeTask(taskTable.getSelectionModel().getSelectedItem().getID());
            taskTable.refresh();
            allTasksInUserViewTable.refresh();
        });

        Button taskBackToMain = new Button("back");
        taskBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 5: the view of completed tasks
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        VBox completedTaskBox = new VBox(30);
        Scene completedTaskScene = new Scene(completedTaskBox,300,250);

        Button viewCompleted = new Button("Completed Tasks");
        viewCompleted.setOnMouseClicked(e->{
            stage.setScene(completedTaskScene);
        });

        Button completedTaskBackToMain = new Button("back");
        completedTaskBackToMain.setOnMouseClicked(e ->{
            stage.setScene(taskScene);
        });

        Label completedTaskLabel = new Label("Completed Task Popup");

        TableColumn<Task, String> completedTaskIDCol = new TableColumn<Task, String>("Task ID");
        completedTaskIDCol.editableProperty().setValue(true);
        completedTaskIDCol.setMinWidth(130);
        completedTaskIDCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("ID")
        );

        TableColumn<Task, String> completedTaskName = new TableColumn<Task, String>("Task Name");
        completedTaskName.setMinWidth(130);
        completedTaskName.setCellValueFactory(
                new PropertyValueFactory<Task, String>("taskName")
        );

        TableColumn<Task, String> completedTaskDescription = new TableColumn<Task, String>("Task description");
        completedTaskDescription.setMinWidth(130);
        completedTaskDescription.setCellValueFactory(
                new PropertyValueFactory<Task, String>("description")
        );

        TableColumn<Task, LocalDateTime> completedTaskDueDate = new TableColumn<Task, LocalDateTime>("Due date");
        completedTaskDueDate.setMinWidth(130);
        completedTaskDueDate.setCellValueFactory(
                new PropertyValueFactory<Task, LocalDateTime>("dueDate")
        );

        // I could group the buttons into the hbox and then add that and the table to the thing 6 lines later
        //        HBox completedTaskTopBar= new HBox();
        //        completedTaskTopBar.getChildren().addAll(completedTaskBackToMain);

        CompletedTaskTable.setItems(CompletedTaskData);
        CompletedTaskTable.setEditable(true);
        CompletedTaskTable.getColumns().addAll(completedTaskIDCol, completedTaskName, completedTaskDescription,completedTaskDueDate);
        completedTaskBox.getChildren().addAll(completedTaskLabel, completedTaskBackToMain, CompletedTaskTable);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 6: Task Employees view
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        VBox taskEmployeesBox = new VBox(30);
        Scene taskEmployeesScene = new Scene(taskEmployeesBox,300,250);
        Label taskEmployeesLabel = new Label("Task Employees Popup view");
        Label taskNameLabel = new Label();
        Label employeeLabelWithinTaskEmployees = new Label("All employees:");

        Button taskEmployees = new Button("View Employees");
        taskEmployees.setOnMouseClicked(e->{
            taskUserData= taskTable.getSelectionModel().getSelectedItem().getStaffList();
            taskUsersTable.setItems(taskUserData);
            String taskName = taskTable.getSelectionModel().getSelectedItem().getTaskName();
            taskNameLabel.setText("Task Name: " + taskName);
            stage.setScene(taskEmployeesScene);
        });

        Button taskAddEmployees = new Button("Assign Employee");
        taskAddEmployees.setOnMouseClicked(e->{
            //taskTable.getSelectionModel().getSelectedItem().addStaff(allUsersInTaskViewTable.getSelectionModel().getSelectedItem());
            taskController.assignEmployee(taskTable.getSelectionModel().getSelectedItem().getID(),allUsersInTaskViewTable.getSelectionModel().getSelectedItem() );
            System.out.println( "the add Employee button has been clicked");
        });

        Button taskRemoveEmployees = new Button("Remove Employee");
        taskRemoveEmployees.setOnMouseClicked(e->{
            //taskTable.getSelectionModel().getSelectedItem().removeStaff(taskUsersTable.getSelectionModel().getSelectedItem().getID());
            taskController.unAssignEmployee(taskTable.getSelectionModel().getSelectedItem().getID(),allUsersInTaskViewTable.getSelectionModel().getSelectedItem() );
            System.out.println( "the remove Employee button has been clicked");
        });


        Button taskEmployeesBackToTask = new Button("back");
        taskEmployeesBackToTask.setOnMouseClicked(e ->{
            stage.setScene(taskScene);
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 6.2: Task's employees inside of employee task view (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        TableColumn<User, String> taskUserIDCol = new TableColumn<User, String>("User ID");
        taskUserIDCol.setMinWidth(130);
        taskUserIDCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("ID")
        );

        TableColumn<User, String> taskUserEmailCol = new TableColumn<User, String>("User Email");
        taskUserEmailCol.setMinWidth(130);
        taskUserEmailCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("email")
        );
        TableColumn<User, String> taskUserPasswordCol = new TableColumn<User, String>("User Password");
        taskUserPasswordCol.setMinWidth(130);
        taskUserPasswordCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("password")
        );
        TableColumn<User, String> taskUserFirstNameCol = new TableColumn<User, String>("User First Name");
        taskUserFirstNameCol.setMinWidth(130);
        taskUserFirstNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("firstName")
        );
        TableColumn<User, String> taskUserLastNameCol = new TableColumn<User, String>("User Last Name");
        taskUserLastNameCol.setMinWidth(130);
        taskUserLastNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("lastName")
        );

        TableColumn<User, LocalDate> taskUserDOBCol = new TableColumn<User, LocalDate>("User Date of Birth");
        taskUserDOBCol.setMinWidth(130);
        taskUserDOBCol.setCellValueFactory(
                new PropertyValueFactory<User, LocalDate>("DOB")
        );

        TableColumn<User, Boolean> taskUserOwnership = new TableColumn<User, Boolean>("Owner (T/F)");
        taskUserOwnership.setMinWidth(130);
        taskUserOwnership.setCellValueFactory(
                new PropertyValueFactory<User, Boolean>("owner")
        );

        taskUsersTable.setItems(taskUserData);
        taskUsersTable.setEditable(true);
        taskUsersTable.getColumns().addAll(taskUserFirstNameCol, taskUserLastNameCol, taskUserOwnership,taskUserIDCol,taskUserEmailCol,taskUserPasswordCol, taskUserDOBCol);


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 6.3: All employee's inside task employee view (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        TableColumn<User, String> allUserIDColInTask = new TableColumn<User, String>("User ID");
        allUserIDColInTask.setMinWidth(130);
        allUserIDColInTask.setCellValueFactory(
                new PropertyValueFactory<User, String>("ID")
        );

        TableColumn<User, String> allUserEmailColInTask = new TableColumn<User, String>("User Email");
        allUserEmailColInTask.setMinWidth(130);
        allUserEmailColInTask.setCellValueFactory(
                new PropertyValueFactory<User, String>("email")
        );

        TableColumn<User, String> allUserPasswordColInTask = new TableColumn<User, String>("User Password");
        allUserPasswordColInTask.setMinWidth(130);
        allUserPasswordColInTask.setCellValueFactory(
                new PropertyValueFactory<User, String>("password")
        );
        TableColumn<User, String> allUserFirstNameColInTask = new TableColumn<User, String>("User First Name");
        allUserFirstNameColInTask.setMinWidth(130);
        allUserFirstNameColInTask.setCellValueFactory(
                new PropertyValueFactory<User, String>("firstName")
        );

        TableColumn<User, String> allUserLastNameColInTask = new TableColumn<User, String>("User Last Name");
        allUserLastNameColInTask.setMinWidth(130);
        allUserLastNameColInTask.setCellValueFactory(
                new PropertyValueFactory<User, String>("lastName")
        );

        TableColumn<User, LocalDate> allUserDOBColInTask = new TableColumn<User, LocalDate>("User Date of Birth");
        allUserDOBColInTask.setMinWidth(130);
        allUserDOBColInTask.setCellValueFactory(
                new PropertyValueFactory<User, LocalDate>("DOB")
        );

        TableColumn<User, Boolean> allUserOwnershipInTask = new TableColumn<User, Boolean>("Owner (T/F)");
        allUserOwnershipInTask.setMinWidth(130);
        allUserOwnershipInTask.setCellValueFactory(
                new PropertyValueFactory<User, Boolean>("owner")
        );

        allUsersInTaskViewTable.setItems(allUserDataInTaskViewTable);
        allUsersInTaskViewTable.setEditable(true);
        allUsersInTaskViewTable.getColumns().addAll(allUserFirstNameColInTask, allUserLastNameColInTask, allUserOwnershipInTask,
                allUserIDColInTask,allUserEmailColInTask,allUserPasswordColInTask, allUserDOBColInTask);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // todo 6.4:  creating top bar for the view of employee view inside of task and adding buttons to it (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        HBox topTaskAddUserBar= new HBox();
        topTaskAddUserBar.getChildren().addAll(taskAddEmployees, taskRemoveEmployees, taskEmployeesBackToTask);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // todo 6.5: adding the top bar itself into the page (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        taskEmployeesBox.getChildren().addAll(taskEmployeesLabel,taskNameLabel,topTaskAddUserBar, taskUsersTable,employeeLabelWithinTaskEmployees, allUsersInTaskViewTable);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 7: Task view formatting (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        HBox topBar= new HBox();
        topBar.getChildren().addAll(addTask,editTask,markComplete,viewCompleted,taskEmployees ,taskBackToMain);

        TableColumn<Task, String> taskIDCol = new TableColumn<Task, String>("Task ID");

        taskIDCol.editableProperty().setValue(true);
        taskIDCol.setMinWidth(130);
        taskIDCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("ID")
        );

        TableColumn<Task, String> taskName = new TableColumn<Task, String>("Task Name");
        taskName.setMinWidth(130);
        taskName.setCellValueFactory(
                new PropertyValueFactory<Task, String>("taskName")
        );

        TableColumn<Task, String> taskDescription = new TableColumn<Task, String>("Task description");
        taskDescription.setMinWidth(130);
        taskDescription.setCellValueFactory(
                new PropertyValueFactory<Task, String>("description")
        );

        TableColumn<Task, LocalDateTime> taskDueDate = new TableColumn<Task, LocalDateTime>("Due date");
        taskDueDate.setMinWidth(130);
        taskDueDate.setCellValueFactory(
                new PropertyValueFactory<Task, LocalDateTime>("dueDate")
        );

        taskTable.setItems(taskData);
        taskTable.setEditable(true);
        taskTable.getColumns().addAll(taskIDCol,taskName,taskDescription,taskDueDate);
        taskPage.getChildren().addAll(topBar,taskTable);

    }


    public void setStageMenuTask(Stage stage, Scene MenuScene, Scene taskScene){
        this.stage = stage;
        this.MenuScene = MenuScene;
        this.taskScene = taskScene;
        taskScene.getStylesheets().add(getClass().getClassLoader().getResource("task.css").toExternalForm());
    }

    public void setStageMenuUser(Stage stage, Scene MenuScene, Scene userScene){
        this.stage = stage;
        this.MenuScene = MenuScene;
        this.userScene = userScene;
        userScene.getStylesheets().add(getClass().getClassLoader().getResource("user.css").toExternalForm());
    }

}
