trait Queryable[T] {
  // Fill something here
}

trait Orderable[T] {
  def orderBy[F](fieldSelector: T => F)(implicit ord: Ordering[F]): Seq[T]
}

object select {
  def from[T](from: Queryable[T]) = from
} 


sealed trait Brand
case object DC extends Brand
case object Marvel extends Brand

case class SuperHero(id: Int, name: String, power: String, brand: Brand)

object Heroes {

  val heroes = List(
    SuperHero(8, "Superman", "SuperStrength", DC),
    SuperHero(1, "Hulk", "SuperStrength", Marvel),
    SuperHero(9, "Batman", "SuperMoney", DC),
    SuperHero(4, "Captain America", "ModerateStrength", Marvel)
  )
}