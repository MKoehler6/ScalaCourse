package playground

import java.time.LocalDate

import scala.::

object test1 extends App {

  def apply(f: Int => String, x: Int) = f(x)

  def f2(number: Int): String = number.toString

  def f3square(number: Int): String = (number * number).toString

  println(apply(f2, 3))
  println(apply(f3square, 3))

  def power(base: Int, exponent:Int): Int = {
    def powerHelper(i: Int, accumulator: Int): Int = {
      if (i == 0) accumulator
      else powerHelper(i - 1, base * accumulator)
    }
    powerHelper(exponent, 1)
  }

  println(power(2,8))
  println(power(3,3))

  class Person2(val name: String, val yearOfBirth: Int) {
    def getAge(): Int = {
      val year = LocalDate.now.getYear
      year - yearOfBirth
    }
  }
  object testObject {

  }
  val doubler = (x: Int) => x * 2

  println(Range(1,10,1).map(doubler))

  def printStringWithStars(s: String) = println("*** " + s + " ***")
  def stringWithStars(s: String): String = "*** " + s + " ***"
  def printString(s: String) = println(s)

  def testFunction[T](a: String)(f:String => T): T = {
    val aUpper = a.toUpperCase()
    f(aUpper)
  }
  def testFunction2[T](a: String, f:String => T): T = {
    val aUpper = a.toUpperCase()
    f(aUpper)
  }

  def countLetters(a: String): Char => Int = {
    val stringToLowerCase = a.toLowerCase
    s:Char => stringToLowerCase.toVector
      .filter(_ == s)
      .length
  }

  testFunction("wert")(printStringWithStars) // -> WERT
  testFunction2("wert", printStringWithStars) // -> WERT

  println(testFunction("kuckuck"){s: String => s.toVector}) // -> Vector(K, U, C, K, U, C, K)
  println(testFunction2("kuckuck", (s: String) => s.toVector)) // -> Vector(K, U, C, K, U, C, K)

  println(countLetters("Hello World")('l'))

  val aList = List(1,2,3,4)
  val listMatching = aList match {
    //case listOfStrings: List[String] => "a list of strings"
    //case listOfNumbers: List[Int] => "a list of numbers"
    //case List(1, _, _, _) => "List with four elements and the first is 1"
    //case List(1, _*) => "List of arbitrary length and the first element is 1"
    case 1 :: List(_) => "infix pattern"
    case _ => "No matching"
  }
  println(listMatching)

  val simplePartialFunction: PartialFunction[Int, Int] = {
    case 1 => 220
    case 2 => 245
    case 5 => 999
  }
  println(simplePartialFunction(5))

  abstract class Person { def name: String }

  case class Student(name: String) extends Person

  def printPersons(persons: List[Person]) =
    persons.foreach(person => println(person.name))

  val students: List[Student] = List(Student("Mike"), Student("Anne"))
  printPersons(students)

  val aList2 = List(1,2,3)
  val prepend = 42 :: aList2  // List(42, 1, 2, 3)
  val prepended = 42 +: aList2 :+ 89 // List(42, 1, 2, 3, 89)
  println(prepended)
}
