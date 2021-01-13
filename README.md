# Symboleo-Model-Checker-Test-Generator
This is an Eclipse project that generates performance benchmarks and evaluates the execution time of the SymboleoPC tool using various configurations. The test generator tool allows users to configure the number of obligations and powers, and the rate of interdependency among these terms.
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
  java Main
  ```
  For running the program in background:
  ```sh
  nohub java Main &
  ```
  Find outputs in 'result' folder.
