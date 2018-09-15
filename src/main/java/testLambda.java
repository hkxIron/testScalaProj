import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class testLambda {

    public static void main(String[] args) {

        Map<String, String> slotMap = new HashMap<String, String>(){{
            put("name","小狗");
            put("cate","动物");
        }};

        List<String> searchQueryType = new ArrayList<String>() {{
            add("name"); //商家名称
            add("cate"); //商家类别
            add("subCate");//次级类别
            add("area");  //商圈
            add("address");//地址
            add("searchQuery");
        }};
        Stream stream =slotMap.entrySet().stream();
        // java lambda表达式
        String searchQuery = String.join(",",
                slotMap.entrySet().stream().filter(
                    entry -> searchQueryType.contains(entry.getKey())
                ).map(
                    entry -> entry.getValue()
                ).collect(Collectors.toList())
        );
        System.out.println("matched searchQuery:"+ searchQuery);
    }
}
