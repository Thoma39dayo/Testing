package vsp25.pa.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Performance benchmarking class for graph algorithms.
 * Measures runtime complexity of Task 1 and Task 2 methods for both ALGraph and AMGraph.
 * Generates performance charts using JFreeChart.
 */
public class GraphPerformanceBenchmark {
    
    private static final int WARMUP_ITERATIONS = 1000;
    private static final int MEASUREMENT_ITERATIONS = 10000;
    private static final int[] GRAPH_SIZES = {10, 50, 100, 200, 500, 1000, 2000};
    
    public static void main(String[] args) {
        var alTask1Results = new HashMap<String, Map<Integer, Double>>();
        var amTask1Results = new HashMap<String, Map<Integer, Double>>();
        var alTask2Results = new HashMap<String, Map<Integer, Double>>();
        var amTask2Results = new HashMap<String, Map<Integer, Double>>();
        
        benchmarkTask1Methods(alTask1Results, amTask1Results);
        benchmarkTask2Methods(alTask2Results, amTask2Results);
        generateSummaryChart(alTask1Results, amTask1Results, alTask2Results, amTask2Results);
        organizeCharts();
    }
    
    /**
     * Benchmark Task 1 methods: addVertex, addEdge, hasVertex, hasEdge, getEdge, getEdgeLength
     */
    private static void benchmarkTask1Methods(Map<String, Map<Integer, Double>> alResults, 
                                            Map<String, Map<Integer, Double>> amResults) {
        System.out.println("\n=== Task 1 Methods Benchmark ===");
        
        // Initialize result maps
        String[] task1Methods = {"addVertex", "addEdge", "hasVertex", "hasEdge", "getEdge", "getEdgeLength"};
        for (String method : task1Methods) {
            alResults.put(method, new HashMap<>());
            amResults.put(method, new HashMap<>());
        }
        
        // Benchmark each graph size
        for (int size : GRAPH_SIZES) {
            System.out.println("Testing graph size: " + size);
            
            // Create test graphs
            ALGraph<Vertex, Edge<Vertex>> alGraph = new ALGraph<>();
            AMGraph<Vertex, Edge<Vertex>> amGraph = new AMGraph<>();
            
            // Pre-populate graphs with vertices
            List<Vertex> vertices = createVertices(size);
            for (Vertex v : vertices) {
                alGraph.addVertex(v);
                amGraph.addVertex(v);
            }
            
            // Create some edges for testing
            List<Edge<Vertex>> edges = createEdges(vertices, size / 2);
            for (Edge<Vertex> e : edges) {
                alGraph.addEdge(e);
                amGraph.addEdge(e);
            }
            
            // Benchmark each method
            for (String method : task1Methods) {
                double alTime = benchmarkMethod(alGraph, method, vertices, edges);
                double amTime = benchmarkMethod(amGraph, method, vertices, edges);
                
                alResults.get(method).put(size, alTime);
                amResults.get(method).put(size, amTime);
            }
        }
        
        // Generate charts for Task 1
        generateTask1Charts(alResults, amResults);
    }
    
    /**
     * Benchmark Task 2 methods: getTotalEdgeLength, removeEdge, remove, getVertices, getEdges, getNeighbors
     */
    private static void benchmarkTask2Methods(Map<String, Map<Integer, Double>> alResults, 
                                            Map<String, Map<Integer, Double>> amResults) {
        System.out.println("\n=== Task 2 Methods Benchmark ===");
        
        // Initialize result maps
        String[] task2Methods = {"getTotalEdgeLength", "removeEdge", "remove", "getVertices", "getEdges", "getNeighbors"};
        for (String method : task2Methods) {
            alResults.put(method, new HashMap<>());
            amResults.put(method, new HashMap<>());
        }
        
        // Benchmark each graph size
        for (int size : GRAPH_SIZES) {
            System.out.println("Testing graph size: " + size);
            
            // Create test graphs
            ALGraph<Vertex, Edge<Vertex>> alGraph = new ALGraph<>();
            AMGraph<Vertex, Edge<Vertex>> amGraph = new AMGraph<>();
            
            // Pre-populate graphs with vertices and edges
            List<Vertex> vertices = createVertices(size);
            List<Edge<Vertex>> edges = createEdges(vertices, size);
            
            for (Vertex v : vertices) {
                alGraph.addVertex(v);
                amGraph.addVertex(v);
            }
            
            for (Edge<Vertex> e : edges) {
                alGraph.addEdge(e);
                amGraph.addEdge(e);
            }
            
            // Benchmark each method
            for (String method : task2Methods) {
                double alTime = benchmarkMethod(alGraph, method, vertices, edges);
                double amTime = benchmarkMethod(amGraph, method, vertices, edges);
                
                alResults.get(method).put(size, alTime);
                amResults.get(method).put(size, amTime);
            }
        }
        
        // Generate charts for Task 2
        generateTask2Charts(alResults, amResults);
    }
    
    /**
     * Benchmark a specific method on a graph
     */
    private static double benchmarkMethod(GraphInterface<Vertex, Edge<Vertex>> graph, String methodName, 
                                        List<Vertex> vertices, List<Edge<Vertex>> edges) {
        
        // Warmup
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            executeMethod(graph, methodName, vertices, edges);
        }
        
        // Measurement
        long startTime = System.nanoTime();
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            executeMethod(graph, methodName, vertices, edges);
        }
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / (double) MEASUREMENT_ITERATIONS; // Average time per operation in nanoseconds
    }
    
    /**
     * Execute a specific method on the graph
     */
    private static void executeMethod(GraphInterface<Vertex, Edge<Vertex>> graph, String methodName,
                                    List<Vertex> vertices, List<Edge<Vertex>> edges) {
        var random = new Random();
        
        switch (methodName) {
            case "addVertex" -> {
                if (vertices.size() < 1000) { // Limit to avoid memory issues
                    var newVertex = new Vertex(vertices.size() + random.nextInt(1000), 
                        "v" + (vertices.size() + random.nextInt(1000)));
                    graph.addVertex(newVertex);
                }
            }
            
            case "addEdge" -> {
                if (vertices.size() > 1 && edges.size() < vertices.size() * 2) {
                    var v1 = vertices.get(random.nextInt(vertices.size()));
                    var v2 = vertices.get(random.nextInt(vertices.size()));
                    if (!v1.equals(v2)) {
                        var newEdge = new Edge<>(v1, v2, random.nextInt(100) + 1);
                        graph.addEdge(newEdge);
                    }
                }
            }
            
            case "hasVertex" -> {
                var testVertex = vertices.get(random.nextInt(vertices.size()));
                graph.hasVertex(testVertex);
            }
            
            case "hasEdge" -> {
                if (!edges.isEmpty()) {
                    var testEdge = edges.get(random.nextInt(edges.size()));
                    graph.hasEdge(testEdge);
                }
            }
            
            case "getEdge" -> {
                if (vertices.size() > 1) {
                    var v1 = vertices.get(random.nextInt(vertices.size()));
                    var v2 = vertices.get(random.nextInt(vertices.size()));
                    graph.getEdge(v1, v2);
                }
            }
            
            case "getEdgeLength" -> {
                if (vertices.size() > 1) {
                    var v1 = vertices.get(random.nextInt(vertices.size()));
                    var v2 = vertices.get(random.nextInt(vertices.size()));
                    graph.getEdgeLength(v1, v2);
                }
            }
            
            case "getTotalEdgeLength" -> graph.getTotalEdgeLength();
            
            case "removeEdge" -> {
                if (!edges.isEmpty()) {
                    var edgeToRemove = edges.get(random.nextInt(edges.size()));
                    graph.removeEdge(edgeToRemove);
                    // Re-add the edge to maintain graph structure
                    graph.addEdge(edgeToRemove);
                }
            }
            
            case "remove" -> {
                if (vertices.size() > 1) {
                    var vertexToRemove = vertices.get(random.nextInt(vertices.size()));
                    graph.remove(vertexToRemove);
                    // Re-add the vertex to maintain graph structure
                    graph.addVertex(vertexToRemove);
                }
            }
            
            case "getVertices" -> graph.getVertices();
            case "getEdges" -> graph.getEdges();
            
            case "getNeighbors" -> {
                if (!vertices.isEmpty()) {
                    var testV = vertices.get(random.nextInt(vertices.size()));
                    graph.getNeighbors(testV);
                }
            }
            
            default -> throw new IllegalArgumentException("Unknown method: " + methodName);
        }
    }
    
    /**
     * Create a list of vertices
     */
    private static List<Vertex> createVertices(int count) {
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            vertices.add(new Vertex(i, "v" + i));
        }
        return vertices;
    }
    
    /**
     * Create a list of edges
     */
    private static List<Edge<Vertex>> createEdges(List<Vertex> vertices, int edgeCount) {
        List<Edge<Vertex>> edges = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < edgeCount && i < vertices.size() * (vertices.size() - 1) / 2; i++) {
            Vertex v1 = vertices.get(random.nextInt(vertices.size()));
            Vertex v2 = vertices.get(random.nextInt(vertices.size()));
            
            if (!v1.equals(v2)) {
                Edge<Vertex> edge = new Edge<>(v1, v2, random.nextInt(100) + 1);
                edges.add(edge);
            }
        }
        
        return edges;
    }
    
    /**
     * Generate charts for Task 1 methods
     */
    private static void generateTask1Charts(Map<String, Map<Integer, Double>> alResults, 
                                          Map<String, Map<Integer, Double>> amResults) {
        String[] methods = {"addVertex", "addEdge", "hasVertex", "hasEdge", "getEdge", "getEdgeLength"};
        
        for (String method : methods) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            
            // Add ALGraph data
            for (int size : GRAPH_SIZES) {
                if (alResults.get(method).containsKey(size)) {
                    dataset.addValue(alResults.get(method).get(size), "ALGraph", String.valueOf(size));
                }
            }
            
            // Add AMGraph data
            for (int size : GRAPH_SIZES) {
                if (amResults.get(method).containsKey(size)) {
                    dataset.addValue(amResults.get(method).get(size), "AMGraph", String.valueOf(size));
                }
            }
            
            JFreeChart chart = ChartFactory.createLineChart(
                "Task 1: " + method + " Performance",
                "Graph Size (vertices)",
                "Average Time (nanoseconds)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
            );
            
            try {
                ChartUtils.saveChartAsPNG(new File("task1_" + method + "_performance.png"), chart, 800, 600);
                System.out.println("Generated chart: task1_" + method + "_performance.png");
            } catch (IOException e) {
                System.err.println("Error saving chart: " + e.getMessage());
            }
        }
    }
    
    /**
     * Generate charts for Task 2 methods
     */
    private static void generateTask2Charts(Map<String, Map<Integer, Double>> alResults, 
                                          Map<String, Map<Integer, Double>> amResults) {
        String[] methods = {"getTotalEdgeLength", "removeEdge", "remove", "getVertices", "getEdges", "getNeighbors"};
        
        for (String method : methods) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            
            // Add ALGraph data
            for (int size : GRAPH_SIZES) {
                if (alResults.get(method).containsKey(size)) {
                    dataset.addValue(alResults.get(method).get(size), "ALGraph", String.valueOf(size));
                }
            }
            
            // Add AMGraph data
            for (int size : GRAPH_SIZES) {
                if (amResults.get(method).containsKey(size)) {
                    dataset.addValue(amResults.get(method).get(size), "AMGraph", String.valueOf(size));
                }
            }
            
            JFreeChart chart = ChartFactory.createLineChart(
                "Task 2: " + method + " Performance",
                "Graph Size (vertices)",
                "Average Time (nanoseconds)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
            );
            
            try {
                ChartUtils.saveChartAsPNG(new File("task2_" + method + "_performance.png"), chart, 800, 600);
                System.out.println("Generated chart: task2_" + method + "_performance.png");
            } catch (IOException e) {
                System.err.println("Error saving chart: " + e.getMessage());
            }
        }
    }
    
    /**
     * Generate a comprehensive summary chart comparing ALGraph vs AMGraph performance
     */
    private static void generateSummaryChart(Map<String, Map<Integer, Double>> alTask1Results,
                                           Map<String, Map<Integer, Double>> amTask1Results,
                                           Map<String, Map<Integer, Double>> alTask2Results,
                                           Map<String, Map<Integer, Double>> amTask2Results) {
        
        // Use the largest graph size for comparison
        int largestSize = GRAPH_SIZES[GRAPH_SIZES.length - 1];
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Task 1 methods
        String[] task1Methods = {"addVertex", "addEdge", "hasVertex", "hasEdge", "getEdge", "getEdgeLength"};
        for (String method : task1Methods) {
            if (alTask1Results.get(method).containsKey(largestSize) && 
                amTask1Results.get(method).containsKey(largestSize)) {
                dataset.addValue(alTask1Results.get(method).get(largestSize), "ALGraph", method);
                dataset.addValue(amTask1Results.get(method).get(largestSize), "AMGraph", method);
            }
        }
        
        // Task 2 methods
        String[] task2Methods = {"getTotalEdgeLength", "removeEdge", "remove", "getVertices", "getEdges", "getNeighbors"};
        for (String method : task2Methods) {
            if (alTask2Results.get(method).containsKey(largestSize) && 
                amTask2Results.get(method).containsKey(largestSize)) {
                dataset.addValue(alTask2Results.get(method).get(largestSize), "ALGraph", method);
                dataset.addValue(amTask2Results.get(method).get(largestSize), "AMGraph", method);
            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "ALGraph vs AMGraph Performance Comparison (Graph Size: " + largestSize + " vertices)",
            "Method",
            "Average Time (nanoseconds)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        try {
            ChartUtils.saveChartAsPNG(new File("performance_comparison_summary.png"), chart, 1200, 800);
            System.out.println("Generated summary chart: performance_comparison_summary.png");
        } catch (IOException e) {
            System.err.println("Error saving summary chart: " + e.getMessage());
        }
    }
    
    /**
     * Organize all generated charts into a performance_charts directory
     */
    private static void organizeCharts() {
        try {
            // Create performance_charts directory if it doesn't exist
            File chartsDir = new File("performance_charts");
            if (!chartsDir.exists()) {
                chartsDir.mkdir();
            }
            
            // Move all task1, task2, and summary charts to the directory
            File[] pngFiles = new File(".").listFiles((dir, name) -> name.endsWith(".png"));
            if (pngFiles != null) {
                for (File file : pngFiles) {
                    if (file.getName().startsWith("task1_") || 
                        file.getName().startsWith("task2_") || 
                        file.getName().equals("performance_comparison_summary.png")) {
                        
                        File destFile = new File(chartsDir, file.getName());
                        if (file.renameTo(destFile)) {
                            System.out.println("Moved " + file.getName() + " to performance_charts/");
                        }
                    }
                }
            }
            
            System.out.println("\nCharts organized in 'performance_charts/' directory:");
            File[] organizedFiles = chartsDir.listFiles((dir, name) -> name.endsWith(".png"));
            if (organizedFiles != null) {
                for (File file : organizedFiles) {
                    System.out.println("  - " + file.getName());
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error organizing charts: " + e.getMessage());
        }
    }
} 