package hangman;

public class GuessAlreadyMadeException extends Exception {

    GuessAlreadyMadeException()
    {

    }

    GuessAlreadyMadeException(String message)
    {
        System.out.println(message);

    }
}
