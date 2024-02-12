package TEST;



public class TestAll implements Runnable {

    public static void main(String[] args){ }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        System.out.println("Automatic tests are running successfully.");
        TestField.main();
        TestGrainBin.main();
        TestCrop.main();
        TestChemical.main();
        TestOwner.main();
        TestTask.main();
        TestBinControl.main();
        //TestFieldControl.main();
        //TestUserControl.main();
        //TestTaskControl.main();

    }
}
