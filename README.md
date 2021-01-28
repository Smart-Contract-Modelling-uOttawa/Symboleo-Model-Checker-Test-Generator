# Symboleo-Model-Checker-Test-Generator
This is an Java tool that generates performance benchmarks and evaluates the execution time of the SymboleoPC tool using various configurations. The test generator tool allows users to configure the number of obligations and powers, and the rate of interdependency among these terms.
Install Spot
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
  java Main <maximum number of obligations> <maximum number of powers> <number of LTL properties> 
  ```
  For running the program in background:
  ```sh
  nohup java Main <maximum number of obligations> <maximum number of powers> <number of LTL properties> &
  ```
  Find outputs in 'result' folder.
