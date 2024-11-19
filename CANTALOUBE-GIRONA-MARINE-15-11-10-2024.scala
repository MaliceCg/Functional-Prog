// Databricks notebook source
def multiply(a: Int, b: Int): Int = a * b

// COMMAND ----------

val result_after_first_call = multiply(12, 7)

// COMMAND ----------

val result_after_second_call = multiply(12, 7)

// COMMAND ----------

var total = 0
def addToTotal(a: Int): Int = {
  total += a
  total
}

// COMMAND ----------

val total_after_first_call = addToTotal(3)

// COMMAND ----------

val total_after_second_call = addToTotal(3)

// COMMAND ----------

val xs = List(1,2,3,4)

// COMMAND ----------

xs = List(4,2,1,3)

// COMMAND ----------

val newList = 17 :: xs

// COMMAND ----------

def applyTwice(f: Int => Int, x: Int): Int = f(f(x))

// COMMAND ----------

val increment= (x: Int) => x + 1

// COMMAND ----------

println(applyTwice(increment, 3))

// COMMAND ----------

def multiplier(factor: Int): Int => Int = {
  (x: Int) => x * factor
}

// COMMAND ----------

val triple = multiplier(4)
println(triple(20))

// COMMAND ----------

//(val1: Type1, val2: Type2) => expression

(x: Int, y: Int) => x + y

(a, b) => a + b

// COMMAND ----------

val numbers = List(3, 4, 5, 6, 7)

// COMMAND ----------

def add(x: Int)(y: Int): Int = x + y

// COMMAND ----------

add(5)(10)

// COMMAND ----------

val addFive = add(5)_
addFive(7)

// COMMAND ----------

def log(level: String)(message: String): Unit = {
  println(s"[$level]$message")
}

// COMMAND ----------

val infoLog= log("INFO")_
val errorLog= log("ERROR")_

// COMMAND ----------

infoLog("this is an information")
errorLog("this is not an information")

// COMMAND ----------

val numbers = List(3, 4, 5, 6, 7)

val doubled = numbers.map(_ * 2)

val doubleEvens = numbers.collect { case x if x % 2 == 0 => x * 2}

val even = numbers.filter (_ % 2 == 0 )

val lessThanFive = numbers.takeWhile(_ < 5)
val moreThanFive = numbers.dropWhile(_ < 5)

val sum = numbers.reduce(_ + _)
val product = numbers.reduce(_ * _)

val words = List("Scala", "is", "scala")
val sentence = words.foldLeft("Programing in ")(_ + " " + _)

val maxNumber = numbers.reduceLeft((x, y) => if (x > y) x else y)

// COMMAND ----------

val sumOfSuqarre = numbers.aggregate(0)(
  (acc, num) => acc + num *num,
  (acc1, num1) => acc1 + num1
)

// COMMAND ----------

val setA = Set(1, 2, 3)
val setB = Set(4, 5, 6)


// COMMAND ----------

println(setA union setB)
println(setA intersect setB)
