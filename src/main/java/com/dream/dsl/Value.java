package com.dream.dsl;

import lombok.Getter;

/**
 * @author: kexin
 * @date: 2022/4/4 16:31
 **/
public class Value {
    enum Type{
        STR,
        BOOL,
        DOUBLE,
        INT,
        IS_BREAK,
        IS_CONTINUE
    }

    public static Value NULL = new Value((String)null);
    @Getter
    private Type type;
    private Object obj;

    public Value(Type type){
        this.type = type;
    }

    public Value(String s){
       this.obj =(Object) s;
       this.type = Type.STR;
    }

    public Value(boolean s){
        this.obj =(Object) s;
        this.type = Type.BOOL;
    }
    public Value(double s){
        this.obj =(Object) s;
        this.type = Type.DOUBLE;
    }
   /* public Value(int s){
        this.obj =(Object) s;
        this.type = Type.INT;
    }*/

    public double asDouble(){
        assert this.type == Type.DOUBLE;
        return (double) this.obj;
    }

   /* public int asInt(){
        assert this.type == Type.INT;
        return (int)this.obj;
    }*/

    public boolean asBoolean(){
        assert this.type == Type.BOOL;
        return (boolean)this.obj;
    }

    public String asString(){
        if(this.obj!=null){
            return this.obj.toString();
        }else{
            return "nil";
        }
    }

    public boolean isDouble(){
        return this.type == Type.DOUBLE;
    }

}
