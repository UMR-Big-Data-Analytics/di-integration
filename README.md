# di-integration
A data integration project that offers the project frame and automatic tests for the homework assignments of the "Data Integration" lecture.

## Requirements
- Java Version >= 11
- Maven Compiler Version >= 3.8.1

## Getting started
1. Clone repo
  ```
  git clone git@github.com:UMR-Big-Data-Analytics/ddm-akka.git
  ```

2. Build project with maven
  ```
  cd ..
  mvn package
  ```

Most automatic JUnit tests should fail at this step. The task is to implement all algorithmic steps marked as "DATA INTEGRATION ASSIGNMENT", such that all tests finally succeed.

## Advanced testing

1. Read the program documentation
  ```
  java -jar target/di-integration-1.0.jar
  ```

2. Run a specific data integration step, for example:
  ```
  java -jar target/di-integration-1.0.jar Levenshtein --string1 "Data Integration Uni Marburg" --string2 "Datenintegration Universität Marburg" --withDamerau true
  ```

## Important comments

1. Please do *NOT* fork this project or post solutions for the assignments on GitHub!

2. The code ducumentation and task specifications may provide very useful hints and help for solving the individual tasks.
