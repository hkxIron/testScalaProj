package old;

import java.util.HashMap;
import java.util.Map;

public class prior {

    //定义在多个slot时的优先级，数值越大，越优先
    public static final Map<String, Integer> slotPrior = new HashMap<String, Integer>(){{
        put("temporary", -2);
        put("type", -1); // 冷知识优先级最低
        put("trivia", 0); // 冷知识优先级最低
        put("no_sound", 1);
        put("animal", 2);
        put("simulator", 2);
        put("character_voice", 2);
    }};
}
