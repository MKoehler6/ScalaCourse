package exercises

abstract class Maybe[+A] {

  def map[B](myTransformer: A => B): Maybe[B] // use Function1
  def flatMap[B](myTransformer: A => Maybe[B]): Maybe[B]
  def filter(myPredicate: A => Boolean): Maybe[A]

}
case object MaybeNot extends Maybe[Nothing] {

  def map[B](myTransformer: Nothing => B): Maybe[B] = MaybeNot
  def flatMap[B](myTransformer: Nothing => Maybe[B]): Maybe[B] = MaybeNot
  def filter(myPredicate: Nothing => Boolean): Maybe[Nothing] = MaybeNot
}

case class Just[+A](element: A) extends Maybe[A] {

  def map[B](myTransformer: A => B): Maybe[B] =
    Just(myTransformer(element))
  def flatMap[B](myTransformer: A => Maybe[B]): Maybe[B] =
    myTransformer.apply(element)
  def filter(myPredicate: A => Boolean): Maybe[A] =
    if (myPredicate.apply(element)) this
    else MaybeNot
}

object MaybeTest extends App {
  val listOne3 = Just(3)
  println(listOne3)
  println(listOne3.map(_ * 2))
  println(listOne3.flatMap(x => Just(x % 2 == 0)))
  println(listOne3.filter(_ % 2 == 0))
}