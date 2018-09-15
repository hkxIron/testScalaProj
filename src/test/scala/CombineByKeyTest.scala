package com.scu.hkx.test
import java.io.{File, PrintWriter}

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
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

        val sc = new SparkContext(new SparkConf().setAppName("CombineByKey Examples").setMaster("local[1]"))
        val keysWithValuesList = Array("foo=A", "foo=A", "foo=A", "foo=A", "foo=B", "bar=C", "bar=D", "bar=D")
        val data:RDD[String] = sc.parallelize(keysWithValuesList)
        val initialScores = Array(("Fred", 88.0), ("Fred", 95.0), ("Fred", 91.0), ("Wilma", 93.0), ("Wilma", 95.0), ("Wilma", 98.0))
        val wilmaAndFredScores:RDD[(String, Double)] = sc.parallelize(initialScores).cache()

        //Create key value pairs
        val kv:RDD[(String,String)] = data
                .map(_.split("="))
                .map(v => (v(0), v(1)))
                .cache()
        /* createCombiner就是一个函数，调用将会产生一个set
        scala> createCombiner("a")
        res8: scala.collection.mutable.HashSet[String] = Set(a)
        */

        val createCombiner = (v: String) => mutable.HashSet(v) //为第一个单元素服务
        val combiningFunction = (s: mutable.HashSet[String], v: String) => s += v // 往集合加添加一个元素
        val mergeCombiners = (p1: mutable.HashSet[String], p2: mutable.HashSet[String]) => p1 ++= p2 // 两个集合进行合并

        val uniqueValues:RDD[(String, mutable.HashSet[String])] = kv.combineByKey(createCombiner, combiningFunction, mergeCombiners)
        val writer = new PrintWriter(new File("test_combine.txt"))
        writer.println("CombinByKey unique Results")

        val uniqueResults = uniqueValues.collect()
        for (indx <- uniqueResults.indices) {
            val r = uniqueResults(indx)
            writer.println(r._1 + " -> " + r._2.mkString(","))
        }

        //　第一个元素
        val createScoreCombiner = (score: Double) => (1, score) //为第一个单元素服务

        // 同一partition内元素进行合并
        type ScoreCollector = (Int, Double) // numberScores, totalScore)
        type PersonScores = (String, (Int, Double)) // person -> (numberScores, totalScore)

        val scoreCombiner = (collector: ScoreCollector, score: Double) => {
            val (numberScores, totalScore) = collector
            (numberScores + 1, totalScore + score) // 第一个元素计数，第二个元素求和
        }
        // 不同parition的两个集合进行合并
        val scoreMerger = (collector1: ScoreCollector, collector2: ScoreCollector) => {
            val (numScores1, totalScore1) = collector1
            val (numScores2, totalScore2) = collector2
            (numScores1 + numScores2, totalScore1 + totalScore2)
        }

        val averagingFunction = (personScore: PersonScores) => {
            val (name, (numberScores, totalScore)) = personScore
            (name, totalScore / numberScores)
        }

        val scores:RDD[(String, (Int, Double))] = wilmaAndFredScores
                .combineByKey(createScoreCombiner, scoreCombiner, scoreMerger)
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
    }

}