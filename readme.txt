# Traveling Salesman SAT Solver

## Introduction
This project offers a unique solution to the Traveling Salesman Problem (TSP) by converting it into a SAT (Boolean Satisfiability Problem) and solving it using the SAT4J library. It aims to demonstrate the applicability of SAT solvers to classic combinatorial optimization problems, providing an efficient and scalable approach to solving TSP instances.

## Features
- **SAT Solver Integration**: Utilizes the SAT4J library to handle SAT instances generated from TSP problems.
- **Example Instances**: Includes various examples to demonstrate solving TSP problems under different conditions (flights, SAT, and UNSAT examples).
- **Extensible Framework**: The provided framework allows for easy integration of additional SAT solvers or TSP problem sets.

## Getting Started

### Prerequisites
- Java JDK 8 or higher
- An IDE that supports Java (e.g., IntelliJ IDEA, Eclipse)

### Installation
1. Clone this repository to your local machine.
2. Open the project in your IDE.
3. Add `org.sat4j.core.jar` to your project's classpath.

### Usage
- **Running Examples**: The project includes several example classes (`ExamplesFlights.java`, `ExamplesSAT.java`, `ExamplesUNSAT.java`) that you can run directly within your IDE to see the solver in action.
- **Creating Your Own TSP Instances**: Modify `Assignment2.java` to include your custom TSP instances and utilize the `SATSolver.java` class to solve them.

## Project Structure
- `Assignment2.java`: Main class for defining TSP problems.
- `ExamplesFlights.java`: Demonstrates solving TSP instances with flight routes.
- `ExamplesSAT.java`: Contains SAT examples for the solver.
- `ExamplesUNSAT.java`: Contains UNSAT examples to demonstrate the solver's handling of unsolvable instances.
- `SATSolver.java`: Interface to the SAT4J solver.
- `Tests.java`: Basic tests for validating the solver's functionality.
- `org.sat4j.core.jar`: The SAT4J library jar file.
- `.idea/`, `out/`: IDE-specific directories and compiled output (should be ignored in version control).

## License
This project is open-sourced under the MIT License. See the LICENSE file for more details.