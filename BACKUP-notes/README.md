## Dump & Restore of PostgreSQL databases
- Dump command
  - pg_dump -Fc -d **database_name** -U postgres -h localhost -p 30002 > production_**database_name**_**timestamp**.dump
- Restore command
  - pg_restore --clean -d **database_name** -U postgres production_**database_name**_**timestamp**.dump

## Dump & Restore of Mongo databases
- Dump command
  - mongodump --db=**database_name** --out=**target_directory**
- Restore command
  - mongorestore (add --drop if the database already exists) --db=**database_name** **target_directory**/**database_name**
