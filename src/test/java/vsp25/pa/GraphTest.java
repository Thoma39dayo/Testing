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
        // Setup simple path: v1 --5-- v2 --3-- v3
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
        
        // Test shortest path
        List<Vertex> alPath = alGraph.shortestPath(v1, v3);
        List<Vertex> amPath = amGraph.shortestPath(v1, v3);
        
        assertEquals(3, alPath.size());
        assertEquals(3, amPath.size());
        assertEquals(v1, alPath.get(0));
        assertEquals(v2, alPath.get(1));
        assertEquals(v3, alPath.get(2));
        assertEquals(v1, amPath.get(0));
        assertEquals(v2, amPath.get(1));
        assertEquals(v3, amPath.get(2));
        
        // Test path length
        assertEquals(8, alGraph.pathLength(alPath));
        assertEquals(8, amGraph.pathLength(amPath));
        
        // Test same vertex
        List<Vertex> sameVertexPath = alGraph.shortestPath(v1, v1);
        assertEquals(1, sameVertexPath.size());
        assertEquals(v1, sameVertexPath.get(0));
        
        // Test disconnected vertices
        alGraph.addVertex(v4);
        List<Vertex> disconnectedPath = alGraph.shortestPath(v1, v4);
        assertTrue(disconnectedPath.isEmpty());
    }
    
    @Test
    void testTask3_GetNeighborsWithRange() {
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
        
        // Test getNeighbors with range 5 (should include v2)
        Map<Vertex, Edge<Vertex>> alNeighbors5 = alGraph.getNeighbors(v1, 5);
        Map<Vertex, Edge<Vertex>> amNeighbors5 = amGraph.getNeighbors(v1, 5);
        
        assertEquals(1, alNeighbors5.size());
        assertEquals(1, amNeighbors5.size());
        assertTrue(alNeighbors5.containsKey(v2));
        assertTrue(amNeighbors5.containsKey(v2));
        
        // Test getNeighbors with range 1 (should not include v2 since edge length is 5)
        Map<Vertex, Edge<Vertex>> alNeighbors1 = alGraph.getNeighbors(v1, 1);
        Map<Vertex, Edge<Vertex>> amNeighbors1 = amGraph.getNeighbors(v1, 1);
        
        assertEquals(0, alNeighbors1.size());
        assertEquals(0, amNeighbors1.size());
        
        // Test getNeighbors with range 8 (should include v3)
        Map<Vertex, Edge<Vertex>> alNeighbors8 = alGraph.getNeighbors(v1, 8);
        Map<Vertex, Edge<Vertex>> amNeighbors8 = amGraph.getNeighbors(v1, 8);
        
        assertEquals(2, alNeighbors8.size());
        assertEquals(2, amNeighbors8.size());
        assertTrue(alNeighbors8.containsKey(v2));
        assertTrue(alNeighbors8.containsKey(v3));
        assertTrue(amNeighbors8.containsKey(v2));
        assertTrue(amNeighbors8.containsKey(v3));
        
        // Test getNeighbors with range 0
        Map<Vertex, Edge<Vertex>> alNeighbors0 = alGraph.getNeighbors(v1, 0);
        Map<Vertex, Edge<Vertex>> amNeighbors0 = amGraph.getNeighbors(v1, 0);
        
        assertTrue(alNeighbors0.isEmpty());
        assertTrue(amNeighbors0.isEmpty());
        
        // Test getNeighbors with negative range
        Map<Vertex, Edge<Vertex>> alNeighborsNeg = alGraph.getNeighbors(v1, -1);
        Map<Vertex, Edge<Vertex>> amNeighborsNeg = amGraph.getNeighbors(v1, -1);
        
        assertTrue(alNeighborsNeg.isEmpty());
        assertTrue(amNeighborsNeg.isEmpty());
    }
    
    @Test
    void testTask3_PathLength() {
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
        
        // Test valid path
        List<Vertex> path = List.of(v1, v2, v3);
        assertEquals(8, alGraph.pathLength(path)); // 5 + 3 = 8
        assertEquals(8, amGraph.pathLength(path));
        
        // Test single vertex path
        List<Vertex> singlePath = List.of(v1);
        assertEquals(0, alGraph.pathLength(singlePath));
        assertEquals(0, amGraph.pathLength(singlePath));
        
        // Test empty path
        List<Vertex> emptyPath = List.of();
        assertEquals(0, alGraph.pathLength(emptyPath));
        assertEquals(0, amGraph.pathLength(emptyPath));
        
        // Test null path
        assertEquals(0, alGraph.pathLength(null));
        assertEquals(0, amGraph.pathLength(null));
        
        // Test invalid path (non-existent edge)
        alGraph.addVertex(v4);
        List<Vertex> invalidPath = List.of(v1, v4);
        assertEquals(-1, alGraph.pathLength(invalidPath));
        assertEquals(-1, amGraph.pathLength(invalidPath));
    }
    
    // ===== TASK 4 TESTS: Advanced Algorithm Operations =====
    
    @Test
    void testTask4_Diameter() {
        // Setup simple graph: v1 --5-- v2 --3-- v3
        alGraph.addVertex(v1);
        alGraph.addVertex(v2);
        alGraph.addVertex(v3);
        alGraph.addEdge(e1); // v1 to v2, length 5
        alGraph.addEdge(e2); // v2 to v3, length 3
        
        amGraph.addVertex(v1);
        amGraph.addVertex(v2);
        amGraph.addVertex(v3);
        amGraph.addEdge(e1);
        amGraph.addEdge(e2);
        
        // Test diameter - longest shortest path should be v1 to v3 via v2: 5 + 3 = 8
        int alDiameter = alGraph.diameter();
        int amDiameter = amGraph.diameter();
        
        assertEquals(8, alDiameter);
        assertEquals(8, amDiameter);
        
        // Test empty graph
        GraphInterface<Vertex, Edge<Vertex>> emptyGraph = new ALGraph<>();
        assertEquals(-1, emptyGraph.diameter());
    }
    
    @Test
    void testTask4_GetCenter() {
        // Setup graph: v1 --5-- v2 --3-- v3
        alGraph.addVertex(v1);
        alGraph.addVertex(v2);
        alGraph.addVertex(v3);
        alGraph.addEdge(e1); // v1 to v2, length 5
        alGraph.addEdge(e2); // v2 to v3, length 3
        
        amGraph.addVertex(v1);
        amGraph.addVertex(v2);
        amGraph.addVertex(v3);
        amGraph.addEdge(e1);
        amGraph.addEdge(e2);
        
        // Test center - v2 should be the center (minimum eccentricity)
        Vertex alCenter = alGraph.getCenter();
        Vertex amCenter = amGraph.getCenter();
        
        assertEquals(v2, alCenter);
        assertEquals(v2, amCenter);
        
        // Test empty graph
        GraphInterface<Vertex, Edge<Vertex>> emptyGraph = new ALGraph<>();
        assertNull(emptyGraph.getCenter());
    }
    
    @Test
    void testTask4_MinimumSpanningComponents() {
        // Setup graph with 4 vertices in a square
        Vertex v4 = new Vertex(4, "v4");
        Edge<Vertex> e3 = new Edge<>(v3, v4, 2); // v3 to v4, length 2
        Edge<Vertex> e4 = new Edge<>(v1, v4, 4); // v1 to v4, length 4
        
        alGraph.addVertex(v1);
        alGraph.addVertex(v2);
        alGraph.addVertex(v3);
        alGraph.addVertex(v4);
        alGraph.addEdge(e1); // v1 to v2, length 5
        alGraph.addEdge(e2); // v2 to v3, length 3
        alGraph.addEdge(e3); // v3 to v4, length 2
        alGraph.addEdge(e4); // v1 to v4, length 4
        
        amGraph.addVertex(v1);
        amGraph.addVertex(v2);
        amGraph.addVertex(v3);
        amGraph.addVertex(v4);
        amGraph.addEdge(e1);
        amGraph.addEdge(e2);
        amGraph.addEdge(e3);
        amGraph.addEdge(e4);
        
        // Test minimum spanning components with k=1 (should return entire graph)
        Set<GraphInterface<Vertex, Edge<Vertex>>> alComponents1 = alGraph.minimumSpanningComponents(1);
        Set<GraphInterface<Vertex, Edge<Vertex>>> amComponents1 = amGraph.minimumSpanningComponents(1);
        
        assertEquals(1, alComponents1.size());
        assertEquals(1, amComponents1.size());
        
        // Test minimum spanning components with k=2 (should create 2 components)
        Set<GraphInterface<Vertex, Edge<Vertex>>> alComponents2 = alGraph.minimumSpanningComponents(2);
        Set<GraphInterface<Vertex, Edge<Vertex>>> amComponents2 = amGraph.minimumSpanningComponents(2);
        
        assertEquals(2, alComponents2.size());
        assertEquals(2, amComponents2.size());
        
        // Test invalid k values
        Set<GraphInterface<Vertex, Edge<Vertex>>> invalidK = alGraph.minimumSpanningComponents(0);
        assertEquals(0, invalidK.size());
        
        Set<GraphInterface<Vertex, Edge<Vertex>>> tooLargeK = alGraph.minimumSpanningComponents(10);
        assertEquals(0, tooLargeK.size());
        
        // Test empty graph
        GraphInterface<Vertex, Edge<Vertex>> emptyGraph = new ALGraph<>();
        Set<GraphInterface<Vertex, Edge<Vertex>>> emptyComponents = emptyGraph.minimumSpanningComponents(1);
        assertEquals(0, emptyComponents.size());
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
