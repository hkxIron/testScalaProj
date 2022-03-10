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

public abstract class BaseScope implements Scope {
    // 指向上一层的域
    Scope enclosingScope; // null if global (outermost) scope(父域)
    Map<String, Symbol> symbols = new LinkedHashMap<String, Symbol>();

    public BaseScope(Scope enclosingScope) { this.enclosingScope = enclosingScope;  }

    // Look up name in this scope
    public Symbol findSymbol(String name) {
        Symbol s = symbols.get(name);
        if ( s!=null ) return s;
        // if not here, check any enclosing scope
        if ( enclosingScope != null ) return enclosingScope.findSymbol(name);
        return null; // not found
    }

    public Symbol findSymbolInCurrentScope(String name) {
        Symbol s = symbols.get(name);
        if ( s!=null ) return s;
        return null; // not found
    }

    public void addSymbol(Symbol sym) {
        symbols.put(sym.name, sym);
        sym.scope = this; // track the scope in each symbol
    }

    public Scope getParentScope() { return enclosingScope; }

    public String toString() { return getScopeName()+":"+symbols.keySet().toString(); }
}
