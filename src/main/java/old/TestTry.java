package old;

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
