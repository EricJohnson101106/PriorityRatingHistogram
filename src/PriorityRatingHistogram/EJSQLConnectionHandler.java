package PriorityRatingHistogram;

import java.sql.*;

public class EJSQLConnectionHandler {
    //Constructor for a connection with the EPGP Database
    public Connection connection;

    public EJSQLConnectionHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EPGP",
                    "EPGP",
                    "EPGPpassword");

        } catch (Exception exc) {
            System.out.println("EJSQLConnectionHandler: " + exc.getMessage());
            exc.printStackTrace();
        }
    }
}
