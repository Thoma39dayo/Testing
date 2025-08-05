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
 * Performance benchmarking class for Task 4 graph algorithms.
 * Measures runtime complexity of diameter, getCenter, and minimumSpanningComponents
 * for both ALGraph and AMGraph implementations.
 * Generates performance charts using JFreeChart.
 */
public class Task4PerformanceBenchmark {
    
    private static final int WARMUP_ITERATIONS = 100; // Reduced for complex algorithms
    private static final int MEASUREMENT_ITERATIONS = 500; // Reduced for complex algorithms
    private static final int[] GRAPH_SIZES = {10, 25, 50, 100, 200}; // Smaller sizes for complex algorithms
    private static final int[] K_VALUES = {2, 3, 5, 10}; // Different k values for minimumSpanningComponents
    
    public static void main(String[] args) {
        System.out.println("=== Task 4 Performance Benchmark ===");
        
        var alResults = new HashMap<String, Map<Integer, Double>>();
        var amResults = new HashMap<String, Map<Integer, Double>>();
        
        // Initialize result maps
        String[] task4Methods = {"diameter", "getCenter", "minimumSpanningComponents"};
        for (String method : task4Methods) {
            alResults.put(method, new HashMap<>());
            amResults.put(method, new HashMap<>());
        }
        
        // Benchmark each graph size
        for (int size : GRAPH_SIZES) {
            System.out.println("Testing graph size: " + size);
            
            // Create test graphs
            ALGraph<Vertex, Edge<Vertex>> alGraph = new ALGraph<>();
            AMGraph<Vertex, Edge<Vertex>> amGraph = new AMGraph<>();
            
            // Create connected graph for testing
            List<Vertex> vertices = createVertices(size);
            List<Edge<Vertex>> edges = createConnectedEdges(vertices, size);
            
            // Populate graphs
            for (Vertex v : vertices) {
                alGraph.addVertex(v);
                amGraph.addVertex(v);
            }
            
            for (Edge<Vertex> e : edges) {
                alGraph.addEdge(e);
                amGraph.addEdge(e);
            }
            
            // Benchmark each method
            for (String method : task4Methods) {
                double alTime = benchmarkMethod(alGraph, method, vertices, edges);
                double amTime = benchmarkMethod(amGraph, method, vertices, edges);
                
                alResults.get(method).put(size, alTime);
                amResults.get(method).put(size, amTime);
            }
        }
        
        // Generate charts
        generateTask4Charts(alResults, amResults);
        System.out.println("Task 4 performance charts generated successfully!");
    }
    
    /**
     * Benchmark a specific method on a graph
     */
    private static double benchmarkMethod(GraphInterface<Vertex, Edge<Vertex>> graph, String methodName, 
                                        List<Vertex> vertices, List<Edge<Vertex>> edges) {
        Random random = new Random(42); // Fixed seed for reproducibility
        
        // Warmup
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            executeMethod(graph, methodName, vertices, edges, random);
        }
        
        // Measurement
        long startTime = System.nanoTime();
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            executeMethod(graph, methodName, vertices, edges, random);
        }
        long endTime = System.nanoTime();
        
        return (double) (endTime - startTime) / MEASUREMENT_ITERATIONS;
    }
    
    /**
     * Execute a specific method for benchmarking
     */
    private static void executeMethod(GraphInterface<Vertex, Edge<Vertex>> graph, String methodName,
                                    List<Vertex> vertices, List<Edge<Vertex>> edges, Random random) {
        switch (methodName) {
            case "diameter":
                graph.diameter();
                break;
            case "getCenter":
                graph.getCenter();
                break;
            case "minimumSpanningComponents":
                // Test with different k values
                int k = K_VALUES[random.nextInt(K_VALUES.length)];
                if (k <= vertices.size()) {
                    graph.minimumSpanningComponents(k);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown method: " + methodName);
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
     * Create a connected graph with random edges
     */
    private static List<Edge<Vertex>> createConnectedEdges(List<Vertex> vertices, int size) {
        List<Edge<Vertex>> edges = new ArrayList<>();
        Random random = new Random(42);
        
        // Ensure connectivity by creating a spanning tree first
        for (int i = 1; i < vertices.size(); i++) {
            int parent = random.nextInt(i);
            int weight = random.nextInt(10) + 1;
            edges.add(new Edge<>(vertices.get(parent), vertices.get(i), weight));
        }
        
        // Add additional random edges to make it more interesting
        int additionalEdges = size / 2;
        for (int i = 0; i < additionalEdges; i++) {
            int v1 = random.nextInt(vertices.size());
            int v2 = random.nextInt(vertices.size());
            if (v1 != v2) {
                int weight = random.nextInt(10) + 1;
                Edge<Vertex> edge = new Edge<>(vertices.get(v1), vertices.get(v2), weight);
                if (!edges.contains(edge)) {
                    edges.add(edge);
                }
            }
        }
        
        return edges;
    }
    
    /**
     * Generate all Task 4 performance charts
     */
    private static void generateTask4Charts(Map<String, Map<Integer, Double>> alResults,
                                          Map<String, Map<Integer, Double>> amResults) {
        // Create performance_charts directory if it doesn't exist
        File chartsDir = new File("performance_charts");
        if (!chartsDir.exists()) {
            chartsDir.mkdir();
        }
        
        // Generate individual charts
        generateChart(alResults, amResults, "diameter", 
                     "Task 4: Diameter Performance", "task4_diameter_performance.png");
        generateChart(alResults, amResults, "getCenter", 
                     "Task 4: Get Center Performance", "task4_getCenter_performance.png");
        generateChart(alResults, amResults, "minimumSpanningComponents", 
                     "Task 4: Minimum Spanning Components Performance", "task4_minimumSpanningComponents_performance.png");
        
        // Generate summary chart
        generateTask4SummaryChart(alResults, amResults);
    }
    
    /**
     * Generate a performance chart for a specific method
     */
    private static void generateChart(Map<String, Map<Integer, Double>> alResults,
                                    Map<String, Map<Integer, Double>> amResults,
                                    String methodName, String title, String filename) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Add ALGraph data
        Map<Integer, Double> alData = alResults.get(methodName);
        for (Map.Entry<Integer, Double> entry : alData.entrySet()) {
            dataset.addValue(entry.getValue() / 1000.0, "ALGraph", entry.getKey().toString());
        }
        
        // Add AMGraph data
        Map<Integer, Double> amData = amResults.get(methodName);
        for (Map.Entry<Integer, Double> entry : amData.entrySet()) {
            dataset.addValue(entry.getValue() / 1000.0, "AMGraph", entry.getKey().toString());
        }
        
        JFreeChart chart = ChartFactory.createLineChart(
            title,
            "Graph Size (vertices)",
            "Average Time (microseconds)",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );
        
        try {
            ChartUtils.saveChartAsPNG(new File("performance_charts/" + filename), chart, 800, 600);
        } catch (IOException e) {
            System.err.println("Error saving chart: " + e.getMessage());
        }
    }
    
    /**
     * Generate a summary chart comparing all Task 4 methods
     */
    private static void generateTask4SummaryChart(Map<String, Map<Integer, Double>> alResults,
                                                Map<String, Map<Integer, Double>> amResults) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Use the largest graph size for comparison
        int maxSize = GRAPH_SIZES[GRAPH_SIZES.length - 1];
        
        // Add data for each method
        String[] methods = {"diameter", "getCenter", "minimumSpanningComponents"};
        for (String method : methods) {
            double alTime = alResults.get(method).get(maxSize) / 1000.0;
            double amTime = amResults.get(method).get(maxSize) / 1000.0;
            
            dataset.addValue(alTime, "ALGraph", method);
            dataset.addValue(amTime, "AMGraph", method);
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Task 4: Performance Comparison (Graph Size: " + maxSize + ")",
            "Method",
            "Average Time (microseconds)",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );
        
        try {
            ChartUtils.saveChartAsPNG(new File("performance_charts/task4_performance_summary.png"), chart, 800, 600);
        } catch (IOException e) {
            System.err.println("Error saving summary chart: " + e.getMessage());
        }
    }
} 