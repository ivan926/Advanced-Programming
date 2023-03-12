package Service;

import java.util.UUID;

public class UniqueID {
    private static UUID ID = null;
    private static UniqueID ID_Database;
    private String stringID;

    private UniqueID(){}


    public static UniqueID getUniqueDatabaseInstance()
    {   String uniqueId;
        if(ID_Database == null)
        {

            ID_Database = new UniqueID();


        }

        return ID_Database;

    }

    public String getUniqueID()
    {
       return UUID.randomUUID().toString();

    }


}
