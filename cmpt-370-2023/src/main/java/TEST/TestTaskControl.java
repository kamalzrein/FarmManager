package TEST;

import control.TaskControl;
import org.InitialFarm.dataManager;
import org.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestTaskControl {

    static String error_message(String test, String expected, String result) {
        return "Test Error: " + test + ". Expected: " + expected +
                ". Returned: " + result + " instead. :(";
    }

    public static void main() throws NoSuchFieldException {

        int count = 0, failed = 0;

        String task_id = "tID";
        String task_name = "tName";
        String task_description = "tDescription";
        LocalDateTime dueDate = LocalDateTime.of(2012, 12, 24, 18, 30);

        String task_id2 = "tID2";
        String task_name2 = "tName2";
        String task_description2 = "tDescription2";
        LocalDateTime dueDate2 = LocalDateTime.of(2009, 11, 12, 23, 0);

        String task_id3 = "tID3";
        String task_name3 = "tName3";
        String task_description3 = "tDescription3";
        LocalDateTime dueDate3 = LocalDateTime.of(2002, 9, 9, 12, 0);

        String task_id4 = "tID4";
        String task_name4 = "tName4";
        String task_description4 = "tDescription4";
        LocalDateTime dueDate4 = LocalDateTime.of(2000, 7, 15, 23, 30);

        TaskControl TaskController = new TaskControl();
        dataManager manager = new dataManager();

        String reason1 = "Testing get taskList size using data manager";
        int result1 = TaskController.taskList.size();
        int result2 = TaskController.finishedTaskList.size();
        int expected1 = 0;
        int expected2 = 0;
        if ((result1 != expected1) || (result2 != expected2)){
            failed++;
            System.out.println(error_message(reason1, String.valueOf(expected1),
                    String.valueOf(result1)));
        }

        String reason2 = "Testing addTask() using field list size";
        TaskController.addTask(task_id, task_name, task_description, dueDate);
        TaskController.addTask(task_id2, task_name2, task_description2, dueDate2);
        TaskController.addTask(task_id3, task_name3, task_description3, dueDate3);
        result1 = TaskController.taskList.size();
        expected1 = 3;
        if (result1 != (expected1)) {
            failed++;
            System.out.println(error_message(reason2, String.valueOf(expected1),
                    String.valueOf(result1)));
        }

        String reason3 = "Testing complete Task() using task lists";
        TaskController.completeTask(TaskController.taskList.get(2).getID());
        result1 = TaskController.finishedTaskList.size();
        expected1 = 1;
        result2 = TaskController.taskList.size();
        expected2 = 2;
        if ((result1 != expected1) || (result2 != expected2)){
            failed++;
            System.out.println(error_message(reason1, String.valueOf(expected1),
                    String.valueOf(result1)));
        }

        String reason4 = "Testing editTask()";
        TaskController.editTask(task_id2, task_id4, task_name4, task_description4, dueDate4);
        String result = TaskController.taskList.get(1).getTaskName();
        if (!result.equals(task_name4)) {
            failed++;
            System.out.println(error_message(reason4, task_name4, result));
        }

        String reason5 = "Testing Assign employee()";
        User user = new User(null, "user_id", "user_email", "user_password",
                "first_name", "last_name", LocalDate.of(2012, 8, 24),
                false);
        User user1db = manager.saveClass(user);
        TaskController.assignEmployee(task_id2, user1db);
        result = TaskController.taskList.get(1).getStaffList().get(0).getFirstName();
        String expected = user1db.getFirstName();
        if (!result.equals(expected)) {
            failed++;
            System.out.println(error_message(reason5, expected , result));
        }

        /*
            to ensure a clean slate after testing, delete all bins added during testing.
         */
        TaskController.completeTask(TaskController.taskList.get(1).getID());
        TaskController.completeTask(TaskController.taskList.get(0).getID());
        manager.removeClass(user1db);


        String[] reasons = {reason1, reason2, reason3, reason4, reason5};
        System.out.println("*** Field Control Class Testing  ***\n");
        for (String reason : reasons) {
            count++;
            System.out.println("Passed " + (count - failed) + " out of total " + (count) + " tests"); // reason  8 isn't tested
        }
        System.out.println("Total Tests: " + count + " Tests. Test Passed: " + (count - failed) + " tests");
        System.out.println("-----------------------------------\n");
    }

}

