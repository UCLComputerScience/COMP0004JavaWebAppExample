<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<head>
  <title>Patient Information</title>
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
      List<String> displaySequence = (List<String>) request.getAttribute("displaySequence");
      for(String column: displaySequence){
        String value = patientInfo.get(column);
        if(!value.isEmpty()){
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
      <%for(String columnName: displaySequence){%>
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