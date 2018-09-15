import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object testRdd2 {
    def main(args: Array[String]) {
        val conf = new SparkConf().setAppName("SingleRDD").setMaster("local[1]")
        val sc = new SparkContext(conf)
        // 设置日志等级
        sc.setLogLevel("WARN")
        // jsonFile.txt内容如上图
        val data: RDD[String] = sc.textFile("jsonFile.txt")
        // SingleRDD 去重=>distinct API
        data.distinct().foreach(println)
        // 结果
        //{"name":"李四","age":26,"from":"HuNan"}
        //{"name":"王五","age":26,"from":"HuBei"}
        //{"name":"张三","age":24,"from":"HuBei"}
    }
}
