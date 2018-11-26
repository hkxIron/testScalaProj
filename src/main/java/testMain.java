import javax.sound.midi.Soundbank;

import org.apache.commons.lang3.StringUtils;
import scala.Option;
import scala.Option$;
import scala.collection.JavaConverters;
import scala.concurrent.JavaConversions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testMain {
    public enum ActionType {
        // 订座
        search("search"),
        select("select"),
        confirm("confirm"),
        cancel("cancel"),
        black("black"),
        none("none"),
        // 外卖
        takeout_book_restaurant("takeout_food_book_by_restaurant_name"), // 根据餐馆名查询
        takeout_book_food("takeout_food_book_by_food_name"); // 根据食物名查询

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        ActionType(String name) {
            this.value = name;
        }
    }
    public static String generalNormalize(String str) {
        if (str == null || str.length() == 0) return str;
        return str
                .replaceAll(" ", "")
                .replaceAll("\\pP", "") // 会去除所有的标点符号,无论是中文还是英文,但空格不会被去除
                ;
    }

    public static void main(String[] args) {
        {
            Pattern emptyPattern = Pattern.compile("( |\\pP|^(小艾同学|小爱同学|小米机器人|小爱|嗯|呃|哎呀))");
            String[] arr = { "小爱狗怎么叫", "小爱同学怎么叫", "小爱同学, 怎么叫", "小爱  狗怎么叫", "小艾狗叫", "小爱嗯鸡怎么叫"};
            for(String query:arr){
                System.out.println(emptyPattern.matcher(query).replaceAll(""));
            }
        }
        {
            String str="零一二两三四五六七八九十0123456";
            Map<String, String> map = new HashMap<String, String>() {
                {
                    put("零", "0");
                    put("一", "1");
                    put("二", "2");
                    put("三", "3");
                    put("四", "4");
                    put("五", "5");
                    put("六", "6");
                    put("七", "7");
                    put("八", "8");
                    put("九", "9");
                    put("十", "10");
                }
            };

            StringBuilder sb = new StringBuilder();
            for(char ch:str.toCharArray()){
                System.out.println("ch:"+ch+" int:"+(int)ch);
            }
        }
        {
            Pattern twoPattern =  Pattern.compile("俩(?:.*)(?=个人|餐位|餐桌|座位|位子)");
            Pattern numPattern =  Pattern.compile("(?<num>[零一二三四五六七八九十0-9]{1,2})(?:.*)(?=|个人|餐位|餐桌|座位|位子)");
            String query = "我想订俩个人仨个人的座位";
            query = "我们三个人想吃饭帮我们预订个位子吧";
            String s1 = twoPattern.matcher(query).replaceFirst("两");
            String normQuery = query
                    .replaceFirst("俩(?=个人|餐位|餐桌|座位|位子)","两")
                    .replaceFirst("仨(?=个人|餐位|餐桌|座位|位子)","三");
            System.out.println("out:"+normQuery);
            System.out.println("s1:"+s1);
            Matcher matcher = numPattern.matcher(query);
            System.out.println("s2:"+matcher.replaceFirst("XXX"));
            System.out.println("==================");
            query = "我们三个人想吃饭帮我们预订个位子吧";
            query = "我们三个人想吃饭帮我们";
            String arr[]={ "我们三个人想吃饭帮我们预订个位子吧",
                    "在美团上帮我预定下午五点在五彩城院落的三餐位",
                    "帮我们四个人预约一下7月7号五彩城将太无二的座位",
                    "给我预约一个我们六个人能坐下的餐位",
                    "给我用美团预定晚上八点晓寿司五彩城店的位子我们六个人",
                    "用美团帮我们六个人预定一下五彩城小吊梨汤的位子吧",
                    "找个饭店预订一下餐位我们五个人",
                    "给我用美团订五彩城院落的四人桌座位",
                    "用美团帮我们四个人找个饭店订个位子",
                    "用美团给我们四个订一下7月6号的餐位"
            };
            Pattern numPattern2 = Pattern.compile(".*(?<num>[零一二两三四五六七八九十0-9]{1,2})(个人|人|个).*(?=餐位|餐桌|座位|位子).*");
            Pattern numPattern3 = Pattern.compile(".*(?=餐位|餐桌|座位|位子).*(?<num>[零一二两三四五六七八九十0-9]{1,2})(个人|人|个).*");
            Pattern numPattern4 = Pattern.compile(".*(?<num>[零一二两三四五六七八九十0-9]{1,2})(?=餐位|餐桌|座位|位子).*");
            for(String str:arr) {
                Matcher matcher2 = numPattern2.matcher(str);
                Matcher matcher3 = numPattern3.matcher(str);
                Matcher matcher4 = numPattern4.matcher(str);
                if (matcher2.find()) {
                    System.out.println("query:" + str + " cnt:" + matcher2.group("num"));
                }else if(matcher3.find()){ System.out.println("query:" + str + " cnt:" + matcher3.group("num"));
                }else if(matcher4.find()){ System.out.println("query:" + str + " cnt:" + matcher4.group("num"));
                }else{
                    System.out.println("not found:"+str);
                }
            }

            System.exit(-1);
        }
        {
            System.out.println(StringUtils.isEmpty("    ")); // false
            System.out.println(StringUtils.isBlank("    ")); // true, 如果只有空白字符，则会为true
            System.out.println(StringUtils.isEmpty(" a   ")); // false
            System.out.println(StringUtils.isBlank(" a   ")); // false
        }
        {
            Map<String,String> m1=new HashMap<String,String>();
            m1.put("k1",null);
            String v1 = m1.getOrDefault("k1","default");
            System.out.println("v1:"+v1);
            System.out.println("v1:"+ Optional.ofNullable(m1.get("k1")).orElse("default"));
            System.out.println("v2:"+ m1.get("k2"));
            System.out.println("v2:"+ Optional.ofNullable(m1.get("k2")).orElse("default"));
        }
        {
            String str="alarm_小狗的声音";
            String s1 = generalNormalize(str);
            System.out.println("s1:"+s1);
            System.out.println("s1:"+generalNormalize("我们,来个")); // 我们来个
            System.out.println("s1:"+generalNormalize("我们，来个")); // 我们来个
            System.out.println("s1:"+generalNormalize("我们。来个")); // 我们来个
            System.out.println("s1:"+generalNormalize("我们 来个")); // 我们来个
            System.out.println("用美团预定明天十二点回龙观潇湘码头五个人的座位".length()); // 23
        }

        {
            TestClass t1 = new TestClass(1, scala.Option$.MODULE$.apply("xx"));
            System.out.println("id:"+t1.id());
            System.out.println("name:"+t1.name());
            System.out.println("name.get:"+t1.name().get());
            TestClass t2 = new TestClass(1, scala.Option$.MODULE$.apply(null)); // 赋值为None
            System.out.println("name:"+t2.name());
            System.out.println("name is defined:"+t2.name().isDefined());
            System.out.println("name is empty:"+t2.name().isEmpty());
        }
        {
            System.out.println(ActionType.takeout_book_restaurant.name()); // takeout_book_restaurant
            System.out.println(ActionType.takeout_book_restaurant.toString()); // takeout_book_restaurant
            System.out.println(ActionType.takeout_book_restaurant.getValue()); // takeout_food_book_by_restaurant_name
            ActionType actionType = ActionType.valueOf("takeout_book_restaurant");
            System.out.println("type:"+ actionType); // takeout_book_restaurant
            System.out.println("type name:"+ actionType.name()); // takeout_book_restaurant
            System.out.println("type value:"+ actionType.value); // takeout_food_book_by_restaurant_name
            System.out.println("type get value:"+ actionType.getValue()); // takeout_food_book_by_restaurant_name
            try {
                System.out.println("type2:" + ActionType.valueOf("takeout_food_book_by_restaurant_name")); // throw error
            }catch (Exception e){
                System.out.println("catch errror");
            }
        }
        {
            String str = "miui_voiceassist";

            AppType appType = AppType.parseFromString(str); // VOICE_ASSIST
            System.out.println("appType:" + appType); // VOICE_ASSIST
            System.out.println("equal:" + AppType.VOICE_ASSIST.equals(appType)); // true
            System.out.println("equal:" + (AppType.MIUI_PAD == appType)); // false
            switch (appType) {
                case VOICE_ASSIST:
                    System.out.println("switch voice_assist"); // switch voice_assist
                    break;
                case MIUI_PAD:
                    System.out.println("switch pad");
                    break;
            }

            System.out.println(" toString:" + appType.toString()); // VOICE_ASSIST
            System.out.println("name:"+appType.name()); // VOICE_ASSIST
            System.out.println(" getValue:" + appType.getValue()); // miui_voiceassist
            AppType appType2 = AppType.valueOf("VOICE_ASSIST"); // VOICE_ASSIST
            System.out.println(" appType2:" + appType2.getValue()); // appType2:miui_voiceassist
            System.out.println(" appType2:" + appType2.name()); // VOICE_ASSIST
            System.out.println("--------------");
        }

        {
            String str="100";
            int x = Integer.decode(str); // 内部会调用Integer.valueOf()，有更多的格式选择
            System.out.println("x:"+x);
        }

        {
            String s1= "阁里香（解放大路二店）";
            System.out.println("length:"+ s1.length());
            System.out.println(s1.hashCode());
        }
        {
            long t1= Long.MAX_VALUE;
            System.out.println("out:"+t1);
        }
        {
            String s1= "阁里香（解放大路二店）s234sdfsd;fjsdf;lsdfjsdlfsdfsdl;fsdf;sdfsd;lf";
            long num =  HashNumber.BKDRHash32(s1);
            System.out.println("bkhash32:"+ num);
            System.out.println("bkhash64:"+ HashNumber.BKDRHash64(s1));
        }
    }
}
