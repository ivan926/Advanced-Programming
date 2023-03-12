package JSON;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class jsonTest {

    Gson gson;
    json fillInData;
    fnames fatherNames;
    mnames motherNames;
    snames spouseNames;
    LocationData locations;
    json getFillInData1;
    json getFillInData2;



    @BeforeEach
    void setUp()
    {
        gson = new Gson();
        fillInData = json.getInstance();

    }

    @Test
    void printLocationsTest()  {
        locations = fillInData.getLocationArray();
        locations.printArrays();


    }

    @Test
    void printSnamesTest()  {
        spouseNames = fillInData.getSurNames();
        spouseNames.printArrays();


    }

    @Test
    void printfnamesTest()  {
        fatherNames = fillInData.getFatherNames();
        fatherNames.printArrays();


    }

    @Test
    void printMnamesTest()  {
        motherNames = fillInData.getMotherNames();
        motherNames.printArrays();


    }

    @Test
    void singleTonPrincipleSuccess()
    {
        fillInData = json.getInstance();

        getFillInData1 = json.getInstance();

        getFillInData2 = json.getInstance();

        if(getFillInData1 == fillInData)
        {
            System.out.println("The class is the same reference");
            System.out.println(fillInData);
            System.out.println(getFillInData2);
        }

        assertEquals(getFillInData2,getFillInData1);


    }

}
