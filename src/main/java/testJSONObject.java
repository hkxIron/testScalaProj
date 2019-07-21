/*
 * Created by IntelliJ IDEA.
 * Author: hukexin
 * Date: 19-3-27
 * Time: 下午2:16
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import play.api.libs.json.JsObject;
import play.api.libs.json.JsValue;
import play.api.libs.json.Json;

public class testJSONObject {
    public static void main(String... args) {
        String jsonStr="{\n" +
                "  \"app_name\": \"视频\",\n" +
                "  \"apps\": [\n" +
                "    {\n" +
                "      \"display_name\": \"视频\",\n" +
                "      \"icon_url\": \"\",\n" +
                "      \"is_native\": true,\n" +
                "      \"is_online\": true,\n" +
                "    }\n" +
                "  ],\n" +
                "}";

        // System.out.println("origin str:"+jsonStr);
        try {
            JSONObject jsonObj =  new JSONObject(jsonStr);
            System.out.println("str:"+jsonObj.getString("app_name"));
            System.out.println("obj:"+jsonObj);
            jsonObj.remove("apps");
            System.out.println("new obj:"+ jsonObj.toString());

            JSONObject jsonObj2 =  new JSONObject(jsonObj); // !!!!!不能这样复制对象,是个空json
            System.out.println("new obj2:"+ jsonObj2);

        } catch (Exception e) {
            System.out.println("edgeParser intention occur exception. ");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("xxx;bb;");
        sb.setLength(sb.length()-1);
        System.out.println("sb:"+sb.toString());

        String str = "s1,s2";
        String arr[] = str.split(";");
        System.out.println(arr[0]);
        //
        String s1 ="{\"name\" : \"Watership Down\", \"key\":\"xx\"}";

    }
}
