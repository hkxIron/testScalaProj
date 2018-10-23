import javax.sound.midi.Soundbank;
import scala.Option;
import scala.Option$;
import scala.collection.JavaConverters;
import scala.concurrent.JavaConversions;

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

    public static void main(String[] args){
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
