trait JsonSerializer[T] {
  def toJson(value: T): String
}

case class Person(name: String, age: Int)

def jsonOf[T](value: T)(implicit serializer: JsonSerializer[T]): String = {
  serializer.toJson(value)
}



object JsonSerializerExercise {

val luis = Person("Luis", 27)
val pedro = Person("Pedro", 23)
val maria = Person("Maria", 24)
val ana = Person("Ana", 23)

val someGuy: Option[Person] = Some(luis)
val somePeopleList: List[Option[Person]] = List(Some(luis), Some(maria), None, Some(pedro))
val peopleMap = Map(1 -> luis, 2 -> pedro, 3 -> maria, 4 -> ana)


val luisJson = jsonOf(luis)
val someGuyJson = jsonOf(someGuy)
val somePeopleListJson = jsonOf(somePeopleList)
val peopleMapJson = jsonOf(peopleMap)

}