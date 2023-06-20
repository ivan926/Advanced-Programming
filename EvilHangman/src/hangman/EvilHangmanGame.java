package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Character.isAlphabetic;

public class EvilHangmanGame implements IEvilHangmanGame {

    private  Set<String> dictionaryOfWordsOfFixedLength = new TreeSet();
    private char currentLetter;
    private ArrayList lettersGuessed = new ArrayList<Character>();
    private String guessedWordSoFar;
    private String initialKey;
    private String updatedGuess;
    private Boolean LetterFound = false;
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
    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
       dictionaryOfWordsOfFixedLength.clear();
        lettersGuessed.clear();



        Scanner input = new Scanner(dictionary);
        Boolean DictionaryDoesContainWords = false;

        StringBuilder gw = new StringBuilder();

        gw.delete(0,gw.length());
        for(int i = 0 ; i < wordLength; i++)
        {
            gw.append("_");
        }
        updatedGuess = gw.toString();
        initialKey = gw.toString();
        guessedWordSoFar = gw.toString();

        while(input.hasNext())
        {
            String currentWord = input.next();
            if(currentWord.length() == wordLength)
            {
                DictionaryDoesContainWords = true;
                dictionaryOfWordsOfFixedLength.add(currentWord);
               // System.out.println(currentWord);

            }


        }
        if(!DictionaryDoesContainWords)
        {
            throw new EmptyDictionaryException("Dictionary is empty");
        }





    }






    private String Partitioning(char letter, String word)
    {
       // System.out.println(word);
       char[] charArray = word.toCharArray();
       StringBuilder concatLetterForStringKey = new StringBuilder();

        for(int i = 0; i < charArray.length; i++)
        {
            if(charArray[i] == letter)
            {
                concatLetterForStringKey.append(letter);
            }
            else
            {
                concatLetterForStringKey.append("_");
            }


        }

        String key = concatLetterForStringKey.toString();
        concatLetterForStringKey.delete(0,concatLetterForStringKey.length());

        return key;
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

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        char temporaryLetter = Character.toLowerCase(guess);
        if(lettersGuessed.contains(temporaryLetter) || !isAlphabetic(temporaryLetter) )
        {
            if(!isAlphabetic(temporaryLetter) )
            {
                throw new GuessAlreadyMadeException("Invalid input");
            }
            else {
                throw new GuessAlreadyMadeException();
            }
        }

        lettersGuessed.add(temporaryLetter);


        //initialize the map that will store the sub sets
        currentLetter = guess;
        Map<String,Set<String>> KeyStringToWord = new HashMap<>();
        //make the letters all lower case



    // send the letter to a function which will return a sub string ok a key
        // the key will determine which kind of subset to create
        //use controls structures to determine the creation of a new subset i for the map
        Iterator iter = dictionaryOfWordsOfFixedLength.iterator();

        while(iter.hasNext())
        {
            String tempWord = iter.next().toString();
            String KeyType = "";
            //System.out.println(tempWord);

            KeyType = Partitioning(temporaryLetter,tempWord);

          if( !KeyStringToWord.containsKey(KeyType))
          {
                HashSet<String> newSet = new HashSet<String>();
                newSet.add(tempWord);
                KeyStringToWord.put(KeyType, newSet);

          }
          else
          {
             KeyStringToWord.get(KeyType).add(tempWord);


          }






           //determine if other duplicate subsets with similar sizes exist

          // try to figure out which subset inside the map is the largest of them all
           // findingCorrectSubset(KeyType,KeyStringToWord);




        }
        int maxNumber = 0;
        Boolean foundDuplicate = false;
        Set<String> newWordSet = new HashSet<String>();

        maxNumber = findMaxNumber(KeyStringToWord);

        foundDuplicate = findDuplicate(KeyStringToWord,maxNumber);

        if(foundDuplicate)
        {

            newWordSet = tieBreaker(KeyStringToWord,maxNumber);

        }
        else
        {
            newWordSet = assignTheSubsetWithMostElements(KeyStringToWord, maxNumber);
        }


       // System.out.println("Made it out");


        dictionaryOfWordsOfFixedLength = newWordSet;

        if(guessedWordSoFar.equals(initialKey))
        {   LetterFound = false;
            System.out.printf("Sorry there are no %c's%n",guess);
        }
        else if(!guessedWordSoFar.equals(initialKey))
        {
            updateCurrentGuessedLetters();
        }

        return newWordSet;
    }

    public Boolean userGuessedCorrectWord()
    {
        Boolean guessedWord = false;

        if ( updatedGuess.indexOf("_") == -1)
        {
            guessedWord = true;
        }

        return guessedWord;
    }

    private void updateCurrentGuessedLetters()
    {
        StringBuilder updateCurrentGuess = new StringBuilder();

        char[] CurrentTempArray = guessedWordSoFar.toCharArray();
        char[] tempCurrentString = updatedGuess.toCharArray();
        Boolean foundLetter = false;
        int letterCount = 0;
        char letterFound = ' ';

        for(int i = 0 ; i< CurrentTempArray.length; i++)
        {
            if(tempCurrentString[i] == '_' &&  isAlphabetic(CurrentTempArray[i]))
            {
                letterFound = CurrentTempArray[i];
                foundLetter = true;
                letterCount++;
                updateCurrentGuess.append(CurrentTempArray[i]);
            }
            else if(tempCurrentString[i] == '_' )
            {
                updateCurrentGuess.append('_');
            }
            else if(isAlphabetic(tempCurrentString[i]))
            {
                updateCurrentGuess.append(tempCurrentString[i]);
            }

        }
        if(foundLetter)
        {
            LetterFound = true;
            System.out.printf("Yes there is %d %c%n",letterCount, letterFound);
        }

        updatedGuess = updateCurrentGuess.toString();
        updateCurrentGuess.delete(0,updateCurrentGuess.length());

    }

    public Boolean LetterFound()
    {
        return LetterFound;
    }



    @Override
    public SortedSet<Character> getGuessedLetters() {

        SortedSet<Character> guessedLetters = new TreeSet<>();
        for(Object letter : lettersGuessed )
        {
           guessedLetters.add( (char)letter);

        }


        return guessedLetters;
    }

    public void GetGuessedWordSoFar()
    {
        System.out.println(updatedGuess);
    }


    private int findMaxNumber(Map<String,Set<String>> KeyStringToWord){

        Iterator<Map.Entry<String, Set<String>>> itr = KeyStringToWord.entrySet().iterator();
        int setWithGreatestSize = 0;


        //find max number
        int maxNumber = 0;
        int currentNumber = 0;
        while(itr.hasNext())
        {

            Map.Entry<String, Set<String>> entry = itr.next();

            currentNumber = entry.getValue().size();

            if(currentNumber > maxNumber)
            {
                maxNumber = currentNumber;
            }



        }


        return maxNumber;
    }


    private Boolean findDuplicate(Map<String,Set<String>> KeyStringToWord, int MaxNumber){

        Iterator<Map.Entry<String, Set<String>>> itr = KeyStringToWord.entrySet().iterator();
        int setWithGreatestSize = 0;



        //find max number
        int duplicateSubsetWithSize = 0;
        int currentNumber = 0;

        while(itr.hasNext())
        {

            Map.Entry<String, Set<String>> entry = itr.next();

            currentNumber = entry.getValue().size();

            if(currentNumber == MaxNumber)
            {
                duplicateSubsetWithSize++;
            }



        }

        boolean foundDuplicate = false;

        if(duplicateSubsetWithSize > 1)
        {
            foundDuplicate = true;
        }

        return foundDuplicate;
    }

    private Set<String> assignTheSubsetWithMostElements(Map <String,Set<String>> KeyStringToWord, int maxNumber)
    {
        Set<String> newWordDictionary = new HashSet<String>();

        Iterator<Map.Entry<String, Set<String>>> itr = KeyStringToWord.entrySet().iterator();


        int currentNumber = 0;

        while(itr.hasNext())
        {

            Map.Entry<String, Set<String>> entry = itr.next();

            currentNumber = entry.getValue().size();

            if(currentNumber == maxNumber)
            {
                guessedWordSoFar = entry.getKey();
                newWordDictionary = entry.getValue();
            }



        }


        return newWordDictionary;

    }

    private Set<String> tieBreaker(Map <String,Set<String>> KeyStringToWord, int MaxNumber)
    {
        Iterator<Map.Entry<String, Set<String>>> itr = KeyStringToWord.entrySet().iterator();
        int setWithGreatestSize = 0;
        Set<String> newWordSet = new HashSet<>();
        ArrayList<String> subSetKeysLetterNotContained = new ArrayList<String>();
        ArrayList<String> SubsetKeysLettersFound = new ArrayList<String>();
        Map<String,Set<String>> mapOfSubsetsThatContainLetter = new HashMap<>();



        //find max number

        int currentNumber = 0;
        int setDoesNotContainLetter = 0;
        Boolean LetterFound = false;

        while(itr.hasNext())
        {

            Map.Entry<String, Set<String>> entry = itr.next();

            currentNumber = entry.getValue().size();

            if(currentNumber == MaxNumber)
            {

                LetterFound = letterContainedInSubset(entry.getValue());
                if(!LetterFound)
                {
                    guessedWordSoFar = entry.getKey();
                    newWordSet = entry.getValue();
                    setDoesNotContainLetter++;
                    subSetKeysLetterNotContained.add(entry.getKey());

                }
                else if(LetterFound)
                {
                    SubsetKeysLettersFound.add(entry.getKey());
                    Set<String> tempSet = new HashSet<>();
                    tempSet = entry.getValue();

                    mapOfSubsetsThatContainLetter.put(entry.getKey(),tempSet);
                }
            }

        }


        if(SubsetKeysLettersFound.size() > 1 && setDoesNotContainLetter < 1)
        {
            newWordSet = SetWithLeastWordsFound(mapOfSubsetsThatContainLetter);


            return newWordSet;
            //all subsets share the same letter

        }
        else{// you have multiple sets that contain same letter




            return newWordSet;

        }


    }

    private Boolean letterContainedInSubset(Set<String> wordSet)
    {
        Boolean letterFoundInWordSet = false;

        Iterator<String> iter = wordSet.iterator();

        while(iter.hasNext() && !letterFoundInWordSet)
        {
            String currentWord = iter.next();

          char[] charArray = currentWord.toCharArray();

          for(char letter : charArray)
          {
              if(letter == currentLetter)
              {
                  letterFoundInWordSet = true;
                  break;
              }
          }


        }


        return letterFoundInWordSet;


    }

    private Set<String> SetWithLeastWordsFound(Map<String,Set<String>> map)
    {
        ArrayList<String> leastLetterKey = new ArrayList();
        Set<String> winnerSet = new HashSet<>();
        Map<String,Integer> KeysWithLetterAmount = new HashMap<>();

        Iterator<Map.Entry<String, Set<String>>> itr = map.entrySet().iterator();


        int currentNumber = 0;
        int MaxnumOfletters = 0;
        int letterCount = 0;
        int leastAmount = 300;
        int DuplicateLeastAmount = 0;

        while(itr.hasNext())
        {
            letterCount = 0;
            Map.Entry<String, Set<String>> entry = itr.next();

           String currentWord = entry.getKey();

           char[] charArray = currentWord.toCharArray();

           for(char letter : charArray)
           {
               if(letter == currentLetter)
               {
                   letterCount++;
               }
           }

           if(letterCount <= leastAmount)
           {
               leastLetterKey.add(currentWord);

               if(letterCount == leastAmount)
               {
                   DuplicateLeastAmount++;
               }
                leastAmount = letterCount;


           }

            KeysWithLetterAmount.put(currentWord,letterCount);

        }

        if(leastLetterKey.size() > 1)
        {

            String StringThatIsRightMost = mostRightKey(leastLetterKey);
            guessedWordSoFar = StringThatIsRightMost;
            winnerSet = map.get(StringThatIsRightMost);
            return winnerSet;

        }
        else
        {
            guessedWordSoFar = leastLetterKey.get(0);
            winnerSet = map.get(leastLetterKey.get(0));

            return winnerSet;
        }





    }

    private String mostRightKey(ArrayList<String> LeastLetterSets)
    { //Boolean SingleStringFound = true;
        String winningString = "";
        Iterator iter = LeastLetterSets.iterator();

        int currentAmount = 0;
        int greatestAmount = 0;
        while(iter.hasNext())
        {
            String currentKey = iter.next().toString();
            currentAmount = 0;

            char[] charArray = currentKey.toCharArray();

            for(int i = 0; i < charArray.length; i++)
            {
                if(charArray[i] == currentLetter)
                {
                    currentAmount+=i;
                }


            }

            if(currentAmount >= greatestAmount)
            {
                winningString = currentKey;
                greatestAmount = currentAmount;
            }



        }



        return winningString;
    }






}
