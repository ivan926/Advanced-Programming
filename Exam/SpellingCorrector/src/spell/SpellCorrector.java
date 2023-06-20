package spell;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {


    //Set<String> suggestedWords = new HashSet<>();
    Map<String,Integer> suggestedWords = new HashMap<>();
    Trie Dictionary = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File dictionary = new File(dictionaryFileName);
        Scanner scanner = new Scanner(dictionary);
        suggestedWords.clear();

        while(scanner.hasNext())
        {   String currentWord = scanner.next();

            Dictionary.add(currentWord);
        }


    }

    private ArrayList<String> deletionDistance(String word,ArrayList<String> editDistanceOne)
    {
        String tempString = word;
        char[] arrayOfChar = word.toCharArray();
        ArrayList<Character> arrayListOfChars = new ArrayList<>();

        for(int i = 0 ; i < arrayOfChar.length;i++)
        {
            arrayListOfChars.add(arrayOfChar[i]);
        }

        StringBuilder str = new StringBuilder();
        ArrayList<Character> tempArray = new ArrayList<>();

        for(int i = 0 ; i < arrayListOfChars.size();i++)
        {
            tempArray = (ArrayList<Character>) arrayListOfChars.clone();
            tempArray.remove(i);
            for(Character letter : tempArray)
            {
                str.append(letter);


            }
             tempString = str.toString();
            editDistanceOne.add(tempString);

            str.setLength(0);

        }

        return editDistanceOne;


    }

    private ArrayList<String> transpositionDistance(String word, ArrayList<String> editDistanceOneArray)
    {
        char[] arrayOfChar = word.toCharArray();

        for(int i = 0 ; i < arrayOfChar.length-1;i++)
        {
            char swap;
            arrayOfChar = word.toCharArray();

            swap =  arrayOfChar[i];
            arrayOfChar[i] =  arrayOfChar[i+1];
            arrayOfChar[i+1] = swap;
            System.out.println(String.valueOf(arrayOfChar));
           editDistanceOneArray.add(String.valueOf(arrayOfChar));



        }


        return editDistanceOneArray;
    }

    private ArrayList<String> alterationDistance(String word,ArrayList<String> editDistanceOneArray)
    {
        char[] alpahbet = new char[26];
        char[] arrayOfChars = word.toCharArray();

        for(int i = 0 ; i < arrayOfChars.length;i++)
        {   arrayOfChars = word.toCharArray();
            char restrictedLetter = arrayOfChars[i];
            for(int j = 0; j < alpahbet.length;j++)
            {
                char letter = (char)('a'+j);
                if(letter != restrictedLetter)
                {
                    arrayOfChars[i] = letter;
                    editDistanceOneArray.add(String.valueOf(arrayOfChars));

                }

            }


        }

        return editDistanceOneArray;

    }
    private ArrayList<String> insertionDistance(String word, ArrayList<String> editDistanceOneArray)
    {
        char[] alphabet = new char[26];

        char[] arrayOfChar = word.toCharArray();

        StringBuffer str = new StringBuffer();

        for(int i = 0 ; i < arrayOfChar.length+1;i++)
        {
            for(int j = 0 ; j < alphabet.length;j++)
            {   char letter = (char)('a'+j);
                str.append(word);
                str.insert(i,letter);

                editDistanceOneArray.add(str.toString());
                str.setLength(0);

            }



        }

        return editDistanceOneArray;
    }

    private Boolean editDistanceCheck(ArrayList<String> editDistanceArray)
    {
        Node currentNode;
        Boolean foundAtLeastOneWord = false;
        suggestedWords.clear();

        Iterator iter = editDistanceArray.iterator();

        while(iter.hasNext())
        {
            String currentWord = iter.next().toString();

            currentNode = (Node)Dictionary.find(currentWord);

            if(currentNode != null)
            {
                foundAtLeastOneWord = true;
                suggestedWords.put(currentWord,currentNode.getValue());

            }

        }

        return foundAtLeastOneWord;

    }



    private Boolean editDistanceCheckTwo(ArrayList<String> editDistanceTwoCheck,ArrayList<String> editDistanceOneArray )
    {
        editDistanceOneArray.clear();
        Boolean foundAtLeastOneWord = false;
        for(int i = 0 ; i < editDistanceTwoCheck.size();i++)
        {

            deletionDistance(editDistanceTwoCheck.get(i),editDistanceOneArray);

            transpositionDistance(editDistanceTwoCheck.get(i),editDistanceOneArray);

            alterationDistance(editDistanceTwoCheck.get(i),editDistanceOneArray);

            insertionDistance(editDistanceTwoCheck.get(i),editDistanceOneArray);

        }

        foundAtLeastOneWord = editDistanceCheck(editDistanceOneArray);
        return foundAtLeastOneWord;

    }

    private String findSuggestedWord()
    {

        int max = 0;
        ArrayList<String> MostFrequentWords = new ArrayList<>();
        for(Map.Entry<String,Integer> entry : suggestedWords.entrySet())
        {
            int currentValue = entry.getValue();
            if(currentValue > max)
            {
                max = currentValue;
            }

        }

        for(Map.Entry<String,Integer> entry : suggestedWords.entrySet())
        {
            int currentValue = entry.getValue();
            if(currentValue == max)
            {
                MostFrequentWords.add(entry.getKey());
            }

        }

        String suggestedWord = null;

        if(MostFrequentWords.size() == 1)
        {
            suggestedWord = MostFrequentWords.get(0);
        }
        else if(MostFrequentWords.size() > 1)
        {
            int difference = 0;
            for(int i = 0 ; i < MostFrequentWords.size()-1;i++)
            {
                String tempWord = MostFrequentWords.get(i);

                difference = tempWord.compareTo(MostFrequentWords.get(i+1));

                if(difference < 0)
                {
                    suggestedWord = tempWord;
                }
                else {
                    suggestedWord = MostFrequentWords.get(i+1);
                }

            }
        }
        else {
            suggestedWord = null;
        }


        return suggestedWord;
    }



    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        ArrayList<String> editDistanceOneArray = new ArrayList<>();
        ArrayList<String> editDistanceTwoArray = new ArrayList<>();


        if(Dictionary.find(inputWord) != null)
        {

            return inputWord;
        }
        else
        {
            Boolean foundWord = false;
            String suggestedWord = null;

            deletionDistance(inputWord,editDistanceOneArray);

            transpositionDistance(inputWord,editDistanceOneArray);

            alterationDistance(inputWord,editDistanceOneArray);

            insertionDistance(inputWord,editDistanceOneArray);

            foundWord = editDistanceCheck(editDistanceOneArray);

            if(!foundWord)
            {
                editDistanceTwoArray = (ArrayList<String>) editDistanceOneArray.clone();

                foundWord = editDistanceCheckTwo(editDistanceTwoArray,editDistanceOneArray);
                if(foundWord)
                {
                    suggestedWord = findSuggestedWord();

                }
                else
                {
                    suggestedWord = null;
                }



            }
            else if(foundWord)
            {
                suggestedWord = findSuggestedWord();
            }




            return suggestedWord;


        }





    }
}
