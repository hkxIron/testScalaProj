import org.apache.commons.collections.bag.SynchronizedSortedBag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testRegx {
    enum ObjType{ english, math, computer };

    public static void main(String[] args){
        {
            String[] blackListPattern = {"为什么", "你好", "我想"};
            StringBuffer sb = new StringBuffer();
            for(String blackPattern: blackListPattern){
                sb.append(blackPattern+"|");
            }
            if(sb.length()>0) {
                sb.setLength(sb.length() - 1);
                sb.insert(0, "(");
                sb.append(")");
            }
            Pattern blackPattern = Pattern.compile(sb.toString());
            String query = "我在看为什么你们";
            Matcher matcher = blackPattern.matcher(query);
            System.out.println("find:"+matcher.find());
        }

        {
            String s = "2015-10-26";
            Pattern p = Pattern.compile("(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})");
            Matcher m = p.matcher(s);
            if (m.find()) {
                System.out.println("year: " + m.group("year")); //年
                System.out.println("month: " + m.group("month")); //月
                System.out.println("day: " + m.group("day")); //日

                System.out.println("year: " + m.group(1)); //第一组
                System.out.println("month: " + m.group(2)); //第二组
                System.out.println("day: " + m.group(3)); //第三组
            }
        }
        {
            System.out.println("====================");
            System.out.println("测试贪心与非贪心");
            // 按指定模式在字符串查找
            String line = "This order was placed for QT3000! OK?";
            String greedyPattern = "(?:.*)(30+)(.*)";
            String nonGreedyPattern = "(?:.*)(30+?)(.*)"; // 非贪婪模式
            Matcher m1 = Pattern.compile(greedyPattern).matcher(line);
            System.out.println("测试贪心");
            if (m1.find()) {
                System.out.println("Found value: " + m1.group(0) );
                System.out.println("Found value: " + m1.group(1) ); // 3000
            } else {
                System.out.println("NO MATCH");
            }

            System.out.println("测试非贪心");
            m1 = Pattern.compile(nonGreedyPattern).matcher(line);
            if (m1.find()) {
                System.out.println("Found value: " + m1.group(0) );
                System.out.println("Found value: " + m1.group(1) ); // 30
            } else {
                System.out.println("NO MATCH");
            }
        }
        {
            System.out.println("====================");
            String []strs = {"循环播放小鸟的声音","我听听大象的声音","你好", "你不要","你好啊" };
            // (.+?)表示非贪婪匹配,匹配结果: 大象, (.+)?会贪婪匹配：大象的
            String pattern = "^(?:我[想要]?听听?|有没有|学习?个?|(?:循环)?播?放?)(.+?)的?(?:声音|叫声?)$";
            Pattern compilePattern = Pattern.compile(pattern);
            for(String str:strs) {
                Matcher preSufPattern = compilePattern.matcher(str);
                if (preSufPattern.find()) {
                    System.out.println("group count:" + preSufPattern.groupCount());
                    String noPreSufStr = preSufPattern.group(1);
                    System.out.println("group:" + noPreSufStr);
                }
                else {
                    System.out.println("no match query:" + str);
                }
            }
            //System.exit(-1);
        }

        {
            System.out.println("====================");
            String []strs = {"你给我","放鞭炮","你好", "你不要","你好啊" };
            String pattern = "(^*鞭炮$|^你给我$|^你好么?$)";
            Pattern compilePattern = Pattern.compile(pattern);
            for(String str:strs) {
                Matcher preSufPattern = compilePattern.matcher(str);
                if (preSufPattern.find()) {
                    System.out.println("group count:" + preSufPattern.groupCount());
                    String noPreSufStr = preSufPattern.group(1);
                    System.out.println("group:" + noPreSufStr);
                }
                else {
                    System.out.println("no match query:" + str);
                }
            }
            //System.exit(-1);
        }
        {
            System.out.println("====================");
            String str = "你给我放鞭炮";
            String pattern = "^(?:小知同学|小知)?(?:你能不能|请|你？让我|你？给我们?)?(?:再放|在放|你?会?放点?)(?:[一二俩两三仨四五])?(?:个)?(.+?)(?:来?听听|好吗|好嘛|吗|啊|吧|呗)?$";
            Matcher preSufPattern = Pattern.compile(pattern).matcher(str);
            if (preSufPattern.find()) {
                System.out.println("group count:" + preSufPattern.groupCount());
                String noPreSufStr = preSufPattern.group(1);
                System.out.println("group:" + noPreSufStr);
            }
        }
        {
            System.out.println("====================");
            String xx = null;
            String str = String.format("test str: %s",xx);
            System.out.println(str);
        }
        {
            System.out.println("====================");
            String str = "潮汐|下雨";
            // str = "潮汐下雨";
            String[] arr = str.split("\\|");
            System.out.println(arr.length);
            int randomIndex = (int)(arr.length * Math.random());
            System.out.println(arr[randomIndex]);
        }

        {
            System.out.println("====================");
            //String str = "大白象";
        /* (?:pattern)
        匹配 pattern 但不捕获该匹配的子表达式，即它是一个非捕获匹配，不存储供以后使用的匹配。
        这对于用"or"字符 (|) 组合模式部件的情况很有用。例如，'industr(?:y|ies) 是比 'industry|industries' 更经济的表达式。

        . 匹配除"\r\n"之外的任何单个字符。若要匹配包括"\r\n"在内的任意字符，请使用诸如"[\s\S]"之类的模式。
         */
            String str = "熊猫";
            Matcher preSufPattern = Pattern.compile("(?:大白|大|小|棕|黑|白|黄|灰|花|公|母|老)?(.+?)(?:儿)?$").matcher(str);
            if (preSufPattern.find()) {
                System.out.println("group count:" + preSufPattern.groupCount());
                String noPreSufStr = preSufPattern.group(1);
                System.out.println("group:" + noPreSufStr);
            }
        }

        {
            System.out.println("====================");
            // 按指定模式在字符串查找
            String line = "This order was placed for QT3000! OK?";
            String pattern = "(\\D*)(\\d+)(.*)";
            // 创建 Pattern 对象
            Pattern r = Pattern.compile(pattern);
            // 现在创建 matcher 对象
            Matcher m = r.matcher(line);
            if (m.find()) {
                System.out.println("Found value: " + m.group(0) );
                System.out.println("Found value: " + m.group(1) );
                System.out.println("Found value: " + m.group(2) );
                System.out.println("Found value: " + m.group(3) );
            } else {
                System.out.println("NO MATCH");
            }
        }

        {
            System.out.println("====================");
            // 按指定模式在字符串查找
            String line = "This order \\ was placed for QT3000! OK?";
            String pattern = "(?:.*)(\\\\)(.*)"; // 表示匹配一个 \
            // 创建 Pattern 对象
            Pattern r = Pattern.compile(pattern);
            // 现在创建 matcher 对象
            Matcher m = r.matcher(line);
            if (m.find()) {
                System.out.println("Found value: " + m.group(0) );
                System.out.println("Found value: " + m.group(1) ); // 输出：\
            } else {
                System.out.println("NO MATCH");
            }
        }



        {
            System.out.println("output type: " + ObjType.computer.toString() );
            System.out.println("output type: " + ObjType.computer.equals("computer")); // false
            System.out.println("output type: " + ObjType.computer.equals(ObjType.computer)); // true
        }

        {
            System.out.println("====================");
            String line = "Windows 2000";
            String pattern = "Windows (?=95|98|NT|2000)";
            Matcher m1 = Pattern.compile(pattern).matcher(line);
            System.out.println("执行正向预测先行搜索的子表达式");
            if (m1.find()) {
                System.out.println("Found value: " + m1.group(0));
            } else {
                System.out.println("NO MATCH");
            }

            System.out.println("执行反向预测先行搜索的子表达式");
            line = "Windows 3.1";
            pattern = "Windows (?!95|98|NT|2000)";
            m1 = Pattern.compile(pattern).matcher(line);
            if (m1.find()) {
                System.out.println("Found value: " + m1.group(0));
            }
        }

        {
            /**
             * 可以看到这个例子是使用单词边界，以确保字母 "c" "a" "t" 并非仅是一个较长的词的子串。它也提供了一些关于输入字符串中匹配发生位置的有用信息。
             *
             * Start 方法返回在以前的匹配操作期间，由给定组所捕获的子序列的初始索引，end 方法最后一个匹配字符的索引加 1。
             */
            System.out.println("====================");
            System.out.println("测试start end方法");
            String REGEX = "\\bcat\\b"; // 边界cat边界
            String INPUT = "cat cat cat cattie cat";

            Pattern p = Pattern.compile(REGEX);
            Matcher m = p.matcher(INPUT); // 获取 matcher 对象
            int count = 0;

            while(m.find()) {
                count++;
                System.out.println("Match number "+count);
                System.out.println("start(): "+m.start());
                System.out.println("end(): "+m.end());
            }
        }

        // --------------
        {
            System.out.println("测试match lookingAt");
            System.out.println("====================");
            final String REGEX = "foo";
            final String INPUT = "fooooooooooooooooo";
            final String INPUT2 = "ooooofoooooooooooo";
            Pattern pattern;
            Matcher matcher;
            Matcher matcher2;
            pattern = Pattern.compile(REGEX);
            matcher = pattern.matcher(INPUT);
            matcher2 = pattern.matcher(INPUT2);

            System.out.println("Current REGEX is: "+ REGEX);
            System.out.println("Current INPUT is: "+ INPUT);
            System.out.println("Current INPUT2 is: "+ INPUT2);

            System.out.println("lookingAt(): "+matcher.lookingAt());
            System.out.println("matches(): "+matcher.matches());
            System.out.println("lookingAt(): "+matcher2.lookingAt());
        }

        {
            System.out.println("正则替换");
            System.out.println("====================");
            String REGEX = "dog";
            String INPUT = "The dog says meow. " +
                    "All dogs say meow.";
            String REPLACE = "cat";
            Pattern p = Pattern.compile(REGEX);
            // get a matcher object
            Matcher matcher= p.matcher(INPUT);
            INPUT = matcher.replaceAll(REPLACE);
            System.out.println(INPUT);
        }

        {
            System.out.println("appendReplacement");
            System.out.println("====================");
            String REGEX = "a*b";
            String INPUT = "aabfooaabfooabfoobkkk";
            String REPLACE = "-";
            Pattern p = Pattern.compile(REGEX);
            Matcher m = p.matcher(INPUT); //　将所有的a*b替换成-
            StringBuffer sb = new StringBuffer();
            while(m.find()){
                m.appendReplacement(sb,REPLACE);
            }
            m.appendTail(sb);
            System.out.println(sb.toString());
        }

        {
            // |作用域为所有连续的字（而不是其中的一个字），且不会被?断开
            // ?只作用于前面一个字符
            // ? 比|结合性更强, "让我|你?给我们?"解释成：（让我　or 你?(给我)们？
            System.out.println("====================--");
            String str = "你让我"; // 1
            str = "你能不能"; //1
            str ="请";// 1
            str ="让我"; // 1
            str ="你让我"; // 1
            str = "你给我们"; // 1
            str = "给我们"; // 1
            str = "给我"; // 1, ?作用于前面一个字符
            str = "给"; // 0
            str = "你"; // 0
            str = "一个"; // 0
            str = "给你一个"; // 1
            str = "给你一"; // 1
            String pattern;
           // String pattern;
            pattern = "^(你能不能|请|你?让我|你?给我们?|给你来?一个?)(?:.?)$"; //
            Matcher preSufPattern = Pattern.compile(pattern).matcher(str);
            if (preSufPattern.find()) {
                System.out.println("group count:" + preSufPattern.groupCount());
                String noPreSufStr = preSufPattern.group(1);
                System.out.println("group:" + noPreSufStr);
            }
        }
        {
            System.out.println("====================");
            String str = "";
            // 匹配结果，往往还与pattern里的先后顺序有关
            String pattern1 = "^你?会不?会?(BBOX|bboxman|bbox|逼爆渴斯|逼爆克斯|bboss|逼boss|比boss|逼爆克死|逼抱死|BBOXMAN|逼爆case|逼爆是|被boss|唱bboss|唱壁报克斯|逼爆|口技|呼噜|喷嚏|打嗝|咳嗽)(?:吗|啊|嘛)?";
            String pattern2 = "^你?会不?会?(BBOXMAN|bboxman|BBOX|bbox|逼爆渴斯|逼爆克斯|bboss|逼boss|比boss|逼爆克死|逼抱死|逼爆case|逼爆是|被boss|唱bboss|唱壁报克斯|逼爆|口技|呼噜|喷嚏|打嗝|咳嗽)(?:吗|啊|嘛)?";
            str = "你会BBOXMAN";
            {
                Matcher m1 = Pattern.compile(pattern1).matcher(str);
                if(m1.find()) { // 记住：只有在find时才会去匹配,如果不加find,就不会执行匹配动作
                    System.out.println("group count:" + m1.groupCount());
                    System.out.println("group 1:" + m1.group(1));
                    System.out.println("all match group:" + m1.group()); // group zero denotes the entire pattern
                }
            }
            System.out.println();
            {
                Matcher m1 = Pattern.compile(pattern2).matcher(str);
                if(m1.find()) {
                    System.out.println("group count:" + m1.groupCount());
                    System.out.println("group 1:" + m1.group(1));
                    System.out.println("all match group:" + m1.group()); // roup zero denotes the entire pattern
                }
            }
            /**
             * group count:1
             * group 1:BBOX
             * all match group:你会BBOX
             *
             * group count:1
             * group 1:BBOXMAN
             * all match group:你会BBOXMAN
             */

            {
                Pattern isRepeatPattern = Pattern.compile("循环|重复|持续");
                String query ="循环播放";
                query ="我想持续播放";
                if(isRepeatPattern.matcher(query).find()){
                    System.out.println("循环 true");
                }


            }
        }

        {
            System.out.println("====================--");
            String str = "老虎怎么叫"; // 1
            String pattern;
            // String pattern;
            pattern = "(叫|笑|哭)"; //
            Matcher matcher = Pattern.compile(pattern).matcher(str);
            if (matcher.find()) {
                System.out.println("group count:" + matcher.groupCount());
                String noPreSufStr = matcher.group(1);
                System.out.println("group:" + noPreSufStr);
            }
        }

        {
            System.out.println("====================--");
            String str = "小宝贝怎么哭"; // 1
            System.out.println("query："+str);
            String pattern;
            // String pattern;
            pattern = "(?:小爱|小爱同学|小爱朋友)?(.+?)(怎么|如何|怎样)(叫|大叫|哭|哭|大哭|笑|大笑)(吧|呗|啵|啦|了|咯|啰|吗|嘛|么|呢|啊|好不好|呀)?"; //
            Matcher matcher = Pattern.compile(pattern).matcher(str);
            if (matcher.find()) {
                System.out.println("group count:" + matcher.groupCount());
                String noPreSufStr = matcher.group(1);
                System.out.println("group:" + noPreSufStr);
            }
        }

        {
            // blog: https://www.jb51.net/article/79715.htm
            /**
             * 很多正则引擎都支持命名分组，java是在java7中才引入这个特性，语法与.Net类似（.Net允许同一表达式出现名字相同的分组，java不允许）。
             * 命名分组很好理解，就是给分组进行命名。下面简单演示一下java中如何使用以及注意事项。
             * 1.正则中定义名为NAME的分组
             * (?<NAME>X)
             * 这里X为我们要匹配的内容，注意，在这个命名不能重复，名字也不能以数字开头！
             * 2.反向引用NAME组所匹配到的内容
             * \k<NAME>
             * 注意，反向引用是针对组所匹配到的内容，而非组的表达式。
             * 3.替换中，引用组NAME中捕获到的字符串
             * ${NAME}
             * 4.获取NAME组捕获的字符串
             * group(String NAME)
             * 注意：也可以使用序号对命名捕获进行引用，序号从1开始，0为正则的完整匹配结果。
             */
            String s = "2015-10-26";
            Pattern p = Pattern.compile("(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})");
            Matcher m = p.matcher(s);
            if (m.find()) {
                System.out.println("year: " + m.group("year")); //年
                System.out.println("month: " + m.group("month")); //月
                System.out.println("day: " + m.group("day")); //日

                System.out.println("year: " + m.group(1)); //第一组
                System.out.println("month: " + m.group(2)); //第二组
                System.out.println("day: " + m.group(3)); //第三组
                System.out.println("all group: " + m.group(0)); // 正则的完整匹配结果
            }

            System.out.println(s.replaceAll("(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})", "${day}-${month}-${year}")); //将 年-月-日 形式的日期改为 日-月-年 形式
        }
    }
}
