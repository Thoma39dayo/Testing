# Task 3 Performance Analysis

## Overview
This document analyzes the performance characteristics of Task 3 graph algorithms: `shortestPath`, `pathLength`, and `getNeighbors` with range parameter. The analysis compares ALGraph (Adjacency List) and AMGraph (Adjacency Matrix) implementations.

## Algorithm Complexity Analysis

### shortestPath (Dijkstra's Algorithm)
**Theoretical Complexity:**
- **ALGraph:** O((V + E) log V) - Using priority queue with adjacency list traversal
- **AMGraph:** O((V + E) log V) - Using priority queue with matrix row scanning

**Key Performance Factors:**
- Priority queue operations: O(log V) per vertex
- Neighbor exploration: O(deg(v)) for ALGraph, O(V) for AMGraph per vertex
- Overall: Both implementations have similar asymptotic complexity

**Empirical Observations:**
- ALGraph shows slightly better performance for sparse graphs
- AMGraph performs better for dense graphs due to cache locality
- Performance difference becomes more pronounced with larger graphs

### pathLength
**Theoretical Complexity:**
- **ALGraph:** O(P) where P is path length
- **AMGraph:** O(P) where P is path length

**Key Performance Factors:**
- Simple path traversal with edge lookups
- ALGraph: O(deg(v)) per edge lookup
- AMGraph: O(1) per edge lookup (matrix access)

**Empirical Observations:**
- Both implementations show very similar performance
- AMGraph has slight advantage due to O(1) edge lookups
- Performance scales linearly with path length

### getNeighbors (with range)
**Theoretical Complexity:**
- **ALGraph:** O(V + E) - BFS with adjacency list traversal
- **AMGraph:** O(V²) - BFS with matrix row scanning

**Key Performance Factors:**
- BFS traversal with distance tracking
- ALGraph: Efficient neighbor exploration O(deg(v))
- AMGraph: Matrix row scanning O(V) per vertex

**Empirical Observations:**
- ALGraph significantly outperforms AMGraph
- Performance gap increases with graph size
- Range parameter has minimal impact on relative performance

## Performance Charts Generated

### 1. task3_shortestPath_performance.png
**Chart Type:** Line chart showing performance vs graph size
**Key Insights:**
- Both implementations show similar growth patterns
- ALGraph performs better for smaller graphs (< 200 vertices)
- AMGraph catches up for larger graphs due to cache efficiency
- Performance scales roughly as O(V log V) as expected

### 2. task3_pathLength_performance.png
**Chart Type:** Line chart showing performance vs graph size
**Key Insights:**
- Very similar performance between implementations
- Linear scaling with path length
- AMGraph shows slight advantage due to O(1) edge lookups
- Minimal performance difference across graph sizes

### 3. task3_getNeighborsRange_performance.png
**Chart Type:** Line chart showing performance vs graph size
**Key Insights:**
- ALGraph significantly outperforms AMGraph
- Performance gap widens with graph size
- ALGraph shows O(V + E) scaling
- AMGraph shows O(V²) scaling as expected

### 4. task3_performance_summary.png
**Chart Type:** Bar chart comparing all methods at 500 vertices
**Key Insights:**
- `shortestPath` is the most computationally expensive operation
- `pathLength` is the fastest operation
- `getNeighbors(range)` shows the largest performance difference between implementations
- ALGraph is generally preferred for algorithm operations

## Detailed Performance Analysis

### Shortest Path Algorithm Performance

**ALGraph Implementation:**
- **Strengths:**
  - Efficient neighbor exploration O(deg(v))
  - Better memory locality for sparse graphs
  - Lower memory overhead
- **Weaknesses:**
  - Cache misses for large graphs
  - Overhead of adjacency list traversal

**AMGraph Implementation:**
- **Strengths:**
  - Better cache locality for dense graphs
  - Predictable memory access patterns
  - O(1) edge weight lookups
- **Weaknesses:**
  - O(V) neighbor exploration per vertex
  - Higher memory usage O(V²)
  - Poor performance for sparse graphs

### Path Length Performance

**Both Implementations:**
- **Similar Performance:** Both show nearly identical performance
- **Linear Scaling:** Performance scales linearly with path length
- **Minimal Overhead:** Very fast operation regardless of implementation

**Key Factors:**
- Simple path traversal with minimal computation
- Edge lookup performance difference is negligible
- Memory access patterns are similar

### Range-Based Neighbor Search Performance

**ALGraph Implementation:**
- **Optimal Performance:** O(V + E) complexity
- **Efficient BFS:** Direct neighbor access via adjacency lists
- **Scalable:** Performance scales well with graph size

**AMGraph Implementation:**
- **Suboptimal Performance:** O(V²) complexity
- **Matrix Scanning:** Must scan entire matrix row for each vertex
- **Poor Scaling:** Performance degrades rapidly with graph size

## Recommendations

### When to Use ALGraph for Task 3 Algorithms:
- **Sparse graphs** (E << V²)
- **Frequent range-based neighbor queries**
- **Memory-constrained environments**
- **Graphs with high vertex degrees**

### When to Use AMGraph for Task 3 Algorithms:
- **Dense graphs** (E ≈ V²)
- **Frequent shortest path queries on dense graphs**
- **Cache-optimized environments**
- **Graphs with low vertex degrees**

### Hybrid Approach:
Consider using both implementations for different operations:
- Use ALGraph for `getNeighbors(range)` operations
- Use AMGraph for `shortestPath` on dense graphs
- Use either for `pathLength` (minimal difference)

## Test Methodology

### Benchmark Configuration:
- **Graph Sizes:** 10, 25, 50, 100, 200, 500 vertices
- **Warmup Iterations:** 500 per method
- **Measurement Iterations:** 2,000 per method
- **Graph Structure:** Connected graphs with 25% extra edges
- **Edge Weights:** Random weights 1-20
- **Range Values:** 1, 5, 10, 20, 50 for range-based tests

### Test Environment:
- **Java Version:** 17
- **JVM:** HotSpot 64-Bit Server VM
- **Platform:** macOS ARM64
- **Memory:** 16GB RAM
- **CPU:** Apple M1 Pro

## Conclusion

Task 3 algorithms show significant performance differences between ALGraph and AMGraph implementations:

1. **shortestPath:** Both implementations perform similarly, with slight advantages for ALGraph on sparse graphs
2. **pathLength:** Minimal performance difference between implementations
3. **getNeighbors(range):** ALGraph significantly outperforms AMGraph, especially on larger graphs

The choice of implementation should be based on:
- Graph density (sparse vs dense)
- Primary use case (which algorithms are most important)
- Memory constraints
- Performance requirements

For most practical applications, ALGraph is recommended for Task 3 algorithms due to its superior performance on range-based operations and better overall scalability. 