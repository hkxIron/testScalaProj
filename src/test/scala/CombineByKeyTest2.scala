import java.io.{File, PrintWriter}

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.scalatest.FunSuite

import scala.collection.mutable
// blog: http://codingjunkie.net/spark-combine-by-key/

/**
  *
  */
case class Student(name:String, id:String, score:Double)
class CombineByKeyTest2 extends FunSuite {

    test("testCombine") {
        Logger.getLogger("org.apache").setLevel(Level.ERROR)
        Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

        val sc = new SparkContext(new SparkConf().setAppName("CombineByKey Examples").setMaster("local[1]"))
        val peopleScore = Array(
            ("Fred", 88.0),
            ("Fred", 95.0),
            ("Fred", 91.0),
            ("Wilma", 93.0),
            ("Wilma", 95.0),
            ("Wilma", 98.0),
            ("hkx", 98.0),
            ("hkx", 90.0))
        val data = sc.parallelize(peopleScore).partitionBy(new HashPartitioner(3))

        //Create key value pairs
        val kv = data.zipWithIndex()
                .map{case(v, index) =>
                    val name = v._1
                    val student = Student(v._1, index.toString, v._2) // name , id, score
                    name -> student
                }
                .cache()
        /* createCombiner就是一个函数，调用将会产生一个set
        scala> createCombiner("a")
        res8: scala.collection.mutable.HashSet[String] = Set(a)
        */

        val createCombiner = (x:Student) => (mutable.HashSet(x.id), x.score, 1) //为第一个单元素服务
        type CombineCollector = (mutable.HashSet[String], Double, Int)
        val combiningFunction = (collector: CombineCollector, x:Student) => {
            (collector._1 += x.id, collector._2 + x.score, collector._3 + 1)
        }
        val mergeCombiners = (p1: CombineCollector, p2: CombineCollector) => { // 两个集合进行合并
            ( p1._1++=p2._1, p1._2+p2._2, p1._3+p2._3)
        }

        //val tt = mutable.HashSet[String]("sd")
        //val uniqueValues:RDD[(String, mutable.HashSet[String])] = kv.combineByKey(createCombiner, combiningFunction, mergeCombiners)

        val initCombiner = (x: Student) => { (mutable.HashSet(x.id),x.score,1)}
        type combineType =(mutable.HashSet[String], Double, Int)
        val combine = (collector: combineType, x: Student) =>{
            collector._1+=x.id
            (collector._1, collector._2+x.score, collector._3 + 1)
        }
        val merge = (collector1: combineType, collector2: combineType) => {
            collector1._1 ++= collector2._1
            (collector1._1, collector1._2 + collector2._2, collector1._3+collector2._3)
        }
        // 经过约2天的摸索，终于将 多个key合并的任务完成
        val avgScoresRDD = kv.combineByKey(initCombiner, combine, merge )
          .map{ case(name, (idSet, totalScore, count)) =>
              val avgScore = totalScore*1.0/count
              (name, (idSet, totalScore, count, avgScore) )
          }

        val writer = new PrintWriter(new File("test_combine2.txt"))
        writer.println("CombinByKey unique Results")

        avgScoresRDD.collect().foreach{
            case (name, (idSet, totalScore, count, avgScore) )=>
              writer.println(s"name:$name idSet:${idSet.mkString(",")} totalScore:${totalScore} count:$count avgScore:$avgScore" )
        }

      /*
        val avgScoresRDD = kv.combineByKey(
            (x: Student) => (mutable.HashSet.empty[String].add(x.id), x.score, 1) /*createCombiner*/,
            (collector: (mutable.HashSet[String], Double, Int), x: Student) => (collector._1 += x.id, collector._2+x.score,collector._3 + 1) /*mergeValue*/,
            (collector1: (mutable.HashSet[String], Double, Int), collector2: (mutable.HashSet[String],Double, Int)) =>
                (collector1._1++=collector2._1, collector1._2+collector2._2,collector1._3+collector2._3) /*mergeCombiners*/
            // calculate the average
        )//.map( { case(key, value) => (key, value._1/value._2) })
        */


        writer.close()
        /*
        Average Scores using CombingByKey
        Fred's average score : 91.33333333333333
        Wilma's average score : 95.33333333333333
        */
    }

}
