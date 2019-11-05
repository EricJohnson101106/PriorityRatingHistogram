package PriorityRatingHistogram;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "TableServlet")
public class TableServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EJSQLConnectionHandler connHandler = new EJSQLConnectionHandler();
        Connection conn = connHandler.connection;
        PrintWriter out = response.getWriter();

        try {
            PreparedStatement tableStatement = null;
            tableStatement = conn.prepareStatement("SELECT * FROM `data`");
            ResultSet rs = tableStatement.executeQuery();
            //format the table header
            out.print(
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

                out.print(

                        "<tr>" +
                                "<td> " + rs.getString(1) + "</td>" +   //Transaction ID
                                "<td> " + rs.getString(2) + "</td>" +   //Awarded To
                                "<td> " + rs.getString(3) + "</td>" +   //Awarded By
                                "<td> " + rs.getString(4) + "</td>" +   //Award Message
                                "<td> " + rs.getString(5) + "</td>" +   //EP Before
                                "<td> " + rs.getString(6) + "</td>" +   //EP After
                                "<td> " + rs.getString(7) + "</td>" +   //GP Before
                                "<td> " + rs.getString(8) + "</td>" +   //GP After
                                "<td> " + rs.getString(9) + "</td>" +   //Item Awarded
                                "</tr>"
                );

            } //end while
            out.print("</table>");

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/table.jsp");
        rd.include(request, response);
    }
}

