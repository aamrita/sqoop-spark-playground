# sqoop-spark-playground

A demo project that import data from MySQL tables using Sqoop, process it using Spark and export the processed data back to RDBMS tables.

### Task 1 : Import data using Sqoop (Sqoop Import):
- Import employee database to mysql - https://github.com/datacharmer/test_db
- Import all the tables from employee database into HDFS.
- Tables should be accessible via hive

### Task 2 : Transform data
- Write Scala script to find the employees whose title is 'Senior Engineer' and salary is in range '$75,000 - $100,000'.
- Save the results in HDFS.

### Task 3 : Export data using Sqoop (Sqoop export):
- Export the result back to a MySQL table.
