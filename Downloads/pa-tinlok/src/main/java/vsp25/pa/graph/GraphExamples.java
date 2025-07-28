package vsp25.pa.graph;

import java.util.*;

/**
 * Comprehensive examples demonstrating how to use the new graph patterns.
 * This file shows practical usage of GraphInterface, GraphBuilder, and GraphStrategy.
 * 
 * @author Sathish Gopalakrishnan
 * @version 1.0
 */
public class GraphExamples {
    
    /**
     * Example 1: Basic usage of the unified GraphInterface
     * Shows how to create and use graphs with different implementations.
     */
    public static void basicGraphUsage() {
        System.out.println("=== Basic Graph Usage ===");
        
        // Create graphs with different implementations
        GraphInterface<Vertex, Edge<Vertex>> alGraph = new ALGraph<>();
        GraphInterface<Vertex, Edge<Vertex>> amGraph = new AMGraph<>(100);
        
        // Create vertices
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        
        // Create edges
        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 3);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 10);
        
        // Add vertices and edges (same interface for both implementations)
        alGraph.addVertex(v1);
        alGraph.addVertex(v2);
        alGraph.addVertex(v3);
        alGraph.addEdge(e1);
        alGraph.addEdge(e2);
        alGraph.addEdge(e3);
        
        amGraph.addVertex(v1);
        amGraph.addVertex(v2);
        amGraph.addVertex(v3);
        amGraph.addEdge(e1);
        amGraph.addEdge(e2);
        amGraph.addEdge(e3);
        
        // Query operations
        System.out.println("ALGraph has edge A-B: " + alGraph.hasEdge(v1, v2));
        System.out.println("AMGraph has edge A-B: " + amGraph.hasEdge(v1, v2));
        
        // Algorithm operations
        List<Vertex> alPath = alGraph.shortestPath(v1, v3);
        List<Vertex> amPath = amGraph.shortestPath(v1, v3);
        
        System.out.println("ALGraph shortest path A to C: " + alPath);
        System.out.println("AMGraph shortest path A to C: " + amPath);
        
        // Both implementations work the same way!
        System.out.println("Path lengths are equal: " + 
            (alGraph.pathLength(alPath) == amGraph.pathLength(amPath)));
    }
    
    /**
     * Example 2: Using the GraphBuilder pattern
     * Shows how to build graphs with different views and representations.
     */
    public static void builderPatternUsage() {
        System.out.println("\n=== GraphBuilder Pattern Usage ===");
        
        // Create vertices and edges
        Vertex v1 = new Vertex(1, "Earth");
        Vertex v2 = new Vertex(2, "Mars");
        Vertex v3 = new Vertex(3, "Jupiter");
        Vertex v4 = new Vertex(4, "Saturn");
        
        Edge<Vertex> e1 = new Edge<>(v1, v2, 10);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 15);
        Edge<Vertex> e3 = new Edge<>(v3, v4, 12);
        
        // Build a graph with mutable view
        GraphBuilder<Vertex, Edge<Vertex>> builder = new GraphBuilder<>();
        GraphBuilder.MutableGraphView<Vertex, Edge<Vertex>> mutableGraph = builder
            .addVertices(v1, v2, v3)
            .addEdges(e1, e2)
            .buildMutable();
        
        System.out.println("Mutable graph vertices: " + mutableGraph.getVertices().size());
        
        // Add more elements using mutable operations
        mutableGraph.addVertex(v4);
        mutableGraph.addEdge(e3);
        
        System.out.println("After adding elements: " + mutableGraph.getVertices().size());
        
        // Build a graph with immutable view
        GraphBuilder<Vertex, Edge<Vertex>> builder2 = new GraphBuilder<>();
        GraphBuilder.ImmutableGraphView<Vertex, Edge<Vertex>> immutableGraph = builder2
            .addVertices(v1, v2, v3)
            .addEdges(e1, e2)
            .buildImmutable();
        
        // Use query operations only
        Set<Vertex> vertices = immutableGraph.getVertices();
        List<Vertex> path = immutableGraph.shortestPath(v1, v3);
        
        System.out.println("Immutable graph vertices: " + vertices.size());
        System.out.println("Shortest path: " + path);
        
        // Build with different representations
        GraphBuilder<Vertex, Edge<Vertex>> sparseBuilder = new GraphBuilder<>(); // ALGraph
        GraphBuilder<Vertex, Edge<Vertex>> denseBuilder = new GraphBuilder<>(100); // AMGraph
        
        GraphInterface<Vertex, Edge<Vertex>> sparseGraph = sparseBuilder
            .addVertices(v1, v2, v3)
            .addEdges(e1, e2)
            .build();
            
        GraphInterface<Vertex, Edge<Vertex>> denseGraph = denseBuilder
            .addVertices(v1, v2, v3)
            .addEdges(e1, e2)
            .build();
        
        System.out.println("Sparse graph diameter: " + sparseGraph.diameter());
        System.out.println("Dense graph diameter: " + denseGraph.diameter());
    }
    
    /**
     * Example 3: Using the GraphStrategy pattern
     * Shows how to use different strategies for different use cases.
     */
    public static void strategyPatternUsage() {
        System.out.println("\n=== GraphStrategy Pattern Usage ===");
        
        // Create vertices and edges
        Vertex v1 = new Vertex(1, "Tatooine");
        Vertex v2 = new Vertex(2, "Coruscant");
        Vertex v3 = new Vertex(3, "Naboo");
        Vertex v4 = new Vertex(4, "Kamino");
        
        Edge<Vertex> e1 = new Edge<>(v1, v2, 8);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 6);
        Edge<Vertex> e3 = new Edge<>(v3, v4, 9);
        
        // Strategy 1: Use adjacency list for sparse graphs (good for space exploration)
        GraphInterface<Vertex, Edge<Vertex>> sparseGraph = new ALGraph<>();
        GraphStrategy.MutableView<Vertex, Edge<Vertex>> sparseMutable = 
            new GraphStrategy.MutableView<>(sparseGraph);
        GraphStrategy.ImmutableView<Vertex, Edge<Vertex>> sparseImmutable = 
            new GraphStrategy.ImmutableView<>(sparseGraph);
        
        // Strategy 2: Use adjacency matrix for dense graphs (good for city planning)
        GraphInterface<Vertex, Edge<Vertex>> denseGraph = new AMGraph<>(100);
        GraphStrategy.MutableView<Vertex, Edge<Vertex>> denseMutable = 
            new GraphStrategy.MutableView<>(denseGraph);
        GraphStrategy.ImmutableView<Vertex, Edge<Vertex>> denseImmutable = 
            new GraphStrategy.ImmutableView<>(denseGraph);
        
        // Build sparse graph (space routes)
        sparseMutable.addVertex(v1);
        sparseMutable.addVertex(v2);
        sparseMutable.addVertex(v3);
        sparseMutable.addEdge(e1);
        sparseMutable.addEdge(e2);
        
        // Build dense graph (city connections)
        denseMutable.addVertex(v1);
        denseMutable.addVertex(v2);
        denseMutable.addVertex(v3);
        denseMutable.addVertex(v4);
        denseMutable.addEdge(e1);
        denseMutable.addEdge(e2);
        denseMutable.addEdge(e3);
        
        // Use immutable views for analysis
        System.out.println("Sparse graph (space routes):");
        System.out.println("  Vertices: " + sparseImmutable.getVertices().size());
        System.out.println("  Edges: " + sparseImmutable.getEdges().size());
        System.out.println("  Diameter: " + sparseImmutable.diameter());
        System.out.println("  Center: " + sparseImmutable.getCenter());
        
        System.out.println("Dense graph (city connections):");
        System.out.println("  Vertices: " + denseImmutable.getVertices().size());
        System.out.println("  Edges: " + denseImmutable.getEdges().size());
        System.out.println("  Diameter: " + denseImmutable.diameter());
        System.out.println("  Center: " + denseImmutable.getCenter());
        
        // Both strategies provide the same interface!
        List<Vertex> sparsePath = sparseImmutable.shortestPath(v1, v3);
        List<Vertex> densePath = denseImmutable.shortestPath(v1, v3);
        
        System.out.println("Sparse path: " + sparsePath);
        System.out.println("Dense path: " + densePath);
    }
    
    /**
     * Example 4: Game integration example
     * Shows how the graph can be used in a game context.
     */
    public static void gameIntegrationExample() {
        System.out.println("\n=== Game Integration Example ===");
        
        // Create a game world with planets
        GraphInterface<Vertex, Edge<Vertex>> gameWorld = new Graph<>();
        
        // Add planets
        Vertex tatooine = new Vertex(1, "Tatooine");
        Vertex coruscant = new Vertex(2, "Coruscant");
        Vertex naboo = new Vertex(3, "Naboo");
        Vertex kamino = new Vertex(4, "Kamino");
        Vertex geonosis = new Vertex(5, "Geonosis");
        
        gameWorld.addVertex(tatooine);
        gameWorld.addVertex(coruscant);
        gameWorld.addVertex(naboo);
        gameWorld.addVertex(kamino);
        gameWorld.addVertex(geonosis);
        
        // Add space routes
        Edge<Vertex> route1 = new Edge<>(tatooine, coruscant, 10);
        Edge<Vertex> route2 = new Edge<>(coruscant, naboo, 8);
        Edge<Vertex> route3 = new Edge<>(naboo, kamino, 12);
        Edge<Vertex> route4 = new Edge<>(kamino, geonosis, 15);
        Edge<Vertex> route5 = new Edge<>(tatooine, geonosis, 20);
        
        gameWorld.addEdge(route1);
        gameWorld.addEdge(route2);
        gameWorld.addEdge(route3);
        gameWorld.addEdge(route4);
        gameWorld.addEdge(route5);
        
        // Game logic: Find optimal routes
        System.out.println("Game World Analysis:");
        System.out.println("  Total planets: " + gameWorld.getVertices().size());
        System.out.println("  Total routes: " + gameWorld.getEdges().size());
        System.out.println("  World diameter: " + gameWorld.diameter());
        System.out.println("  Central planet: " + gameWorld.getCenter());
        
        // Find shortest route between two planets
        List<Vertex> route = gameWorld.shortestPath(tatooine, kamino);
        int routeLength = gameWorld.pathLength(route);
        
        System.out.println("Shortest route from Tatooine to Kamino:");
        System.out.println("  Route: " + route);
        System.out.println("  Distance: " + routeLength + " parsecs");
        
        // Find all planets within range of a starting point
        Map<Vertex, Edge<Vertex>> nearbyPlanets = gameWorld.getNeighbors(tatooine, 2);
        System.out.println("Planets within 2 jumps of Tatooine: " + nearbyPlanets.keySet());
    }
    
    /**
     * Example 5: Performance comparison
     * Shows how different implementations perform with different graph types.
     */
    public static void performanceComparison() {
        System.out.println("\n=== Performance Comparison ===");
        
        // Create large sparse graph (many vertices, few edges)
        GraphInterface<Vertex, Edge<Vertex>> sparseGraph = new ALGraph<>();
        GraphInterface<Vertex, Edge<Vertex>> sparseGraphMatrix = new AMGraph<>(1000);
        
        // Create large dense graph (many vertices, many edges)
        GraphInterface<Vertex, Edge<Vertex>> denseGraph = new ALGraph<>();
        GraphInterface<Vertex, Edge<Vertex>> denseGraphMatrix = new AMGraph<>(1000);
        
        // Add vertices
        for (int i = 1; i <= 100; i++) {
            Vertex v = new Vertex(i, "Planet" + i);
            sparseGraph.addVertex(v);
            sparseGraphMatrix.addVertex(v);
            denseGraph.addVertex(v);
            denseGraphMatrix.addVertex(v);
        }
        
        // Add edges to sparse graph (only a few edges)
        for (int i = 1; i <= 10; i++) {
            Vertex v1 = new Vertex(i, "Planet" + i);
            Vertex v2 = new Vertex(i + 1, "Planet" + (i + 1));
            Edge<Vertex> e = new Edge<>(v1, v2, i);
            sparseGraph.addEdge(e);
            sparseGraphMatrix.addEdge(e);
        }
        
        // Add edges to dense graph (many edges)
        for (int i = 1; i <= 50; i++) {
            for (int j = i + 1; j <= Math.min(i + 10, 100); j++) {
                Vertex v1 = new Vertex(i, "Planet" + i);
                Vertex v2 = new Vertex(j, "Planet" + j);
                Edge<Vertex> e = new Edge<>(v1, v2, i + j);
                denseGraph.addEdge(e);
                denseGraphMatrix.addEdge(e);
            }
        }
        
        System.out.println("Sparse Graph (ALGraph):");
        System.out.println("  Vertices: " + sparseGraph.getVertices().size());
        System.out.println("  Edges: " + sparseGraph.getEdges().size());
        
        System.out.println("Sparse Graph (AMGraph):");
        System.out.println("  Vertices: " + sparseGraphMatrix.getVertices().size());
        System.out.println("  Edges: " + sparseGraphMatrix.getEdges().size());
        
        System.out.println("Dense Graph (ALGraph):");
        System.out.println("  Vertices: " + denseGraph.getVertices().size());
        System.out.println("  Edges: " + denseGraph.getEdges().size());
        
        System.out.println("Dense Graph (AMGraph):");
        System.out.println("  Vertices: " + denseGraphMatrix.getVertices().size());
        System.out.println("  Edges: " + denseGraphMatrix.getEdges().size());
    }
    
    /**
     * Main method to run all examples
     */
    public static void main(String[] args) {
        System.out.println("Graph Pattern Examples");
        System.out.println("=====================");
        
        basicGraphUsage();
        builderPatternUsage();
        strategyPatternUsage();
        gameIntegrationExample();
        performanceComparison();
        
        System.out.println("\n=== All Examples Completed ===");
    }
} 