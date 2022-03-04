package old;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.api.libs.json.JsObject;
import play.api.libs.json.JsValue;
import play.api.libs.json.Json;

/*

 * Created by IntelliJ IDEA.
 *
 * Author: hukexin
 * Email: hukexin@xiaomi.com
 * Date: 19-9-6
 * Time: 下午5:21
 */
public class TestPlayJson {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestPlayJson.class); // /home/work/data/soft/ai-dialog-guide-api/logs/
    @Before
    public void setup() throws Exception {
    }

    @Test
    public void testJson() throws Exception {
        String intentStr = "{\n" +
                "          \"type\": \"相声\",\n" +
                "          \"score_info\": {\n" +
                "            \"rule_score\": 0,\n" +
                "            \"model_score\": 0\n" +
                "          },\n" +
                "          \"query\": \"我想听冯巩相声\",\n" +
                "          \"score\": 0,\n" +
                "          \"action\": \"query\",\n" +
                "          \"complete\": true,\n" +
                "          \"prob\": 0\n" +
                "        }";

        //JsonNode intentionJs = Json.parse(intentStr);
        JsValue jsValue = Json.parse(intentStr);
        System.out.println("json:"+ jsValue.toString());
        JsObject jsObject = null;
        if (jsValue instanceof JsObject){
           jsObject = (JsObject) jsValue;
        }
        assert jsObject != null;
        System.out.println("jsObj:"+ jsObject.toString());
        //intentionJs instanceof JsObject
        //org.json.JSONObject jsonObject = RequestApi.requestApi3(api3Request, "test");
    }
}
