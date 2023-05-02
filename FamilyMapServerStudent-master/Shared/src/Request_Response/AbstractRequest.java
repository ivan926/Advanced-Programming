package Request_Response;

public class AbstractRequest {
    AbstractRequest(){}


    public void toString(String username,String password) {
        System.out.printf("Username = %s password = %s",username,password);
    }

    public void toString(String username,String password,String email,String firstname, String lastname,String gender) {
        System.out.printf("{ \"Username\" = \"%s\",%n\"password\" = \"%s\",%n\"email\" = \"%s\",%n\"firstName\" = \"%s\",%n\"lastName\" = \"%s\",%n\"gender\" = \"%s\" }%n",username,password,email,firstname,lastname,gender);
    }


}
