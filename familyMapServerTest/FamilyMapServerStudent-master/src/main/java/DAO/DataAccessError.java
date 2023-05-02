package DAO;

/**
 * if there is a SQL error this object will be thrown
 */
public class DataAccessError extends Throwable {
    /**
     * This will print out a statement saying this was a sql error
     */
    public DataAccessError(String message)
    {
        System.out.println(message);
    }


}
