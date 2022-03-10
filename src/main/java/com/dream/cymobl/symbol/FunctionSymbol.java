package com.dream.cymobl.symbol; /***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import java.util.LinkedHashMap;
import java.util.Map;

public class FunctionSymbol extends Symbol implements Scope {
    Map<String, Symbol> arguments = new LinkedHashMap<String, Symbol>(); // 函数会有很多参数
    Scope parentScope;

    public FunctionSymbol(String name, Type retType, Scope parentScope) {
        super(name, retType);
        this.parentScope = parentScope;
    }

    public Symbol findSymbol(String name) {
        Symbol s = arguments.get(name);
        if ( s!=null ) return s;
        // if not here, check any enclosing scope
        if ( getParentScope() != null ) {
            return getParentScope().findSymbol(name);
        }
        return null; // not found
    }

    @Override
    public Symbol findSymbolInCurrentScope(String name) {
        Symbol s = arguments.get(name);
        if ( s!=null ) return s;
        return null;
    }

    public void addSymbol(Symbol sym) {
        arguments.put(sym.name, sym);
        sym.scope = this; // track the scope in each symbol
    }

    public Scope getParentScope() { return parentScope; }
    public String getScopeName() { return name; }

    public String toString() { return "function"+super.toString()+":"+arguments.values(); }
}
