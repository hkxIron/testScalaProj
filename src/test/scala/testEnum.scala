import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FunSuite

class testEnum extends FunSuite {
  object ImplicitIntentionType extends Enumeration {
    // 订票
    val OrderTrainTicket = Value(0, "order_train_ticket")
    val OrderFlightTicket = Value(1, "order_flight_ticket")
    // 外卖
    val takeoutFoodBookByRestaurantName = Value(2, "takeout_food_book_by_restaurant_name") // 根据餐馆名查询
    val takeoutFoodBookByFoodName = Value(3, "takeout_food_book_by_food_name") // 根据食物名查询
    val orderOther = Value
  }

  test("testEnumWithName") {
    val tt = ImplicitIntentionType.withName("takeout_food_book_by_restaurant_name")
    println("tt: "+ tt.toString) // takeout_food_book_by_restaurant_name
    println("orderOther: "+ ImplicitIntentionType.orderOther.toString) // takeout_food_book_by_restaurant_name
    try {
      val t2 = ImplicitIntentionType.withName("takeoutFoodBookByRestaurantName") // error: No value found for 'takeoutFoodBookByRestaurantName'
      println("t2: " + t2.toString)
    }catch {
      case e: Throwable=>
      case _ =>
    }
  }
}
