package controllers
 
import play.api.mvc.{Action, Controller}

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.ws.WS

case class Result(from: String, text: String)
 
object RestController extends Controller {

  implicit val tweetReads = (
    (__ \ "from_user_name").read[String] and
      (__ \ "text").read[String]
    )(Result)

  implicit val tweetsWrites = Json.writes[Result]

  def call(query: String) = Action {
    Async {
      val responsePromise =  WS.url("http://search.twitter.com/search.json").withQueryString(("q", query)).get
      responsePromise.map {
        response =>
          val results = Json.parse(response.body).\("results").as[Seq[Result]]
          Ok(Json.toJson(results.map(r => Json.toJson(r))))
      }
    }
  }
}