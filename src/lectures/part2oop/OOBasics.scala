package lectures.part2oop

/**
 * Created by Daniel.
 */
object OOBasics extends App {

  // constructor
  class Person(name: String, val age: Int = 0) {
    // body
    val x = 2
    println("This is class Person")
    println(1 + 3)

    // method
    def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

    // overloading
    def greet(): Unit = println(s"Hi, I am $name") // name is automatically this.name

    // multiple constructors
    def this(name: String) = this(name, 0)
    def this() = this("John Doe")
  }

  val person = new Person("John", 26) // --> This is class Person .. 4
  // every expression inside {..} will be executed
  // println(person.name) // doesn't work... class parameters are NOT fields
  println(person.age) // works, because "val" or "var" before age in constructor
  println(person.x) // val x is field
  person.greet("Daniel")
  person.greet()

  val author = new Writer("Charles", "Dickens", 1812)
  val imposter = new Writer("Charles", "Dickens", 1812)
  val novel = new Novel("Great Expectations", 1861, author)

  println(novel.authorAge)
  println(novel.isWrittenBy(author)) // true
  println(novel.isWrittenBy(imposter)) // false

  val counter = new Counter
  counter.inc.print
  counter.inc.inc.inc.print
  counter.inc(10).print
}



/*
  Novel and a Writer
  Writer: first name, surname, year
    - method fullname
  Novel: name, year of release, author
  - authorAge
  - isWrittenBy(author)
  - copy (new year of release) = new instance of Novel
 */
/*
  Counter class
    - receives an int value
    - method current count
    - method to increment/decrement => new Counter
    - overload inc/dec to receive an amount
 */

class Counter2(count: Int) {
  def currentCount = count
  def increment() = new Counter2(count + 1) // this is principle IMMUTABILITY, create new instance instead of change count
  def decrement() = new Counter2(count - 1)
  def increment(amount: Int) = new Counter2(count + amount)
  def decrement(amount: Int) = new Counter2(count - amount)
}

class Writer(firstName: String, surname: String, val year: Int) {
  def fullName: String = firstName + " " + surname
}

class Novel(name: String, year: Int, author: Writer) {
  def authorAge = year - author.year
  def isWrittenBy(author: Writer) = author == this.author
  def copy(newYear: Int): Novel = new Novel(name, newYear, author)
  def getNovelName: String = name
  def getNovelYear: Int = year
}

/*
  Counter class
    - receives an int value
    - method current count
    - method to increment/decrement => new Counter
    - overload inc/dec to receive an amount
 */
class Counter(val count: Int = 0) {
  def inc = {
    println("incrementing")
    new Counter(count + 1)  // immutability
  }

  def dec = {
    println("decrementing")
    new Counter(count - 1)
  }

  def inc(n: Int): Counter = {
    if (n <= 0) this
    else inc.inc(n-1)
  }

  def dec(n: Int): Counter =
    if (n <= 0) this
    else dec.dec(n-1)

  def print = println(count)
}

// class parameters are NOT FIELDS
