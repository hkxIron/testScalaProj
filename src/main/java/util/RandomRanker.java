package util;

/*
 * Created by IntelliJ IDEA.
 *
 * Author: hukexin
 * Email: hukexin@xiaomi.com
 * Date: 19-7-27
 * Time: 下午3:59
 */
public class RandomRanker implements RankerIface {
    @Override
    public float rank() {
        System.out.println("randomRanker");
        return 1;
    }
}
