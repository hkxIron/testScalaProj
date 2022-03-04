package sparkRDD

import java.io.{File, PrintWriter}

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession

object SparkJoinTest {
  System.setProperty("HADOOP_HOME", "D:\\hadoop_2.6.0")

  def main(args: Array[String]): Unit = {

    //System.setProperty("HADOOP_HOME", "D:\\hadoop_2.6.0")
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.apache.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf()
      .setAppName("DF2RDD")
      .setMaster("local[2]")
      .set("spark.sql.warehouse.dir","file:///") // 如果不加此行,会出现error: Exception in thread "main" java.lang.IllegalArgumentException: java.net.URISyntaxException: Relative path in absolute URI: file:

    val sc = new SparkContext(conf)
    val spark = SparkSession.builder.config(conf).getOrCreate()

    /**
      * 张三丰,99999
      * 风清扬,88888
      * 令狐冲,66666
      * 周伯通,99999
      */
    val swordPower=spark.sparkContext.textFile("D:\\code\\python_code\\testScalaProj\\sword1.txt")
    val heCol="name,force_value"
    val heFields=heCol.split(",")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val heSchema=StructType(heFields)
    val heRowRDD=swordPower.map(_.split(",")).map(parts⇒Row(parts(0),parts(1)))
    val heDF=spark.createDataFrame(heRowRDD, heSchema)

    /**
      * heroine.txt
      * 不婚族,张三丰
      * 慕容雪,风清扬
      * 任盈盈,令狐冲
      * 瑛姑,周伯通
      */
    val sheCol="name,lower_name"
    val sheFields=sheCol.split(",").map(fieldName => StructField(fieldName, StringType, nullable = true))
    val sheSchema=StructType(sheFields)
    val sheHePair=spark.sparkContext.textFile("D:\\code\\python_code\\testScalaProj\\she_he_pair.txt")
    val sheRowRDD=sheHePair.map(_.split(",")).map(parts⇒Row(parts(0),parts(1)))
    val sheDF=spark.createDataFrame(sheRowRDD, sheSchema)

    val heView=heDF.createOrReplaceTempView("swordsman")
    val sheView=sheDF.createOrReplaceTempView("heroine")

    val resDF=spark.sql("SELECT t1.name,t2.name as lower,t1.force_value " +
      "FROM swordsman t1 " +
      "join heroine t2 " +
      "on t1.name=t2.lower_name " +
      "order by t1.force_value desc")
    val writer = new PrintWriter(new File("out/sparkJoinTestOut.txt"))
    resDF.show()
    writer.println(resDF.rdd.collect().mkString("\n"))
    writer.close()

    /** 输出:
      * [张三丰,不婚族,99999]
      * [风清扬,慕容雪,88888]
      * [令狐冲,任盈盈,66666]
      */

    spark.stop()
  }
}
