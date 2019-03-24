
/*
* Created by IntelliJ IDEA.
* Author: hukexin
* Date: 18-10-23
* Time: 下午8:31
*/
case class TestClass (
  id:Int,
  name:Option[String]
)

case class Prompt2(text: String = "",
                   hints: play.api.libs.json.JsArray // for video
                 )
case class Prompt(
                   text: String = "",

                   open_mic: Option[Boolean] = None, // for incomplete api2 to pass open_mic to content

                   relative_slots: Option[String] = None, // for video

                   hints: Option[play.api.libs.json.JsArray] = None // for video
                 )
case class Prompt3(text: String = "",
                   hints: String =""
                  )
