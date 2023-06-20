package spell;

import java.util.Objects;

public class Trie implements ITrie{
    int nodeCount = 0;
    int totalUniqueWords = 0;

    Node root;

    public Trie()
    {
        nodeCount = 1;
        root = new Node();

    }

    private void addHelper(Node currentNode, String word)
    {
        if(word.length() == 0)
        {
            return;
        }

        char letter = word.charAt(0);

        int index = letter - 'a';

        if(currentNode.getChildren()[index] == null)
        {
            nodeCount++;
            currentNode.getChildren()[index] = new Node();
            currentNode = (Node)currentNode.getChildren()[index];

            word = word.substring(1,word.length());

            if(word.length() == 0)
            {
                if(currentNode.getValue() == 0)
                {

                    totalUniqueWords++;
                }

                currentNode.incrementValue();


            }

            addHelper(currentNode,word);


        }
        else if(currentNode.getChildren()[index] != null)
        {
            currentNode = (Node)currentNode.getChildren()[index];
            word = word.substring(1,word.length());
            if(word.length() == 0)
            {
                if(currentNode.getValue() == 0)
                {

                    totalUniqueWords++;
                }

                currentNode.incrementValue();


            }

            addHelper(currentNode,word);

        }



    }
    @Override
    public void add(String word) {
        word = word.toLowerCase();
        addHelper(root,word);

    }

    private Node findHelper(Node currentNode, String word)
    {
        if(currentNode == null)
        {

            return null;
        }
        else if (currentNode.getValue() >= 1 && word.length() == 0)
        {
            return currentNode;

        }
        else if (currentNode.getValue() == 0 && word.length() == 0)
        {
            return null;
        }
        char letter = word.charAt(0);
        int index = letter - 'a';


        if(currentNode.getChildren()[index] != null)
        {
            currentNode = (Node)currentNode.getChildren()[index];
            word = word.substring(1,word.length());

            return findHelper(currentNode,word);

        }
        else
        {

            return null;
        }


    }
    @Override
    public INode find(String word) {

        return findHelper(root,word);

    }

    @Override
    public int getWordCount() {
        return totalUniqueWords;
    }



    private Boolean equalsHelper(Node currentNode,Node otherNode, Boolean isEqual)
    {
        if(currentNode.getValue() != otherNode.getValue())
        {
            isEqual = false;
            return isEqual;
        }
        for(int i = 0 ; i < currentNode.getChildren().length;i++)
        {
            char letter = (char)('a'+i);

            if(currentNode.getChildren()[i] != null)
            {

                if(otherNode.getChildren()[i] != null)
                {

                    Node newcurrentNode = (Node) currentNode.getChildren()[i];
                    Node newotherNode = (Node) otherNode.getChildren()[i];
                   isEqual = equalsHelper(newcurrentNode,newotherNode,isEqual);

                }
                else if(otherNode.getChildren()[i] == null)
                {
                    isEqual = false;
                }

                if(isEqual == false)
                {
                    return isEqual;
                }

            }
            else if(otherNode.getChildren()[i] != null)
            {
                isEqual = false;
                return isEqual;
            }
        }

        return isEqual;
    }
    @Override
    public boolean equals(Object o) {
        if(o == null)
        {
            return false;
        }
        if(o == this)
        {
            return true;
        }
        if(o.getClass() != this.getClass())
        {
            return false;
        }

        Trie tempObj = (Trie)o;

        if(tempObj.getNodeCount() != this.getNodeCount())
        {
            return false;
        }
        if(tempObj.getWordCount() != this.getWordCount())
        {
            return false;
        }
        Boolean isEqual = true;
        return equalsHelper(this.root,tempObj.root,isEqual);

    }

    private void StringHelper(Node CurrentNode,StringBuilder currentWord, StringBuilder output)
    {
        if(CurrentNode.getValue() >= 1) {
            output.append(currentWord.toString());
            output.append("\n");
        }
            for(int i = 0 ; i < CurrentNode.getChildren().length;i++)
            {
                Node child = (Node)CurrentNode.getChildren()[i];
                if(child != null)
                {

                    char letter = (char)('a'+ i);
                    currentWord.append(letter);
                    StringHelper(child,currentWord,output);
                    currentWord.deleteCharAt(currentWord.length() -1);

                }

            }
    }
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();
        StringHelper(root,currentWord,output);

        return output.toString();
    }
    private int hashHelper(int hash)
    {
        for(int i = 0 ; i < root.getChildren().length;i++)
        {   Node currentNode = (Node)root.getChildren()[i];
            if(currentNode != null)
            {
                hash+=i;
            }

        }

        return hash;
    }
    @Override
    public int hashCode() {
        int hash = totalUniqueWords * nodeCount;
        hash+=hashHelper(hash);

        return hash;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }
}
