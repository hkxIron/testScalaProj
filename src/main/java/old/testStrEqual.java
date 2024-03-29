package old;

public class testStrEqual {
    public static void main(String[] args) {
        String a = new String("ab"); // a 为一个引用
        String b = new String("ab"); // b为另一个引用,对象的内容一样
        String aa = "ab"; // 放在常量池中
        String bb = "ab"; // 从常量池中查找
        if (aa == bb) // true
            System.out.println("aa==bb");
        if (a == b) // false，非同一对象
            System.out.println("a==b");
        else 
            System.out.println("a, b not same object");
        if (a.equals(b)) // true
            System.out.println("a EQ b");
        if (42 == 42.0) { // true
            System.out.println("true");
        }
    }
}
/*
运行：
$javac test1.java
$java -Xmx128M -Xms16M test1
aa==bb
a, b not same object
a EQ b
true
*/
