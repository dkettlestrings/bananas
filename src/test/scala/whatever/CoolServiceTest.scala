package whatever

import akka.actor.{ActorSystem, Props}
import org.scalatest.FlatSpec

import scala.concurrent.Await
import akka.pattern.ask

import scala.concurrent.duration._
import akka.util.Timeout
import MessageApplier._

import language.postfixOps


class CoolServiceTest extends FlatSpec {

  "The service" should "be testable via actors" in {

    implicit val timeout: Timeout = 5 seconds
    val duration: Duration = timeout.duration

    val service = RatingService.inMemory

    val system = ActorSystem("CoolServiceTestSystem")

    val loginUserProps = (1 to 5).map(i => Props(new LoginOnlyUser(service)) )

    val ratingUserProps = (1 to 5).map( i => Props(new LoginAndRankUser(service)) )

    val allProps = loginUserProps.toList ++ ratingUserProps.toList

    val allUsers = allProps.map( user => system.actorOf(user) )

    val results: List[RatingServiceMessage] = allUsers.flatMap( user =>
      Await.result(ask(user, 2), duration).asInstanceOf[List[RatingServiceMessage]]
    )

    val expectedState = results.foldLeft[ExternalRatingServiceState](ExternalRatingServiceState.empty)( (state, message) => state.:+:(message) )

    val actualState = service.getState

    assert(expectedState ~= actualState)

  }

}
