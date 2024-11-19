// Databricks notebook source
// MAGIC %md
// MAGIC LOAD DATA JSON AND QUERIES BUT WITHOUT MONGODB

// COMMAND ----------

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._


val programStartTime = System.nanoTime()

// Définition des chemins des fichiers
val file_2024 = "/FileStore/tables/nvdcve_1_1_2024-2.json"
val file_2023 = "/FileStore/tables/nvdcve_1_1_2023-2.json"

// Liste des fichiers JSON à traiter
val jsonFiles = Seq(file_2023, file_2024)

// Définition du schéma
val schema = StructType(Seq(
  StructField("ID", StringType, nullable = true),
  StructField("Description", StringType, nullable = true),
  StructField("baseScore", DoubleType, nullable = true),
  StructField("baseSeverity", StringType, nullable = true),
  StructField("exploitabilityScore", DoubleType, nullable = true),
  StructField("impactScore", DoubleType, nullable = true)
))

// Traitement de chaque fichier JSON
val cveDataFrames = jsonFiles.map { filePath =>
  val jsonData = spark.read.option("multiline", "true").json(filePath)

  // Extraction des champs pertinents pour chaque entrée CVE
  jsonData
    .select(explode(col("CVE_Items")).as("item"))
    .select(
      col("item.cve.CVE_data_meta.ID").as("ID"),
      col("item.cve.description.description_data")(0)("value").as("Description"),
      col("item.impact.baseMetricV3.cvssV3.baseScore").as("baseScore"),
      col("item.impact.baseMetricV3.cvssV3.baseSeverity").as("baseSeverity"),
      col("item.impact.baseMetricV3.exploitabilityScore").as("exploitabilityScore"),
      col("item.impact.baseMetricV3.impactScore").as("impactScore")
    )
}

// Combinaison de toutes les données en un seul DataFrame
val combinedData: DataFrame = cveDataFrames.reduce(_ union _)

// Application du schéma défini
val finalData = combinedData.select(schema.fields.map(field => col(field.name).cast(field.dataType)): _*)

println(s"\nCount of records in JSON files: ${finalData.count()}")
finalData.printSchema()
finalData.show(5)

// Séparer les données de 2023 et 2024
val data2023 = finalData.filter(col("ID").startsWith("CVE-2023"))
val data2024 = finalData.filter(col("ID").startsWith("CVE-2024"))

// Top 5 des impact scores en 2023
println("\nTop 5 des impact scores en 2023:")
data2023.orderBy(col("impactScore").desc)
  .select("ID", "impactScore")
  .show(5)

// Top 5 des impact scores en 2024
println("\nTop 5 des impact scores en 2024:")
data2024.orderBy(col("impactScore").desc)
  .select("ID", "impactScore")
  .show(5)
finalData.write
  .option("header", "true")
  .csv("/FileStore/tables/cve_data_full.csv")

val programElapsedTime = (System.nanoTime() - programStartTime) / 1e9
println(s"\nProgram execution time: $programElapsedTime seconds")

// COMMAND ----------

// MAGIC %md
// MAGIC MongoDb essai : ne fonctionne pas

// COMMAND ----------

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import com.mongodb.spark._
import com.mongodb.spark.config._

val programStartTime = System.nanoTime()

// Définition des chemins des fichiers
val file_2024 = "/FileStore/tables/nvdcve_1_1_2024-2.json"
val file_2023 = "/FileStore/tables/nvdcve_1_1_2023-2.json"

// Liste des fichiers JSON à traiter
val jsonFiles = Seq(file_2023, file_2024)

// Définition du schéma
val schema = StructType(Seq(
  StructField("ID", StringType, nullable = true),
  StructField("Description", StringType, nullable = true),
  StructField("baseScore", DoubleType, nullable = true),
  StructField("baseSeverity", StringType, nullable = true),
  StructField("exploitabilityScore", DoubleType, nullable = true),
  StructField("impactScore", DoubleType, nullable = true)
))

// Traitement de chaque fichier JSON
val cveDataFrames = jsonFiles.map { filePath =>
  val jsonData = spark.read.option("multiline", "true").json(filePath)

  // Extraction des champs pertinents pour chaque entrée CVE
  jsonData
    .select(explode(col("CVE_Items")).as("item"))
    .select(
      col("item.cve.CVE_data_meta.ID").as("ID"),
      col("item.cve.description.description_data")(0)("value").as("Description"),
      col("item.impact.baseMetricV3.cvssV3.baseScore").as("baseScore"),
      col("item.impact.baseMetricV3.cvssV3.baseSeverity").as("baseSeverity"),
      col("item.impact.baseMetricV3.exploitabilityScore").as("exploitabilityScore"),
      col("item.impact.baseMetricV3.impactScore").as("impactScore")
    )
}

// Combinaison de toutes les données en un seul DataFrame
val combinedData: DataFrame = cveDataFrames.reduce(_ union _)

// Application du schéma défini
val finalData = combinedData.select(schema.fields.map(field => col(field.name).cast(field.dataType)): _*)

println(s"\nCount of records in JSON files: ${finalData.count()}")
finalData.printSchema()
finalData.show(5)

// Configuration de la connexion MongoDB Atlas
val mongodbUri = "mongodb://atlas-sql-673c980ec73c40015625d1c3-rpbb0.a.query.mongodb.net/sample_mflix?ssl=true&authSource=admin"
val database = "sample_mflix"
val collection = "cve_data"  // Vous pouvez changer le nom de la collection si nécessaire

val writeConfig = WriteConfig(Map(
  "uri" -> mongodbUri,
  "database" -> database,
  "collection" -> collection
))

// Écriture des données dans MongoDB Atlas
finalData.write
  .format("com.mongodb.spark.sql.DefaultSource")
  .mode("overwrite")
  .options(writeConfig.asOptions)
  .save()

println("Données écrites dans MongoDB Atlas avec succès.")

// Lecture des données depuis MongoDB Atlas
val readConfig = ReadConfig(Map(
  "uri" -> mongodbUri,
  "database" -> database,
  "collection" -> collection
))

val dfFromMongo = MongoSpark.load(spark, readConfig)

println("Données lues depuis MongoDB Atlas :")
dfFromMongo.printSchema()
dfFromMongo.show(5)

val programElapsedTime = (System.nanoTime() - programStartTime) / 1e9
println(s"\nProgram execution time: $programElapsedTime seconds")

// COMMAND ----------

spark.read
  .option("header", "true")
  .csv("/FileStore/tables/cve_data.csv")
  .show(10)  // Affiche les 10 premières lignes
