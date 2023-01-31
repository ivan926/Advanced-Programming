package hangman;

public class NotCorrectLengthException extends Throwable {
    NotCorrectLengthException()
    {
        System.out.println("Not the correct length");
    }
}
