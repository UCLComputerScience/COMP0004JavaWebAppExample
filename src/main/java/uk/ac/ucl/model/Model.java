package uk.ac.ucl.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Model {
    static final String[] OPTIONAL_COLUMNS = {"BIRTHPLACE","CITY","ETHNICITY","GENDER","RACE","STATE","MARITAL"};
    static final String JSON_PREFIX_BLOCK =
            """
            {
                "patients":[
            """;
    static final String JSON_SUFFIX_BLOCK =
            """
                ]
            }
            """;
    private DataFrame newFrame = new DataFrame();
    public void readFile(String pathName){
        DataLoader newLoader = new DataLoader(pathName);
        newFrame = newLoader.getLoadedData();
    }

    public List<String> getPatientList() {
        return newFrame.getPatientNames();
    }

    public HashMap<Integer, String> getPatientList(List<Integer> patients){
        return newFrame.getPatientNames(patients);
    }

    public HashMap<Integer, String> searchFor(String option, String keyword){
        return newFrame.searchKeyword(option, keyword);
    }

    public HashMap<String, String> getPatientInfo(String id) {
        return newFrame.getPersonalInfo(id);
    }

    public List<String> getColumnSequence(){
        return newFrame.getInfoSequence();
    }

    public List<Integer> getFilteredPatient(String column, String value) {
        if (column.isEmpty() || value.isEmpty()){
            return newFrame.getAllPatientsIndices();
        } else {
            return newFrame.getFilteredPatients(column, value);
        }
    }

    public HashMap<Integer,String> getValueFromColumnIndices(String columnName, List<Integer> indices){
        HashMap<Integer, String> valueAndIndexCollection = new HashMap<>();
        List<String> values =  newFrame.getValueFromColumnIndices(columnName, indices);
        for (int i = 0; i < indices.size(); i ++){
            valueAndIndexCollection.put(indices.get(i), values.get(i));
        }
        return valueAndIndexCollection;
    }

    public HashMap<String, List<String>> getAvailOptionCollection(){
        HashMap<String, List<String>> availOptionCollection = new HashMap<>();
        for (String columnName: OPTIONAL_COLUMNS){
            List<String> correspondingUniqueValues = newFrame.getUniqueValueOfColumn(columnName);
            availOptionCollection.put(columnName,correspondingUniqueValues);
        }
        return availOptionCollection;
    }

    public List<Integer> findHighestAge(List<Integer> filteredPatients){
       return newFrame.getHighestAge(filteredPatients);
    }

    public List<Integer> findLowestAge(List<Integer> filteredPatients){
        return newFrame.getLowestAge(filteredPatients);
    }

    public List<Integer> sortBy(List<Integer> filteredPatients, String sortMethod, String sortColumn){
        return newFrame.getSortedColumnBy(filteredPatients, sortColumn, sortMethod);
    }

    public void deletePatient(int id) {
       newFrame.removeRow(id);
       writeNewFrame();
    }

    public void editPatient(int id, String column, String newValue) {
        newFrame.putValue(column, id, newValue);
        writeNewFrame();
    }

    private void writeNewFrame(){
        DataWriter.write(newFrame);
    }

    public void addPatient(HashMap<String, String> infoOfPatient) {
        newFrame.addRow(infoOfPatient);
        writeNewFrame();
    }

    public void saveAsJson(String newFilePath) {
        List<String> patients = newFrame.getPatientsInJsonFormat();
        try(FileWriter fileWriter = new FileWriter(newFilePath)){
            fileWriter.write(JSON_PREFIX_BLOCK);
            for(String patient: patients){
                fileWriter.write(patient);
            }
            fileWriter.write(JSON_SUFFIX_BLOCK);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> getAgeDistribution() {
        return newFrame.getAgeDistribution();
    }

    public List<String> getAgeGroups() {
        return newFrame.getAgeGroups();
    }


}

