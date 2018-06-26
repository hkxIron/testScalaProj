import java.io.{File, PrintWriter}

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FunSuite

import scala.collection.mutable
// blog: http://codingjunkie.net/spark-combine-by-key/

/**
  * Created by bbejeck on 7/31/15.
  *
  * Example of using AggregateByKey
  */
class CombineByKeyTest extends FunSuite {

    test("testCombine") {

        Logger.getLogger("org.apache").setLevel(Level.ERROR)
        Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

        type ScoreCollector = (Int, Double)
        type PersonScores = (String, (Int, Double))

        val sc = new SparkContext(new SparkConf().setAppName("CombineByKey Examples").setMaster("local[1]"))

        val keysWithValuesList = Array("foo=A", "foo=A", "foo=A", "foo=A", "foo=B", "bar=C", "bar=D", "bar=D")

        val initialScores = Array(("Fred", 88.0), ("Fred", 95.0), ("Fred", 91.0), ("Wilma", 93.0), ("Wilma", 95.0), ("Wilma", 98.0))

        val data = sc.parallelize(keysWithValuesList)
        val wilmaAndFredScores = sc.parallelize(initialScores).cache()

        //Create key value pairs
        val kv = data.map(_.split("=")).map(v => (v(0), v(1))).cache()

        val createCombiner = (v: String) => mutable.HashSet(v)
        val combiningFunction = (s: mutable.HashSet[String], v: String) => s += v
        val mergeCombiners = (p1: mutable.HashSet[String], p2: mutable.HashSet[String]) => p1 ++= p2

        val uniqueValues = kv.combineByKey(createCombiner, combiningFunction, mergeCombiners)

        val createScoreCombiner = (score: Double) => (1, score)

        val scoreCombiner = (collector: ScoreCollector, score: Double) => {
            val (numberScores, totalScore) = collector
            (numberScores + 1, totalScore + score)
        }

        val scoreMerger = (collector1: ScoreCollector, collector2: ScoreCollector) => {
            val (numScores1, totalScore1) = collector1
            val (numScores2, totalScore2) = collector2

            (numScores1 + numScores2, totalScore1 + totalScore2)
        }

        val averagingFunction = (personScore: PersonScores) => {
            val (name, (numberScores, totalScore)) = personScore
            (name, totalScore / numberScores)
        }

        val scores = wilmaAndFredScores.combineByKey(createScoreCombiner, scoreCombiner, scoreMerger)
        val averageScores = scores.collectAsMap().map(averagingFunction)

        val writer = new PrintWriter(new File("test_combine.txt"))
        writer.println("CombinByKey unique Results")

        val uniqueResults = uniqueValues.collect()
        for (indx <- uniqueResults.indices) {
            val r = uniqueResults(indx)
            writer.println(r._1 + " -> " + r._2.mkString(","))
        }

        writer.println("Average Scores using CombingByKey")
        averageScores.foreach((ps) => {
            val (name, average) = ps
            writer.println(name + "'s average score : " + average)
        })

        writer.close()
        /*
        Average Scores using CombingByKey
        Fred's average score : 91.33333333333333
        Wilma's average score : 95.33333333333333
        */
    }

}