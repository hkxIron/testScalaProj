import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.naming.event.ObjectChangeListener;
import javax.xml.bind.SchemaOutputResolver;

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
        {
            String str = "{\"category\": [\"4S\", \"汽车服务\"], \"city\": \"北京\", \"domain\": [\"汽车服务\"], \"@fromurl\": \"http://meishi.meituan.com/i/poi/269320\", \"subCategory\": [\"4S\", \"汽车服务\"], \"avagePrice\": \"0\", \"open\": \"1\", \"starRating\": \"3.5\", \"wifi\": \"0\", \"hasParking\": \"0\", \"longitude\": \"116.236724\", \"telephone\": [\"010-88208080\", \"88207767\", \"88208080\"], \"ur_md5\": \"1bb8092de073fcfafd6c5779c63ba2a0b40b8c53439d221dde202adf99749fd3f6e7edca16daccb81ce8fe4f80935efa66c162a99d6040dfb3aea2938570636\", \"canonicalImage\": \"http://p1.meituan.net/poi/aaa8ed65397426ea3f3c1d1e94838c07114688.jpg\", \"address\": \"海淀区阜石路63号（龙涛检测场南侧）\", \"latitude\": \"39.926036\", \"openingHours\": \"周一至周日 08:30-17:30\", \"@id\": \"http://meishi.meituan.com/i/poi/269320\", \"@type\": \"POI\", \"name\": \"森华丰田\"}";
            //String str = "{ \"category\": [\"其他美食\", \"美食\"], \"city\": \"威海\", \"domain\": [\"美食\"] }";
            JSONObject js2 = JSON.parseObject(str);
            Map<String, Object> innerMap= js2.getInnerMap();
            JSONArray arr = js2.getJSONArray("category");
            //JSONArray arr = js2.getJSONArray("t1"); // null,但不会报错
            System.out.println("arr:"+ arr); // arr:["其他美食","美食"]
            System.out.println("arr:"+ arr.contains("美食")); // arr:true
            System.out.println(String.join(",", arr.toJavaList(String.class))); // 其他美食,美食
            System.out.println("arr:"+arr.toJSONString()); // arr:["其他美食","美食"]
        }
        {
            // ojbect => json
            System.out.println("print");
            Person p1 = new Person("hkx", "male", 20);
            JSONObject jsObject = new JSONObject();
            jsObject.put("name", "hkx");
            jsObject.put("age", 20);
            jsObject.put("sex", "male");
            jsObject.put("birth", null);
            String js = jsObject.toJSONString();
            System.out.println("json string:" + js);
            JSONObject js2 = JSON.parseObject(js);
            System.out.println("name:" + js2.getString("name") + " age:" + js2.getString("age") + " salary:" + js2.getString("salary"));
        }
        {
           // json string => map
            String js="{\"sound_url\":\"http://files.ai.xiaomi.com/aiservice/voice/dd88a709ce1282e4e0d64b376e5ef9bf\", \"toSpeak\":\"您好\"}";
            JSONObject js2 = JSON.parseObject(js);
            Map<String, Object> innerMap= js2.getInnerMap();
            for(Map.Entry<String, Object> entry: innerMap.entrySet()){
                System.out.println(entry.getKey()+ ":"+ entry.getValue().toString());
            }
        }
    }
}
