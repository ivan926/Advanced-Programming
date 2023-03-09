package spell;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class SpellCorrector implements ISpellCorrector{


    /**
     * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
     * for generating suggestions.
     * @pre SpellCorrector will have had empty-param constructor called, but dictionary has nothing in it.
     * @param dictionaryFileName the file containing the words to be used
     * @throws IOException If the file cannot be read
     * @post SpellCorrector will have dictionary filled and be ready to suggestSimilarWord any number of times.
     */
    private Trie dictionary = new Trie();
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File input = new File(dictionaryFileName);

        Scanner in = new Scanner(input);

        while(in.hasNext())
        {

            String currentWord = in.next();

            dictionary.add(currentWord);


        }

    }


    private String tieBreaker(Map<String,Integer> currentWords)
    {
        String winningString = null;
        int highester = 0;
       for ( Map.Entry<String,Integer> entry : currentWords.entrySet())
        {
               int currentHighest = entry.getValue();
            if(currentHighest > highester)
            {
                highester = currentHighest;
            }
        }
       ArrayList<String> highestFrequency = new ArrayList<String>();
        for ( Map.Entry<String,Integer> entry : currentWords.entrySet())
        {
            int currentHighest = entry.getValue();
            if(currentHighest == highester)
            {
                highestFrequency.add(entry.getKey());
            }
        }
        if(highestFrequency.size() == 1)
        {
            winningString = highestFrequency.get(0);
        }
        else if(highestFrequency.size() > 1)
        {
            winningString = firstAlpha(highestFrequency);

        }


        return winningString;

    }

    private String editDistance2Check(ArrayList<String> arrayList)
    {
        ArrayList<String> edit2NewWords = new ArrayList<String>();
        for(int i = 0 ; i < arrayList.size(); i ++)
        {
            deleteEditDistance(edit2NewWords,arrayList.get(i));

            TranspositionEditDistance(edit2NewWords,arrayList.get(i));

            alterationEditDistance(edit2NewWords,arrayList.get(i));

            InsertionEditDistance(edit2NewWords,arrayList.get(i));

        }

        return editDistanceCheck(edit2NewWords);
    }

    private String firstAlpha(ArrayList<String> arrayList)
    {

        Collections.sort( arrayList);


        return arrayList.get(0);

    }

    private Boolean hasBeenUsed = false;

    private String editDistanceCheck(ArrayList<String> arrayList)
    {
        String chosenString = null;
        ArrayList<String> possibleValues = new ArrayList<String>();
        Map<String,Integer> possibleWords = new HashMap<>();

        Iterator iter = arrayList.iterator();

        while(iter.hasNext())
        {
            String currentWord = iter.next().toString();
            Node currentNode = (Node)dictionary.find(currentWord);
            if(currentNode != null)
            {
                possibleWords.put(currentWord, currentNode.getValue());

            }

        }

        if(possibleWords.size() == 1)
        {
            return possibleWords.keySet().toArray()[0].toString();
        }
        else if(possibleWords.size() > 1)
        {
            chosenString = tieBreaker(possibleWords);
        }
        else if(possibleWords.size() == 0 && !hasBeenUsed)
        {   hasBeenUsed = true;
           chosenString = editDistance2Check(arrayList);
        }


        return chosenString;




    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        ArrayList<String> newWords = new ArrayList<String>();
        String suggestedWord = null;
        if(dictionary.find(inputWord) != null)
        {
            return inputWord;
        }

        deleteEditDistance(newWords,inputWord);

        TranspositionEditDistance(newWords ,inputWord);

        alterationEditDistance(newWords,inputWord);

        InsertionEditDistance(newWords,inputWord);

        suggestedWord  = editDistanceCheck(newWords);


        hasBeenUsed = false;




        return suggestedWord;
    }

    private void deleteEditDistance(ArrayList<String> arrayList,String word)
    {
        char[] charArray = word.toCharArray();
        ArrayList<Character> characterContainer = new ArrayList<Character>();


        for(int i = 0 ; i < charArray.length;i++)
        {
            characterContainer.add(charArray[i]);

        }

        StringBuilder str = new StringBuilder();

        ArrayList<Character> copyContainer = new ArrayList<Character>();

        for(int i = 0 ; i < charArray.length; i++)
        {
            copyContainer = (ArrayList<Character>) characterContainer.clone();
            copyContainer.remove(i);
            for(Character letter : copyContainer)
            {
                str.append(letter);

            }
            arrayList.add(str.toString());
            str.setLength(0);


        }







    }

    private void TranspositionEditDistance(ArrayList<String> arrayList,String word)
    {
        char[] charArray = word.toCharArray();

        for(int i  = 0 ; i < charArray.length -1;i++)
        {
            charArray = word.toCharArray();
            char swap;
            swap = charArray[i];
            charArray[i] = charArray[i+1];
            charArray[i+1] = swap;

            arrayList.add(String.valueOf(charArray));


        }




    }

    private void alterationEditDistance(ArrayList<String> arrayList,String word)
    {

        StringBuilder str = new StringBuilder();
        char[] charArray = word.toCharArray();
        char[] alphabet = new char[26];


        for(int i  = 0 ; i < charArray.length;i++)
        {
            charArray = word.toCharArray();
            char originalLetter = charArray[i];
            for(int j = 0; j < alphabet.length;j++)
            {
                char letter = (char)('a' + j);
                if(originalLetter != letter) {
                    charArray[i] = letter;
                    arrayList.add(String.valueOf(charArray));
                }



            }


        }



    }

    private void InsertionEditDistance(ArrayList<String> arrayList,String word)
    {
        StringBuffer buffer = new StringBuffer();
        char[] charArray = word.toCharArray();
        char[] alphabet = new char[26];


        for(int i  = 0 ; i < charArray.length + 1;i++)
        {
            charArray = word.toCharArray();

            for(int j = 0; j < alphabet.length;j++)
            {
                char letter = (char)('a' + j);
                buffer.append(String.valueOf(charArray));
                buffer.insert(i,letter);
                arrayList.add(buffer.toString());
                buffer.setLength(0);
            }



        }





    }



}
