import utils.Pair;
import utils.Scanner;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        String p = Files.readString(Path.of("C:\\Users\\iulia\\Desktop\\Anul III\\FLCT\\Lab2\\src\\p1"), StandardCharsets.US_ASCII);
        String string = Files.readString(Path.of("C:\\Users\\iulia\\Desktop\\Anul III\\FLCT\\Lab2\\src\\token.in"), StandardCharsets.US_ASCII);
        StringTokenizer st = new StringTokenizer(string, " ");
        List<String> keywords = new ArrayList<>();
        while (st.hasMoreTokens())
            keywords.add(st.nextToken());

        Scanner scanner = new Scanner(p, keywords);
        scanner.run();
        if (scanner.getError().size() > 0)
            System.out.println(scanner.getError());
        else {
            System.out.println("Lexically correct");
            FileWriter pif = new FileWriter("PIF.out");
            pif.write("token positionInST\n");
            for (Pair pair : scanner.getPIF()) {
                pif.write(pair.toString());
                pif.write("\r\n");
            }
            pif.close();
            FileWriter symbolTable = new FileWriter("ST.out");
            symbolTable.write("The Symbol Table is represented as a Hashtable with Separate Chaining\n");
            symbolTable.write("token positionInST\n");
            symbolTable.write(scanner.getSymbolTable().toString());
            symbolTable.close();
        }
    }
}
