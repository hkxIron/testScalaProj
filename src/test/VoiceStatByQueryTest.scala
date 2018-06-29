import java.io.{File, PrintWriter}

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FunSuite

import scala.collection.mutable
// blog: http://codingjunkie.net/spark-combine-by-key/

/**
  * 统计不同query下的人均收听时长(去重)，总收听人数（去重），收听人次，总时长
  * 数据格式：
  * query user_id time
  * 如：
  * q1 u1  2.0
  * q1 u2  1.5
  * q1 u1  3.0
  * q2 u1  3.0
  * q2 u2  2.0
  */
class VoiceStatByQueryTest extends FunSuite {

    test("testStat") {

        Logger.getLogger("org.apache").setLevel(Level.ERROR)
        Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

        val sc = new SparkContext(new SparkConf().setAppName("CombineByKey Examples").setMaster("local[1]"))

        val initialScores = Array(
            ("q1", "u1", 88.0),
            ("q1", "u2", 90.0),
            ("q1", "u3", 95.0),
            ("q1", "u1", 45.0),
            //--------
            ("q2", "u1", 30.0),
            ("q2", "u2", 25.0),
            ("q2", "u3", 15.0),
            //-------
            ("q3", "u1", 75.0),
            ("q3", "u1", 65.0),
            ("q3", "u2", 90.0),
            ("q3", "u3", 5.0)
        )

        val logRDD:RDD[(String, (String, Double))] = sc.parallelize(initialScores)
                .map(x=>x._1->(x._2, x._3))
                .cache()

/*
        val createCombiner = (v: String) => mutable.HashSet(v) //为第一个单元素服务
        val combiningFunction = (s: mutable.HashSet[String], v: String) => s += v // 往集合加添加一个元素
        val mergeCombiners = (p1: mutable.HashSet[String], p2: mutable.HashSet[String]) => p1 ++= p2 // 两个集合进行合并
        */

        val writer = new PrintWriter(new File("test_combine.txt"))
        // userid加入hash,score用来求和，1用来计数
        val createScoreCombiner = (userID: String, score: Double) => (mutable.HashSet(userID), score) //为第一个单元素服务
        // 同一partition内元素进行合并
        type combinerValue = (String, Double)
        type ScoreCollector = (mutable.HashSet[String], Double) // userSet, totalScore,numberScores

        val combiner = (collector: ScoreCollector, value: combinerValue) => {
            val (userIDSet, totalScore) = collector
            val (newUserID, newScore) = value
            (userIDSet += newUserID , totalScore + newScore) // 第一个元素计数，第二个元素求和
        }

        // 不同parition的两个集合进行合并
        val merger = (collector1: ScoreCollector, collector2: ScoreCollector) => {
            val (userIDSet1, totalScore1) = collector1
            val (userIDSet2, totalScore2) = collector2
            (userIDSet1 ++= userIDSet2, totalScore1 + totalScore2)
        }

        type PersonScores = (String, (Int, Double)) // person -> (numberScores, totalScore)
        val averagingFunction = (personScore: PersonScores) => {
            val (name, (numberScores, totalScore)) = personScore
            (name, totalScore / numberScores)
        }
/*
        val scores:RDD[(String, (mutable.HashSet[String], Double, Int))]
            = logRDD.combineByKey(createScoreCombiner, combiner, merger)

        val averageScores = scores.collectAsMap().map(averagingFunction)

        writer.println("Average Scores using CombingByKey")
        averageScores.foreach(ps => {
            val (name, average) = ps
            writer.println(name + "'s average score : " + average)
        })

        writer.close()
        /*
        Average Scores using CombingByKey
        Fred's average score : 91.33333333333333
        Wilma's average score : 95.33333333333333
        */
        */
    }

}