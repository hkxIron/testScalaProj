import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

case class SearchDocResult(docId:String, docName:String, query:String)

object testObject {
    def detectRepeat(tt:String):Unit ={
        //var tt = "啷个哩个啷啷哩个啷啷个哩个啷个哩个浪里格浪"
        println( tt.grouped(2).toList)
        val count= tt.grouped(2)
          .toList
          .map(x=>(x,1))
          .groupBy(_._1)
          .mapValues(x=>x.map(y=>y._2).sum)
          .filter(_._2>1)
          .toList.sortWith(_._2>_._2).take(4)
        println("count: "+count)
    }

    def detectRepeat2(tt:String):Unit ={
        //var tt = "啷个哩个啷啷哩个啷啷个哩个啷个哩个浪里格浪"
        //println( tt.grouped(2).toList)
        val count= tt.grouped(2)
          .toList
          .map(x=>(x,1))
          .groupBy(_._1)
          .mapValues(x=>x.map(y=>y._2).sum)
          .filter(_._2>1)
          .toList.sortWith(_._2>_._2).take(4).map(x=>x._2).sum

        val count2= tt.tail.grouped(2)
          .toList
          .map(x=>(x,1))
          .groupBy(_._1)
          .mapValues(x=>x.map(y=>y._2).sum)
          .filter(_._2>1)
          .toList.sortWith(_._2>_._2).take(4).map(x=>x._2).sum

        println("query: "+tt + " count:"+math.max(count, count2))
    }

    def main(args: Array[String]) {
        {
            val flag = 0
            val myMap = if(flag>2){
               Map("x"->1)
            }else{
                Map.empty // 正确写法,返回scala.collection.immutable.Map[A, B]
            }

            println("myMap1:"+ myMap)
        }
        {
            val flag = 0
            val myMap = if(flag>2){
                Map("x"->1)
            }else{
                /**
                  * val m = Map.empty
                  * m(),找不到对应的方法
                  */
                Map.empty() // 错误写法，会抛异常
                // 实际上调用了 apply(key : A)，scala临时传了一个 BoxedUnit.UNIT进去
            }
            println("myMap2:"+ myMap)
            sys.exit(-1)
        }

        {
            val test = testObj(Some("xx"), 12)
            println(test.productIterator.mkString(" "))
            sys.exit(-1)
        }
        {
            val s1:Option[Seq[String]] = Some(Seq("s1","s2"))
            println(s1.get.contains("s1"))
            val tt= s1.map(x=>x.contains("s1")).reduceLeft( _ || _)
            println("t1:"+tt)
            val s2:Option[Seq[String]] = None
            println("t2:"+s2.map(x=>x.contains("s1")))
            //println(s1.exists(true))
        }
        {
            var strs = Seq(
                "你个逼你个傻逼你个傻逼傻逼你个傻逼你个傻逼你个",
                "啷个哩个啷啷哩个啷啷个哩个啷个哩个浪里格浪",
                "再来不用吃什么东西呢我要说个说个说个说个说个说个",
                "西瓜恢复吃西瓜吃西瓜吃西瓜吃西瓜",
                "那你啦牛奶牛奶牛奶牛奶牛奶牛奶牛奶",
                "啷个哩个啷个哩个啷个哩个啷",
                "讲个笑话讲个笑话讲个笑话讲个笑话讲个笑话讲个笑话",
                "仔仔在跟你个你个你个你个你个你个呆九个念",
                "傻逼日你奶奶个蛋的奶奶个腿买了个鸡巴蛋奶奶个腿的奶奶个腚"
            )
            //strs.foreach(x=>detectRepeat(x.tail)) // >=5
            strs.foreach(x=>detectRepeat2(x)) // >=6
            val str="xxx我爱小12343爱小爱小爱"
            //val filteredStr = str.filterNot(x=>x.isLetterOrDigit)
            val filteredStr = str.filterNot{x=>
              val value = x.toLower.toInt
              val isEng = value>=97&&value<=(97+25)
              x.isDigit||isEng
            }
            println("filtered:"+filteredStr)
            println('a'.toInt, 'z'.toInt)
        }

        {
            println(s"class name:${getClass.toGenericString}")
            val result = SearchDocResult("1", "test", "hello")
            println(result.productIterator.mkString(" "))
        }
    }
}
