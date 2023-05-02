package JSON;

import java.util.Arrays;

public class mnames {
    String[] data;



    public void printArrays()
    {
        int arraySize = Arrays.stream(data).toArray().length;
        for(int i = 0; i <  arraySize;i++)
        {
            System.out.println(Arrays.stream(data).toArray()[i].toString());
        }

    }

    public String[] getMaleNamesArray()
    {
        return data;
    }
}
