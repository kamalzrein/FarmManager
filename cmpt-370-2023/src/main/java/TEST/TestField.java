package TEST;


import org.entities.Field;

import java.time.LocalDate;
import java.util.Calendar;

public class TestField {
    static String error_message(String test, String expected, String result) {
        return "Test Error: " + test + ". Expected: " + expected+
                ". Returned: " + result + " instead. :(";
    }

    public static void main(){

        int count = 0, failed = 0;

        /* For testing the Field class */
        /* Field creation variables */
        String ID, fName, location;
        double size;

        /* size manipulation variables */
        double result2;
        double expected2;
        String result, expected;

        /* Year and Date manipulation variables */
        int year, test_year;
        LocalDate new_date;

        String reason1 = "Constructor + getName()";
        ID = "F123";
        fName = "Field1";
        location = "Northwest";
        size = 123;

        // test all methods with this instance
        Field Field1 = new Field(null, ID ,fName, size, location);
        result = Field1.getName();
        expected = "Field1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason1, expected, result));}


        String reason2 = "Testing getName()";
        result = Field1.getName();
        expected = fName;
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason2, expected, result));}

        String reason3 = "Testing getID()";
        result = Field1.getID();
        expected = ID;
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason3, expected, result));}


        String reason4 = "Testing getSize()";
        result2 = Field1.getSize();
        expected2 = size;
        if (result2 != (expected2)) { failed ++ ;System.out.println(error_message(reason4, String.valueOf(expected2), String.valueOf(result2)));}


        String reason5 = "Testing getLocation()";
        result = Field1.getLocation();
        expected = location;
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason5, expected, result));}


        String reason6 = "Testing setName()";
        expected = "Field2";
        Field1.setName(expected);
        result = Field1.getName();
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason6, expected, result));}


        String reason7 = "Testing setSize()";
        expected2 = 233;
        Field1.setSize(expected2);
        result2 = Field1.getSize();
        if (result2 != (expected2)) { failed ++ ;System.out.println(error_message(reason7, String.valueOf(expected2), String.valueOf(result2)));}


        String reason8 = "Testing years list after constructor";
        int yearResult = Field1.getYears().size();
        if (yearResult != 0) { failed ++ ;System.out.println(error_message(reason8, "0", String.valueOf(yearResult)));}



        String reason9 = "Testing getCurrent_year() with  newYear()";
        year = 2013;
        new_date = LocalDate.of(2013, Calendar.AUGUST, 23);
        Field1.newYear(year, new_date);
        test_year = Field1.getCurrent_Year().getYear();
        if (test_year != (year)) { failed ++ ;System.out.println(error_message(reason9, String.valueOf(year), String.valueOf(test_year)));}


        String reason10 = "Testing years list after newYear()";
        yearResult = Field1.getYears().size();
        if (yearResult != 1) { failed ++ ;System.out.println(error_message(reason10, "1", String.valueOf(yearResult)));}


        String reason11 = "Testing getYears()";
        yearResult = Field1.getCurrent_Year().getYear();
        if (yearResult != (year)) { failed ++ ;System.out.println(error_message(reason11, String.valueOf(year), String.valueOf(yearResult)));}


        String reason12 = "Testing toString(year)";
        result = Field1.toString(Field1.getCurrent_Year());
        expected = """
                Name = Field2  ID = F123  Location = Northwest  Size = 233.0 acres.
                Year = 2013,
                 new year starting date = 2013-07-23,
                 crop = null,
                 seeding_date = null,
                 seeding_rate = 0.0lbs/acre,
                 fertilizer_rate = 0.0lbs/acre,
                 spraying_date = null,
                 harvest_date = null,
                 end_of_year = null,
                 chemical_records
                 \s
                 task_records
                 \s
                """;
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason12, expected, result));}


        String[] reasons = {reason1, reason2, reason3, reason4, reason5, reason6, reason7,
                            reason8, reason9, reason10, reason11, reason12};
        System.out.println("*** Field Class Testing  ***\n");
        for (String reason : reasons) {
            count ++;
            System.out.println("Passed " + (count - failed) + " out of total " + count + " tests");
        }
        System.out.println("Total Tests: " + count + " Tests. Test Passed: " + (count - failed) + " tests");
        System.out.println("-----------------------------------\n");




    }

}

