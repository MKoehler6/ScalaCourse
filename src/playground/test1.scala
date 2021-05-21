package playground

import java.time.LocalDate
import scala.annotation.tailrec

object test1 extends App {

  def apply(f: Int => String, x: Int) = f(x)

  def f2(number: Int): String = number.toString

  def f3square(number: Int): String = (number * number).toString

  println(apply(f2, 3))
  println(apply(f3square, 3))

  def power(base: Int, exponent:Int): Int = {
    @tailrec
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


  def nTimes(f: Int => Int, n: Int, x: Int): Int =
    if (n <= 0) x
    else nTimes(f, n-1, f(x))

  val plusOne = (x: Int) => x + 1
  println(nTimes(plusOne, 10, 1))

  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) =
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n-1)(f(x))
  val plus10 = nTimesBetter(plusOne, 10)
  println(plus10(1))


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

  def superAdd(a: Int)(b: Int) = a + b

  val superAdd2 = (a: Int) => (b: Int) => a + b

  println(superAdd(3)(4))
  println(superAdd2(3)(4))


  def myFormatter(c: String): Double => String = x => c.format(x)

  val standardFormat = myFormatter("%.2f")
  val preciseFormat = myFormatter("%.8f")

  println(standardFormat(Math.PI))
  println(preciseFormat(Math.PI))

  val text = "Die Menge an Daten in der Welt steigt seit Jahren mit großer Geschwindigkeit an. " +
    "Damit wächst der Bedarf in Wissenschaften und Wirtschaft, durch Analyse dieser Daten Informationen und Wissen zu generieren. " +
    "In vielen Bereichen sind Daten heute die Grundlage für wichtige Entscheidungen. Beispiele dafür sind Klimaforschung, " +
    "Businessanalysen, Pandemiebewältigung oder medizinische Diagnosen.\n\nData Science als interdisziplinäres Wissenschaftsfeld " +
    "beschäftigt sich mit dem Prozess, aus Daten nützliche Informationen zu gewinnen. Dafür werden Methoden aus Mathematik, " +
    "Statistik und Informatik angewendet.  \n\nIn der Informatik hat der Umgang mit den wachsenden Datenmengen viele neue " +
    "Technologien hervorgebracht. Es sind neue Tools und Frameworks entstanden. Die Programmiersprachen Python, R und Scala " +
    "haben sich zu den am meisten benutzten Sprachen für Data Science Anwendungen herauskristallisiert.\n\nScala ist im " +
    "Vergleich zu Python und R noch relativ wenig verbreitet, jedoch wächst ihre Bedeutung gerade im Zusammenhang mit Big Data " +
    "Anwendungen kontinuierlich an. Das liegt besonders an den funktionalen Anteilen der Sprache. Damit können Programme " +
    "geschrieben werden, die eine präzise und gut testbare Codestruktur besitzen. Besonders für verteilte Anwendungen und " +
    "parallele Verarbeitung von großen Datenmengen ist Scala sehr gut geeignet.\n\nDie vorliegende Arbeit möchte die Rolle der " +
    "Programmiersprache Scala im Zusammenhang mit Data Science Engineering beleuchten. Die ersten Kapitel geben einen kurzen " +
    "Überblick zu den Begriffen Data Science und Data Science Engineering. Danach werden die Grundlagen von Scala erläutert. " +
    "Die anschließenden Kapitel gehen auf die Besonderheiten von Scala ein. Der Fokus liegt dabei auf der speziellen " +
    "Eignung von Scala für Data Science Engineering."

  val words = text.split(' ').toVector
  val wordsPar: scala.collection.concurrent.Map  = words.

  val list = (1 to 10000).toList
  list.map(_ + 42)
  list.par.map(_ + 42)
}
