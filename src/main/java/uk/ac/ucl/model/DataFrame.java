package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class DataFrame {
    Hashtable<String, Column> columnsCollection = new Hashtable<>();
    static final String FIRST_NAME = "FIRST";

    public void addColumn(String name){
        columnsCollection.put(name, new Column(name));
    }

    public List<String> getPatientNames(){
        Column nameColumn = columnsCollection.get(FIRST_NAME);
        return nameColumn.getRows();
    }

    public int getRowCount(){
        Enumeration<Column> columns = columnsCollection.elements();
        if(columns.hasMoreElements()){
            return columns.nextElement().getSize();
        } else {
            // empty dataframe
            return 0;
        }
    }

    public String getValue(String columnName,int row) {
        Column targetColumn = columnsCollection.get(columnName);
        return targetColumn.getRowValue(row);
    }

    public void putValue(String columnName, int row, String value) {
        Column targetColumn = columnsCollection.get(columnName);
        targetColumn.setRowValue(row, value);
    }

    public void addValue(String columnName, String value){
        Column targetColumn = columnsCollection.get(columnName);
        targetColumn.addRowValue(value);
    }

    public ArrayList<String> searchKeyword(String keyword){
        ArrayList<String> matchingValues = new ArrayList<>();
        for(Column column: columnsCollection.values()){
            matchingValues.addAll(column.searchRows(keyword));
        }

        if (matchingValues.isEmpty()){
            matchingValues.add("404 Keyword Not Found");
        }

        return matchingValues;
    }


}

