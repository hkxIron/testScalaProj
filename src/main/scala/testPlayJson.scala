import play.api.libs.json.{JsObject, Json, __}

/*
* Created by IntelliJ IDEA.
* Author: hukexin
* Date: 19-1-18
* Time: 下午4:57
*/
object testPlayJson {
  def main(args: Array[String]): Unit = {
    val str:String = """{
          "type": "相声",
          "from_domain": "weather",
          "from_IDENTITY": "相声",
          "from_EXECUTION_TIME": "2019-08-07T13:20:01.481+08:00[Asia/Shanghai]",
          "from_GENDER": "male",
          "sub_type": "COMEDY",
          "name": "精品相声",
          "artist": "冯巩",
          "offset": 0,
          "has_online_feat_res": true,
          "res_is_featured": false,
          "res_rank": -1,
          "artist_rank": -1,
          "episode": 4641,
          "season": 0,
          "check_update": false,
          "knowledge_sim": 0,
          "nlp_sim": 0,
          "intent_score": 0,
          "qc": false,
          "processing_round": 0,
          "score_info": {
            "rule_score": 0,
            "model_score": 0
          },
          "query": "我想听冯巩相声",
          "domain": "station",
          "score": 0,
          "action": "query",
          "complete": true,
          "prob": 0,
          "debug_info": {},
          "intention_info": {}
        }
      """
      val t1 = Json.parse(str)
      println("jsonObj:"+t1.toString)
      println("")
      println((t1 \ "type").as[String])

      val t2:JsObject = t1.asInstanceOf[JsObject]
      println(t2.isInstanceOf[JsObject])
      println("t2:"+t2)
  }
}

