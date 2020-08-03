package generics.expandingcollection

/*
  1. Generic trait MyPredicate[-T] with a little test(T) => boolean
  2. Generic trait MyTransformer[-A, B] with a method transform(A) => B
  3. MyList:
    - map(transformer) => MyList
    - filter(predicate) => MyList
    - flatMap(transformer from A to MyList[B]) => MyList[B]

    class EvenPredicate extends MyPredicate[T]
    class StringToIntTransformer extends MyTransformer[String, Int]

    [1,2,3].map(n * 2) = [2,3,6]
    [1,2,3,4].filter(n % 2) = [2,4]
    [1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]
 */
object MyApp extends App {
  val listOfIntegers = Element(1, Element(2, Element(3)))

  val listOfDoubleOfIntegers = listOfIntegers.map(new MyTransformer[Int, Int] {
    override def transform(element: Int): Int = element * 2
  })
  println("list of double of integers: ")
  println(listOfDoubleOfIntegers)

  val filteredListOfIntegers = listOfIntegers.filter(new MyPredicate[Int] {
    override def test(element: Int): Boolean = element % 2 == 0
  })

  println("list of integers: ")
  println(filteredListOfIntegers)

  val anotherListOfIntegers = Element(4, Element(5))
  println("concatenated list: ")
  println((listOfIntegers ++ anotherListOfIntegers).toString)

  val flatMappedList = listOfIntegers.flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(element: Int): MyList[Int] =
      Element(element, Element(element + 1))
  })

  println("flatMapped list:")
  print(flatMappedList)
}

abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  private[expandingcollection] def printElements: String
  override def toString: String = s"[$printElements]"

  def map[B](myTransformer: MyTransformer[A, B]): MyList[B]
  def flatMap[B](myTransformer: MyTransformer[A, MyList[B]]): MyList[B]
  def filter(predicate: MyPredicate[A]): MyList[A]

  def ++[B >: A](list: MyList[B]): MyList[B]
}

object EmptyList extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException
  override def tail: MyList[Nothing] = throw new UnsupportedOperationException
  override def isEmpty: Boolean = true
  override def add[B >: Nothing](element: B): MyList[B] = Element(element, this)
  override def printElements: String = ""

  override def map[B](myTransformer: MyTransformer[Nothing, B]): MyList[B] = EmptyList
  override def flatMap[B](myTransformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = EmptyList
  override def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = EmptyList
  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
}

case class Element[+A](value: A, tail: MyList[A] = EmptyList) extends MyList[A] {
  override def head: A = value
  override def isEmpty: Boolean = false
  override def add[B >: A](element: B): MyList[B] = Element(element, this)
  override def printElements: String =
    if(tail.isEmpty) s"$value"
    else s"$value ${tail.printElements}"

  /*
    [1,2,3].map(n * 2)
      = Element(2, [2,3].map(n * 2))
      = Element(2, Element(4, [3].map(n * 2)))
      = Element(2, Element(4, Element(3, EmptyList.map(n * 2))))
      = Element(2, Element(4, Element(3, EmptyList)))
   */
  override def map[B](myTransformer: MyTransformer[A,B]): MyList[B] =
    Element(myTransformer.transform(value), tail.map(myTransformer))

  /*
    [1,2].flatMap(n => [n, n+1] =
    = [1,2] ++ [2].flatMap(n => [n, n+1])
    = [1,2] ++ [2,3] ++ Empty.flatMap(n +> [n, n+1])
    = [1,2] ++ [2,3] ++ Empty
   */
  override def flatMap[B](myTransformer: MyTransformer[A, MyList[B]]): MyList[B] =
    myTransformer.transform(value) ++ tail.flatMap(myTransformer)

  /*
    [1,2,3].filter(n % 2 == 0) =
      = [2,3].filter(n % 2 == 0) =
      = Element(2, [3].filter(n % 2 == 0))) =
      = Element(2, EmptyList.filter(n % 2 == 0)) =
      = Element(2, EmptyList)
   */
  override def filter(predicate: MyPredicate[A]): MyList[A] =
    if(predicate.test(value)) Element(value, tail.filter(predicate))
      else tail.filter(predicate)

  /*
    [1,2] ++ [3,4,5] =
    Element(1, [2] ++ [3,4,5]) =
    Element(1, Element(2, EmptyList ++ [3,4,5])
    Element(1, Element(2, Element(3, Element(4, Element(5)))))
   */
  override def ++[B >: A](list: MyList[B]): MyList[B] =
    Element(value, tail ++ list)
}

trait MyPredicate[-T] {
  def test(element: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(element: A): B
}
