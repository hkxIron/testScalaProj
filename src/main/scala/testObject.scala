import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

case class SearchDocResult(docId:String, docName:String, query:String)

object testObject {
    def main(args: Array[String]) {
        println(s"class name:${getClass.toGenericString}")
        val result = SearchDocResult("1", "test", "hello")
        println(result.productIterator.mkString(" "))
    }
}
