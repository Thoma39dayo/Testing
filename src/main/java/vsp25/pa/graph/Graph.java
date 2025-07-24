package vsp25.pa.graph;

import java.util.*;

/**
 * Main graph implementation that uses composition with either ALGraph or AMGraph.
 * This class provides a unified interface for graph operations and algorithms.
 *
 * @param <V> the type of vertex
 * @param <E> the type of edge
 */
public class Graph<V extends Vertex, E extends Edge<V>> implements GraphInterface<V, E> {
    
    // Internal graph implementation (composition)
    private final GraphInterface<V, E> implementation;
    
    /**
     * Constructs a graph using adjacency list representation.
     */
    public Graph() {
        this.implementation = new ALGraph<>();
    }
    
    /**
     * Constructs a graph using the specified implementation.
     * 
     * @param implementation the graph implementation to use
     */
    public Graph(GraphInterface<V, E> implementation) {
        this.implementation = implementation;
    }
    
    /**
     * Constructs a graph using adjacency matrix representation.
     * 
     * @param maxVertices the maximum number of vertices
     */
    public Graph(int maxVertices) {
        this.implementation = new AMGraph<>(maxVertices);
    }
    
    // ===== MUTABLE OPERATIONS =====
    
    @Override
    public boolean addVertex(V v) {
        return implementation.addVertex(v);
    }
    
    @Override
    public boolean addEdge(E e) {
        return implementation.addEdge(e);
    }
    
    @Override
    public boolean remove(V v) {
        return implementation.remove(v);
    }
    
    @Override
    public boolean removeEdge(E e) {
        return implementation.removeEdge(e);
    }
    
    // ===== QUERY OPERATIONS =====
    
    @Override
    public boolean hasVertex(V v) {
        return implementation.hasVertex(v);
    }
    
    @Override
    public boolean hasEdge(E e) {
        return implementation.hasEdge(e);
    }
    
    @Override
    public boolean hasEdge(V v1, V v2) {
        return implementation.hasEdge(v1, v2);
    }
    
    @Override
    public E getEdge(V v1, V v2) {
        return implementation.getEdge(v1, v2);
    }
    
    @Override
    public int getEdgeLength(V v1, V v2) {
        return implementation.getEdgeLength(v1, v2);
    }
    
    @Override
    public int getTotalEdgeLength() {
        return implementation.getTotalEdgeLength();
    }
    
    // ===== COLLECTION OPERATIONS =====
    
    @Override
    public Set<V> getVertices() {
        return implementation.getVertices();
    }
    
    @Override
    public Set<E> getEdges() {
        return implementation.getEdges();
    }
    
    @Override
    public Set<E> getEdges(V v) {
        return implementation.getEdges(v);
    }
    
    @Override
    public Map<V, E> getNeighbors(V v) {
        return implementation.getNeighbors(v);
    }
    
    // ===== ALGORITHM OPERATIONS =====
    
    @Override
    public List<V> shortestPath(V source, V sink) {
        return implementation.shortestPath(source, sink);
    }
    
    @Override
    public int pathLength(List<V> path) {
        return implementation.pathLength(path);
    }
    
    @Override
    public Map<V, E> getNeighbors(V v, int range) {
        return implementation.getNeighbors(v, range);
    }
    
    @Override
    public Set<GraphInterface<V, E>> minimumSpanningComponents(int k) {
        return implementation.minimumSpanningComponents(k);
    }
    
    @Override
    public int diameter() {
        return implementation.diameter();
    }
    
    @Override
    public V getCenter() {
        return implementation.getCenter();
    }

    //// add all new code above this line ////

    /**
     * This method removes some edges at random while preserving connectivity
     * <p>
     * DO NOT CHANGE THIS METHOD
     * </p>
     * <p>
     * You will need to implement getVertices() and getEdges(V v) for this
     * method to run correctly
     *</p>
     * <p><strong>requires:</strong> this graph is connected</p>
     *
     * @param rng random number generator to select edges at random
     */
    public void pruneRandomEdges(Random rng) {
        class VEPair {
            V v;
            E e;

            public VEPair(V v, E e) {
                this.v = v;
                this.e = e;
            }
        }
        /* Visited Nodes */
        Set<V> visited = new HashSet<>();
        /* Nodes to visit and the edge used to reach them */
        Deque<VEPair> stack = new LinkedList<VEPair>();
        /* Edges that could be removed */
        ArrayList<E> candidates = new ArrayList<>();
        /* Edges that must be kept to maintain connectivity */
        Set<E> keep = new HashSet<>();

        V start = null;
        for (V v : this.getVertices()) {
            start = v;
            break;
        }
        if (start == null) {
            // nothing to do
            return;
        }
        stack.push(new VEPair(start, null));
        while (!stack.isEmpty()) {
            VEPair pair = stack.pop();
            if (visited.add(pair.v)) {
                keep.add(pair.e);
                for (E e : this.getEdges(pair.v)) {
                    stack.push(new VEPair(e.distinctVertex(pair.v), e));
                }
            } else if (!keep.contains(pair.e)) {
                candidates.add(pair.e);
            }
        }
        // randomly trim some candidate edges
        int iterations = rng.nextInt(candidates.size());
        for (int count = 0; count < iterations; ++count) {
            int end = candidates.size() - 1;
            int index = rng.nextInt(candidates.size());
            E trim = candidates.get(index);
            candidates.set(index, candidates.get(end));
            candidates.remove(end);
            removeEdge(trim);
        }
    }
}
