package model;

import java.util.Vector;

public class ViewModel {

    public ViewModel() {
    }

    public Vector<String> getUserColumnNames() {

        Vector<String> columnNames = new Vector<>();

        columnNames.add("Name");
        columnNames.add("Email");
        columnNames.add("Password");
        columnNames.add("Id");

        return columnNames;
    }
}
