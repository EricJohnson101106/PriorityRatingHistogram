<%@ page import="PriorityRatingHistogram.EJSQLConnectionHandler" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: Flidro
  Date: 11/4/2019
  Time: 5:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Table</title>
</head>
<body>
<% EJSQLConnectionHandler connHandler = new EJSQLConnectionHandler();
    Connection conn = connHandler.connection;
    PrintWriter outWriter = response.getWriter();

    try (PreparedStatement bookStatement = conn.prepareStatement("SELECT * " +
            "FROM `data`")) {
        ResultSet rs = bookStatement.executeQuery();
        //format the table header
        outWriter.print(
                "<table>" +
                        "<tr>" +
                        "<th>Transaction ID</th>" +
                        "<th>Awarded To</th>" +
                        "<th>Awarded By</th>" +
                        "<th>Award Message</th>" +
                        "<th>EP Before</th>" +
                        "<th>EP After</th>" +
                        "<th>GP Before</th>" +
                        "<th>GP After</th>" +
                        "<th>Item Awarded</th>" +
                        "</tr>"
        );

        while (rs.next()) {

            outWriter.print(

                    "<tr>" +
                            "<td>" + rs.getString(1) + "</td>" +    //Transaction ID
                            "<td>" + rs.getString(2) + "</td>" +    //Awarded To
                            "<td>" + rs.getString(3) + "</td>" +    //Awarded By
                            "<td>" + rs.getString(4) + "</td>" +    //Award Message
                            "<td> " + rs.getString(5) + "</td>" +  //EP Before
                            "<td> " + rs.getString(6) + "</td>" +  //EP After
                            "<td> " + rs.getString(7) + "</td>" +  //GP Before
                            "<td> " + rs.getString(8) + "</td>" +  //GP After
                            "<td> " + rs.getString(9) + "</td>" +  //Item Awarded
                            "</tr>"
            );

        } //end while
        outWriter.print("</table>");

    } catch (SQLException exc) {
        exc.printStackTrace();
    }

    RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/table.jsp");
    rd.include(request, response);
    %>
</body>
</html>
