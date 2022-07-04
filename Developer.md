# Building and Testing the Java Client Core Library

## Build/Dev Environment Prerequisites 
 - [Java version 8+ e.g Temurin distribution](https://projects.eclipse.org/projects/adoptium.temurin/downloads)
 - [Apache Maven version 3.8.5+](https://maven.apache.org/download.cgi)
 - [Java IDE e.g. IntelliJ Community Edition Version 2022.1.3+](https://www.jetbrains.com/idea/download/#section=windows)
 - Note: Developers can use any IDE or JDKs they want


## How to build this project
 - Clone this repository, open the project an IDE that supports java
 - Click on the maven panel, right click the package tab, and click run maven build
  - This step generates the .jar file in the target directory
  
Note: If you add or modify a library, open the maven panel on the side and click reload project 

## How to run the tests locally

If you are using IntelliJ, all the unit/integration tests are in the src/main/test/ directory of this project
 
 In order to run the tests, right click the test file or test directory of your choice and click run Tests in <Test directory or File> 
 
 You should be able to see the test results in the terminal after running those tests
 
Note: If you are using an older version of IntelliJ, there terminal will not show any thrown exceptions



