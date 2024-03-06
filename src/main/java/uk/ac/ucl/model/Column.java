package uk.ac.ucl.model;

import java.util.ArrayList;

public class Column {
    /*
    * Column Class that stores the data in each column
    * */
    private String name;
    private ArrayList<String> rows = new ArrayList<>();

    public Column(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public int getSize(){
        return rows.size();
    }
    public String getRowValue(int rowNumber){
        return rows.get(rowNumber);
    }
    public void setRowValue(int rowNumber, String newValue){
        rows.set(rowNumber, newValue);
    }
    public void addRowValue(String newValue){
        rows.add(newValue);
    }
    public ArrayList<String> searchRows(String keywords){
        ArrayList<String> matchingValues = new ArrayList<>();
        for (String value: rows){
            if (value.contains(keywords)) {matchingValues.add(value);}
        }
        return matchingValues;
    }
}
