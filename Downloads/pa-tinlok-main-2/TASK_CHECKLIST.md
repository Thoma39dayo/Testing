# Programming Assignment Task Checklist

## How to Build and Test
- **Use Gradle as the primary way to build and test your code.**
- Use `./gradlew build` (Mac/Linux) or `gradlew.bat build` (Windows) to build the project.
- Use `./gradlew test` (Mac/Linux) or `gradlew.bat test` (Windows) to run the tests.
- The Gradle wrapper scripts are included, so you do not need to install Gradle manually.

## Task 1: Basic GraphInterface Implementation ✅
**Goal**: Implement core graph operations using both adjacency list and matrix representations.

### ALGraph Implementation
- [ ] `addVertex(V v)` - Add a vertex to the graph
- [ ] `addEdge(E e)` - Add an edge to the graph  
- [ ] `hasVertex(V v)` - Check if a vertex exists in the graph
- [ ] `hasEdge(E e)` - Check if an edge exists in the graph
- [ ] `hasEdge(V v1, V v2)` - Check if there's an edge between two vertices
- [ ] `getEdge(V v1, V v2)` - Get the edge between two vertices
- [ ] `getEdgeLength(V v1, V v2)` - Get the length of an edge between vertices

### AMGraph Implementation
- [ ] `addVertex(V v)` - Add a vertex to the graph
- [ ] `addEdge(E e)` - Add an edge to the graph  
- [ ] `hasVertex(V v)` - Check if a vertex exists in the graph
- [ ] `hasEdge(E e)` - Check if an edge exists in the graph
- [ ] `hasEdge(V v1, V v2)` - Check if there's an edge between two vertices
- [ ] `getEdge(V v1, V v2)` - Get the edge between two vertices
- [ ] `getEdgeLength(V v1, V v2)` - Get the length of an edge between vertices

### Java 17 Features to Use
- [ ] Use `var` for local variable type inference
- [ ] Use `List.of()` and `Set.of()` for immutable collections
- [ ] Use pattern matching with `instanceof`
- [ ] Use enhanced switch expressions where appropriate

### Tests for Task 1
- [ ] Test basic vertex and edge operations
- [ ] Test edge cases (null inputs, duplicate vertices/edges)
- [ ] Test bidirectional edge handling
- [ ] Test edge length calculations

---

## Task 2: Advanced GraphInterface Operations ✅
**Goal**: Add more complex graph operations to your implementations.

### ALGraph & AMGraph Implementation
- [ ] `getTotalEdgeLength()` - Sum of all edge lengths in the graph
- [ ] `removeEdge(E e)` - Remove an edge from the graph
- [ ] `remove(V v)` - Remove a vertex and all its incident edges
- [ ] `getVertices()` - Get all vertices in the graph
- [ ] `getEdges(V v)` - Get all edges incident on a vertex
- [ ] `getEdges()` - Get all edges in the graph
- [ ] `getNeighbors(V v)` - Get all neighbors of a vertex

### Java 17 Features to Use
- [ ] Use records for return types where appropriate
- [ ] Use stream API enhancements (`.toList()`)
- [ ] Use text blocks for complex string formatting

### Tests for Task 2
- [ ] Test remove operations (edge and vertex removal)
- [ ] Test collection operations (getVertices, getEdges)
- [ ] Test getNeighbors returns correct Map<V, E>
- [ ] Test edge length sum calculations
- [ ] Test edge cases (empty graphs, isolated vertices)

---

## Task 3: Basic Graph Algorithms ✅
**Goal**: Implement fundamental graph algorithms using composition.

### Graph Implementation (using composition)
- [ ] `getEdge(V v1, V v2)` - Find the edge connecting two vertices
- [ ] `shortestPath(V source, V sink)` - Compute shortest path between vertices
- [ ] `pathLength(List<V> path)` - Calculate total length of a path
- [ ] `getNeighbors(V v, int range)` - Get neighbors within a given range

### Implementation Strategy
- [ ] Use composition with your ALGraph or AMGraph
- [ ] Implement Dijkstra's algorithm for shortestPath
- [ ] Use breadth-first search for getNeighbors with range
- [ ] Handle disconnected vertices properly

### Java 17 Features to Use
- [ ] Use sealed classes for algorithm results
- [ ] Use pattern matching for result handling
- [ ] Use enhanced switch expressions for algorithm selection

### Tests for Task 3
- [ ] Test getEdge with existing and non-existing edges
- [ ] Test shortestPath with connected and disconnected vertices
- [ ] Test pathLength with various path lengths
- [ ] Test getNeighbors with different range values
- [ ] Test edge cases (same vertex, empty path)

---

## Task 4: Advanced Graph Algorithms ✅
**Goal**: Implement complex graph algorithms with performance considerations.

### Graph Implementation
- [ ] `minimumSpanningComponents(int k)` - Partition graph into k connected components
- [ ] `diameter()` - Compute the diameter of the graph
- [ ] `getCenter()` - Find the center of the graph

### Algorithm Implementation
- [ ] **Implement in `GraphAlgorithms` utility class** to avoid code duplication
- [ ] Implement Kruskal's or Prim's algorithm for MST
- [ ] Implement diameter calculation (all-pairs shortest paths)
- [ ] Implement center calculation (minimum eccentricity)
- [ ] Optimize for performance with large datasets

### Java 17 Features to Use
- [ ] Use records for algorithm results (GraphStats, PathResult)
- [ ] Use stream API for efficient data processing
- [ ] Use collection factories for immutable results

### Tests for Task 4
- [ ] Test minimumSpanningComponents with different k values
- [ ] Test diameter calculation on various graph structures
- [ ] Test center calculation
- [ ] Performance test with Marvel Comics Dataset

---

## Task 5: The Kamino Game ✅
**Goal**: Implement game logic for the spaceship navigation game.

### MillenniumFalcon Implementation
- [ ] `hunt(HunterStage state)` - Navigate to find Kamino
- [ ] `gather(GathererStage state)` - Collect spice and return to Earth

### Game Strategy
- [ ] Implement signal-based navigation for hunt phase
- [ ] Implement fuel-efficient spice collection for gather phase
- [ ] Use graph algorithms from Tasks 3-4 for navigation
- [ ] Optimize for high game scores

### Java 17 Features to Use
- [ ] Use records for game state representation
- [ ] Use pattern matching for game event handling
- [ ] Use enhanced switch expressions for game logic

### Tests for Task 5
- [ ] Test hunt phase with different starting positions
- [ ] Test gather phase with fuel constraints
- [ ] Test game integration with your graph implementation
- [ ] Benchmark performance and scores

---

## Design Patterns Bonus ✅
**Goal**: Understand and use the provided design patterns.

### GraphBuilder Pattern
- [ ] Study the GraphBuilder implementation
- [ ] Understand mutable vs immutable views
- [ ] Use builder pattern in your own code where appropriate

### GraphStrategy Pattern
- [ ] Study the GraphStrategy implementation
- [ ] Understand strategy pattern for different usage scenarios
- [ ] Use strategy pattern for algorithm selection

### Java 17 Features in Patterns
- [ ] Use records for pattern results
- [ ] Use sealed classes for pattern hierarchies
- [ ] Use pattern matching with patterns

---

## Final Checklist ✅
- [ ] All GraphInterface methods implemented and tested
- [ ] Code compiles without errors
- [ ] All tests pass
- [ ] Performance acceptable for large datasets
- [ ] Game achieves good scores
- [ ] Code is well-documented and commented
- [ ] Java 17 features used appropriately
- [ ] No memory leaks or performance issues

---

## Tips for Success
1. **Start with Task 1**: Get basic operations working first
2. **Use Java 17 features**: Leverage modern Java for cleaner code
3. **Test incrementally**: Add tests for each method you implement
4. **Use composition**: Leverage your ALGraph/AMGraph in Graph implementation
5. **Consider performance**: Especially important for Task 4
6. **Test edge cases**: Empty graphs, disconnected components, null inputs
7. **Document your algorithms**: Comment complex implementations
8. **Benchmark regularly**: Test with larger datasets as you progress
9. **Study the examples**: Use `Java17Examples.java` for modern patterns
10. **Use the design patterns**: Understand GraphBuilder and GraphStrategy

Good luck with your implementation! 🚀 