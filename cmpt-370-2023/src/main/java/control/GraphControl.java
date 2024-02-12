package control;

import javafx.collections.ObservableList;
import org.InitialFarm.dataManager;
import org.entities.Field;
import org.entities.Year;

import java.util.ArrayList;

public class GraphControl {


    ObservableList<Field> allFields;
    dataManager dataManager = new dataManager();

    public GraphControl() {

        allFields = dataManager.initializeFieldsFromDB();
        System.out.println("graph control test");
    }

    public ObservableList<Field> getAllFields() {
        allFields = dataManager.initializeFieldsFromDB();
        return allFields;
    }
    public ArrayList<Year> getYears(Field field) {
        ArrayList<Year> years = new ArrayList<>();
        for (Year year : field.getYears()) {
            years.add(year);
        }
        return years;
    }
}
