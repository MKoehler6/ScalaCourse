package exercises

import scala.annotation.tailrec

abstract class MyListHOFCurry[+A] {
  def head: A
  def tail: MyListHOFCurry[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyListHOFCurry[B]
  def printElements: String
  override def toString: String = s"[${printElements}]"

  def map[B](myTransformer: A => B): MyListHOFCurry[B] // use Function1
  def flatMap[B](myTransformer: A => MyListHOFCurry[B]): MyListHOFCurry[B]
  def filter(myPredicate: A => Boolean): MyListHOFCurry[A]

  def ++[B >: A](list: MyListHOFCurry[B]): MyListHOFCurry[B]  // method to concatenate
  def foreach(f: A => Unit): Unit
  def sort(compare: (A,A) => Int): MyListHOFCurry[A]
  def zipWith[B,C](list: MyListHOFCurry[B],zip: (A,B) => C): MyListHOFCurry[C]
  def fold[B](start: B)(operator: (B,A) => B): B
}

case object EmptyHOFCurry extends MyListHOFCurry[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyListHOFCurry[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyListHOFCurry[B] = new ConsHOFCurry(element, EmptyHOFCurry)
  def printElements: String = ""

  def map[B](myTransformer: Nothing => B): MyListHOFCurry[B] = EmptyHOFCurry
  def flatMap[B](myTransformer: Nothing => MyListHOFCurry[B]): MyListHOFCurry[B] = EmptyHOFCurry
  def filter(myPredicate: Nothing => Boolean): MyListHOFCurry[Nothing] = EmptyHOFCurry

  def ++[B >: Nothing](list: MyListHOFCurry[B]): MyListHOFCurry[B] = list
  def foreach(f: Nothing => Unit): Unit = ()
  def sort(compare: (Nothing,Nothing) => Int): MyListHOFCurry[Nothing] = EmptyHOFCurry
  def zipWith[B,C](list: MyListHOFCurry[B], zip: (Nothing,B) => C): MyListHOFCurry[C] = {
    if (!list.isEmpty) throw new RuntimeException("List do not have the same length")
    else EmptyHOFCurry
  }
  def fold[B](start: B)(operator: (B,Nothing) => B): B = start
}

case class ConsHOFCurry[+A](h: A, t: MyListHOFCurry[A]) extends MyListHOFCurry[A] {
  def head: A = h
  def tail: MyListHOFCurry[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyListHOFCurry[B] = new ConsHOFCurry(element, this)
  def printElements: String = {
    if (tail.isEmpty) "" + head
    else head + " " + tail.printElements
  }

  def map[B](myTransformer: A => B): MyListHOFCurry[B] =
    new ConsHOFCurry(myTransformer(head), tail.map(myTransformer))
  def flatMap[B](myTransformer: A => MyListHOFCurry[B]): MyListHOFCurry[B] =
    myTransformer.apply(head) ++ tail.flatMap(myTransformer)
  def filter(myPredicate: A => Boolean): MyListHOFCurry[A] =
    if (myPredicate.apply(head)) new ConsHOFCurry(head, tail.filter(myPredicate))
    else tail.filter(myPredicate)
  // myPredicate.apply(head) equivalent to myPredicate(head)

  def ++[B >: A](list: MyListHOFCurry[B]): MyListHOFCurry[B] = new ConsHOFCurry(head, tail ++ list)
  def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }
  def sort(compare: (A,A) => Int): MyListHOFCurry[A] = {
    def insert(x: A, sortedList: MyListHOFCurry[A]): MyListHOFCurry[A] =
      if (sortedList.isEmpty) new ConsHOFCurry(x, EmptyHOFCurry)
      else if (compare(x, sortedList.head) <= 0) new ConsHOFCurry(x, sortedList)
      else new ConsHOFCurry(sortedList.head, insert(x, sortedList.tail))

    val sortedTail = tail.sort(compare)
    insert(head, sortedTail)
  }
  def zipWith[B,C](list: MyListHOFCurry[B], zip: (A,B) => C): MyListHOFCurry[C] =
    if (list.isEmpty) throw new RuntimeException("List do not have the same length")
    else new ConsHOFCurry[C](zip(head, list.head), tail.zipWith(list.tail, zip))
  def fold[B](start: B)(operator: (B,A) => B): B = {
    tail.fold(operator(start, head))(operator)
  }
}

object ListTestHOFCurry extends App {
  val listInt: MyListHOFCurry[Int] = new ConsHOFCurry(1,EmptyHOFCurry)
  println(listInt.toString)
  val list2 = listInt.add(3)
  val list3 = list2.add(4)
  println(list3.toString)

  val listOfStrings: MyListHOFCurry[String] = new ConsHOFCurry("X", new ConsHOFCurry("Y", new ConsHOFCurry("Z",EmptyHOFCurry)))
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

  println(list3.flatMap(new Function[Int, MyListHOFCurry[Int]] {
    override def apply(element: Int): MyListHOFCurry[Int] = new ConsHOFCurry(element, new ConsHOFCurry(element + 1, EmptyHOFCurry))
  }).toString)
  // or
  println(list3.flatMap(element => new ConsHOFCurry(element, new ConsHOFCurry(element * element, EmptyHOFCurry))))

  // case classes
  val listOfStrings2: MyListHOFCurry[String] = ConsHOFCurry("X", ConsHOFCurry("Y", ConsHOFCurry("Z",EmptyHOFCurry)))
  println(listOfStrings == listOfStrings2) // true, because of case classes

  list3.foreach(x => println(x)) // = list3.foreach(println)
  val list4 = new ConsHOFCurry(1, new ConsHOFCurry(2, new ConsHOFCurry(4, EmptyHOFCurry)))
  println(list4.sort((x,y) => y - x)) // descending

  println(list3.zipWith[Int,Int](list4, (x,y) => x * y))
  println(listOfStrings2.zipWith[Int,String](list4, _ + "" + _))

  println(list3.fold(0)(_ + _)) // [4,3,1] -> 8

  // for-comprehensions
  // combination of 2 lists, each element of list1 with each element of list2
  val forCompr = for {
    x <- list3
    y <- list4
  } yield x + " " + y
  println(forCompr)
}
