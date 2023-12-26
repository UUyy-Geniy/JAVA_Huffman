import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String helpString = "Arguments format: <command> <file path>," +
                " where command is encode, decode, info";
        if (args.length < 2)
        {
            System.out.println("Incorrect request in Huffman/src folder");
            System.out.println("Use: java Main <encode/decode/info> <inputFile>");
            return;
        }
        Huffman h = new Huffman();

        String[] nargs = new String[args.length];
        System.arraycopy(args, 0, nargs, 0, args.length);

        String[] commands = new String[] { "encode", "decode", "info" };

        switch (nargs[0]) {
            case "decode" -> {

                ArchiveData archiveData = FileManager.readArchive(args[1]);

                byte[] result = h.decode(archiveData.compressedData, archiveData.recoveryMap);

                FileManager.writeFile(result, archiveData.originalFileName);
            }
            case "encode" -> {

                byte[] data = FileManager.readFile(nargs[1]);
                byte[] result = h.encode(data);
                File f = new File(nargs[1]);
                String originalFileName = f.getName();
                String outputFilePath = originalFileName.substring(0, originalFileName.lastIndexOf('.')) + ".arc";

                FileManager.writeArchive(result, h.codeMap, originalFileName, outputFilePath);
            }
            case "info" -> {

                ArchiveData archiveData = FileManager.readArchive(args[1]);
                byte[] result = h.decode(archiveData.compressedData, archiveData.recoveryMap);

                FileManager.test(archiveData, result);
            }
            default ->
                    System.out.println("Invalid mode. Use 'encode', 'decode'," +
                            " or 'info'");
        }
    }

};