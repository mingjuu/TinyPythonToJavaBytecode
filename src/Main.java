import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception{
        tinyPythonLexer lexer = new tinyPythonLexer(CharStreams.fromFileName("test.tpy"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tinyPythonParser parser = new tinyPythonParser(tokens);
        ParseTree tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();
        tinyPythonToJasmin toJavaBytecode = new tinyPythonToJasmin();
        walker.walk(toJavaBytecode, tree);

        try (PrintWriter writer = new PrintWriter(new FileWriter("Test.j"))) {
            writer.print(toJavaBytecode.fffffinal);
        }
    }
}