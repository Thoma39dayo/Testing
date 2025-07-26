package vsp25.pa.graph;

import java.util.*;
import java.util.stream.IntStream;

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
        return edges.stream()
            .mapToInt(E::length)
            .sum();
    }

    @Override
    public Set<V> getVertices() {
        return Set.copyOf(vertices);
    }

    @Override
    public Set<E> getEdges() {
        return Set.copyOf(edges);
    }

    @Override
    public Set<E> getEdges(V v) {
        if (v == null || !vertexToIndex.containsKey(v)) return Set.of();
        return edges.stream()
            .filter(e -> e.v1().equals(v) || e.v2().equals(v))
            .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    @Override
    public java.util.Map<V, E> getNeighbors(V v) {
        var idx = vertexToIndex.get(v);
        if (v == null || idx == null) return Map.of();
        
        return IntStream.range(0, vertices.size())
            .filter(i -> adjacencyMatrix[idx][i] != NO_EDGE)
            .mapToObj(vertices::get)
            .collect(HashMap::new, (map, other) -> {
                var edge = edges.stream()
                    .filter(e -> (e.v1().equals(v) && e.v2().equals(other)) || 
                                (e.v2().equals(v) && e.v1().equals(other)))
                    .findFirst()
                    .orElse(null);
                if (edge != null) {
                    map.put(other, edge);
                }
            }, HashMap::putAll);
    }
    
    @Override
    public List<V> shortestPath(V source, V sink) {
        if (source == null || sink == null || !vertexToIndex.containsKey(source) || !vertexToIndex.containsKey(sink)) {
            return List.of();
        }
        
        if (source.equals(sink)) {
            return List.of(source);
        }
        
        // Dijkstra's algorithm implementation
        Map<V, Integer> distances = new HashMap<>();
        Map<V, V> previous = new HashMap<>();
        PriorityQueue<Map.Entry<V, Integer>> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.getValue(), b.getValue())
        );
        
        // Initialize distances
        for (V vertex : vertices) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        pq.offer(Map.entry(source, 0));
        
        while (!pq.isEmpty()) {
            var current = pq.poll();
            V u = current.getKey();
            int dist = current.getValue();
            
            if (dist > distances.get(u)) continue;
            
            if (u.equals(sink)) break;
            
            // Explore neighbors using adjacency matrix
            Integer uIndex = vertexToIndex.get(u);
            for (int i = 0; i < vertices.size(); i++) {
                if (adjacencyMatrix[uIndex][i] != NO_EDGE) {
                    V v = vertices.get(i);
                    int weight = adjacencyMatrix[uIndex][i];
                    int newDist = dist + weight;
                    
                    if (newDist < distances.get(v)) {
                        distances.put(v, newDist);
                        previous.put(v, u);
                        pq.offer(Map.entry(v, newDist));
                    }
                }
            }
        }
        
        // Reconstruct path
        if (distances.get(sink) == Integer.MAX_VALUE) {
            return List.of(); // No path exists
        }
        
        List<V> path = new ArrayList<>();
        V current = sink;
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }
        
        return path;
    }
    
    @Override
    public int pathLength(List<V> path) {
        if (path == null || path.size() < 2) return 0;
        
        int totalLength = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            V v1 = path.get(i);
            V v2 = path.get(i + 1);
            E edge = getEdge(v1, v2);
            if (edge == null) return -1; // Invalid path
            totalLength += edge.length();
        }
        return totalLength;
    }
    
    @Override
    public Map<V, E> getNeighbors(V v, int range) {
        if (v == null || !vertexToIndex.containsKey(v) || range < 0) {
            return Map.of();
        }
        
        if (range == 0) {
            return Map.of();
        }
        
        // BFS implementation for range-based neighbors
        Map<V, E> neighbors = new HashMap<>();
        Map<V, Integer> distances = new HashMap<>();
        Queue<V> queue = new LinkedList<>();
        
        distances.put(v, 0);
        queue.offer(v);
        
        while (!queue.isEmpty()) {
            V current = queue.poll();
            int currentDist = distances.get(current);
            
            if (currentDist >= range) continue;
            
            // Explore neighbors using adjacency matrix
            Integer currentIndex = vertexToIndex.get(current);
            for (int i = 0; i < vertices.size(); i++) {
                if (adjacencyMatrix[currentIndex][i] != NO_EDGE) {
                    V neighbor = vertices.get(i);
                    int weight = adjacencyMatrix[currentIndex][i];
                    int newDist = currentDist + weight;
                    
                    if (newDist <= range && !distances.containsKey(neighbor)) {
                        distances.put(neighbor, newDist);
                        // Find the edge to this neighbor
                        E edge = edges.stream()
                            .filter(e -> (e.v1().equals(current) && e.v2().equals(neighbor)) || 
                                        (e.v2().equals(current) && e.v1().equals(neighbor)))
                            .findFirst()
                            .orElse(null);
                        if (edge != null) {
                            neighbors.put(neighbor, edge);
                        }
                        queue.offer(neighbor);
                    }
                }
            }
        }
        
        return neighbors;
    }
    
    @Override
    public Set<GraphInterface<V, E>> minimumSpanningComponents(int k) {
        return GraphAlgorithms.minimumSpanningComponents(this, k);
    }
    
    @Override
    public int diameter() {
        return GraphAlgorithms.diameter(this);
    }
    
    @Override
    public V getCenter() {
        return GraphAlgorithms.getCenter(this);
    }
}
