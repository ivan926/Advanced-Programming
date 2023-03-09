package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Character.isAlphabetic;

public class EvilHangmanGame implements IEvilHangmanGame{
    private String initialGuess = null;
    private Set<String> wordSet = new TreeSet<>();
    private String currentGuess = null;
    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        Scanner in = new Scanner(dictionary);
        wordSet.clear();
        Boolean dictionaryIsEmpty = true;
        lettersUsed.clear();
        StringBuilder str = new StringBuilder();
        for(int i  = 0 ; i < wordLength; i++)
        {
            str.append("_");
        }
        initialGuess = str.toString();
        currentGuess = initialGuess;


        while(in.hasNext())
        {
            String currentWord = in.next();
            if(currentWord.length() == wordLength)
            {
                wordSet.add(currentWord);
                dictionaryIsEmpty = false;
            }

        }

        if(dictionaryIsEmpty)
        {
            throw new EmptyDictionaryException();
        }


    }
    private String partitionKeyCreator(String word, char guess)
    {

        StringBuilder str = new StringBuilder();
        char[] charArray = word.toCharArray();

        for(int i = 0 ; i < charArray.length;i++)
        {

            if(charArray[i] == guess)
            {
                str.append(guess);
            }
            else
            {
                str.append("_");
            }

        }

        String createdKey = str.toString();
        str.setLength(0);

        return createdKey;

    }


    private char currentLetter;
    SortedSet<Character> lettersUsed = new TreeSet<>();
    private Boolean badGuess = false;
    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);
        currentLetter = guess;
        Map<String,Set<String>> subSets = new HashMap<>();

        if(lettersUsed.contains(guess))
        {
            throw new GuessAlreadyMadeException("Guess already made!");
        }
        lettersUsed.add(guess);

        Iterator iter = wordSet.iterator();

        while(iter.hasNext())
        {

            String currentWord = iter.next().toString();

            String newKey = partitionKeyCreator(currentWord,guess);

            if(!subSets.containsKey(newKey))
            {
                Set<String> newSet = new TreeSet<>();
                newSet.add(currentWord);

                subSets.put(newKey,newSet);
            }
            else
            {
                subSets.get(newKey).add(currentWord);
            }





        }

        ArrayList<String> subsetsWithHighest = new ArrayList<String>();
        int highestNumber = 0;

        for(Map.Entry<String,Set<String>> entry : subSets.entrySet())
        {
            int currentValue = entry.getValue().size();
            if(currentValue > highestNumber)
            {
                highestNumber = currentValue;
            }


        }

        for(Map.Entry<String,Set<String>> entry : subSets.entrySet())
        {
            int currentValue = entry.getValue().size();
            if(currentValue == highestNumber)
            {
                subsetsWithHighest.add(entry.getKey());
            }


        }

        String winningKey = null;

        if(subsetsWithHighest.size() == 1)
        {
            winningKey = subsetsWithHighest.get(0);
        }
        else if(subsetsWithHighest.size() > 1)
        {
            if(subsetsWithHighest.contains(initialGuess))
            {

                winningKey = initialGuess;
            }
            else
            {
               winningKey = tieBreaker(subsetsWithHighest);
                //tiebreakers
            }

        }


        if(winningKey.equals(initialGuess))
        {
            System.out.printf("Sorry there are no %c's%n",currentLetter);
            badGuess = true;
        }
        else
        {
            updateCurrentGuess(winningKey);
            //update the currentGuess
            badGuess = false;

        }

       wordSet = subSets.get(winningKey);

        return wordSet;
    }

    private void updateCurrentGuess(String newestKey)
    {
        StringBuilder str = new StringBuilder();
        char[] newestKeyArray = newestKey.toCharArray();
        char[] currentGuessArray = currentGuess.toCharArray();
        int letterFrequencyCount = 0;

        for(int i  = 0 ; i < currentGuessArray.length;i++)
        {
            if(currentGuessArray[i] == '_' && isAlphabetic(newestKeyArray[i]))
            {
                letterFrequencyCount++;
                str.append(newestKeyArray[i]);

            }
            else if(isAlphabetic(currentGuessArray[i]))
            {
                str.append(currentGuessArray[i]);

            }
            else
            {
                str.append("_");
            }
        }
        System.out.printf("yes there is %d %c%n",letterFrequencyCount,currentLetter);
        currentGuess = str.toString();
        str.setLength(0);



    }

    public Boolean didUserGuessRight()
    {
        return badGuess;
    }

    public Boolean DidUserGuessTheWord()
    {
        Boolean userWon = false;
        if(!currentGuess.contains("_"))
        {
            userWon = true;
        }

        return userWon;

    }





    public String printCurrentGuess()
    {
       return currentGuess;
    }

    private String tieBreaker(ArrayList<String> arrayList)
    {
        int fewestLetters = 700;
        for(String currentWord : arrayList)
        {
            char[] charArray = currentWord.toCharArray();
            int currentValue = 0;
            for(char letter : charArray)
            {

                if(letter == currentLetter)
                {
                    currentValue++;

                }

            }

            if(currentValue < fewestLetters)
            {
                fewestLetters = currentValue;
            }

        }

        ArrayList<String> keysWithFewestLetters = new ArrayList<String>();

        for(String currentKey : arrayList)
        {
            char[] charArray = currentKey.toCharArray();
            int currentValue = 0;
            for(char letter : charArray)
            {

                if(letter == currentLetter)
                {
                    currentValue++;

                }

            }

            if(currentValue == fewestLetters)
            {
                keysWithFewestLetters.add(currentKey);
            }

        }
        String winningKey = null;

        if(keysWithFewestLetters.size() == 1)
        {
            winningKey = keysWithFewestLetters.get(0);
        }
        else if(keysWithFewestLetters.size() > 1)
        {

            winningKey = furthestRight(keysWithFewestLetters);

        }



        return winningKey;
    }

    private String furthestRight(ArrayList<String> arrayList)
    {
        int max = 0;
        String winningKey = null;
        for(String currentKey : arrayList)
        {
            char[] charArray = currentKey.toCharArray();
            int currentValue = 0;
            for(int i = 0 ; i < charArray.length; i++)
            {

                if(charArray[i] == currentLetter)
                {
                    currentValue+=i;
                }


            }
            if(currentValue > max)
            {
                max = currentValue;
                winningKey = currentKey;
            }



        }

        return winningKey;



    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return lettersUsed;
    }
}
