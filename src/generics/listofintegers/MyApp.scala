package generics.listofintegers

object MyApp extends App {
  val listOfIntegers: MyList[Int] = new Element(1, new Element(2, new Element(3, Empty)))
  println(s"head -> ${listOfIntegers.head}")
  println(s"tail -> ${listOfIntegers.tail}")
  println(s"second element -> ${listOfIntegers.tail.head}")
  println(s"toString -> $listOfIntegers")
}

abstract class MyList[Int] {
  def head: Int
  def tail: MyList[Int]
  def isEmpty: Boolean
  def add(element: Int): MyList[Int]
  private[listofintegers] def printElements: String
  override def toString: String = s"[$printElements]"
}

class Element(h: Int, t: MyList[Int]) extends MyList[Int] {
  override def head: Int = h
  override def tail: MyList[Int] = t
  override def isEmpty: Boolean = false
  override def add(element: Int): MyList[Int] = new Element(element, this)
  override def printElements: String =
    if (t.isEmpty) h.toString
      else h.toString + " " + t.printElements
}

object Empty extends MyList[Int] {
  override def head: Int = throw new NoSuchElementException
  override def tail: MyList[Int] = throw  new NoSuchElementException
  override def isEmpty: Boolean = true
  override def add(element: Int): MyList[Int] = new Element(element, Empty)
  override def printElements: String = ""
}