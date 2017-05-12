package whatever

object MessageApplier {

  trait CanApplyMessage[A] {

    def :+:(message: RatingServiceMessage): A

  }

  implicit def toApplicable(state: ExternalRatingServiceState): CanApplyMessage[ExternalRatingServiceState] = new CanApplyMessage[ExternalRatingServiceState] {


    override def :+:(message: RatingServiceMessage): ExternalRatingServiceState = message match {

      case Login =>
        ExternalRatingServiceState(
          state.numLogins + 1,
          state.numVotes.toDouble / (state.numLogins + 1),
          state.starRanking)

      case Rate(rating) =>

        ExternalRatingServiceState(
          state.numLogins,
          (state.numVotes +1).toDouble / state.numLogins,
          ((state.starRanking * state.numVotes) + rating) / (state.numVotes + 1))

    }

  }


}






