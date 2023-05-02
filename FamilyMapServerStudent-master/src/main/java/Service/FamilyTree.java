package Service;

import DAO.DataAccessError;
import DAO.EventDAO;
import DAO.PersonDAO;
import JSON.Location;
import JSON.json;
import model.Event;
import model.Person;
import model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Year;
import java.util.Random;

public class FamilyTree {
    Person root;
    UniqueID ID;

    int usersGeneration;
    int birthYear;

    Connection conn;
    Year year = Year.now();

    PersonDAO personDAO;
    EventDAO eventDAO;

    public FamilyTree()
    {
        this.ID = UniqueID.getUniqueDatabaseInstance();
    }

    public FamilyTree(User RootPerson, Connection conn){
        root = new Person(RootPerson.getPersonID(),RootPerson.getUsername(),RootPerson.getFirstName(),RootPerson.getLastName(),
        RootPerson.getGender(),null,null,null);
        this.ID = UniqueID.getUniqueDatabaseInstance();

        this.conn = conn;
    }

    //testing purposes delete later after passing connections
    public FamilyTree(User RootPerson){
        root = new Person(RootPerson.getPersonID(),RootPerson.getUsername(),RootPerson.getFirstName(),RootPerson.getLastName(),
                RootPerson.getGender(),null,null,null);
        this.ID = UniqueID.getUniqueDatabaseInstance();


    }

    public void fillTree()
    {
        int defaultGeneration = 4;
        setStartingBirthYear(defaultGeneration+1);

        usersGeneration = defaultGeneration;
        Person userPerson = fillTreeHelper(root.getGender(),defaultGeneration);
        //sent userPerson data
        personDAO = new PersonDAO(conn);
        try {
            personDAO.insertPerson(userPerson);
            conn.commit();
        } catch (DataAccessError dataAccessError) {
            System.out.println("Had trouble inserting Users person object");
            dataAccessError.printStackTrace();
        }
        catch(SQLException sqlException) {
            System.out.println("Had an issue with the database when trying to add user");
        }
    }

    public void fillTree(int generations)
    {
        //plus 1 to push by a generation from current year for the current user so they are born in 90's early 2000's
        //can always change the current year to 1 generation back as well I.E 2001 or 1997
        setStartingBirthYear(generations+1);

        usersGeneration = generations;
        Person userPerson = fillTreeHelper(root.getGender(),generations);
        personDAO = new PersonDAO(conn);
        try {
            personDAO.insertPerson(userPerson);
            conn.commit();
        } catch (DataAccessError dataAccessError) {
            System.out.println("Had trouble inserting Users person object");
            dataAccessError.printStackTrace();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        //sent userPerson data

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

        if(generations >= 1)
        {
            mother = fillTreeHelper(Gender.FEMALE.toString(), generations-1);
//            if(generations == usersGeneration)
//            {
//                System.out.println("Resetting birht year");
//                setStartingBirthYear(usersGeneration);
//            }
            father = fillTreeHelper(Gender.MALE.toString(), generations-1);


            //increments birth year
//            if(generations != usersGeneration)
//            {
                incrementCurrentBirthYear();
//            }
            //get marriage year
            int marriageYear = getMarriageYear();


            //set mother and fathers spouse ID's
            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());



            Location[] location = fillInData.getLocationArray().getArray();
            Random random = new Random();
            int randomNum = random.nextInt(location.length);
            String country = location[randomNum].getCountry();
            String city  = location[randomNum].getCity();
            Float latitude = Float.parseFloat(location[randomNum].getLatitude());
            Float longitude = Float.parseFloat(location[randomNum].getLongitude());

            eventDAO = new EventDAO(conn);
            PersonDAO  personDAO = new PersonDAO(conn);
            //add marriage events to mother and father
            Event Motherevent = new Event(ID.getUniqueID(),root.getAssociatedUsername(), mother.getPersonID(),latitude,
                    longitude,country,city,"Marriage",marriageYear);

            Event Fatherevent = new Event(ID.getUniqueID(),root.getAssociatedUsername(), father.getPersonID(),latitude,
                    longitude,country,city,"Marriage",marriageYear);

            //send data to database
            try {
                personDAO.insertPerson(mother);
                personDAO.insertPerson(father);
                eventDAO.insertEvent(Motherevent);
                eventDAO.insertEvent(Fatherevent);
                conn.commit();
            } catch (DataAccessError dataAccessError) {
                System.out.println("Had trouble inserting marriage events");
                dataAccessError.printStackTrace();
            }
            catch ( SQLException dataAccessError)
            {
                System.out.println("Had trouble closing database connection");
            }


            //(Their Marriage events must be in sync with each other)

        }

        if(generations == 0)
        {
            System.out.println("Resetting birht year");
            setStartingBirthYear(usersGeneration+1);
        }
        //second eventDAO object outside the scope

        eventDAO = new EventDAO(conn);

        Random random = new Random();
        int numberOfFatherNames = fillInData.getMaleNames().getMaleNamesArray().length;
        int numberOfMotherNames =  fillInData.getFemaleNames().getFemaleNameArray().length;
        int numberOfSurNames = fillInData.getSurNames().getSurnameNameArray().length;

        int randomNum;


        Person person = null;
        String personID = ID.getUniqueID();

        String[] maleNames = fillInData.getMaleNames().getMaleNamesArray();
        String[] femaleNames = fillInData.getFemaleNames().getFemaleNameArray();
        String[] lastnames = fillInData.getSurNames().getSurnameNameArray();

        //setting person properties
        if(generations == usersGeneration)
        {   //this is the user themselves being created
            person = new Person();
            String spouseID = null;
            if(root.getSpouseID() != null)
            {
                spouseID = root.getSpouseID();
                person.setSpouseID(spouseID);
            }
            else
            {
                person.setSpouseID(spouseID);
            }

            person.setPersonID(root.getPersonID());
            person.setAssociatedUsername(root.getAssociatedUsername());
            person.setFirstName(root.getFirstName());
            person.setLastName(root.getLastName());
            person.setGender(root.getGender());


            String fatherID = null;
            String motherID = null;
            if(father != null)
            {
                fatherID = father.getPersonID();
            }

            if (mother != null) {
                motherID = mother.getPersonID();
            }

            person.setFatherID(fatherID);
            person.setMotherID(motherID);

            //generate birth event

            Location[] locations = fillInData.getLocationArray().getArray();
            randomNum = random.nextInt(locations.length);

            float latitude = Float.parseFloat(locations[randomNum].getLatitude());
            float longitude = Float.parseFloat(locations[randomNum].getLongitude());
            String city = locations[randomNum].getCity();
            String country = locations[randomNum].getCountry();


            Event Birthevent;

            int currentPersonBirthYear = getBirthYear();
            Birthevent = new Event(ID.getUniqueID(), root.getAssociatedUsername(), person.getPersonID(), latitude, longitude,
                    country, city, "Birth", currentPersonBirthYear);

            EventDAO eventDAO = new EventDAO(conn);


            try {
                eventDAO.insertEvent(Birthevent);
                conn.commit();
            } catch (DataAccessError dataAccessError) {
                System.out.println("Issue adding birth event for user");
                dataAccessError.printStackTrace();
            }
            catch(SQLException exception)
            {
                System.out.println("Had an issue closing the database connection");
            }



        }
        else {
            if (gender.equals("m")) {
                //building Male person
                randomNum = random.nextInt(numberOfFatherNames);
                String MaleName = maleNames[randomNum];
                String lastName = lastnames[random.nextInt(numberOfSurNames)];
                person = new Person();

                person.setPersonID(personID);
                person.setFirstName(MaleName);
                person.setLastName(lastName);
                person.setGender(gender);
                person.setAssociatedUsername(root.getAssociatedUsername());


                String fatherID = null;
                String motherID = null;
                if(father != null)
                {
                    fatherID = father.getPersonID();
                }

                if (mother != null) {
                    motherID = mother.getPersonID();
                }

                person.setFatherID(fatherID);
                person.setMotherID(motherID);

            } else {
                //building Female person
                randomNum = random.nextInt(numberOfMotherNames);
                String femaleName = femaleNames[randomNum];
                String lastName = lastnames[random.nextInt(numberOfSurNames)];
                person = new Person();

                person.setPersonID(personID);
                person.setAssociatedUsername(root.getAssociatedUsername());
                person.setFirstName(femaleName);
                person.setLastName(lastName);
                person.setGender(gender);

                String fatherID = null;
                String motherID = null;

                if(father != null)
                {
                    fatherID = father.getPersonID();
                }

                if (mother != null) {
                    motherID = mother.getPersonID();
                }


                person.setFatherID(fatherID);
                person.setMotherID(motherID);

            }

            Location[] locations = fillInData.getLocationArray().getArray();
            randomNum = random.nextInt(locations.length);

            float latitude = Float.parseFloat(locations[randomNum].getLatitude());
            float longitude = Float.parseFloat(locations[randomNum].getLongitude());
            String city = locations[randomNum].getCity();
            String country = locations[randomNum].getCountry();


            //Generate events for person (except marriage)
            Event Birthevent;
            Event Death;

            int currentPersonBirthYear = getBirthYear();
            Birthevent = new Event(ID.getUniqueID(), root.getAssociatedUsername(), person.getPersonID(), latitude, longitude,
                    country, city, "Birth", currentPersonBirthYear);

            //regenerate location
            randomNum = random.nextInt(locations.length);

            latitude = Float.parseFloat(locations[randomNum].getLatitude());
            longitude = Float.parseFloat(locations[randomNum].getLongitude());
            city = locations[randomNum].getCity();
            country = locations[randomNum].getCountry();


            int currentYear = year.getValue();
            int PersonsAge = currentYear - currentPersonBirthYear;
            if (PersonsAge > 120) {   //instant death

                int yearOfDeath = getYearOfDeathIfOver120(currentYear, PersonsAge);

                Death = new Event(ID.getUniqueID(), root.getAssociatedUsername(), person.getPersonID(), latitude,
                        longitude, country, city, "Death", yearOfDeath);

                try {
                    eventDAO.insertEvent(Birthevent);
                    eventDAO.insertEvent(Death);
                    conn.commit();
                } catch (DataAccessError dataAccessError) {
                    System.out.println("Issue adding birth event for user");
                    dataAccessError.printStackTrace();
                }
                catch(SQLException exception)
                {
                    System.out.println("Had an issue closing the database connection");
                }

            } else {

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

                try {
                    eventDAO.insertEvent(Birthevent);
                    eventDAO.insertEvent(Death);
                    conn.commit();
                } catch (DataAccessError dataAccessError) {
                    System.out.println("Issue adding birth event for user");
                    dataAccessError.printStackTrace();
                }
                catch(SQLException exception)
                {
                    System.out.println("Had an issue closing the database connection");
                }


            }

        }
        //save person in database

        return person;


    }

    private void incrementCurrentBirthYear()
    {
        Random random = new Random();
        int newAge = 0;

        int upperBound = ((this.birthYear+16+5)+10);
        int lowerBound = (this.birthYear+16+5);;
        this.birthYear = random.nextInt((upperBound - lowerBound) - 1) + lowerBound;
    }

    private int getBirthYear()
    {
        Random random = new Random();
        int personsBirthYear = 0;

        int upperBound = (this.birthYear+5);
        int lowerBound = birthYear;
        personsBirthYear = random.nextInt((upperBound - lowerBound) - 1) + lowerBound;
        //this.birthYear = personAge;

        return personsBirthYear;
    }

    private int getMarriageYear()
    {
        int marriage = this.birthYear;

        return marriage;

    }


    private void setStartingBirthYear(int generation)
    {

        int birthYear = 0;

        int CurrentYear =  year.getValue();

        birthYear =  CurrentYear - (generation*25);

        this.birthYear = birthYear;
    }

    private int getYearOfDeathIfOver120(int currentYear,int PersonsAge)
    {   Random random = new Random();
        int highestYearTheyCanDie =  (currentYear - (PersonsAge - 120)) - 1;
        int lowestBound = highestYearTheyCanDie - 35; //about life expectancy
        int yearOfDeath = 0;
        yearOfDeath = (random.nextInt(highestYearTheyCanDie - lowestBound - 1) + lowestBound);

        return yearOfDeath;
    }

    private int getYearOfDeathIfUnder120(int currentYear,int PersonsAge)
    {   Random random = new Random();

        int highestYearTheyCanDie =  (currentYear + (120-PersonsAge)) - 1;
        int lowestBound = highestYearTheyCanDie - 35; //about life expectancy
        //int yearOfDeath = 0;
        int yearOfDeath = (random.nextInt((highestYearTheyCanDie - lowestBound) - 1 ) + lowestBound);

        return yearOfDeath;
    }

}
