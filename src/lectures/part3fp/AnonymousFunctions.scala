package lectures.part3fp

/**
 * Created by Daniel.
 */
object AnonymousFunctions extends App {

  // anonymous function (LAMBDA)
  val doubler: Int => Int = (x: Int) => x * 2
  // short: val doubler = (x: Int) => x * 2
  // or :   val doubler: Int => Int = x => x * 2

  // multiple params in a lambda
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b
  // short: val adder = (a: Int, b: Int) => a + b

  // no params
  val justDoSomething: () => Int = () => 3
  // short: val justDoSomething = () => 3

  // careful
  println(justDoSomething) // lectures.part3fp.AnonymousFunctions$$$Lambda$5/123961122@2aafb23c
  println(justDoSomething()) // 3

  // curly braces with lambdas (common style)
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // MOOR syntactic sugar
  val niceIncrementer: Int => Int = _ + 1 // equivalent to x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _ // equivalent to (a,b) => a + b

  /*
    1.  MyList: replace all FunctionX calls with lambdas
    2.  Rewrite the "special" adder as an anonymous function
   */

  // println(list3.map(element => element * 2).toString)
  // println(list3.filter(element => element % 2 == 0).toString)
  // println(list3.flatMap(element => new ConsGenerics(element, new ConsGenerics(element + 1, EmptyGenerics))).toString)

  // shorter for map and filter:
  // println(list3.map(_ * 2).toString)
  // println(list3.filter(_ % 2 == 0).toString)

  val superAdd2: Int => (Int => Int) = x => (y => x + y)



  val superAdd = (x: Int) => (y: Int) => x + y
  println(superAdd(3)(4))
}