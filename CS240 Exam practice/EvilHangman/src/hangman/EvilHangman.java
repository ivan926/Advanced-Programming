package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Character.isAlphabetic;

public class EvilHangman {

    public static void main(String[] args) {

        File dictionary = new File(args[0]);
        int wordLength = Integer.parseInt( args[1]);
        int guesses = Integer.parseInt(args[2]);
        EvilHangmanGame gameInterface = new EvilHangmanGame();

        if(wordLength < 2)
        {
            System.exit(0);
        }
        if(guesses < 1)
        {
            System.exit(0);
        }
        try{

            gameInterface.startGame(dictionary,wordLength);
        }
        catch(EmptyDictionaryException | IOException exception) {

        }


        Boolean gameStillOn = true;
        Scanner input = new Scanner(System.in);
        while(gameStillOn)
        {
            if(guesses != 1) {
                System.out.printf("You have %d guesses left %n", guesses);
            }
            else{
                System.out.printf("You have %d guess left %n", guesses);
            }

            System.out.printf("Used letters: %s", gameInterface.getGuessedLetters());
            System.out.printf("%nWord: %s %nEnter a guess: ", gameInterface.currentGuess());

            String userInput = input.nextLine();
            char[] array = userInput.toCharArray();

            try {
                if(userInput == "" || !isAlphabetic(array[0]))
                {
                    throw new GuessAlreadyMadeException("Invalid input");
                }


                gameInterface.makeGuess(array[0]);
                if(gameInterface.guessedCorrectly == false)
                {
                    guesses--;
                }


            }
            catch(GuessAlreadyMadeException exception)
            {

            }

            //if user guesses word correctly.
            if(gameInterface.userHasGuessedWord())
            {   System.out.printf("Congratulations the word is: %s",gameInterface.currentGuess());
                gameStillOn = false;
            }

            else if(guesses == 0)
            {
                System.out.println("Sorry you lost!");
                gameStillOn = false;
            }

        }






    }

}
