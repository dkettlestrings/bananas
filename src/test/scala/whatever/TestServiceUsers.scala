package whatever

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask
import org.scalatest.FlatSpec

import language.postfixOps
import scala.concurrent.Await
import scala.concurrent.duration._

// Not necessary for this example, it just exists to make sure I didn't do anything too stupid when writing it.
class TestServiceUsers extends FlatSpec {

  "LoginOnlyUser" should "only have Logins" in {

    implicit val timeout: Timeout = 5 seconds
    val duration: Duration = timeout.duration

    val service = RatingService.inMemory

    val system = ActorSystem("LoginOnlyUserTestSystem")

    val loginUserProps = (1 to 5).map(i => Props(new LoginOnlyUser(service)) ).toList

    val loginUsers = loginUserProps.map( user => system.actorOf(user) )

    val results: List[RatingServiceMessage] = loginUsers.flatMap( user => Await.result(ask(user, 2), duration).asInstanceOf[List[RatingServiceMessage]] )

    assert(results == List.fill(10)(Login))

  }

  "LoginAndRankUser" should "have half Logins and half Ratings" in {

    implicit val timeout: Timeout = 5 seconds
    val duration: Duration = timeout.duration

    val service = RatingService.inMemory

    val system = ActorSystem("LoginAndRankUserTestSystem")

    val ratingUserProps = (1 to 5).map( i => Props(new LoginAndRankUser(service)) ).toList

    val ratingUsers = ratingUserProps.map( user => system.actorOf(user) )

    val results: List[RatingServiceMessage] = ratingUsers.flatMap( user => Await.result(ask(user, 2), duration).asInstanceOf[List[RatingServiceMessage]] )

    assert(results.length == 20)

    Range(0, 18, 2).foreach(i => assert(results(i) == Login))

    Range(1, 19, 2).foreach(i => {

      results(i) match {

        case Login => false
        case Rate(_) => true
      }

    })




  }

}
