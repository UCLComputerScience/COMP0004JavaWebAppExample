package uk.ac.ucl.model;

import java.util.*;

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

    public HashMap<Integer, String> searchRows(String keywords){
        HashMap<Integer,String> matchingValues = new HashMap<>();
        for (int i = 0; i < rows.size(); i ++){
            if (rows.get(i).contains(keywords)) {
                matchingValues.put(i,rows.get(i));
            }
        }
        return matchingValues;
    }

    public List<String> getRows(){
        return rows;
    }

    public int getValueIndex(String targetVal){
        int rowSize = rows.size();
        for (int i = 0; i < rowSize; i ++){
            if (rows.get(i).equals(targetVal)){
                return i;
            }
        }
        return -1;
    }

    public List<Integer> getFilteredValuesIndex(String value){
        List<Integer> filteredIndex = new ArrayList();
        for(int i = 0; i < rows.size(); i++){
            if(rows.get(i).equals(value)){
               filteredIndex.add(i);
            }
        }
        return filteredIndex;
    }

    public List<String> getUniqueValues(){
        Set<String> uniqueValueSet = new HashSet<>(rows);
        return new ArrayList<>(uniqueValueSet);
    }

    public List<Integer> sortBy(List<Integer> filteredPatients, String sortMethod){
        List<Integer> indices = new ArrayList<>(filteredPatients);
        indices.sort(Comparator.comparing(rows::get));
        if (sortMethod.equals("Descending")){
            indices = indices.reversed();
        }
        return indices;
    }

    public void removeRow(int id) {
        rows.remove(id);
    }
}
