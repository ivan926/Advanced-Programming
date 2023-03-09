package spell;

import java.util.Locale;
import java.util.Random;

public class Trie implements ITrie{
    private Node Root = null;

    public Trie()
    {
        Root = new Node();
        nodeCount = 1;
    }

    private int totalWordCount;
    private int nodeCount;



    public void addHelper(String word, Node currentNode){

        if(word.length() == 0 )
        {
            return;
        }

        char letter = word.charAt(0);



       // System.out.println(letter);
        int index = letter - 'a';

        //if no node child exist create below
        if(currentNode.getChildren()[index] == null && (word.length() != 0) )
        {
            currentNode.getChildren()[index] = new Node();
            nodeCount++;

           currentNode = currentNode.getChildren()[index];

            word = word.substring(1,word.length());

            if(word.length() == 0)
            {
                if(currentNode.getValue() == 0) {
                    totalWordCount++;
                }

                currentNode.incrementValue();


                //System.out.println( currentNode.getValue());
            }

           addHelper(word,currentNode);

        }//else go to next node if letter already exist
        else if(currentNode.getChildren()[index] != null)
        {   //if we have reached the end of our word and have found it increment and return;
            word = word.substring(1,word.length());

            currentNode = currentNode.getChildren()[index];

            if(word.length() == 0)
            {
                if(currentNode.getValue() == 0) {
                    totalWordCount++;
                }
                currentNode.incrementValue();

              // System.out.println( currentNode.getValue());
            }

            addHelper(word,currentNode);

        }









    }


    @Override
    public void add(String word) {
       // System.out.println("I am in the function");
        word = word.toLowerCase();
        addHelper(word,Root);
    }


    public INode findHelper(String currentWord, INode currentNode){

        if(currentNode == null)
        {
            return null;
        }

        if(currentWord.length() == 0 && currentNode.getValue() >= 1)
        {
            return currentNode;
        }
        else if(currentWord.length() == 0 && currentNode.getValue() == 0)
        {
            return null;
        }

        char letter = currentWord.charAt(0);
        //shortening the word by 1 each



       //computing the index of the child array for the letter
        int index =  letter - 'a';



        //if the letter is found keep recursing below
        if(currentNode.getChildren()[index] != null)
        {
            //if we made it to the last letter of the word and it is not null return the node


            currentNode = currentNode.getChildren()[index];

            currentWord = currentWord.substring(1,currentWord.length());

            return findHelper(currentWord,currentNode);

        }
        else{

            return null;
        }


    }

    @Override
    public INode find(String word) {
        word = word.toLowerCase();

        return findHelper(word, Root);
    }

    public void traversalOfTrie(Node currentNode)
    {
        if(currentNode.getValue() > 0)
        {
            totalWordCount++;
        }

        for(Node childNode : currentNode.getChildren())
        {
            if(childNode != null)
            {
                traversalOfTrie(childNode);
            }


        }



    }


    @Override
    public int getWordCount() {


       // getWogetWordCountHelperrdCountHelper(Root);

        return totalWordCount;
    }

    private void traversalForNodeCount(Node currentNode)
    {
//        if(currentNode.getValue() > 0)
//        {
//            totalWordCount++;
//        }

        for(Node childNode : currentNode.getChildren())
        {
            if(childNode != null)
            {   nodeCount++;
                traversalForNodeCount(childNode);
            }


        }



    }


    private Boolean traversalForEquals(Node copyObj, Node thisObj, boolean isEqual)
    {

        if(copyObj.getValue() != thisObj.getValue())
        {   isEqual = false;
            return isEqual;

        }


        for(int i=0 ; i < thisObj.getChildren().length; i++ )
        {
            int letter = 'a' + i;
            // System.out.println(letter);
            char let = (char)letter;
            //System.out.println(let);
            if (thisObj.getChildren()[i] != null)
            {
                if(copyObj.getChildren()[i] != null) {

                    Node tempNodeCopyObj = copyObj.getChildren()[i];
                    Node tempNodeThisObj = thisObj.getChildren()[i];
                    isEqual = traversalForEquals(tempNodeCopyObj,tempNodeThisObj,isEqual);
                }
                else if(copyObj.getChildren()[i] == null)
                {
                    isEqual = false;

                    return isEqual;
                }

                if(isEqual == false)
                {
                    return isEqual;
                }



            }
            else if(copyObj.getChildren()[i] != null)
            {
                return false;

            }


        }


        return isEqual;

    }

    private boolean equalsHelper(Node copyObj ,Node thisObj)
    {
        boolean isEqual = true;
        if( copyObj.getValue() != thisObj.getValue())
        {
            isEqual = false;
            return isEqual;
        }

        for(int i=0 ; i < thisObj.getChildren().length; i++ )
        {
            int letter = 'a' + i;
            // System.out.println(letter);
            char let = (char)letter;
            //System.out.println(let);
            if (thisObj.getChildren()[i] != null)
            {
                if(copyObj.getChildren()[i] != null) {

                    Node tempNodeCopyObj = copyObj.getChildren()[i];
                    Node tempNodeThisObj = thisObj.getChildren()[i];
                    isEqual = traversalForEquals(tempNodeCopyObj,tempNodeThisObj,isEqual);
                }
                else if(copyObj.getChildren()[i] == null)
                {
                    isEqual = false;

                    return isEqual;
                }
                //escape ladder if one of the children do not match or frequency of child
                if(isEqual == false)
                {
                    return isEqual;
                }



            }
            else if(copyObj.getChildren()[i] != null)
            {
                isEqual = false;

                return isEqual;
            }




        }

        return isEqual;

    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
        {
            return false;
        }
        if(obj == this)
        {

            return true;
        }
        if(obj.getClass() != this.getClass() )
        {
            return false;
        }
        Trie tempObj = (Trie)obj;
        if(tempObj.getWordCount() != this.getWordCount() )
        {
            return false;
        }
        if(tempObj.getNodeCount() != this.getNodeCount() )
        {
            return false;
        }

        return equalsHelper(tempObj.Root,this.Root);


    }

    private void toStringHelper(Node n, StringBuilder curWord, StringBuilder output){
        if (n.getValue() > 0) {
            output.append(curWord.toString());
            output.append("\n");
        }

        for(int i = 0; i < n.getChildren().length; i++) {
            Node child = n.getChildren()[i];
            if (child != null) {
                char letter = (char) ('a' + i);
                curWord.append(letter);
                toStringHelper(child, curWord, output);
                curWord.deleteCharAt(curWord.length() - 1);
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toStringHelper(Root, curWord, output);

        return output.toString();

    }

    @Override
    public int getNodeCount() {
        Node currentNode = Root;

        //getNodeCounterHelper(currentNode);

        return nodeCount;
    }

    private int hashHelper(int hash)
    {
        int totalSumOfIndex = 0;
        for(int i = 0; i < Root.getChildren().length;i++)
        {
            if(Root.getChildren()[i] != null)
            {
                hash += i;
            }

        }

        System.out.println(hash);

        return hash;


    }

    @Override
    public int hashCode() {
      //  Random rand = new Random();
       int hash;

       hash = totalWordCount * nodeCount;

        hash += hashHelper(hash);

        return hash;

    }

}
