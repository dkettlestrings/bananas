package whatever

import org.scalatest.FlatSpec

// Not necessary for this example, it just exists to make sure I didn't do anything too stupid when writing it.
class TestRatingService extends FlatSpec {

  "RatingService" should "update state properly" in {

    val service = RatingService.inMemory

    service.send(Login)
    service.send(Rate(3))

    service.send(Login)

    service.send(Login)
    service.send(Rate(4))

    val expectedState = ExternalRatingServiceState(3, 0.66, 3.5)
    val actualState = service.getState

    assert(expectedState ~= actualState)
  }

}
