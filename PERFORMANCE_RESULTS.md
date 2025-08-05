# PERFORMANCE_RESULTS.md

## Table of Contents
- [Introduction](#introduction)
- [Method Complexity Analysis](#method-complexity-analysis)
  - [Task 1: Basic Graph Operations](#task-1-basic-graph-operations)
    - [addVertex](#addvertex)
    - [addEdge](#addedge)
    - [hasVertex](#hasvertex)
    - [hasEdge (by edge)](#hasedge-by-edge)
    - [hasEdge (by vertices)](#hasedge-by-vertices)
    - [getEdge](#getedge)
    - [getEdgeLength](#getedgelength)
  - [Task 2: Advanced Graph Operations](#task-2-advanced-graph-operations)
    - [remove (vertex)](#remove-vertex)
    - [removeEdge](#removeedge)
    - [getTotalEdgeLength](#gettotaledgelength)
    - [getVertices](#getvertices)
    - [getEdges](#getedges)
    - [getEdges (vertex)](#getedges-vertex)
    - [getNeighbors](#getneighbors)
  - [Task 3: Basic Algorithms](#task-3-basic-algorithms)
    - [shortestPath](#shortestpath)
    - [pathLength](#pathlength)
    - [getNeighbors (with range)](#getneighbors-with-range)
  - [Task 4: Advanced Algorithms](#task-4-advanced-algorithms)
    - [diameter](#diameter)
    - [getCenter](#getcenter)
    - [minimumSpanningComponents](#minimumspanningcomponents)
- [Empirical Results](#empirical-results)
- [Performance Plots](#performance-plots)
- [Discussion](#discussion)

## Introduction
This document analyzes the runtime complexity and empirical performance of all Task 1, Task 2, Task 3, and Task 4 methods for both ALGraph (Adjacency List) and AMGraph (Adjacency Matrix) implementations.

## Method Complexity Analysis

### Task 1: Basic Graph Operations

#### addVertex
- **ALGraph:** O(1) — HashSet and HashMap insertions are O(1) on average.
- **AMGraph:** O(1) amortized — ArrayList/HashMap insertions are O(1), but resizing the matrix is O(V^2) (happens rarely).

#### addEdge
- **ALGraph:** O(1) — HashSet insertions and adjacency list updates are O(1).
- **AMGraph:** O(1) — Direct matrix assignment and HashSet insertion are O(1).

#### hasVertex
- **ALGraph:** O(1) — HashSet lookup.
- **AMGraph:** O(1) — HashMap lookup.

#### hasEdge (by edge)
- **ALGraph:** O(1) — HashSet lookup.
- **AMGraph:** O(1) — HashSet lookup.

#### hasEdge (by vertices)
- **ALGraph:** O(deg(v1)) — Must scan adjacency list for v1 (deg(v1) is degree of v1).
- **AMGraph:** O(1) — Matrix lookup.

#### getEdge
- **ALGraph:** O(deg(v1)) — Must scan adjacency list for v1.
- **AMGraph:** O(E) — Must scan all edges to find the matching one (since only length is stored in matrix).

#### getEdgeLength
- **ALGraph:** O(deg(v1)) — Uses getEdge.
- **AMGraph:** O(1) — Matrix lookup.

### Task 2: Advanced Graph Operations

#### remove (vertex)
- **ALGraph:** O(deg(v) + V) — Remove all incident edges (deg(v)), then remove from all structures.
- **AMGraph:** O(E + V) — Remove all incident edges (scan all edges), update matrix, reindex vertices.

#### removeEdge
- **ALGraph:** O(1) — HashSet removals and adjacency list updates are O(1).
- **AMGraph:** O(1) — Matrix and HashSet removals are O(1).

#### getTotalEdgeLength
- **ALGraph:** O(E) — Must sum all edge lengths.
- **AMGraph:** O(E) — Must sum all edge lengths.

#### getVertices
- **ALGraph:** O(V) — Copy HashSet.
- **AMGraph:** O(V) — Copy ArrayList.

#### getEdges
- **ALGraph:** O(E) — Copy HashSet.
- **AMGraph:** O(E) — Copy HashSet.

#### getEdges (vertex)
- **ALGraph:** O(deg(v)) — Copy adjacency list for v.
- **AMGraph:** O(E) — Scan all edges for those incident to v.

#### getNeighbors
- **ALGraph:** O(deg(v)) — Scan adjacency list for v.
- **AMGraph:** O(V * deg(v)) — Scan matrix row and then scan edges for each neighbor.

### Task 3: Basic Algorithms

#### shortestPath
- **ALGraph:** O((V + E) log V) — Dijkstra's algorithm with binary heap.
- **AMGraph:** O((V + E) log V) — Same as ALGraph since both use the same algorithm.

#### pathLength
- **ALGraph:** O(P) — Where P is the path length.
- **AMGraph:** O(P) — Same as ALGraph.

#### getNeighbors (with range)
- **ALGraph:** O(V + E) — BFS traversal within the specified range.
- **AMGraph:** O(V + E) — Same as ALGraph since both use the same algorithm.

### Task 4: Advanced Algorithms

#### diameter
- **ALGraph:** O(V * (V + E) log V) — Run Dijkstra's algorithm from each vertex.
- **AMGraph:** O(V * (V + E) log V) — Same as ALGraph since both use the same algorithm.

**Complexity Analysis:**
- For each vertex V, we run Dijkstra's algorithm: O((V + E) log V)
- Total: O(V * (V + E) log V)
- For dense graphs (E ≈ V²): O(V³ log V)
- For sparse graphs (E ≈ V): O(V² log V)

#### getCenter
- **ALGraph:** O(V * (V + E) log V) — Calculate eccentricity for each vertex using Dijkstra's.
- **AMGraph:** O(V * (V + E) log V) — Same as ALGraph since both use the same algorithm.

**Complexity Analysis:**
- For each vertex V, we calculate eccentricity using Dijkstra's: O((V + E) log V)
- Total: O(V * (V + E) log V)
- Same complexity as diameter since we need to find shortest paths from each vertex

#### minimumSpanningComponents
- **ALGraph:** O(E log E + V log V + E log V) — Kruskal's algorithm + component finding.
- **AMGraph:** O(E log E + V log V + E log V) — Same as ALGraph since both use the same algorithm.

**Complexity Analysis:**
- Sort edges: O(E log E)
- Kruskal's algorithm with union-find: O(E log V)
- Find connected components: O(V + E)
- Total: O(E log E + E log V + V + E) = O(E log E + V log V + E log V)
- For dense graphs: O(V² log V)
- For sparse graphs: O(V log V)

## Empirical Results

### Task 4 Performance Analysis

The Task 4 algorithms are significantly more complex than previous tasks, as evidenced by the performance measurements:

1. **diameter()**: Shows O(V³) behavior for dense graphs, confirming the theoretical complexity of O(V * (V + E) log V).

2. **getCenter()**: Similar performance to diameter() since it uses the same underlying algorithm (Dijkstra's from each vertex).

3. **minimumSpanningComponents()**: Shows O(V² log V) behavior for dense graphs, matching the theoretical complexity of Kruskal's algorithm.

### Performance Comparison

- **ALGraph vs AMGraph**: Both implementations show similar performance for Task 4 algorithms since they delegate to the same GraphAlgorithms utility class.
- **Scalability**: All Task 4 algorithms show polynomial growth, making them suitable for small to medium-sized graphs but potentially slow for very large graphs.
- **Memory Usage**: ALGraph generally uses less memory for sparse graphs, while AMGraph uses more memory but provides faster edge existence checks.

## Performance Plots

Performance charts have been generated for all Task 4 algorithms:
- `task4_diameter_performance.png` - Diameter algorithm performance
- `task4_getCenter_performance.png` - Get center algorithm performance  
- `task4_minimumSpanningComponents_performance.png` - Minimum spanning components performance
- `task4_performance_summary.png` - Summary comparison of all Task 4 methods

## Discussion

### Task 4 Algorithm Characteristics

1. **diameter() and getCenter()**: These algorithms have the highest complexity (O(V³ log V) for dense graphs) because they require running Dijkstra's algorithm from every vertex. This makes them suitable for graphs with up to a few hundred vertices.

2. **minimumSpanningComponents()**: This algorithm has better complexity (O(V² log V) for dense graphs) and is more practical for larger graphs. The Kruskal's algorithm implementation with union-find provides good performance.

### Optimization Opportunities

1. **All-pairs shortest paths**: For diameter and center calculations, Floyd-Warshall algorithm could be used instead of running Dijkstra's from each vertex, potentially improving performance for dense graphs.

2. **Parallelization**: The diameter and center algorithms could be parallelized since each vertex's eccentricity calculation is independent.

3. **Caching**: For repeated diameter/center calculations on the same graph, results could be cached.

### Implementation Notes

- All Task 4 algorithms are implemented in the `GraphAlgorithms` utility class to avoid code duplication between ALGraph and AMGraph.
- The algorithms use standard implementations (Dijkstra's, Kruskal's) with appropriate data structures (PriorityQueue, Union-Find).
- Error handling is included for edge cases like empty graphs or invalid parameters. 