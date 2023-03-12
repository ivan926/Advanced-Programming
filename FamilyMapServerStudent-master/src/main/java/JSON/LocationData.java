package JSON;

import java.util.Arrays;

public class LocationData {
    Location [] data;

//    public LocationData(Location[] data) {
//        this.data = data;
//    }

    public LocationData() { }

    public void printArrays()
    {
        int arraySize = Arrays.stream(data).toArray().length;
        for(int i = 0; i <  arraySize;i++)
        {
            System.out.println(Arrays.stream(data).toArray()[i].toString());
        }

    }

    public Location[] getArray()
    {
        return data;
    }


}
