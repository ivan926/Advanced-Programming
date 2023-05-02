package DAO;

/**
 * if there is a SQL error this object will be thrown
 */
public class DataAccessError extends Throwable {
    int errorCode;
    String message;
    /**
     * This will print out a statement saying this was a sql error
     */
    public DataAccessError(String message)
    {
        System.out.println(message);
    }

    public DataAccessError(int errorCode,String message)
    {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int returnErrorCode()
    {
       return  this.errorCode;
    }

    public String returnMessage()
    {
        return  this.message;
    }





}
