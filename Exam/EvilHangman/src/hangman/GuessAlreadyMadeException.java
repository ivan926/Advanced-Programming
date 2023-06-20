package hangman;

public class GuessAlreadyMadeException extends Exception {


    GuessAlreadyMadeException(String message)
    {
        System.out.println(message);

    }
}
