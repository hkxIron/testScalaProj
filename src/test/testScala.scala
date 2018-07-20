import java.util
import java.util.List
import java.util.regex.Pattern

import com.google.common.collect.Lists
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks

class testScala extends FunSuite{
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
}