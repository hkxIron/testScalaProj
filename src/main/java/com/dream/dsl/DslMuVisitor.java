package com.dream.dsl;

/**
 * @author: kexin
 * @date: 2022/4/4 13:38
 **/

import lombok.Getter;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DslMuVisitor extends MuBaseVisitor<Value> {

    // used to compare floating point numbers
    public static final double SMALL_VALUE = 0.00000000001;

    // 只有全局变量,没有局部变量
    // store variables (there's only one global scope!)
    @Getter
    private Map<String, Value> memory = new HashMap<String, Value>();
    private boolean isBreak = false;
    private boolean isContinue = false;

    // assignment/id overrides
    @Override
    public Value visitAssignment(MuParser.AssignmentContext ctx) {
        String id = ctx.ID().getText();
        Value value = this.visit(ctx.expr());
        return memory.put(id, value);
    }

    @Override
    public Value visitIdAtom(MuParser.IdAtomContext ctx) {
        String id = ctx.getText();
        Value value = memory.get(id);
        if (value == null) {
            throw new RuntimeException("no such variable: " + id);
        }
        return value;
    }

    // atom overrides
    @Override
    public Value visitStringAtom(MuParser.StringAtomContext ctx) {
        String str = ctx.getText();
        // strip quotes
        str = str.substring(1, str.length() - 1).replace("\"\"", "\"");
        return new Value(str);
    }

    // 所有的数字都以double来表示
    @Override
    public Value visitNumberAtom(MuParser.NumberAtomContext ctx) {
        return new Value(Double.valueOf(ctx.getText()));
    }

    @Override
    public Value visitBooleanAtom(MuParser.BooleanAtomContext ctx) {
        return new Value(Boolean.valueOf(ctx.getText()));
    }

    @Override
    public Value visitNilAtom(MuParser.NilAtomContext ctx) {
        return new Value((String) null);
    }

    // expr overrides
    // 计算括号的表达式
    @Override
    public Value visitParExpr(MuParser.ParExprContext ctx) {
        return this.visit(ctx.expr());
    }

    @Override
    public Value visitPowExpr(MuParser.PowExprContext ctx) {
        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));
        return new Value(Math.pow(left.asDouble(), right.asDouble()));
    }

    @Override
    public Value visitUnaryMinusExpr(MuParser.UnaryMinusExprContext ctx) {
        Value value = this.visit(ctx.expr());
        return new Value(-value.asDouble());
    }

    @Override
    public Value visitNotExpr(MuParser.NotExprContext ctx) {
        Value value = this.visit(ctx.expr());
        return new Value(!value.asBoolean());
    }

    @Override
    public Value visitMultiplicationExpr(@NotNull MuParser.MultiplicationExprContext ctx) {
        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case MuParser.MULT:
                return new Value(left.asDouble() * right.asDouble());
            case MuParser.DIV:
                return new Value(left.asDouble() / right.asDouble());
            case MuParser.MOD:
                return new Value(left.asDouble() % right.asDouble());
            default:
                throw new RuntimeException("unknown operator: " + MuParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public Value visitChangeThenGet(@NotNull MuParser.ChangeThenGetContext ctx) {
        return setGetValue(ctx.ID().getText(), ctx.op, true);
    }

    private Value setGetValue(String id, Token op, boolean setThenGetFlag) {
        Value oldValue = memory.get(id);
        Value newValue;
        int diff;
        switch (op.getType()) {
            case MuParser.MINUS_MINUS:
                diff = -1;
                break;
            case MuParser.PLUS_PLUS:
                diff = 1;
                break;
            default:
                throw new RuntimeException("unknown operator: " + MuParser.tokenNames[op.getType()]);
        }
        newValue = new Value(oldValue.asDouble() + diff);
        memory.put(id, newValue);
        if (setThenGetFlag) {
            return newValue;
        } else {
            return oldValue;
        }
    }

    @Override
    public Value visitGetThenChange(@NotNull MuParser.GetThenChangeContext ctx) {
        return setGetValue(ctx.ID().getText(), ctx.op, false);
    }

    @Override
    public Value visitAdditiveExpr(@NotNull MuParser.AdditiveExprContext ctx) {

        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case MuParser.PLUS:
                return left.isDouble() && right.isDouble() ?
                        new Value(left.asDouble() + right.asDouble()) :
                        new Value(left.asString() + right.asString()); // 只要二者均不是double,则都会toString后用字符串连接
            case MuParser.MINUS:
                return new Value(left.asDouble() - right.asDouble());
            default:
                throw new RuntimeException("unknown operator: " + MuParser.tokenNames[ctx.op.getType()]);
        }
    }

    // 关系运算, >,<,>=,<=
    @Override
    public Value visitRelationalExpr(@NotNull MuParser.RelationalExprContext ctx) {
        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case MuParser.LT:
                return new Value(left.asDouble() < right.asDouble());
            case MuParser.LTEQ:
                return new Value(left.asDouble() <= right.asDouble());
            case MuParser.GT:
                return new Value(left.asDouble() > right.asDouble());
            case MuParser.GTEQ:
                return new Value(left.asDouble() >= right.asDouble());
            default:
                throw new RuntimeException("unknown operator: " + MuParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public Value visitEqualityExpr(@NotNull MuParser.EqualityExprContext ctx) {

        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case MuParser.EQ:
                return left.isDouble() && right.isDouble() ?
                        new Value(Math.abs(left.asDouble() - right.asDouble()) < SMALL_VALUE) : // 数值相等
                        new Value(left.equals(right)); // 否则用对象相等
            case MuParser.NEQ:
                return left.isDouble() && right.isDouble() ?
                        new Value(Math.abs(left.asDouble() - right.asDouble()) >= SMALL_VALUE) :
                        new Value(!left.equals(right));
            default:
                throw new RuntimeException("unknown operator: " + MuParser.tokenNames[ctx.op.getType()]);
        }
    }

    // 逻辑运算
    @Override
    public Value visitAndExpr(MuParser.AndExprContext ctx) {
        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));
        return new Value(left.asBoolean() && right.asBoolean());
    }

    @Override
    public Value visitOrExpr(MuParser.OrExprContext ctx) {
        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));
        return new Value(left.asBoolean() || right.asBoolean());
    }

    // print,打印变量
    @Override
    public Value visitPrint(MuParser.PrintContext ctx) {
        Value value = this.visit(ctx.expr());
        System.out.println(value.asString());
        return value;
    }


    // if override
    @Override
    public Value visitIf_stat(MuParser.If_statContext ctx) {
        List<MuParser.Condition_blockContext> conditions = ctx.condition_block();
        boolean evaluatedBlock = false;

        // 对每一个condition条件进行运算
        for (MuParser.Condition_blockContext condition : conditions) {
            Value evaluated = this.visit(condition.expr());
            if (evaluated.asBoolean()) { // 该条件满足
                evaluatedBlock = true;
                // evaluate this block whose expr==true
                this.visit(condition.stat_block());
                break;
            }
        }

        // 运行else分支
        if (!evaluatedBlock && ctx.stat_block() != null) {
            // evaluate the else-stat_block (if present == not null)
            this.visit(ctx.stat_block());
        }

        return Value.NULL; // if-else不返回任何值
    }

    // while override
    @Override
    public Value visitWhile_stat(MuParser.While_statContext ctx) {
        Value value = this.visit(ctx.expr());

        outer:
        while (value.asBoolean()) {
            // evaluate the code block
            //this.visit(ctx.stat_block()); // 原来的
            List<MuParser.StatContext> stats = ctx.stat_block().block().stat();
            for (MuParser.StatContext stat : stats) {
                this.visit(stat);
                if (this.isBreak) {
                    this.isBreak =false;
                    break outer; // break直接跳到最外层
                } else if (this.isContinue) {
                    this.isContinue =false; // 跳过余下的stat
                    break;
                }
/*                Value statValue = this.visit(stat);
                if (statValue != null) {
                    if (statValue.getType() == Value.Type.IS_BREAK) {
                        break outer;
                    } else if (statValue.getType() == Value.Type.IS_CONTINUE) {
                        break;
                    }
                }*/
            }
            // evaluate the expression
            value = this.visit(ctx.expr());
        }

        return Value.NULL; // while不返回任何值
    }

    @Override
    public Value visitFor_stat(MuParser.For_statContext ctx) {
        this.visit(ctx.for_init_stat());
        MuParser.ExprContext condExpr = ctx.for_condition_stat().expr();
        Value conditionValue = this.visit(condExpr);
        outer:
        while (conditionValue.asBoolean()) {
            //this.visit(ctx.stat_block());
            List<MuParser.StatContext> stats = ctx.stat_block().block().stat();
            for (MuParser.StatContext stat : stats) {
                this.visit(stat);
                if (this.isBreak) {
                    this.isBreak =false;
                    break outer; // break直接跳到最外层
                } else if (this.isContinue) {
                    this.isContinue =false; // 跳过余下的stat
                    break;
                }
            }
            // 自加(i++)
            this.visit(ctx.for_recurrent_stat());
            conditionValue = this.visit(condExpr);
        }
        return Value.NULL; // for不返回任何值
    }

    @Override
    public Value visitBreak_stat(MuParser.Break_statContext ctx) {
        //return new Value(Value.Type.IS_BREAK);
        this.isBreak = true;
        return Value.NULL;
    }

    @Override
    public Value visitContinue_stat(MuParser.Continue_statContext ctx) {
        this.isContinue = true;
        return Value.NULL;
        //return new Value(Value.Type.IS_CONTINUE);
    }
}
