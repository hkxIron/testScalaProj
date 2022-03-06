package com.dream.cymobl; /***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import com.dream.cymbol.CymbolBaseListener;
import com.dream.cymbol.CymbolLexer;
import com.dream.cymbol.CymbolParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.MultiMap;
import org.antlr.v4.runtime.misc.OrderedHashSet;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.stringtemplate.v4.ST;
import util.FileUtil;

import java.util.Set;

public class CallGraph {
    /** A graph model of the output. Tracks call from one function to
     *  another by mapping function to list of called functions. E.g.,
     *  f -> [g, h]
     *  Can dump DOT two ways (StringBuilder and ST). Sample output:
         digraph G {
           ... setup ...
           f -> g;
           g -> f;
           g -> h;
           h -> h;
         }
     */
    static class Graph {
        // I'm using org.antlr.v4.runtime.misc: OrderedHashSet, MultiMap
        Set<String> nodes = new OrderedHashSet<String>(); // list of functions
        // multiMap就是一个key可以对多个value,即 K->List<V>
        MultiMap<String, String> edges =                  // caller->callee
            new MultiMap<String, String>();
        public void addEdge(String source, String target) {
            edges.map(source, target);
        }
        public String toString() {
            return "edges: "+edges.toString()+", functions: "+ nodes;
        }
        public String toDOT() {
            StringBuilder buf = new StringBuilder();
            buf.append("digraph G {\n");
            buf.append("  ranksep=.25;\n");
            buf.append("  addEdge [arrowsize=.5]\n");
            buf.append("  node [shape=circle, fontname=\"ArialNarrow\",\n");
            buf.append("        fontsize=12, fixedsize=true, height=.45];\n");
            buf.append("  ");
            for (String node : nodes) { // print all nodes first
                buf.append(node);
                buf.append("; ");
            }
            buf.append("\n");
            for (String src : edges.keySet()) {
                for (String trg : edges.get(src)) {
                    buf.append("  ");
                    buf.append(src);
                    buf.append(" -> ");
                    buf.append(trg);
                    buf.append(";\n");
                }
            }
            buf.append("}\n");
            return buf.toString();
        }

        /** Fill StringTemplate:
             digraph G {
               rankdir=LR;
               <edgePairs:{addEdge| <addEdge.a> -> <addEdge.b>;}; separator="\n">
               <childless:{f | <f>;}; separator="\n">
             }

		    Just as an example. Much cleaner than buf.append method
         */
        public ST toST() {
            ST st = new ST(
                "digraph G {\n" +
                "  ranksep=.25; \n" +
                "  addEdge [arrowsize=.5]\n" +
                "  node [shape=circle, fontname=\"ArialNarrow\",\n" +
                "        fontsize=12, fixedsize=true, height=.45];\n" +
                "  <funcs:{f | <f>; }>\n" +
                "  <edgePairs:{addEdge| <addEdge.a> -> <addEdge.b>;}; separator=\"\\n\">\n" + // 注意:ST中可以使用对象的属性
                //"  <edgePairs:{addEdge| <addEdge.a.replace(\"a\",\"b\")> -> <addEdge.b>;}; separator=\"\\n\">\n" + // 注意:ST中可以使用对象的属性
                "}\n"
            );
            st.add("edgePairs", edges.getPairs());
            st.add("funcs", nodes);
            return st;
        }
    }

    static class FunctionListener extends CymbolBaseListener {
        Graph graph = new Graph();
        String currentFunctionName = null;

        public void enterFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
            currentFunctionName = ctx.ID().getText();
            graph.nodes.add(currentFunctionName);
        }

        public void exitCall(CymbolParser.CallContext ctx) {
            String funcName = ctx.ID().getText();
            // map current function to the callee
            graph.addEdge(currentFunctionName, funcName);
        }
    }

    public static void main(String[] args) throws Exception {
//        String inputFile = null;
//        if ( args.length>0 ) inputFile = args[0];
//        InputStream is = System.in;
//        if ( inputFile!=null ) {
//            is = new FileInputStream(inputFile);
//        }
//        ANTLRInputStream input = new ANTLRInputStream(is);

        String content = FileUtil.getFileContent("t.cymbol");
        if(content==null){
            System.out.println("input is null, return!");
            return;
        }
        //ANTLRInputStream input = new ANTLRInputStream(is);
        // 1.Lexical analysis
        CharStream input = CharStreams.fromString(content);

        CymbolLexer lexer = new CymbolLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CymbolParser parser = new CymbolParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.file();
        // show tree in text form
//        System.out.println(tree.toStringTree(parser));

        ParseTreeWalker walker = new ParseTreeWalker();
        FunctionListener collector = new FunctionListener();
        walker.walk(collector, tree);
        System.out.println(collector.graph.toString());
        System.out.println(collector.graph.toDOT());

        // Here's another example that uses StringTemplate to generate output
        System.out.println("use StringTemplate:");
        System.out.println(collector.graph.toST().render());
    }
}
