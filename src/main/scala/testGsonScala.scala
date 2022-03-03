import old.GsonUtil
import play.api.libs.json.{JsObject, Json, __}

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

/*
* Created by IntelliJ IDEA.
* Author: hukexin
* Date: 19-1-18
* Time: 下午4:57
*/
object testGsonScala {
  def main(args: Array[String]): Unit = {
    val resultJson = Map(
      "token"->"李刚",
      "norm_token"->"李刚",
      "score"-> 1.9
    )
    val str = GsonUtil.getUnderScoreGson.toJson(resultJson.asJava)
    println(str)
  }
}

