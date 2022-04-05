package com.dream.dsl;

/**
 * @author: kexin
 * @date: 2022/4/4 16:31
 **/
public class Value {
    public static String VOID = "";
    private String s;
    private boolean b;
    private double d;
    private int i;


    public Value(String s){
       this.s =s;
    }

    public Value(boolean b){
       this.b = b;
    }

    public Value() {

    }
}
