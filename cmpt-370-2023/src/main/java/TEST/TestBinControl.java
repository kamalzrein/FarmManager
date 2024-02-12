package TEST;

import control.BinControl;
import org.InitialFarm.Crop;
import org.InitialFarm.dataManager;

public class TestBinControl {

    static String error_message(String test, String expected, String result) {
        return "Test Error: " + test + ". Expected: " + expected+
                ". Returned: " + result + " instead. :(";
    }

    public static void main() {

        int count = 0, failed = 0;

        String binName = "GrainBin1";
        String binLocation = "saskatoon";
        int binSize = 50;
        boolean hopper = true;
        boolean fan = true;

        String binName2 = "GrainBin2";
        String binLocation2 = "warman";
        int binSize2 = 40;
        boolean hopper2 = false;
        boolean fan2 = true;

        String binName3 = "GrainBin3";
        String binLocation3 = "princealbert";
        int binSize3 = 70;
        boolean hopper3 = false;
        boolean fan3 = false;

        BinControl BinController = new BinControl();
        dataManager manager = new dataManager();

        Crop newCrop1 = new Crop(null,"corn","yellow corn", 50.0);
        Crop newCrop1db = manager.saveClass(newCrop1);
        Crop newCrop2 = new Crop(null,"beans","honey beans", 35.0);
        Crop newCrop2db = manager.saveClass(newCrop2);

        String reason1 = "Testing get bin list size using data manager";
        int result1 = BinController.binList.size();
        int expected1 = 0;
        if (result1 != (expected1)) { failed ++ ;System.out.println(error_message(reason1, String.valueOf(expected1),
                String.valueOf(result1)));}

        String reason2 = "Testing addBin() using bin list size";
        BinController.addBin(binName, binSize, binLocation, hopper, fan);
        BinController.addBin(binName2, binSize2, binLocation2, hopper2, fan2);
        BinController.addBin(binName3, binSize3, binLocation3, hopper3, fan3);
        result1 = BinController.binList.size();
        expected1 = 3;
        if (result1 != (expected1)) { failed ++ ;System.out.println(error_message(reason2, String.valueOf(expected1),
                String.valueOf(result1)));}

        String reason3 = "Testing removeBin() using bin list size";
        BinController.deleteBin(BinController.binList.get(2).getDbId());
        result1 = BinController.binList.size();
        expected1 = 2;
        if (result1 != (expected1)) { failed ++ ;System.out.println(error_message(reason3, String.valueOf(expected1),
                String.valueOf(result1)));}

        String reason4 = "Testing addCrop()";
        BinController.addBin(binName3, binSize3, binLocation3, hopper3, fan3);
        BinController.addCrop(BinController.binList.get(0).getDbId(), newCrop2db, 20, true, false, false);
        BinController.addCrop(BinController.binList.get(1).getDbId(), newCrop1db, 25, true, false, false);
        BinController.addCrop(BinController.binList.get(2).getDbId(), newCrop1db, 23, true, false, false);
        String result = BinController.binList.get(2).getCurrentCrop().getCropType();
        String expected = newCrop1.getCropType();
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason4, expected, result));}

        String reason5 = "Testing clearBin()";
        BinController.binList.get(2).clearBin();
        Double result2 = BinController.binList.get(2).getCropBushels();
        if (result2 != 0) { failed ++ ;System.out.println(error_message(reason5, null, String.valueOf(result2)));}

        String reason6 = "Testing unload()";
        BinController.binList.get(1).unloadBin(8, true);
        result2 = BinController.binList.get(1).getCropBushels();
        if (result2 != 17.0) { failed ++ ;System.out.println(error_message(reason6, "17.0", String.valueOf(result2)));}

        /*
            to ensure a clean slate after testing, delete all bins added during testing.
         */
        BinController.deleteBin(BinController.binList.get(2).getDbId());
        BinController.deleteBin(BinController.binList.get(1).getDbId());
        BinController.deleteBin(BinController.binList.get(0).getDbId());
        manager.removeClass(newCrop1db);
        manager.removeClass(newCrop2db);


        String[] reasons = {reason1, reason2, reason3, reason4, reason5, reason6};
        System.out.println("*** Bin Control Class Testing  ***\n");
        for (String reason : reasons) {
            count ++;
            System.out.println("Passed " + (count - failed) + " out of total " + (count) + " tests"); // reason  8 isn't tested
        }
        System.out.println("Total Tests: " + count + " Tests. Test Passed: " + (count - failed) + " tests");
        System.out.println("-----------------------------------\n");
    }

}
