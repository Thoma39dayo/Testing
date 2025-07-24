package vsp25.pa.graph;

import java.util.*;

/**
 * Adjacency Matrix implementation of a graph.
 * This implementation is efficient for dense graphs and provides
 * fast edge lookups.
 * 
 * Note: Vertex deletions are typically infrequent in matrix-based applications.
 * Implementors can optimize accordingly.
 * 
 * @param <V> the type of vertex
 * @param <E> the type of edge
 */
public class AMGraph<V extends Vertex, E extends Edge<V>> implements GraphInterface<V, E> {
    
    // Internal data structures for adjacency matrix representation
    private int[][] adjacencyMatrix;
    private List<V> vertices;
    private Map<V, Integer> vertexToIndex;
    private Set<E> edges;
    private int capacity;
    private static final int NO_EDGE = -1;

    /**
     * Create an empty graph with dynamic resizing.
     */
    public AMGraph() {
        this(16); // Default initial capacity
    }
    
    /**
     * Create an empty graph with specified initial capacity.
     * 
     * @param initialCapacity the initial capacity for the adjacency matrix
     */
    public AMGraph(int initialCapacity) {
        if (initialCapacity <= 0) initialCapacity = 16;
        this.capacity = initialCapacity;
        this.adjacencyMatrix = new int[capacity][capacity];
        for (var row : adjacencyMatrix) Arrays.fill(row, NO_EDGE);
        this.vertices = new ArrayList<>(capacity);
        this.vertexToIndex = new HashMap<>();
        this.edges = new HashSet<>();
    }
    
    // ===== TASK 1: Basic Graph Operations =====
    
    @Override
    public boolean addVertex(V v) {
        if (v == null || vertexToIndex.containsKey(v)) return false;
        if (vertices.size() >= capacity) {
            // Resize matrix
            int newCapacity = capacity * 2;
            int[][] newMatrix = new int[newCapacity][newCapacity];
            for (var row : newMatrix) Arrays.fill(row, NO_EDGE);
            for (int i = 0; i < capacity; i++)
                System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, capacity);
            adjacencyMatrix = newMatrix;
            capacity = newCapacity;
        }
        vertexToIndex.put(v, vertices.size());
        vertices.add(v);
        return true;
    }
    
    @Override
    public boolean addEdge(E e) {
        if (e == null) return false;
        V v1 = e.v1();
        V v2 = e.v2();
        Integer i1 = vertexToIndex.get(v1);
        Integer i2 = vertexToIndex.get(v2);
        if (i1 == null || i2 == null) return false;
        if (adjacencyMatrix[i1][i2] != NO_EDGE) return false;
        adjacencyMatrix[i1][i2] = e.length();
        adjacencyMatrix[i2][i1] = e.length(); // undirected
        edges.add(e);
        return true;
    }
    
    @Override
    public boolean hasVertex(V v) {
        return vertexToIndex.containsKey(v);
    }
    
    @Override
    public boolean hasEdge(E e) {
        if (e == null) return false;
        V v1 = e.v1();
        V v2 = e.v2();
        Integer i1 = vertexToIndex.get(v1);
        Integer i2 = vertexToIndex.get(v2);
        if (i1 == null || i2 == null) return false;
        return adjacencyMatrix[i1][i2] != NO_EDGE;
    }
    
    @Override
    public boolean hasEdge(V v1, V v2) {
        Integer i1 = vertexToIndex.get(v1);
        Integer i2 = vertexToIndex.get(v2);
        if (i1 == null || i2 == null) return false;
        return adjacencyMatrix[i1][i2] != NO_EDGE;
    }
    
    @Override
    public E getEdge(V v1, V v2) {
        Integer i1 = vertexToIndex.get(v1);
        Integer i2 = vertexToIndex.get(v2);
        if (i1 == null || i2 == null) return null;
        if (adjacencyMatrix[i1][i2] == NO_EDGE) return null;
        for (E e : edges) {
            if ((e.v1().equals(v1) && e.v2().equals(v2)) ||
                (e.v1().equals(v2) && e.v2().equals(v1))) {
                return e;
            }
        }
        return null;
    }
    
    @Override
    public int getEdgeLength(V v1, V v2) {
        Integer i1 = vertexToIndex.get(v1);
        Integer i2 = vertexToIndex.get(v2);
        if (i1 == null || i2 == null) return -1;
        return adjacencyMatrix[i1][i2];
    }
    
    // ===== TASK 2: Advanced Graph Operations =====
    
    @Override
    public boolean remove(V v) {
        Integer idx = vertexToIndex.get(v);
        if (v == null || idx == null) return false;
        // Remove all incident edges
        Set<E> toRemove = new HashSet<>();
        for (E e : edges) {
            if (e.v1().equals(v) || e.v2().equals(v)) {
                toRemove.add(e);
            }
        }
        for (E e : toRemove) {
            removeEdge(e);
        }
        // Remove from matrix, list, and map
        for (int i = 0; i < capacity; i++) {
            adjacencyMatrix[idx][i] = NO_EDGE;
            adjacencyMatrix[i][idx] = NO_EDGE;
        }
        vertices.remove((int) idx);
        vertexToIndex.remove(v);
        // Reindex
        for (int i = 0; i < vertices.size(); i++) {
            vertexToIndex.put(vertices.get(i), i);
        }
        return true;
    }

    @Override
    public boolean removeEdge(E e) {
        if (e == null || !edges.contains(e)) return false;
        V v1 = e.v1();
        V v2 = e.v2();
        Integer i1 = vertexToIndex.get(v1);
        Integer i2 = vertexToIndex.get(v2);
        if (i1 == null || i2 == null) return false;
        adjacencyMatrix[i1][i2] = NO_EDGE;
        adjacencyMatrix[i2][i1] = NO_EDGE;
        edges.remove(e);
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
        Set<E> result = new HashSet<>();
        if (v == null || !vertexToIndex.containsKey(v)) return result;
        for (E e : edges) {
            if (e.v1().equals(v) || e.v2().equals(v)) {
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public java.util.Map<V, E> getNeighbors(V v) {
        Map<V, E> neighbors = new HashMap<>();
        Integer idx = vertexToIndex.get(v);
        if (v == null || idx == null) return neighbors;
        for (int i = 0; i < vertices.size(); i++) {
            if (adjacencyMatrix[idx][i] != NO_EDGE) {
                V other = vertices.get(i);
                for (E e : edges) {
                    if ((e.v1().equals(v) && e.v2().equals(other)) || (e.v2().equals(v) && e.v1().equals(other))) {
                        neighbors.put(other, e);
                        break;
                    }
                }
            }
        }
        return neighbors;
    }
    
    // ===== TASK 3: Basic Algorithm Operations =====
    
    @Override
    public List<V> shortestPath(V source, V sink) {
        // TODO: Implement Dijkstra's algorithm
        // - Use PriorityQueue for efficient vertex selection
        // - Track distances and previous vertices
        // - Use matrix for neighbor lookups
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
        // - Use matrix for neighbor lookups
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
     * ADJACENCY MATRIX REPRESENTATION:
     * - Use 2D array to represent edges between vertices
     * - Good for dense graphs (many edges relative to vertices)
     * - Fast edge lookups O(1)
     * - Less efficient for iterating over neighbors
     * 
     * SUGGESTED DATA STRUCTURES:
     * - int[][] adjacencyMatrix: stores edge weights/lengths
     * - List<V> vertices: maps vertex index to vertex object
     * - Map<V, Integer> vertexToIndex: maps vertex to its index
     * - Set<E> edges: stores all edges for quick lookup
     * - int capacity: current matrix capacity
     * 
     * IMPLEMENTATION TIPS:
     * - Initialize matrix with -1 to indicate no edge
     * - Use vertex indices as matrix coordinates
     * - Remember matrix[i][j] = matrix[j][i] for undirected graphs
     * - Handle vertex removal carefully (reindexing or marking as deleted)
     * - For remove(V v): Set all matrix entries for that vertex to -1
     * - For getNeighbors(V v): Scan the row/column for non-null entries
     * 
     * COMMON PITFALLS:
     * - Don't forget to validate initialCapacity > 0
     * - Remember that matrix[i][j] = matrix[j][i] for undirected graphs
     * - Consider space efficiency vs time efficiency trade-offs
     * - Handle vertex removal carefully (reindexing or marking as deleted)
     * - Don't forget to handle null parameters
     * - Remember to check if vertices exist before adding edges
     * - Consider edge cases: empty graphs, single vertex, disconnected components
     * - Use defensive copies for returned collections
     * - Note: Vertex deletions are infrequent in typical matrix usage
     */
}
