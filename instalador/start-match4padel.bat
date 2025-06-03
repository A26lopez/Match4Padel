@echo off
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -h localhost -P 3306 -u root -p1234 < init.sql

start "" Release\match4padel-staff.exe
start "" javaw -jar match4padel-api-0.0.1-SNAPSHOT.jar
