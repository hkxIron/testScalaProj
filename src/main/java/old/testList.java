package old;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * Created by IntelliJ IDEA.
 *
 * Author: hukexin
 * Email: hukexin@xiaomi.com
 * Date: 19-9-12
 * Time: 下午2:15
 */
public class testList {
    @Before
    public void setup() throws Exception {}

    @Test
    public void testList() throws Exception {
        List<Integer> list1 = new ArrayList<>();
        list1.add(8);
        list1.add(10);
        list1.add(9);
        list1.add(5);
        list1.add(3);
        list1.add(7);

        list1.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        List list2 = list1.subList(Math.max(0, list1.size()-3), list1.size());
        //list1.subList(0, Math.min(10, list1.size()));
        // java join 也可以这样写
        System.out.println("list1:"+ list1.stream().map(Object::toString).collect(Collectors.joining(",")));
        System.out.println("list2:"+ list2.stream().map(Object::toString).collect(Collectors.joining(",")));

        List<Integer> list3 = new ArrayList<>();
        list3.add(8);
        list3.add(10);
        list3.add(9);
        list3.add(5);
        list3.add(3);
        list3.add(7);
        List<Integer> list4 = list3.stream().sorted().skip(Math.max(list3.size() -3, 0)).collect(Collectors.toList());
        System.out.println("list4:"+list4.stream().map(Objects::toString).collect(Collectors.joining(",")));
        System.out.println("list4:"+list4.stream().map(Objects::toString).collect(Collectors.joining(",")));
    }
}
