import org.apache.commons.collections.bag.SynchronizedSortedBag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
// blog:https://segmentfault.com/a/1190000009162306
// http://www.runoob.com/java/java-regular-expressions.html
public class testRegx {
    enum ObjType{ english, math, computer };

    /**
     * \d	匹配一个数字，是 [0-9] 的简写
     * \D	匹配一个非数字，是 [^0-9] 的简写
     * \s	匹配一个空格，是 [ \t\n\x0b\r\f] 的简写
     * \S	匹配一个非空格
     * \w	匹配一个单词字符（大小写字母、数字、下划线），是 [a-zA-Z_0-9] 的简写
     * \W	匹配一个非单词字符（除了大小写字母、数字、下划线之外的字符），等同于 [^\w]
     */

    public static void main(String[] args){
        {
            Pattern storePattern = Pattern.compile("(.+)(?:超市|便利店)");
            String[] arr={"超市", "家乐福超市", "7天超市", "超市","超市在哪里","来个屁吧"};
            for(String str:arr){
                Matcher matcher = storePattern.matcher(str);
                if(matcher.find()){
                    /*
                    query:家乐福超市 store:家乐福 count:1
                    query:7天超市 store:7天 count:1
                    */
                    System.out.println("query:"+str+" store:"+matcher.group(1)+ " count:"+matcher.groupCount());
                }
            }
            System.exit(-1);
        }
        {
            Pattern voiceActionPattern = Pattern.compile("(叫|笑|哭|放个屁|放屁|屁)");
            String[] arr={"我让你放个屁", "你放屁吧", "放屁好嘛","来个屁吧"};
            for(String str:arr){
                Matcher matcher = voiceActionPattern.matcher(str);
                if(matcher.find()){
                    System.out.println("query:"+str+" action:"+matcher.group(1)+ " count:"+matcher.groupCount());
                }
            }
            System.exit(-1);
        }
        {
            String NUM = "$NUM$";
            String str="我们在1973年时";
            str = str.toLowerCase();
            Pattern p = Pattern.compile("\\d+");
            Matcher matcher = p.matcher(str);
            while (matcher.find()) {
               //str = matcher.replaceAll(NUM);
                System.out.println(matcher.group());
                System.out.println("replaced:"+str.replace(matcher.group(),NUM));
            }
            System.out.println(str);
        }
        {
            // 匹配 1990到2017间的所有年份
            String str = "1990\n2010\n2001\n2017";
            // 这里应用了 (?m) 的多行匹配模式，只为方便我们测试输出
            // "^1990$|^199[1-9]$|^20[0-1][0-6]$|^2017$" 为判断 1990-2017 正确的正则表达式
            Pattern pattern = Pattern.compile("(?m)^1990$|^199[1-9]$|^20[0-1][0-6]$|^2017$");
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                System.out.println(matcher.group());
            }
        }
        {
            // [\u4e00-\u9fa5]+ 代表匹配中文字,匹配中文
            String str = "test:閑人到人间";
            Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                System.out.println(matcher.group());
            }
        }
        {   System.out.println("wxj".matches("wxj"));
            System.out.println("----------");

            String[] array = "w x j".split("\\s");
            for (String item : array) {
                System.out.println(item);
            }
            System.out.println("----------");

            System.out.println("w x j".replaceFirst("\\s", "-"));
            System.out.println("----------");
            System.out.println("w x j".replaceAll("\\s", "-"));}
            System.out.println("===============================");
        {
            String str = "<img  src='aaa.jpg' /><img src=bbb.png/><img src=\"ccc.png\"/>" +
                    "<img src='ddd.exe'/><img src='eee.jpn'/>";
            // 这里我们考虑了一些不规范的 img 标签写法，比如：空格、引号
            Pattern pattern = Pattern.compile("<img\\s+src=(?:['\"])?(?<src>\\w+.(jpg|png))(?:['\"])?\\s*/>");
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                System.out.println(matcher.group("src"));
            }
            System.out.println("===============================");
        }

        {
            /**
             * (?=表达式) 零宽度正预测先行断言 表示匹配表达式前面的位置
             *
             * 例如，要匹配 cooking ，singing ，doing中除了ing之外的内容，只取cook, sing, do的内容，这时候的增则表达式可以用 [a-z]*(?=ing) 来匹配
             *
             * 注意：先行断言的执行步骤是这样的先从要匹配的字符串中的最右端找到第一个 ing (也就是先行断言中的表达式)然后 再匹配其前面的表达式，若无法匹配则继续查找第二个 ing 再匹配第二个 ing 前面的字符串，若能匹配则匹配，符合正则的贪婪性。
             *
             * 例如： .*(?=ing) 可以匹配 “cooking singing” 中的 “cooking sing” 而不是 cook
             */
            String line = "cooking singing";
            String pattern = ".*(?=ing)";
            Matcher matcher = Pattern.compile(pattern).matcher(line);
            System.out.println("执行正向预测先行搜索的子表达式");
            if (matcher.find()) {
                System.out.println("group count:"+matcher.groupCount()); //0,注意，它不占用 groupCount
                System.out.println("Found value:" + matcher.group(0)); // 匹配 “cooking singing” 中的 “cooking sing” 而不是 cook
                System.out.println("start:"+ matcher.start()); // inclusive = 11
                System.out.println("end:"+ matcher.end()); // exclusive = 19
            } else {
                System.out.println("NO MATCH");
            }
            System.out.println("===============================");
        }

        {
            /**
             * (?<=表达式) 零宽度正回顾后发断言 表示匹配表达式后面的位置
             *
             * 例如 (?<=abc).* 可以匹配 abcdefg 中的 defg
             *
             * 注意：后发断言跟先行断言恰恰相反 它的执行步骤是这样的：先从要匹配的字符串中的最左端找到第一个abc(也就是先行断言中的表达式)然后 再匹配其后面的表达式，若无法匹配则继续查找第二个 abc 再匹配第二个 abc 后面的字符串，若能匹配则匹配。
             *
             * 例如 (?<=abc).* 可以匹配 abcdefgabc 中的 defgabc 而不是 abcdefg
             */
            String line = "abcdefgabc";
            String pattern = "(?<=abc).*";
            Matcher matcher = Pattern.compile(pattern).matcher(line);
            if (matcher.find()) {
                System.out.println("group count:"+matcher.groupCount()); //0,注意，它不占用 groupCount
                System.out.println("Found value:" + matcher.group(0)); // 匹配 “cooking singing” 中的 “cooking sing” 而不是 cook
                System.out.println("start:"+ matcher.start()); // inclusive = 11
                System.out.println("end:"+ matcher.end()); // exclusive = 19
            } else {
                System.out.println("NO MATCH");
            }
            System.out.println("===============================");
        }

        {
            String line = "please use Windows 2000";
            String pattern = "Windows (?=95|98|NT|2000)";
            Matcher matcher = Pattern.compile(pattern).matcher(line);
            System.out.println("执行正向预测先行搜索的子表达式");
            if (matcher.find()) {
                System.out.println("group count:"+matcher.groupCount()); //0,注意，它不占用 groupCount
                System.out.println("Found value:" + matcher.group(0)); // Windows,注意，匹配的并不是 2000
                System.out.println("start:"+ matcher.start()); // inclusive = 11
                System.out.println("end:"+ matcher.end()); // exclusive = 19
            } else {
                System.out.println("NO MATCH");
            }

            System.out.println("\n执行反向预测先行搜索的子表达式");
            line = "please use Windows 3.1";
            pattern = "Windows (?!95|98|NT|2000)"; // 捕获后面不能是 91,98,NT,2000
            matcher = Pattern.compile(pattern).matcher(line);
            if (matcher.find()) {
                System.out.println("group count:"+matcher.groupCount()); //0,注意，它不占用 groupCount
                System.out.println("Found value:" + matcher.group(0)); // Windows
                System.out.println("start:"+ matcher.start()); // inclusive
                System.out.println("end:"+ matcher.end()); // exclusive
            }else {
                System.out.println("NO MATCH");
            }
            System.out.println("===================");
        }
        {
            String line = "please use Windows 3.1";
            String pattern = "Windows (?=95|98|NT|2000)";
            Matcher matcher = Pattern.compile(pattern).matcher(line);
            System.out.println("执行正向预测先行搜索的子表达式");
            if (matcher.find()) {
                System.out.println("group count:"+matcher.groupCount());
                System.out.println("Found value:" + matcher.group(0));
                System.out.println("start:"+ matcher.start()); // inclusive = 11
                System.out.println("end:"+ matcher.end()); // exclusive = 19
            } else {
                System.out.println("NO MATCH");
            }

            System.out.println("\n执行反向预测先行搜索的子表达式");
            line = "please use Windows 2000";
            pattern = "Windows (?!95|98|NT|2000)"; // 捕获后面不能是 91,98,NT,2000
            matcher = Pattern.compile(pattern).matcher(line);
            if (matcher.find()) {
                System.out.println("group count:"+matcher.groupCount());
                System.out.println("Found value:" + matcher.group(0));
                System.out.println("start:"+ matcher.start()); // inclusive
                System.out.println("end:"+ matcher.end()); // exclusive
            } else {
                System.out.println("NO MATCH");
            }
            System.out.println("===================");
        }
        {
            String str = "@wxj 你好啊";
            Pattern pattern = Pattern.compile("@(?<first>\\w+\\s)"); // 中文并未匹配到
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                System.out.println("group count:" + matcher.groupCount()); //
                System.out.println(matcher.group()); // 0
                System.out.println(matcher.group(1));
                System.out.println("start:"+ matcher.start()); // inclusive =0,针对文本str的下标而言
                System.out.println("end:"+ matcher.end()); // exclusive=5
                System.out.println(matcher.group("first"));
                System.out.println("sub:"+ str.substring(matcher.start(), matcher.end()));
                System.out.println("===================");
            }
        }
        {
            // 去除单词与 , 和 . 之间的空格
            String Str = "Hello , World .";
            //Str = " World .";
            String pattern = "(\\w)(\\s+)([.,])"; //
            // $0 匹配 `(\w)(\s+)([.,])` 结果为 `o空格,` 和 `d空格.`
            // $1 匹配 `(\w)` 结果为 `o` 和 `d`
            // $2 匹配 `(\s+)` 结果为 `空格` 和 `空格`
            // $3 匹配 `([.,])` 结果为 `,` 和 `.`
            Matcher matcher = Pattern.compile(pattern).matcher(Str);
            //System.out.println("find:" + matcher.find());
            while (matcher.find()) { // 如果不用while，则只会返回第一个匹配就停止
                System.out.println("$0:[" + matcher.group(0) + "]"); // o空格,
                System.out.println("$1:[" + matcher.group(1) + "]"); // o
                System.out.println("$2:[" + matcher.group(2) + "]"); // 空格
                System.out.println("$3:[" + matcher.group(3) + "]"); // ,
                System.out.println("group count:" + matcher.groupCount()); // 3
            }
            System.out.println(Str.replaceAll(pattern, "$1$3")); // Hello, World.
            System.out.println("===================");
        }
        {
            String str = "换#voice_unit#白噪声";
            str = "换白噪声";
            //Pattern pattern=Pattern.compile("(#general_xiaoai#)?(#general_pronoun#)?(#general.want|来段|换一?个|给(#general_pronoun#)?)?(#general_repeat#)?(#voice_play_action#)?(小米)?白噪[声音]?$");
            Pattern pattern=Pattern.compile("(#general_xiaoai#)?(#general_pronoun#)?(想|要|想要|让|使|叫)?(来一?段|换一?个|换$voice.unit|给(#general_pronoun#)?)?(#general_repeat#)?(#voice_play_action#)?(小米)?白噪[声音]?$");
            Matcher tokenMatcher = pattern.matcher(str);
            System.out.println(" "+ tokenMatcher);
            System.out.println("find:"+ tokenMatcher.find());
            System.out.println("group :"+ tokenMatcher.group(0));
            System.out.println("start:"+ tokenMatcher.start());
            System.out.println("end:"+ tokenMatcher.end());
            System.out.println("===================");
        }
        {
            String matchStr = "大小女儿";
            Matcher preSufPattern = Pattern.compile("(?:大白|大|小|棕|黑|白|黄|灰|花|公|母|老)?(.+?)(?:儿)?$").matcher(matchStr);
            if (preSufPattern.find()) {
               System.out.println("group 1: "+preSufPattern.group(1));
            }
            System.out.println("===================");
        }
        {
            System.out.println("====================");
            String []strs = {"爆竹声声", // match：爆竹
                    "爆竹", // no match
                    "爆竹声声声",// match:爆竹 （一直没想明白，这里为何是 “爆竹”）
                    "爆竹声", // match：爆竹
                    "爆竹的声", // match:爆竹的
                    "爆竹的响声", // match:爆竹的响
            };
            // (.+?)表示非贪婪匹配,匹配结果: 大象, (.+)?会贪婪匹配：大象的
            String pattern = "(.+?)声";
            Pattern compilePattern = Pattern.compile(pattern);
            for(String str:strs) {
                Matcher preSufPattern = compilePattern.matcher(str);
                //System.out.println("query:"+str);
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
