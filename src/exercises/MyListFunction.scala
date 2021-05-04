package exercises

abstract class MyListFunctions[+A] {
  def head: A
  def tail: MyListFunctions[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyListFunctions[B]
  def printElements: String
  override def toString: String = s"[${printElements}]"

  def map[B](myTransformer: A => B): MyListFunctions[B] // use Function1
  def flatMap[B](myTransformer: A => MyListFunctions[B]): MyListFunctions[B]
  def filter(myPredicate: A => Boolean): MyListFunctions[A]

  def ++[B >: A](list: MyListFunctions[B]): MyListFunctions[B]  // method to concatenate
}

case object EmptyFunctions extends MyListFunctions[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyListFunctions[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyListFunctions[B] = new ConsFunctions(element, EmptyFunctions)
  def printElements: String = ""

  def map[B](myTransformer: Nothing => B): MyListFunctions[B] = EmptyFunctions
  def flatMap[B](myTransformer: Nothing => MyListFunctions[B]): MyListFunctions[B] = EmptyFunctions
  def filter(myPredicate: Nothing => Boolean): MyListFunctions[Nothing] = EmptyFunctions

  def ++[B >: Nothing](list: MyListFunctions[B]): MyListFunctions[B] = list
}

case class ConsFunctions[+A](h: A, t: MyListFunctions[A]) extends MyListFunctions[A] {
  def head: A = h
  def tail: MyListFunctions[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyListFunctions[B] = new ConsFunctions(element, this)
  def printElements: String = {
    if (tail.isEmpty) "" + head
    else head + " " + tail.printElements
  }

  def map[B](myTransformer: A => B): MyListFunctions[B] =
    new ConsFunctions(myTransformer(head), tail.map(myTransformer))
  def flatMap[B](myTransformer: A => MyListFunctions[B]): MyListFunctions[B] =
    myTransformer.apply(head) ++ tail.flatMap(myTransformer)
  def filter(myPredicate: A => Boolean): MyListFunctions[A] =
    if (myPredicate.apply(head)) new ConsFunctions(head, tail.filter(myPredicate))
    else tail.filter(myPredicate)
  // myPredicate.apply(head) equivalent to myPredicate(head)
  def ++[B >: A](list: MyListFunctions[B]): MyListFunctions[B] = new ConsFunctions(head, tail ++ list)
}

object ListTestFunctions extends App {
  val listInt: MyListFunctions[Int] = new ConsFunctions(1,EmptyFunctions)
  println(listInt.toString)
  val list2 = listInt.add(3)
  val list3 = list2.add(4)
  println(list3.toString)

  val listOfStrings: MyListFunctions[String] = new ConsFunctions("X", new ConsFunctions("Y", new ConsFunctions("Z",EmptyFunctions)))
  println(listOfStrings.toString)

  //  println(list3.map(new MyTransformer[Int, Int] {
  //    override def transform(element: Int): Int = element * 2
  //  }).toString)
  // or:
  println(list3.map(new Function1[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }).toString)
  // or:
  println(list3.map((element: Int) => element * 2).toString)
  // or
  println(list3.map(element => element * 2).toString)


  println(list3.filter(new Function1[Int, Boolean] {
    override def apply(element: Int): Boolean = element % 2 == 0
  }).toString)
  // or
  println(list3.filter(element => element % 2 == 0))

  println(list3.flatMap(new Function[Int, MyListFunctions[Int]] {
    override def apply(element: Int): MyListFunctions[Int] = new ConsFunctions(element, new ConsFunctions(element + 1, EmptyFunctions))
  }).toString)
  // or
  println(list3.flatMap(element => new ConsFunctions(element, new ConsFunctions(element * element, EmptyFunctions))))

  // case classes
  val listOfStrings2: MyListFunctions[String] = ConsFunctions("X", ConsFunctions("Y", ConsFunctions("Z",EmptyFunctions)))
  println(listOfStrings == listOfStrings2) // true, because of case classes
}
