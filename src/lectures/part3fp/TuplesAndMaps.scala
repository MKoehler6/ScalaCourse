package lectures.part3fp

import scala.annotation.tailrec

/**
 * Created by Daniel.
 */
object TuplesAndMaps extends App {

  // tuples = finite ordered "lists"
  val aTuple = (2, "hello, Scala")  // Tuple2[Int, String] = (Int, String)

  println(aTuple._1)  // 2
  println(aTuple.copy(_2 = "goodbye Java"))
  println(aTuple.swap)  // ("hello, Scala", 2)

  // Maps - keys -> values
  val aMap: Map[String, Int] = Map()

  val phonebook = Map(("Jim", 555), "Daniel" -> 789, ("JIM", 9000)).withDefaultValue(-1) // avoid "key not found" Exception
  // a -> b is sugar for (a, b)
  println(phonebook)

  // map ops
  println(phonebook.contains("Jim"))
  println(phonebook("Mary")) // -1

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhonebook = phonebook + newPairing
  println(newPhonebook)

  // functionals on maps
  // map, flatMap, filter
  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys
  println(phonebook.filterKeys(x => x.startsWith("J")))
  // mapValues
  println(phonebook.mapValues(number => "0245-" + number))

  // conversions to other collections
  println(phonebook.toList)
  println(List(("Daniel", 555)).toMap)
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0)))
  println()

  /*
    1.  What would happen if I had two original entries "Jim" -> 555 and "JIM" -> 900
        !!! careful with mapping keys.
    2.  Overly simplified social network based on maps
        Person = String
        - add a person to the network
        - remove
        - friend (mutual)
        - unfriend
        - number of friends of a person
        - person with most friends
        - how many people have NO friends
        - if there is a social connection between two people (direct or not)
   */
  // ========================================================================================
  // My implementation
  def add2(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network + (person -> Set[String]())

  def friend2(network: Map[String, Set[String]], person1: String, person2: String): Map[String, Set[String]] = {
    val friends1 = network(person1)
    val friends2 = network(person2)
    network + (person1 -> (friends1 + person2)) + (person2 -> (friends2 + person1))
  }
  def unfriend2(network: Map[String, Set[String]], person1: String, person2: String): Map[String, Set[String]] = {
    val friends1 = network(person1)
    val friends2 = network(person2)
    network + (person1 -> (friends1 - person2)) + (person2 -> (friends2 - person1))
  }

  def remove2(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    def removeFriends(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
      if (network(person).isEmpty) network
      else {
        val friend = network(person).head
        val networkTemp = network + (friend -> (network(friend) - person))
        removeFriends(networkTemp + (person -> (network(person) - friend)), person)
      }
    }
    val networkFriendsCleared = removeFriends(network, person)
    networkFriendsCleared - person
  }

  def socialConnection2(network: Map[String, Set[String]], a: String, b: String): Boolean = {
    if (network(a).isEmpty || network(b).isEmpty) false
    else if (network(a).contains(b)) true
    else {
      var foundPersons = Set[String](a)
      def iterateNetwork(friends: Set[String]): Boolean  = {
        if (friends.isEmpty) false
        else {
          if (foundPersons.contains(friends.head)) iterateNetwork(friends.tail)
          else {
            foundPersons = foundPersons + friends.head
            friends.head.equals(b) | iterateNetwork(friends.tail) | iterateNetwork(network(friends.head))
          }
        }
      }
      iterateNetwork(network(a))
    }
  }

  println("My network: ")
  val empty2: Map[String, Set[String]] = Map()
  val network2 = add(add(empty2, "Bob"), "Mary")
  println(network2)
  println(friend(network2, "Bob", "Mary"))
  // println(unfriend(friend(network2, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(friend(network2, "Bob", "Mary"), "Bob"))

  val people2 = add(add(add(add(add(add(add(empty2, "Bob"), "Mary"), "Jim"), "Micha"), "Mike"), "A"), "B")
  val jimBob2 = friend(people2, "Bob", "Jim")
  val testNet2 = friend(jimBob2, "Bob", "Mary")
  val testNet3 = friend(testNet2, "Jim", "Micha")
  val testNet4 = friend(testNet3, "Mike", "Micha")
  val testNet5 = friend(testNet4, "A", "B")

  println(testNet5)
  println(socialConnection2(testNet5, "Mary", "Jim") + " T")
  println(socialConnection2(testNet5, "Micha", "Mary") + " T")
  println(socialConnection2(testNet5, "Mike", "Mary") + " T")
  println(socialConnection2(testNet5, "A", "B") + " T")
  println(socialConnection2(testNet5, "A", "Mary") + " F")
  println(socialConnection2(testNet5, "B", "Bob") + " F")
  println(socialConnection2(network2, "Mary", "Bob") + " F")
  println("===================================================")
  println()

  // =============================================================================================

  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network + (person -> Set())

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA + b)) + (b -> (friendsB + a))
  }

  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA - b)) + (b -> (friendsB - a))
  }

  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] =
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))

    val unfriended = removeAux(network(person), network)
    unfriended - person
  }

  println(("Network:"))
  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Bob"), "Mary")
  println(network)
  println(friend(network, "Bob", "Mary"))
  println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(friend(network, "Bob", "Mary"), "Bob"))

  // Jim,Bob,Mary
  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Mary")

  println(testNet)

  def nFriends(network: Map[String, Set[String]], person: String): Int =
    if (!network.contains(person)) 0
    else network(person).size

  println(nFriends(testNet, "Bob"))

  def mostFriends(network: Map[String, Set[String]]): String =
    network.maxBy(pair => pair._2.size)._1 // pair._2 = value of map = Set[String]  ._1 = return the key of found pair

  println(mostFriends(testNet))

  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int =
    network.count(_._2.isEmpty) // or: network.count(pair => pair._2.isEmpty)
  println(nPeopleWithNoFriends(testNet))
  // alternative
  def nPeopleWithNoFriends2(network: Map[String, Set[String]]): Int =
    network.filter(pair => pair._2.isEmpty).size

  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {
    @tailrec
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
      }
    }

    bfs(b, Set(), network(a) + a)
  }

  println(socialConnection(testNet, "Mary", "Jim"))
  println(socialConnection(network, "Mary", "Bob"))

}