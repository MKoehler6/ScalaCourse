package seminar

object ListTest extends App {
  val testList1 = List[Int](1,2,3)

  def transform[A, B](list: List[A], transformedList: List[B], transformer: A => B): List[B] =
    if (list.isEmpty) transformedList
    else transform(list.tail, transformer(list.head) +: transformedList, transformer)

  println(transform(testList1, List[Int](),(a:Int) => a + 1))

}