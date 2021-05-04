package lectures.part2oop

/**
 * Created by Daniel.
 */
object Objects extends App {

  // SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY ("static")
  object Person { // Objects do not receive parameters
    // "static"/"class" - level functionality
    // Scala object = SINGLETON INSTANCE
    val N_EYES = 2
    def canFly: Boolean = false
    println(Person.N_EYES)

    // factory method
    def apply(mother: Person, father: Person): Person = new Person("Bobbie")
  }
  class Person(val name: String) {
    // instance-level functionality
  }
  // Pattern: object and class with same name in same scope = COMPANIONS

  println(Person.N_EYES)
  println(Person.canFly)

  val mary = new Person("Mary") // with new: class will be used
  val john = new Person("John")
  println(mary == john) // false

  val person1 = Person // object will be used, only one instance
  val person2 = Person
  println(person1 == person2) // true

  val bobbie = Person(mary, john) // = Person.apply(mary, john), factory method

  // Scala Applications = Scala object with def main(args: Array[String]): Unit
  // if you omit "extends App" you need def main...

  val k = 6.67e-11
}
