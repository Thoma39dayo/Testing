package vsp25.pa.graph;

import java.util.*;

/**
 * Builder pattern for creating graphs with different representations and views.
 * This demonstrates how to create mutable and immutable views of the same graph.
 * 
 * @param <V> the type of vertex
 * @param <E> the type of edge
 */
public class GraphBuilder<V extends Vertex, E extends Edge<V>> {
    
    private final GraphInterface<V, E> graph;
    
    /**
     * Creates a new graph builder with adjacency list representation.
     */
    public GraphBuilder() {
        this.graph = new ALGraph<>();
    }
    
    /**
     * Creates a new graph builder with adjacency matrix representation.
     * 
     * @param maxVertices the maximum number of vertices
     */
    public GraphBuilder(int maxVertices) {
        this.graph = new AMGraph<>(maxVertices);
    }
    
    /**
     * Creates a new graph builder with the specified graph implementation.
     * 
     * @param graph the graph implementation to use
     */
    public GraphBuilder(GraphInterface<V, E> graph) {
        this.graph = graph;
    }
    
    /**
     * Adds a vertex to the graph.
     * 
     * @param vertex the vertex to add
     * @return this builder for method chaining
     */
    public GraphBuilder<V, E> addVertex(V vertex) {
        graph.addVertex(vertex);
        return this;
    }
    
    /**
     * Adds an edge to the graph.
     * 
     * @param edge the edge to add
     * @return this builder for method chaining
     */
    public GraphBuilder<V, E> addEdge(E edge) {
        graph.addEdge(edge);
        return this;
    }
    
    /**
     * Adds multiple vertices to the graph.
     * 
     * @param vertices the vertices to add
     * @return this builder for method chaining
     */
    @SafeVarargs
    public final GraphBuilder<V, E> addVertices(V... vertices) {
        for (V vertex : vertices) {
            graph.addVertex(vertex);
        }
        return this;
    }
    
    /**
     * Adds multiple edges to the graph.
     * 
     * @param edges the edges to add
     * @return this builder for method chaining
     */
    @SafeVarargs
    public final GraphBuilder<V, E> addEdges(E... edges) {
        for (E edge : edges) {
            graph.addEdge(edge);
        }
        return this;
    }
    
    /**
     * Builds the graph with a mutable view.
     * 
     * @return a mutable view of the graph
     */
    public MutableGraphView<V, E> buildMutable() {
        return new MutableGraphView<>(graph);
    }
    
    /**
     * Builds the graph with an immutable view.
     * 
     * @return an immutable view of the graph
     */
    public ImmutableGraphView<V, E> buildImmutable() {
        return new ImmutableGraphView<>(graph);
    }
    
    /**
     * Builds the graph with a unified interface.
     * 
     * @return the graph with unified interface
     */
    public GraphInterface<V, E> build() {
        return graph;
    }
    
    /**
     * Mutable view of a graph that allows modification operations.
     * 
     * @param <V> the type of vertex
     * @param <E> the type of edge
     */
    public static class MutableGraphView<V extends Vertex, E extends Edge<V>> {
        private final GraphInterface<V, E> graph;
        
        private MutableGraphView(GraphInterface<V, E> graph) {
            this.graph = graph;
        }
        
        /**
         * Adds a vertex to the graph.
         * 
         * @param vertex the vertex to add
         * @return true if the vertex was added successfully
         */
        public boolean addVertex(V vertex) {
            return graph.addVertex(vertex);
        }
        
        /**
         * Adds an edge to the graph.
         * 
         * @param edge the edge to add
         * @return true if the edge was added successfully
         */
        public boolean addEdge(E edge) {
            return graph.addEdge(edge);
        }
        
        /**
         * Removes a vertex from the graph.
         * 
         * @param vertex the vertex to remove
         * @return true if the vertex was removed successfully
         */
        public boolean removeVertex(V vertex) {
            return graph.remove(vertex);
        }
        
        /**
         * Removes an edge from the graph.
         * 
         * @param edge the edge to remove
         * @return true if the edge was removed successfully
         */
        public boolean removeEdge(E edge) {
            return graph.removeEdge(edge);
        }
        
        // Query operations
        public boolean hasVertex(V vertex) {
            return graph.hasVertex(vertex);
        }
        
        public boolean hasEdge(E edge) {
            return graph.hasEdge(edge);
        }
        
        public boolean hasEdge(V v1, V v2) {
            return graph.hasEdge(v1, v2);
        }
        
        public E getEdge(V v1, V v2) {
            return graph.getEdge(v1, v2);
        }
        
        public int getEdgeLength(V v1, V v2) {
            return graph.getEdgeLength(v1, v2);
        }
        
        public int getTotalEdgeLength() {
            return graph.getTotalEdgeLength();
        }
        
        // Collection operations
        public Set<V> getVertices() {
            return graph.getVertices();
        }
        
        public Set<E> getEdges() {
            return graph.getEdges();
        }
        
        public Set<E> getEdges(V v) {
            return graph.getEdges(v);
        }
        
        public Map<V, E> getNeighbors(V v) {
            return graph.getNeighbors(v);
        }
        
        // Algorithm operations
        public List<V> shortestPath(V source, V sink) {
            return graph.shortestPath(source, sink);
        }
        
        public int pathLength(List<V> path) {
            return graph.pathLength(path);
        }
        
        public Map<V, E> getNeighbors(V v, int range) {
            return graph.getNeighbors(v, range);
        }
        
        public Set<GraphInterface<V, E>> minimumSpanningComponents(int k) {
            return graph.minimumSpanningComponents(k);
        }
        
        public int diameter() {
            return graph.diameter();
        }
        
        public V getCenter() {
            return graph.getCenter();
        }
        
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
     * Immutable view of a graph that only allows query operations.
     * 
     * @param <V> the type of vertex
     * @param <E> the type of edge
     */
    public static class ImmutableGraphView<V extends Vertex, E extends Edge<V>> {
        private final GraphInterface<V, E> graph;
        
        private ImmutableGraphView(GraphInterface<V, E> graph) {
            this.graph = graph;
        }
        
        /**
         * Checks if a vertex exists in the graph.
         * 
         * @param vertex the vertex to check
         * @return true if the vertex exists
         */
        public boolean hasVertex(V vertex) {
            return graph.hasVertex(vertex);
        }
        
        /**
         * Checks if an edge exists in the graph.
         * 
         * @param edge the edge to check
         * @return true if the edge exists
         */
        public boolean hasEdge(E edge) {
            return graph.hasEdge(edge);
        }
        
        /**
         * Gets the edge between two vertices.
         * 
         * @param v1 the first vertex
         * @param v2 the second vertex
         * @return the edge between the vertices, or null if no such edge exists
         */
        public E getEdge(V v1, V v2) {
            return graph.getEdge(v1, v2);
        }
        
        /**
         * Gets all vertices in the graph.
         * 
         * @return a set of all vertices
         */
        public Set<V> getVertices() {
            return graph.getVertices();
        }
        
        /**
         * Gets all edges in the graph.
         * 
         * @return a set of all edges
         */
        public Set<E> getEdges() {
            return graph.getEdges();
        }
        
        /**
         * Gets all edges incident on a vertex.
         * 
         * @param vertex the vertex
         * @return a set of edges incident on the vertex
         */
        public Set<E> getEdges(V vertex) {
            return graph.getEdges(vertex);
        }
        
        /**
         * Gets all neighbors of a vertex.
         * 
         * @param vertex the vertex
         * @return a map where keys are neighbors and values are the edges to those neighbors
         */
        public Map<V, E> getNeighbors(V vertex) {
            return graph.getNeighbors(vertex);
        }
        
        /**
         * Computes the shortest path between two vertices.
         * 
         * @param source the starting vertex
         * @param sink the destination vertex
         * @return a list of vertices representing the shortest path
         */
        public List<V> shortestPath(V source, V sink) {
            return graph.shortestPath(source, sink);
        }
        
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
     * Example usage of the GraphBuilder pattern.
     * This demonstrates how to create graphs with different views.
     */
    public static void exampleUsage() {
        // Create vertices
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        
        // Create edges
        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 3);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 10);
        
        // Build a graph with mutable view
        GraphBuilder<Vertex, Edge<Vertex>> builder = new GraphBuilder<>();
        MutableGraphView<Vertex, Edge<Vertex>> mutableGraph = builder
            .addVertices(v1, v2, v3)
            .addEdges(e1, e2, e3)
            .buildMutable();
        
        // Use mutable operations
        Vertex v4 = new Vertex(4, "D");
        Edge<Vertex> e4 = new Edge<>(v3, v4, 2);
        mutableGraph.addVertex(v4);
        mutableGraph.addEdge(e4);
        
        // Build a graph with immutable view
        GraphBuilder<Vertex, Edge<Vertex>> builder2 = new GraphBuilder<>();
        ImmutableGraphView<Vertex, Edge<Vertex>> immutableGraph = builder2
            .addVertices(v1, v2, v3)
            .addEdges(e1, e2, e3)
            .buildImmutable();
        
        // Use query operations only
        Set<Vertex> vertices = immutableGraph.getVertices();
        List<Vertex> path = immutableGraph.shortestPath(v1, v3);
        
        // Build a graph with unified interface
        GraphInterface<Vertex, Edge<Vertex>> graph = builder2
            .addVertices(v1, v2, v3)
            .addEdges(e1, e2, e3)
            .build();
        
        // Use all operations
        graph.addVertex(v4);
        graph.addEdge(e4);
        Set<Vertex> allVertices = graph.getVertices();
        List<Vertex> shortestPath = graph.shortestPath(v1, v4);
    }
} 