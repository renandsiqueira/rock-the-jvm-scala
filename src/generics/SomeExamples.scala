package generics

object SomeExamples extends App {
  // VARIANCE PROBLEMS
  abstract class Animal
  case class Dog() extends Animal
  case class Cat() extends Animal

  // 1. YES, List[Cat] extends List[Animal] = COVARIANCE
  class CovarianceList[+A]
  val listOfAnimals: CovarianceList[Animal] = new CovarianceList[Cat]
  // listOfAnimals.add(new Dog) ??? HARD QUESTION => we return a list of animals

  // 2. NO = INVARIANCE
  class InvariantList[A]
  val invariantList: InvariantList[Animal] = new InvariantList[Animal]

  // 3. HELL, NO! = CONTRAVARIANCE
  class Trainer[-A]
  val trainers: Trainer[Cat] = new Trainer[Animal]

  // bonded types
  class Cage[A <: Animal](animal: Animal)
  new Cage(new Dog)

  class Car
  // new Cage(new Car) Car ins`t a Animal

  class Cage2[A >: Animal](animal: Animal)
}
