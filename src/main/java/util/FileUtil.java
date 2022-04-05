package util;

import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/*
 * Created by IntelliJ IDEA.
 * Author: hukexin
 * Date: 18-9-26
 * Time: 下午8:59
 */
public class FileUtil {

    public static String getResFileAbsPath(String relPath) {
        try {
            URL res = FileUtil.class.getClassLoader().getResource(relPath);
            File file = Paths.get(res.toURI()).toFile();
            return file.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getFileContent(String filePath) {
        String fileAbsPath = FileUtil.getResFileAbsPath(filePath);
        if (StringUtils.isBlank(fileAbsPath)) {
            return null;
        } else {
            System.out.println(" ruleFilePath:" + filePath);
        }
        String content = "";
        try {
            content = Files.asCharSource(new File(fileAbsPath), StandardCharsets.UTF_8).read();
        } catch (Exception e) {
            System.out.println("read file:" + filePath+" error");
        }
        return content;
    }

    public static boolean isUTF8(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        byte[] b = new byte[3];
        in.read(b);
        in.close();
        if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
            return true;
        } else {
            return false;
        }
    }

    // 参考blog:https://blog.csdn.net/qyp199312/article/details/79501112?utm_source=copy
    // 比原始方法更为鲁棒

    /**
     * 在一段字节码里面找到符合 UTF-8 编码的字节数量. 取其数量占比
     * 同时考虑到了 BOM 位 (仅在初始值时) 未针对 Unicode 其它编码方式进行判断。
     * 该端代码具有很强的针对性, 如果文件是文本则可以很高效的分辨出是不是UTF-8, 但是如果是二进制文件, 则存在较大几率上的误差.
     * <p>
     * 参照格式为：
     * 0x0*******
     * <p>
     * 0x110*****
     * 0x10******
     * <p>
     * 0x1110****
     * 0x10******
     * 0x10******
     * <p>
     * 0x11110***
     * 0x10******
     * 0x10******
     * 0x10******
     * <p>
     * 0x111110**
     * 0x10******
     * 0x10******
     * 0x10******
     * 0x10******
     * <p>
     * 0x1111110*
     * 0x10******
     * 0x10******
     * 0x10******
     * 0x10******
     * 0x10******
     *
     * @param raw 指定的字节码
     * @return 数量占比
     */
    public static int getUTF8Percent(byte[] raw, int len) {
        int i;
        int utf8 = 0, ascii = 0;

        if (raw.length > 3) {
            if ((raw[0] == (byte) 0xEF) && (raw[1] == (byte) 0xBB) && (raw[2] == (byte) 0xBF)) {
                return 100;
            }
        }

        //len = raw.length;
        int child = 0;
        for (i = 0; i < len; ) {

            // UTF-8 中一定没有 FF 和 FE
            if ((raw[i] & (byte) 0xFF) == (byte) 0xFF || (raw[i] & (byte) 0xFE) == (byte) 0xFE) {
                return 0;
            }

            if (child == 0) {
                if ((raw[i] & (byte) 0x7F) == raw[i] && raw[i] != 0) {
                    // ASCII 在所有编码中格式为 0x0*******
                    ascii++;
                } else if ((raw[i] & (byte) 0xC0) == (byte) 0xC0) {
                    // 如果是 0x11****** 形式的, 则有一定可能性是 UTF-8 的
                    for (int bit = 0; bit < 8; bit++) {
                        if ((((byte) (0x80 >> bit)) & raw[i]) == ((byte) (0x80 >> bit))) {
                            child = bit;
                        } else {
                            break;
                        }
                    }
                    utf8++;
                }
                i++;
            } else {
                child = (raw.length - i > child) ? child : (raw.length - i);
                boolean currentNotUtf8 = false;
                for (int children = 0; children < child; children++) {
                    // 格式必须是 10******
                    if ((raw[i + children] & ((byte) 0x80)) != ((byte) 0x80)) {
                        if ((raw[i + children] & (byte) 0x7F) == raw[i + children] && raw[i] != 0) {
                            // ASCII 在所有编码中格式为 0x0*******
                            ascii++;
                        }
                        currentNotUtf8 = true;
                    }
                }
                if (currentNotUtf8) {
                    utf8--;
                    i++;
                } else {
                    utf8 += child;
                    i += child;
                }
                child = 0;
            }
        }
        // 纯ascii的, 也可以理解为纯 utf-8 的。
        if (ascii == len) {
            return 100;
        }
        // 把 ascii 的也算成 utf-8 的。
        System.out.println(" len: " + len + " utf8+ascii:" + (utf8 + ascii));
        return (int) (100 * ((float) (utf8 + ascii) / (float) len));
    }

    public static boolean isGeneralUTF8(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        byte[] b = new byte[100];
        int len = in.read(b);
        in.close();
        return getUTF8Percent(b, len) >= 90;
    }
}
