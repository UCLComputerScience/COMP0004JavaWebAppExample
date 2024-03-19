<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: aiden
  Date: 18/03/2024
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Stats</title>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
</head>
<body>
<canvas id="statsChart" style="width:100%"></canvas>
<script>
  let xValues = [];
  let yValues = [];
  <%
    HashMap<String, Integer> distributionByAge = (HashMap<String, Integer>) request.getAttribute("distributionByAge");
    for(String ageSection: (List<String>) request.getAttribute("ageGroups")){%>
    xValues.push('<%=ageSection%>');
    yValues.push('<%=distributionByAge.get(ageSection)%>');
  <%
    }
  %>

  new Chart("statsChart", {
    type: "bar",
    data: {
      labels: xValues,
      datasets: [{
        backgroundColor: "blue",
        data: yValues
      }]
    },
    options: {
      legend: {display: false},
      title: {
        display: true,
        text: "Distribution by Age"
      }
    }
  });
</script>
<form action="menu.html">
<button type="submit">Go Back</button>
</form>
</body>
</html>
