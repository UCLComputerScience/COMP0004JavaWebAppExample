package uk.ac.ucl.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

// This class gives access to the model to any other class that needs it.
// Calling the static method getModel (i.e., ModelFactory.getModel()) returns
// an initialised Model object. This version limits the program to one model object,
// which is returned whenever getModel is called.
// The factory also illustrates how a data file can be passed to the model.

public class ModelFactory
{
  private static Model model;
  private static final String TARGET_FILE_PATH = "data/tempData.csv";

  public static Model getModel() throws IOException
  {
    if (model == null) {
      model = new Model();
      // Note where the data file is stored in the data directory,
      // and the pathname to locate it.
      // The data should be read the file once, not every time the model is accessed!
      model.readFile(TARGET_FILE_PATH);
      System.out.println("read a new model");
    }
    return model;
  }

  public static void initFileBase(String file) throws IOException {
    if (file == null) {
      return;
    }
    String path = getFilePath(file);
    copyFile(path);
    // Read the database after choose the file
    model = new Model();
    model.readFile(TARGET_FILE_PATH);
  }

  private static String getFilePath(String file){
    String path;
    switch (file){
      case "100" -> path = "data/patients100.csv";
      case "1000" -> path = "data/patients1000.csv";
      case "10000" -> path = "data/patients10000.csv";
      case "100000" -> path = "data/patients100000.csv";
      default -> path = "";
    }
    return path;
  }

  private static void copyFile(String filePath){
    try {
      Path sourceFilePath = Paths.get(filePath);
      Path newFilePath = Paths.get(TARGET_FILE_PATH);

      Files.copy(sourceFilePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e){
      e.printStackTrace();
    }
  }

  public static void deletePatientAndUpdateModel(int id){
    model.deletePatient(id);
    updateModel();
  }

  public static void editPatientAndUpdateModel(int id, String column, String newValue){
    model.editPatient(id, column, newValue);
    updateModel();
  }

  public static void addPatientAndUpdateModel(HashMap<String, String> infoOfPatient){
    model.addPatient(infoOfPatient);
    updateModel();
  }

  private static void updateModel(){
    // re-read file to update the model
    model.readFile(TARGET_FILE_PATH);
  }

  public static void saveFile(String newFilePath) {
    model.saveAsJson(newFilePath);
  }
}
