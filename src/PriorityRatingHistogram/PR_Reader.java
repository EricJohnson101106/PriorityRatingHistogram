package PriorityRatingHistogram;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PR_Reader {
    //Read in the LUA File and parse each line to input into the database
    //Needs to filter the desired lines
    //Needs to check that line against a DB entry
    //TODO When given an updated file, needs to check against current records to prevent overwriting or duplication of information. Currently only writes new information to the table
    //Needs to input line into DB if not in DB already

    public static void main(String[] args) throws Exception, SQLException {

        File sourceFile = new File("C:\\Users\\Flidro\\IdeaProjects\\Priority Rating Histogram\\src\\Priority_Rating_Histogram\\CEPGP.lua");

        //Connect to the MySQL database
        EJSQLConnectionHandler connectionHandler = new EJSQLConnectionHandler();
        Connection conn = connectionHandler.connection;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement("INSERT INTO `data`(`target_name`,`awarded_by`,`award_message`,`ep_before`,`ep_after`,`gp_before`,`gp_after`,`item_awarded`) VALUES (?,?,?,?,?,?,?,?)");
            preparedStatement.setString(8, "");
            String startToken = "TRAFFIC = {"; //start of the filter
            String endToken = "CEPGP_raid_logs = {"; //end of the filter
            boolean trimToken = false;
            boolean writeToken = false;
            int cellCounter = 1; //Used for preparedStatement paramaterIndex (1-8). Never 0

            Scanner scan = new Scanner(sourceFile);
            //check each line for the tokens. When start token is found, begin adding substrings to line until the endToken is found
            //Boolean output is used to know to check for the correct token. Starts at false. When false AND startToken is found, change output
            //to true and begin adding substrings to String line.
            //When output is true AND endToken is found, set output to false and close String line

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (!trimToken && line.indexOf(startToken) > -1) {
                    trimToken = true;
                    line = line.substring(line.indexOf(startToken) + startToken.length()); //Removes open brace at the start

                } else if (trimToken && line.indexOf(endToken) > -1) {
                    trimToken = false;
                    System.out.println(line.substring(0, line.indexOf(endToken))); //Removes close brace at the end
                }
                if (trimToken) {
                    // If start of new table, go to next line, set an writeToken to true
                    // if end of table, set writeToken to false
                    // if writeToken is true, begin writing to database
                    // While doing this, need to only capture whatever is within the quotes and discard the rest.
                    // Use substring(indexOf, lastIndexOf) with the first and last quote of the message
                    if (line.indexOf("{") > -1) {
                        //If the line starts with { set writeToken to true and continue.
                        //Don't want the { to interfere with writing to the DB but still want to use it to distinguish
                        //between the LUA tables
                        writeToken = true;
                    } else if (line.indexOf("}") > -1) {
                        //If the line starts wtih } set writeToken to false and then continue. **Denotes end of LUA table
                        //Don't want to write } to the database but still want to use it to distinguish between
                        //the LUA tables
                        writeToken = false;
                        cellCounter = 1; //revert the cellCounter back to 0 when the LUA table closes. Used for writing to DB
                        //TODO double sends the final preparedStatement. Likely an issue with the endToken since the TRAFFIC = { } table has that closing brace as well
                        preparedStatement.execute(); //execute with whatever is loaded into the PreparedStatement before looping back
                    } else if (writeToken) {
                        //If writeToken is true, write the line to the Database.
                        // Using the preparedStatement initialized above.
                        // cellCounter determines which VALUE the information will go into, 1-8.
                        // VALUE 8 is already initialized to an empty string since not all LUA tables have 8 rows (only 7)
                        preparedStatement.setString(cellCounter, line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""))); //Pull out the substring between the first and last quotes. Probably only works in this situation and likely not the best solution
                        cellCounter++; //after writing the line to the DB, increment by one
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}

