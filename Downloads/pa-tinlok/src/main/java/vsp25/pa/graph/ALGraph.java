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
        var edges = adjacencyList.get(v1);
        return edges.stream().anyMatch(e -> 
            (e.v1().equals(v1) && e.v2().equals(v2)) ||
            (e.v1().equals(v2) && e.v2().equals(v1))
        );
    }
    
    @Override
    public E getEdge(V v1, V v2) {
        if (!vertices.contains(v1) || !vertices.contains(v2)) return null;
        var edges = adjacencyList.get(v1);
        return edges.stream()
            .filter(e -> (e.v1().equals(v1) && e.v2().equals(v2)) ||
                        (e.v1().equals(v2) && e.v2().equals(v1)))
            .findFirst()
            .orElse(null);
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
        if (v == null || !vertices.contains(v)) return Set.of();
        return Set.copyOf(adjacencyList.get(v));
    }
    
    @Override
    public Map<V, E> getNeighbors(V v) {
        if (v == null || !vertices.contains(v)) return Map.of();
        var edges = adjacencyList.get(v);
        return edges.stream()
            .collect(HashMap::new, 
                (map, e) -> {
                    var other = e.v1().equals(v) ? e.v2() : e.v1();
                    map.put(other, e);
                }, 
                HashMap::putAll);
    }
    
    @Override
    public List<V> shortestPath(V source, V sink) {
        if (source == null || sink == null || !vertices.contains(source) || !vertices.contains(sink)) {
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
            
            // Explore neighbors
            for (E edge : adjacencyList.get(u)) {
                V v = edge.v1().equals(u) ? edge.v2() : edge.v1();
                int weight = edge.length();
                int newDist = dist + weight;
                
                if (newDist < distances.get(v)) {
                    distances.put(v, newDist);
                    previous.put(v, u);
                    pq.offer(Map.entry(v, newDist));
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
        if (v == null || !vertices.contains(v) || range < 0) {
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
            
            // Explore neighbors
            for (E edge : adjacencyList.get(current)) {
                V neighbor = edge.v1().equals(current) ? edge.v2() : edge.v1();
                int newDist = currentDist + edge.length();
                
                if (newDist <= range && !distances.containsKey(neighbor)) {
                    distances.put(neighbor, newDist);
                    neighbors.put(neighbor, edge);
                    queue.offer(neighbor);
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
