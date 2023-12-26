import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
    HashMap<Byte, String> codeMap = new HashMap<>();

    public byte[] encode(byte[] data) {
        HashMap<Byte, Integer> weightMap = calculateWeightMap(data);
        PriorityQueue<Node> weightQueue = buildWeightQueue(weightMap);
        Node root = buildHuffmanTree(weightQueue);

        root.fillCodeMap("", this.codeMap);

        String compressed = compressData(data);
        compressed = padWithZeros(compressed);

        return convertToByteArray(compressed);
    }

    public byte[] decode(String compressed, HashMap<String, Byte> recoveryMap) {
        ArrayList<Byte> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (int index = 0; index < compressed.length(); index++) {
            current.append(compressed.charAt(index));

            if (recoveryMap.containsKey(current.toString())) {
                result.add(recoveryMap.get(current.toString()));
                current.setLength(0);
            }
        }

        return convertListToArray(result);
    }

    private String padWithZeros(String compressed) {
        int delta = 8 - compressed.length() % 8;

        for (int counter = 0; counter < delta; counter++) {
            compressed += "0";
        }

        return String.format("%8s", Integer.toBinaryString(delta & 0xff)).replace(" ", "0") + compressed;
    }

    private Node buildHuffmanTree(PriorityQueue<Node> queue) {
        while (queue.size() > 1) {
            Node node1 = queue.poll();
            Node node2 = queue.poll();

            Node node = new Node(node1.frequency + node2.frequency);
            node.right = node1;
            node.left = node2;

            queue.add(node);
        }

        return queue.poll();
    }

    private PriorityQueue<Node> buildWeightQueue(HashMap<Byte, Integer> map) {
        PriorityQueue<Node> queue = new PriorityQueue<>();

        for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
            Byte symbol = entry.getKey();
            Integer weight = entry.getValue();
            Node node = new Leaf(symbol, weight);

            queue.add(node);
        }

        return queue;
    }

    private HashMap<Byte, Integer> calculateWeightMap(byte[] data) {
        HashMap<Byte, Integer> map = new HashMap<>();

        for (byte b : data) {
            map.merge(b, 1, Integer::sum);
        }

        return map;
    }

    private String compressData(byte[] data) {
        StringBuilder compressed = new StringBuilder();

        for (byte b : data) {
            compressed.append(codeMap.get(b));
        }

        return compressed.toString();
    }

    private byte[] convertToByteArray(String compressed) {
        StringBuilder compressedString = new StringBuilder(compressed);
        byte[] result = new byte[compressedString.length() / 8];

        for (int index = 0; index < result.length; index++) {
            result[index] = (byte) Integer.parseInt(
                    compressedString.substring(index * 8, (index + 1) * 8), 2
            );
        }

        return result;
    }

    private byte[] convertListToArray(ArrayList<Byte> list) {
        byte[] result = new byte[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        return result;
    }
}