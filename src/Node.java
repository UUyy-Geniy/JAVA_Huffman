import java.util.HashMap;

public class Node implements Comparable<Node> {
    public Integer frequency;
    public Node left;
    public Node right;

    public Node(Integer frequency) {
        this.frequency = frequency;
    };

    @Override
    public int compareTo(Node n) {
        return this.frequency.compareTo(n.frequency);
    };

    public String toString() {
        return "w=" + this.frequency;
    };


    public void fillCodeMap(String character, HashMap<Byte, String> codeMap) {
        this.left.fillCodeMap(character + "0", codeMap);
        this.right.fillCodeMap(character + "1", codeMap);
    };
};