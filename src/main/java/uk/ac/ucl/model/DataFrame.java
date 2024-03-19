package uk.ac.ucl.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 *  A DataFrame that holds a collection of columns
 *  Providing various methods to interact with the database
 */
public class DataFrame {
    HashMap<String, Column> columnsCollection = new HashMap<>();
    static final String FIRST_NAME = "FIRST";
    static final String LAST_NAME = "LAST";
    static final int NUMBER_OF_GROUPS = 11;

    public void addColumn(String name){
        columnsCollection.put(name, new Column(name));
    }

    public List<String> getColumnNames(){
        return columnsCollection.keySet().stream().toList();
    }

    public int getRowCount(){
        Iterator<Map.Entry<String, Column>> columns = columnsCollection.entrySet().iterator();
        if(columns.hasNext()){
            return columns.next().getValue().getSize();
        } else {
            // empty dataframe
            return 0;
        }
    }

    public String getValue(String column,int row) {
        Column targetColumn = columnsCollection.get(column);
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

    public void addRow(HashMap<String, String> infoOfPatient) {
        for(String column: getColumnNames()){
            String newValue = infoOfPatient.get(column);
            columnsCollection.get(column).addRowValue(newValue);
        }
    }

    public void removeRow(int id){
        for(String name: columnsCollection.keySet()){
            columnsCollection.get(name).removeRow(id);
        }
    }

    public HashMap<Integer, String> searchKeyword(String column, String keyword){
        return columnsCollection.get(column).searchRows(keyword);
    }

    /**
     * @return a list of patients' full name in sequence based on index position
     */
    public List<String> getPatientNames(){
        Column firstNameColumn = columnsCollection.get(FIRST_NAME);
        Column lastNameColumn = columnsCollection.get(LAST_NAME);
        List<String> fullNames = new ArrayList<>();

        // combine first and last name
        for(int i = 0; i < getRowCount(); i ++){
            fullNames.add(firstNameColumn.getRowValue(i) + " " +
                    lastNameColumn.getRowValue(i));
        }

        return fullNames;
    }

    /**
     * @param patients a list of indices of patient
     * @return the full name of the provided patients
     */
    public HashMap<Integer, String> getPatientNames(List<Integer> patients){
        Column firstNameColumn = columnsCollection.get(FIRST_NAME);
        Column lastNameColumn = columnsCollection.get(LAST_NAME);
        HashMap<Integer, String> fullNames = new HashMap<>();

        // combine first and last name
        patients.forEach(
                i -> fullNames.put(i,
                firstNameColumn.getRowValue(i) + " " + lastNameColumn.getRowValue(i)
        ));

        return fullNames;
    }

    public HashMap<String, String> getPersonalInfo(String id){
        HashMap<String, String> infos = new HashMap<>();

        for (String column: getColumnNames()){
            String value = columnsCollection.get(column).getRowValue(Integer.parseInt(id));
            infos.put(column, value);
        }
        return infos;
    }

    public List<Integer> getFilteredPatients(String column, String value){
        return columnsCollection.get(column).getValueIndex(value);
    }

    public List<Integer> getAllPatientsIndices(){
        List<Integer> indicesList = new ArrayList<>();
        for(int i = 0; i < getRowCount(); i ++){
            indicesList.add(i);
        }
        return indicesList;
    }

    public List<String> getUniqueValueOfColumn(String columnName){
        return columnsCollection.get(columnName).getUniqueValues();
    }

    public List<String> getValueFromColumnIndices(String columnName, List<Integer> indices){
        List<String> values = new ArrayList<>();
        Column targetColumn = columnsCollection.get(columnName);
        for(int i: indices){
            values.add(targetColumn.getRowValue(i));
        }
        return values;
    }

    public List<Integer> getHighestAge(List<Integer> filteredIndices){
        List<Integer> highestAge = new ArrayList<>();
        // set a bottom age limit
        long currentLargestAge = 0;
        for(int index:filteredIndices){
            Long age = calculateAge(index);
            // ignore null age
            if (age != null){
                if(age > currentLargestAge){
                    currentLargestAge = age;
                    highestAge.clear();
                    highestAge.add(index);
                } else if (age == currentLargestAge) {
                   highestAge.add(index);
                }
            }
        }
        return highestAge;
    }

    public List<Integer> getLowestAge(List<Integer> filteredIndices){
        List<Integer> lowestAge = new ArrayList<>();
        // set a top age limit
        long currentLowestAge = 200;

        for(int index:filteredIndices){
            Long age = calculateAge(index);
            // ignore null age
            if (age!= null) {
                if (age < currentLowestAge) {
                    currentLowestAge = age;
                    lowestAge.clear();
                    lowestAge.add(index);
                } else if (age == currentLowestAge) {
                    lowestAge.add(index);
                }
            }
        }
        return lowestAge;
    }

    private Long calculateAge(int index) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String birthDateString = columnsCollection.get("BIRTHDATE").getRowValue(index);
        if (!birthDateString.isEmpty()) {
            LocalDate birthDate = LocalDate.parse(birthDateString, formatter);

            String deathDateString = columnsCollection.get("DEATHDATE").getRowValue(index);
            LocalDate currentDate;
            if (deathDateString.isEmpty()) {
                currentDate = LocalDate.now();
            } else {
                // use current if no death date
                currentDate = LocalDate.parse(deathDateString, formatter);
            }

            return ChronoUnit.YEARS.between(birthDate, currentDate);
        } else {
            // if data not exist
            return null;
        }
    }

    public List<Integer> getSortedColumnBy(List<Integer> filteredPatients, String columnName, String sortMethod){
        if (!columnName.equals("AGE")) {
            return sortByColumn(filteredPatients, columnName, sortMethod);
        } else {
            return sortByAge(filteredPatients, sortMethod);
        }
    }

    private List<Integer> sortByColumn(List<Integer> filteredPatients, String columnName, String sortMethod) {
        return columnsCollection.get(columnName).sortBy(filteredPatients, sortMethod);
    }

    private List<Integer> sortByAge(List<Integer> filteredPatients, String sortMethod){
        List<Integer> orderOfPatients = new ArrayList<>(filteredPatients);
        // map patient to the age
        HashMap<Integer, Long> patientsToAge = getPatientsToAgeMap(filteredPatients);
        // Sort patients
        orderOfPatients.sort(Comparator.comparingLong(patient -> {
            Long age = patientsToAge.get(filteredPatients.indexOf(patient));
            // append null at the end
            return age != null ? age : Long.MAX_VALUE;
        }));

        if (sortMethod.equals("Descending")){
            orderOfPatients = orderOfPatients.reversed();
        }
        return orderOfPatients;
    }

    private HashMap<Integer, Long> getPatientsToAgeMap(List<Integer> filteredPatients) {
        HashMap<Integer, Long> patientsToAge = new HashMap<>();
        for(int patient: filteredPatients){
            Long age = calculateAge(patient);
            patientsToAge.put(patient, age);
        }
        return patientsToAge;
    }

    public List<String> getPatientsInJsonFormat() {
        List<String> patientsInJsonFormat = new ArrayList<>();
        for(int i = 0; i < getRowCount(); i ++){
            patientsInJsonFormat.add(convertNewPatientToString(i));
        }
        return patientsInJsonFormat;
    }

    private String convertNewPatientToString(int index){
        // prefix
        StringBuilder newPatient = new StringBuilder("\t\t{\n");
        List<String> columns = getColumnNames();

        // patient info
        for (String column: columns){
            String columnValue = getValue(column, index);
            if (!columns.getLast().equals(column)){
                newPatient.append("""
                                    "%s":"%s",
                            """.formatted(column, columnValue));
            } else {
                // handle last column
                newPatient.append("""
                                    "%s":"%s"
                            """.formatted(column, columnValue));
            }
        }

        // suffix
        if (index != getRowCount() - 1) {
            newPatient.append("\t\t},\n");
        } else {
            // handle last patient
            newPatient.append("\t\t}\n");
        }
        return newPatient.toString();
    }

    public HashMap<String, Integer> getAgeDistribution() {
        HashMap<String, Integer> ageDistribution = new HashMap<>();
        List<String> ageGroups = getAgeGroups();
        List<Integer> ageDistributionInList = generateInitedList(NUMBER_OF_GROUPS);

        for(int i = 0; i < getRowCount(); i ++){
            Long ageLong = calculateAge(i);
            if (ageLong == null) {
                break;
            } else {
                int index = ageLong.intValue() / 10;
                if (index < 10) {
                    ageDistributionInList.set(index, ageDistributionInList.get(index) + 1);
                } else {
                    ageDistributionInList.set(10, ageDistributionInList.get(10) + 1);
                }
            }
        }

        for(int i = 0; i < NUMBER_OF_GROUPS; i ++){
            // map the age group string to the calculated age distribution
            ageDistribution.put(ageGroups.get(i), ageDistributionInList.get(i));
        }

        return ageDistribution;
    }



    public List<String> getAgeGroups(){
        List<String> ageGroups = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_GROUPS - 1; i += 1){
            String newSection = (i*10) + "-" + (i*10+10);
            ageGroups.add(newSection);
        }
        // last age group
        ageGroups.add(">100");

        return ageGroups;
    }

    private List<Integer> generateInitedList(int size) {
        // generate a list with elements zero
        List<Integer> ageDistributionInList = new ArrayList<>();
        for (int i = 0; i < size; i ++){
            ageDistributionInList.add(0);
        }
        return ageDistributionInList;
    }

}

