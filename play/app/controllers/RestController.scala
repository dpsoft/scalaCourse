package controllers
 
import play.api.mvc.{Action, Controller}

import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.ws.WS

case class Result(from: String, text: String)
 
object RestController extends Controller {

  def call(query: String) = Action {
    Async {
      val responsePromise =  WS.url("http://search.twitter.com/search.json").get
      responsePromise.map {
        response =>
          val results = Json.parse(response.body).\("results").as[Seq[Result]]
          Ok(results)
      }
    }
  }
}