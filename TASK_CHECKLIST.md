# Programming Assignment Task Checklist

## How to Build and Test
- **Use Gradle as the primary way to build and test your code.**
- Use `./gradlew build` (Mac/Linux) or `gradlew.bat build` (Windows) to build the project.
- Use `./gradlew test` (Mac/Linux) or `gradlew.bat test` (Windows) to run the tests.
- The Gradle wrapper scripts are included, so you do not need to install Gradle manually.

## Task 1: Basic GraphInterface Implementation ✅
**Goal**: Implement core graph operations using both adjacency list and matrix representations.

### ALGraph Implementation
- [x] `addVertex(V v)` - Add a vertex to the graph
- [x] `addEdge(E e)` - Add an edge to the graph  
- [x] `hasVertex(V v)` - Check if a vertex exists in the graph
- [x] `hasEdge(E e)` - Check if an edge exists in the graph
- [x] `hasEdge(V v1, V v2)` - Check if there's an edge between two vertices
- [x] `getEdge(V v1, V v2)` - Get the edge between two vertices
- [x] `getEdgeLength(V v1, V v2)` - Get the length of an edge between vertices

### AMGraph Implementation
- [x] `addVertex(V v)` - Add a vertex to the graph
- [x] `addEdge(E e)` - Add an edge to the graph  
- [x] `hasVertex(V v)` - Check if a vertex exists in the graph
- [x] `hasEdge(E e)` - Check if an edge exists in the graph
- [x] `hasEdge(V v1, V v2)` - Check if there's an edge between two vertices
- [x] `getEdge(V v1, V v2)` - Get the edge between two vertices
- [x] `getEdgeLength(V v1, V v2)` - Get the length of an edge between vertices

### Java 17 Features to Use
- [x] Use `var` for local variable type inference
- [x] Use `List.of()` and `Set.of()` for immutable collections
- [x] Use pattern matching with `instanceof`
- [x] Use enhanced switch expressions where appropriate

### Tests for Task 1
- [x] Test basic vertex and edge operations
- [x] Test edge cases (null inputs, duplicate vertices/edges)
- [x] Test bidirectional edge handling
- [x] Test edge length calculations

---

## Task 2: Advanced GraphInterface Operations ✅
**Goal**: Add more complex graph operations to your implementations.

### ALGraph & AMGraph Implementation
- [x] `getTotalEdgeLength()` - Sum of all edge lengths in the graph
- [x] `removeEdge(E e)` - Remove an edge from the graph
- [x] `remove(V v)` - Remove a vertex and all its incident edges
- [x] `getVertices()` - Get all vertices in the graph
- [x] `getEdges(V v)` - Get all edges incident on a vertex
- [x] `getEdges()` - Get all edges in the graph
- [x] `getNeighbors(V v)` - Get all neighbors of a vertex

### Java 17 Features to Use
- [x] Use records for return types where appropriate
- [x] Use stream API enhancements (`.toList()`)
- [x] Use text blocks for complex string formatting

### Tests for Task 2
- [x] Test remove operations (edge and vertex removal)
- [x] Test collection operations (getVertices, getEdges)
- [x] Test getNeighbors returns correct Map<V, E>
- [x] Test edge length sum calculations
- [x] Test edge cases (empty graphs, isolated vertices)

---

## Task 3: Basic Graph Algorithms ✅
**Goal**: Implement fundamental graph algorithms using composition.

### Graph Implementation (using composition)
- [x] `getEdge(V v1, V v2)` - Find the edge connecting two vertices
- [x] `shortestPath(V source, V sink)` - Compute shortest path between vertices
- [x] `pathLength(List<V> path)` - Calculate total length of a path
- [x] `getNeighbors(V v, int range)` - Get neighbors within a given range

### Implementation Strategy
- [x] Use composition with your ALGraph or AMGraph
- [x] Implement Dijkstra's algorithm for shortestPath
- [x] Use breadth-first search for getNeighbors with range
- [x] Handle disconnected vertices properly

### Java 17 Features to Use
- [x] Use sealed classes for algorithm results
- [x] Use pattern matching for result handling
- [x] Use enhanced switch expressions for algorithm selection

### Tests for Task 3
- [x] Test getEdge with existing and non-existing edges
- [x] Test shortestPath with connected and disconnected vertices
- [x] Test pathLength with various path lengths
- [x] Test getNeighbors with different range values
- [x] Test edge cases (same vertex, empty path)

---

## Task 4: Advanced Graph Algorithms ✅
**Goal**: Implement complex graph algorithms with performance considerations.

### Graph Implementation
- [x] `minimumSpanningComponents(int k)` - Partition graph into k connected components
- [x] `diameter()` - Compute the diameter of the graph
- [x] `getCenter()` - Find the center of the graph

### Algorithm Implementation
- [x] **Implement in `GraphAlgorithms` utility class** to avoid code duplication
- [x] Implement Kruskal's or Prim's algorithm for MST
- [x] Implement diameter calculation (all-pairs shortest paths)
- [x] Implement center calculation (minimum eccentricity)
- [x] Optimize for performance with large datasets

### Java 17 Features to Use
- [x] Use records for algorithm results (GraphStats, PathResult)
- [x] Use stream API for efficient data processing
- [x] Use collection factories for immutable results

### Tests for Task 4
- [x] Test minimumSpanningComponents with different k values
- [x] Test diameter calculation on various graph structures
- [x] Test center calculation
- [x] Performance test with Marvel Comics Dataset

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
- [x] All GraphInterface methods implemented and tested
- [x] Code compiles without errors
- [x] All tests pass
- [x] Performance acceptable for large datasets
- [ ] Game achieves good scores
- [x] Code is well-documented and commented
- [x] Java 17 features used appropriately
- [x] No memory leaks or performance issues

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