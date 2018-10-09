import org.apache.commons.lang3.StringUtils;

/*
 * Created by IntelliJ IDEA.
 * Author: hukexin
 * Date: 18-9-26
 * Time: 下午3:37
 */
public class testEditDistance {

    public static double calculateStringSimilarity(String str1, String str2) {
        System.out.println("s1:"+str1 +" str2:"+str2);
        int distance = org.apache.commons.lang3.StringUtils.getLevenshteinDistance(str1, str2);
        int maxLength = Math.max(str1.length(), str2.length());
        return 1.0 - (distance * 1.0 / Math.max(1, maxLength));
    }


    public static void main(String... args) {
        String s1= "hello";
        String s2= "hell";
        System.out.println("sim:"+ calculateStringSimilarity("涮你喜欢","喜欢上你时"));
        System.out.println("distance:"+ StringUtils.getLevenshteinDistance(s1, s2)); // distance:1
        System.out.println("similar:"+ calculateStringSimilarity(s1,s2));
        System.out.println("similar:"+ calculateStringSimilarity(s1,s1));
    }
}

