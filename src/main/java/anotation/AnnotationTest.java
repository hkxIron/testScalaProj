package anotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

/**
 * Annotation在反射函数中的使用示例
 */
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String[] value() default "unknown";
}

/**
 * Person类。它会使用MyAnnotation注解。
 */
class Person {
   
    /**
     * empty()方法同时被 "@Deprecated" 和 "@MyAnnotation(value={"a","b"})"所标注
     * (01) @Deprecated，意味着empty()方法，不再被建议使用
     * (02) @MyAnnotation, 意味着empty() 方法对应的MyAnnotation的value值是默认值"unknown"
     */
    @MyAnnotation
    @Deprecated
    public void empty(){
        System.out.println("\ncall empty");
    } // empty的value值是unknown
   
    /**
     * sombody() 被 @MyAnnotation(value={"girl","boy"}) 所标注，
     * @MyAnnotation(value={"girl","boy"}), 意味着MyAnnotation的value值是{"girl","boy"}
     */
    @MyAnnotation(value={"girl","boy"})
    public void somebody(String name, int age){
        System.out.println("\ncall somebody: "+name+", "+age);
    }
}

public class AnnotationTest {

    public static void main(String[] args) throws Exception {
       
        // 新建Person
        Person person = new Person();
        // 获取Person的Class实例
        Class<Person> c = Person.class;
        // 获取 somebody() 方法的Method实例 => somebody(String name, int age)
        Method mSomebody = c.getMethod("somebody", new Class[]{String.class, int.class});
        // 通过反射执行该方法
        mSomebody.invoke(person, new Object[]{"lily", 18}); // 参数传到somebody(string, int)中
        printAnnotationsForMethod(mSomebody);
       

        // 获取 somebody() 方法的Method实例 => empty()
        Method mEmpty = c.getMethod("empty", new Class[]{});
        // 执行该方法
        mEmpty.invoke(person, new Object[]{}); // 空参数传到empty()中
        printAnnotationsForMethod(mEmpty);
    }
   
    public static void printAnnotationsForMethod(Method method) {
        // 判断 somebody() 方法是否包含MyAnnotation注解
        if(method.isAnnotationPresent(MyAnnotation.class)){
            // 获取该方法的MyAnnotation注解实例
            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            // 获取 myAnnotation的值，并打印出来
            String[] values = myAnnotation.value();
            System.out.print("MyAnnotation.value:");
            for (String str:values)
                System.out.print(str+", ");
            System.out.println();
        }
       
        // 获取方法上的所有注解，并打印出来
        System.out.println("print all annotations:");
        Annotation[] annotations = method.getAnnotations();
        for(Annotation annotation : annotations){
            System.out.println(annotation);
        }
    }
}