package TEST;

import org.InitialFarm.Crop;
import org.InitialFarm.GrainBin;

public class TestGrainBin {

    static String error_message(String test, String expected, String result) {
        return "Test Error: " + test + ". Expected: " + expected+
                ". Returned: " + result + " instead. :(";
    }

    public static void main(){

        int count = 0, failed = 0;

        /* For testing the Grain Bin class */
        String binName = "GrainBin1";
        String binLocation = "location1";
        int binSize = 100;
        boolean hopper = true;
        boolean fan = false;

        String reason1 = "Constructor + getBinName()";
        GrainBin GrainBin1 = new GrainBin(null, binName, binLocation, binSize, hopper, fan);
        String result = GrainBin1.getBinName();
        String expected = "GrainBin1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason1, expected, result));}

        String reason2 = "Testing getBinName()";
        result = GrainBin1.getBinName();
        expected = binName;
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason2, expected, result));}

        String reason3 = "Testing getBinLocation()";
        result = GrainBin1.getBinLocation();
        expected = binLocation;
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason3, expected, result));}

        String reason4 = "Testing getSize()";
        double result2 = GrainBin1.getBinSize();
        if (result2 != (binSize)) { failed ++ ;System.out.println(error_message(reason4, String.valueOf(binSize), String.valueOf(result2)));}

        String reason5 = "Testing isHopper()";
        boolean result3 = GrainBin1.isHopper();
        if (!result3){ failed++; System.out.println(error_message(reason5, "true", "false")); }

        String reason6 = "Testing isFan()";
        result3 = GrainBin1.isFan();
        if (result3){ failed++; System.out.println(error_message(reason6, "false", "true")); }

        Crop cropResult;
        String reason7 = "Testing currentCrop() with no Crop";
        cropResult = GrainBin1.getCurrentCrop();
        if (cropResult != null){ failed++; System.out.println(error_message(reason7, null, cropResult.getCropType())); }

        String reason8 = "";
        try {
            GrainBin1.unloadBin(12, true);
        } catch(Exception e) {
            reason8 = " Testing unloadBin() with empty bin";
        }



        Crop testCrop = new Crop(null,"cropType", "cropVariety", 3);
        String reason9 = "Testing currentCrop() with Crop";
        GrainBin1.addCrop(testCrop, 12, true, true, true );
        cropResult = GrainBin1.getCurrentCrop();
        if (!cropResult.equals(testCrop)){ failed++; System.out.println(error_message(reason9, testCrop.getCropType(), cropResult.getCropType())); }

        String reason10 = " Testing unloadBin() with a bin with a crop in it";
        GrainBin1.unloadBin(4, true);
        result2 = GrainBin1.getCropBushels();
        if (result2 != 0){ failed++; System.out.println(error_message(reason10, String.valueOf(result2), String.valueOf(GrainBin1.getCropBushels()))); }



        String[] reasons = {reason1, reason2, reason3, reason4, reason5, reason6, reason7,
                reason8, reason9, reason10};
        System.out.println("*** GrainBin Class Testing  ***\n");
        for (String reason : reasons) {
            count ++;
            System.out.println("Passed " + (count - failed) + " out of total " + (count) + " tests"); // reason  8 isn't tested
        }
        System.out.println("Total Tests: " + count + " Tests. Test Passed: " + (count - failed) + " tests");
        System.out.println("-----------------------------------\n");


    }
}
