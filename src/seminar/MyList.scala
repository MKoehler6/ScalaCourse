package seminar

import scala.annotation.tailrec

object ListTest extends App {

  @tailrec
  def transform[A, B](list: List[A], transformedList: List[B], transformer: A => B): List[B] =
    if (list.isEmpty) transformedList
    else transform(list.tail, transformedList :+ transformer(list.head), transformer)

  val testList1 = List[Int](1,2,3)
  println(transform(testList1, List[Int](), (a:Int) => a + ": Entry"))

}