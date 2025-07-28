# Graph Patterns and Design

This document explains the new graph patterns implemented in this codebase, including the unified `GraphInterface`, `GraphBuilder`, and `GraphStrategy` patterns with Java 17 features.

## How to Build and Test
- **Use Gradle as the primary way to build and test your code.**
- Use `./gradlew build` (Mac/Linux) or `gradlew.bat build` (Windows) to build the project.
- Use `./gradlew test` (Mac/Linux) or `gradlew.bat test` (Windows) to run the tests.
- The Gradle wrapper scripts are included, so you do not need to install Gradle manually.

## Overview

The original design had separate `MGraph` and `ImGraph` interfaces, which led to type confusion and interface segregation issues. The new design provides:

1. **Unified Interface**: `GraphInterface` combines all operations
2. **Builder Pattern**: `GraphBuilder` for flexible graph construction
3. **Strategy Pattern**: `GraphStrategy` for different usage patterns
4. **Modern Java**: Java 17 features throughout

## 1. GraphInterface - Unified Interface

The `GraphInterface` provides a single, comprehensive interface for all graph operations:

```java
public interface GraphInterface<V extends Vertex, E extends Edge<V>> {
    // Mutable operations
    boolean addVertex(V v);
    boolean addEdge(E e);
    boolean remove(V v);
    boolean removeEdge(E e);  // Renamed from remove(E e) to avoid name clash
    
    // Query operations
    boolean hasVertex(V v);
    boolean hasEdge(E e);
    boolean hasEdge(V v1, V v2);
    E getEdge(V v1, V v2);
    int getEdgeLength(V v1, V v2);
    int getTotalEdgeLength();
    
    // Collection operations
    Set<V> getVertices();
    Set<E> getEdges();
    Set<E> getEdges(V v);
    Map<V, E> getNeighbors(V v);
    
    // Algorithm operations
    List<V> shortestPath(V source, V sink);
    int pathLength(List<V> path);
    Map<V, E> getNeighbors(V v, int range);
    Set<GraphInterface<V, E>> minimumSpanningComponents(int k);
    int diameter();
    V getCenter();
}
```

### Usage Examples with Java 17 Features

```java
// Create graphs with different implementations
var alGraph = new ALGraph<Vertex, Edge<Vertex>>();
var amGraph = new AMGraph<Vertex, Edge<Vertex>>(100);

// Add vertices and edges
var v1 = new Vertex(1, "A");
var v2 = new Vertex(2, "B");
var e1 = new Edge<>(v1, v2, 5);

alGraph.addVertex(v1);
alGraph.addVertex(v2);
alGraph.addEdge(e1);

// Query operations
var hasEdge = alGraph.hasEdge(v1, v2);
var length = alGraph.getEdgeLength(v1, v2);

// Algorithm operations
var path = alGraph.shortestPath(v1, v2);
var diameter = alGraph.diameter();
var center = alGraph.getCenter();

// Using Java 17 pattern matching
if (alGraph instanceof ALGraph<?, ?> al) {
    System.out.println("Using adjacency list implementation");
}
```

## 2. GraphBuilder - Builder Pattern

The `GraphBuilder` pattern provides flexible graph construction with different views:

### Basic Usage with Java 17

```java
// Create a builder
var builder = new GraphBuilder<Vertex, Edge<Vertex>>();

// Build with mutable view
var mutableView = builder
    .addVertices(v1, v2, v3)
    .addEdges(e1, e2)
    .buildMutable();

// Add more elements
mutableView.addVertex(v4);
mutableView.addEdge(e3);

// Build with immutable view
var immutableView = builder
    .addVertices(v1, v2, v3)
    .addEdges(e1, e2)
    .buildImmutable();

// Use query operations only
var vertices = immutableView.getVertices();
var path = immutableView.shortestPath(v1, v3);
```

### Different Representations

```java
// Sparse graph (adjacency list)
var sparseBuilder = new GraphBuilder<Vertex, Edge<Vertex>>();
var sparseGraph = sparseBuilder
    .addVertices(v1, v2, v3)
    .addEdges(e1, e2)
    .build();

// Dense graph (adjacency matrix)
var denseBuilder = new GraphBuilder<Vertex, Edge<Vertex>>(100);
var denseGraph = denseBuilder
    .addVertices(v1, v2, v3)
    .addEdges(e1, e2)
    .build();
```

## 3. GraphStrategy - Strategy Pattern

The `GraphStrategy` pattern provides different views for different use cases:

### Mutable vs Immutable Views

```java
// Create a graph
var graph = new ALGraph<Vertex, Edge<Vertex>>();

// Mutable view for construction
var mutableView = new GraphStrategy.MutableView<>(graph);
mutableView.addVertex(v1);
mutableView.addVertex(v2);
mutableView.addEdge(e1);

// Immutable view for analysis
var immutableView = new GraphStrategy.ImmutableView<>(graph);

// Query operations
var vertices = immutableView.getVertices();
var hasEdge = immutableView.hasEdge(v1, v2);

// Algorithm operations
var path = immutableView.shortestPath(v1, v2);
var diameter = immutableView.diameter();
var center = immutableView.getCenter();
```

### Strategy Selection with Java 17

```java
// Strategy 1: Adjacency list for sparse graphs
var sparseGraph = new ALGraph<Vertex, Edge<Vertex>>();
var sparseMutable = new GraphStrategy.MutableView<>(sparseGraph);
var sparseImmutable = new GraphStrategy.ImmutableView<>(sparseGraph);

// Strategy 2: Adjacency matrix for dense graphs
var denseGraph = new AMGraph<Vertex, Edge<Vertex>>(100);
var denseMutable = new GraphStrategy.MutableView<>(denseGraph);
var denseImmutable = new GraphStrategy.ImmutableView<>(denseGraph);

// Both strategies provide the same interface!
sparseMutable.addVertex(v1);
denseMutable.addVertex(v1);

var sparseHasEdge = sparseImmutable.hasEdge(v1, v2);
var denseHasEdge = denseImmutable.hasEdge(v1, v2);
```

## 4. Java 17 Features in Graph Implementation

### Records for Graph Components

```java
// Define graph statistics as a record
record GraphStats(int vertexCount, int edgeCount, int diameter) {}

// Define path result as a record
record PathResult(List<Vertex> path, int length) {}

// Use in algorithms
var stats = new GraphStats(graph.getVertices().size(), 
                          graph.getEdges().size(), 
                          graph.diameter());
```

### Pattern Matching

```java
// Pattern matching with instanceof
public String describeGraph(GraphInterface<?, ?> graph) {
    return switch (graph) {
        case ALGraph<?, ?> al -> "Adjacency List Graph";
        case AMGraph<?, ?> am -> "Adjacency Matrix Graph";
        case Graph<?, ?> g -> "Composition Graph";
        default -> "Unknown Graph Type";
    };
}

// Pattern matching for algorithm results
public String describeResult(Object result) {
    if (result instanceof PathResult(var path, var length)) {
        return "Path with %d vertices, length %d".formatted(path.size(), length);
    }
    return "Unknown result type";
}
```

### Enhanced Switch Expressions

```java
// Algorithm selection with switch expressions
public String selectAlgorithm(String operation) {
    return switch (operation) {
        case "shortest_path" -> "Dijkstra's Algorithm";
        case "minimum_spanning_tree" -> "Kruskal's Algorithm";
        case "diameter" -> "All-Pairs Shortest Paths";
        case "center" -> "Minimum Eccentricity";
        default -> "Unknown Algorithm";
    };
}
```

### Collection Factory Methods

```java
// Immutable collections
var vertices = Set.of(v1, v2, v3);
var edges = List.of(e1, e2, e3);
var graphMap = Map.of(v1, e1, v2, e2);

// Stream API enhancements
var vertexNames = graph.getVertices().stream()
    .map(Vertex::name)
    .toList(); // Java 16+ method
```

## 5. Implementation Classes

### ALGraph (Adjacency List)

- **Best for**: Sparse graphs, iterating over neighbors
- **Space**: O(V + E)
- **Time**: O(1) for adding vertices/edges, O(degree) for neighbor iteration

```java
var graph = new ALGraph<Vertex, Edge<Vertex>>();
```

### AMGraph (Adjacency Matrix)

- **Best for**: Dense graphs, fast edge lookups
- **Space**: O(V²)
- **Time**: O(1) for edge lookups, O(V) for neighbor iteration

```java
var graph = new AMGraph<Vertex, Edge<Vertex>>(maxVertices);
```

### Graph (Composition)

- **Best for**: High-level usage with composition
- **Implementation**: Delegates to ALGraph or AMGraph

```java
var graph = new Graph<Vertex, Edge<Vertex>>(); // Uses ALGraph
var graph2 = new Graph<Vertex, Edge<Vertex>>(100); // Uses AMGraph
```

## 6. Game Integration Example with Java 17

```java
// Create a game world
var gameWorld = new Graph<Vertex, Edge<Vertex>>();

// Add planets using collection factory methods
var planets = List.of(
    new Vertex(1, "Tatooine"),
    new Vertex(2, "Coruscant"),
    new Vertex(3, "Naboo")
);

planets.forEach(gameWorld::addVertex);

// Add space routes
var routes = List.of(
    new Edge<>(planets.get(0), planets.get(1), 10),
    new Edge<>(planets.get(1), planets.get(2), 8)
);

routes.forEach(gameWorld::addEdge);

// Game logic with pattern matching
var route = gameWorld.shortestPath(planets.get(0), planets.get(2));
var routeLength = gameWorld.pathLength(route);

var nearbyPlanets = gameWorld.getNeighbors(planets.get(0), 2);

// Using records for game state
record GameState(Vertex currentLocation, int fuel, int score) {}
var gameState = new GameState(planets.get(0), 100, 0);
```

## 7. Testing with Java 17

The test suite includes comprehensive tests for all patterns:

```java
// Test basic operations
@Test
void testTask1_AddVertex() {
    assertTrue(alGraph.addVertex(v1));
    assertTrue(alGraph.hasVertex(v1));
}

// Test algorithm operations
@Test
void testTask3_ShortestPath() {
    var path = alGraph.shortestPath(v1, v3);
    assertEquals(3, path.size());
}

// Test builder pattern
@Test
void testGraphBuilder() {
    var mutableView = builder
        .addVertices(v1, v2, v3)
        .addEdges(e1, e2)
        .buildMutable();
    
    assertTrue(mutableView.addVertex(v4));
}

// Test strategy pattern
@Test
void testGraphStrategy() {
    var mutableView = new GraphStrategy.MutableView<>(graph);
    
    mutableView.addVertex(v1);
    assertTrue(mutableView.hasVertex(v1));
}
```

## 8. Running Examples

To see the patterns in action, run:

```bash
# Run the main graph examples
./gradlew runGraphExamples

# Run Java 17 feature examples
./gradlew runJava17Examples
```

This will demonstrate:
- Basic graph usage
- Builder pattern usage
- Strategy pattern usage
- Game integration
- Performance comparison
- Java 17 features in action

## 9. Assignment Tasks

The new design supports all assignment tasks:

- **Task 1**: Basic operations (`addVertex`, `addEdge`, `hasVertex`, `hasEdge`, `getEdgeLength`)
- **Task 2**: Advanced operations (`remove`, `getVertices`, `getEdges`, `getNeighbors`)
- **Task 3**: Algorithm operations (`shortestPath`, `getNeighbors` with range)
- **Task 4**: Advanced algorithms (`diameter`, `getCenter`, `minimumSpanningComponents`)
- **Task 5**: Game integration (all operations work with game components)

All tasks can be implemented using the unified `GraphInterface` with either `ALGraph` or `AMGraph` implementations, enhanced with Java 17 features for better code quality and readability. 