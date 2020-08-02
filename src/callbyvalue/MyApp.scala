package callbyvalue

object MyApp extends App {

  def two(): Int = {
    println("Give me the number two!")
    2
  }

  def callByValue(n: Int): Int = {
    n + n
  }

  def callByName(n: => Int): Int = {
    n + n
  }

  println("Call by Value")
  println(callByValue(two()))
  println("*****************")
  println("Call by Name")
  println(callByName(two()))
}
