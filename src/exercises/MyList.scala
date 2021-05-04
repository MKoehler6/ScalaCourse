package exercises

abstract class MyList {
  def head: Int
  def tail: MyList
  def isEmpty: Boolean
  def add(element: Int): MyList
  def printElements: String
  override def toString: String = s"[${printElements}]"
}

object Empty extends MyList {
  def head: Int = throw new NoSuchElementException
  def tail: MyList = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Int): MyList = new Cons(element, Empty)
  def printElements: String = ""
}

case class Cons(h: Int, t: MyList) extends MyList {
  def head: Int = h
  def tail: MyList = t
  def isEmpty: Boolean = false
  def add(element: Int): MyList = new Cons(element, this)
  def printElements: String = {
    if (tail.isEmpty) "" + head
    else head + " " + tail.printElements
  }
}

object ListTest extends App {
  val list1 = new Cons(1,Empty)
  println(list1.toString)
  val list2 = list1.add(3)
  println(list2.toString)

  println(new Cons(5, new Cons(7, new Cons(3,Empty))))
}