package spell;

public class Trie implements ITrie {
    public Trie()
    {
        Root = new Node();
        nodeCount++;
    }
    private int wordCount = 0;
    private int nodeCount = 0;
    private Node Root;
    private void addHelper(Node currentNode, String word)
    {
        if(word.length() == 0)
        {
            return;
        }


        char letter = word.charAt(0);

        int index = letter - 'a';

        if(currentNode.getChildren()[index] == null && word.length() != 0)
        {
            nodeCount++;
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

            addHelper(currentNode,word);



        }
        else if(currentNode.getChildren()[index] != null  && word.length() != 0)
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
            addHelper(currentNode,word);

        }



    }



    @Override
    public void add(String word) {
        word = word.toLowerCase();
        addHelper(Root,word);

    }

    private INode findHelper(Node currentNode, String word)
    {
        if(word.length() == 0)
        {
            return null;
        }

        char letter = word.charAt(0);

        int index = letter - 'a';

        if(currentNode.getChildren()[index] != null && word.length() != 0)
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

          currentNode = (Node)findHelper(currentNode,word);



        }
        else if(currentNode.getChildren()[index] == null)
        {
            return null;

        }





        return currentNode;

    }

    @Override
    public INode find(String word) {
        word = word.toLowerCase();

       return findHelper(Root,word);
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode = wordCount * nodeCount;

        for(int i = 0 ; i < Root.getChildren().length;i++)
        {
            if(Root.getChildren()[i] != null)
            {
                hashCode+=i;
            }

        }

        return hashCode;

    }
    public boolean equalsHelper(Node currentNode,Node otherNode) {
        Boolean isEqual = true;
        if(currentNode.getValue() != otherNode.getValue())
        {
            return false;
        }

        for(int i = 0 ; i < currentNode.getChildren().length; i++)
        {
            Node thisNode = (Node)currentNode.getChildren()[i];
            Node otherOBJ = (Node)otherNode.getChildren()[i];

            if(thisNode != null)
            {
                if(otherOBJ != null)
                {
                    isEqual = equalsHelper(thisNode,otherOBJ);

                }
                else if(otherOBJ == null)
                {
                    return false;
                }


                if(isEqual == false)
                {
                    return isEqual;
                }

            }
            else if(otherOBJ != null)
            {

                return false;
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
        if(obj.getClass() != this.getClass())
        {
            return false;
        }
        Trie otherNode = (Trie)obj;
        if(otherNode.wordCount != this.wordCount)
        {
            return false;
        }
        if(otherNode.nodeCount != this.nodeCount)
        {
            return false;
        }
        return equalsHelper(this.Root, otherNode.Root);
    }
    private void toStringHelper(Node currentNode, StringBuilder currentWord, StringBuilder output)
    {
        if(currentNode.getValue() > 0)
        {
            output.append(currentWord.toString());
            output.append("\n");

        }


        for(int i = 0; i < currentNode.getChildren().length; i++)
        {
            Node child = (Node)currentNode.getChildren()[i];

            if(child != null)
            {
                char letter = (char)('a' + i);
                currentWord.append(letter);
                toStringHelper(child,currentWord,output);
                currentWord.deleteCharAt(currentWord.length()-1);
            }

        }

    }
    @Override
    public String toString() {

       StringBuilder output = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();
        toStringHelper(Root,currentWord,output);
        return output.toString();
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }
}
