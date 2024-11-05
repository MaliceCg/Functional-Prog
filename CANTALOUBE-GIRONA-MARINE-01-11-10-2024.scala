// Databricks notebook source
println("Hello world !")

// COMMAND ----------

var x = 10

// COMMAND ----------

x = 20


// COMMAND ----------

val y = 10

// COMMAND ----------

y = 20

// COMMAND ----------

def add(firstInput: Int, secondInput:Int): Int = {
  val sum = firstInput + secondInput
  return sum
}

// COMMAND ----------

val addNumbers = add(6,7)

// COMMAND ----------

def addSimple(firstInput: Int, secondInput:Int): Int = firstInput + secondInput

// COMMAND ----------

val addTwoNumbers = addSimple(4,5)

// COMMAND ----------

def encode(n:Int, f:(Int)=> Long): Long = {
  val x = n*10
  f(x)
}

// COMMAND ----------

val higherOrderFunctionTest1 = encode(10,(x:Int)=>{x+100})

// COMMAND ----------

val higherOrderFunctionTest2 = encode(5, _ + 100)

// COMMAND ----------

class Car(mk:String, ml:String, cr:String)
{
  val make = mk
  val model = ml
  var color = cr
  def repaint(newColor:String)={
    color = newColor
  }

}

// COMMAND ----------

val mustang = new Car("Ford","Mustang","red")
val corvette = new Car("GM","Corvette","black")

// COMMAND ----------

corvette.repaint("blue")
corvette.color


// COMMAND ----------

case class Message(from:String,to:String,content:String)
val request = Message("harry","sam","discussion")

// COMMAND ----------

def colorToNumber(color:String): Int = {
  val num = color match{
    case "red"=> 1
    case "blue" => 2
    case "green" => 3
    case "yellow" => 4
    case _ => 5
  }
  num
}

// COMMAND ----------

val colorName = "red"
val colorCode = colorToNumber(colorName)
println(s"The color code for $colorName is $colorCode")

// COMMAND ----------

def f(x: Int,y:Int,operator:String):Double = {
  operator match {
    case "+" => x + y
    case "-" => x - y
    case "*" => x * y
    case "/" => x / y.toDouble
  }
}

// COMMAND ----------

val sum = f(10,20,"+")
val product = f(10,20,"*")


// COMMAND ----------

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

// MAGIC %python
// MAGIC x = "Python"
// MAGIC print(x)
// MAGIC
// MAGIC x = 45
// MAGIC print(x)

// COMMAND ----------

val x = 10
val y = 20
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

// MAGIC %md
// MAGIC
