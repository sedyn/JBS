@echo off
mvn clean
pause
mvn package -DskipTests
exit