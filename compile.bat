@echo off

REM Create the build directory if it doesn't exist
if not exist build mkdir build
javac -d build Main.java
REM Compile all.java files in the utils directory
javac -d build -cp "modules/*" utils\*.java