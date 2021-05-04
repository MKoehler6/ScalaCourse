package lectures.part3fp

/**
 * Created by Daniel.
 */
object MapFlatmapFilterFor extends App {

  val list = List(1,2,3)
  println(list.head)
  println(list.tail)

  // map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0))

  // flatMap
  val toPair = (x: Int) => List(x, x+1)
  println(list.flatMap(toPair))

  // print all combinations between two lists
  val numbers = List(1,2,3,4)
  val chars = List('a','b','c','d')

  val combinationOfTwoList = numbers.flatMap(n => chars.map(c => "" + c + n))
  println(combinationOfTwoList)

  val colors = List("black", "white")
  // "iterating"
  val combinations = numbers.filter(_ % 2 == 0).flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + "-" + color)))
  println(combinations)


  // foreach
  list.foreach(println)

  // for-comprehensions: better readable
  val forCombinations = for {
    n <- numbers if n % 2 == 0
    c <- chars
    color <- colors
  } yield "" + c + n + "-" + color
  println(forCombinations)

  for {
    n <- numbers
  } println(n)
  // equivalent to
  numbers.foreach(println)

  // syntax overload
  list.map { x =>
    x * 2
  }

  /*
    1.  MyList supports for comprehensions?
        signature of map, filter and flatMap has to be:
          map(f: A => B) => MyList[B]
          filter(p: A => Boolean) => MyList[A]
          flatMap(f: A => MyList[B]) => MyList[B]
    2.  A small collection of at most ONE element - Maybe[+T]
        - map, flatMap, filter
   */


}