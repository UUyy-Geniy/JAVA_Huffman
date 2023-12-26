import java.util.HashMap;

public class ArchiveData {
    String compressedData;
    String originalFileName;
    HashMap<String, Byte> recoveryMap;
    int originalLength;
    int dataOffset;
};