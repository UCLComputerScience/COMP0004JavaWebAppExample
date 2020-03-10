package uk.ac.ucl.model;

import java.io.File;
import java.util.List;

public class Model
{
  // The example code in this class should be replaced by your Model class code.
  // The data should be stored in a DataFrame.
  // private DataFrame frame = ...

  public List<String> getPatientNames()
  {
    return List.of("Name1", "Name2", "Name3"); // This is just dummy data
  }

  public void readFile(File file)
  {
    // Read a patient .csv data file and create the DataFrame.
  }

  // This also returns dummy data. The real version should use the keyword parameter to search
  // the patient data and return a list of matching items.
  public List<String> searchFor(String keyword)
  {
    return List.of("Search keyword is: "+ keyword, "result1", "result2", "result3");
  }
}
