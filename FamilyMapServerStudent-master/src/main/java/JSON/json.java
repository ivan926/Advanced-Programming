package JSON;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class json {
    static json fillData;
    LocationData locationArray;
    mnames motherNames;
    fnames fatherNames;
    snames spouseNames;
    Gson gson;
    Reader reader = null;


    public LocationData getLocationArray() {
        return locationArray;
    }

    public mnames getMotherNames() {
        return motherNames;
    }

    public fnames getFatherNames() {
        return fatherNames;
    }

    public snames getSurNames() {
        return spouseNames;
    }

    private json()
    {
        gson = new Gson();
        locationArray = new LocationData();
        fatherNames = new fnames();
        motherNames = new mnames();
        spouseNames = new snames();









        try {
            reader = new FileReader("json/snames.json");
            spouseNames = gson.fromJson(reader, snames.class);
            reader.close();

            reader = new FileReader("json/mnames.json");
            motherNames = gson.fromJson(reader, mnames.class);
            reader.close();

            reader = new FileReader("json/fnames.json");
            fatherNames = gson.fromJson(reader, fnames.class);
            reader.close();

            reader = new FileReader("json/locations.json");
            locationArray = gson.fromJson(reader,LocationData.class);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            System.out.println("Something has been wrong with the file");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static json getInstance()
    {
        if(fillData == null)
        {
            fillData = new json();
        }
        return fillData;
    }
}
