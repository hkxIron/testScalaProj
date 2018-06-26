import java.io.{File, PrintWriter}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FunSuite
/*
aggregate(zeroValue,seq,comb,taskNums)
:
将初始值和第一个分区中的第一个元素传递给seq函数进行计算，然后将计算结果和第二个元素传递给seq函数，直到计算到最后一个值。
第二个分区中也是同理操作。最后将初始值、所有分区的结果经过combine函数进行计算（先将前两个结果进行计算，将返回结果和下一个结果传给combine函数，以此类推），并返回最终结果。

aggregateByKey(zeroValue,seq,comb,taskNums):
在kv对的RDD中，按key将value进行分组合并，合并时，将每个value和初始值作为seq函数的参数，进行计算，
返回的结果作为一个新的kv对，然后再将结果按照key进行合并，最后将每个分组的value传递给combine函数进行计算
（先将前两个value进行计算，将返回结果和下一个value传给combine函数，以此类推），将key与计算结果作为一个新的kv对输出。

*/

class aggregate extends FunSuite{
    test("testAggregate") {
        val rdd = List(1,2,3,4,5,6,7,8,9)
        val res = rdd.par.aggregate((0,0))(
            (acc,number) => (acc._1 + number, acc._2 + 1), //求和，　计数
            (par1,par2) => (par1._1 + par2._1, par1._2 + par2._2)
        )
        println("res:", res)
    }

    test("testAggregateByKeyRDD"){
        val sparkConf: SparkConf = new SparkConf()
                .setAppName("AggregateByKey")
                .setMaster("local")

        val sc: SparkContext = new SparkContext(sparkConf)

        val data=List((1,3),(1,2),(1,4),(2,3))
        val rdd=sc.parallelize(data, 2)

        //合并在同一个partition中的值，a的数据类型为zeroValue的数据类型，b的数据类型为原value的数据类型
        def seqOp(a:String,b:Int):String={
            println("SeqOp:"+a+"\t"+b)
            a+b
        }

        //合并不同partition中具有相同key的值，a，b的数据类型为zeroValue的数据类型
        def combOp(a:String,b:String):String={
            println("combOp: "+a+"\t"+b)
            a+b
        }
        //zeroValue:中立值,定义返回value的类型，并参与运算
        //seqOp:用来在同一个partition中合并值
        //combOp:用来在不同partiton中合并值
        sc.setLogLevel("OFF")
        val aggregateByKeyRDD=rdd.aggregateByKey("100")(seqOp, combOp)
        val writer = new PrintWriter(new File("out1.txt"))
        println("=============================================")
        aggregateByKeyRDD.collect().foreach(writer.println)
        writer.close()
        println("=============================================")
        sc.stop()
    }

    /**
      *　@note 注意：此方法效率不高,不过具有教学意义
      * 举个例子,比如要统计用户的总访问次数和去除访问同一个URL之后的总访问次数,随便造了几条样例数据(四个字段:id,name,vtm,url,vtm字段本例没用,不用管)如下:
      *
      * id1,user1,2,http://www.hupu.com
      * id1,user1,2,http://www.hupu.com
      * id1,user1,3,http://www.hupu.com
      * id1,user1,100,http://www.hupu.com
      * id2,user2,2,http://www.hupu.com
      * id2,user2,1,http://www.hupu.com
      * id2,user2,50,http://www.hupu.com
      * id2,user2,2,http://touzhu.hupu.com
      * 根据这个数据集,我们可以写hql 实现:select id,name, count(0) as ct,count(distinct url) as urlcount from table group by id,name.
      * 得出结果应该是:
      *
      * id1,user1,4,1
      *
      * id2,user2,4,2
      *
      * 下面用spark实现这个聚合功能
      */
    test("aggregateList"){
        case class User(id: String, name: String, vtm: String, url: String)
        //val rowkey = (new RowKey).evaluate(_)
        //val HADOOP_USER = "hdfs"
        // 设置访问spark使用的用户名
        //System.setProperty("user.name", HADOOP_USER)
        // 设置访问hadoop使用的用户名
        //System.setProperty("HADOOP_USER_NAME", HADOOP_USER)

        val conf = new SparkConf().setAppName("wordcount").setMaster("local")
                //.setExecutorEnv("HADOOP_USER_NAME", HADOOP_USER)
        val sc = new SparkContext(conf)
        val data = sc.textFile("test.txt")
        val rdd1 = data.map(line => {
            val r = line.split(",")
            User(r(0), r(1), r(2), r(3))
        })
        val rdd2 = rdd1.map(r => ((r.id, r.name), r))

        val seqOp = (a: (Int, List[String]), b: User) => a match {
            case (0, List()) => (1, List(b.url)) // 处理单元素: zeroValue与list
            case _ => (a._1 + 1, b.url :: a._2) // 处理单元素： non-zeroValue与list
        }

        val combOp = (a: (Int, List[String]), b: (Int, List[String])) => {
            (a._1 + b._1, a._2 ::: b._2)
        }

        println("-----------------------------------------")
        val write = new PrintWriter(new File("out.txt"))
        val rdd3 = rdd2.aggregateByKey((0, List[String]()))(seqOp, combOp).map(a => {
            (a._1, a._2._1, a._2._2.distinct.length)
        })
        rdd3.collect.foreach(write.println)
        println("-----------------------------------------")
        sc.stop()
    }

}


