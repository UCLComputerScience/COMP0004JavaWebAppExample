package uk.ac.ucl.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class DataLoader {
    DataFrame patientData = new DataFrame();
    final static String PATIENT_DATA_PATH = "data/patient100.csv";
    List<String> headerNames;

    public void loadPatient(){
        loadFile(PATIENT_DATA_PATH);
    }

    private void loadFile(String pathName){
        try (Reader reader = new FileReader(pathName);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)){

            List<String> headerNames = csvParser.getHeaderNames();
            loadColumns(headerNames);
            processLines(csvParser);

        } catch (IOException e) {
            // Should handle exception here!!
            e.printStackTrace();
        }
    }

    private void loadColumns(List<String> columnNames){
        for (String name:columnNames){
            patientData.addColumn(name);
        }
    }

    private void processLines(CSVParser csvParser){
        for (CSVRecord record : csvParser){
            for (String headerName: headerNames){
                String value = record.get(headerName)
                patientData.addValue(headerName, record.get(headerName));
            }
        }
    }
}


