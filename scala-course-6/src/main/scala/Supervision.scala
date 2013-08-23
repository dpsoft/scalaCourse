import akka.actor._
import akka.actor.SupervisorStrategy.{Restart, Resume}

object Supervision extends App {
  val system = ActorSystem("supervision-test")
  val supervisor = system.actorOf(Props[Supervisor], "supervisor")

  for(_ <- 1 to 200) {
    supervisor ! Increment
  }
}

object Increment

class Supervisor extends Actor  {
  val counterA = context.actorOf(Props[GoodCounter], "counter-a")
  val counterB = context.actorOf(Props[BadCounter], "counter-b")
  def receive = {
    case anyMessage => {
      counterA.forward(anyMessage)
      counterB.forward(anyMessage)
    }
  }

  override def supervisorStrategy: SupervisorStrategy = {
    AllForOneStrategy() {
      case _ => Restart
    }
  }
}
class BadCounter extends Actor with ActorLogging {
  var i = 0
  def receive = {
    case Increment => {
      i += 1
      if(i % 10 == 0)
        throw new NullPointerException
      log.info("Counter: "+ i)
    }
  }
}

class GoodCounter extends Actor with ActorLogging {
  var i = 0
  def receive = {
    case Increment => {
      i += 1
      Thread.sleep(100)
      log.info("Counter: "+ i)
    }
  }
}
