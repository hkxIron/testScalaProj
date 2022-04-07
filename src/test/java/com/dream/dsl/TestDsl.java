package com.dream.dsl;

import com.google.gson.Gson;
import old.GsonUtil;
import old.GsonUtils;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.BeforeClass;
import org.junit.Test;
import util.FileUtil;

/**
 * @author hukexin
 * @date 20-12-30
 */
public class TestDsl {
    Gson gson = GsonUtils.prettyGson;

    @BeforeClass
    public static void setup() throws Exception {
    }

    @Test
    public void testIf() throws Exception {
        //MuLexer lexer = new MuLexer(new ANTLRFileStream(args[0]));
        String content = FileUtil.getFileContent("test_dsl.txt");
        CharStream input = CharStreams.fromString(content);
        MuLexer lexer = new MuLexer(input);
        MuParser parser = new MuParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.parse();
        DslMuVisitor visitor = new DslMuVisitor();
//        visitor.visit(tree);
        Value value = visitor.visit(tree);
        //System.out.println(value);
        System.out.println("vars:");
        System.out.println(gson.toJson(visitor.getMemory()));
    }

    @Test
    public void testFor() throws Exception {
        //MuLexer lexer = new MuLexer(new ANTLRFileStream(args[0]));
        String content = FileUtil.getFileContent("test_dsl_for.txt");
        CharStream input = CharStreams.fromString(content);
        MuLexer lexer = new MuLexer(input);
        MuParser parser = new MuParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.parse();
        DslMuVisitor visitor = new DslMuVisitor();
//        visitor.visit(tree);
        Value value = visitor.visit(tree);
        //System.out.println(value);
        //System.out.println("vars:");
    }
}
