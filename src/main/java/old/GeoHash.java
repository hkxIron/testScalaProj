package old;// blog:https://blog.csdn.net/universe_ant/article/details/74785989

import java.util.BitSet;
import java.util.HashMap;

public class GeoHash {
    private static int numbits = 6 * 5;
    final static char[] digits =
           {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    final static HashMap<Character, Integer> lookup = new HashMap<Character, Integer>();
    static {
        int i = 0;
        for(char c : digits)
            lookup.put(c, i++);
    }

    public double[] decode(String geoHash) {
        StringBuilder buffer = new StringBuilder();
        for(char c : geoHash.toCharArray()) {
            int i = lookup.get(c) + 32;
            buffer.append(Integer.toString(i, 2).substring(1));
        }

        BitSet lonset = new BitSet();
        BitSet latset = new BitSet();

        // even bits
        int j = 0;
        for(int i = 0; i < numbits*2; i += 2) {
            boolean isSet = false;
            if(i < buffer.length())
                isSet = buffer.charAt(i) == '1';
            lonset.set(j++, isSet);
        }

        // odd bits
        j = 0;
        for(int i = 1; i < numbits*2; i += 2) {
            boolean isSet = false;
            if(i < buffer.length())
                isSet = buffer.charAt(i) == '1';
            latset.set(j++, isSet);
        }

        double lon = decode(lonset, -180, 180);
        double lat = decode(latset, -90, 90);

        return new double[] {lat, lon};
    }

    private double decode(BitSet bs, double floor, double ceiling) {
        double mid = 0;
        for(int i = 0; i < bs.length(); i++) {
            mid = (floor + ceiling) / 2;
            if(bs.get(i))
                floor = mid;
            else
                ceiling = mid;
        }
        return mid;
    }

    public String encode(double lat, double lon) {
        BitSet latbits = getBits(lat, -90, 90);
        BitSet lonbits = getBits(lon, -180, 180);
        StringBuilder buffer = new StringBuilder();
        for(int i = 0; i < numbits; i++) {
            buffer.append(lonbits.get(i) ? '1' : '0');
            buffer.append(latbits.get(i) ? '1' : '0');
        }
        return base32(Long.parseLong(buffer.toString(), 2));
    }

    private BitSet getBits(double d, double floor, double ceiling) {
        BitSet buffer = new BitSet(numbits);
        for(int i = 0; i < numbits; i++) {
            double mid = (floor + ceiling) / 2;
            if(d >= mid) {
                buffer.set(i);
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
        return buffer;
    }

    private static String base32(long i) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);
        if(!negative)
            i = -i;
        while(i <= -32) {
            buf[charPos--] = digits[(int) (-(i % 32))];
            i /= 32;
        }
        buf[charPos] = digits[(int) (-i)];

        if(negative)
            buf[--charPos] = '-';
        return new String(buf, charPos, (65 - charPos));
    }

    public static void main(String[] args) {
        System.out.println(new GeoHash().encode(39.92324, 116.3906));
        //输出：wx4g0ec19x3d
        double[] arr = new GeoHash().decode("wx4g0ec19x3d");
        System.out.println(arr[0]+" "+arr[1]); // arr:39.92323983460665 116.39059983193874
    }
}
