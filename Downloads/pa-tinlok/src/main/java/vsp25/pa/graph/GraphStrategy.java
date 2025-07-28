package vsp25.pa.graph;

import java.util.*;

/**
 * Strategy pattern for graph operations with mutable and immutable interfaces.
 * This demonstrates how to separate concerns while maintaining a unified interface.
 * 
 * @param <V> the type of vertex
 * @param <E> the type of edge
 */
public class GraphStrategy<V extends Vertex, E extends Edge<V>> {
    
    /**
     * Interface for mutable graph operations.
     * 
     * @param <V> the type of vertex
     * @param <E> the type of edge
     */
    public interface MutableOperations<V extends Vertex, E extends Edge<V>> {
        boolean addVertex(V v);
        boolean addEdge(E e);
        boolean remove(V v);
        boolean remove(E e);
    }
    
    /**
     * Interface for algorithm operations.
     * 
     * @param <V> the type of vertex
     * @param <E> the type of edge
     */
    public interface AlgorithmOperations<V extends Vertex, E extends Edge<V>> {
        List<V> shortestPath(V source, V sink);
        Map<V, E> getNeighbors(V v, int range);
        Set<GraphInterface<V, E>> minimumSpanningComponents(int k);
        int diameter();
        V getCenter();
    }
    
    /**
     * Unified graph interface that combines mutable and algorithm operations.
     * 
     * @param <V> the type of vertex
     * @param <E> the type of edge
     */
    public interface Graph<V extends Vertex, E extends Edge<V>> extends 
            MutableOperations<V, E>, 
            AlgorithmOperations<V, E>,
            GraphInterface<V, E> {
        // Inherits all methods from GraphInterface
    }
    
    /**
     * Immutable view of a graph that only allows query and algorithm operations.
     * 
     * @param <V> the type of vertex
     * @param <E> the type of edge
     */
    public static class ImmutableView<V extends Vertex, E extends Edge<V>> {
        private final GraphInterface<V, E> graph;
        
        public ImmutableView(GraphInterface<V, E> graph) {
            this.graph = graph;
        }
        
        // Query operations
        public boolean hasVertex(V v) { return graph.hasVertex(v); }
        public boolean hasEdge(E e) { return graph.hasEdge(e); }
        public boolean hasEdge(V v1, V v2) { return graph.hasEdge(v1, v2); }
        public E getEdge(V v1, V v2) { return graph.getEdge(v1, v2); }
        public int getEdgeLength(V v1, V v2) { return graph.getEdgeLength(v1, v2); }
        public int getTotalEdgeLength() { return graph.getTotalEdgeLength(); }
        
        // Collection operations
        public Set<V> getVertices() { return graph.getVertices(); }
        public Set<E> getEdges() { return graph.getEdges(); }
        public Set<E> getEdges(V v) { return graph.getEdges(v); }
        public Map<V, E> getNeighbors(V v) { return graph.getNeighbors(v); }
        
        // Algorithm operations
        public List<V> shortestPath(V source, V sink) { return graph.shortestPath(source, sink); }
        public int pathLength(List<V> path) { return graph.pathLength(path); }
        public Map<V, E> getNeighbors(V v, int range) { return graph.getNeighbors(v, range); }
        public Set<GraphInterface<V, E>> minimumSpanningComponents(int k) { return graph.minimumSpanningComponents(k); }
        public int diameter() { return graph.diameter(); }
        public V getCenter() { return graph.getCenter(); }
        
        /**
         * Gets the underlying graph interface.
         * 
         * @return the graph interface
         */
        public GraphInterface<V, E> getGraph() {
            return graph;
        }
    }
    
    /**
     * Mutable view of a graph that allows all operations.
     * 
     * @param <V> the type of vertex
     * @param <E> the type of edge
     */
    public static class MutableView<V extends Vertex, E extends Edge<V>> {
        private final GraphInterface<V, E> graph;
        
        public MutableView(GraphInterface<V, E> graph) {
            this.graph = graph;
        }
        
        // Mutable operations
        public boolean addVertex(V v) { return graph.addVertex(v); }
        public boolean addEdge(E e) { return graph.addEdge(e); }
        public boolean remove(V v) { return graph.remove(v); }
        public boolean removeEdge(E e) { return graph.removeEdge(e); }
        
        // Query operations
        public boolean hasVertex(V v) { return graph.hasVertex(v); }
        public boolean hasEdge(E e) { return graph.hasEdge(e); }
        public boolean hasEdge(V v1, V v2) { return graph.hasEdge(v1, v2); }
        public E getEdge(V v1, V v2) { return graph.getEdge(v1, v2); }
        public int getEdgeLength(V v1, V v2) { return graph.getEdgeLength(v1, v2); }
        public int getTotalEdgeLength() { return graph.getTotalEdgeLength(); }
        
        // Collection operations
        public Set<V> getVertices() { return graph.getVertices(); }
        public Set<E> getEdges() { return graph.getEdges(); }
        public Set<E> getEdges(V v) { return graph.getEdges(v); }
        public Map<V, E> getNeighbors(V v) { return graph.getNeighbors(v); }
        
        // Algorithm operations
        public List<V> shortestPath(V source, V sink) { return graph.shortestPath(source, sink); }
        public int pathLength(List<V> path) { return graph.pathLength(path); }
        public Map<V, E> getNeighbors(V v, int range) { return graph.getNeighbors(v, range); }
        public Set<GraphInterface<V, E>> minimumSpanningComponents(int k) { return graph.minimumSpanningComponents(k); }
        public int diameter() { return graph.diameter(); }
        public V getCenter() { return graph.getCenter(); }
        
        /**
         * Gets the underlying graph interface.
         * 
         * @return the graph interface
         */
        public GraphInterface<V, E> getGraph() {
            return graph;
        }
    }
    
    /**
     * Example usage of the GraphStrategy pattern.
     * This demonstrates how to use different views of the same graph.
     */
    public static void exampleUsage() {
        // Create a graph
        GraphInterface<Vertex, Edge<Vertex>> graph = new ALGraph<>();
        
        // Create vertices
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        
        // Create edges
        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 3);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 10);
        
        // Use mutable view for construction
        MutableView<Vertex, Edge<Vertex>> mutableView = new MutableView<>(graph);
        mutableView.addVertex(v1);
        mutableView.addVertex(v2);
        mutableView.addVertex(v3);
        mutableView.addEdge(e1);
        mutableView.addEdge(e2);
        mutableView.addEdge(e3);
        
        // Use immutable view for algorithms
        ImmutableView<Vertex, Edge<Vertex>> immutableView = new ImmutableView<>(graph);
        
        // Query operations
        Set<Vertex> vertices = immutableView.getVertices();
        boolean hasEdge = immutableView.hasEdge(v1, v2);
        
        // Algorithm operations
        List<Vertex> path = immutableView.shortestPath(v1, v3);
        int diameter = immutableView.diameter();
        Vertex center = immutableView.getCenter();
        
        // Switch back to mutable view for modifications
        Vertex v4 = new Vertex(4, "D");
        Edge<Vertex> e4 = new Edge<>(v3, v4, 2);
        mutableView.addVertex(v4);
        mutableView.addEdge(e4);
        
        // Use immutable view again for new queries
        Set<Vertex> newVertices = immutableView.getVertices();
        List<Vertex> newPath = immutableView.shortestPath(v1, v4);
    }
    
    /**
     * Example of using the strategy pattern with different graph implementations.
     */
    public static void strategyExample() {
        // Strategy 1: Use adjacency list for sparse graphs
        GraphInterface<Vertex, Edge<Vertex>> sparseGraph = new ALGraph<>();
        MutableView<Vertex, Edge<Vertex>> sparseMutable = new MutableView<>(sparseGraph);
        ImmutableView<Vertex, Edge<Vertex>> sparseImmutable = new ImmutableView<>(sparseGraph);
        
        // Strategy 2: Use adjacency matrix for dense graphs
        GraphInterface<Vertex, Edge<Vertex>> denseGraph = new AMGraph<>(100);
        MutableView<Vertex, Edge<Vertex>> denseMutable = new MutableView<>(denseGraph);
        ImmutableView<Vertex, Edge<Vertex>> denseImmutable = new ImmutableView<>(denseGraph);
        
        // Use the same interface regardless of implementation
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        
        // Both implementations work the same way
        sparseMutable.addVertex(v1);
        sparseMutable.addVertex(v2);
        sparseMutable.addEdge(e1);
        
        denseMutable.addVertex(v1);
        denseMutable.addVertex(v2);
        denseMutable.addEdge(e1);
        
        // Query operations work the same
        boolean sparseHasEdge = sparseImmutable.hasEdge(v1, v2);
        boolean denseHasEdge = denseImmutable.hasEdge(v1, v2);
        
        // Algorithm operations work the same
        List<Vertex> sparsePath = sparseImmutable.shortestPath(v1, v2);
        List<Vertex> densePath = denseImmutable.shortestPath(v1, v2);
    }
} 