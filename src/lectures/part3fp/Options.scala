package lectures.part3fp

import java.util.Random

/**
 * Created by Daniel.
 */
object Options extends App {

  val myFirstOption: Option[Int] = Some(4) // Some and None are child classes of Option
  val noOption: Option[Int] = None

  println(myFirstOption)

  // WORK with unsafe APIs
  def unsafeMethod(): String = null
  // println(unsafeMethod().length) // -> NullPointerException
  //  val result = Some(unsafeMethod()) // WRONG
  val result = Option(unsafeMethod()) // Some or None
  println("result: " + result)

  // chained methods
  def backupMethod(): String = "A valid result"
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))

  // DESIGN unsafe APIs
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")
  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()


  // functions on Options
  println(myFirstOption.isEmpty)
  println(myFirstOption.get)  // UNSAFE - DO NOT USE THIS

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(x => x > 10)) // None
  println(myFirstOption.flatMap(x => Option(x * 10))) // Some(40)

  // for-comprehensions

  /*
    Exercise.
   */
  val config: Map[String, String] = Map(
    // fetched from elsewhere
    "host" -> "176.45.36.1",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected" // connect to some server
  }
  object Connection {
    val random = new Random(System.nanoTime())

    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }

  // try to establish a connection, if so - print the connect method
  val host = config.get("host") // return value is Option[String]
  val port = config.get("port") // return value is Option[String]
  /* standard implementation
    if (h != null)
      if (p != null)
        return Connection.apply(h, p)
    return null
   */
  val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h, p)))

  /*
    if (c != null)
      return c.connect
    return null
   */
  val connectionStatus = connection.map(c => c.connect)
  // if (connectionStatus == null) println(None) else print (Some(connectionstatus.get))
  println(connectionStatus)
  /*
    if (status != null)
      println(status)
   */
  connectionStatus.foreach(println) // only print if connectionStatus != null


  // Alternatives:
  // chained calls
  println("chain calls:")
  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port))
      .map(connection => connection.connect))
    .foreach(println) // only print if connection != null

  // for-comprehensions
  println("for-comprehensions:")
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
  forConnectionStatus.foreach(println)

  println()
  println("Test flatMap:")
  val testList = List("Hallo", "World")
  val testList2 = List(1,3,5,7,9)
  println(testList.flatMap(s => s))
  println(testList2.flatMap(i => List(i*i, "A")))
}
