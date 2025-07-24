# VSP 2025: Implementing Graphs for Fun and Learning

This repository contains the skeleton code for implementing a Graph Abstract Data Type (ADT) with two different representations: Adjacency List and Adjacency Matrix.

## 🚀 **Use Java 17**

We recommend that everyone uses **Java 17** (LTS) with modern Java features such as:

- **Pattern Matching** for cleaner type checking
- **Enhanced Switch Expressions** for more readable code
- **Records** for immutable data classes
- **Text Blocks** for multi-line strings
- **Local Variable Type Inference** (`var`) for cleaner code
- **Collection Factory Methods** for immutable collections
- **Stream API Enhancements** for functional programming

See `src/main/java/vsp25/pa/graph/Java17Examples.java` for comprehensive examples of these features in graph implementations.

> We recommend using these new language features but if you are new to Java then use simpler approaches that you can understand.

## 📋 Assignment Overview

The assignment is divided into 5 tasks, each building upon the previous. 

For all the algorithms in Task 1 through Task 4, you should derive the runtime complexity and you should also measure the runtime on graphs of different sizes and compare with your runtime complexity estimate. Include these results in a Markdown file named `PERFORMANCE_RESULTS.md` where you have your analysis for each method and a performance plot.

### Task 1: Basic Graph Operations
Implement core graph operations in both `ALGraph` and `AMGraph`:
- `addVertex(V v)`
- `addEdge(E e)`
- `hasVertex(V v)`
- `hasEdge(E e)`
- `hasEdge(V v1, V v2)`
- `getEdge(V v1, V v2)`
- `getEdgeLength(V v1, V v2)`

### Task 2: Advanced Graph Operations
Extend your implementations with:
- `remove(V v)` and `removeEdge(E e)`
- `getTotalEdgeLength()`
- Collection operations: `getVertices()`, `getEdges()`, `getEdges(V v)`, `getNeighbors(V v)`

### Task 3: Basic Algorithms
Implement fundamental graph algorithms:
- `shortestPath(V source, V sink)` - Dijkstra's algorithm
- `pathLength(List<V> path)`
- `getNeighbors(V v, int range)` - BFS with distance tracking

### Task 4: Advanced Algorithms
Implement complex graph algorithms:
- `minimumSpanningComponents(int k)` - Kruskal's or Prim's algorithm
- `diameter()` - All-pairs shortest paths
- `getCenter()` - Minimum eccentricity vertex

**Note**: The Task 4 algorithms are implemented in the `GraphAlgorithms` utility class to avoid code duplication between `ALGraph` and `AMGraph`. Both implementations delegate to this shared utility class.

### Task 5: Game Integration

Using your implementation of `Graph`, you can complete the `Kamino` game.

The game scenario is as follows. *In a galaxy far far away...* 

The Red October is a specialized spice mining planet rover that has been smuggled away to the planet of Kamino. Han Solo and Chewbacca have been tasked with retrieving the Red October. The catch is that the planet Kamino has been erased from all known records so Han and Chewie have to fly the Millennium Falcon from planet to planet, following the tracking signal emitted by the Red October. The further a planet is from Kamino the weaker is the signal from lost rover. At each planet, Han and Chewie obtain signal strengths from all the neighbouring planets and have to decide how to retrieve the rover without wasting too much fuel. The first stage of the mission is the hunt for Red October.

After locating Kamino and securing the Red October, the Millennium Falcon crew have been instructed to retrieve as much of the valuable spice *melange* (believe to enable super-powers) as they can on the trip back to Earth. (Who knows why Earth.) Their goal on the return leg is to collect as much of the spice as possible with the limited fuel reserves they have available. The second stage of the mission is the spice gathering stage.

Your goal is to embed the Millennium Falcon with specialized hunt-and-gather intelligence so that the spaceship can find Kamino and return to Earth with as much spice as possible. Finding Kamino and returning to Earth are essential; the more spice one gathers the better.

- Read the provided skeleton code for the Kamino game because it illustrates some import design patterns such as the Model-View-Controller pattern that is commonly used with GUIs.
- See how the `Planet` type has been implemented. `Planet` is a subtype of a graph `Vertex` but also implements the `GameElement` interface.

Complete the Kamino Game implementation in `MillenniumFalcon.java`:
- `hunt()` method for the hunter phase
- `gather()` method for the gatherer phase

## 🏗️ Architecture

### Unified Interface Design
The project uses a unified `GraphInterface` that combines all graph operations.

### Design Patterns
- **Builder Pattern**: `GraphBuilder` for flexible graph construction
- **Strategy Pattern**: `GraphStrategy` for different usage patterns
- **Composition**: `Graph` class delegates to `ALGraph` or `AMGraph`

### Key Classes
- `GraphInterface<V, E>` - Unified graph interface
- `ALGraph<V, E>` - Adjacency List implementation (for students to complete)
- `AMGraph<V, E>` - Adjacency Matrix implementation (for students to complete)
- `Graph<V, E>` - Composition-based graph using either implementation
- `GraphBuilder<V, E>` - Builder pattern for graph construction
- `GraphStrategy<V, E>` - Strategy pattern for different usage patterns

## 🛠️ Getting Started

### Prerequisites
- **Java 17** or higher
- **Gradle 8.5** (included via wrapper)

### Building and Testing the Project (Recommended)
**Use Gradle as the primary way to build and test your code.** The project includes the Gradle wrapper scripts (`./gradlew` for Mac/Linux, `gradlew.bat` for Windows), so you do not need to install Gradle manually.

To build the project:
```bash
./gradlew build
```

To run the tests:
```bash
./gradlew test
```

### Running Examples
```bash
# Run the main graph examples
./gradlew runGraphExamples

# Run Java 17 feature examples
./gradlew runJava17Examples
```

## 📚 Learning Resources

### Java 17 Features for Graph Implementation
See `src/main/java/vsp25/pa/graph/Java17Examples.java` for examples of:
- Pattern matching with `instanceof`
- Enhanced switch expressions
- Records for immutable data
- Text blocks for multi-line strings
- Local variable type inference (`var`)
- Collection factory methods
- Stream API enhancements

### Implementation Guidance
Each implementation class (`ALGraph.java`, `AMGraph.java`) contains:
- Detailed TODO comments for each method
- Implementation tips and common pitfalls
- Suggested data structures
- Algorithm guidance

### Testing
The test suite (`GraphTest.java`) provides:
- Task-by-task test organization
- Comprehensive test cases
- Performance testing examples
- Builder and Strategy pattern tests

## 🎯 Success Tips

1. **Start with Task 1**: Implement basic operations first
2. **Use Java 17 features**: Leverage modern Java for cleaner code
3. **Test incrementally**: Run tests after each method implementation
4. **Consider performance**: Choose appropriate data structures
5. **Use the examples**: Study `Java17Examples.java` for modern patterns
6. **Use Gradle for all builds and tests**: This ensures consistency and avoids environment issues.

## 📖 Documentation

- `GRAPH_PATTERNS_README.md` - Detailed explanation of design patterns
- `TASK_CHECKLIST.md` - Comprehensive task checklist

Good luck with your implementation! 🎉