package TEST;

import org.InitialFarm.Crop;

public class TestCrop {

    static String error_message(String test, String expected, String result) {
        return "Test Error: " + test + ". Expected: " + expected+
                ". Returned: " + result + " instead. :(";
    }

    public static void main (){

        int count = 0, failed = 0;
        String cropType = "type1";
        String cropVariety = "variety1";
        float bushelWeight = 3;

        String reason1 = "Constructor + getCropType()";
        Crop Crop1 = new Crop(null, cropType, cropVariety, bushelWeight);
        String result = Crop1.getCropType();
        String expected = "type1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason1, expected, result));}

        String reason2 = "Testing getCropType()";
        result = Crop1.getCropType();
        expected = "type1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason2, expected, result));}

        String reason3 = "Testing getCropVariety()";
        result = Crop1.getCropVariety();
        expected = "variety1";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason3, expected, result));}

        String reason4 = "Testing getBushelWeight()";
        double result2 = Crop1.getBushelWeight();
        float expected2 = 3;
        if (result2 != (expected2)) { failed ++ ;System.out.println(error_message(reason4, String.valueOf(expected2), String.valueOf(result2)));}

        String cropType2 = "type2";
        String cropVariety2 = "variety2";
        float bushelWeight2 = 5;

        String reason5 = "Testing setCropType()";
        Crop1.setCropType(cropType2);
        result = Crop1.getCropType();
        expected = "type2";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason5, expected, result));}

        String reason6 = "Testing setCropVariety()";
        Crop1.setCropVariety(cropVariety2);
        result = Crop1.getCropVariety();
        expected = "variety2";
        if (!result.equals(expected)) { failed ++ ;System.out.println(error_message(reason6, expected, result));}

        String reason7 = "Testing setBushelWeight()";
        Crop1.setBushelWeight(bushelWeight2);
        result2 = Crop1.getBushelWeight();
        expected2 = 5;
        if (result2 != (expected2)) { failed ++ ;System.out.println(error_message(reason7, String.valueOf(expected2), String.valueOf(result2)));}


        String[] reasons = {reason1, reason2, reason3, reason4, reason5, reason6, reason7};
        System.out.println("*** Crop Class Testing  ***\n");
        for (String reason : reasons) {
            count ++;
            System.out.println("Passed " + (count - failed) + " out of total " + (count) + " tests");
        }
        System.out.println("Total Tests: " + count + " Tests. Test Passed: " + (count - failed) + " tests");
        System.out.println("-----------------------------------\n");








    }
}
