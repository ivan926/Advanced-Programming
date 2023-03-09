package spell;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;


public class SpellCorrector implements ISpellCorrector{

//
//    private Trie Dictionary = new Trie();
//    private Boolean AlreadyCalled = false;




    public SpellCorrector() {
       Dictionary = new Trie();
    }

    private Trie Dictionary;
    private Boolean hasBeenCalledAlready = false;
    /**
     * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
     * for generating suggestions.
     * @pre SpellCorrector will have had empty-param constructor called, but dictionary has nothing in it.
     * @param dictionaryFileName the file containing the words to be used
     * @throws IOException If the file cannot be read
     * @post SpellCorrector will have dictionary filled and be ready to suggestSimilarWord any number of times.
     */
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {



        File fileName = new File(dictionaryFileName);

        Scanner input = new Scanner(fileName);

        while(input.hasNext()) {

            String currentWord = input.next();
            Dictionary.add(currentWord);

        }







//        File file = new File(dictionaryFileName);
//
//        Scanner word = new Scanner(file);
//
//
//        while(word.hasNext())
//        {
//           String currentWord = word.next();
//           Dictionary.add(currentWord);
//        }



    }

    /**
     * Suggest a word from the dictionary that most closely matches
     * <code>inputWord</code>.
     * @param inputWord the word we are trying to find or find a suggestion for
     * @return the suggestion or null if there is no similar word in the dictionary
     */
    @Override
    public String suggestSimilarWord(String inputWord) {
        ArrayList<String> wordsCreated = new ArrayList<>();
        inputWord = inputWord.toLowerCase();
        if(Dictionary.find( inputWord) != null) {
            return inputWord;

        }

        deletionEditDistance(wordsCreated,inputWord);

        TranspositionDistance(wordsCreated,inputWord);

        AlterationDistance(wordsCreated,inputWord);

        InsertionDistance(wordsCreated,inputWord);

       String corrected = editChecker(wordsCreated);

        hasBeenCalledAlready = false;

        return corrected;






        //        inputWord = inputWord.toLowerCase();
//        ArrayList EditDistanceOne = new ArrayList();
//
//        if(Dictionary.find(inputWord)!= null)
//        {
//           return inputWord;
//        }
//
//        deletionEditDistance(EditDistanceOne,inputWord);
//
//        TranspositionDistance(EditDistanceOne,inputWord);
//
//        AlterationDistance(EditDistanceOne,inputWord);
//
//        InsertionDistance(EditDistanceOne,inputWord);
//
//        String suggestedWord = editChecker(EditDistanceOne);
//
//        AlreadyCalled = false;
//
//        return suggestedWord;



    }

    private String editDistanceTwoCheck(ArrayList arrayList)
    {
        hasBeenCalledAlready = true;
        ArrayList edit2List = new ArrayList();

        for(int i = 0 ; i < arrayList.size();i++)
        {
            deletionEditDistance(edit2List,arrayList.get(i).toString());
            TranspositionDistance(edit2List,arrayList.get(i).toString());
            AlterationDistance(edit2List,arrayList.get(i).toString());
            InsertionDistance(edit2List,arrayList.get(i).toString());

        }


       return editChecker(edit2List);








//    {   ArrayList edit2List = new ArrayList();
//        AlreadyCalled = true;
//        for(int i = 0; i < arrayList.size();i++)
//        {
//            deletionEditDistance(edit2List, arrayList.get(i).toString());
//
//            TranspositionDistance(edit2List,arrayList.get(i).toString());
//
//            AlterationDistance(edit2List,arrayList.get(i).toString());
//
//            InsertionDistance(edit2List,arrayList.get(i).toString());
//
//        }
//
//        return editChecker(edit2List);
    }


    private String editChecker(ArrayList arrayList)
    {
        Map<String,Integer> map = new TreeMap<>();
        String chosenWord = null;

        Iterator iter = arrayList.iterator();
        while(iter.hasNext()) {


            String currentWord = iter.next().toString();
            INode currentNode = Dictionary.find(currentWord);

            if(currentNode != null) {
                map.put(currentWord,currentNode.getValue());
            }

        }

        if(map.size() == 1) {
            return map.keySet().toArray()[0].toString();
        }
        else if(map.size() > 1) {
            chosenWord = tieBreaker(map);
        }
        else if(map.size() == 0 && !hasBeenCalledAlready){

           chosenWord = editDistanceTwoCheck(arrayList);

        }


        return chosenWord;



//        String guessedWord = null;
//        Map<String,Integer> suggestedWord = new TreeMap<>();
//        Iterator iter = arrayList.iterator();
//
//        while(iter.hasNext())
//        {
//            String currentWord = iter.next().toString();
//            INode currentNode = Dictionary.find(currentWord);
//            if(currentNode != null)
//            {
//                suggestedWord.put(currentWord, currentNode.getValue());
//            }
//
//
//        }
//
//        if(suggestedWord.size() == 1)
//        {
//            guessedWord = suggestedWord.keySet().toArray()[0].toString();
//
//        }
//        else if(suggestedWord.size() > 1)
//        { //implement tie breakers
//
//           guessedWord = tieBreaker(suggestedWord);
//        }
//        else if(suggestedWord.size() == 0 && !AlreadyCalled)
//        {
//            guessedWord = editDistanceTwoCheck(arrayList);
//
//            //call edit distance 2
//        }
//
//
//            return guessedWord;
    }

    private String tieBreaker(Map<String,Integer> map)
    {
        int highest_value = 0;
        ArrayList<String> duplicates = new ArrayList<>();
        for(Map.Entry<String,Integer> entry : map.entrySet())
        {
            int currenValue = entry.getValue();
            if(currenValue > highest_value)
            {
                highest_value = currenValue;
            }


        }

        for(Map.Entry<String,Integer> entry : map.entrySet())
        {
            int currenValue = entry.getValue();
            if(currenValue == highest_value)
            {
                duplicates.add(entry.getKey().toString());
            }


        }

        if(duplicates.size() > 1)
        {
            return FirstAlpha(duplicates);
        }
        else
        {
            return duplicates.get(0);
        }









//
//    String chosenString = null;
//        int duplicate = 0;
//        int highestValue = 0;
//        ArrayList<String> tiedString = new ArrayList<>();
//       for( Map.Entry<String,Integer> entry : map.entrySet())
//       {
//
//         int currentValue = entry.getValue();
//           if(currentValue > highestValue)
//           {
//               highestValue = currentValue;
//           }
//       }
//
//        for( Map.Entry<String,Integer> entry : map.entrySet())
//        {
//
//            int currentValue = entry.getValue();
//            if(currentValue == highestValue)
//            {
//                tiedString.add(entry.getKey().toString());
//            }
//        }
//
//        if(tiedString.size() == 1)
//        {
//            chosenString = tiedString.get(0);
//            return chosenString;
//        }
//        else
//        {
//          return FirstAlpha(tiedString);
//        }


    }

    private String FirstAlpha(ArrayList<String> arrayList)
    {

                Collections.sort(arrayList);
                return arrayList.get(0);





//       Collections.sort(arrayList);
//       return arrayList.get(0);


    }


    private ArrayList<String> deletionEditDistance(ArrayList<String> arrayList, String inputWord)
    {

            char[] charArray = inputWord.toCharArray();
            ArrayList<Character> allCharacters = new ArrayList<>();

            for(char letter : charArray) {

                allCharacters.add(letter);

            }

            StringBuilder str = new StringBuilder();
            ArrayList<Character> tempContainer = new ArrayList<Character>();

            for(int i = 0 ; i < charArray.length; i++) {
                tempContainer = (ArrayList<Character>)allCharacters.clone();
                tempContainer.remove(i);

                for(Character letter : tempContainer) {

                    str.append(letter);

                }

                arrayList.add(str.toString());
                str.setLength(0);

            }



            return arrayList;












//            ArrayList<Character> Setword = new ArrayList<>();
//
//            char[] characters = inputWord.toCharArray();
//
//            for(int i  =0 ; i < characters.length; i++)
//            {
//
//                Setword.add(characters[i]);
//
//            }
//
//            StringBuilder sb = new StringBuilder();
//
//        ArrayList<Character> tempList = new ArrayList<>();
//
//
//            for(int i  =0 ; i < characters.length; i++)
//            {
//                tempList = (ArrayList)Setword.clone();
//                tempList.remove(i);
//
//                for(Character letter : tempList)
//                {
//                    sb.append(letter);
//                }
//
//                arrayList.add(sb.toString());
//                sb.setLength(0);
//
//            }
//
//
//
//        return arrayList;
    }

    private ArrayList TranspositionDistance(ArrayList<String> arrayList, String inputWord)
    {
        char[] arrayChar = inputWord.toCharArray();

        for(int i = 0 ; i< arrayChar.length-1;i++) {

            char swap;
            arrayChar = inputWord.toCharArray();
            swap = arrayChar[i];
            arrayChar[i] = arrayChar[i+1];
            arrayChar[i+1] = swap;

           arrayList.add(String.valueOf(arrayChar));


        }

        return arrayList;









//        String temp = inputWord;
//
//        char[] arrayOfChar = inputWord.toCharArray();
//
//        for(int i  =0 ; i < arrayOfChar.length - 1;i++) {
//            char swap;
//           // temp = inputWord;
//
//            arrayOfChar = inputWord.toCharArray();
//
//            swap = arrayOfChar[i];
//
//            arrayOfChar[i] = arrayOfChar[i+1];
//
//            arrayOfChar[i+1] = swap;
//
//            arrayList.add(String.valueOf(arrayOfChar));


   //     }

 //       return arrayList;






    }




    private ArrayList AlterationDistance(ArrayList<String> arrayList,String inputWord )
    {

            char[] alpha = new char[26];
            char[] chararray = inputWord.toCharArray();

            char letter;
            for(int i  = 0; i < chararray.length;i++) {
                chararray = inputWord.toCharArray();
                letter = chararray[i];
                for(int j = 0; j < alpha.length;j++) {

                    if(letter != ((char)('a'+j))) {
                        chararray[i] = (char)('a'+j);
                        arrayList.add(String.valueOf(chararray));
                    }
                }
            }

            return arrayList;














//
//
//        char[] alphabet = new char[26];
//
//        String tempWord;
//
//
//        char[] arrayOfChar = inputWord.toCharArray();
//
//        char letter = 0;
//
//        for(int i = 0; i < arrayOfChar.length;i++)
//        {
//            arrayOfChar = inputWord.toCharArray();
//            letter = arrayOfChar[i];
//
//            for(int j = 0; j < alphabet.length; j++)
//            {
//                if(letter != ((char)('a' + j)) )
//                {
//                    arrayOfChar[i] = (char)('a' + j);
//                    arrayList.add(String.valueOf(arrayOfChar));
//                }
//
//
//
//            }
//
//
//        }
//
//
//
//
//
//
//
//        return arrayList;
    }



    private ArrayList InsertionDistance(ArrayList<String> arrayList,String inputWord)
    {



            char[] alpha = new char[26];

            StringBuffer buff = new StringBuffer();

            for(int i = 0 ; i < inputWord.length() + 1; i++) {

                for(int j = 0 ; j < alpha.length;j++)
                {   char letter = (char)('a' + j);
                    buff.append(inputWord);
                    buff.insert(i,letter);
                    arrayList.add(buff.toString());

                    buff.setLength(0);
                }

            }



        return arrayList;










//        char[] alphabet = new char[26];
//        String tempWord = inputWord;
//
//      //  char[] arrayOfChar = inputWord.toCharArray();
//
//        char letter = 0;
//
//        StringBuffer str = new StringBuffer();
//
//        for(int i = 0; i < inputWord.length() + 1 ;i++)
//        {
//
//            // arrayOfChar = inputWord.toCharArray();
//            //tempWord = inputWord;
//            for (int j = 0; j < alphabet.length;j++)
//            {
//                letter =  (char) ('a' + j);
//
//                str.append(inputWord);
//
//                str.insert(i,letter);
//
////                    arrayOfChar[i] = (char) ('a' + j);
//                //tempWord = String.valueOf(arrayOfChar);
//
//                tempWord = str.toString();
//
//
//                //  System.out.println(tempWord);
//                arrayList.add(tempWord);
//
//                str.delete(0,str.length());
//            }
  //      }


//        return arrayList;

    }



}



