package lectures.part3fp

/**
 * Created by Daniel.
 */
object WhatsAFunction extends App {

  // DREAM: use functions as first class elements
  // problem: oop

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  // function types = Function1[A, B]
  val stringToIntConverter = new Function1[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("3") + 4)

  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  // Function types Function2[A, B, R] === (A,B) => R

  // ALL SCALA FUNCTIONS ARE OBJECTS

  /*
    1.  a function which takes 2 strings and concatenates them
    2.  transform the MyPredicate and MyTransformer into function types
    3.  define a function which takes an int and returns another function which takes an int and returns an int
        - what's the type of this function
        - how to do it
   */

  val concat: ((String, String) => String) = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1 + v2
  }

  abstract class MyListGenerics[+A] {
    // higher order functions
    def map[B](myTransformer: A => B): MyListGenerics[B] // Function1
    def flatMap[B](myTransformer: A => MyListGenerics[B]): MyListGenerics[B] // Function1
    def filter(myPredicate: A => Boolean): MyListGenerics[A] // Function1
    def ++[B >: A](list: MyListGenerics[B]): MyListGenerics[B]  // method to concatenate
  }

  case object EmptyGenerics extends MyListGenerics[Nothing] {
    def map[B](myTransformer: Nothing => B): MyListGenerics[B] = EmptyGenerics
    def flatMap[B](myTransformer: Nothing => MyListGenerics[B]): MyListGenerics[B] = EmptyGenerics
    def filter(myPredicate: Nothing => Boolean): MyListGenerics[Nothing] = EmptyGenerics
    def ++[B >: Nothing](list: MyListGenerics[B]): MyListGenerics[B] = list
  }

  case class ConsGenerics[+A](h: A, t: MyListGenerics[A]) extends MyListGenerics[A] {
    def head: A = h
    def tail: MyListGenerics[A] = t
    def map[B](myTransformer: A => B): MyListGenerics[B] =
      new ConsGenerics(myTransformer(head), tail.map(myTransformer))
    def flatMap[B](myTransformer: A => MyListGenerics[B]): MyListGenerics[B] =
      myTransformer(head) ++ tail.flatMap(myTransformer)
    def filter(myPredicate: A => Boolean): MyListGenerics[A] =
      if (myPredicate(head)) new ConsGenerics(head, tail.filter(myPredicate))
      else tail.filter(myPredicate)
    def ++[B >: A](list: MyListGenerics[B]): MyListGenerics[B] = new ConsGenerics(head, tail ++ list)
  }
//  println(list3.filter(new Function[Int, Boolean] {
//    override def apply(element: Int): Boolean = element % 2 == 0
//  }).toString)


  val myFunction: Int => Function[Int, Int] = new Function[Int, Function[Int, Int]] {
    override def apply(x: Int): Function[Int, Int] = new Function[Int,Int] {
      override def apply(y: Int): Int = x + y
    }
  }




  def concatenator: (String, String) => String = new Function2[String, String, String] {
    override def apply(a: String, b: String): String = a + b
  }
  println(concatenator("Hello ", "Scala"))

  // Function1[Int, Function1[Int, Int]]
  val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int): Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  val adder3 = superAdder(3)
  println(adder3(4))
  println(superAdder(3)(4)) // curried function

}

trait MyFunction[A, B] {
  def apply(element: A): B
}
