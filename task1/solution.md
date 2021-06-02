### Importing tables one-by-one:
```
sqoop import --connect jdbc:mysql://localhost/employees --username root -P --table salaries --target-dir /user/maria_dev/employee_tables/salaries -m 8
```

### Importing all tables at once:
```
sqoop import-all-tables -Dorg.apache.sqoop.splitter.allow_text_splitter=true --connect jdbc:mysql://localhost/employees --username root --P --exclude-tables salaries,titles,
dept_emp_latest_date,current_dept_emp --warehouse-dir /user/maria_dev/employee_tables --num-mappers 8
```

### Incremental Import sqoop job:
```
sqoop job --create my_tab_import_daily -- import --connect jdbc:mysql://localhost/employees --username root --P --table
 my_tab --warehouse-dir /user/maria_dev/employee_tables --incremental append --check-column id --last-value 0 --split-by id
 ```
