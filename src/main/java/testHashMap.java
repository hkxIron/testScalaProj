import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class testHashMap {
    public static void main(String... args) {
        Map<String, String> map= new HashMap<>();
        map.put("name", "hkx");
        map.put("topic", "season");

        Map<String, String> newMap = new HashMap<>(map);
        newMap.put("name","wang");

        System.out.println("old map:"+map.get("name"));
        System.out.println("new map:"+newMap.get("name"));
    }
}
