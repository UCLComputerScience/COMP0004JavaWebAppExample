package uk.ac.ucl.model;

import java.util.List;

public class Model {
    DataFrame newFrame = new DataFrame();
    public DataFrame readFile(String pathName){
        DataLoader newLoader = new DataLoader(pathName);
        return newLoader.getLoadedData();
    }

    public List<String> getPatientNames(){
        return newFrame.getColumnNames();
    }

    public List<String> searchFor(String keyword){
        return newFrame.searchKeyword(keyword);
    }
}

