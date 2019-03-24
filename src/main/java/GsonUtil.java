/*
 * Created by IntelliJ IDEA.
 * Author: hukexin
 * Date: 19-1-18
 * Time: 下午5:20
 */

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

/**
 * Created by lijunjie on 16/11/15.
 */
public class GsonUtil {
    private static final Gson gson
            = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private static final JsonParser jsonParser = new JsonParser();

    /**
     * 获取转下划线的gson。
     *
     * @return
     */
    public static Gson getUnderScoreGson() {
        return gson;
    }

    public static JsonParser getJsonParser() {
        return jsonParser;
    }
}
