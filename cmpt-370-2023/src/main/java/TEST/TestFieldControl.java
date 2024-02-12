package TEST;

import control.FieldControl;
import org.InitialFarm.Crop;
import org.InitialFarm.dataManager;

import java.time.LocalDate;

public class TestFieldControl {

    static String error_message(String test, String expected, String result) {
        return "Test Error: " + test + ". Expected: " + expected +
                ". Returned: " + result + " instead. :(";
    }

    public static void main() {

        int count = 0, failed = 0;

        String fID = "f1D";
        String fName = "Field1";
        String fLocation = "saskatoon";
        int fSize = 50;

        String fID2 = "f1D2";
        String fName2 = "Field2";
        String fLocation2 = "vancouver";
        int fSize2 = 40;

        String fID3 = "f1D3";
        String fName3 = "Field3";
        String fLocation3 = "toronto";
        int fSize3 = 70;

        String fID4 = "f1D4";
        String fName4 = "Field4";
        String fLocation4 = "ottawa";
        int fSize4 = 45;

        String chemName = " chem1";
        String chemGroup = " Group 11";

        FieldControl FieldController = new FieldControl();
        dataManager manager = new dataManager();

        // testing makeCROP() its functionality is tested further below
        Crop newCrop1db = FieldController.makeCrop(null, "corn", "yellow corn", 50.0);
        Crop newCrop2db = FieldController.makeCrop(null, "beans", "honey beans", 35.0);

        String reason1 = "Testing get field list size using data manager";
        int result1 = FieldController.fieldList.size();
        int expected1 = 0;
        if (result1 != (expected1)) {
            failed++;
            System.out.println(error_message(reason1, String.valueOf(expected1),
                    String.valueOf(result1)));
        }

        String reason2 = "Testing addField() using field list size";
        FieldController.addField(fID, fName, fSize, fLocation);
        FieldController.addField(fID2, fName2, fSize2, fLocation2);
        FieldController.addField(fID3, fName3, fSize3, fLocation3);
        result1 = FieldController.fieldList.size();
        expected1 = 3;
        if (result1 != (expected1)) {
            failed++;
            System.out.println(error_message(reason2, String.valueOf(expected1),
                    String.valueOf(result1)));

            String reason3 = "Testing delete field() using bin list size";
            FieldController.deleteField(FieldController.fieldList.get(2).getID());
            result1 = FieldController.fieldList.size();
            expected1 = 2;
            if (result1 != (expected1)) {
                failed++;
                System.out.println(error_message(reason3, String.valueOf(expected1),
                        String.valueOf(result1)));
            }

            String reason4 = "Testing addCrop()";
            FieldController.addField(fID3, fName3, fSize3, fLocation3);
            FieldController.addCrop(FieldController.fieldList.get(0).getID(), newCrop2db, 50, LocalDate.now());
            FieldController.addCrop(FieldController.fieldList.get(1).getID(), newCrop1db, 55, LocalDate.now());
            FieldController.addCrop(FieldController.fieldList.get(2).getID(), newCrop1db, 73, LocalDate.now());
            String result = FieldController.fieldList.get(2).getCurrent_Year().getCrop().getCropType();
            String expected = newCrop1db.getCropType();
            if (!result.equals(expected)) {
                failed++;
                System.out.println(error_message(reason4, expected, result));
            }

            String reason5 = "Testing editField()";
            FieldController.editField(fID3, fID4, fName4, fSize4, fLocation4);
            result = FieldController.fieldList.get(2).getCurrent_Year().getCrop().getCropType();
            expected = newCrop1db.getCropType();
            if (!result.equals(expected)) {
                failed++;
                System.out.println(error_message(reason5, expected, result));
            }

            String reason6 = "Testing addChemical()";
            FieldController.addChemical(fID4, 30.0, chemName, chemGroup, LocalDate.now());
            result = FieldController.fieldList.get(2).getCurrent_Year().getChemical_sprayed().getChemicalName();
            if (!result.equals(chemName)) {
                failed++;
                System.out.println(error_message(reason6, chemName, (result)));
            }

        /*
            to ensure a clean slate after testing, delete all bins added during testing.
         */
            FieldController.deleteField(FieldController.fieldList.get(2).getID());
            FieldController.deleteField(FieldController.fieldList.get(1).getID());
            FieldController.deleteField(FieldController.fieldList.get(0).getID());
            manager.removeClass(newCrop1db);
            manager.removeClass(newCrop2db);


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
}
