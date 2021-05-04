package lectures.part2oop

/**
 * Created by Daniel.
 */
object Generics extends App {

  class MyList[+A] { // +A means covariant list
    // use the type A
    def add[B >: A](element: B): MyList[B] = ???
    // means: when I add a supertype, then the list turns to a List of B

  }

  class MyMap[Key, Value]

  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]

  // generic methods
  object MyList {
    def empty[A]: MyList[A] = ??? // return Nothing
  }
  val emptyListOfIntegers = MyList.empty[Int]

  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1. yes, List[Cat] extends List[Animal] = COVARIANCE
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog) // ??? HARD QUESTION => we return a list of Animals, line 22

  // 2. NO = INVARIANCE : List of Cats and List of Animals are two different things
  class InvariantList[A]
  // compiler error: val invariantAnimalList: InvariantList[Animal] = new InvariantList[Cat]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]

  // 3. Hell, no! CONTRAVARIANCE
  class ContravariantList[-A]
  val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal] // makes no sense, list of Animals can contain Dogs...
  class Trainer[-A] // Trainer trains animals
  val trainer: Trainer[Cat] = new Trainer[Animal] //make more sense

  // bounded types
  class Cage[A <: Animal](animal: A) // <: means subtypes of animals
  val cage = new Cage(new Dog)

  class Car
  // generic type needs proper bounded type
  //  val newCage = new Cage(new Car)


  // expand MyList to be generic

}