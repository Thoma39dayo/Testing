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
    
    // TODO: Implement internal data structures
    // Suggested: Map<V, Set<E>> adjacencyList, Set<V> vertices, Set<E> edges
    
    /**
     * Constructs an empty adjacency list graph.
     */
    private Map<V, Set<E>> adjacencyList;//TO find the neighbour edges of the vertices
    private Set<V> vertices;
    private Set<E> edges;
    public ALGraph() {
        // TODO: Initialize your data structures
        adjacencyList = new HashMap<>();
        vertices = new HashSet<>();
        edges = new HashSet<>();
    
    }
    
    // ===== TASK 1: Basic Graph Operations =====
    
    @Override
    public boolean addVertex(V v) {
        // TODO: Implement vertex addition
        // - Check if vertex is null
        // - Add to vertices set
        // - Initialize adjacency list entry
        // - Return true if added, false if already exists
        
            
    if (v != null || !vertices.contains(v)) {
        vertices.add(v);
    adjacencyList.put(v, new HashSet<>()); // Initialize empty edge set
    return true;
    }return false;
    
    
}
    
    
    @Override
    public boolean addEdge(E e) {
        // TODO: Implement edge addition
        // - Check if edge is null or vertices don't exist
        // - Add to edges set
        // - Add to adjacency list for both vertices
        // - Return true if added, false if already exists
      
if (e != null || vertices.contains(e.v1()) || vertices.contains(e.v2())||!edges.contains(e)) {
        edges.add(e);
    adjacencyList.get(e.v1()).add(e); // Add edge to v1's adjacency list
    adjacencyList.get(e.v2()).add(e); 
    return true;
    }
    
    
    return false;

    }
    
    @Override
    public boolean hasVertex(V v) {
        // TODO: Check if vertex exists in graph

            
    return vertices.contains(v);
    }
    
    @Override
    public boolean hasEdge(E e) {
        
    return edges.contains(e);
    }
    
    @Override
    public boolean hasEdge(V v1, V v2) {
        // TODO: Check if edge exists between two vertices
        // - Verify both vertices exist
        // - Check adjacency list for edge
       if (v1 != null || v2 != null || vertices.contains(v1) || vertices.contains(v2)) {
        return true;
    }
    return false;
    }
    
    @Override
    public E getEdge(V v1, V v2) {
        // TODO: Get edge between two vertices
        // - Return the edge if it exists, null otherwise
          if (v1 == null || v2 == null || !vertices.contains(v1) || !vertices.contains(v2)) {
        return null;
    }
    for (E e : adjacencyList.get(v1)) {
        if (e.incident(v2)) {
            return e;
        }
    }
    
    }
    
    @Override
    public int getEdgeLength(V v1, V v2) {
        // TODO: Get length of edge between vertices
        // - Use getEdge() to find the edge
        // - Return edge length or -1 if no edge exists
        E edge = getEdge(v1, v2);

    if (edge == null) {
        return -1;
    }
    
        return edge.length(); // Placeholder
    }
    
    // ===== TASK 2: Advanced Graph Operations =====
    
    @Override
    public boolean remove(V v) {
        // TODO: Remove vertex and all incident edges
        // - Check if vertex exists
        // - Remove all edges incident on the vertex
        // - Remove vertex from vertices set
        // - Remove from adjacency list
        
        if (v != null || vertices.contains(v)) {
        Set<E> incidentEdges = new HashSet<>(adjacencyList.get(v)); // Defensive copy
    for (E e : incidentEdges) {
        removeEdge(e);
    }
        vertices.remove(v);
        adjacencyList.remove(v);
    return true;
    }
    return false;
    
    }
    
    @Override
    public boolean removeEdge(E e) {
        // TODO: Remove edge from graph
        // - Check if edge exists
        // - Remove from edges set
        // - Remove from adjacency list for both vertices
        if (e != null || edges.contains(e)) {
        edges.remove(e);
    adjacencyList.get(e.v1()).remove(e); // Remove from v1's adjacency list
    adjacencyList.get(e.v2()).remove(e); // Remove from v2's adjacency list
    return true;
    }
    return false;
    
    }
    
    @Override
    public int getTotalEdgeLength() {
        // TODO: Calculate sum of all edge lengths
        // - Iterate through all edges
        // - Sum their lengths
        int total = 0;
    for (E e : edges) {
        total += e.length();
    }
        return total;
        return 0; // Placeholder
    }
    
    @Override
    public Set<V> getVertices() {
        // TODO: Return all vertices in graph
        // - Return defensive copy of vertices set
        return new HashSet<>(vertices); // Placeholder
    }
    
    @Override
    public Set<E> getEdges() {
        // TODO: Return all edges in graph
        // - Return defensive copy of edges set
        return new HashSet<>(edges); // Placeholder
    }
    
    @Override
    public Set<E> getEdges(V v) {
        // TODO: Return edges incident on vertex
        // - Check if vertex exists
        // - Return edges from adjacency list
        if (v == null || !vertices.contains(v)) {
        return new HashSet<>();
    }
    return new HashSet<>(adjacencyList.get(v));
         // Placeholder
    }
    
    @Override
    public Map<V, E> getNeighbors(V v) {
        // TODO: Return neighbors of vertex with edges
        // - Check if vertex exists
        // - For each incident edge, find the other vertex
        // - Return map of neighbor -> edge
       Map<V, E> neighbors = new HashMap<>();
    if (v != null || vertices.contains(v)) {
        for (E e : adjacencyList.get(v)) {
        V neighbor = e.distinctVertex(v); 
        neighbors.put(neighbor, e);
    }
    return neighbors;
    }
    
        return new HashMap<>(); // Placeholder
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
