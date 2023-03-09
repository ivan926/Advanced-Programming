package hangman;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Character.isAlphabetic;

public class EvilHangmanGame implements IEvilHangmanGame{
    /**
     * Starts a new game of evil hangman using words from <code>dictionary</code>
     * with length <code>wordLength</code>.
     *	<p>
     *	This method should set up everything required to play the game,
     *	but should not actually play the game. (ie. There should not be
     *	a loop to prompt for input from the user.)
     *
     * @param dictionary Dictionary of words to use for the game
     * @param wordLength Number of characters in the word to guess
     * @throws IOException if the dictionary does not exist or an error occurs when reading it.
     * @throws EmptyDictionaryException if the dictionary does not contain any words.
     */
    private Set<String> wordSet = new HashSet<>();
    private String initialGuess = null;
    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        Boolean isEmpty = false;
        StringBuilder str = new StringBuilder();
        sortedListOfGuessedLetters.clear();
        wordSet.clear();

        Scanner input = new Scanner(dictionary);
        for(int i = 0 ; i < wordLength; i++)
        {
            str.append("_");

        }
        initialGuess = str.toString();
        currentGuess = initialGuess;

        str.setLength(0);



        while(input.hasNext())
        {
            String currentWord = input.next();
            if(currentWord.length() == wordLength)
            {   isEmpty = true;
                wordSet.add(currentWord);
            }


        }

        if(isEmpty == false)
        {
            throw new EmptyDictionaryException();

        }

    }

    /**
     * Make a guess in the current game.
     *
     * @param guess The character being guessed, case insensitive
     *
     * @return The set of strings that satisfy all the guesses made so far
     * in the game, including the guess made in this call. The game could claim
     * that any of these words had been the secret word for the whole game.
     *
     * @throws GuessAlreadyMadeException if the character <code>guess</code>
     * has already been guessed in this game.

     */

    private String partitionKey(String word, char guess)
    {
        StringBuilder str = new StringBuilder();
        char[] arrayOfChar = word.toCharArray();
        for(int i = 0 ; i < arrayOfChar.length;i++)
        {
            if(arrayOfChar[i] == guess)
            {
                str.append(guess);
            }
            else
            {
                str.append("_");
            }

        }

        String newKey = str.toString();

        return newKey;

    }

    private char letterBeingUsed;
    private String currentGuess = null;
    private SortedSet<Character> sortedListOfGuessedLetters = new TreeSet<>();

    public Boolean guessedCorrectWord = false;
    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
       guess = Character.toLowerCase(guess);
        letterBeingUsed = guess;

       Map<String,Set<String>> subsets = new TreeMap<>();
        if(sortedListOfGuessedLetters.contains(guess))
        {
            throw new GuessAlreadyMadeException();
        }
        sortedListOfGuessedLetters.add(guess);

        Iterator iter = wordSet.iterator();

        while(iter.hasNext())
        {
            String currentWord = iter.next().toString();
           String currentKey = partitionKey(currentWord,guess);
               if(!subsets.containsKey(currentKey))
               {
                   Set<String > newSet = new HashSet<>();
                   newSet.add(currentWord);
                   subsets.put(currentKey,newSet);
               }
               else
               {
                   subsets.get(currentKey).add(currentWord);
               }



        }
        int mostElelements = 0;
        for(Map.Entry<String,Set<String>> entry : subsets.entrySet())
        {
            int currentAmount = entry.getValue().size();
            if(currentAmount > mostElelements)
            {
                mostElelements = currentAmount;
            }

        }

        ArrayList<String> keysWithHighestAmounts = new ArrayList<String>();
        for(Map.Entry<String,Set<String>> entry : subsets.entrySet())
        {
            int currentAmount = entry.getValue().size();
            if(currentAmount == mostElelements)
            {
                keysWithHighestAmounts.add(entry.getKey());
            }

        }
        String winningKey = null;

        if(keysWithHighestAmounts.size() == 1)
        {
            if(keysWithHighestAmounts.contains(initialGuess))
            {//if it is an empty string or blank

                System.out.printf("Sorry there are no %c's%n",letterBeingUsed);
                winningKey = keysWithHighestAmounts.get(0);
            }
            else {

                winningKey = keysWithHighestAmounts.get(0);

            }
        }
        else if(keysWithHighestAmounts.size() > 1)
        {
            if(keysWithHighestAmounts.contains(initialGuess))
            {

                System.out.printf("Sorry there are no %c's%n",letterBeingUsed);

                winningKey = initialGuess;


            }
            else {
                winningKey = tieBreaker(keysWithHighestAmounts);


            }
        }

        System.out.println(winningKey);
        if(winningKey.equals( initialGuess))
        {
           // updateCurrentGuess(winningKey);

            guessedCorrectWord = false;
        }
        else
        {

            updateCurrentGuess(winningKey);
                guessedCorrectWord = true;
        }

        wordSet = subsets.get(winningKey);

        return wordSet;
    }
    public String getCurrentGuess()
    {

        return currentGuess;
    }

    public Boolean HasPlayerGuessedTheWord()
    {
        Boolean playerWon = false;
        if(!currentGuess.contains("_"))

        {
            playerWon = true;
        }

        return playerWon;
    }

    private void updateCurrentGuess(String newKey)
    {
                StringBuilder str = new StringBuilder();
                char[] newKeyChar = newKey.toCharArray();
                char[] currentKeyChar = currentGuess.toCharArray();
                int letterFrequency = 0;

                for(int i  =0 ; i < currentKeyChar.length;i++)
                {
                    if(currentKeyChar[i] == '_' && isAlphabetic(newKeyChar[i]) )
                    {      letterFrequency++;
                        str.append(newKeyChar[i]);
                    }
                    else if(isAlphabetic(currentKeyChar[i]))
                    {
                        str.append(currentKeyChar[i]);
                    }
                    else{
                        str.append("_");
                    }

                }
                System.out.printf("There is %d %c%n",letterFrequency,letterBeingUsed);

                currentGuess = str.toString();
                str.setLength(0);



     }

    private String tieBreaker(ArrayList<String> arrayList)
    {
        //wich has the fewest
        int leastAmount = 700;
        for(String currentWord : arrayList)
        {
            char[] letterArray = currentWord.toCharArray();
            int currentAmount = 0;
            for(char letter : letterArray)
            {
                if(letter == letterBeingUsed)
                {
                    currentAmount++;
                }
            }
            if(currentAmount < leastAmount)
            {
                leastAmount = currentAmount;
            }

        }

        ArrayList<String> newArrays = new ArrayList<String>();

        for(String currentKey : arrayList)
        {
            char[] letterArray = currentKey.toCharArray();
            int currentAmount = 0;
            for(char letter : letterArray)
            {
                if(letter == letterBeingUsed)
                {
                    currentAmount++;
                }
            }
            if(currentAmount == leastAmount)
            {
                newArrays.add(currentKey);
            }

        }
        String winningKey = null;

        if(newArrays.size() == 1)
        {
            winningKey = newArrays.get(0);

        }
        else if(newArrays.size() > 1)
        {
            winningKey = furthestRight(newArrays);
        }

        return winningKey;
    }
    private String furthestRight(ArrayList<String> arrayList)
    {
        int oldHighest = 0;
        String winningKey = null;
        for(String word : arrayList)
        {
            char[] charArray = word.toCharArray();
            int highestAmount = 0;
            for(int i = 0 ; i < charArray.length; i++)
            {
                if(charArray[i] == letterBeingUsed)
                {
                    highestAmount+=i;
                }
            }
            if(highestAmount > oldHighest)
            {
                oldHighest = highestAmount;
                winningKey = word;
            }


        }

        return winningKey;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {

//
        return sortedListOfGuessedLetters;
    }
}
