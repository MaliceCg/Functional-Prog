// Databricks notebook source
def f(x: Int): Int = x + 2
def g(x: Int): Int = x + 2
def h(x: Int): Int = f(g(x))

// COMMAND ----------

val name: String = "Scala"
val age: Int = 25

// COMMAND ----------

val name: String = "Scala"
val age: Int = "25"

// COMMAND ----------

val x = 10
val y = 5
val z = x + y

// COMMAND ----------

val z = x.+(y)

// COMMAND ----------

trait Shape {
  def area(): Int
}

class Square(length: Int) extends Shape {
  def area = length * length
}

class Rectangle(length: Int, width: Int) extends Shape {
  def area = length * width
}

val square = new Square(10)
val area = square.area

// COMMAND ----------

val twoElementContainer = (10, "Bob")
val threeElementContainer = ("15", "Franck", true)

// COMMAND ----------

val first = threeElementContainer._1
val second = threeElementContainer._2
val third = threeElementContainer._3

// COMMAND ----------

val arr = Array(10, 2, 30, 4)
arr(0) = 50
val first = arr(0)

// COMMAND ----------

val list = List(1, 2, 3, 4)
val list2 = (1 to 20).toList

// COMMAND ----------

val list3 = arr.toList

// COMMAND ----------

list3.isEmpty

// COMMAND ----------

val v1 = Vector(0, 1, 2, 3, 4, 5)
val v2 = v1 :+ 6
val v3 = v2 :+ 7

// COMMAND ----------

val fruits = Set("pomme", "orange", "banane", "ananas", "kiwi")
fruits.contains("fraise")

// COMMAND ----------

val capitals = Map("France"->"Paris", "Allemagne"->"Berlin", "Royaume-Uni"->"Londre")
val franceCapital = capitals("France")

// COMMAND ----------

val myList = List(1, 2, 3, 4)
val multipliedList = myList.map(_ * 2)

// COMMAND ----------

val line = "scala is (presque) fun"

// COMMAND ----------

val singleSpace = " "

// COMMAND ----------

val words = line.split(singleSpace)

// COMMAND ----------

val arrayOfListOfChars = words.map(_.toList)

// COMMAND ----------

val arrayOfListOfChars = words.flatMap(_.toList)

// COMMAND ----------

val numbers = (1 to 10).toList
val pair = numbers.filter(_%2 == 0)

// COMMAND ----------

val phrase = List("Scala", "est", "(askip)", "fun")
phrase.foreach(println)
