package org.openjfx.javafxmavenarchetypes;

import control.ControllerInitializer;
import control.TableViewsAndDataInitializer;
import control.TaskControl;
import control.UserControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.entities.Task;
import org.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.lang.Boolean.parseBoolean;

public class UserView extends StackPane {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Todo: User tables and data (Need to implement user tasks view)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private VBox userPage = new VBox();
    private Stage stage;
    private Scene MenuScene ;
    private Scene userScene;

    // todo: initializer

    TableViewsAndDataInitializer initer = new TableViewsAndDataInitializer();

    public TaskControl taskController = initer.getTaskController();
    public UserControl userController = initer.getUserController();

    public TableView<Task> taskTable = initer.getTaskTable();
    public TableView<Task> CompletedTaskTable = initer.getCompletedTaskTable();
    public ObservableList<Task> taskData = initer.getTaskData();
    public ObservableList<Task> CompletedTaskData = initer.getCompletedTaskData();

    // Todo: User tables and data (Need to implement user tasks view)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TableView<User> userTable = initer.getUserTable();
    public ObservableList<User> userData = initer.getUserData();

    // Todo: tableviews and data for users assigned to tasks
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TableView<User> taskUsersTable =initer.getTaskUsersTable();
    public TableView<User> allUsersInTaskViewTable =initer.getAllUsersInTaskViewTable();
    public ObservableList<User> taskUserData= initer.getTaskUserData();
    public ObservableList<User> allUserDataInTaskViewTable = initer.getAllUserDataInTaskViewTable();

    // Todo: tableviews and data for tasks assigned to users
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TableView<Task> userTasksTable =initer.getUserTasksTable();
    public TableView<Task> allTasksInUserViewTable = initer.getAllTasksInUserViewTable();
    public ObservableList<Task> userTaskData= initer.getUserTaskData();
    public ObservableList<Task> allTaskDataInUserViewTable =  initer.getAllTaskDataInUserViewTable();




    public UserView(){
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  TODO : User UI Section ( done )
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // TODO: adding functionality to the user tab (connecting to tasks) (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo: adding users (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        VBox userBox2 = new VBox(15);
        Scene addUserScene2 = new Scene(userBox2,300,250);
        VBox adduserbox = new VBox(15);

        Label addUserLabel = new Label("Add New User");
        addUserLabel.getStyleClass().add("page-label");
        HBox adduserlabelBox = new HBox();
        adduserlabelBox.getStyleClass().add("center-label");
        adduserlabelBox.getChildren().add(addUserLabel);

        Label userIDInputLabel = new Label("User ID:");
        userIDInputLabel.getStyleClass().add("text-field-label");
        TextField userIdInput = new TextField();

        Label emailInpuLabel = new Label("User Email:");
        emailInpuLabel.getStyleClass().add("text-field-label");
        TextField emailInput = new TextField();

        Label passwordInputLabel = new Label("User Password:");
        passwordInputLabel.getStyleClass().add("text-field-label");
        TextField passwordInput = new TextField();

        Label fNameInputLabel = new Label("User First Name:");
        fNameInputLabel.getStyleClass().add("text-field-label");
        TextField fNameInput = new TextField();

        Label lNameInputLabel = new Label("User Last Name:");
        lNameInputLabel.getStyleClass().add("text-field-label");
        TextField lNameInput = new TextField();


//        TextField ownerInput = new TextField("Ownership");
        CheckBox ownerInput = new CheckBox("User is owner?");
        ownerInput.getStyleClass().add("text-field-label");

        Label dobLabel = new Label("Date Of Birth:");
        dobLabel.getStyleClass().add("text-field-label");
        DatePicker dob = new DatePicker();

        Button submitAddUserInfo = new Button("Submit");
        Button cancelAddUser = new Button("Cancel");
        HBox cancelSubmitBox = new HBox(15, submitAddUserInfo, cancelAddUser);
        cancelAddUser.setOnMouseClicked(event -> {
            stage.setScene(userScene);
        });

        adduserbox.getChildren().addAll(userIDInputLabel, userIdInput, emailInpuLabel,emailInput, passwordInputLabel, passwordInput, fNameInputLabel, fNameInput, lNameInputLabel,lNameInput, ownerInput, dobLabel, dob, cancelSubmitBox);
        adduserbox.getStyleClass().add("pad-label");

        userBox2.getChildren().addAll(adduserlabelBox, adduserbox);
        //userBox2.getChildren().addAll(addUserLabel, fNameInputLabel, fNameInput, lNameInputLabel,lNameInput, ownerInput, userIDInputLabel, userIdInput, emailInpuLabel,emailInput, passwordInputLabel, passwordInput, dobLabel,dob, cancelSubmitBox);

        Button addUser = new Button("Add User");
        addUser.setOnMouseClicked(e ->{
            stage.setScene(addUserScene2);
        });
        submitAddUserInfo.setOnMouseClicked(e ->{
            //User newUser = new Employee(userIdInput.getText(),emailInput.getText(), passwordInput.getText(), fNameInput.getText(), lNameInput.getText() ,dob.getValue(), parseBoolean(ownerInput.getText()));
            //userData.add(newUser);
            try {
                if (dob.getValue() != null){
                    userController.addUser(userIdInput.getText(),emailInput.getText(), passwordInput.getText(), fNameInput.getText(), lNameInput.getText() ,dob.getValue(), ownerInput.isSelected());

                } else {
                    System.out.println("Select Date Of Birth");
                    showErrorPopup("Select Date Of Birth");
                }
            } catch (NoSuchFieldException ex) {
                throw new RuntimeException(ex);
            }
            if (dob.getValue() != null){
                stage.setScene(userScene);
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
                showPopup("Added User");
            }
        });

        Button userBackToMain = new Button("Back To Main");
        userBackToMain.setOnMouseClicked(e ->{
            stage.setScene(MenuScene);
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 1: remove user (done - might need revisiting later)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button removeUser = new Button("Remove User");
        removeUser.setOnMouseClicked(e-> {
            if (userTable.getSelectionModel().getSelectedItem() != null){
                // not sure if any additional work is needed here within the class itself
                //userTable.getSelectionModel().getSelectedItem().
                //userTable.getItems().remove(userTable.getSelectionModel().getSelectedItem());
                userController.removeUser(userTable.getSelectionModel().getSelectedItem().getID());
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
                System.out.println(userController.allEmployees);
                showPopup("User removed");
            } else {
                System.out.println("Need to select a user");
                showErrorPopup("Need to select a user");
            }

        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 2: edit user (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button editUser = new Button("Edit User");

        //Making editPopup
        VBox actualUserEditBox = new VBox(15);
        Scene actualUserEditScene = new Scene(actualUserEditBox,300,250);

        VBox inputBox = new VBox(15);

        HBox pagelabelBox = new HBox();
        pagelabelBox.getStyleClass().add("center-label");
        Label editUserLabel = new Label();
        pagelabelBox.getChildren().add(editUserLabel);
        editUserLabel.getStyleClass().add("page-label");

        HBox textfieldlabelBox = new HBox();
        Label userIDEditLabel = new Label("User ID:");
        textfieldlabelBox.getChildren().add(userIDEditLabel);
        userIDEditLabel.getStyleClass().add("text-field-label");
        TextField userIdEdit = new TextField("");

        HBox emaillabelBox = new HBox();
        Label emailEditLabel = new Label("User Email:");
        emaillabelBox.getChildren().add(emailEditLabel);
        emailEditLabel.getStyleClass().add("text-field-label");
        TextField emailEdit = new TextField("");

        HBox passwordlabelBox = new HBox();
        Label passwordEditLabel = new Label("User Password:");
        passwordlabelBox.getChildren().add(passwordEditLabel);
        passwordEditLabel.getStyleClass().add("text-field-label");
        TextField passwordEdit = new TextField("");

        HBox firstnamelabelBox = new HBox();
        Label fNameEditLabel = new Label("First Name:");
        firstnamelabelBox.getChildren().add(fNameEditLabel);
        fNameEditLabel.getStyleClass().add("text-field-label");
        TextField fNameEdit = new TextField("");

        HBox lastnamelabelBox = new HBox();
        Label lNameEditLabel = new Label("Last Name:");
        lastnamelabelBox.getChildren().add(lNameEditLabel);
        lNameEditLabel.getStyleClass().add("text-field-label");
        TextField lNameEdit = new TextField("");

        HBox isownerlabelBox = new HBox();
        CheckBox ownerEdit = new CheckBox("Is user owner?");
        isownerlabelBox.getChildren().add(ownerEdit);
        ownerEdit.getStyleClass().add("text-field-label");

        HBox doblabelBox = new HBox();
        Label dobEditLabel = new Label("Date of Birth:");
        doblabelBox.getChildren().add(dobEditLabel);
        dobEditLabel.getStyleClass().add("text-field-label");

        DatePicker dobEdit = new DatePicker();
        Button submitUserInfoEdit = new Button("Submit");
        Button cancelEditUser = new Button("Cancel");
        HBox cancelSubmitEditUser = new HBox(15, submitUserInfoEdit, cancelEditUser);
        cancelEditUser.setOnMouseClicked(event -> {
            stage.setScene(userScene);
        });

        inputBox.getChildren().addAll(textfieldlabelBox, userIdEdit, firstnamelabelBox, fNameEdit, lastnamelabelBox, lNameEdit, emaillabelBox, emailEdit, passwordlabelBox, passwordEdit, isownerlabelBox,  doblabelBox, dobEdit, cancelSubmitEditUser);
        inputBox.getStyleClass().add("pad-label");
        actualUserEditBox.getChildren().addAll(pagelabelBox, inputBox);
        //actualUserEditBox.getChildren().addAll(editUserLabel, fNameEditLabel ,fNameEdit, lNameEditLabel,lNameEdit,ownerEdit, userIDEditLabel ,userIdEdit, emailEditLabel, emailEdit, passwordEditLabel, passwordEdit, dobEdit, cancelSubmitEditUser);
        submitUserInfoEdit.setOnMouseClicked(e-> {
            userController.editUser(userTable.getSelectionModel().getSelectedItem().getID(),
                    userIdEdit.getText(), fNameEdit.getText(),lNameEdit.getText(),
                    parseBoolean(ownerEdit.getText()),emailEdit.getText(),
                    passwordEdit.getText(), dobEdit.getValue());
            System.out.println(userTable.getSelectionModel().getSelectedItem());

            taskTable.refresh();
            allTasksInUserViewTable.refresh();
            userTasksTable.refresh();
            CompletedTaskTable.refresh();
            taskUsersTable.refresh();
            allUsersInTaskViewTable.refresh();
            userTable.refresh();
            stage.setScene(userScene);
            showPopup("Added User");
        });

        editUser.setOnMouseClicked(e-> {
            if (userTable.getSelectionModel().getSelectedItem() != null){
                editUserLabel.setText("Edit user with ID (" + userTable.getSelectionModel().getSelectedItem().getID() + ")");
                fNameEdit.setText(userTable.getSelectionModel().getSelectedItem().getFirstName());
                lNameEdit.setText(userTable.getSelectionModel().getSelectedItem().getLastName());
                ownerEdit.setSelected(userTable.getSelectionModel().getSelectedItem().getOwner());
                userIdEdit.setText(userTable.getSelectionModel().getSelectedItem().getID());
                emailEdit.setText(userTable.getSelectionModel().getSelectedItem().getEmail());
                passwordEdit.setText(userTable.getSelectionModel().getSelectedItem().getPassword());
                dobEdit.setValue(userTable.getSelectionModel().getSelectedItem().getDOB());

                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();

                stage.setScene(actualUserEditScene);
                System.out.println(userController.allEmployees);
            } else {
                showErrorPopup("Need to select a user");
                System.out.println("Need to select a user");
            }

        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 3: promote user (done - revisit changes to employee hierarchy) (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button promoteUser = new Button("Promote User");
        promoteUser.setOnMouseClicked(e-> {
            if (userTable.getSelectionModel().getSelectedItem() != null){
                // not sure if any additional work is needed here within the class itself
                //userTable.getSelectionModel().getSelectedItem().
                userController.promoteUser(userTable.getSelectionModel().getSelectedItem().getID());
                //userTable.getSelectionModel().getSelectedItem().setOwner(true);
                showPopup("User promoted");
                System.out.println(userController.allEmployees);
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
            } else {
                System.out.println("Need to select a user");
                showErrorPopup("Need to select a user");
            }

        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Todo 4: Employee Tasks view. ( assigning and unassigning tasks to Employee) (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        VBox employeeTasksBox = new VBox(15);
        employeeTasksBox.getStyleClass().add("pad-label");
        Scene employeeTasksScene = new Scene(employeeTasksBox,300,250);
        Label employeeTasksLabel = new Label("Employee Tasks Popup view");
        HBox employeeTasksLabelBox = new HBox();
        employeeTasksLabelBox.getStyleClass().add("center-label");
        employeeTasksLabelBox.getChildren().add(employeeTasksLabel);
        employeeTasksLabel.getStyleClass().add("page-label");
        Label employeeNameLabel = new Label();
        HBox employeeNameLabelBox = new HBox();
        employeeNameLabelBox.getStyleClass().add("center-label");
        employeeNameLabelBox.getChildren().add(employeeNameLabel);
        employeeNameLabel.getStyleClass().add("text-field-label");

        Button employeeTasks = new Button("View Tasks");
        employeeTasks.setOnMouseClicked(e->{
            if (userTable.getSelectionModel().getSelectedItem() != null){
                userTaskData= userTable.getSelectionModel().getSelectedItem().getTaskList();
                userTasksTable.setItems(userTaskData);
                String firstName = userTable.getSelectionModel().getSelectedItem().getFirstName();
                String lastName =  userTable.getSelectionModel().getSelectedItem().getLastName();
                employeeNameLabel.setText("Employee: " + firstName +" " + lastName);
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
                stage.setScene(employeeTasksScene);
            } else {
                System.out.println("Need to select a user");
                showErrorPopup("Need to select a user");
            }

        });

        userTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                userTaskData= userTable.getSelectionModel().getSelectedItem().getTaskList();
                userTasksTable.setItems(userTaskData);
                String firstName = userTable.getSelectionModel().getSelectedItem().getFirstName();
                String lastName =  userTable.getSelectionModel().getSelectedItem().getLastName();
                employeeNameLabel.setText("Employee: " + firstName +" " + lastName);
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
                stage.setScene(employeeTasksScene);
            }
        });

        userTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                if (userTable.getSelectionModel().getSelectedItem() != null) {
                    userTaskData = userTable.getSelectionModel().getSelectedItem().getTaskList();
                    userTasksTable.setItems(userTaskData);
                    String firstName = userTable.getSelectionModel().getSelectedItem().getFirstName();
                    String lastName = userTable.getSelectionModel().getSelectedItem().getLastName();
                    employeeNameLabel.setText("Employee: " + firstName + " " + lastName);
                    taskTable.refresh();
                    allTasksInUserViewTable.refresh();
                    userTasksTable.refresh();
                    CompletedTaskTable.refresh();
                    taskUsersTable.refresh();
                    allUsersInTaskViewTable.refresh();
                    userTable.refresh();
                    stage.setScene(employeeTasksScene);
                }
            }
        });

        Button employeeAddTasks = new Button("Add Task");
        employeeAddTasks.setOnMouseClicked(e->{
            if (allTasksInUserViewTable.getSelectionModel().getSelectedItem() != null) {
                //userTable.getSelectionModel().getSelectedItem().addTask(allTasksInUserViewTable.getSelectionModel().getSelectedItem());
                userController.assignTask(userTable.getSelectionModel().getSelectedItem().getID(), allTasksInUserViewTable.getSelectionModel().getSelectedItem());
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
                showPopup("Task Added");
                System.out.println("the add task button has been clicked");
            } else {
                showErrorPopup("No task to be added");
            }
        });

        Button employeeRemoveTasks = new Button("Remove Task");
        employeeRemoveTasks.setOnMouseClicked(e->{
            //userTable.getSelectionModel().getSelectedItem().removeTask(userTasksTable.getSelectionModel().getSelectedItem().getID());
            if (userTasksTable.getSelectionModel().getSelectedItem() != null){
                userController.unAssignTask( userTable.getSelectionModel().getSelectedItem().getID(), userTasksTable.getSelectionModel().getSelectedItem());
                taskTable.refresh();
                allTasksInUserViewTable.refresh();
                userTasksTable.refresh();
                CompletedTaskTable.refresh();
                taskUsersTable.refresh();
                allUsersInTaskViewTable.refresh();
                userTable.refresh();
                showPopup("Task removed");
                System.out.println( "the remove task button has been clicked");
            } else {
                showErrorPopup("No task to be removed");
            }

        });

        Button employeeTasksBackToMain = new Button("Back");
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
        taskLabelWithinEmployeeTasks.getStyleClass().add("page-label");
        HBox topUserAddTaskBar= new HBox(15);
        topUserAddTaskBar.getChildren().addAll( employeeAddTasks, employeeRemoveTasks ,employeeTasksBackToMain);


        employeeTasksBox.getChildren().addAll(employeeTasksLabel, employeeNameLabel, topUserAddTaskBar, userTasksTable, taskLabelWithinEmployeeTasks, allTasksInUserViewTable);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Todo 4.3: User Table Formatting (done)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        HBox userFunctionBar = new HBox();
        userFunctionBar.getStyleClass().add("function-bar");
        userBackToMain.getStyleClass().add("back-button");
        HBox topUserBar= new HBox();
        topUserBar.getStyleClass().add("top-bar");
        HBox.setHgrow(userFunctionBar, Priority.ALWAYS);
        HBox.setHgrow(userBackToMain, Priority.ALWAYS);
        userFunctionBar.getChildren().addAll(addUser,editUser,removeUser, promoteUser ,employeeTasks);
        topUserBar.getChildren().addAll(userFunctionBar,userBackToMain);

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
        VBox userTableContainer = new VBox();
        userTableContainer.getStyleClass().add("table-container");
        userTableContainer.getChildren().add(userTable);
        userPage.getChildren().addAll(topUserBar,userTableContainer);

        // css
        addUserScene2.getStylesheets().add(getClass().getClassLoader().getResource("user.css").toExternalForm());
        actualUserEditScene.getStylesheets().add(getClass().getClassLoader().getResource("user.css").toExternalForm());
        employeeTasksScene.getStylesheets().add(getClass().getClassLoader().getResource("user.css").toExternalForm());

        this.getChildren().add(userPage);
    }


    /**
     * Sets the primary stage and scenes of the application. Specifically handles the
     * menu and user scenes. Additionally, applies a stylesheet to the user scene.
     *
     * @param stage The primary JavaFX stage where scenes will be displayed.
     * @param MenuScene The scene representing the application's main menu.
     * @param userScene The scene representing the user view of the application and to which the style sheet is to be applied.
     *
     */
    public void setStageMenuUser(Stage stage, Scene MenuScene, Scene userScene){
        this.stage = stage;
        this.MenuScene = MenuScene;
        this.userScene = userScene;
        userScene.getStylesheets().add(getClass().getClassLoader().getResource("user.css").toExternalForm());
    }

    private void showPopup(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("MESSAGE");
        alert.setHeaderText("CONFIRM MESSAGE");
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorPopup(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText("INVALID");
        alert.setContentText(content);
        alert.showAndWait();
    }

}
