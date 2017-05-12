package whatever

import org.scalactic.TripleEquals._
import org.scalactic.Tolerance._

case class ExternalRatingServiceState(numLogins: Int, votingRatio: Double, starRanking: Double) {

  // Convenience method to make the code more readable.
  def numVotes: Int = (votingRatio * numLogins).round.toInt

  def ~=(other: ExternalRatingServiceState): Boolean = {

    numLogins == other.numLogins &&
    votingRatio === other.votingRatio +- .01 &&
    starRanking === other.starRanking +- .01
  }

}

object ExternalRatingServiceState {

  val empty = ExternalRatingServiceState(0, 0, 0)

}
