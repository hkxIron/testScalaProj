import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}
import org.scalatest.FunSuite

class testRdd extends FunSuite{
    test("test1") {
        val conf = new SparkConf()
                .setAppName("SingleRDD")
                .setMaster("local[1]")
        val sc = new SparkContext(conf)
        // 设置日志等级
        //sc.setLogLevel("WARN")
        // sc.setLogLevel("ERROR")
        // jsonFile.txt内容如上图
        val data: RDD[String] = sc.textFile("data.txt")
        // SingleRDD 去重=>distinct API
        println("================================")
        data.distinct().foreach(println)
        println("================================")
        // 结果
        //{"name":"李四","age":26,"from":"HuNan"}
        //{"name":"王五","age":26,"from":"HuBei"}
        //{"name":"张三","age":24,"from":"HuBei"}
    }
}


