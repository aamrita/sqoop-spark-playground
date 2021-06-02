package com.demo

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object ReadData {
  val spark = SparkSession.builder()
    .appName("Read Data")
    .master("local")
    .getOrCreate()

  def main(args: Array[String]): Unit = {

    // reading data from hdfs to dataframes
    val employees_df = spark.read
      .format("csv")
      .schema(empSchema)
      .load("hdfs:///user/maria_dev/employees")
      .createTempView("emp_view")

    val titles_df = spark.read
      .format("csv")
      .schema(titleSchema)
      .load("hdfs:///user/maria_dev/titles")
      .filter("title = 'Senior Engineer'")
      .createTempView("title_view")

    val salaries_df = spark.read
      .format("csv")
      .schema(salSchema)
      .load("hdfs:///user/maria_dev/salaries")
      .createTempView("sal_view")

    //query
    val res = spark.sql("select Employee_Num,Name,Second_Name from (" +
      "select *, ROW_NUMBER() OVER(PARTITION BY Employee_Num ORDER BY sal.salary DESC) as row_num  " +
      "from (select emp.emp_no as Employee_Num,emp.first_name as Name ,emp.last_name as Second_Name, title.title as emp_title " +
      "from title_view title left outer join emp_view emp " +
      "on title.emp_no = emp.emp_no) SM" +
      " left outer join sal_view sal " +
      "on sm.Employee_Num = sal.emp_no " +
      "where sal.salary > 75000 and sal.salary < 100000 ) where row_num=1"
    )

    //writing results to hdfs
    res.write
      .format("com.databricks.spark.csv")
      .option("header", "false")
      .save("hdfs:///user/maria_dev/result")
  }

  val titleSchema = StructType(Array(StructField("emp_no", IntegerType, true), StructField("title", StringType, true), StructField("from_date", StringType, true), StructField("to_date", StringType, true)))
  val empSchema = StructType(Array(StructField("emp_no", IntegerType, true), StructField("birth_date", StringType, true), StructField("first_name", StringType, true), StructField("last_name", StringType, true), StructField("gender", StringType, true), StructField("hire_date", StringType, true)))
  val salSchema = StructType(Array(StructField("emp_no", IntegerType, true), StructField("salary", IntegerType, true), StructField("from_date", StringType, true), StructField("to_date", StringType, true)
  ))
}