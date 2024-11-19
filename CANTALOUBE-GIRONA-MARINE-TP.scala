// Databricks notebook source
import scala.io.Source
import play.api.libs.json._

//extract user with more than 25 years old
object FilterUsers {
  def main(args: Array[String]): Unit = {
    val jsonFile = "data.json"
    val jsonData = Source.fromFile(jsonFile).getLines().mkString

    val parsedJson = Json.parse(jsonData)

    parsedJson match {
      case JsArray(users) =>
        val filteredUsers = users.filter { user =>
          (user \ "age").asOpt[Int].exists(_ > 25)
        }

        filteredUsers.foreach { user =>
          println(Json.prettyPrint(user))
        }

      case _ =>
        println("JSON file doesn't contain a valid tab.")
    }
  }
}


//extract name and city
object ExtractNameAndCity {
  def main(args: Array[String]): Unit = {
    val jsonFile = "data.json"
    val jsonData = Source.fromFile(jsonFile).getLines().mkString

    val parsedJson = Json.parse(jsonData)

    parsedJson match {
      case JsArray(users) =>
        val extractedData = users.map { user =>
          val name = (user \ "name").asOpt[String].getOrElse("N/A")
          val city = (user \ "city").asOpt[String].getOrElse("N/A")

          Json.obj("name" -> name, "city" -> city)
        }

        extractedData.foreach { data =>
          println(Json.prettyPrint(data))
        }

      case _ =>
        println("JSON file doesn't contain a valid tab.")
    }
  }
}

//group by cities
object GroupByCities {
  def main(args: Array[String]): Unit = {
    val jsonFile = "data.json"
    val jsonData = Source.fromFile(jsonFile).getLines().mkString

    val parsedJson = Json.parse(jsonData)

    parsedJson match {
      case JsArray(users) =>
        val groupedData = users.groupBy { user =>
          (user \ "city").asOpt[String].getOrElse("N/A")
        }

        groupedData.foreach { case (city, users) =>
          println(s"$city: ${users.length} users")
        }

      case _ =>
        println("JSON file doesn't contain a valid tab.")
    }
  }
}
