package JSON;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class mnamesTest {
    Gson gson;
    mnames mnamesArray;
    FileReader file;


    @BeforeEach
    void setUp()
    {
        gson = new Gson();
        mnamesArray = new mnames();

    }

    @Test
    void serializeJsonToObject() throws FileNotFoundException {
        Reader reader = new FileReader("json/mnames.json");
        mnamesArray = gson.fromJson(reader, mnames.class);

        mnamesArray.printArrays();

    }
}
