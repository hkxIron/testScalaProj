package model;

public class Child extends Person{

        public void say(String s) {
            System.out.println("Hello invork>>>"+s);
        }

        public void add(int a,int b) {
            a+=b;
            System.out.println("a+b="+a);
        }
}