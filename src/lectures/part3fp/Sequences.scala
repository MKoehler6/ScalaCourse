package lectures.part3fp

import scala.util.Random

/**
 * Created by Daniel.
 */
object Sequences extends App {

  // Seq
  val aSequence = Seq(1,3,2,4) // List(1, 3, 2, 4): object Seq has a factory method -> returns a subtype: list
  println(aSequence)
  println(aSequence.reverse)
  println(aSequence(2))
  println(aSequence ++ Seq(7,5,6)) // concat
  println(aSequence.sorted)

  // Ranges
  val aRange1: Seq[Int] = 1 until 10 // 123456789
  aRange1.foreach(print)
  val aRange2: Seq[Int] = 1 to 10 // 12345678910
  aRange2.foreach(print)

  (1 to 5).foreach(x => println("Hello"))

  // lists                     in standard library cons is written as '::'
  val aList = List(1,2,3)
  val prepend = 42 :: aList  // List(42, 1, 2, 3)
  val prepended = 42 +: aList :+ 89 // List(42, 1, 2, 3, 89)
  println(prepended)
  println(aList.mkString("-|-")) // 1-|-2-|-3

  val apples5 = List.fill(5)("apple") // List(apple, apple, apple, apple, apple)
  println(apples5)

  // arrays
  val numbers = Array(1,2,3,4)
  val threeElements = Array.ofDim[String](3)
  threeElements.foreach(println) // null null null
  val defaultValues = Array.ofDim[Int](3)
  defaultValues.foreach(println) // 0 0 0

  // mutation
  numbers(2) = 0  // syntax sugar for numbers.update(2, 0)
  println(numbers.mkString(" ")) // 1 2 0 4

  // arrays and seq
  val numbersSeq: Seq[Int] = numbers  // implicit conversion
  println(numbersSeq)  // ArraySeq(1, 2, 0, 4)

  // vectors
  val vector: Vector[Int] = Vector(1,2,3)
  println(vector)

  // vectors vs lists

  val maxRuns = 1000
  val maxCapacity = 1000000

  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt()) // update on a random index with a random value
      System.nanoTime() - currentTime
    }

    times.sum * 1.0 / maxRuns // average time
  }

  val numbersList = (1 to maxCapacity).toList
  val numbersVector = (1 to maxCapacity).toVector

  // keeps reference to tail
  // updating an element in the middle takes long
  println(getWriteTime(numbersList) + " ns") // 4227324.9 ns = 4.2 ms
  // depth of the tree is small
  // needs to replace an entire 32-element chunk
  println(getWriteTime(numbersVector) + " ns") // 2467.5 ns = 0.002 ms

}
