<%@ page import="PriorityRatingHistogram.EJSQLConnectionHandler" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: Flidro
  Date: 10/19/2019
  Time: 3:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Priority Rating Histogram</title>
</head>
<body>
<h1>Test</h1>
<p>To invoke the java servlet click <a href="TableServlet">here</a></p>
<form action="TableServlet" method="get">
    <input type="submit" value="Go To Table">
</form>


</body>
</html>
