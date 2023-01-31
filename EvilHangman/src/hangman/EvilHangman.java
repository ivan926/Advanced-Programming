package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangman {

    public static void main(String[] args) {
        EvilHangmanGame preparation = new EvilHangmanGame();
        File DictionaryName = new File(args[0]);
        int wordLength = Integer.parseInt(args[1]);

        try {
            if(wordLength < 2)
            {
                throw new NotCorrectLengthException();
            }
            //Set up the dictionary
            preparation.startGame(DictionaryName, wordLength);
        } catch (EmptyDictionaryException | IOException | NotCorrectLengthException exception) {
            exception.fillInStackTrace();

        }
        // initialize current word set
        Set<String> currentWordSet = new TreeSet<String>();
        Boolean gameStillOn = true;
        ArrayList lettersUsed = new ArrayList();
        Scanner myObj = new Scanner(System.in);
        int chancesLeft = Integer.parseInt(args[2]);
        if(chancesLeft > 26)
        {
            System.exit(0);
        }

        //need to make some type of loop so you can continue having some guesses
        System.out.println("Welcome to the game");

        while(gameStillOn)
        {
            System.out.printf("%nYou have %d guesses left: %n", chancesLeft);
            System.out.printf("Used Letters: %s %n", preparation.getGuessedLetters());

            preparation.GetGuessedWordSoFar();

            System.out.print("Enter guess: \n");

            String input = myObj.nextLine();

            char[] inputArray = input.toCharArray();




            try {

                if(input == "")
                {
                    throw new GuessAlreadyMadeException("Invalid Input");
                }
                lettersUsed.add(inputArray[0]);
                Collections.sort(lettersUsed);
                currentWordSet = preparation.makeGuess(inputArray[0]);

                if(!preparation.LetterFound()) {
                    chancesLeft--;
                }


            } catch ( GuessAlreadyMadeException exception) {



            }




            if(chancesLeft == 0)
            {
                System.out.printf("Sorry you lost! The word was %s",currentWordSet.toArray()[0]);
//                currentWordSet.toArray();
//                System.out.print( currentWordSet.toArray()[0]);
                gameStillOn = false;
            }
            else if(preparation.userGuessedCorrectWord())
            {
                System.out.printf("You win! You got the word: %s",currentWordSet.toArray()[0]);
                gameStillOn = false;
            }

        }

    }

}
