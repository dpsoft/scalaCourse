import akka.actor.{Props, ActorSystem, Actor}
import java.util.concurrent.atomic.AtomicLong

object PingPong extends App {
  import scala.concurrent.duration._

  val counter = new LongAdder
  //val counter = new AtomicLong()
  val system = ActorSystem("ping-pong")
  implicit val exeContext = system.dispatcher

  system.scheduler.schedule(0 seconds, 1 seconds) {
    println(s"Rate: ${counter.sumThenReset()}")
    //counter.set(0L)
  }


  for(i <- 1 to 16) {
    val pinger = system.actorOf(Props[Pinger], s"pinger-$i")
    val ponger = system.actorOf(Props[Ponger], s"ponger-$i")

    for(_ <- 1 to 100) {
      pinger.tell(Pong, ponger)
    }
  }
}
case object Ping
case object Pong

class Pinger extends Actor {
  def receive = {
    case Pong  => sender ! Ping; PingPong.counter.increment()
  }
}

class Ponger extends Actor {
  def receive = {
    case Ping => sender ! Pong; PingPong.counter.increment()
  }
}