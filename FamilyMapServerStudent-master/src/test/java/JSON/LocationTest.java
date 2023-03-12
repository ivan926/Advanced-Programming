package JSON;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

public class LocationTest {
    Gson gson;
    LocationData locationArray;
    FileReader file;


    @BeforeEach
    void setUp()
    {
        gson = new Gson();
        locationArray = new LocationData();

    }

    @Test
    void serializeJsonToObject() throws FileNotFoundException {
        Reader reader = new FileReader("json/locations.json");
        locationArray = gson.fromJson(reader,LocationData.class);

        locationArray.printArrays();

    }






}
