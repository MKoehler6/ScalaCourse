package lectures.part1basics

import scala.annotation.tailrec

/**
 * Created by Daniel on 07-May-18.
 */
object Recursion extends App {

  def factorial(n: Int): Int =
    if (n <= 1) 1
    else {
      println("Computing factorial of " + n + " - I first need factorial of " + (n-1))
      val result = n * factorial(n-1)
      println("Computed factorial of " + n)

      result
    }

  println(factorial(10))
  //  println(factorial(5000)) --> StackOverFlowError

  def anotherFactorial(n: Int): BigInt = {
    @tailrec
    def factHelper(x: Int, accumulator: BigInt): BigInt =
      if (x <= 1) accumulator
      else factHelper(x - 1, x * accumulator) // TAIL RECURSION = use recursive call as the LAST expression

    factHelper(n, 1)
  }

  /*
    anotherFactorial(10) = factHelper(10, 1)
    = factHelper(9, 10 * 1)
    = factHelper(8, 9 * 10 * 1)
    = factHelper(7, 8 * 9 * 10 * 1)
    = ...
    = factHelper(2, 3 * 4 * ... * 10 * 1)
    = factHelper(1, 1 * 2 * 3 * 4 * ... * 10)
    = 1 * 2 * 3 * 4 * ... * 10
   */

  //println(anotherFactorial(20000)) // works

  // WHEN YOU NEED LOOPS, USE _TAIL_ RECURSION.

  /*
    1.  Concatenate a string n times
    2.  IsPrime function tail recursive
    3.  Fibonacci function, tail recursive.
   */

  def concatStringTail(aString: String, n: Int): String = {
    @tailrec
    def concatHelper(accum: String, count: Int): String = {
      if (count == 0) accum
      else concatHelper(accum + aString, count - 1)
    }
    concatHelper("",n)
  }
  println(concatStringTail("A ", 5))

  def isPrimeTail(n: Int): Boolean = {
    @tailrec
    def primeHelper(myDivider: Int): Boolean = {
      if (myDivider > n / 2) true
      else if (n % myDivider == 0) false
      else primeHelper(myDivider + 1)
    }
    primeHelper(2)
  }
  println(isPrimeTail(7477))

  def fibonacciTail(n: Int): Int = {
    @tailrec
    def helperFibo(accu1: Int, accu2: Int, count: Int): Int = {
      if (count == n) accu1 + accu2
      else helperFibo(accu2, accu1 + accu2, count + 1)
    }
    if (n <= 2) 1
    else helperFibo(1,1,3)
  }
  println(fibonacciTail(8))

  // ================ Musterlösung ===================

  @tailrec
  def concatenateTailrec(aString: String, n: Int, accumulator: String): String =
    if (n <= 0) accumulator
    else concatenateTailrec(aString, n-1, aString + accumulator)

  println(concatenateTailrec("hello", 3, ""))

  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeTailrec(t: Int, isStillPrime: Boolean): Boolean =
      if (!isStillPrime) false
      else if (t <= 1) true
      else isPrimeTailrec(t - 1, n % t != 0 && isStillPrime)

    isPrimeTailrec(n / 2, true)
  }

  println(isPrime(2003))
  println(isPrime(629))

  def fibonacci(n: Int): Int = {
    def fiboTailrec(i: Int, last: Int, nextToLast: Int): Int =
      if(i >= n) last
      else fiboTailrec(i + 1, last + nextToLast, last)

    if (n <= 2) 1
    else fiboTailrec(2, 1, 1)
  }

  println(fibonacci(8)) // 1 1 2 3 5 8 13, 21
}
