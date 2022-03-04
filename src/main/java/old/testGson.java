package old;

import com.google.gson.*;

import java.io.Serializable;
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
        return "old.Student[ name = "+name+", roll no: "+rollNo+ "]";
    }
}


class TestBean   {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    private String title;
    private boolean isShow = true;//这里给isShow设置了默认值，坑点
}

class TestBean2{
    public TestBean2() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    private String title;
    private boolean isShow = true;//这里给isShow设置了默认值，坑点
}

class IntentionTest implements Serializable {
    public class ShowApp {
        // !!!该函数用来初始化,以备gson反序列化时调用,请勿删除!!!
        public ShowApp() {
            this.isNative=false;
            this.isOnline=true;
        }

        public ShowApp(String displayName, String packageName, String iconUrl, boolean isNative, boolean isOnline) {
            this.isNative = isNative;
            this.isOnline = isOnline;
        }

        public boolean isNative() { return isNative; }

        public void setNative(boolean aNative) { isNative = aNative; }

        public boolean isOnline() { return isOnline; }

        public void setOnline(boolean online) { isOnline = online; }

        private boolean isNative = false;
        private boolean isOnline = true;

    }


    public List<ShowApp> getApps() {
        return apps;
    }

    public void setApps(List<ShowApp> apps) {
        this.apps = apps;
    }

    private List<ShowApp> apps;
    private String tag;

    public IntentionTest() {
        tag = "";
        apps = new ArrayList<>();
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
            Gson gson = new Gson();
            TestBean testBean = gson.fromJson("{title:\"标题\"}", TestBean.class);
            TestBean2 testBean2 = gson.fromJson("{title:\"标题\"}", TestBean2.class);
            String str="{ \n" +
                    "\"apps\": [\n" +
                    "                    {\n" +
                    "                        \"icon_url\": \"http://t2.a.market\",\n" +
                    "                        \"package_name\": \"com.i.lens\",\n" +
                    "                        \"display_name\": \"智能识物\"\n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"download_now\": false,\n" +
                    "                \"tag\": \"defaultApp\"\n" +
                    "}";
            System.out.println(testBean.isShow());
            System.out.println(testBean2.isShow());
            IntentionTest testIntent = gson.fromJson(str, IntentionTest.class);
            System.out.println(testIntent.getApps().get(0).isOnline());
        }
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
