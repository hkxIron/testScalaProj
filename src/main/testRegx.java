import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testRegx {
    enum ObjType{ english, math, computer };

    public static void main(String[] args){
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

    }
}
