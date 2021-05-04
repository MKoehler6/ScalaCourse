package exercises

abstract class MyListGenerics[+A] {
  def head: A
  def tail: MyListGenerics[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyListGenerics[B]
  def printElements: String
  override def toString: String = s"[${printElements}]"

  def map[B](myTransformer: MyTransformer[A,B]): MyListGenerics[B]
  def flatMap[B](myTransformer: MyTransformer[A,MyListGenerics[B]]): MyListGenerics[B]
  def filter(myPredicate: MyPredicate[A]): MyListGenerics[A]

  def ++[B >: A](list: MyListGenerics[B]): MyListGenerics[B]  // method to concatenate
}

case object EmptyGenerics extends MyListGenerics[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyListGenerics[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyListGenerics[B] = new ConsGenerics(element, EmptyGenerics)
  def printElements: String = ""

  def map[B](myTransformer: MyTransformer[Nothing,B]): MyListGenerics[B] = EmptyGenerics
  def flatMap[B](myTransformer: MyTransformer[Nothing,MyListGenerics[B]]): MyListGenerics[B] = EmptyGenerics
  def filter(myPredicate: MyPredicate[Nothing]): MyListGenerics[Nothing] = EmptyGenerics

  def ++[B >: Nothing](list: MyListGenerics[B]): MyListGenerics[B] = list
}

case class ConsGenerics[+A](h: A, t: MyListGenerics[A]) extends MyListGenerics[A] {
  def head: A = h
  def tail: MyListGenerics[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyListGenerics[B] = new ConsGenerics(element, this)
  def printElements: String = {
    if (tail.isEmpty) "" + head
    else head + " " + tail.printElements
  }

  def map[B](myTransformer: MyTransformer[A,B]): MyListGenerics[B] =
    new ConsGenerics(myTransformer.transform(head), tail.map(myTransformer))
  def filter(myPredicate: MyPredicate[A]): MyListGenerics[A] =
    if (myPredicate.test(head)) new ConsGenerics(head, tail.filter(myPredicate))
    else tail.filter(myPredicate)

  def ++[B >: A](list: MyListGenerics[B]): MyListGenerics[B] = new ConsGenerics(head, tail ++ list)
  def flatMap[B](myTransformer: MyTransformer[A,MyListGenerics[B]]): MyListGenerics[B] =
    myTransformer.transform(head) ++ tail.flatMap(myTransformer)
}

trait MyPredicate[-T] {
  def test(element: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(element: A): B
}



object ListTestGenerics extends App {
  val listInt: MyListGenerics[Int] = new ConsGenerics(1,EmptyGenerics)
  println(listInt.toString)
  val list2 = listInt.add(3)
  val list3 = list2.add(4)
  println(list3.toString)

  val listOfStrings: MyListGenerics[String] = ConsGenerics("A", ConsGenerics("B", ConsGenerics("C",EmptyGenerics)))
  println(listOfStrings.toString)

  println(list3.map(new MyTransformer[Int, Int] {
    override def transform(element: Int): Int = element * 2
  }).toString)

  println(list3.filter(new MyPredicate[Int] {
    override def test(element: Int): Boolean = element % 2 == 0
  }).toString)

  println((list3.flatMap(new MyTransformer[Int, MyListGenerics[Int]] {
    override def transform(element: Int): MyListGenerics[Int] = new ConsGenerics(element, new ConsGenerics(element + 1, EmptyGenerics))
  })).toString)


  // case classes
  val listOfStrings2: MyListGenerics[String] = new ConsGenerics("A", new ConsGenerics("B", new ConsGenerics("C",EmptyGenerics)))
  println(listOfStrings == listOfStrings2) // true, because of case classes
}