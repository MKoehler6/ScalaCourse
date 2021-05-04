package lectures.part1basics

/**
 * Created by Daniel on 07-May-18.
 */
object Expressions extends App {

  val x = 1 + 2 // EXPRESSION
  println(x)

  println(2 + 3 * 4)
  // + - * / & | ^ << >> >>> (right shift with zero extension)

  println(1 == x)
  // == != > >= < <=

  println(!(1 == x))
  // ! && ||

  var aVariable = 2
  aVariable += 3 // also works with -= *= /= ..... side effects
  println(aVariable)

  // Instructions (things to do: println() or send something, change a variable)
  // vs
  // Expressions (something that has a VALUE or type, give me a value of something) !!!

  // IF expression
  val aCondition = true
  val aConditionedValue = if(aCondition) 5 else 3 // IF EXPRESSION: RETURNS A VALUE
  println(aConditionedValue)
  println(if(aCondition) 5 else 3)
  println(1 + 3)

  var i = 0
  val aWhile = while (i < 10) {
    println(i)
    i += 1
  }
  // NEVER WRITE THIS AGAIN.

  // EVERYTHING in Scala is an Expression!

  val aWeirdValue = (aVariable = 3) // type = Unit === void
  println(aWeirdValue) // --> ()

  // side effects: println(), whiles, reassigning: are expressions and all return Unit

  // Code blocks (are Expressions, the value (return type) is the last expression: string in this case:)

  val aCodeBlock = {
    val y = 2  // visible only in code block
    val z = y + 1

    if (z > 2) "hello" else "goodbye"
  }
  println(aCodeBlock) // hello

  // 1. difference between "hello world" vs println("hello world")?
  // --> string "..." is a value with type string,
  // println(...) is expression with type Unit and has the side effect printing something

  val someValue = {
    2 < 3
  }
  println(someValue) // true

  val someOtherValue = {
    if(someValue) 239 else 986
    42
  }
  println(someOtherValue) // 42


}
