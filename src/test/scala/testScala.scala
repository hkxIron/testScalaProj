import java.util
import java.util.{Collections, Comparator, List}
import java.util.regex.Pattern

import com.google.common.collect.Lists
import old.prior
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks
import scala.util.matching.Regex
import scala.collection.JavaConverters._

class testScala extends FunSuite{
  test("test seq") {
    val tt = Seq(1,2,3,4,5,6)
    val res = tt.flatMap{x=>
      if(x<10) {
        None
      } else{
        Some(-x)
      }
    }
    println(res)
  }
    test("test sort") {
       val myList = Seq("temporary", "type", "animal", "tt").toList.asJava // scala里的list是不可以改变的
        /*Collections.sort(myList,
            new Comparator[String](){
                @Override
                def compare(s1:String, s2:String):Int = { // 所以排序时会出现错误
                    if (old.prior.slotPrior.containsKey(s1) && old.prior.slotPrior.containsKey(s2))
                        -old.prior.slotPrior.get(s1).compareTo(old.prior.slotPrior.get(s2))
                    else 0
                }
            }
        )
        println("my_list", myList)
        */
      /*
     myList.sort( new Comparator[String](){
          @Override
          def compare(s1:String, s2:String):Int = { // 所以排序时会出现错误
              if (old.prior.slotPrior.containsKey(s1) && old.prior.slotPrior.containsKey(s2))
                  -old.prior.slotPrior.get(s1).compareTo(old.prior.slotPrior.get(s2))
              else 0
          }
      }
      */
  println("my_list", myList)

val myList2 = myList.asScala.sortWith((s1,s2)=> if(prior.slotPrior.containsKey(s1)&&prior.slotPrior.containsKey(s2)) {
    -prior.slotPrior.get(s1).compareTo(prior.slotPrior.get(s2)) <=0
    } else{
        false
    })
println("my_list2", myList2)

val myList3 = myList.asScala.sortWith((s1,s2)=>
    -prior.slotPrior.getOrDefault(s1, 1).compareTo(prior.slotPrior.getOrDefault(s2, 1)) <=0
)
println("my_list2", myList3)
}

test("测试scala group"){
val tt = Seq(1->"a",2->"a", 3->"b", 4->"b")
println("before:", tt)
val after = tt.map(x=>x._2 -> x._1)
  .groupBy(_._1)
  .map{case x=>
    x._1 -> x._2.map(_._2)
  }
println("after:", after)
}

test("测试正则表达式") {
val patternSeq = Seq(
    "^(.+)(怎么|如何|咋)叫$",
    "^(?:我[想要]?听听?|有没有|学习?个?|(?:循环)?播?放?)(.+?)的?(?:声音|叫声?)$"
).map(x=> new Regex(x))
val strs = Seq("循环播放小鸟的声音", "我听听大象的声音", "小狗怎么叫", "你不要", "你好啊")
val ss =strs.flatMap{str=>
    val tt= patternSeq.flatMap(_.findFirstMatchIn(str))
    tt
}.map{x=>
   x -> x.group(1)
}
println("query obj:", ss.mkString("\n"))

//val query = strs(0)
val query = "qxcdfsdfs"
val findObj = patternSeq.flatMap(_.findFirstMatchIn(query)).map(_.group(1)).mkString("|")

println("findObj:"+findObj+ s" obj is none:${findObj.isEmpty}")
}

test("将两个一维数组，拼成一个二维数组") {
println("unit test, hello world!")
val xx = "aa,bb,cc,dd,ee".split(",")
val yy = Seq(1,2,3,4)
val xy = xx.flatMap {
    x => yy.map(y=>x->y)
}
println(xy.mkString("\n"))

/**
  * (aa,1)
  * (aa,2)
  * (aa,3)
  * (aa,4)
  * (bb,1)
  * (bb,2)
  * (bb,3)
  * (bb,4)
  * (cc,1)
  * (cc,2)
  * (cc,3)
  * (cc,4)
  * (dd,1)
  * (dd,2)
  * (dd,3)
  * (dd,4)
  * (ee,1)
  * (ee,2)
  * (ee,3)
  * (ee,4)
  */
}

test("white_noise"){
val whiteNoiseToken: util.List[String] = Lists.newArrayList("白噪声", "白噪", "白噪音")
val pattern = whiteNoiseToken.toArray.mkString("|")
val query = "我要听白噪声啊"
println(s"query: $query pattern: $pattern")
val is_general = Pattern.compile(pattern).matcher(query).find()
println(s"is_general: $is_general")
}

test("enume"){
object PlayModeType extends Enumeration {
    // single:单曲循环 random:随机播放 list_loop:列表循环 seq_loop:顺序播放
    val single, random, list_loop, seq_loop = Value
}
val mode = PlayModeType.list_loop.toString
println(s"mode: $mode")
}

test("是否连续") {
val intents = ArrayBuffer(
    Some(("下雨的声音", "voice", 0.95)),
    Some(("下雨的声音", "voice", 0.95)),
    Some(("下雨的声音", "voice", 0.95))
)
val num = 2

var voiceCnt = 0
var equalCnt = 0
val breaks = new Breaks
breaks.breakable {
    for (item <- intents) {
        if (item.isDefined && item.get._2 == "voice") {
            voiceCnt += 1
            if (item.get._1 == "下雨的声音") {
                equalCnt += 1
            }
        } else {
            breaks.break()
        }
    }
}
println(s"equalCnt :$equalCnt")
if (equalCnt == num) (true, voiceCnt) else (false, voiceCnt)
}

test("test") {
val tt= new Array[String](0) // 代表中0代表length, length代表array的长度
}


}
