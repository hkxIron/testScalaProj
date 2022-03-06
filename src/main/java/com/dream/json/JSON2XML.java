package com.dream.json; /***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import util.FileUtil;

import java.io.FileInputStream;
import java.io.InputStream;

/*
{
    "description" : "An imaginary server config file",
    "logs" : {"level":"verbose", "dir":"/var/log"},
    "host" : "antlr.org",
    "admin": ["parrt", "tombu"]
    "aliases": []
}

to

<description>An imaginary server config file</description>
<logs>
    <level>verbose</level>
    <dir>/var/log</dir>
</logs>
<host>antlr.org</host>
<admin>
    <element>parrt</element> <!-- inexact -->
    <element>tombu</element>
</admin>
<aliases></aliases>
 */

public class JSON2XML {
    public static class XMLEmitter extends JSONBaseListener {
        ParseTreeProperty<String> xml = new ParseTreeProperty<String>();
        String getXML(ParseTree ctx) { return xml.get(ctx); }
        void setXML(ParseTree ctx, String s) { xml.put(ctx, s); }

        public void exitJson(JSONParser.JsonContext ctx) {
            setXML(ctx, getXML(ctx.getChild(0)));
        }

        // {"level":"verbose", "dir":"/var/log"}
        public void exitAnObject(JSONParser.AnObjectContext ctx) {
            StringBuilder buf = new StringBuilder();
            buf.append("\n");
            // 多个pair都追加在后面
            for (JSONParser.PairContext pctx : ctx.pair()) {
                buf.append(getXML(pctx));
            }
            setXML(ctx, buf.toString());
        }
        public void exitEmptyObject(JSONParser.EmptyObjectContext ctx) {
            setXML(ctx, "");
        }

        public void exitArrayOfValues(JSONParser.ArrayOfValuesContext ctx) {
            StringBuilder buf = new StringBuilder();
            buf.append("\n");
            for (JSONParser.ValueContext vctx : ctx.value()) {
                buf.append("<element>"); // conjure up element for valid XML
                buf.append(getXML(vctx));
                buf.append("</element>");
                buf.append("\n");
            }
            setXML(ctx, buf.toString());
        }

        public void exitEmptyArray(JSONParser.EmptyArrayContext ctx) {
            setXML(ctx, "");
        }

        // 将"host":"antlr.org" 转换成 <host> antlr.org </host> \n
        public void exitPair(JSONParser.PairContext ctx) {
            String tag = stripQuotes(ctx.STRING().getText());
            JSONParser.ValueContext vctx = ctx.value();
            String x = String.format("<%s>%s</%s>\n", tag, getXML(vctx), tag);
            setXML(ctx, x);
        }

        // 将复合元素的翻译结果拷贝到自身的语法分析树节点中
        public void exitObjectValue(JSONParser.ObjectValueContext ctx) {
            // analogous to String value() {return object();}
            setXML(ctx, getXML(ctx.object()));
        }

        // 将array的翻译结果拷贝到自身的语法分析树节点中
        public void exitArrayValue(JSONParser.ArrayValueContext ctx) {
            setXML(ctx, getXML(ctx.array())); // String value() {return array();}
        }

        public void exitAtom(JSONParser.AtomContext ctx) {
            setXML(ctx, ctx.getText());
        }

        // 注意: string是有双引号的,需要去除
        public void exitString(JSONParser.StringContext ctx) {
            setXML(ctx, stripQuotes(ctx.getText()));
        }

        public static String stripQuotes(String s) {
            if ( s==null || s.charAt(0)!='"' ) return s;
            return s.substring(1, s.length() - 1);
        }
    }

    public static void main(String[] args) throws Exception {
//        String inputFile = null;
//        if ( args.length>0 ) inputFile = args[0];
//        InputStream is = System.in;
//        if ( inputFile!=null ) {
//            is = new FileInputStream(inputFile);
//        }
        String content = FileUtil.getFileContent("test_json.json");
        if(content==null){
            System.out.println("input is null, return!");
            return;
        }
        //ANTLRInputStream input = new ANTLRInputStream(is);

        // 1.Lexical analysis
        CharStream input = CharStreams.fromString(content);
        JSONLexer lexer = new JSONLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //  2.Syntax analysis
        JSONParser parser = new JSONParser(tokens);
        parser.setBuildParseTree(true);
        // show tree in text form
        //System.out.println(tree.toStringTree(parser));

        // 根据语法树(json)进行转换
        ParseTree tree = parser.json();
        ParseTreeWalker walker = new ParseTreeWalker();
        XMLEmitter converter = new XMLEmitter();
        walker.walk(converter, tree);
        System.out.println(converter.getXML(tree));
    }
}
