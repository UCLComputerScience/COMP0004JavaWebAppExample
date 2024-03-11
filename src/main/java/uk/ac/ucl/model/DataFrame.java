package uk.ac.ucl.model;

import java.util.*;

public class DataFrame {
    Hashtable<String, Column> columnsCollection = new Hashtable<>();
    static final String FIRST_NAME = "FIRST";
    static final List<String> INFO_SEQUENCE = Arrays.asList("PREFIX","FIRST", "LAST", "SUFFIX","MAIDEN","GENDER", "ID","BIRTHDATE","DEATHDATE","BIRTHPLACE", "SSN", "DRIVERS", "PASSPORT", "MARITAL", "RACE", "ETHNICITY", "ADDRESS", "CITY", "STATE", "ZIP");

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

    public List<String> getPersonalInfo(String patientName){
        List<String> infos = new ArrayList<>();
        int index = columnsCollection.get(FIRST_NAME).getValueIndex(patientName);
        System.out.println(patientName);
        System.out.println(columnsCollection.get(FIRST_NAME).getRows());

        for (String info: INFO_SEQUENCE){
            String infoValue = columnsCollection.get(info).getRowValue(index);
            if(!infoValue.isEmpty()) {
                // if the info exist, reformat the value
                infoValue = info + ": " + infoValue;
            }
            infos.add(infoValue);
        }
        return infos;
    }

}

