package whatever

sealed trait RatingServiceMessage

case object Login extends RatingServiceMessage
case class Rate(starRating: Int) extends RatingServiceMessage
