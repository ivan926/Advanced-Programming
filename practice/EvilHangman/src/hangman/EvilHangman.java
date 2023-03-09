package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Character.isAlphabetic;

public class EvilHangman {

    public static void main(String[] args) {
        String DictionaryName = args[0];
        File diction = new File(DictionaryName);
        int wordLength = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);

        EvilHangmanGame gameInterface = new EvilHangmanGame();
        try{
            gameInterface.startGame(diction,wordLength);
        }
        catch(EmptyDictionaryException | IOException exception)
        {

        }

        Boolean gameStillOn = true;
        Scanner in = new Scanner(System.in);

        while(gameStillOn)
        {
            System.out.printf("You have %d left%n",guesses);
            System.out.printf("Used letters: %s %n",gameInterface.getGuessedLetters());
            System.out.printf("Word: %s%n",gameInterface.getCurrentGuess());

            String input = in.next();
            char[] arrayInput = input.toCharArray();
            try{
                if(input == "" || !isAlphabetic(arrayInput[0]))
                {
                    throw new GuessAlreadyMadeException();
                }
                gameInterface.makeGuess(arrayInput[0]);
                if(!gameInterface.guessedCorrectWord)
                {
                    guesses--;
                }
            }
            catch(GuessAlreadyMadeException exception)
            {

            }
            if(gameInterface.HasPlayerGuessedTheWord())
            {
                System.out.printf("Sign you sadly won the game the word is: %s",gameInterface.getCurrentGuess());
                gameStillOn = false;
            }
             else if(guesses == 0)
            {
                gameStillOn = false;
            }






        }


    }

}
