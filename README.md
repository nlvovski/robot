#Robot Tech Programming Test
Written in Java, no specific libraries used
####  
Uses standard in/out
####  
######Added:
1. Different error reporting
#Testing  
####Most of coverage is in tests. See `src/test/java/com/anz/robot for details`
####To run tests:  
`./gradlew test`  
Test Results will be generates in `build/reports/tests/`
##Additional test data for integration test  
Path: `/src/test/resources/testdata.txt`
The above file contains all 3 examples from the requirement.
###Command to run from the file is:
`cat src/test/resources/testdata.txt | java -cp ./build/libs/robot-1.0-SNAPSHOT.jar com.anz.robot.RobotRunner`
The output should be:  
`Output: 0,1,NORTH`
`Output: 0,0,WEST`
`Output: 3,3,NORTH`
######
#To run the program
From command line run jar file  
##Example  
`java -cp ./build/libs/robot-1.0-SNAPSHOT.jar com.anz.robot.RobotRunner`
