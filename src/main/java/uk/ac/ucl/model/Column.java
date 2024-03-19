package uk.ac.ucl.model;
import java.util.*;

/*
 * Column Class that stores the data in each column
 * */
public class Column {

    private final String name;
    private final ArrayList<String> rows = new ArrayList<>();

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

    /**
     * The method finds the keywords partly matching the value in the column
     * @param keyword Keyword string
     * @return A HashMap of the result {index : matchingValue}
     */
    public HashMap<Integer, String> searchRows(String keyword){
        HashMap<Integer,String> matchingValues = new HashMap<>();
        for (int i = 0; i < rows.size(); i ++){
            if (rows.get(i).contains(keyword)) {
                matchingValues.put(i,rows.get(i));
            }
        }
        return matchingValues;
    }

    /**
     * Find all rows that has the same value
     * @param value value to find
     * @return A List of index of all rows have the same value
     */
    public List<Integer> getValueIndex(String value){
        List<Integer> filteredIndex = new ArrayList<>();
        for(int i = 0; i < rows.size(); i++){
            if(rows.get(i).equals(value)){
               filteredIndex.add(i);
            }
        }
        return filteredIndex;
    }

    /**
     * @return  a list of unique elements in the column
     */
    public List<String> getUniqueValues(){
        Set<String> uniqueValueSet = new HashSet<>(rows);
        return new ArrayList<>(uniqueValueSet);
    }

    /**
     * Sort in ascending or descending order based on the value within a range of members
     * @param filteredPatients the member of patients to sort, ignore the others
     * @param sortMethod Ascending or Descending
     * @return A list of patient index based on the corresponding value
     */
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
