import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Leaf extends Node {
    public Byte symbol;
    public String prefix;

    public Leaf(Byte symbol, Integer weight) {
        super(weight);
        this.symbol = symbol;
    };

    public String toString() {
        return this.symbol + "(" + new String(new byte[] { this.symbol }, StandardCharsets.UTF_8) + ")=" + this.prefix;
    };

    public void fillCodeMap(String character, HashMap<Byte, String> codeMap) {
        this.prefix = character;
        codeMap.put(this.symbol, this.prefix);
    };
};