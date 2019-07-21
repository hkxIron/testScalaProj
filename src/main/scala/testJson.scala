import play.api.libs.json.{JsArray, JsString, JsValue, Json}

import scala.collection.JavaConverters._
/*
* Created by IntelliJ IDEA.
* Author: hukexin
* Date: 19-1-18
* Time: 下午4:57
*/
object testJson {
  def main(args: Array[String]): Unit = {
    val str =
      """ {
 "text": "",
 "hints": [
 {
  "action": "INSTALL",
  "text": "你已安装%s, 正在打开"
 },
 {
  "action": "QUERY",
  "text": "你已安装多个今日头条应用，请问你要打开第几个？"
 },
 {
  "action": "NOT_FOUND",
  "text": "为你找到多个今日头条应用，快点击下载吧"
 },
 {
  "action": "NOT_SUPPORT",
  "text": "小爱还不支持安装该应用哦"
 }
 ]
}
"""


    {

      val intentJS: org.json.JSONObject = new org.json.JSONObject(str)
      //println(s"json str:${intentJS.toString}")
      println(intentJS.getJSONArray("hints"))
      val arr = intentJS.getJSONArray("hints")

      var hintArray = new play.api.libs.json.JsArray
      val topText = intentJS.optString("text")
      val len = arr.length()

      for (i <- 0 until len) {
        val hint = arr.getJSONObject(i)
        val action = hint.optString("action", "")
        val text = hint.optString("text", "")
        var hintObj = Json.obj("action" -> action,
          "text" -> text)
        hintArray = hintArray :+ hintObj
      }
      println("\n\n")
      //val prompt = GsonUtil.getUnderScoreGson.fromJson(intentJS.toString(), classOf[Prompt2])
      println(s"prompt: ${hintArray.toString()}")
    }


    {

      // -------------
      //val promot = Prompt("你发好不好", open_mic = Some(true))
      //val str = GsonUtil.getUnderScoreGson.toJson(promot)
      println("str:", str)
      val tt = GsonUtil.getJsonParser.parse(str).getAsJsonObject
      // tt.get("text")
      println("tt:", tt.get("text").toString)
      println("tt arr:", tt.getAsJsonArray("hints"))
    }

    {
      println("test 3")
      val myMap = Map("s1" -> JsString("s1_value"), "s2" -> JsString("s2_value"))
      val tt = myMap.+(("s3", JsString("s3_value")))
      println("myMap:"+myMap.toString())
      println("tt:"+tt.toString())
    }

  }
}
