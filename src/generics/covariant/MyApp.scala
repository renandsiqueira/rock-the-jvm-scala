package generics.covariant

object MyApp extends App {
  val myList: MyList[Int] = Element(1)
  println(myList.add(2).add(3).add(4))

  val myStringList: MyList[String] = Element("a")
  println(myStringList.add("b").add("c").add("d"))

  val myAnimalList: MyList[Animal] = Element(new Dog)
  println(myAnimalList.add(new Cat))
}

abstract class Animal
case class Dog() extends Animal
case class Cat() extends Animal

/***
 * MyList definition
 * head = first element of the list
 * tail = remainder of the list
 * isEmpty = is this list empty
 * add(int) => new list with thi element added
 * toString => string representation of the list
 */
abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  private[covariant] def printElements: String
  override def toString: String = s"[$printElements]"
}

case class Element[+A](value: A, tail: MyList[A] = EmptyList) extends MyList[A] {
  override def head: A = value
  override def isEmpty: Boolean = false
  override def add[B >: A](element: B): MyList[B] = Element(element, this)
  override def printElements: String =
    if(tail.isEmpty) s"$value"
    else s"$value ${tail.printElements}"
}

object EmptyList extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException
  override def tail: MyList[Nothing] = throw new UnsupportedOperationException
  override def isEmpty: Boolean = true
  override def add[B >: Nothing](element: B): MyList[B] = Element(element, this)
  override def printElements: String = ""
}

