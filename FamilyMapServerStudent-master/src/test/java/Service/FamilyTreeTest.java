package Service;

import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class FamilyTreeTest {
    User Rootuser;
    Person person;
    FillService fillData;
    FamilyTree tree;


    @BeforeEach
    void setUp()
    {
        Rootuser = new User("navis462","cookies","lastY@gmail.com","Ravioi"
        ,"Canalozi","m","777");




    }

    @Test
    void fillFamilyTree()
    {
        tree = new FamilyTree(Rootuser);
        tree.fillTree(4);

    }
}
