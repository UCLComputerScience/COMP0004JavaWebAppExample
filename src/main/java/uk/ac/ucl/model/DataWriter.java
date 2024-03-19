package uk.ac.ucl.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class DataWriter {
    private static final String WRITE_DESTINATION = "data/tempData.csv";
    public static void write(DataFrame newFrame) {
        List<String> headerRow = newFrame.getColumnNames();
        System.out.println("new value " + newFrame.getValue("FIRST", 0));
        try {
            Writer writer = new FileWriter(WRITE_DESTINATION);
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headerRow.toArray(new String[0])));

            for (int i = 0; i < newFrame.getRowCount(); i++){
                List<String> row = new ArrayList<>();
                for (String column : headerRow){
                    row.add(newFrame.getValue(column, i));
                }
                csvPrinter.printRecord(row);
            }

            csvPrinter.flush();
            csvPrinter.close();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
