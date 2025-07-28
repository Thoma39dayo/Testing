package vsp25.pa.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Unified graph interface that combines mutable and immutable operations.
 * This interface provides all graph operations in a single, consistent API.
 * 
 * @author Sathish Gopalakrishnan
 * @param <V> the type of vertex
 * @param <E> the type of edge
 */
public interface GraphInterface<V extends Vertex, E extends Edge<V>> {
    
    // ===== MUTABLE OPERATIONS =====
    
    /**
     * Add a vertex to the graph.
     * 
     * @param v the vertex to add
     * @return true if the vertex was added successfully, false otherwise
     */
    boolean addVertex(V v);
    
    /**
     * Add an edge to the graph.
     * 
     * @param e the edge to add
     * @return true if the edge was added successfully, false otherwise
     */
    boolean addEdge(E e);
    
    /**
     * Remove a vertex from the graph and all its incident edges.
     * 
     * @param v the vertex to remove
     * @return true if the vertex was removed successfully, false otherwise
     */
    boolean remove(V v);
    
    /**
     * Remove an edge from the graph.
     * 
     * @param e the edge to remove
     * @return true if the edge was removed successfully, false otherwise
     */
    boolean removeEdge(E e);
    
    // ===== QUERY OPERATIONS =====
    
    /**
     * Check if a vertex exists in the graph.
     * 
     * @param v the vertex to check
     * @return true if the vertex exists, false otherwise
     */
    boolean hasVertex(V v);
    
    /**
     * Check if an edge exists in the graph.
     * 
     * @param e the edge to check
     * @return true if the edge exists, false otherwise
     */
    boolean hasEdge(E e);
    
    /**
     * Check if there's an edge between two vertices.
     * 
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @return true if there's an edge between v1 and v2, false otherwise
     */
    boolean hasEdge(V v1, V v2);
    
    /**
     * Get the edge between two vertices.
     * 
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @return the edge between v1 and v2, or null if no such edge exists
     */
    E getEdge(V v1, V v2);
    
    /**
     * Get the length of an edge between vertices.
     * 
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @return the length of the edge, or -1 if no such edge exists
     */
    int getEdgeLength(V v1, V v2);
    
    /**
     * Get the sum of all edge lengths in the graph.
     * 
     * @return the total length of all edges
     */
    int getTotalEdgeLength();
    
    // ===== COLLECTION OPERATIONS =====
    
    /**
     * Get all vertices in the graph.
     * 
     * @return a set of all vertices
     */
    Set<V> getVertices();
    
    /**
     * Get all edges in the graph.
     * 
     * @return a set of all edges
     */
    Set<E> getEdges();
    
    /**
     * Get all edges incident on a vertex.
     * 
     * @param v the vertex
     * @return a set of edges incident on v
     */
    Set<E> getEdges(V v);
    
    /**
     * Get all neighbors of a vertex.
     * 
     * @param v the vertex
     * @return a map where keys are neighbors and values are the edges to those neighbors
     */
    Map<V, E> getNeighbors(V v);
    
    // ===== ALGORITHM OPERATIONS =====
    
    /**
     * Compute the shortest path between two vertices.
     * 
     * @param source the starting vertex
     * @param sink the destination vertex
     * @return a list of vertices representing the shortest path, or empty list if no path exists
     */
    List<V> shortestPath(V source, V sink);
    
    /**
     * Compute the length of a given path.
     * 
     * @param path the list of vertices representing the path
     * @return the total length of the path
     */
    int pathLength(List<V> path);
    
    /**
     * Get all vertices within a given range from a vertex.
     * 
     * @param v the starting vertex
     * @param range the maximum path distance
     * @return a map where keys are vertices within range and values are the last edge on the shortest path
     */
    Map<V, E> getNeighbors(V v, int range);
    
    /**
     * Compute minimum spanning components.
     * 
     * @param k the number of components
     * @return a set of graphs representing the components
     */
    Set<GraphInterface<V, E>> minimumSpanningComponents(int k);
    
    /**
     * Compute the diameter of the graph.
     * 
     * @return the diameter (length of longest shortest path)
     */
    int diameter();
    
    /**
     * Find the center of the graph.
     * 
     * @return the vertex with minimum eccentricity
     */
    V getCenter();
} 