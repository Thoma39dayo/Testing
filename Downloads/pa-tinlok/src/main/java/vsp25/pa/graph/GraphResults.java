package vsp25.pa.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Java 17 Records for graph operation results.
 * These provide immutable, concise data classes for returning results from graph operations.
 */
public class GraphResults {
    
    /**
     * Record for path-related results
     */
    public record PathResult(List<Vertex> path, int length) {
        public PathResult {
            if (path == null) path = List.of();
            if (length < 0) length = -1;
        }
    }
    
    /**
     * Record for neighbor-related results
     */
    public record NeighborResult(Map<Vertex, Edge<Vertex>> neighbors, int count) {
        public NeighborResult {
            if (neighbors == null) neighbors = Map.of();
            count = neighbors.size();
        }
    }
    
    /**
     * Record for graph statistics
     */
    public record GraphStats(int vertexCount, int edgeCount, int totalLength) {
        public GraphStats {
            if (vertexCount < 0) vertexCount = 0;
            if (edgeCount < 0) edgeCount = 0;
            if (totalLength < 0) totalLength = 0;
        }
    }
    
    /**
     * Record for edge information
     */
    public record EdgeInfo(Vertex v1, Vertex v2, int length, boolean exists) {
        public EdgeInfo {
            if (v1 == null || v2 == null) {
                exists = false;
                length = -1;
            }
        }
    }
    
    /**
     * Record for vertex information
     */
    public record VertexInfo(Vertex vertex, int degree, boolean exists) {
        public VertexInfo {
            if (vertex == null) {
                exists = false;
                degree = 0;
            }
        }
    }
} 