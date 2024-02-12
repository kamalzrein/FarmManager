package TEST;

import org.InitialFarm.Chemical;

import java.util.ArrayList;
import java.util.List;

public class TestChemical {

    static String error_message(String test, String expected, String result) {
        return "Test Error: " + test + ". Expected: " + expected+
                ". Returned: " + result + " instead. :(";
    }

    public static void main (){

        int count = 0, failed = 0;

        String chemName = "Chem1";
        List<String> chemGroup = new ArrayList<>();

        String reason1 = "Testing Constructor + getName()";
        Chemical Chemical1 = new Chemical(null, chemName, chemGroup);
        String result = Chemical1.getChemicalName();
        String expected = "Chem1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason1, expected, result));}

        String reason2 = "Testing getName()";
        result = Chemical1.getChemicalName();
        expected = "Chem1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason2, expected, result));}

        String reason3 = "Testing getGroup() with empty group";
        List<String> testChemGroup = new ArrayList<>();
        List<String> resultChemGroup;
        resultChemGroup = Chemical1.getChemicalGroup();
        if (!resultChemGroup.equals(testChemGroup)) { failed ++ ;System.out.println(error_message(reason3, String.valueOf(testChemGroup), String.valueOf(resultChemGroup)));}

        String reason4 = "Testing getGroup() with a group";
        testChemGroup.add("4"); testChemGroup.add("14");
        Chemical1.getChemicalGroup().add("4"); Chemical1.getChemicalGroup().add("14");
        resultChemGroup = Chemical1.getChemicalGroup();
        if (!resultChemGroup.equals(testChemGroup)) { failed ++ ;System.out.println(error_message(reason4, String.valueOf(testChemGroup), String.valueOf(resultChemGroup)));}


        String[] reasons = {reason1, reason2, reason3, reason4};
        System.out.println("*** Chemical Class Testing  ***\n");
        for (String reason : reasons) {
            count ++;
            System.out.println("Passed " + (count - failed) + " out of total " + count + " tests");
        }
        System.out.println("Total Tests: " + count + " Tests. Test Passed: " + (count - failed) + " tests");
        System.out.println("-----------------------------------\n");


    }
}
