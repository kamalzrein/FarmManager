package TEST;

import javafx.collections.ObservableList;
import org.entities.User;
import org.entities.Owner;
import org.entities.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;

public class TestTask {
    static String error_message(String test, String expected, String result) {
        return "Test Error: " + test + ". Expected: " + expected +
                ". Returned: " + result + " instead. :(";
    }


    public static void main() {

        int count = 0, failed = 0;

        LocalDate dob = LocalDate.of(2002, Calendar.FEBRUARY, 2);

        Owner staff1 = new Owner(null, "ID_1", "John1@gmail.com", "pass1", "John1", "Josh1", dob);
        Owner staff2 = new Owner(null, "ID_2", "John2@gmail.com", "pass2", "John2", "Josh2", dob);

        LocalDateTime duDate = LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43);
        LocalDateTime duDate2 = LocalDateTime.of(2024, Month.JANUARY, 2, 13, 32, 43);

        String reason1 = "Constructor + getTaskName()";
        Task task1 = new Task(null, "1", "task 1", "task 1 description",  duDate);
        String result = task1.getTaskName();
        String expected = "task 1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason1, expected, result));}

        String reason2 = "Testing getTaskName()";
        result = task1.getTaskName();
        expected = "task 1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason2, expected, result));}

        String reason3 = "Testing getID()";
        result = task1.getID();
        expected = "1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason3, expected, result));}

        String reason4 = "Testing getDescription()";
        result = task1.getDescription();
        expected = "task 1 description";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason4, expected, result));}

        String reason5 = "Testing getDueDate()";
        LocalDateTime result2 = task1.getDueDate();
        if (!result2.equals(duDate)) { failed ++ ;System.out.println(error_message(reason5, String.valueOf(duDate), String.valueOf(result2)));}

        String reason6 = "Testing getStatus() without setStatus()";
        result = task1.getStatus();
        expected = "Incomplete";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason6, expected, result));}

        String reason7 = "Testing getStatus() with setStatus()";
        task1.setStatus("Paused");
        result = task1.getStatus();
        expected = "Paused";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason7, expected, result));}

        String reason8 = "Testing getStatus() with setStatus()";
        task1.setStatus("Completed");
        result = task1.getStatus();
        expected = "Completed";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason8, expected, result));}

        String reason9 = "Testing getStatus() with setStatus()";
        task1.setStatus("Incomplete");
        result = task1.getStatus();
        expected = "Incomplete";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason9, expected, result));}

        String reason10 = "Testing addStaff() + getStaffList";
        task1.addStaff(staff1);
        ObservableList<User> result3 = task1.getStaffList();
        ArrayList <User> expected3 =   new ArrayList<>();
        expected3.add(staff1);
        if (!result3.equals(expected3)) { failed ++ ;System.out.println(error_message(reason10, String.valueOf(expected3), String.valueOf(result3)));}

        String reason11 = "Testing addStaff() + getStaffList (multiple append)";
        task1.addStaff(staff2);
        result3 = task1.getStaffList();
        expected3.add(staff2);
        if (!result3.equals(expected3)) { failed ++ ;System.out.println(error_message(reason11, String.valueOf(expected3), String.valueOf(result3)));}

        String reason12 = "Testing removeStaff() + getStaffList";
        task1.removeStaff("ID_2");
        result3 = task1.getStaffList();
        expected3.remove(staff2);
        if (!result3.equals(expected3)) { failed ++ ;System.out.println(error_message(reason12, String.valueOf(expected3), String.valueOf(result3)));}

        String reason13 = "Testing removeStaff() + getStaffList";
        task1.removeStaff("ID_1");
        result3 = task1.getStaffList();
        expected3.remove(staff1);
        if (!result3.equals(expected3)) { failed ++ ;System.out.println(error_message(reason13, String.valueOf(expected3), String.valueOf(result3)));}

        String reason14 = "Testing removeAll() + getStaffList";
        task1.addStaff(staff1);
        task1.addStaff(staff2);
        task1.removeAllStaff();
        result3 = task1.getStaffList();
        expected3.add(staff1);
        expected3.add(staff2);
        expected3.clear();
        if (!result3.equals(expected3)) { failed ++ ;System.out.println(error_message(reason14, String.valueOf(expected3), String.valueOf(result3)));}

        String reason15 = "Testing isCompleted()";
        task1.markAsCompleted(true);
        Boolean result4 = task1.isCompleted();
        Boolean expected4 = true;
        if (!result4.equals(expected4)) { failed ++ ;System.out.println(error_message(reason15, String.valueOf(expected4), String.valueOf(result4)));}

        String reason16 = "Testing isPaused()";
        task1.pauseTask(true);
        result4 = task1.isPaused();
        expected4 = true;
        if (!result4.equals(expected4)) { failed ++ ;System.out.println(error_message(reason16, String.valueOf(expected4), String.valueOf(result4)));}

        String reason17 = "Testing isOverDue()";
        result4 = task1.isOverDue();
        expected4 = true;
        if (!result4.equals(expected4)) { failed ++ ;System.out.println(error_message(reason17, String.valueOf(expected4), String.valueOf(result4)));}

        String reason18 = "Testing setTaskName() + getTaskName()";
        task1.setTaskName("Water Crops");
        result = task1.getTaskName();
        expected = "Water Crops";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason18, expected, result));}

        String reason19 = "Testing setID ()+ getID()";
        task1.setID("1234");
        result = task1.getID();
        expected = "1234";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason19, expected, result));}

        String reason20 = "Testing setDescription() + getDescription()";
        task1.setDescription("Ensure you wet the crops");
        result = task1.getDescription();
        expected = "Ensure you wet the crops";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason20, expected, result));}

        String reason21 = "Testing setDueDate + getDueDate()";
        task1.setDueDate(duDate2);
        result2 = task1.getDueDate();
        if (!result2.equals(duDate2)) { failed ++ ;System.out.println(error_message(reason21, String.valueOf(duDate2), String.valueOf(result2)));}

        String reason22 = "Testing isOverDue()";
        result4 = task1.isOverDue();
        expected4 = false;
        if (!result4.equals(expected4)) { failed ++ ;System.out.println(error_message(reason22, String.valueOf(expected4), String.valueOf(result4)));}

        String reason23 = "Testing toString()";
        result = task1.toString();
        expected =
                "Task ID: 1234" +
                "\nTask name: Water Crops"+
                        "\nTask dbID: null" +
                "\nTask Description: Ensure you wet the crops" +
                "\nCreated: " + task1.getDate() +
                "\nStatus: Paused" +
                "\nCompletion Date: " + task1.getCompletionDate() +
                "\nPause Date: " + task1.getPauseDate() +
                "\nStaffs: [ empty ]";

        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason23, expected, result));}

        String[] reasons = {reason1, reason2, reason3, reason4, reason5, reason6, reason7,
                reason8, reason9, reason10, reason11, reason12, reason13, reason14, reason15,
                reason16, reason17, reason18, reason19, reason20, reason21, reason22, reason23};

        System.out.println("*** Task Class Testing  ***\n");
        for (String reason : reasons) {
            count ++;
            System.out.println("Passed " + (count - failed) + " out of total " + count + " tests");
        }
        System.out.println("Total Tests: " + count + " Tests. Test Passed: " + (count - failed) + " tests");
        System.out.println("-----------------------------------\n");

    }
}