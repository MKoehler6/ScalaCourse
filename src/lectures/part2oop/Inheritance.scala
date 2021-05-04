package lectures.part2oop

/**
 * Created by Daniel.
 */
object Inheritance extends App {

  // constructors
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name) // Superclass needs parameters
                                                                           // of one of the constructors

  // single class inheritance
  sealed class Animal { // sealed: only inherit in this file
    val creatureType = "wild"
    def eat = println("Animal: nomnom")
  }

  class Cat extends Animal {
    def crunch = {
      eat
      println("Cat: crunch crunch")
    }
  }

  val cat = new Cat
  cat.crunch

  // overriding
  class Dog(override val creatureType: String) extends Animal {
    //    override val creatureType = "domestic"  // --> same thing like overriding in parameters
    override def eat = {
      super.eat
      println("Dog: crunch, crunch")
    }
  }
  val dog = new Dog("K9")
  dog.eat
  println(dog.creatureType)


  // type substitution (broad: polymorphism)
  val unknownAnimal: Animal = new Dog("K9")
  unknownAnimal.eat // -> Dog: crunch, crunch

  // overRIDING vs overLOADING

  // preventing overrides (vermeiden):
  // 1 - use "final" on method in superclass
  // 2 - use "final" on the entire class: you can't inherit
  // 3 - seal the class = extend classes in THIS FILE, prevent extension in other files
}
