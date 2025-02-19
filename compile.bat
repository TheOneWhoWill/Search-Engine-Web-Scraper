@echo off

REM Create the build directory if it doesn't exist
rmdir /s /q build
mkdir build
REM Compile all.java files in the utils directory
javac -d build -cp "modules/*" utils\*.java utils\crawler\*.java utils\embedding\*.java Main.java
REM Run the Main class.  Adjust the classpath and Main class name if necessary.
java -cp "build;modules/*" Main