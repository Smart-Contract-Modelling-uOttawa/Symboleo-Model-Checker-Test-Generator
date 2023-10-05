#  SymboleoPC Benchmark Tool
The SymboleoPC Benchmark Tool is a Java-based utility designed to facilitate performance benchmarking and the assessment of execution times for the <a href="https://github.com/Smart-Contract-Modelling-uOttawa/Symboleo-Model-Checker">Symboleo Model Checker (SymboleoPC)</a> tool. It empowers users to comprehensively evaluate the efficiency of SymboleoPC by exploring various configurations.

## Key Features

:gear: Customizable Test Scenarios: Tailor test scenarios to your specific needs by configuring parameters such as the number of obligations and powers.

:link: Interdependency Analysis: Analyze and adjust the rate of interdependency among obligations and powers for in-depth performance testing.

:chart_with_upwards_trend: Performance Evaluation: Gain valuable insights into the execution time of SymboleoPC under different configurations, helping you optimize your modeling and verification processes.

Whether you're fine-tuning SymboleoPC for a specific use case or conducting research on model checking performance, the SymboleoPC Benchmark Tool empowers you with the flexibility and precision you need to achieve your goals.

## Installation

### Install Spot

Spot is a tool that randomly generates LTL properties. Follow <a href="https://spot.lrde.epita.fr/install.html"> the Spot Installation Instructions</a> to install Spot on your Linux system.

### Verify nuXmv

Verify the permissions of the `nuXmv` file by running the following command:

```sh
ls -l nuXmv
```

If the file does not have executable permission, you can grant it using the chmod command:
```sh
sudo chmod +x nuXmv
```

### Compilation and Execution on Linux
#### Ensure JavaSDK is Installed
Verify that you have JavaSDK installed on your system:

```sh
java --version
javac --version
```

#### Compile the Program

```sh
javac -Xlint:all *.java
```

#### Run the Program
Execute the program with the desired parameters:

```sh
java Main <maximum number of obligations> <maximum number of powers> <number of LTL properties> <number of CTL properties>
```
  
To run the program in the background:
```sh
nohup java Main <number1> <number2> <number of LTL properties> <number of CTL properties> &
```
  
This command generates a set of scenarios with 0 to 2^number1 obligations and 0 to 2^number2 powers while applying the specified properties.


### Output
You can find the generated outputs in the 'result' folder.

## Citation
If you find our work beneficial for your research, we kindly request you to consider citing
```bibtex
@inproceedings{parvizimosaed2022model,
  title={Model-checking legal contracts with SymboleoPC},
  author={Parvizimosaed, Alireza and Roveri, Marco and Rasti, Aidin and Amyot, Daniel and Logrippo, Luigi and Mylopoulos, John},
  booktitle={Proceedings of the 25th International Conference on Model Driven Engineering Languages and Systems},
  pages={278--288},
  year={2022}
}
```