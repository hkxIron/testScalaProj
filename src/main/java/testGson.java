import com.google.gson.*;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Created by IntelliJ IDEA.
 * Author: hukexin
 * Date: 18-10-18
 * Time: 下午2:29
 */

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
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("k1", "v1:tt xx:t1 yy:t2");
            jsonObject.addProperty("k2", "v2");
            System.out.println("has key:" + jsonObject.has("k1"));
            System.out.println("has key:" + jsonObject.has("k3"));
            System.out.println("object:" + jsonObject.toString());
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
