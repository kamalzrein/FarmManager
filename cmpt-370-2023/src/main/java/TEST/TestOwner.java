package TEST;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.entities.Owner;
import org.entities.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;

public class TestOwner {

    static String error_message(String test, String expected, String result) {
        return "Test Error: " + test + ". Expected: " + expected+
                ". Returned: " + result + " instead. :(";
    }


    public static void main(){

        int count = 0, failed = 0;

        LocalDate dob = LocalDate.of(2002, Calendar.FEBRUARY, 2);
        LocalDate specificDate = LocalDate.of(2111, Month.JANUARY, 1);

        Task task1 = new Task(null, "1", "task 1", "task 1 description",  LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));
        Task task2 = new Task(null, "2", "task 2", "task 2 description",  LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));
        Task task3 = new Task(null, "3", "task 3", "task 3 description",  LocalDateTime.of(2012, Month.JANUARY, 2, 13, 32, 43));


        String reason1 = "Constructor + getFirstName()";
        Owner staff1 = new Owner(null, "ID_1", "John1@gmail.com", "pass1", "John1", "Josh1", dob);
        String result = staff1.getFirstName();
        String expected = "John1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason1, expected, result));}


        String reason2 = "Testing getFirstName()";
        result = staff1.getFirstName();
        expected = "John1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason2, expected, result));}

        String reason3 = "Testing getID()";
        result = staff1.getID();
        expected = "ID_1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason3, expected, result));}

        String reason4 = "Testing getLastName()";
        result = staff1.getLastName();
        expected = "Josh1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason4, expected, result));}

        String reason5 = "Testing getEmail()";
        result = staff1.getEmail();
        expected = "John1@gmail.com";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason5, expected, result));}

        String reason6 = "Testing getPassword()";
        result = staff1.getPassword();
        expected = "pass1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason6, expected, result));}

        String reason7 = "Testing getDOB()";
        LocalDate result2 = staff1.getDOB();
        if (!result2.equals(dob)) { failed ++ ;System.out.println(error_message(reason7, String.valueOf(dob), String.valueOf(result2)));}

        String reason8 = "Testing setFirstName()";
        staff1.setFirstName("John2");
        result = staff1.getFirstName();
        expected = "John2";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason8, expected, result));}

        String reason9 = "Testing setID()";
        staff1.setID("ID_2");
        result = staff1.getID();
        expected = "ID_2";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason9, expected, result));}

        String reason10 = "Testing setLastName()";
        staff1.setLastName("Josh2");
        result = staff1.getLastName();
        expected = "Josh2";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason10, expected, result));}

        String reason11 = "Testing setEmail()";
        staff1.setEmail("John2@gmail.com");
        result = staff1.getEmail();
        expected = "John2@gmail.com";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason11, expected, result));}

        String reason12 = "Testing setPassword()";
        staff1.setPassword("pass2");
        result = staff1.getPassword();
        expected = "pass2";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason12, expected, result));}

        String reason13 = "Testing setDOB()";
        staff1.setDOB(specificDate);
        result2 = staff1.getDOB();
        if (!result2.equals(specificDate)) { failed ++ ;System.out.println(error_message(reason13, String.valueOf(specificDate), String.valueOf(result2)));}

        String reason14 = "Testing getTasks() with no task";
        ObservableList<Task> testTask = FXCollections.observableArrayList();
        ObservableList<Task> resultTask;
        resultTask = staff1.getTaskList();
        if (!resultTask.equals(testTask)) { failed ++ ;System.out.println(error_message(reason14, String.valueOf(testTask), String.valueOf(resultTask)));}

        String reason15 = "Testing getTasks() with tasks";
        staff1.getTaskList().add(task1); staff1.getTaskList().add(task2); staff1.getTaskList().add(task3);
        testTask.add(task1); testTask.add(task2); testTask.add(task3);
        resultTask = staff1.getTaskList();
        if (!resultTask.equals(testTask)) { failed ++ ;System.out.println(error_message(reason15, String.valueOf(testTask), String.valueOf(resultTask)));}

        String reason16 = "Testing removeTasks() with tasks";
        staff1.removeTask(task3.getID());
        testTask.remove(2);
        resultTask = staff1.getTaskList();
        if (!resultTask.equals(testTask)) { failed ++ ;System.out.println(error_message(reason16, String.valueOf(testTask), String.valueOf(resultTask)));}

        String reason17 = "Testing removeAllTasks() with tasks";
        staff1.removeAllTasks();
        testTask.remove(0); testTask.remove(0);
        resultTask = staff1.getTaskList();
        if (!resultTask.equals(testTask)) { failed ++ ;System.out.println(error_message(reason17, String.valueOf(testTask), String.valueOf(resultTask)));}

        String[] reasons = {reason1, reason2, reason3, reason4, reason5, reason6, reason7,
                reason8, reason9, reason10, reason11, reason12, reason13, reason14, reason15,
                reason16, reason17};
        System.out.println("*** Owner Class Testing  ***\n");
        for (String reason : reasons) {
            count ++;
            System.out.println("Passed " + (count - failed) + " out of total " + count + " tests");
        }
        System.out.println("Total Tests: " + count + " Tests. Test Passed: " + (count - failed) + " tests");
        System.out.println("-----------------------------------\n");




    }
}
