package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Character.isAlphabetic;

public class EvilHangmanGame implements IEvilHangmanGame {

    public EvilHangmanGame()
    {
        wordSet = new HashSet<>();
        currentProgress = "_";
    }
    Set<String> wordSet;
    String currentProgress;
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
    private String initialGuess;
    public void buildInitialGuess(int wordLength)
    {
        StringBuilder initialOutput = new StringBuilder();
        for(int i = 0;i < wordLength;i++)
        {
            initialOutput.append('_');
        }
        initialGuess = initialOutput.toString();
        currentProgress = initialGuess;
    }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        lettersUsed.clear();
        wordSet.clear();
        Scanner words = new Scanner(dictionary);
        StringBuilder initialOutput = new StringBuilder();
        Boolean emptyDictionary = true;
        for(int i = 0;i < wordLength;i++)
        {
            initialOutput.append('_');
        }
        initialGuess = initialOutput.toString();
        currentProgress = initialGuess;

        while(words.hasNext())
        {
            String currentWord = words.next();


            if(currentWord.length() == wordLength)
            {
                emptyDictionary = false;
                wordSet.add(currentWord);
            }

        }

        if(emptyDictionary)
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

    //return key to map
    private String partitionKey(char guess, String word)
    {
        char[]currentWordCharArray = word.toCharArray();
        StringBuilder keyCreator = new StringBuilder();
        for(int i = 0 ; i < currentWordCharArray.length; i++)
        {

            if(currentWordCharArray[i] == guess)
            {
                keyCreator.append(guess);
            }
            else
            {
                keyCreator.append('_');
            }

        }
        String keyCreated = keyCreator.toString();
        keyCreator.setLength(0);

        return keyCreated;


    }
   private ArrayList<Character> lettersUsed = new ArrayList<Character>();
    private char currentLetterGuessed;
    private Boolean GuessedLetter = false;
    public Boolean guessedCorrectly = false;
    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
       guess = Character.toLowerCase(guess);
        currentLetterGuessed = guess;
       Map<String,Set<String>> subSets = new TreeMap<>();
       if(lettersUsed.contains(guess))
       {
           throw new GuessAlreadyMadeException("Guess already made\n");
       }
       lettersUsed.add(guess);

       Iterator iter = wordSet.iterator();
       while(iter.hasNext())
       {
            String CurrentWord = iter.next().toString();
            String currentKey = partitionKey(guess,CurrentWord);

            if(!subSets.containsKey(currentKey))
            {
                Set<String> keySubSet = new HashSet<>();
                keySubSet.add(CurrentWord);
                subSets.put(currentKey,keySubSet);
            }
            else
            {
                subSets.get(currentKey).add(CurrentWord);
            }


       }
       int highestValue = 0;

        for (Map.Entry<String,Set<String>> entry : subSets.entrySet())
        {
            if(entry.getValue().size() > highestValue)
            {
                highestValue = entry.getValue().size();
            }
        }

        ArrayList<String> potentialWinningKey = new ArrayList<String>();
        for (Map.Entry<String,Set<String>> entry : subSets.entrySet())
        {
            if(entry.getValue().size() == highestValue)
            {
                potentialWinningKey.add(entry.getKey());
            }
        }



        if(potentialWinningKey.size() == 1)
        {
            if(potentialWinningKey.get(0).equals(initialGuess))
            {   guessedCorrectly = false;
                System.out.printf("Sorry there are no %c's%n", currentLetterGuessed);
                wordSet = subSets.get(potentialWinningKey.get(0));
            }
            else {
                wordSet = subSets.get(potentialWinningKey.get(0));
                updateProgress(potentialWinningKey.get(0));
            }

        }
        else if(potentialWinningKey.size() > 1)
        {
            if(potentialWinningKey.contains(initialGuess))
            {   guessedCorrectly = false;
                System.out.printf("Sorry there are no %c's%n", currentLetterGuessed);
                wordSet = subSets.get(initialGuess);
                return wordSet;
            }
            else
            {
               String winningKey = multipleKeys(potentialWinningKey);
                updateProgress(winningKey);
                wordSet = subSets.get(winningKey);
            }

        }



        return wordSet;
    }

    private String multipleKeys(ArrayList<String> keys)
    {
        int leastAmountOfLetters = 600;
        for( String currentWord : keys)
        {   int currentAmount = 0;
            char[] arrayofChar = currentWord.toCharArray();
            for(int i = 0; i < arrayofChar.length;i++)
            {
                if(arrayofChar[i] == currentLetterGuessed)
                {
                    currentAmount++;
                }
            }

            if(currentAmount < leastAmountOfLetters)
            {
                leastAmountOfLetters = currentAmount;
            }

        }

        ArrayList<String> LeastLetters = new ArrayList<>();
        for( String currentWord : keys)
        {   int currentAmount = 0;
            char[] arrayofChar = currentWord.toCharArray();
            for(int i = 0; i < arrayofChar.length;i++)
            {
                if(arrayofChar[i] == currentLetterGuessed)
                {
                    currentAmount++;
                }
            }

            if(currentAmount == leastAmountOfLetters)
            {
                LeastLetters.add(currentWord);
            }

        }

        String winningKey = null;
        if(LeastLetters.size() == 1)
        {
            winningKey = LeastLetters.get(0);
        }
        else if(LeastLetters.size() > 1)
        {
           winningKey = furtherstRight(LeastLetters);
        }


        return winningKey;
    }

    private String furtherstRight(ArrayList<String> arrayList)
    {
        int max = 0;
        String winningKey = null;
        for(String currentKey : arrayList)
        {
            char[] arrayOfChar = currentKey.toCharArray();
            int currentAmount = 0;
            for(int i = 0 ; i < arrayOfChar.length;i++)
            {
                if(arrayOfChar[i] == currentLetterGuessed)
                {
                    currentAmount+=i;
                }

            }

            if(currentAmount > max)
            {
                max = currentAmount;
                winningKey = currentKey;
            }

        }

        return winningKey;
    }
    public void updateProgress(String newKey)
    {
        StringBuilder newConstruction = new StringBuilder();
        char[] charArray = newKey.toCharArray();
        char[] currentGuessArray = currentProgress.toCharArray();
        int guessedSize = 0;
        for(int i = 0 ; i < currentGuessArray.length;i++)
        {
            if(currentGuessArray[i] == '_' && isAlphabetic(charArray[i]))
            {   guessedSize++;
                guessedCorrectly = true;
                newConstruction.append(charArray[i]);
            }
            else if(isAlphabetic(currentGuessArray[i]))
            {
                newConstruction.append(currentGuessArray[i]);
            }
            else if(currentGuessArray[i] == '_' && charArray[i] == '_')
            {
                newConstruction.append("_");
            }

        }
        System.out.printf("Yes there is %d %c%n",guessedSize,currentLetterGuessed);
        currentProgress = newConstruction.toString();
        newConstruction.setLength(0);

    }

    public String currentGuess()
    {
        return currentProgress;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        SortedSet<Character> sorted = new TreeSet<>();
        Collections.sort(lettersUsed);

        for(Character letter: lettersUsed)
        {
            sorted.add(letter);
        }

        return sorted;
    }

    public Boolean userHasGuessedWord()
    {   Boolean guessedCorrectWord = false;

        if(!currentProgress.contains("_"))
        {
            guessedCorrectWord = true;
        }
        return guessedCorrectWord;
    }
}
