package JSON;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class json {
    static json fillData;
    LocationData locationArray;
    mnames maleNames;
    fnames femaleNames;
    snames surnameNames;
    Gson gson;
    Reader reader = null;


    public LocationData getLocationArray() {
        return locationArray;
    }

    public mnames getMaleNames() {
        return maleNames;
    }

    public fnames getFemaleNames() {
        return femaleNames;
    }

    public snames getSurNames() {
        return surnameNames;
    }

    private json()
    {
        gson = new Gson();
        locationArray = new LocationData();
        femaleNames = new fnames();
        maleNames = new mnames();
        surnameNames = new snames();









        try {
            reader = new FileReader("json/snames.json");
            surnameNames = gson.fromJson(reader, snames.class);
            reader.close();

            reader = new FileReader("json/mnames.json");
            maleNames = gson.fromJson(reader, mnames.class);
            reader.close();

            reader = new FileReader("json/fnames.json");
            femaleNames = gson.fromJson(reader, fnames.class);
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
