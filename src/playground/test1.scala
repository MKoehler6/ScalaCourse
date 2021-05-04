package playground

object test1 extends App {

  def apply(f: Int => String, x: Int) = f(x)

  def f2(number: Int): String = number.toString

  def f3square(number: Int): String = (number * number).toString

  println(apply(f2, 3))
  println(apply(f3square, 3))
}
