package old;

/*
 * Created by IntelliJ IDEA.
 *
 * Author: hukexin
 * Email: hukexin@xiaomi.com
 * Date: 22-3-11
 * Time: 下午3:26
 */
// 以下实例当在循环中使用 break 或 continue 循环时跳到指定的标签处：
public class TestLabel {
    public static void main(String[] args) {
        //test1();
        //test2();
        //testLabel();
        testlabel1();
        testlabel2();
    }

    public static void test1() {
        System.out.println("===============");
        String strSearch = "search for a substring in a sentence.";
        String substring = "substring";
        boolean found = false;
        int max = strSearch.length() - substring.length();
        int iter = 0;
        testlbl:
        for (int i = 0; i <= max; i++) {
            int length = substring.length();
            int j = i;
            int k = 0;
            while (length-- != 0) {
                if (strSearch.charAt(j++) != substring.charAt(k++)) {
                    iter++;
                    continue testlbl;
                }
            }
            found = true;
            break testlbl;
        }
        if (found) {
            System.out.println("发现子字符串。");
        } else {
            System.out.println("字符串中没有发现子字符串。");
        }
        System.out.println("iter:" + iter);
    }

    public static void test2() {
        System.out.println("===============");
        String strSearch = "search for a substring in a sentence.";
        String substring = "substring";
        boolean found = false;
        int max = strSearch.length() - substring.length();
        int iter = 0;
        for (int i = 0; i <= max; i++) {
            int length = substring.length();
            int j = i;
            int k = 0;
            while (length-- != 0) {
                if (strSearch.charAt(j++) != substring.charAt(k++)) {
                    iter++;
                    continue;
                }
            }
            found = true;
            break;
        }
        if (found) {
            System.out.println("发现子字符串。");
        } else {
            System.out.println("字符串中没有发现子字符串。");
        }
        System.out.println("iter:" + iter);
    }

    /**
     * java中的label转跳，只能紧挨着循环而定义否则代码中使用到就会编译不通过，如果没有使用到可以正常编译通过。
     */
    private static void testLabel() {
        System.out.println("===============");
        Label:
        for (int i = 0; i < 10; i++) {
            if (i == 9) {
                continue Label;
            }
            if (i == 7) {
                continue Label;
            }

/*            if (i == 5) {
                break Label;
            }*/

            if (i == 5) {
                break;
            }
            System.out.println(i);
        }

        System.out.println("end...");
    }

    private static void testlabel1() {
        System.out.println("===============");
        //Outer loop checks if number is multiple of 2
        OUTER:
        //outer label
        for (int i = 0; i < 4; i++) {
            INNER:
            //inter label
            for (int j = 0; j < 4; j++) {
                System.out.println("i: " + i + " j:" + j + ", break  from INNER label");
                if (i == 1) {
                    break OUTER; // 一下子 break外层循环，而不只是中断内层循环
                }
            }
        }
    }

    private static void testlabel2() {
        System.out.println("===============");
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                System.out.println("i: " + i + " j:" + j + ", break  from INNER label");
                if(i == 1){
                    break;
                }
            }
        }
    }
}
