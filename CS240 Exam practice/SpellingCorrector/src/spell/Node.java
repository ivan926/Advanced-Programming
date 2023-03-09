package spell;

import java.util.ArrayList;

public class Node implements INode{
    Node[] children;

    Node()
    {
         children = new Node[26];
    }

    private int frequencyCount = 0;
    /**
     * Returns the frequency count for the word represented by the node.
     *
     * @return the frequency count for the word represented by the node.
     */
    @Override
    public int getValue() {
        return frequencyCount;
    }

    /**
     * Increments the frequency count for the word represented by the node.
     */

    @Override
    public void incrementValue() {
        frequencyCount++;
    }
    /**
     * Returns the child nodes of this node.
     *
     * @return the child nodes.
     */

    @Override
    public INode[] getChildren() {
        return children;
    }
}
