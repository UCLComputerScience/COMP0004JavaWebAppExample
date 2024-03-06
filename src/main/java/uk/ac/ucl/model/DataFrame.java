package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class DataFrame {
    Hashtable<String, Column> columnsCollection = new Hashtable<>();

    public void addColumn(String name){
        columnsCollection.put(name, new Column(name));
    }

    public ArrayList<String> getColumnNames() {
        return new ArrayList<>(columnsCollection.keySet());
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


}

