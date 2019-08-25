import java.io.{File, PrintWriter}

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FunSuite
import org.apache.spark.sql.SparkSession

import scala.collection.mutable
// blog: http://codingjunkie.net/spark-agr-by-key/

/**
  * Created by bbejeck on 7/31/15.
  *
  * Example of using AggregateByKey
  */
class AggregateByKeyUnitTest2 extends FunSuite{

    System.setProperty("hadoop.home.dir", "D:\\hadoop_2.6.0")
    System.setProperty("HADOOP_HOME", "D:\\hadoop_2.6.0")


    test("testAggregate2"){

        Logger.getLogger("org.apache").setLevel(Level.ERROR)
        val logger = Logger.getLogger("org.eclipse.jetty.server")
        logger.setLevel(Level.OFF)
        logger.info("start to run =======================")
        val sc = new SparkContext(new SparkConf().setAppName("Grouping Examples").setMaster("local[1]"))
        val keysWithValuesList = Array("foo=1,2", "foo=3,4", "bar=-1,-2", "bar=-3,-4", "bar=-5,-6")

        val data = sc.parallelize(keysWithValuesList)

        //Create key value pairs
        val kv = data.map(_.split("=")).map{v =>
          val arr = v(1).split(",")
          v(0)->(arr(0).toInt, arr(1).toLong) // count, sum
        }.cache()

        /**
          * groupByKey是比较耗时的操作，groupByKey函数会将所有的相同的key放到一个reducer中去，若某个key上的元素较多，将非常耗时。
          *
          * 而aggregateByKey将会将同一个partition上的元素进行提前聚合
          * aggregateByKey有3个参数：
          * 1.初始空集合
          * 2.集合中添加元素的函数
          * 3.两个集合进行合并的聚合函数
          *
          */
        val uniqueByKey = kv.aggregateByKey((0,0L))( // count, sum
            (u, v) => (u._1+v._1, u._2+v._2), // add element
            (u1, u2) =>(u1._1+u2._1, u1._2+u2._2) // merge partiions
        )

        val writer = new PrintWriter(new File("aggregate_by_key_test2.txt"))
        writer.println("Aggregate By Key unique Results")
        val uniqueResults = uniqueByKey.collect()
        for(indx <- uniqueResults.indices){
            val r = uniqueResults(indx)
            writer.println(r._1 + " -> " + r._2.productIterator.mkString(","))
        }

        writer.println("------------------")
        writer.close()
    }

}