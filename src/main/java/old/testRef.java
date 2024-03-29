package old;

/**
 * 这是一个Java的经典问题，大部分人从C，C++语言入门，C语言有三种传递方式：值传递，地址传递和引用传递。详细的对C语言指针，引用的我个人的理解，见链接。
 *
 * Java所有操作都是传值操作！都是传值操作！都是传值操作！重要的事情说三遍。
 *
 * 疑问？那为什么别人讲的时候都是说，java的基本数据类型都是传值，所有的自定义数据（类的对象）都是传引用？？
 *
 * 很简单,因为这样好理解，意思是说：“同学们，如果我们把一个基本数据类型的值（变量）传递给一个函数的形参，那么无论我们对这个变量怎么操作，函数运行完之后，并不会改变这个变量的值！（到这里都是对的）但是如果我们把一个类的实例（对象）作为参数传递给函数，那么我们在函数里面对这个对象的改变，会实际地改变这个对象的值！（这里就不完全正确了）”。
 *
 * 对于Java的对象与引用的理解，请参见：浅谈Java中的对象和引用。我这里简单说一下Person person = new Person("张三")，这里面new Person("张三")是类Person的实例（或者说是对象），person是这个实例（对象）的引用。
 *
 * Java的参数传递都是值传递！！！
 *
 * 先贴一篇博文：理解Java中的引用传递和值传递，如果你觉得这个人讲的很有道理，那么你是麻瓜（哇！马老师附体！）。我先不解释，去看下这篇博文的1楼评论！
 *
 * 我们先定义，什么是值传递，什么是引用传递。
 *
 * 值传递：方法调用时，实际参数把它的值的副本传递给对应的形式参数。特点：此时内存中存在两个相等的基本类型，即实际参数和形式参数，后面方法中的操作都是对形参这个值的修改，不影响实际参数的值。
 *
 * 引用传递：方法调用时，实际参数的引用(地址，而不是参数的值)被传递给方法中相对应的形式参数，函数接收的是原始值的内存地址；特点：在方法执行中，形参和实参内容相同，指向同一块内存地址，方法执行中对引用的操作将会影响到实际对象。
 *
 * 值传递实在是太简单了，大家应该都容易理解，既然传递的是实际参数的副本，那么更改这个副本，跟原来的变量没有一点关系。
 *
 * 但是对于引用传递，一般的支持引用的例子我也不说了。看一个稍微特殊的例子：如果我们把下面的例子理解为引用传递，输出应该是: LI Si 才对！因为zhangsan这个（new Person("ZHANG San")的）引用指向了新的对象！但实际结果是zhangsan这个引用指向的对象的名字并没有变！
 *
 * 所以这里，我们把这种传递也理解为”值传递“只不过这里的值，是一个”引用”的值！也就是我们把实际参数（一个引用）拷贝一份赋值给形式参数，形式参数进行操作。当形式参数对本身的对象进行了变动操作，这里的效果跟引用传递是相同的。但是一旦给形式参数进行赋值类型的操作，这个赋值操作并不会像C语言的引用那样，把这个赋值的效果反映在函数运行结束之后！！
 *
 * 总结：为了更好地理解整个运行过程的变化，请参考：Java：按值传递还是按引用传递详细解说（其中有图片演示，很清楚）。
 * https://www.cnblogs.com/chen-kh/p/6696303.html
 * https://blog.csdn.net/zzp_403184692/article/details/8184751
 */
class Person2 {
    String name = "default";
    public Person2(String name) {
        this.name = name;
    }
    public void changeName(String name){
        this.name = name;
    }
    void printName() {
        System.out.println(this.name);
    }
}

public class testRef {

    public static void main(String[] args) {
        Person2 zhangsan = new Person2("ZHANG San");
        changePerson(zhangsan);
        zhangsan.printName(); // ZHANG San
    }

    public static void changePerson(Person2 person){
        person = new Person2("LI Si");
    }
}

