package com.dream.cymobl; /***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import com.dream.cymbol.CymbolBaseListener;
import com.dream.cymbol.CymbolParser;
import com.dream.cymobl.symbol.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class DefPhase extends CymbolBaseListener {
    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<Scope>();
    GlobalScope globals;
    Scope currentScope; // addSymbol symbols in this scope
    boolean isRedined = false;
    public void enterFile(CymbolParser.FileContext ctx) {
        globals = new GlobalScope(null);
        currentScope = globals;
    }
	
    public void exitFile(CymbolParser.FileContext ctx) {
        System.out.println(globals);
    }

    public void enterFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
        String name = ctx.ID().getText();
        int typeTokenType = ctx.type().start.getType();
        Symbol.Type type = CheckSymbols.getType(typeTokenType);
		
        // push new scope by making new one that points to enclosing scope
        FunctionSymbol function = new FunctionSymbol(name, type, currentScope);
        currentScope.addSymbol(function); // Define function in current scope
        saveScopeOnTree(ctx, function);      // Push: set function's parent to current
        // 注意:进入函数之后,会是函数的scope
        currentScope = function;       // Current scope is now function scope
    }

    void saveScopeOnTree(ParserRuleContext ctx, Scope s) { scopes.put(ctx, s); }

    public void exitFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
        System.out.println(currentScope);
        currentScope = currentScope.getParentScope(); // pop scope
    }

    public void enterBlock(CymbolParser.BlockContext ctx) {
        // push new local scope
        currentScope = new LocalScope(currentScope);
        saveScopeOnTree(ctx, currentScope);
    }

    public void exitBlock(CymbolParser.BlockContext ctx) {
        System.out.println(currentScope);
        currentScope = currentScope.getParentScope(); // pop scope
    }

    public void exitFormalParameter(CymbolParser.FormalParameterContext ctx) {
        // 并未改变scope
        defineVar(ctx.type(), ctx.ID().getSymbol());
    }

    public void enterVarDecl(CymbolParser.VarDeclContext ctx) {
        Token token = ctx.ID().getSymbol();
        // 如果重复定义,则不需要再次定义了
        if(currentScope.findSymbolInCurrentScope(token.getText())!=null){
            CheckSymbols.error(token, "has already defined: "+ token.getText());
            isRedined = true;
        }
    }

    public void exitVarDecl(CymbolParser.VarDeclContext ctx) {
        if(isRedined){
            // 如果重复定义,则不需要再次定义了
            isRedined = false;
        }else{
            defineVar(ctx.type(), ctx.ID().getSymbol());
        }
    }

    void defineVar(CymbolParser.TypeContext typeCtx, Token nameToken) {
        int typeTokenType = typeCtx.start.getType();
        Symbol.Type type = CheckSymbols.getType(typeTokenType);
        VariableSymbol var = new VariableSymbol(nameToken.getText(), type);
        currentScope.addSymbol(var); // Define symbol in current scope
        // 注意:定义局部变量并不会引起当前域的变化
    }
}
