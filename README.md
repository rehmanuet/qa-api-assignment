# RestAssured | TestNG | CircleCI | ExtentReport
<sup>Live Build Status:</sup><br />[![CircleCI](https://circleci.com/gh/rehmanuet/qa-api-assignment/tree/main.svg?style=svg)](https://circleci.com/gh/rehmanuet/qa-api-assignment/tree/main)

Introduction:
---------------
This Test Automation Framework is created using Java + RestAssured Library + TestNG which can be used to test APIs. Covered all the mandatory & additional scenarios with independent test and parallel execution.


Stack
---------------
RestAssured Library
```
REST Assured is a Java library which removes the need for writing a lot of boilerplate code required to set up an HTTP connection, send a request and receive and parse a response. It also integrates into CICD setup easily, especially when combined with a Java testing framework TestNG
```
Lombok
```
Lombok reduces the boilerplate code for POJO classes and provides the easy integration of Sl4j Logging
```
TestNG
```
TestNG provided the parallel execution to reduce the execution time and pre/post steps required by the tests
```
ExtentReport
```
ExtendReport provided the detailed reports about success/failure of test-cases
```
CircleCI
```
CI execution on every git push
```

Prerequisites:
---------------
*	Java jdk-1.8 or higher
*	Apache Maven 3 or higher
*	Please refer for any help in Maven.
* 	http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
* 	http://www.tutorialspoint.com/maven/maven_environment_setup.htm

Execution
---------------
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

```
git clone https://github.com/rehmanuet/qa-api-assignment.git
mvn clean install
mvn clean test -Dsurefire.suiteXmlFiles=TestNG.xml -DbaseURI=https://jsonplaceholder.typicode.com
```

Coverage
---------------
This framework covers the both positive and negative testcases.

#### Test Cases Breakdown
|    <sub>Endpoint</sub>  |    <sub>Test cases</sub> |
| :-:  | :-: |
|    <b> <sub>/users</sub> </b>   | <sub>5</sub>  |
|    <b> <sub>/posts</sub> </b>   | <sub>3</sub>  |
|    <b> <sub>/comments</sub> </b>   | <sub>5</sub>  
<b><sub>Sub Total Test cases</sub></b>|    <b><sub>13</b></sub> |
