package JSON;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class snamesTest {
    Gson gson;
    snames snamesArray;
    FileReader file;


    @BeforeEach
    void setUp()
    {
        gson = new Gson();
        snamesArray = new snames();

    }

    @Test
    void serializeJsonToObject() throws FileNotFoundException {
        Reader reader = new FileReader("json/snames.json");
        snamesArray = gson.fromJson(reader, snames.class);

        snamesArray.printArrays();

    }
}
