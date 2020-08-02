package exercises.recursion

object Recursion extends App {

  /*
    1. Concatenate a string n times
    2. Is Prime function
    3. Fibonacci function tail recursive
   */

  // 1.
  def concatenate(value: String, n: Int): String = if(n <= 1) value else value + concatenate(value, n - 1)
  def concatenateTail(value: String, n: Int): String = {
    def concatenateTailRecursive(n: Int, accumulator: String): String = {
      if (n <= 1) accumulator
      else concatenateTailRecursive(n - 1, accumulator + value)
    }

    concatenateTailRecursive(n, value)
  }

  def fibonacci(n: BigInt): BigInt = {
    if (n <= 2) 1
    else fibonacci(n - 1) + fibonacci(n - 2)
  }
  // 1 1 2 3 5 8 13 21

  def fibonacciTail(n: BigInt): BigInt = {
    def fibonacciTailRecursive(n: BigInt, fibLessOne: BigInt, fibLessTwo: BigInt): BigInt = {
      if (n <= 2) 1
        else 1
    }

    fibonacciTailRecursive(n,1, 1)
  }

  //println(concatenateTail("Renan", 10000))
  //println(fibonacciTail(4))

  println("Hello Recursion!")
}
