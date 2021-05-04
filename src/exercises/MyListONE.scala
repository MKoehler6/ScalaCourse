package exercises

abstract class MyListONE[+A] {
  def element: A
  def add[B >: A](element: B): MyListONE[B]
  def printElement: String
  override def toString: String = s"[${printElement}]"

  def map[B](myTransformer: A => B): MyListONE[B] // use Function1
  def flatMap[B](myTransformer: A => MyListONE[B]): MyListONE[B]
  def filter(myPredicate: A => Boolean): MyListONE[A]

}
case object EmptyOne extends MyListONE[Nothing] {
  def element: Nothing = throw new NoSuchElementException
  def add[B >: Nothing](element: B): MyListONE[B] = new ConsOne(element)
  def printElement: String = ""

  def map[B](myTransformer: Nothing => B): MyListONE[B] = EmptyOne
  def flatMap[B](myTransformer: Nothing => MyListONE[B]): MyListONE[B] = EmptyOne
  def filter(myPredicate: Nothing => Boolean): MyListONE[Nothing] = EmptyOne
}

case class ConsOne[+A](e: A) extends MyListONE[A] {
  def element: A = e
  def add[B >: A](element: B): MyListONE[B] = throw new NoSuchElementException
  def printElement: String = "" + element

  def map[B](myTransformer: A => B): MyListONE[B] =
    ConsOne(myTransformer(element))
  def flatMap[B](myTransformer: A => MyListONE[B]): MyListONE[B] =
    myTransformer.apply(element)
  def filter(myPredicate: A => Boolean): MyListONE[A] =
    if (myPredicate.apply(element)) this
    else EmptyOne
}

object MyListONETest extends App {
  val listOne3 = ConsOne(3)
  println(listOne3)
  println(listOne3.map(_ * 2))
  println(listOne3.flatMap(x => ConsOne(x % 2 == 0)))
  println(listOne3.filter(_ % 2 == 0))
}