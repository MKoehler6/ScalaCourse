package lectures.part2oop

import playground.{PrinceCharming, Cinderella => Princess}
import playground.Cinderella
import java.util.Date
import java.sql.{Date => SqlDate}

/**
 * Created by Daniel.
 */
object PackagingAndImports extends App {

  // package members are accessible by their simple name
  val writer = new Writer("Daniel", "RockTheJVM", 2018)

  // import the package
  val princess = new Cinderella  // playground.Cinderella = fully qualified name
  val princess2 = new Princess // you can rename a class Cinderella => Princess, useful for Date, exists in many packages
  // packages are in hierarchy
  // matching folder structure.

  // package object
  sayHello
  println(SPEED_OF_LIGHT)

  // imports
  val prince = new PrinceCharming

  // 1. use FQ names
  val date = new Date
  val sqlDate = new SqlDate(2018, 5, 4)
  // 2. use aliasing

  // default imports
  // java.lang - String, Object, Exception
  // scala - Int, Nothing, Function
  // scala.Predef - println, ???

}