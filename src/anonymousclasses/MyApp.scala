package anonymousclasses

object MyApp extends App {
  abstract class Animal {
    def eat: Unit
  }

  // anonymous class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("ahahahahahaha")
  }

  /*
    equivalent with

    class MyApp$$anon$1 extends Animal {
      override def eat: Unit = println("ahahahahahaha")
    }

    val funnyAnimal: Animal = new MyApp$$anon$1
   */

  println(funnyAnimal.getClass)
}
