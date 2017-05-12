package whatever

import akka.actor.Actor

import scala.collection.mutable
import scala.util.Random

trait ServiceUser extends Actor {

  val service: RatingService

  var messages: scala.collection.mutable.MutableList[RatingServiceMessage] = mutable.MutableList.empty


  def execute(): Unit

  override def receive = {

    case n: Int =>
      (1 to n).foreach( i => execute() )
      sender ! messages.toList

  }

}

class LoginOnlyUser(val service: RatingService) extends ServiceUser {

  override def execute(): Unit = {

    // Send message and store it in the collection of sent messages.
    service.send(Login)
    messages += Login
  }
}

class LoginAndRankUser(val service: RatingService) extends ServiceUser {

  override def execute(): Unit = {

    // Send message and store it in the collection of sent messages.
    service.send(Login)
    messages += Login

    val starRanking = Random.shuffle(1 to 5).toList.head
    val message = Rate(starRanking)
    service.send(message)
    messages += message

  }


}
