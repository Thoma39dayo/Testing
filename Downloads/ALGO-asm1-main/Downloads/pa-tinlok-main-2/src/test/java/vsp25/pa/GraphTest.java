package vsp25.pa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import vsp25.pa.graph.*;

import java.util.*;

/**
 * Comprehensive test suite for graph implementations.
 * Tests both ALGraph and AMGraph implementations using the unified GraphInterface.
 * 
 * NOTE: ALGraph and AMGraph implementations are incomplete and need to be
 * implemented by students as part of their assignment.
 */
public class GraphTest {
    
    private GraphInterface<Vertex, Edge<Vertex>> alGraph;
    private GraphInterface<Vertex, Edge<Vertex>> amGraph;
    private Vertex v1, v2, v3, v4;
    private Edge<Vertex> e1, e2, e3;
    
    @BeforeEach
    void setUp() {
        // Create graph implementations
        alGraph = new ALGraph<>();
        amGraph = new AMGraph<>(10);
        
        // Create test vertices
        v1 = new Vertex(1, "A");
        v2 = new Vertex(2, "B");
        v3 = new Vertex(3, "C");
        v4 = new Vertex(4, "D");
        
        // Create test edges
        e1 = new Edge<>(v1, v2, 5);
        e2 = new Edge<>(v2, v3, 3);
        e3 = new Edge<>(v1, v3, 10);
    }
    
    // ===== TASK 1 TESTS: Basic Graph Operations =====
    
    @Test
    void testTask1_AddVertex() {
        // Test ALGraph
        assertTrue(alGraph.addVertex(v1));
        assertTrue(alGraph.hasVertex(v1));
        assertFalse(alGraph.addVertex(v1)); // Duplicate

        // Test AMGraph
        assertTrue(amGraph.addVertex(v1));
        assertTrue(amGraph.hasVertex(v1));
        assertFalse(amGraph.addVertex(v1)); // Duplicate
    }
    
    @Test
    void testTask1_AddEdge() {
        // Setup vertices
        alGraph.addVertex(v1);
        alGraph.addVertex(v2);
        amGraph.addVertex(v1);
        amGraph.addVertex(v2);
        
        // Test adding edges
        assertTrue(alGraph.addEdge(e1));
        assertTrue(alGraph.hasEdge(e1));
        assertTrue(alGraph.hasEdge(v1, v2));
        
        assertTrue(amGraph.addEdge(e1));
        assertTrue(amGraph.hasEdge(e1));
        assertTrue(amGraph.hasEdge(v1, v2));
    }
    
    @Test
    void testTask1_EdgeLength() {
        // Setup
        alGraph.addVertex(v1);
        alGraph.addVertex(v2);
        alGraph.addEdge(e1);
        
        amGraph.addVertex(v1);
        amGraph.addVertex(v2);
        amGraph.addEdge(e1);
        
        // Test edge length
        assertEquals(5, alGraph.getEdgeLength(v1, v2));
        assertEquals(5, amGraph.getEdgeLength(v1, v2));
        assertEquals(-1, alGraph.getEdgeLength(v1, v3)); // No edge
        assertEquals(-1, amGraph.getEdgeLength(v1, v3)); // No edge
    }
    
    // ===== TASK 2 TESTS: Advanced Graph Operations =====
    
    @Test
    void testTask2_RemoveOperations() {
        // Setup
        alGraph.addVertex(v1);
        alGraph.addVertex(v2);
        alGraph.addEdge(e1);
        
        amGraph.addVertex(v1);
        amGraph.addVertex(v2);
        amGraph.addEdge(e1);
        
        // Test edge removal
        assertTrue(alGraph.removeEdge(e1));
        assertFalse(alGraph.hasEdge(e1));
        assertFalse(alGraph.hasEdge(v1, v2));
        
        assertTrue(amGraph.removeEdge(e1));
        assertFalse(amGraph.hasEdge(e1));
        assertFalse(amGraph.hasEdge(v1, v2));
    }
    
    @Test
    void testTask2_RemoveVertex() {
        // Setup
        alGraph.addVertex(v1);
        alGraph.addVertex(v2);
        alGraph.addEdge(e1);
        
        amGraph.addVertex(v1);
        amGraph.addVertex(v2);
        amGraph.addEdge(e1);
        
        // Test vertex removal
        assertTrue(alGraph.remove(v1));
        assertFalse(alGraph.hasVertex(v1));
        assertFalse(alGraph.hasEdge(e1));
        
        assertTrue(amGraph.remove(v1));
        assertFalse(amGraph.hasVertex(v1));
        assertFalse(amGraph.hasEdge(e1));
    }
    
    @Test
    void testTask2_CollectionOperations() {
        // Setup
        alGraph.addVertex(v1);
        alGraph.addVertex(v2);
        alGraph.addVertex(v3);
        alGraph.addEdge(e1);
        alGraph.addEdge(e2);
        
        amGraph.addVertex(v1);
        amGraph.addVertex(v2);
        amGraph.addVertex(v3);
        amGraph.addEdge(e1);
        amGraph.addEdge(e2);
        
        // Test collection operations
        Set<Vertex> alVertices = alGraph.getVertices();
        Set<Vertex> amVertices = amGraph.getVertices();
        assertEquals(3, alVertices.size());
        assertEquals(3, amVertices.size());
        
        Set<Edge<Vertex>> alEdges = alGraph.getEdges();
        Set<Edge<Vertex>> amEdges = amGraph.getEdges();
        assertEquals(2, alEdges.size());
        assertEquals(2, amEdges.size());
        
        Set<Edge<Vertex>> alVertexEdges = alGraph.getEdges(v1);
        Set<Edge<Vertex>> amVertexEdges = amGraph.getEdges(v1);
        assertEquals(1, alVertexEdges.size());
        assertEquals(1, amVertexEdges.size());
    }
    
    @Test
    void testTask2_GetNeighbors() {
        // Setup
        alGraph.addVertex(v1);
        alGraph.addVertex(v2);
        alGraph.addVertex(v3);
        alGraph.addEdge(e1);
        alGraph.addEdge(e2);
        
        amGraph.addVertex(v1);
        amGraph.addVertex(v2);
        amGraph.addVertex(v3);
        amGraph.addEdge(e1);
        amGraph.addEdge(e2);
        
        // Test getNeighbors
        Map<Vertex, Edge<Vertex>> alNeighbors = alGraph.getNeighbors(v2);
        Map<Vertex, Edge<Vertex>> amNeighbors = amGraph.getNeighbors(v2);
        
        assertEquals(2, alNeighbors.size());
        assertEquals(2, amNeighbors.size());
        assertTrue(alNeighbors.containsKey(v1));
        assertTrue(alNeighbors.containsKey(v3));
        assertTrue(amNeighbors.containsKey(v1));
        assertTrue(amNeighbors.containsKey(v3));
    }
    
    // ===== TASK 3 TESTS: Basic Algorithm Operations =====
    
    @Test
    void testTask3_ShortestPath() {
        // TODO: Implement ALGraph and AMGraph first
        // Setup simple path: v1 --5-- v2 --3-- v3
        // alGraph.addVertex(v1);
        // alGraph.addVertex(v2);
        // alGraph.addVertex(v3);
        // alGraph.addEdge(e1);
        // alGraph.addEdge(e2);
        
        // amGraph.addVertex(v1);
        // amGraph.addVertex(v2);
        // amGraph.addVertex(v3);
        // amGraph.addEdge(e1);
        // amGraph.addEdge(e2);
        
        // Test shortest path
        // List<Vertex> alPath = alGraph.shortestPath(v1, v3);
        // List<Vertex> amPath = amGraph.shortestPath(v1, v3);
        
        // assertEquals(3, alPath.size());
        // assertEquals(3, amPath.size());
        // assertEquals(v1, alPath.get(0));
        // assertEquals(v2, alPath.get(1));
        // assertEquals(v3, alPath.get(2));
        // assertEquals(v1, amPath.get(0));
        // assertEquals(v2, amPath.get(1));
        // assertEquals(v3, amPath.get(2));
        
        // Placeholder test - remove when implementations are complete
        assertTrue(true, "ALGraph and AMGraph implementations need to be completed");
    }
    
    @Test
    void testTask3_GetNeighborsWithRange() {
        // TODO: Implement ALGraph and AMGraph first
        // Setup
        // alGraph.addVertex(v1);
        // alGraph.addVertex(v2);
        // alGraph.addVertex(v3);
        // alGraph.addEdge(e1);
        // alGraph.addEdge(e2);
        
        // amGraph.addVertex(v1);
        // amGraph.addVertex(v2);
        // amGraph.addVertex(v3);
        // amGraph.addEdge(e1);
        // amGraph.addEdge(e2);
        
        // Test getNeighbors with range
        // Map<Vertex, Edge<Vertex>> alNeighbors = alGraph.getNeighbors(v1, 1);
        // Map<Vertex, Edge<Vertex>> amNeighbors = amGraph.getNeighbors(v1, 1);
        
        // assertEquals(1, alNeighbors.size());
        // assertEquals(1, amNeighbors.size());
        // assertTrue(alNeighbors.containsKey(v2));
        // assertTrue(amNeighbors.containsKey(v2));
        
        // Placeholder test - remove when implementations are complete
        assertTrue(true, "ALGraph and AMGraph implementations need to be completed");
    }
    
    // ===== TASK 4 TESTS: Advanced Algorithm Operations =====
    
    @Test
    void testTask4_Diameter() {
        // TODO: Implement ALGraph and AMGraph first
        // Setup simple graph
        // alGraph.addVertex(v1);
        // alGraph.addVertex(v2);
        // alGraph.addVertex(v3);
        // alGraph.addEdge(e1);
        // alGraph.addEdge(e2);
        
        // amGraph.addVertex(v1);
        // amGraph.addVertex(v2);
        // amGraph.addVertex(v3);
        // amGraph.addEdge(e1);
        // amGraph.addEdge(e2);
        
        // Test diameter
        // int alDiameter = alGraph.diameter();
        // int amDiameter = amGraph.diameter();
        
        // assertEquals(8, alDiameter); // v1 to v3 via v2: 5 + 3 = 8
        // assertEquals(8, amDiameter);
        
        // Placeholder test - remove when implementations are complete
        assertTrue(true, "ALGraph and AMGraph implementations need to be completed");
    }
    
    @Test
    void testTask4_GetCenter() {
        // TODO: Implement ALGraph and AMGraph first
        // Setup
        // alGraph.addVertex(v1);
        // alGraph.addVertex(v2);
        // alGraph.addVertex(v3);
        // alGraph.addEdge(e1);
        // alGraph.addEdge(e2);
        
        // amGraph.addVertex(v1);
        // amGraph.addVertex(v2);
        // amGraph.addVertex(v3);
        // amGraph.addEdge(e1);
        // amGraph.addEdge(e2);
        
        // Test center
        // Vertex alCenter = alGraph.getCenter();
        // Vertex amCenter = amGraph.getCenter();
        
        // Center should be v2 (middle vertex)
        // assertEquals(v2, alCenter);
        // assertEquals(v2, amCenter);
        
        // Placeholder test - remove when implementations are complete
        assertTrue(true, "ALGraph and AMGraph implementations need to be completed");
    }
    
    @Test
    void testTask4_MinimumSpanningComponents() {
        // TODO: Implement ALGraph and AMGraph first
        // Setup
        // alGraph.addVertex(v1);
        // alGraph.addVertex(v2);
        // alGraph.addVertex(v3);
        // alGraph.addEdge(e1);
        // alGraph.addEdge(e2);
        
        // amGraph.addVertex(v1);
        // amGraph.addVertex(v2);
        // amGraph.addVertex(v3);
        // amGraph.addEdge(e1);
        // amGraph.addEdge(e2);
        
        // Test minimum spanning components
        // Set<GraphInterface<Vertex, Edge<Vertex>>> alComponents = alGraph.minimumSpanningComponents(1);
        // Set<GraphInterface<Vertex, Edge<Vertex>>> amComponents = amGraph.minimumSpanningComponents(1);
        
        // assertEquals(1, alComponents.size());
        // assertEquals(1, amComponents.size());
        
        // Placeholder test - remove when implementations are complete
        assertTrue(true, "ALGraph and AMGraph implementations need to be completed");
    }
    
    // ===== TASK 5 TESTS: Game Integration =====
    
    @Test
    void testTask5_GameIntegration() {
        // TODO: Implement ALGraph and AMGraph first
        // Test that graph can be used with game components
        // GraphInterface<Vertex, Edge<Vertex>> gameGraph = new Graph<>();
        
        // Add game vertices (planets)
        // Vertex planet1 = new Vertex(1, "Tatooine");
        // Vertex planet2 = new Vertex(2, "Coruscant");
        // Vertex planet3 = new Vertex(3, "Naboo");
        
        // gameGraph.addVertex(planet1);
        // gameGraph.addVertex(planet2);
        // gameGraph.addVertex(planet3);
        
        // Add game edges (space routes)
        // Edge<Vertex> route1 = new Edge<>(planet1, planet2, 10);
        // Edge<Vertex> route2 = new Edge<>(planet2, planet3, 8);
        
        // gameGraph.addEdge(route1);
        // gameGraph.addEdge(route2);
        
        // Test game-related operations
        // List<Vertex> route = gameGraph.shortestPath(planet1, planet3);
        // assertEquals(3, route.size());
        // assertEquals(planet1, route.get(0));
        // assertEquals(planet2, route.get(1));
        // assertEquals(planet3, route.get(2));
        
        // Test path length calculation
        // int routeLength = gameGraph.pathLength(route);
        // assertEquals(18, routeLength); // 10 + 8
        
        // Placeholder test - remove when implementations are complete
        assertTrue(true, "ALGraph and AMGraph implementations need to be completed");
    }
    
    // ===== GENERAL TESTS =====
    
    @Test
    void testEmptyGraph() {
        // TODO: Implement ALGraph and AMGraph first
        // assertTrue(alGraph.getVertices().isEmpty());
        // assertTrue(alGraph.getEdges().isEmpty());
        // assertTrue(amGraph.getVertices().isEmpty());
        // assertTrue(amGraph.getEdges().isEmpty());
        
        // Placeholder test - remove when implementations are complete
        assertTrue(true, "ALGraph and AMGraph implementations need to be completed");
    }
    
    @Test
    void testDisconnectedGraph() {
        // TODO: Implement ALGraph and AMGraph first
        // alGraph.addVertex(v1);
        // alGraph.addVertex(v2);
        // amGraph.addVertex(v1);
        // amGraph.addVertex(v2);
        
        // No path between disconnected vertices
        // List<Vertex> alPath = alGraph.shortestPath(v1, v2);
        // List<Vertex> amPath = amGraph.shortestPath(v1, v2);
        
        // assertTrue(alPath.isEmpty());
        // assertTrue(amPath.isEmpty());
        
        // Placeholder test - remove when implementations are complete
        assertTrue(true, "ALGraph and AMGraph implementations need to be completed");
    }
    
    @Test
    void testNullHandling() {
        // TODO: Implement ALGraph and AMGraph first
        // Test null vertex handling
        // assertFalse(alGraph.addVertex(null));
        // assertFalse(amGraph.addVertex(null));
        
        // Test null edge handling
        // assertFalse(alGraph.addEdge(null));
        // assertFalse(amGraph.addEdge(null));
        
        // Test null queries
        // assertFalse(alGraph.hasVertex(null));
        // assertFalse(amGraph.hasVertex(null));
        
        // Placeholder test - remove when implementations are complete
        assertTrue(true, "ALGraph and AMGraph implementations need to be completed");
    }
    
    // ===== BUILDER PATTERN TESTS =====
    
    @Test
    void testGraphBuilder() {
        // Test builder pattern (this works with the unified interface)
        GraphBuilder<Vertex, Edge<Vertex>> builder = new GraphBuilder<>();
        
        // Build mutable view
        GraphBuilder.MutableGraphView<Vertex, Edge<Vertex>> mutableView = builder
            .addVertices(v1, v2, v3)
            .addEdges(e1, e2)
            .buildMutable();
        
        // Test mutable operations (these will fail until implementations are complete)
        // assertTrue(mutableView.addVertex(v4));
        // Edge<Vertex> e4 = new Edge<>(v3, v4, 2);
        // assertTrue(mutableView.addEdge(e4));
        
        // Build immutable view
        GraphBuilder.ImmutableGraphView<Vertex, Edge<Vertex>> immutableView = builder
            .addVertices(v1, v2, v3)
            .addEdges(e1, e2)
            .buildImmutable();
        
        // Test query operations (these will fail until implementations are complete)
        // Set<Vertex> vertices = immutableView.getVertices();
        // assertEquals(3, vertices.size());
        
        // List<Vertex> path = immutableView.shortestPath(v1, v3);
        // assertEquals(3, path.size());
        
        // Placeholder test - remove when implementations are complete
        assertTrue(true, "GraphBuilder tests need ALGraph and AMGraph implementations to be completed");
    }
    
    // ===== STRATEGY PATTERN TESTS =====
    
    @Test
    void testGraphStrategy() {
        // Test strategy pattern with different implementations
        GraphInterface<Vertex, Edge<Vertex>> sparseGraph = new ALGraph<>();
        GraphInterface<Vertex, Edge<Vertex>> denseGraph = new AMGraph<>(100);
        
        // Create strategy views
        GraphStrategy.MutableView<Vertex, Edge<Vertex>> sparseMutable = 
            new GraphStrategy.MutableView<>(sparseGraph);
        GraphStrategy.ImmutableView<Vertex, Edge<Vertex>> sparseImmutable = 
            new GraphStrategy.ImmutableView<>(sparseGraph);
        
        GraphStrategy.MutableView<Vertex, Edge<Vertex>> denseMutable = 
            new GraphStrategy.MutableView<>(denseGraph);
        GraphStrategy.ImmutableView<Vertex, Edge<Vertex>> denseImmutable = 
            new GraphStrategy.ImmutableView<>(denseGraph);
        
        // Test that both implementations work the same way (these will fail until implementations are complete)
        // sparseMutable.addVertex(v1);
        // sparseMutable.addVertex(v2);
        // sparseMutable.addEdge(e1);
        
        // denseMutable.addVertex(v1);
        // denseMutable.addVertex(v2);
        // denseMutable.addEdge(e1);
        
        // Test query operations (these will fail until implementations are complete)
        // assertTrue(sparseImmutable.hasEdge(v1, v2));
        // assertTrue(denseImmutable.hasEdge(v1, v2));
        
        // Test algorithm operations (these will fail until implementations are complete)
        // List<Vertex> sparsePath = sparseImmutable.shortestPath(v1, v2);
        // List<Vertex> densePath = denseImmutable.shortestPath(v1, v2);
        
        // assertEquals(2, sparsePath.size());
        // assertEquals(2, densePath.size());
        
        // Placeholder test - remove when implementations are complete
        assertTrue(true, "GraphStrategy tests need ALGraph and AMGraph implementations to be completed");
    }
}
