package lectures.part1basics

/**
 * Created by Daniel.
 */
object DefaultArgs extends App {

  def trFact(n: Int, acc: Int = 1): Int =
    if (n <= 1) acc
    else trFact(n-1, n*acc)

  val fact10 = trFact(10) // acc is by default 1, trFact(10,2) ==> acc will be overwritten
  println(fact10)

  def savePicture(format: String = "jpg", width: Int = 1920, height: Int = 1080): Unit =
    println("saving picture format: " + format + " width: " + width + " height: " + height)
  savePicture(width = 800)

  /*
    1. pass in every leading argument
    2. name the arguments
   */

  savePicture(height = 600, width = 800, format = "bmp")
}
