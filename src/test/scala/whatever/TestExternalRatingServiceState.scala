package whatever

import org.scalatest.FlatSpec

// Not necessary for this example, it just exists to make sure I didn't do anything too stupid when writing it.
class TestExternalRatingServiceState extends FlatSpec {

  "numVotes" should "round correctly" in {

    assert(ExternalRatingServiceState(1, 0.0, 0.0).numVotes == 0)
    assert(ExternalRatingServiceState(10, 0.0, 0.0).numVotes == 0)

    assert(ExternalRatingServiceState(10, 0.5, 2.4).numVotes == 5)
    assert(ExternalRatingServiceState(0, 1, 3).numVotes == 0)
  }

}
