import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

class Person {
    String name;
    String sex;
    int age;

    public Person(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }
}

public class testFastJson {

    public static void main(String[] args) {
        System.out.println("print");
        Person p1 = new Person("hkx","male", 20);
        JSONObject jsObject =new JSONObject();
        jsObject.put("name", "hkx");
        jsObject.put("age", 20);
        jsObject.put("sex", "male");
        jsObject.put("birth", null);
        String js = jsObject.toJSONString();
        System.out.println("json string:"+ js);
        JSONObject js2 = JSON.parseObject(js);
        System.out.println("name:"+js2.getString("name") + " age:"+ js2.getString("age") + " salary:"+ js2.getString("salary") );
    }
}
