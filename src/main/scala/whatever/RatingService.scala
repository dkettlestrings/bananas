package whatever

import whatever.RatingService.InternalRatingServiceState

trait RatingService {

  var state: InternalRatingServiceState

  def send(message: RatingServiceMessage): Unit = message match {

    case Login =>
      state = InternalRatingServiceState(
        state.numLogins + 1,
        state.numVoted,
        state.numVoted.toDouble / (state.numLogins + 1),
        state.starRanking
      )

    case Rate(starRating) =>
      state = InternalRatingServiceState(
        state.numLogins,
        state.numVoted + 1,
        (state.numVoted + 1).toDouble / state.numLogins,
        ((state.numVoted * state.starRanking) + starRating) / (state.numVoted + 1)
      )

  }

  // Note that the state the service exposes externally is NOT the same as the internal state.
  // This is done to make the example more "realistic".
  def getState: ExternalRatingServiceState = ExternalRatingServiceState(state.numLogins, state.votingRatio, state.starRanking)

}

object RatingService {

  case class InternalRatingServiceState(numLogins: Int, numVoted: Int, votingRatio: Double, starRanking: Double)

  def inMemory: RatingService = new RatingService {

    override var state: InternalRatingServiceState = InternalRatingServiceState(0, 0, 0.0, 0.0)
  }
}
