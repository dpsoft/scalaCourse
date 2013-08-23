import akka.actor.{Props, ActorSystem, ActorLogging, Actor}
import akka.routing.{FromConfig, Broadcast, RoundRobinRouter}

object Routing extends App {
  val system = ActorSystem("routing")
  val counter = system.actorOf(Props[Counter]
    .withRouter(FromConfig()).withDispatcher("counter-dispatcher"), "counter")
  val db = system.actorOf(Props[DatabaseWriter]
    .withRouter(FromConfig()).withDispatcher("database-dispatcher"), "db")

  for(_ <- 1 to 1000) {
    counter ! Increment
    db ! StoreSomething
  }
  counter ! Broadcast(PrintCurrentCounter)
}


case object PrintCurrentCounter

class Counter extends Actor with ActorLogging {
  var i = 0
  def receive = {
    case Increment            => Thread.sleep(100); i += 1; log.info("Counted")
    case PrintCurrentCounter  => log.info("Counter: " + i)
  }
}

case object StoreSomething
class DatabaseWriter extends Actor with ActorLogging {
  def receive = {
    case StoreSomething => {
      log.info("Completed Storing")
      Thread.sleep(1000)
    }
  }
}