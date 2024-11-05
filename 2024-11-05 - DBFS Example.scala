// Databricks notebook source
// MAGIC %md
// MAGIC
// MAGIC ## Overview
// MAGIC
// MAGIC This notebook will show you how to create and query a table or DataFrame that you uploaded to DBFS. [DBFS](https://docs.databricks.com/user-guide/dbfs-databricks-file-system.html) is a Databricks File System that allows you to store data for querying inside of Databricks. This notebook assumes that you have a file already inside of DBFS that you would like to read from.
// MAGIC
// MAGIC This notebook is written in **Python** so the default cell type is Python. However, you can use different languages by using the `%LANGUAGE` syntax. Python, Scala, SQL, and R are all supported.

// COMMAND ----------

import org.apache.log4j.{Level, Logger}
Logger.getLogger("org").setLevel(Level.ERROR)
val programStartTime = System.nanoTime()
val logFile = "/FileStore/tables/README.md"
val logData = spark.read.textFile(logFile).cache()
   

val numAs = logData.filter(line => line.contains("Scala")).count()
val numBs = logData.filter(line => line.contains("Spark")).count()

println(s"Lines with Scala: $numAs, Lines with Spark: $numBs")
