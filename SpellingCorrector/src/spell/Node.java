package spell;

public class Node implements INode{


    private int count;
    private Node[] children;

    public Node(){
        children = new Node[26];
        count = 0;
    }


    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public Node[] getChildren() {
        return children;
    }



}
