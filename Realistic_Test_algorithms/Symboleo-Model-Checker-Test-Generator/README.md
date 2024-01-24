# Symboleo-Model-Checker-Test-Generator
This is an Java tool that generates performance benchmarks and evaluates the execution time of the <a href="https://github.com/Smart-Contract-Modelling-uOttawa/Symboleo-Model-Checker">Symboleo Model Checker (SymboleoPC)</a> tool using various configurations. The test generator tool allows users to configure the number of obligations and powers, and the rate of interdependency among these terms.
##### Install Spot
Spot is a tool that randomly generates LTL properties. Follow <a href="https://spot.lrde.epita.fr/install.html"> Spot Installation Instruction</a> to install the Spot in Linux.

##### Compile and Run on Linux
  Ensure JavaSDK is installed:
  ```sh
  java --version
  javac --version
  ```
  Compile the program:
  ```sh
  javac -Xlint:all *.java
  ```
  Run the program:
  ```sh
  java Main <maximum number of obligations> <maximum number of powers> <number of LTL properties> <number of CTL properties>
  ```
  For running the program in background:
  ```sh
  nohup java Main <number1> <number2> <number of LTL properties> <number of CTL properties> &
  ```
  it generates a set of scenarios with 0 to 2^number1 obligations and 0 to 2^number2 powers with the mentioned properties. 
  
  Find outputs in 'result' folder.
  
  ## To generate reachable states of nuXmv modules:
  
  - change the name of file test.java to test.java1

  - change the name of file Test_CreatOrdOnly.java1 to test.java and follow the previous steps.
 
  ## To verify LTL and CTL properties:
  
  - change the name of file test.java1 to test.java
  - Follow the steps inside the file to select the suitable algorithm
  - follow the above steps.
