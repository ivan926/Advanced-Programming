package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.Character.isAlphabetic;

public class EvilHangman {

    public static void main(String[] args) {
        String dictionaryStringName = args[0];
        int wordLength = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);
        EvilHangmanGame gameInterface = new EvilHangmanGame();
        File dictionary = new File(dictionaryStringName);
        try
        {
            gameInterface.startGame(dictionary,wordLength);

        }
        catch(IOException | EmptyDictionaryException exception)
        {

        }

        if(wordLength < 2)
        {
            System.exit(0);
        }
        if(guesses < 1)
        {
            System.exit(0);
        }

        Boolean gameStillOn = true;

        Scanner stream = new Scanner(System.in);
        Set<String> wordSet = new TreeSet<>();

        while(gameStillOn)
        {
            System.out.printf("%nYou have %d guesses left%n",guesses);
            System.out.printf("Used letters: %s%n",gameInterface.getGuessedLetters());
            System.out.printf("Word: %s%n",gameInterface.printCurrentGuess());
            System.out.println("Enter a guess: ");



            try {
                String input = stream.next();

                char[] charInput = input.toCharArray();


                if (input == "" || !isAlphabetic(charInput[0])) {
                    throw new GuessAlreadyMadeException("Invalid input");

                }

                wordSet=  gameInterface.makeGuess(charInput[0]);
                if(gameInterface.didUserGuessRight())
                {
                    guesses--;
                }


            }
            catch(GuessAlreadyMadeException exception)
            {


            }

            if(gameInterface.DidUserGuessTheWord())
            {
                System.out.printf("You win! you guessed the word: %s",gameInterface.printCurrentGuess());
                gameStillOn = false;

            }
            else if(guesses == 0)
            {
                System.out.printf("Sorry you lost! the word was: %s", wordSet.stream().toList().get(0));
                gameStillOn = false;
            }

        }


    }

}
