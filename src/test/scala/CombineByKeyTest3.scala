import java.io.{File, PrintWriter}

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.scalatest.FunSuite

import scala.collection.mutable
// blog:https://www.edureka.co/blog/apache-spark-combinebykey-explained
/**
  *
  */
case class ScoreDetail(studentName: String, subject: String, score: Float)

class CombineByKeyTest3 extends FunSuite {

    test("testCombine") {
        Logger.getLogger("org.apache").setLevel(Level.ERROR)
        Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
        val sc = new SparkContext(new SparkConf().setAppName("CombineByKey Examples").setMaster("local[1]"))

        val scores = List(
            ScoreDetail("xiaoming", "Math", 98),
            ScoreDetail("xiaoming", "English", 88),
            ScoreDetail("wangwu", "Math", 75),
            ScoreDetail("wangwu", "English", 78),
            ScoreDetail("lihua", "Math", 90),
            ScoreDetail("lihua", "English", 80),
            ScoreDetail("zhangsan", "Math", 91),
            ScoreDetail("zhangsan", "English", 80))
        val scoresWithKey = for { i <- scores } yield (i.studentName, i)
        val scoresWithKeyRDD = sc.parallelize(scoresWithKey).cache() //.partitionBy(new HashPartitioner(3)).cache

        val writer = new PrintWriter(new File("test_combine3.txt"))
        writer.println(">>>> Elements in each partition")
        scoresWithKeyRDD.foreachPartition(partition => println(partition.length))
        // explore each partition...
        writer.println(">>>> Exploring partitions' data...")

        scoresWithKeyRDD.foreachPartition(
            partition => partition.foreach(
                item => println(item._2)))

        val avgScoresRDD = scoresWithKeyRDD.combineByKey(
            (x: ScoreDetail) => (x.score, 1) /*createCombiner*/,
            (acc: (Float, Int), x: ScoreDetail) => (acc._1 + x.score, acc._2 + 1) /*mergeValue*/,
            (acc1: (Float, Int), acc2: (Float, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2) /*mergeCombiners*/
            // calculate the average
        ).map( { case(key, value) => (key, value._1/value._2) })

        writer.println("final avg score:")
        avgScoresRDD.collect.foreach(writer.println)

        writer.close()
        /*
        Average Scores using CombingByKey
        Fred's average score : 91.33333333333333
        Wilma's average score : 95.33333333333333
        */
    }

}