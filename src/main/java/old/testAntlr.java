package old;

import com.dream.caculator.MyMathLexer;
import com.dream.caculator.MyMathListener;
import com.dream.caculator.MyMathParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;


/*
 * Created by IntelliJ IDEA.
 *
 * Author: hukexin
 * Email: hukexin@xiaomi.com
 * Date: 20-12-30
 * Time: 上午10:03
 */
public class testAntlr {

    public static void main(String[] args) {

        CharStream input = CharStreams.fromString("12*2+12\r\n");
        MyMathLexer lexer=new MyMathLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MyMathParser parser = new MyMathParser(tokens);
        ParseTree tree = parser.prog(); // parse
        MyMathListener listener = new MyMathListener() {
            @Override
            public void enterProg(MyMathParser.ProgContext ctx) { }

            @Override
            public void exitProg(MyMathParser.ProgContext ctx) {
            }

            @Override
            public void enterPrintExpr(MyMathParser.PrintExprContext ctx) {
            }

            @Override
            public void exitPrintExpr(MyMathParser.PrintExprContext ctx) {
            }

            @Override
            public void enterAssign(MyMathParser.AssignContext ctx) {
            }

            @Override
            public void exitAssign(MyMathParser.AssignContext ctx) {
            }

            @Override
            public void enterBlank(MyMathParser.BlankContext ctx) {

            }

            @Override
            public void exitBlank(MyMathParser.BlankContext ctx) {

            }

            @Override
            public void enterParens(MyMathParser.ParensContext ctx) {

            }

            @Override
            public void exitParens(MyMathParser.ParensContext ctx) {

            }

            @Override
            public void enterMulDiv(MyMathParser.MulDivContext ctx) {

            }

            @Override
            public void exitMulDiv(MyMathParser.MulDivContext ctx) {

            }

            @Override
            public void enterAddSub(MyMathParser.AddSubContext ctx) {

            }

            @Override
            public void exitAddSub(MyMathParser.AddSubContext ctx) {

            }

            @Override
            public void enterId(MyMathParser.IdContext ctx) {

            }

            @Override
            public void exitId(MyMathParser.IdContext ctx) {

            }

            @Override
            public void enterInt(MyMathParser.IntContext ctx) {

            }

            @Override
            public void exitInt(MyMathParser.IntContext ctx) {

            }

            @Override
            public void visitTerminal(TerminalNode node) {

            }

            @Override
            public void visitErrorNode(ErrorNode node) {

            }

            @Override
            public void enterEveryRule(ParserRuleContext ctx) {

            }

            @Override
            public void exitEveryRule(ParserRuleContext ctx) {

            }
        };

        System.out.println(tree);
        //System.out.println(old.GsonUtils.prettyGson.toJson(tree));
    }
}
