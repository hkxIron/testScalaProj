package old;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by IntelliJ IDEA.
 *
 * Author: hukexin
 * Email: hukexin@xiaomi.com
 * Date: 22-3-11
 * Time: 下午5:15
 */
public class TestTry {
    public static void main(String[] args) {
        test();
        testList();
    }

    private static void testList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        List<Integer> newList = list.subList(0, 3);
        System.out.println(newList.size());
        System.out.println(list.size());
    }

    private static int test() {
        try {
            System.out.println("try");
            throw new Exception("try");
            //return 0;
        } catch (Exception ex) {
            System.out.println("catch");
            //return 1;
        } finally {
            System.out.println("finally");
        }
        return -1; // finally之前不能有 return
    }
}
