package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Handles the connection
 */




public class Database {
    private Connection conn = null;
    private static Database instance = null;


    /**
     * This will open the connection to the database
     */

    // Whenever we want to make a change to our database we will have to open a connection and use
    // Statements created by that connection to initiate transactions
    private Database() throws DataAccessError {
        try {
            // The Structure for this Connection is driver:language:path
            // The path assumes you start in the root of your project unless given a full file path
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessError("Unable to open connection to database");
        }

    }

    public static Database getInstance() throws DataAccessError {
        if (instance == null) {
            instance = new Database();
        }
            return instance;
    }

    public Connection getConnection() {
        return conn;
    }

    // When we are done manipulating the database it is important to close the connection. This will
    // end the transaction and allow us to either commit our changes to the database (if true is passed in)
    // or rollback any changes that were made before we encountered a potential error (if false is passed in).

    // IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
    // DATABASE TO LOCK. YOUR CODE MUST ALWAYS CLOSE THE DATABASE NO MATTER WHAT ERRORS
    // OR PROBLEMS ARE ENCOUNTERED
    /**
     * This will close the connection to the database
     */
    public void closeConnection(boolean commit) {
        try {
            if (commit) {
                // This will commit the changes to the database
                conn.commit();
            } else {
                // If we find out something went wrong, pass a false into closeConnection and this
                // will rollback any changes we made during this connection
                conn.rollback();
            }
            conn.close();
            conn = null;
        } catch (SQLException e) {
            // If you get here there are probably issues with your code and/or a connection is being left open
            e.printStackTrace();
        }
    }


}

