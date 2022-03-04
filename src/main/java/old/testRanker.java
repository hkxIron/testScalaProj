package old;

import org.apache.commons.lang.exception.ExceptionUtils;
import util.RandomRanker;
import util.RankerIface;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
//import model.Child;

public class testRanker {

    public static void main(String... args)  throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        {
            RankerIface ranker = new RandomRanker();
            float score = ranker.rank();
            System.out.println("score:" + score + " ranker:" + ranker.getClass().getSimpleName());
        }
        {
            // 动态加载
            //String rankerName = "util.RandomRanker";
            String rankerName = "util.EpisilonRanker";
            Class c = Class.forName(rankerName); // 需要带上包名
            RankerIface ranker = (RankerIface) c.newInstance();//通过类类型创建该类的对象
            float score = ranker.rank();
            System.out.println("score:" + score + " ranker:" + ranker.getClass().getSimpleName());
            //
            Field[] fs = c.getDeclaredFields();
            for(Field field:fs){
                System.out.println("获得属性的修饰符，例如public，static等等 >>"+ Modifier.toString(field.getModifiers()));
                System.out.println("属性的类型的名字 >>"+field.getType());
                System.out.println("属性的名字 >>"+field.getName());
            }
            Method[] ms = c.getDeclaredMethods();
            for(Method field:ms){
                System.out.println("获得方法的修饰符，例如public，static等等 >>"+Modifier.toString(field.getModifiers()));
                System.out.println("方法的参数个数 >>"+field.getParameterCount());
                System.out.println("方法的名字 >>"+field.getName());
            }
            System.out.println("c1的父类>>"+c.getSuperclass());

            Class[] cs=c.getInterfaces();
            for(Class field:cs){
                System.out.println("接口的名字 >>"+field.getName());
            }
            System.out.println(">>>>>>>>>>>");
        }

        {
            // invoke
            /**
             * 一个方法可以生成多个Method对象，但只有一个root对象，主要用于持有一个MethodAccessor对象，这个对象也可以认为一个方法只有一个，相当于是static的，因为Method的invoke是交给MethodAccessor执行的。
             */
            try {
                Class<?> clz = Class.forName("model.Child");
                Object obj = clz.newInstance();
                Method method = clz.getMethod("add", int.class, int.class);
                method.invoke(obj, 1, 2); // 调用方法：child.add(1,2)
                method = clz.getDeclaredMethod("say", String.class);
                method.invoke(obj, "http://blog.csdn.net/unix21/"); // 调用方法：child.say()
            }catch (Exception ex){
                System.out.println("error!" + ExceptionUtils.getFullStackTrace(ex));
            }
        }
        /**
         * randomRanker
         * score:1.0 ranker:RandomRanker
         * episilonRanker
         * score:0.0 ranker:EpisilonRanker
         * 获得方法的修饰符，例如public，static等等 >>public
         * 方法的参数个数 >>0
         * 方法的名字 >>rank
         * c1的父类>>class java.lang.Object
         * 接口的名字 >>util.RankerIface
         * >>>>>>>>>>>
         * a+b=3
         * Hello invork>>>http://blog.csdn.net/unix21/
         *
         */

    }
}
