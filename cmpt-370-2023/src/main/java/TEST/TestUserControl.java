package TEST;

import control.UserControl;
import org.InitialFarm.Crop;
import org.InitialFarm.dataManager;
import org.entities.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestUserControl {

    static String error_message(String test, String expected, String result) {
        return "Test Error: " + test + ". Expected: " + expected +
                ". Returned: " + result + " instead. :(";
    }

    public static void main() throws NoSuchFieldException {

        int count = 0, failed = 0;

        String user_id = "uID";
        String user_email = "uEmail";
        String user_password = "uPassword";
        String first_name = "uFirstName";
        String last_name = "uLastName";
        LocalDate dob = LocalDate.of(2012, 12, 24);
        boolean owner = true;

        String user_id2 = "uID2";
        String user_email2 = "uEmail2";
        String user_password2 = "uPassword2";
        String first_name2 = "uFirstName2";
        String last_name2 = "uLastName2";
        LocalDate dob2 = LocalDate.of(2009, 11, 12);
        boolean owner2 = false;

        String user_id3 = "uID3";
        String user_email3 = "uEmail3";
        String user_password3 = "uPassword3";
        String first_name3 = "uFirstName3";
        String last_name3 = "uLastName3";
        LocalDate dob3 = LocalDate.of(2002, 9, 9);
        boolean owner3 = false;

        String user_id4 = "uID3";
        String user_email4 = "uEmail3";
        String user_password4 = "uPassword3";
        String first_name4 = "uFirstName3";
        String last_name4 = "uLastName3";
        LocalDate dob4 = LocalDate.of(2000, 7, 15);
        boolean owner4 = false;

        UserControl UserController = new UserControl();
        dataManager manager = new dataManager();

        String reason1 = "Testing get employee size using data manager";
        int result1 = UserController.allEmployees.size();
        int expected1 = 0;
        if (result1 != (expected1)) {
            failed++;
            System.out.println(error_message(reason1, String.valueOf(expected1),
                    String.valueOf(result1)));
        }

        String reason2 = "Testing addUser() using field list size";
        UserController.addUser(user_id, user_email, user_password, first_name, last_name, dob, owner);
        UserController.addUser(user_id2, user_email2, user_password2, first_name2, last_name2, dob2, owner2);
        UserController.addUser(user_id3, user_email3, user_password3, first_name3, last_name3, dob3, owner3);
        result1 = UserController.allEmployees.size();
        expected1 = 3;
        if (result1 != (expected1)) {
            failed++;
            System.out.println(error_message(reason2, String.valueOf(expected1),
                    String.valueOf(result1)));
        }

        String reason3 = "Testing deleteUser() using bin list size";
        UserController.removeUser(UserController.allEmployees.get(2).getID());
        result1 = UserController.allEmployees.size();
        expected1 = 2;
        if (result1 != (expected1)) {
            failed++;
            System.out.println(error_message(reason3, String.valueOf(expected1),
                    String.valueOf(result1)));
        }

        String reason4 = "Testing editField()";
        UserController.editUser(user_id2, user_id4, first_name4, last_name4, owner4, user_email4, user_password4, dob4);
        String result = UserController.allEmployees.get(0).getFirstName();
        if (!result.equals(first_name4)) {
            failed++;
            System.out.println(error_message(reason4, first_name4, result));
        }

        String reason5 = "Testing promoteUser()";
        UserController.promoteUser(user_id2);
        boolean ownerResult = UserController.allEmployees.get(0).getOwner();
        if (!ownerResult) {
            failed++;
            System.out.println(error_message(reason5, "true" , String.valueOf(false)));
        }

        String reason6 = "Testing assign User ()";
        Task task = new Task(null, "taskID", "plant lentils", " make sure to plant them well", LocalDateTime.now());
        Task task1db = manager.saveClass(task);
        UserController.assignTask(user_id, task1db);
        result = UserController.allEmployees.get(0).getTaskList().get(0).getTaskName();
        String expected = "plant lentils";
        if (!result.equals(expected)) {
            failed++;
            System.out.println(error_message(reason6, expected , result));
        }

        /*
            to ensure a clean slate after testing, delete all bins added during testing.
         */
        UserController.removeUser(UserController.allEmployees.get(1).getID());
        UserController.removeUser(UserController.allEmployees.get(0).getID());
        manager.removeClass(task1db);


        String[] reasons = {reason1, reason2, reason3, reason4, reason5, reason6};
        System.out.println("*** Field Control Class Testing  ***\n");
        for (String reason : reasons) {
            count++;
            System.out.println("Passed " + (count - failed) + " out of total " + (count) + " tests"); // reason  8 isn't tested
        }
        System.out.println("Total Tests: " + count + " Tests. Test Passed: " + (count - failed) + " tests");
        System.out.println("-----------------------------------\n");
        }

    }

