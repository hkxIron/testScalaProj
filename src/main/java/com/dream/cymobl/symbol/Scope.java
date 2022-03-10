package com.dream.cymobl.symbol;

/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
public interface Scope {
    public String getScopeName();

    /** Where to look next for symbols , ie: parent scope*/
    public Scope getParentScope();

    /** Define a symbol in the current scope */
    public void addSymbol(Symbol sym);

    /** Look up name in this scope or in enclosing scope if not here */
    public Symbol findSymbol(String name);

    /** Look up name in this scope*/
    public Symbol findSymbolInCurrentScope(String name);
}
