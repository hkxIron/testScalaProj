import com.google.gson.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Created by IntelliJ IDEA.
 * Author: hukexin
 * Date: 18-10-18
 * Time: 下午2:29
 */
class Student {
    private int rollNo;
    private String name;

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Student[ name = "+name+", roll no: "+rollNo+ "]";
    }
}

public class testGson {
    public String query = "";
    public double score=0.21;

    public JsonObject info = new JsonObject();

    static ExclusionStrategy myExclusionStrategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes fa) {
            //String faName = fa.getName();
            //return false;
            //return faName.equals("houyiResourceInfo");
            return fa.getName().equals("info");
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };
    static final Gson gson = new GsonBuilder().setExclusionStrategies(myExclusionStrategy).create();
    @Override
    public String toString() {
        return gson.toJson(this);
    }
    public static testGson parse(String str) {
        return gson.fromJson(str, testGson.class);
    }

    public static void main(String[] args) {
        {

           Gson gson4 = (new GsonBuilder()).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Student student = new Student();
            student.setRollNo(1);
            String jsonString = gson4.toJson(student);

            System.out.println(jsonString);
            student = gson4.fromJson(jsonString, Student.class);
            System.out.println(student);
            System.exit(-1);

        }
        {
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            builder.setPrettyPrinting();
            Gson gson3 = builder.create();

            Student student = new Student();
            student.setRollNo(1);
            String jsonString = gson3.toJson(student);

            System.out.println(jsonString);
            student = gson3.fromJson(jsonString, Student.class);
            System.out.println(student);
            System.exit(-1);
        }
        {
            Gson gson2 = new Gson();

            Student student = new Student();
            student.setRollNo(1);
            String jsonString = gson2.toJson(student);

            System.out.println(jsonString);
            student = gson2.fromJson(jsonString, Student.class);
            System.out.println(student);
            System.exit(-1);
        }

        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("k1", "v1:tt xx:t1 yy:t2");
            jsonObject.addProperty("k2", "v2");
            System.out.println("has key:" + jsonObject.has("k1"));
            System.out.println("has key:" + jsonObject.has("k3"));
            System.out.println("object:" + jsonObject.toString());
            System.out.println("value:" + jsonObject.get("k2").getAsString());
            //System.out.println("object:"+jsonObject.getAsString()); //exception
        }

        {
            testGson test = new testGson();
            test.info.addProperty("k1","v1");
            String str = test.toString();
            System.out.println("to String:"+str);
            testGson obj = parse(str);
            System.out.println("to String2:"+str);
        }

        {
            List<String> sub = new ArrayList<>(Arrays.asList("1","2","3","4"));
            List<String> allPositivePattern = new ArrayList<>(sub);
            for(String str:allPositivePattern){
                System.out.println(str);
            }
        }
    }
}
