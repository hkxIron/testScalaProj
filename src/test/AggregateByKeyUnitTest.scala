import java.io.{File, PrintWriter}

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import org.scalatest.FunSuite
// blog: http://codingjunkie.net/spark-agr-by-key/

/**
  * Created by bbejeck on 7/31/15.
  *
  * Example of using AggregateByKey
  */
class AggregateByKeyTest extends FunSuite{

    test("testAggregate"){

        Logger.getLogger("org.apache").setLevel(Level.ERROR)
        Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
        val sc = new SparkContext(new SparkConf().setAppName("Grouping Examples").setMaster("local[1]"))

        val keysWithValuesList = Array("foo=A", "foo=A", "foo=A", "foo=A", "foo=B", "bar=C", "bar=D", "bar=D")

        val data = sc.parallelize(keysWithValuesList)

        //Create key value pairs
        val kv = data.map(_.split("=")).map(v => (v(0), v(1))).cache()

        val initialSet = mutable.HashSet.empty[String]
        val addToSet = (s: mutable.HashSet[String], v: String) => s += v
        val mergePartitionSets = (p1: mutable.HashSet[String], p2: mutable.HashSet[String]) => p1 ++= p2

        val uniqueByKey = kv.aggregateByKey(initialSet)(addToSet, mergePartitionSets)

        val initialCount = 0
        val addToCounts = (n: Int, v: String) => n + 1
        val sumPartitionCounts = (p1: Int, p2: Int) => p1 + p2

        val countByKey = kv.aggregateByKey(initialCount)(addToCounts, sumPartitionCounts)


        val writer = new PrintWriter(new File("test_aggreate.txt"))
        writer.println("Aggregate By Key unique Results")
        val uniqueResults = uniqueByKey.collect()
        for(indx <- uniqueResults.indices){
            val r = uniqueResults(indx)
            writer.println(r._1 + " -> " + r._2.mkString(","))
        }

        writer.println("------------------")

        writer.println("Aggregate By Key sum Results")
        val sumResults = countByKey.collect()
        for(indx <- sumResults.indices){
            val r = sumResults(indx)
            writer.println(r._1 + " -> " + r._2)
        }
        writer.close()
/*
result:
        Aggregate By Key unique Results
        bar -> C,D
        foo -> B,A
        ------------------
        Aggregate By Key sum Results
        bar -> 3
        foo -> 5
*/
    }

}