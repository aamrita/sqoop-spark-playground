### Sqoop export:

```
sqoop export --connect jdbc:mysql://localhost/employees \
--username root -P --table results \
--export-dir /user/maria_dev/result -m 8
```
note: Ensure that the table is created in MySQL prior to running the export.
