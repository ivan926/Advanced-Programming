package spell;

import java.util.Locale;

public class Trie implements ITrie{
    private  Node Root;
    private int wordCount;
    private int NodeCount;
    public Trie()
    {
        Root = new Node();
        NodeCount++;
    }
    /**
     * Adds the specified word to the trie (if necessary) and increments the word's frequency count.
     *
     * @param word the word being added to the trie
     */

    @Override
    public void add(String word) {
        word = word.toLowerCase();

        addHelper(word,this.Root);
    }


    private void addHelper(String word, Node currentNode)
    {
        if(word.length() == 0)
        {
            return;
        }

        char letter = word.charAt(0);

        int index = letter - 'a';

        if(currentNode.getChildren()[index] == null && word.length()!= 0 )
        {
            NodeCount++;
            currentNode.getChildren()[index] = new Node();
            currentNode = (Node) currentNode.getChildren()[index];
            word = word.substring(1,word.length());
            if(word.length() == 0)
            {
                if(currentNode.getValue() == 0)
                {
                    wordCount++;
                }
                currentNode.incrementValue();
            }

            addHelper(word,currentNode);

        }
        else if(currentNode.getChildren()[index] != null && word.length()!= 0 )
        {
            currentNode = (Node) currentNode.getChildren()[index];
            word = word.substring(1,word.length());

            if(word.length() == 0)
            {
                if(currentNode.getValue() == 0)
                {
                    wordCount++;
                }
                currentNode.incrementValue();
            }

            addHelper(word,currentNode);


        }


    }
    /**
     * Searches the trie for the specified word.
     *
     * @param word the word being searched for.
     *
     * @return a reference to the trie node that represents the word,
     * 			or null if the word is not in the trie
     */



    @Override
    public INode find(String word) {

  word = word.toLowerCase();

        return findHelper(word,Root);

    }


    private Node findHelper(String word, Node currentNode)
    {
        if(word.length() == 0)
        {   return null;
        }

        char letter = word.charAt(0);
        int index = letter - 'a';

        if(currentNode.getChildren()[index] != null)
        {
            currentNode = (Node)currentNode.getChildren()[index];

            word = word.substring(1,word.length());

            if(word.length() == 0)
            {
                if(currentNode.getValue() > 0)
                {
                    return currentNode;
                }
                else
                {
                    return null;
                }
            }

           currentNode = findHelper(word,currentNode);



        }
        else if(currentNode.getChildren()[index]  == null )
        {
            return null;
        }

     return currentNode;
    }




    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return NodeCount;
    }


 //if(currentNode.getValue() > 0) {
//            output.append(currWord.toString());
//            output.append('\n');
//        }
//
//            for(int i  =0 ; i < currentNode.getChildren().length;i++)
//            {
//                Node child = (Node)currentNode.getChildren()[i];
//
//                if(child != null)
//                {
//                    char letter = (char)('a' + i);
//                    currWord.append(letter);
//                    StringHelper(child,currWord,output);
//                    currWord.deleteCharAt(currWord.length() - 1);
//
//
//                }
//
//            }
//
//


    private void StringHelper(Node currentNode,StringBuilder currWord, StringBuilder output)
    {
        if(currentNode.getValue() > 0)
        {
            output.append(currWord.toString());
            output.append('\n');
        }

        for(int i = 0; i < currentNode.getChildren().length; i++)
        {
            Node child = (Node)currentNode.getChildren()[i];
            if(child != null)
            {
                char letter = (char)('a'+i);
                currWord.append(letter);
                StringHelper(child,currWord,output);
                currWord.deleteCharAt(currWord.length() -1);



            }

        }

//
    }


    //        StringBuilder currentWor = new StringBuilder();
//        StringBuilder output = new StringBuilder();
//        StringHelper(Root,currentWor,output);
//
//        return output.toString();

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();

        StringHelper(this.Root,currentWord,output);

        return output.toString();

    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode = wordCount * NodeCount;
        hashCode *= 31;

        for(int i = 0 ;i < this.Root.getChildren().length; i++)
        {
            if(Root.getChildren()[i] != null) {
                hashCode +=i;
            }
        }

        return hashCode;

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
        if(obj.getClass() != this.getClass())
        {
            return false;
        }
        Trie temp = (Trie)obj;

        if(temp.NodeCount != this.NodeCount)
        {
            return false;
        }
        if(temp.wordCount != this.wordCount)
        {
            return false;
        }



      return equalsHelper(this.Root ,temp.Root);


    }


   private Boolean equalsHelper(Node thisRoot, Node comparedRoot)
    {
        Boolean isEqual = true;

        if(thisRoot.getValue() != comparedRoot.getValue())
        {
            return false;
        }

        for(int i = 0 ; i < thisRoot.getChildren().length;i++)
        {
            char letter = (char)('a' + i);
            Node childNodeOfThis = (Node)thisRoot.getChildren()[i];
            Node childNodeOfThat = (Node)comparedRoot.getChildren()[i];

            if(childNodeOfThis != null)
            {

                if(childNodeOfThat != null)
                {


                   isEqual = equalsHelper(childNodeOfThis,childNodeOfThat);


                }
                else if(childNodeOfThat == null)
                {
                    isEqual = false;

                }

            }
            else if(childNodeOfThat != null)
            {
                isEqual = false;
                return isEqual;
            }

            if(isEqual == false)
            {
                return isEqual;
            }



        }
        return isEqual;
    }


}
