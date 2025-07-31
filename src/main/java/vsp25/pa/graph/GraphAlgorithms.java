package vsp25.pa.graph;

import java.util.*;

/**
 * Utility class containing graph algorithms for Task 4.
 * These algorithms can work with any GraphInterface implementation.
 * 
 * This class provides implementations for:
 * - diameter() - All-pairs shortest paths
 * - getCenter() - Minimum eccentricity vertex
 * - minimumSpanningComponents(int k) - Kruskal's or Prim's algorithm
 */
public class GraphAlgorithms {
    
    /**
     * Calculates the diameter of the graph.
     * The diameter is the longest shortest path between any two vertices.
     * 
     * @param graph the graph to analyze
     * @param <V> vertex type
     * @param <E> edge type
     * @return the diameter of the graph, or -1 if graph is empty
     */
    public static <V extends Vertex, E extends Edge<V>> int diameter(GraphInterface<V, E> graph) {
        Set<V> vertices = graph.getVertices();
        if (vertices.isEmpty()) {
            return -1;
        }
        
        int maxDiameter = 0;
        
        // For each vertex, run Dijkstra's algorithm to find shortest paths to all other vertices
        for (V source : vertices) {
            Map<V, Integer> distances = dijkstra(graph, source);
            
            // Find the maximum distance from this source
            for (int distance : distances.values()) {
                if (distance != Integer.MAX_VALUE && distance > maxDiameter) {
                    maxDiameter = distance;
                }
            }
        }
        
        return maxDiameter;
    }
    
    /**
     * Finds the center of the graph.
     * The center is the vertex with minimum eccentricity (maximum distance to any other vertex).
     * 
     * @param graph the graph to analyze
     * @param <V> vertex type
     * @param <E> edge type
     * @return the center vertex, or null if graph is empty
     */
    public static <V extends Vertex, E extends Edge<V>> V getCenter(GraphInterface<V, E> graph) {
        Set<V> vertices = graph.getVertices();
        if (vertices.isEmpty()) {
            return null;
        }
        
        V center = null;
        int minEccentricity = Integer.MAX_VALUE;
        
        // For each vertex, calculate its eccentricity
        for (V vertex : vertices) {
            int eccentricity = eccentricity(graph, vertex);
            if (eccentricity < minEccentricity) {
                minEccentricity = eccentricity;
                center = vertex;
            }
        }
        
        return center;
    }
    
    /**
     * Partitions the graph into k connected components using minimum spanning tree algorithms.
     * 
     * @param graph the graph to partition
     * @param k the number of components to create
     * @param <V> vertex type
     * @param <E> edge type
     * @return a set of k connected subgraphs
     */
    public static <V extends Vertex, E extends Edge<V>> Set<GraphInterface<V, E>> minimumSpanningComponents(
            GraphInterface<V, E> graph, int k) {
        Set<V> vertices = graph.getVertices();
        if (vertices.isEmpty() || k <= 0 || k > vertices.size()) {
            return new HashSet<>();
        }
        
        // If k == 1, return the entire graph as one component
        if (k == 1) {
            Set<GraphInterface<V, E>> result = new HashSet<>();
            result.add(graph);
            return result;
        }
        
        // Use Kruskal's algorithm to find minimum spanning tree
        List<E> mstEdges = kruskal(graph);
        
        // Remove k-1 edges to create k components
        // Sort edges by weight in descending order and remove the heaviest k-1 edges
        mstEdges.sort((e1, e2) -> Integer.compare(e2.length(), e1.length()));
        
        // Create a new graph with only the MST edges minus the k-1 heaviest
        GraphInterface<V, E> mstGraph = createMSTGraph(graph, mstEdges, k - 1);
        
        // Find connected components in the resulting graph
        return findConnectedComponents(mstGraph);
    }
    
    /**
     * Helper method to run Dijkstra's algorithm from a source vertex.
     * 
     * @param graph the graph to analyze
     * @param source the source vertex
     * @param <V> vertex type
     * @param <E> edge type
     * @return map of vertices to their shortest path distances from source
     */
    private static <V extends Vertex, E extends Edge<V>> Map<V, Integer> dijkstra(
            GraphInterface<V, E> graph, V source) {
        Set<V> vertices = graph.getVertices();
        Map<V, Integer> distances = new HashMap<>();
        Map<V, V> previous = new HashMap<>();
        
        // Initialize distances
        for (V vertex : vertices) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        
        // Priority queue for Dijkstra's algorithm
        PriorityQueue<Map.Entry<V, Integer>> pq = new PriorityQueue<>(
            Map.Entry.comparingByValue()
        );
        pq.offer(Map.entry(source, 0));
        
        while (!pq.isEmpty()) {
            Map.Entry<V, Integer> entry = pq.poll();
            V u = entry.getKey();
            int dist = entry.getValue();
            
            // If we've already found a shorter path, skip
            if (dist > distances.get(u)) {
                continue;
            }
            
            // Explore neighbors
            Map<V, E> neighbors = graph.getNeighbors(u);
            for (Map.Entry<V, E> neighborEntry : neighbors.entrySet()) {
                V v = neighborEntry.getKey();
                E edge = neighborEntry.getValue();
                int weight = edge.length();
                int newDist = dist + weight;
                
                if (newDist < distances.get(v)) {
                    distances.put(v, newDist);
                    previous.put(v, u);
                    pq.offer(Map.entry(v, newDist));
                }
            }
        }
        
        return distances;
    }
    
    /**
     * Helper method to calculate eccentricity of a vertex.
     * Eccentricity is the maximum distance from this vertex to any other vertex.
     * 
     * @param graph the graph to analyze
     * @param vertex the vertex to calculate eccentricity for
     * @param <V> vertex type
     * @param <E> edge type
     * @return the eccentricity of the vertex
     */
    private static <V extends Vertex, E extends Edge<V>> int eccentricity(
            GraphInterface<V, E> graph, V vertex) {
        Map<V, Integer> distances = dijkstra(graph, vertex);
        
        int maxDistance = 0;
        for (int distance : distances.values()) {
            if (distance != Integer.MAX_VALUE && distance > maxDistance) {
                maxDistance = distance;
            }
        }
        
        return maxDistance;
    }
    
    /**
     * Helper method to implement Kruskal's algorithm for finding minimum spanning tree.
     * 
     * @param graph the graph to find MST for
     * @param <V> vertex type
     * @param <E> edge type
     * @return list of edges in the minimum spanning tree
     */
    private static <V extends Vertex, E extends Edge<V>> List<E> kruskal(GraphInterface<V, E> graph) {
        Set<E> edges = graph.getEdges();
        List<E> sortedEdges = new ArrayList<>(edges);
        sortedEdges.sort(Comparator.comparingInt(E::length));
        
        // Union-Find data structure for tracking connected components
        Map<V, V> parent = new HashMap<>();
        Map<V, Integer> rank = new HashMap<>();
        
        // Initialize union-find
        for (V vertex : graph.getVertices()) {
            parent.put(vertex, vertex);
            rank.put(vertex, 0);
        }
        
        List<E> mstEdges = new ArrayList<>();
        
        for (E edge : sortedEdges) {
            V v1 = edge.v1();
            V v2 = edge.v2();
            
            V root1 = find(parent, v1);
            V root2 = find(parent, v2);
            
            if (!root1.equals(root2)) {
                mstEdges.add(edge);
                union(parent, rank, root1, root2);
            }
        }
        
        return mstEdges;
    }
    
    /**
     * Helper method for union-find: find the root of a vertex.
     */
    private static <V extends Vertex> V find(Map<V, V> parent, V vertex) {
        if (!parent.get(vertex).equals(vertex)) {
            parent.put(vertex, find(parent, parent.get(vertex)));
        }
        return parent.get(vertex);
    }
    
    /**
     * Helper method for union-find: union two sets.
     */
    private static <V extends Vertex> void union(Map<V, V> parent, Map<V, Integer> rank, V root1, V root2) {
        if (rank.get(root1) < rank.get(root2)) {
            parent.put(root1, root2);
        } else if (rank.get(root1) > rank.get(root2)) {
            parent.put(root2, root1);
        } else {
            parent.put(root2, root1);
            rank.put(root1, rank.get(root1) + 1);
        }
    }
    
    /**
     * Helper method to create a graph with MST edges minus the heaviest k edges.
     */
    private static <V extends Vertex, E extends Edge<V>> GraphInterface<V, E> createMSTGraph(
            GraphInterface<V, E> originalGraph, List<E> mstEdges, int edgesToRemove) {
        
        // Create a new ALGraph (we could also use AMGraph)
        GraphInterface<V, E> mstGraph = new ALGraph<>();
        
        // Add all vertices
        for (V vertex : originalGraph.getVertices()) {
            mstGraph.addVertex(vertex);
        }
        
        // Add MST edges minus the heaviest edges
        for (int i = edgesToRemove; i < mstEdges.size(); i++) {
            mstGraph.addEdge(mstEdges.get(i));
        }
        
        return mstGraph;
    }
    
    /**
     * Helper method to find connected components in a graph.
     */
    private static <V extends Vertex, E extends Edge<V>> Set<GraphInterface<V, E>> findConnectedComponents(
            GraphInterface<V, E> graph) {
        Set<V> allVertices = graph.getVertices();
        Set<V> visited = new HashSet<>();
        Set<GraphInterface<V, E>> components = new HashSet<>();
        
        for (V vertex : allVertices) {
            if (!visited.contains(vertex)) {
                // BFS to find all vertices in this component
                Set<V> componentVertices = new HashSet<>();
                Queue<V> queue = new LinkedList<>();
                
                queue.offer(vertex);
                visited.add(vertex);
                componentVertices.add(vertex);
                
                while (!queue.isEmpty()) {
                    V current = queue.poll();
                    Map<V, E> neighbors = graph.getNeighbors(current);
                    
                    for (V neighbor : neighbors.keySet()) {
                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            componentVertices.add(neighbor);
                            queue.offer(neighbor);
                        }
                    }
                }
                
                // Create subgraph for this component
                GraphInterface<V, E> component = createSubgraph(graph, componentVertices);
                components.add(component);
            }
        }
        
        return components;
    }
    
    /**
     * Helper method to create a subgraph from a set of vertices.
     * 
     * @param graph the original graph
     * @param vertices the vertices to include in the subgraph
     * @param <V> vertex type
     * @param <E> edge type
     * @return a new graph containing only the specified vertices and their edges
     */
    private static <V extends Vertex, E extends Edge<V>> GraphInterface<V, E> createSubgraph(
            GraphInterface<V, E> graph, Set<V> vertices) {
        GraphInterface<V, E> subgraph = new ALGraph<>();
        
        // Add all specified vertices
        for (V vertex : vertices) {
            subgraph.addVertex(vertex);
        }
        
        // Add all edges between the specified vertices
        for (V vertex : vertices) {
            Map<V, E> neighbors = graph.getNeighbors(vertex);
            for (Map.Entry<V, E> entry : neighbors.entrySet()) {
                V neighbor = entry.getKey();
                E edge = entry.getValue();
                
                if (vertices.contains(neighbor) && !subgraph.hasEdge(edge)) {
                    subgraph.addEdge(edge);
                }
            }
        }
        
        return subgraph;
    }
} 