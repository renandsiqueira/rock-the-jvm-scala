package patternmatching

import scala.util.{Failure, Success, Try}

object MyApp extends App {
  val successfulParse = tryParseInt("2")
  val errorParse = tryParseInt("abc")

  successfulParse match {
    case Success(value) => println(s"the value is $value")
    case Failure(ex) => println(s"exception $ex")
  }

  errorParse match {
    case Success(value) => println(s"the value is $value")
    case Failure(ex) => println(s"exception $ex")
  }

  val optionSuccessfulParse = tryParseOption("2")
  val optionErrorParse = tryParseOption("abc")
  println(optionSuccessfulParse)
  println(optionErrorParse)

  def tryParseInt(value: String): Try[Int] = Try {
    Integer.parseInt(value)
  }

  def tryParseOption(value: String): Option[Int] = {
    try {
      Some(Integer.parseInt(value))
    } catch {
      case ex: Exception => None
    }
  }
}
