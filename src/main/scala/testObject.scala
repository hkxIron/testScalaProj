import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object testObject {
    def main(args: Array[String]) {
        println(s"class name:${getClass.toGenericString}")
    }
}
