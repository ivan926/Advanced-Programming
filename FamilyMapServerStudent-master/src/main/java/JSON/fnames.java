package JSON;

import java.util.Arrays;

public class fnames {
    String[] data;



    public void printArrays()
    {
        int arraySize = Arrays.stream(data).toArray().length;
        for(int i = 0; i <  arraySize;i++)
        {
            System.out.println(Arrays.stream(data).toArray()[i].toString());
        }

    }

    public String[] getFemaleNameArray()
    {
        return data;
    }
}
