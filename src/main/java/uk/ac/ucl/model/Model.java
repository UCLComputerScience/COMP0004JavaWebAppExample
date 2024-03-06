package uk.ac.ucl.model;

import java.util.List;

public class Model {
    public DataFrame newFrame = new DataFrame();
    public void readFile(String pathName){
        DataLoader newLoader = new DataLoader(pathName);
        newFrame = newLoader.getLoadedData();
    }

    public List<String> getPatientNames(){
        return newFrame.getPatientNames();
    }

    public List<String> searchFor(String keyword){
        return newFrame.searchKeyword(keyword);
    }
}

