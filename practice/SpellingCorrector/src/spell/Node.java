package spell;

public class Node implements INode{
    Node()
    {
        children = new INode[26];
    }
    private int frequencyCount;
    private INode[] children;
    @Override
    public int getValue() {
        return frequencyCount;
    }

    @Override
    public void incrementValue() {
        frequencyCount++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }
}
