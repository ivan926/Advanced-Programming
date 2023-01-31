package hangman;

public class GuessAlreadyMadeException extends Exception {
    GuessAlreadyMadeException()
    {
        System.out.println("Guess Already made");
    }

    GuessAlreadyMadeException(String message)
    {
        System.out.println(message);
    }


    public void printMessage(String message)
    {
        System.out.println(message);
    }

}
