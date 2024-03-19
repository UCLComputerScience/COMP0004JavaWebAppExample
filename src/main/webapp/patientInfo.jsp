<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<head>
  <title>Patient Information</title>
  <% final List<String> DISPLAY_SEQUENCE = Arrays.asList("PREFIX","FIRST", "LAST", "SUFFIX","MAIDEN","GENDER", "ID","BIRTHDATE","DEATHDATE","BIRTHPLACE", "SSN", "DRIVERS", "PASSPORT", "MARITAL", "RACE", "ETHNICITY", "ADDRESS", "CITY", "STATE", "ZIP");%>
</head>
<style>
  body{
    font-family: "Arial", sans-serif;
  }

  h2, h1{
    text-align: center;
    background-color: aliceblue;
  }
</style>
<body>
  <jsp:include page="/header.jsp"/>
  <h2>Patient Info</h2>
  <ul>
    <%
      HashMap<String, String> patientInfo = (HashMap<String, String>) request.getAttribute("patientInfo");
      for(String column: DISPLAY_SEQUENCE){
        String value = patientInfo.get(column);
        if(!value.isEmpty()){
          value = column + ": " + value;
    %>
    <li><%=value%></li>
    <%
        }
      }
    %>
  </ul>
  <form action="/editpatient.html">

    <label for = "columnToEdit"> Choose the column to edit</label>
    <select name="columnToEdit" id="columnToEdit">
      <%for(String columnName: DISPLAY_SEQUENCE){%>
        <option value = "<%=columnName%>"><%=columnName%></option>
      <%
        }
      %>
    </select>
    <input type="text" id="newValue" name="newValue" placeholder="new value">
    <button type="submit" name="editId" value = "<%=request.getAttribute("id")%>">Edit</button>
  </form>

  <form action="/deletepatient.html">
    <button type="submit" name="deleteId" value = "<%=request.getAttribute("id")%>">Delete Patient</button>
  </form><br>
</body>
</html>