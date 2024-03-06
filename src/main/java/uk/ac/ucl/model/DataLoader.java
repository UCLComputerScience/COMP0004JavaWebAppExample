package uk.ac.ucl.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.xml.crypto.Data;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public DataFrame patientData = new DataFrame();
    final String PATIENT_DATA_PATH;
    List<String> headerNames = new ArrayList<>();

    public DataLoader(String pathName){
        PATIENT_DATA_PATH = pathName;
    }

    public DataFrame getLoadedData(){
        loadFile(PATIENT_DATA_PATH);
        return patientData;
    }

    private void loadFile(String pathName){
        try {
            Reader reader = new FileReader(pathName);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
            headerNames  = csvParser.getHeaderNames();
            // Load Header
            loadColumns(headerNames);
            // Load Content
            for (CSVRecord lineRecord: csvParser){
                processLines(lineRecord);
            }
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

    private void processLines(CSVRecord lineRecord) {
        for(String columnName: headerNames){
            String value = lineRecord.get(columnName);
            patientData.addValue(columnName, value);
        }
    }
}


