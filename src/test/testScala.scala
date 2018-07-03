import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FunSuite

class testScala extends FunSuite{
    test("将两个一维数组，拼成一个二维数组") {
        println("unit test, hello world!")
        val xx = "aa,bb,cc,dd,ee".split(",")
        val yy = Seq(1,2,3,4)
        val xy = xx.flatMap {
            x => yy.map(y=>x->y)
        }
        println(xy.mkString("\n"))

        /**
          * (aa,1)
          * (aa,2)
          * (aa,3)
          * (aa,4)
          * (bb,1)
          * (bb,2)
          * (bb,3)
          * (bb,4)
          * (cc,1)
          * (cc,2)
          * (cc,3)
          * (cc,4)
          * (dd,1)
          * (dd,2)
          * (dd,3)
          * (dd,4)
          * (ee,1)
          * (ee,2)
          * (ee,3)
          * (ee,4)
          */
    }
}
