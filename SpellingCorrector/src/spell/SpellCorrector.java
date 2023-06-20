package spell;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.*;
import java.util.stream.IntStream;

public class SpellCorrector implements ISpellCorrector{
    private Trie dictionary;
    HashMap<String, Integer> suggestedWords;


    public SpellCorrector()
    {
        dictionary = new Trie();
        suggestedWords = new HashMap<String, Integer>();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {

        File input = new File(dictionaryFileName);

		Scanner scanner = new Scanner(input);


		while(scanner.hasNext())
		{
			dictionary.add(scanner.next());
		}



    }



    private void deletionDistance(String inputWord, ArrayList editDistanceOneArray)
    {
        String tempWord = inputWord;

        char[] charOfArrays = inputWord.toCharArray();
        ArrayList<Character> currentCharOfWord = new ArrayList<Character>();

        for(int i = 0 ; i< charOfArrays.length;i++ )
        {

            currentCharOfWord.add(charOfArrays[i]);


        }


        StringBuilder sb = new StringBuilder();

        ArrayList<Character> tempArray = new ArrayList<>();

        for(int i = 0 ; i < currentCharOfWord.size();i++)
        {
            tempArray = (ArrayList<Character>) currentCharOfWord.clone();

            tempArray.remove(i);

            for(Object Letter : tempArray)
            {
                sb.append(Letter);

            }

            editDistanceOneArray.add(sb.toString());

            sb.setLength(0);


        }




    }

    private void TranspositionDistance(String inputWord,ArrayList editDistanceOneArray)
    {
        String tempWord = inputWord;

        char[] arrayOfChar = inputWord.toCharArray();


        for(int i = 0 ; i < arrayOfChar.length-1;i++)
        {
            char swap;

            arrayOfChar = inputWord.toCharArray();

            swap = arrayOfChar[i];

            arrayOfChar[i] = arrayOfChar[i + 1];

            arrayOfChar[i+1] = swap;

            editDistanceOneArray.add(String.valueOf(arrayOfChar));


        }




    }




    private void AlterationDistance(String inputWord,ArrayList editDistanceOneArray )
    {
        char[] alphabet = new char[26];

        char[] charOfArrays = inputWord.toCharArray();

        for(int i = 0 ; i < charOfArrays.length;i++)
        {
            char restrictedLetter = charOfArrays[i];

            charOfArrays = inputWord.toCharArray();

            for(int j = 0 ; j < alphabet.length;j++)
            {
                char letter = (char)('a' + j);

                if(letter != restrictedLetter)
                {
                    charOfArrays[i] = letter;
                    //String tempWord = String.valueOf(charOfArrays);
                    editDistanceOneArray.add(String.valueOf(charOfArrays));


                }


            }


        }


    }

    private void InsertionDistance(String inputWord, ArrayList editDistanceOneArray)
    {
        char[] alphabet = new char[26];

        char[] arrayOfChar = inputWord.toCharArray();

        StringBuffer sb = new StringBuffer();

        for(int i = 0 ; i < arrayOfChar.length + 1;i++)
        {


            for(int j = 0 ; j < alphabet.length;j++)
            {
                char letter = (char)('a'+j);

                sb.append(inputWord);

                sb.insert(i,letter);
                String tempWord = sb.toString();
                editDistanceOneArray.add(tempWord);
                sb.setLength(0);


            }


        }




    }

    private Boolean editDistanceCheck(ArrayList editDistanceOneArray)
    {
        //this will ensure any edit distance 1 words are gone if we are running this for edit distance 2
        suggestedWords.clear();
        Boolean foundAtLeastOneWord = false;
        INode wordFoundInDictionary;
        Iterator iter = editDistanceOneArray.iterator();
       int count = 0;
        while(iter.hasNext())
        {

            String san = (String)iter.next();
            wordFoundInDictionary = dictionary.find(san);
            if(wordFoundInDictionary  != null )
            {
                foundAtLeastOneWord = true;

                suggestedWords.put(san,wordFoundInDictionary.getValue());
            }


        }

        return foundAtLeastOneWord;

    }

    private Boolean editDistanceTwoWords(ArrayList editDistanceTwoArray,ArrayList editDistanceOneArray)

    {
        //clear edit distance array to be used again in the edit distance methods
//
        editDistanceOneArray.clear();
        Boolean found = false;
        for(int i = 0; i < editDistanceTwoArray.size(); i ++)
        {
            deletionDistance(editDistanceTwoArray.get(i).toString(),editDistanceOneArray);

            TranspositionDistance(editDistanceTwoArray.get(i).toString(),editDistanceOneArray);

            AlterationDistance(editDistanceTwoArray.get(i).toString(),editDistanceOneArray);

            InsertionDistance(editDistanceTwoArray.get(i).toString(),editDistanceOneArray);

        }

        found = editDistanceCheck(editDistanceOneArray);

        if(found)
        {
            //word has been found
            return found;
        }
        else{
            return false;
        }

    }

    private String wordFoundInDictionary()
    {

        String suggestedword = "";
        ArrayList highestFrequencyContainer = new ArrayList();


        int highestFrequency = 0;
        for (Map.Entry<String,Integer> entry : suggestedWords.entrySet())
        {
            int frequency = entry.getValue();

            if(frequency > highestFrequency)
            {
                highestFrequency = frequency;
            }
//                    System.out.println("Key = " + entry.getKey() +
//                            ", Value = " + entry.getValue());

        }

        //pick out the values that have the highest frequency
        for (Map.Entry<String,Integer> entry : suggestedWords.entrySet())
        {
            if(entry.getValue() == highestFrequency)
            {
                highestFrequencyContainer.add(entry.getKey());
            }

        }
        //if there is more than one word with the same frequency
        if(highestFrequencyContainer.size() > 1)
        {

            String tempWord;
            int difference = 0;
            for(int i = 0; i < highestFrequencyContainer.size() - 1; i++)
            {
                tempWord =  highestFrequencyContainer.get(i).toString();
                difference =  tempWord.compareTo((String)highestFrequencyContainer.get(i + 1));
                if(difference < 0)
                {
                    suggestedword = tempWord;
                }
                else
                {
                    suggestedword = highestFrequencyContainer.get(i + 1).toString();
                }

            }


        }
        else if( highestFrequencyContainer.size() == 1)
        {
            suggestedword = highestFrequencyContainer.get(0).toString();

        }
        else
        {
            suggestedword = null;
        }


        //use dictionary to determine if there is more than 1
        //if there is more than 1 use frequency to tell which has higher priority
        //if both have higher priority read write up to determine what the brea
        return suggestedword;
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
//        editDistanceOneArray.clear();
        ArrayList<String> editDistanceOneArray = new ArrayList<>();
        ArrayList<String> editDistanceTwoArray = new ArrayList<>();

        if( dictionary.find(inputWord) != null)
        {
            return inputWord;
        }
        //if the word is not found in the dictionary then try to find the closest suggestion
        //using the closest edit distance
        else{

            String suggestedword = "";

            Boolean foundWordInDictionary = false;

            deletionDistance(inputWord,editDistanceOneArray);

            TranspositionDistance(inputWord,editDistanceOneArray);

            AlterationDistance(inputWord,editDistanceOneArray);

            InsertionDistance(inputWord,editDistanceOneArray);

            foundWordInDictionary = editDistanceCheck(editDistanceOneArray);


            if(foundWordInDictionary == false)
            {
                //use edit distance two to run all words recently created through edit distance methods again.
                //make sure new array list of words are created
                //run new words through edit distance check again.
                editDistanceTwoArray = (ArrayList)editDistanceOneArray.clone();
                foundWordInDictionary = editDistanceTwoWords(editDistanceTwoArray,editDistanceOneArray);


                if(foundWordInDictionary)
                {//if found or if null
                    suggestedword = wordFoundInDictionary();
                }
                else
                {
                    suggestedword = null;
                }


            }
            else if(foundWordInDictionary)
            {
               suggestedword = wordFoundInDictionary();

            }











            return suggestedword;
        }



    }
}
