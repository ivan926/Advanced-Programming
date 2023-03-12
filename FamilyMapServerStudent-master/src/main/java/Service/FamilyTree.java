package Service;

import JSON.Location;
import JSON.json;
import model.Event;
import model.Person;
import model.User;
import java.time.Year;
import java.util.Random;

public class FamilyTree {
    Person root;
    UniqueID ID;
    int usersGeneration;
    int birthYear;
    Year year = Year.now();

    public FamilyTree()
    {
        this.ID = UniqueID.getUniqueDatabaseInstance();
    }

    public FamilyTree(User RootPerson){
        root = new Person(RootPerson.getPersonID(),RootPerson.getUsername(),RootPerson.getFirstName(),RootPerson.getLastName(),
        RootPerson.getGender(),null,null,null);
        this.ID = UniqueID.getUniqueDatabaseInstance();
    }

    public void fillTree(int generations)
    {
        setStartingBirthYear(generations);

        usersGeneration = generations;
        fillTreeHelper(root.getGender(),generations);

    }

    public void fillTree(String gender, int generations)
    {   usersGeneration = generations;
        fillTreeHelper(root.getGender(),generations);

    }


    private Person fillTreeHelper(String gender,int generations)
    {
        Person father = null;
        Person mother = null;

        json fillInData = json.getInstance();

        while(generations > 1)
        {
            mother = fillTreeHelper(Gender.FEMALE.toString(), generations-1);
            father = fillTreeHelper(Gender.MALE.toString(), generations-1);

            //set mother and fathers spouse ID's
            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());

            //increments birth year
            incrementCurrentBirthYear();

            Location[] location = fillInData.getLocationArray().getArray();
            Random random = new Random();
            int randomNum = random.nextInt(location.length);
            String country = location[randomNum].getCountry();
            String city  = location[randomNum].getCity();
            Float latitude = Float.parseFloat(location[randomNum].getLatitude());
            Float longitude = Float.parseFloat(location[randomNum].getLongitude());

            //add marriage events to mother and father
            Event Motherevent = new Event(ID.getUniqueID(),root.getAssociatedUsername(), mother.getPersonID(),latitude,
                    longitude,country,city,"Marriage",1995);

            Event Fatherevent = new Event(ID.getUniqueID(),root.getAssociatedUsername(), father.getPersonID(),latitude,
                    longitude,country,city,"Marriage",1995);


            //(Their Marriage events must be in sync with each other)

        }



        Random random = new Random();
        int numberOfFatherNames = fillInData.getFatherNames().getFatherNameArray().length;
        int numberOfMotherNames = fillInData.getMotherNames().getMotherNamesArray().length;
        int numberOfSurNames = fillInData.getSurNames().getSurnameNameArray().length;

        int randomNum;


        Person person = null;
        String personID = ID.getUniqueID();

        String[] maleNames = fillInData.getFatherNames().getFatherNameArray();
        String[] femaleNames = fillInData.getMotherNames().getMotherNamesArray();
        String[] lastnames = fillInData.getSurNames().getSurnameNameArray();

        //setting person properties
        if(gender.equals("m"))
        {
            //building Male person
            randomNum = random.nextInt(numberOfFatherNames);
            String MaleName = maleNames[randomNum];
            String lastName = lastnames[random.nextInt(numberOfSurNames)];
            person = new Person();

            person.setPersonID(personID);
            person.setFirstName(MaleName);
            person.setLastName(lastName);
            person.setGender(gender);
            person.setFatherID(father.getPersonID());
            person.setMotherID(mother.getPersonID());

        }
        else
        {
            //building Female person
            randomNum = random.nextInt(numberOfMotherNames);
            String femaleName = femaleNames[randomNum];
            String lastName = lastnames[random.nextInt(numberOfSurNames)];
            person = new Person();

            person.setPersonID(personID);
            person.setFirstName(femaleName);
            person.setLastName(lastName);
            person.setGender(gender);
            person.setFatherID(father.getPersonID());
            person.setMotherID(mother.getPersonID());

        }

        Location [] locations = fillInData.getLocationArray().getArray();
        randomNum = random.nextInt(locations.length);

        float latitude = Float.parseFloat(locations[randomNum].getLatitude());
        float longitude = Float.parseFloat(locations[randomNum].getLongitude());
        String city =  locations[randomNum].getCity();
        String country = locations[randomNum].getCountry();


        //Generate events for person (except marriage)
        Event Birthevent;
        Event Death;

        Birthevent = new Event(ID.getUniqueID(),root.getAssociatedUsername(),person.getPersonID(),latitude,longitude,
                country,city,"Birth",getBirthYear());

        //regenerate location
        randomNum = random.nextInt(locations.length);

        latitude = Float.parseFloat(locations[randomNum].getLatitude());
        longitude = Float.parseFloat(locations[randomNum].getLongitude());
        city =  locations[randomNum].getCity();
        country = locations[randomNum].getCountry();


        int currentYear = year.getValue();
        int PersonsAge = currentYear - this.birthYear;
        if(PersonsAge > 120)
        {   //instant death

          int yearOfDeath = getYearOfDeathIfOver120(currentYear,PersonsAge);

            Death = new Event(ID.getUniqueID(),root.getAssociatedUsername(),person.getPersonID(),latitude,
                    longitude,country,city,"Death",yearOfDeath);

        }
        else {

//            //shall they parish?
//            Boolean isDeathBeUponThem;
//            isDeathBeUponThem = random.nextBoolean();
//            if (isDeathBeUponThem) {
                int yearOfDeath = getYearOfDeathIfUnder120(currentYear, PersonsAge);
                Death = new Event(ID.getUniqueID(), root.getAssociatedUsername(), person.getPersonID(), latitude,
                        longitude, country, city, "Death", yearOfDeath);
//            }
//            else {
//            //escaped death..
//                Death = new Event(ID.getUniqueID(), root.getAssociatedUsername(), person.getPersonID(), latitude,
//                        longitude, country, city, "Death", null);
//            }

        }
        //save person in database

        return person;


    }

    private void incrementCurrentBirthYear()
    {
        Random random = new Random();
        int newAge = 0;

        int upperBound = ((this.birthYear+14)+10);
        int lowerBound = (this.birthYear+14);;
        this.birthYear = random.nextInt(upperBound) + lowerBound;
    }

    private int getBirthYear()
    {
        Random random = new Random();
        int personAge = 0;

        int upperBound = (this.birthYear+5);
        int lowerBound = birthYear;
        personAge = random.nextInt(upperBound) + lowerBound;

        return personAge;
    }

    private int getMarriageYear()
    {

        return 0;

    }


    private void setStartingBirthYear(int generation)
    {

        int birthYear = 0;

        int CurrentYear =  year.getValue();

        birthYear = (generation*25) - CurrentYear;

        this.birthYear = birthYear;
    }

    private int getYearOfDeathIfOver120(int currentYear,int PersonsAge)
    {   Random random = new Random();
        int highestYearTheyCanDie = 1 - (currentYear - (PersonsAge - 120));
        int lowestBound = highestYearTheyCanDie - 35; //about life expectancy
        int yearOfDeath = 0;
        yearOfDeath = (random.nextInt(highestYearTheyCanDie) + lowestBound);

        return yearOfDeath;
    }

    private int getYearOfDeathIfUnder120(int currentYear,int PersonsAge)
    {   Random random = new Random();

        int highestYearTheyCanDie = 1 - (currentYear + (120-PersonsAge));
        int lowestBound = highestYearTheyCanDie - 35; //about life expectancy
        //int yearOfDeath = 0;
        int yearOfDeath = (random.nextInt(highestYearTheyCanDie) + lowestBound);

        return yearOfDeath;
    }

}
