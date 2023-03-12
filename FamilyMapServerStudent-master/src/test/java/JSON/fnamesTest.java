package JSON;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class fnamesTest {
    Gson gson;
    fnames fnamesArray;
    FileReader file;


    @BeforeEach
    void setUp()
    {
        gson = new Gson();
        fnamesArray = new fnames();

    }

    @Test
    void serializeJsonToObject() throws FileNotFoundException {
        Reader reader = new FileReader("json/fnames.json");
        fnamesArray = gson.fromJson(reader, fnames.class);

        fnamesArray.printArrays();

    }

}
