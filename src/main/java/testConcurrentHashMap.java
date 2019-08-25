import model.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class testConcurrentHashMap {

    public static void main(String[] args) {

        ConcurrentHashMap<String, Topic> topicMap = new ConcurrentHashMap<>();
        Topic topic1 = new Topic("t1");
        topicMap.put("k1", topic1);
        Topic topic = topicMap.getOrDefault("k1", new Topic("default"));
        //Topic topic = topicMap.putIfAbsent("k1", new Topic("default"));
        topic.name = "modify";

        // java lambda表达式
        System.out.println("matched searchQuery:"+
                String.join(",",topicMap.keySet().stream().map(x->topicMap.get(x).name).collect(Collectors.toList())));
    }
}
