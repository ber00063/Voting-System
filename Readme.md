# Team25: tracy255, ber00063, cacer019, abouz009

### Compilation
Users are assumed to be using IntelliJ, and run main.main().
Working directory is assumed to be the Project2 repository.
Modules include Project2, main (subdirectory of src), and test (subdirectory of src).
All java files should be in the same default package using IntelliJ, as no files 
have package declarations.
SDK version is 17.
This project uses Gradle library manager.

### Running Elections
Users can run elections with a single CSV file or multiple of them. 
To run a single election the user just types in the individual file. For example, "CPLElection1.csv".
To run multiple files the user should then add a space from the first inputted election file and continue as so for more. 
For example, "CPLElection1.csv CPLElection2.csv CPLElection3.csv"

### Project Structure (test and source files in new location)
Project structure differs from expected team directory structure. Rather than a src
and testing subdirectory underneath the Project2 directory, src contains main
(equivalent of src in expected directed) and test (equivalent of testing in expected
directory). Each contains a "java" subdirectory where java files and csv files for
testing are stored. Otherwise, the file structure meets expectations.

### Special case: Testing
As instructed csv test files for system testing were moved in the "testing" directory
underneath "Project2". They are necessary for running the tests within CPLProcessingTest, 
IRProcessingTest, and POProcessingText files and must be moved back into those folders if those tests are ran.

### Special case: Input CSV file location
Any csv file used for running the program should be located under the working directory
which is assumed to be Project2. The checkArgs() method can only locate files in the active
directory!

### Special case: Audit file location
The csv file will be placed alongside the current working directory, which should be 
Project2. If using IntelliJ you can see it alongside .gitignore and README.md.

### Special case: CSV File Validation
New CSV files made using MACOS start with a byte order mark in ASCII with characters
239 187 191. The program cannot validate such files
