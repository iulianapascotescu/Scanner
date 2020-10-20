import utils.Pair;
import utils.Parser;

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
        String p = Files.readString(Path.of("C:\\Users\\iulia\\Desktop\\Anul III\\FLCT\\Lab2\\src\\p2"), StandardCharsets.US_ASCII);
        String string = Files.readString(Path.of("C:\\Users\\iulia\\Desktop\\Anul III\\FLCT\\Lab2\\src\\token.in"), StandardCharsets.US_ASCII);
        StringTokenizer st = new StringTokenizer(string, " ");
        List<String> keywords = new ArrayList<>();
        while (st.hasMoreTokens())
            keywords.add(st.nextToken());

        Parser parser = new Parser(p, keywords);
        parser.run();
        if (parser.getError().size() > 0)
            System.out.println(parser.getError());
        else {
            FileWriter pif = new FileWriter("PIF.out");
            for (Pair pair : parser.getPIF()) {
                pif.write(pair.toString());
                pif.write("\r\n");
            }
            pif.close();
            FileWriter symbolTable = new FileWriter("ST.out");
            symbolTable.write(parser.getSymbolTable().toString());
            symbolTable.close();
        }

        //String p1 = "function f1{\r\nint nr1;\r\nint nr2;\r\nint nr3;\r\nread nr1;\r\nread nr2;\r\nread nr3;\r\nif(nr1 > nr2 and nr1 > nr3)\r\n{\r\nwrite nr1;\r\n}\r\nelse { if(nr2 > nr1 and nr2 > 3)\r\n{\r\nwrite nr2;\r\n}\r\n}\r\nwrite nr3;\r\n} ";
        //String p2 = "function f2{\r\nint nr;\r\nread nr;\r\nif (nr<2)\r\n{\r\nwrite false;\r\n}\r\nint i;\r\ni=2;\r\nwhile(i<nr)\r\n{\r\nif(nr%i==0)\r\n{\r\nwrite false;\r\n}\r\ni=i+1;\r\n}\r\nwrite true;\r\n} ";
        //String p3 = "function f3{\r\nint n;\r\nread n;\r\nint v[100];\r\nint i;\r\ni=0;\r\nint sum;\r\nsum=0;\r\nwhile(i<n)\r\n{\r\nread v[i];\r\nsum=sum+v[i];\r\nsum=sum+v[i];\r\ni=i+1;\r\n}\r\nwrite sum;\r\n} ";
        //String p1err = "function f4{\r\nint 1nr;\r\nint nr2#;\r\nint nr3;\r\nread nr1;\r\nread nr2;\r\nread nr3;\r\nif(nr1 > nr2 and nr1 > nr3)\r\n{\r\nwrite nr1;\r\n}\r\nelse { if(nr2 > nr1 and nr2 > 3)\r\n{\r\nwrite nr2;\r\n}\r\n}\r\nwrite nr3;\r\n} ";
    }
}
