package com.dream.csv;

import old.GsonUtil;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import util.FileUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by IntelliJ IDEA.
 *
 * Author: hukexin
 * Email: hukexin@xiaomi.com
 * Date: 22-3-3
 * Time: 下午8:36
 */
public class CSVLoader extends CSVBaseListener {
    public static final String EMPTY = "";
    List<Map<String, String>> rows = new ArrayList<>();
    List<String> header;
    List<String> currentRow;

    @Override
    public void exitString(CSVParser.StringContext ctx) {
        currentRow.add(ctx.STRING().getText());
    }

    @Override
    public void exitText(CSVParser.TextContext ctx) {
        currentRow.add(ctx.TEXT().getText());
    }

    @Override
    public void exitEmpty(CSVParser.EmptyContext ctx) {
        currentRow.add(EMPTY);
    }

    // 退出标题行
    @Override
    public void exitHdr(CSVParser.HdrContext ctx) {
        // 注意:header也是用row来实现的
        header = new ArrayList<>();
        header.addAll(currentRow);
    }

    @Override
    public void enterRow(CSVParser.RowContext ctx) {
        currentRow = new ArrayList<>();
    }

    @Override
    public void exitRow(CSVParser.RowContext ctx) {
        if (ctx.getParent().getRuleIndex() == CSVParser.RULE_hdr) { // 如果当前行是标题行,直接返回
            return;
        }
        // 不是标题行
        Map<String, String> m = new LinkedHashMap<>();
        int i = 0;
        for (String v : currentRow) {
            m.put(header.get(i), v);
            i++;
        }
        rows.add(m);
    }

    public static void main(String[] args) {
        String content = FileUtil.getFileContent("test.csv");

        CharStream input = CharStreams.fromString(content);
        CSVLexer lexer = new CSVLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CSVParser parser = new CSVParser(tokens);
        ParseTree tree = parser.file();

        ParseTreeWalker walker = new ParseTreeWalker();
        CSVLoader loader = new CSVLoader();
        walker.walk(loader, tree);
        System.out.println(GsonUtil.getUnderScoreGson().toJson(loader.rows));
    }
}
