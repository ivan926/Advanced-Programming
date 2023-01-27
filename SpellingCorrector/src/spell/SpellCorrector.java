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
    ArrayList<String> editDistanceOneArray;
    ArrayList<String> editDistanceTwoArray;

    public SpellCorrector()
    {
        dictionary = new Trie();
        suggestedWords = new HashMap<String, Integer>();
        editDistanceOneArray = new ArrayList();
        editDistanceTwoArray = new ArrayList();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {

        File input = new File(dictionaryFileName);
        Path fileName = Path.of("/Users/ivanaarriolalugo/Downloads/SpellingCorrector/output.txt");
        File output = new File("/Users/ivanaarriolalugo/Downloads/SpellingCorrector/output.txt");
		Scanner scanner = new Scanner(input);
        Scanner scanner2 = new Scanner(input);


		while(scanner.hasNext())
		{
			dictionary.add(scanner.next());
		}

//        while(scanner2.hasNext())
//        {
//
//            String words =  scanner2.next();
//            //System.out.println(words);
//
//            Files.writeString(fileName,words);
//
//        }


    }



    private void deletionDistance(String inputWord)
    {

        String temp = inputWord;
        char[] array = temp.toCharArray();
        ArrayList listOfCharacters = new ArrayList();

        //System.out.println(temp);
        for(int i = 0; i < inputWord.length(); i++)
        {
           // temp = inputWord;

            listOfCharacters.add(array[i]);
            //System.out.println(listOfCharacters.get(i));
//           // System.out.println(temp.substring(i,i+1));
//            temp = temp.replaceFirst(temp.substring(i,i+1),"");
//            newGeneratedWordList.add(temp);
//            System.out.println(temp);

        }

        ArrayList temporaryList = new ArrayList();

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < listOfCharacters.size(); i++)
        {
            temporaryList = (ArrayList)listOfCharacters.clone();
            temporaryList.remove(i);
            for (Object letter: temporaryList)
            {
                sb.append(letter);
            }


           // System.out.println(sb.toString());

            editDistanceOneArray.add(sb.toString());
            //to clear the current string builder
            sb.setLength(0);

           //System.out.println(newGeneratedWordList.get(i));

        }



    }

    private void TranspositionDistance(String inputWord)
    {
        String temp = inputWord;

        char[] arrayOfChar = inputWord.toCharArray();


        //System.out.println(temp);
        for(int i = 0; i < arrayOfChar.length - 1; i++)
        {

            char swap;

            temp = inputWord;

            arrayOfChar = inputWord.toCharArray();

            swap = arrayOfChar[i];

            arrayOfChar[i] = arrayOfChar[i + 1];
            arrayOfChar[i + 1] = swap;



            temp = String.valueOf(arrayOfChar);

            //System.out.println(temp);

            editDistanceOneArray.add(temp);



        }


    }




    private void AlterationDistance(String inputWord )
    {
        char[] alphabet = new char[26];

        String tempWord = inputWord;

            char[] arrayOfChar = inputWord.toCharArray();

        char letter = 0;

        for(int i = 0; i < arrayOfChar.length;i++)
        {
            char restrictedLetter = arrayOfChar[i];
            arrayOfChar = inputWord.toCharArray();
            tempWord = inputWord;
            for (int j = 0; j < alphabet.length;j++)
            {
                letter =  (char) ('a' + j);
                if(letter != restrictedLetter)
                {
                    arrayOfChar[i] = (char) ('a' + j);
                    tempWord = String.valueOf(arrayOfChar);
                   // System.out.println(tempWord);
                    editDistanceOneArray.add(tempWord);
                }


            }
        }


    }

    private void InsertionDistance(String inputWord)
    {
        char[] alphabet = new char[26];

        String tempWord = inputWord;

       // char[] arrayOfChar = inputWord.toCharArray();

        char letter = 0;

        StringBuffer str = new StringBuffer();
        // + 1 to account for the last index position to insert the letter onto the word
        for(int i = 0; i < inputWord.length() + 1 ;i++)
        {

           // arrayOfChar = inputWord.toCharArray();
            //tempWord = inputWord;
            for (int j = 0; j < alphabet.length;j++)
            {
                letter =  (char) ('a' + j);

                str.append(inputWord);

                str.insert(i,letter);

//                    arrayOfChar[i] = (char) ('a' + j);
                    //tempWord = String.valueOf(arrayOfChar);

                tempWord = str.toString();


                  //  System.out.println(tempWord);
                editDistanceOneArray.add(tempWord);

                    str.delete(0,str.length());

            }
        }



    }

    private Boolean editDistanceCheck()
    {
        //this will ensure any edit distance 1 words are gone if we are running this for edit distance 2
        suggestedWords.clear();
        Boolean foundAtLeastOneWord = false;
        INode wordFoundInDictionary;
        Iterator iter = editDistanceOneArray.iterator();
       int count = 0;
        while(iter.hasNext())
        {
           // count++;
           // System.out.println(count);
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

    private Boolean editDistanceTwoWords()

    {
        //clear edit distance array to be used again in the edit distance methods
        editDistanceOneArray.clear();
        Boolean found = false;
        for(int i = 0; i < editDistanceTwoArray.size(); i ++)
        {
            deletionDistance(editDistanceTwoArray.get(i));

            TranspositionDistance(editDistanceTwoArray.get(i));

            AlterationDistance(editDistanceTwoArray.get(i));

            InsertionDistance(editDistanceTwoArray.get(i));

        }

        found = editDistanceCheck();

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

        int duplicateCount = 0;
        int highestFrequency = 0;
        for (Map.Entry<String,Integer> entry : suggestedWords.entrySet())
        {
            int frequency = entry.getValue();

            if(frequency >= highestFrequency)
            {
                if(frequency == highestFrequency)
                {
                    duplicateCount++;
                }
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
        if(duplicateCount > 0)
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
        editDistanceOneArray.clear();

        if( dictionary.find(inputWord) != null)
        {
            return inputWord;
        }
        //if the word is not found in the dictionary then try to find the closest suggestion
        //using the closest edit distance
        else{

            String suggestedword = "";

            Boolean foundWordInDictionary = false;

            deletionDistance(inputWord);

            TranspositionDistance(inputWord);

            AlterationDistance(inputWord);

            InsertionDistance(inputWord);

            foundWordInDictionary = editDistanceCheck();


            if(foundWordInDictionary == false)
            {
                //use edit distance two to run all words recently created through edit distance methods again.
                //make sure new array list of words are created
                //run new words through edit distance check again.
                editDistanceTwoArray = (ArrayList)editDistanceOneArray.clone();
                foundWordInDictionary = editDistanceTwoWords();


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
