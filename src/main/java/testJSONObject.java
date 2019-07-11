/*
 * Created by IntelliJ IDEA.
 * Author: hukexin
 * Date: 19-3-27
 * Time: 下午2:16
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        } catch (Exception e) {
            System.out.println("edgeParser intention occur exception. ");
        }
    }
}
