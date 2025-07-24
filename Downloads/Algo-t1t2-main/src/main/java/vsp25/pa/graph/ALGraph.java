package vsp25.pa.graph;

import java.util.*;

/**
 * Adjacency List implementation of a graph.
 * This implementation is efficient for sparse graphs and provides
 * fast iteration over neighbors.
 * 
 * @param <V> the type of vertex
 * @param <E> the type of edge
 */
public class ALGraph<V extends Vertex, E extends Edge<V>> implements GraphInterface<V, E> {
    
    // Internal data structures for adjacency list representation
    private final Map<V, Set<E>> adjacencyList;
    private final Set<V> vertices;
    private final Set<E> edges;

    /**
     * Constructs an empty adjacency list graph.
     */
    public ALGraph() {
        this.adjacencyList = new HashMap<>();
        this.vertices = new HashSet<>();
        this.edges = new HashSet<>();
    }
    
    // ===== TASK 1: Basic Graph Operations =====
    
    @Override
    public boolean addVertex(V v) {
        if (v == null) return false;
        if (vertices.contains(v)) return false;
        vertices.add(v);
        adjacencyList.put(v, new HashSet<>());
        return true;
    }
    
    @Override
    public boolean addEdge(E e) {
        if (e == null) return false;
        V v1 = e.v1();
        V v2 = e.v2();
        if (!vertices.contains(v1) || !vertices.contains(v2)) return false;
        if (edges.contains(e)) return false;
        edges.add(e);
        adjacencyList.get(v1).add(e);
        if (!v1.equals(v2)) adjacencyList.get(v2).add(e); // undirected
        return true;
    }
    
    @Override
    public boolean hasVertex(V v) {
        return vertices.contains(v);
    }
    
    @Override
    public boolean hasEdge(E e) {
        return edges.contains(e);
    }
    
    @Override
    public boolean hasEdge(V v1, V v2) {
        if (!vertices.contains(v1) || !vertices.contains(v2)) return false;
        for (E e : adjacencyList.get(v1)) {
            if ((e.v1().equals(v1) && e.v2().equals(v2)) ||
                (e.v1().equals(v2) && e.v2().equals(v1))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public E getEdge(V v1, V v2) {
        if (!vertices.contains(v1) || !vertices.contains(v2)) return null;
        for (E e : adjacencyList.get(v1)) {
            if ((e.v1().equals(v1) && e.v2().equals(v2)) ||
                (e.v1().equals(v2) && e.v2().equals(v1))) {
                return e;
            }
        }
        return null;
    }
    
    @Override
    public int getEdgeLength(V v1, V v2) {
        E e = getEdge(v1, v2);
        return (e != null) ? e.length() : -1;
    }
    
    // ===== TASK 2: Advanced Graph Operations =====
    
    @Override
    public boolean remove(V v) {
        if (v == null || !vertices.contains(v)) return false;
        // Remove all incident edges
        Set<E> incident = new HashSet<>(adjacencyList.get(v));
        for (E e : incident) {
            removeEdge(e);
        }
        adjacencyList.remove(v);
        vertices.remove(v);
        return true;
    }
    
    @Override
    public boolean removeEdge(E e) {
        if (e == null || !edges.contains(e)) return false;
        V v1 = e.v1();
        V v2 = e.v2();
        edges.remove(e);
        if (adjacencyList.containsKey(v1)) adjacencyList.get(v1).remove(e);
        if (adjacencyList.containsKey(v2)) adjacencyList.get(v2).remove(e);
        return true;
    }
    
    @Override
    public int getTotalEdgeLength() {
        int sum = 0;
        for (E e : edges) {
            sum += e.length();
        }
        return sum;
    }
    
    @Override
    public Set<V> getVertices() {
        return new HashSet<>(vertices);
    }
    
    @Override
    public Set<E> getEdges() {
        return new HashSet<>(edges);
    }
    
    @Override
    public Set<E> getEdges(V v) {
        if (v == null || !vertices.contains(v)) return new HashSet<>();
        return new HashSet<>(adjacencyList.get(v));
    }
    
    @Override
    public Map<V, E> getNeighbors(V v) {
        Map<V, E> neighbors = new HashMap<>();
        if (v == null || !vertices.contains(v)) return neighbors;
        for (E e : adjacencyList.get(v)) {
            V other = e.v1().equals(v) ? e.v2() : e.v1();
            neighbors.put(other, e);
        }
        return neighbors;
    }
    
    // ===== TASK 3: Basic Algorithm Operations =====
    
    @Override
    public List<V> shortestPath(V source, V sink) {
        // TODO: Implement Dijkstra's algorithm
        // - Use PriorityQueue for efficient vertex selection
        // - Track distances and previous vertices
        // - Reconstruct path from sink to source
        // - Return empty list if no path exists
        return new ArrayList<>(); // Placeholder
    }
    
    @Override
    public int pathLength(List<V> path) {
        // TODO: Calculate total length of path
        // - Check if path is valid (consecutive vertices connected)
        // - Sum edge lengths between consecutive vertices
        // - Throw exception if invalid path
        return 0; // Placeholder
    }
    
    @Override
    public Map<V, E> getNeighbors(V v, int range) {
        // TODO: Implement BFS with distance tracking
        // - Use breadth-first search
        // - Track distances from source vertex
        // - Return vertices within specified range
        return new HashMap<>(); // Placeholder
    }
    
    // ===== TASK 4: Advanced Algorithm Operations =====
    
    @Override
    public Set<GraphInterface<V, E>> minimumSpanningComponents(int k) {
        // Delegate to the utility class for algorithm implementation
        return GraphAlgorithms.minimumSpanningComponents(this, k);
    }
    
    @Override
    public int diameter() {
        // Delegate to the utility class for algorithm implementation
        return GraphAlgorithms.diameter(this);
    }
    
    @Override
    public V getCenter() {
        // Delegate to the utility class for algorithm implementation
        return GraphAlgorithms.getCenter(this);
    }
    
    // ===== IMPLEMENTATION TIPS =====
    
    /*
     * ADJACENCY LIST REPRESENTATION:
     * - Each vertex maps to a set of its incident edges
     * - Good for sparse graphs (few edges relative to vertices)
     * - Efficient for iterating over neighbors
     * - Less efficient for checking if two vertices are connected
     * 
     * SUGGESTED DATA STRUCTURES:
     * - Map<V, Set<E>> adjacencyList: maps vertex to its incident edges
     * - Set<V> vertices: stores all vertices for quick lookup
     * - Set<E> edges: stores all edges for quick lookup
     * 
     * IMPLEMENTATION TIPS:
     * - Use HashMap for O(1) vertex lookups
     * - Use HashSet for edges to avoid duplicates
     * - Remember edges are undirected (v1-v2 = v2-v1)
     * - Update both vertex mappings when adding edges
     * - For remove(V v): Remove all edges connected to the vertex
     * - For getNeighbors(V v): Return Map<V, E> where keys are neighbors, values are edges
     * 
     * COMMON PITFALLS:
     * - Don't forget to handle null parameters
     * - Remember to check if vertices exist before adding edges
     * - Consider edge cases: empty graphs, single vertex, disconnected components
     * - Use defensive copies for returned collections
     * - Handle edge removal carefully (remove from both vertex mappings)
     */
}
