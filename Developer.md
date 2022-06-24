# Building and Testing the Java Client Core Library

## Prerequisite Tech Stack
Before you can build and test the Java Client Core Library, you must install and configure the following tech stacks on your local machine
 - Java version 8+
 - Apache Maven version 3.8.5+ 
 - JUnit 5 Java Testing Framework version version 5.8+
 - IntelliJ Community Edition Version 2022.1.3+ IDE 

### Installation Links
- [Java Version 8+](https://jdk.java.net/18/)
- [Maven build system](https://maven.apache.org/download.cgi)
- [IntelliJ Version](https://www.jetbrains.com/idea/download/#section=windows)
- ***Note: Junit Testing Framework is a public java library that can be imported by adding the dependency to the pom.xml file***

## Getting the Repo
- Login to your github account 

## How to build this project 
- Click on the maven panel on the side of the intelliJ IDE
- In order to generate a .jar file, right click the package tab and click run maven build
- In order to rebuild the project after adding a new library, open the maven panel on the side and click reload project 

## How to run the tests locally
- All the unit/integration tests are in the src/main/test/ directory of this project
- In order to run the tests, right click the test file or test directory of your choice and click run Tests in <Test directory or File> 
- You should be able to see the test results in the terminal after running those tests
- ***If you are using an older version of IntelliJ, there terminal will not show any thrown exceptions***



