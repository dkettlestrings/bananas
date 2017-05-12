package whatever

import org.scalatest.FlatSpec
import MessageApplier._

// Not necessary for this example, it just exists to make sure I didn't do anything too stupid when writing it.
class TestMessageApplier extends FlatSpec {

  "Applying messages to an empty ExternalRatingServiceState" should "update properly" in {

    assert(ExternalRatingServiceState.empty.:+:(Login) ~= ExternalRatingServiceState(1, 0, 0))

  }

  "Applying messages to ExternalRatingServiceState" should "update properly" in {

    assert(ExternalRatingServiceState(1, 1.0, 3).:+:(Login) ~= ExternalRatingServiceState(2, 0.5, 3))

  }

  "Big test cases" should "work" in {

    val state1 = ExternalRatingServiceState.empty.:+:(Login).:+:(Login).:+:(Rate(2)).:+:(Login).:+:(Rate(3))
    assert(state1 ~= ExternalRatingServiceState(3, 0.666, 2.5))


    val state2 = ExternalRatingServiceState.empty.:+:(Login).:+:(Login).:+:(Login).:+:(Login).:+:(Login)
    assert(state2 ~= ExternalRatingServiceState(5, 0.0, 0))

    val state3 = ExternalRatingServiceState.empty.:+:(Login).:+:(Rate(2)).:+:(Login).:+:(Rate(3)).:+:(Login)
    assert(state3 ~= ExternalRatingServiceState(3, 0.666, 2.5))

  }

}
