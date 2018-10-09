import org.apache.commons.lang3.StringUtils;
import util.FileUtil;

import java.io.File;

/*
 * Created by IntelliJ IDEA.
 * Author: hukexin
 * Date: 18-9-26
 * Time: 下午3:37
 */
public class testFileType {

    public static void main(String... args) throws Exception {
        File file1 =new File("/home/hukexin/temp/t1_1.txt");
        File file2 =new File("/home/hukexin/temp/t2_0.txt");
        System.out.println(FileUtil.isGeneralUTF8(file1));
        System.out.println(FileUtil.isGeneralUTF8(file2));
        System.out.println(FileUtil.isGeneralUTF8(new File("/home/hukexin/temp/t2.txt")));
    }
}

