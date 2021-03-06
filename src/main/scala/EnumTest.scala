
/*
* Created by IntelliJ IDEA.
* Author: hukexin
* Date: 18-10-31
* Time: 下午7:32
*/
object EnumTest {

  /***
    * 定义一个星期的枚举
    */
  object WeekDay extends Enumeration{
    type WeekDay = Value  //声明枚举对外暴露的变量类型
    val Not = Value
    val Mon = Value
    val Tue = Value("2")
    val Wed = Value("2")
    val Thu = Value("thu")
    val Fri = Value("5")
    val Sat = Value("6")
    val Sun = Value("7")
    def checkExists(day:String) = this.values.exists(_.toString==day) //检测是否存在此枚举值
    def isWorkingDay(day:WeekDay) = ! ( day==Sat || day == Sun) //判断是否是工作日
    def showAll = this.values.foreach(println) // 打印所有的枚举值
  }



  def main(args: Array[String]): Unit = {


    println(WeekDay.withName("Mon").toString) // Mon
    println(WeekDay.Not.toString) // Not
    println(WeekDay.Mon.toString) // Mon
    println(WeekDay.Thu.toString) // thu
    println(WeekDay.Wed.toString) // 2
    println(WeekDay.checkExists("8"))//检测是否存在 , false

    println(WeekDay.Sun==WeekDay.withName("7"))//正确的使用方法, true


    println(WeekDay.Sun=="7")//错误的使用方法 ,false

    WeekDay.showAll //打印所有的枚举值,注意这里打印的是数字 1,2,3,4,5,6,7

    println(WeekDay.isWorkingDay(WeekDay.Sun)) //是否是工作日, false

    val days = List(WeekDay.Not,WeekDay.Mon, WeekDay.Tue)
    println("contains Mon:", days.contains(WeekDay.Mon))
    println("contains Sum:", days.contains(WeekDay.Sun))

    val s1 = Set(WeekDay.Mon, WeekDay.Tue, WeekDay.Wed)
    println(s1, " size:", s1.size)
  }

}
