package old;

import util.FileUtil;

import java.io.File;

/*
 * Created by IntelliJ IDEA.
 * Author: hukexin
 * Date: 18-11-1
 * Time: 下午3:14
 */
public class TestSimilar {

    public static double calculateStringSimilarity(String str1, String str2) {
        int distance = org.apache.commons.lang3.StringUtils.getLevenshteinDistance(str1, str2);
        int maxLength = Math.max(str1.length(), str2.length());
        double similar = 1.0 - (distance * 1.0 / Math.max(1, maxLength));
        System.out.println("distance:"+distance + " similar:"+ similar);
        return similar;
    }

    /**
     * distance:4 similar:0.0
     * distance:1 similar:0.8
     * distance:1 similar:0.8
     * distance:2 similar:0.6
     */

    public static void main(String... args) throws Exception {
        calculateStringSimilarity("快乐足球", "足球段子"); // 4, 0
        calculateStringSimilarity("快乐足球", "快乐足球啊"); // 1, 0.8
        calculateStringSimilarity("快乐足球哦", "快乐足球啊"); // 1, 0.8
        calculateStringSimilarity("乐足球哦", "快乐足球啊"); // 2, 0.6
        calculateStringSimilarity("2k", "2k11"); // 2, 0.5
        calculateStringSimilarity("nba 2k", "nba 2k11"); // 2, 0.75
    }
}
