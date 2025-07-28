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
 * Performance benchmarking class for Task 3 graph algorithms.
 * Measures runtime complexity of shortestPath, pathLength, and getNeighbors with range
 * for both ALGraph and AMGraph implementations.
 * Generates performance charts using JFreeChart.
 */
public class Task3PerformanceBenchmark {
    
    private static final int WARMUP_ITERATIONS = 500; // Reduced for complex algorithms
    private static final int MEASUREMENT_ITERATIONS = 2000; // Reduced for complex algorithms
    private static final int[] GRAPH_SIZES = {10, 25, 50, 100, 200, 500}; // Smaller sizes for complex algorithms
    private static final int[] RANGE_VALUES = {1, 5, 10, 20, 50}; // Different range values to test
    
    public static void main(String[] args) {
        System.out.println("=== Task 3 Performance Benchmark ===");
        
        var alResults = new HashMap<String, Map<Integer, Double>>();
        var amResults = new HashMap<String, Map<Integer, Double>>();
        
        // Initialize result maps
        String[] task3Methods = {"shortestPath", "pathLength", "getNeighborsRange"};
        for (String method : task3Methods) {
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
            for (String method : task3Methods) {
                double alTime = benchmarkMethod(alGraph, method, vertices, edges);
                double amTime = benchmarkMethod(amGraph, method, vertices, edges);
                
                alResults.get(method).put(size, alTime);
                amResults.get(method).put(size, amTime);
            }
        }
        
        // Generate charts
        generateTask3Charts(alResults, amResults);
        System.out.println("Task 3 performance charts generated successfully!");
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
            case "shortestPath" -> {
                if (vertices.size() > 1) {
                    var source = vertices.get(random.nextInt(vertices.size()));
                    var sink = vertices.get(random.nextInt(vertices.size()));
                    graph.shortestPath(source, sink);
                }
            }
            
            case "pathLength" -> {
                if (vertices.size() > 1) {
                    // Create a random path of 2-5 vertices
                    int pathLength = 2 + random.nextInt(Math.min(4, vertices.size() - 1));
                    List<Vertex> path = new ArrayList<>();
                    for (int i = 0; i < pathLength; i++) {
                        path.add(vertices.get(random.nextInt(vertices.size())));
                    }
                    graph.pathLength(path);
                }
            }
            
            case "getNeighborsRange" -> {
                if (!vertices.isEmpty()) {
                    var testV = vertices.get(random.nextInt(vertices.size()));
                    int range = RANGE_VALUES[random.nextInt(RANGE_VALUES.length)];
                    graph.getNeighbors(testV, range);
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
     * Create a connected graph with edges
     */
    private static List<Edge<Vertex>> createConnectedEdges(List<Vertex> vertices, int size) {
        List<Edge<Vertex>> edges = new ArrayList<>();
        Random random = new Random(42);
        
        // Create a connected graph (minimum spanning tree + some extra edges)
        for (int i = 1; i < vertices.size(); i++) {
            // Connect to a random previous vertex
            int prevIndex = random.nextInt(i);
            Vertex v1 = vertices.get(prevIndex);
            Vertex v2 = vertices.get(i);
            int weight = 1 + random.nextInt(20); // Random weight 1-20
            edges.add(new Edge<>(v1, v2, weight));
        }
        
        // Add some additional edges for denser graph
        int extraEdges = size / 4; // Add extra edges proportional to size
        for (int i = 0; i < extraEdges; i++) {
            int v1Index = random.nextInt(vertices.size());
            int v2Index = random.nextInt(vertices.size());
            if (v1Index != v2Index) {
                Vertex v1 = vertices.get(v1Index);
                Vertex v2 = vertices.get(v2Index);
                int weight = 1 + random.nextInt(20);
                Edge<Vertex> edge = new Edge<>(v1, v2, weight);
                if (!edges.contains(edge)) {
                    edges.add(edge);
                }
            }
        }
        
        return edges;
    }
    
    /**
     * Generate performance charts for Task 3 methods
     */
    private static void generateTask3Charts(Map<String, Map<Integer, Double>> alResults,
                                          Map<String, Map<Integer, Double>> amResults) {
        // Create performance_charts directory if it doesn't exist
        File chartsDir = new File("performance_charts");
        if (!chartsDir.exists()) {
            chartsDir.mkdirs();
        }
        
        // Generate individual charts for each method
        generateChart(alResults, amResults, "shortestPath", "Shortest Path Performance", 
                     "task3_shortestPath_performance.png");
        generateChart(alResults, amResults, "pathLength", "Path Length Performance", 
                     "task3_pathLength_performance.png");
        generateChart(alResults, amResults, "getNeighborsRange", "Get Neighbors with Range Performance", 
                     "task3_getNeighborsRange_performance.png");
        
        // Generate summary chart
        generateTask3SummaryChart(alResults, amResults);
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
        for (int size : GRAPH_SIZES) {
            if (alData.containsKey(size)) {
                dataset.addValue(alData.get(size), "ALGraph", String.valueOf(size));
            }
        }
        
        // Add AMGraph data
        Map<Integer, Double> amData = amResults.get(methodName);
        for (int size : GRAPH_SIZES) {
            if (amData.containsKey(size)) {
                dataset.addValue(amData.get(size), "AMGraph", String.valueOf(size));
            }
        }
        
        JFreeChart chart = ChartFactory.createLineChart(
            title,
            "Graph Size (vertices)",
            "Average Time (nanoseconds)",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );
        
        try {
            ChartUtils.saveChartAsPNG(new File("performance_charts/" + filename), chart, 800, 600);
            System.out.println("Generated chart: " + filename);
        } catch (IOException e) {
            System.err.println("Error saving chart: " + e.getMessage());
        }
    }
    
    /**
     * Generate a summary chart comparing all Task 3 methods
     */
    private static void generateTask3SummaryChart(Map<String, Map<Integer, Double>> alResults,
                                                Map<String, Map<Integer, Double>> amResults) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Use the largest graph size for comparison
        int largestSize = GRAPH_SIZES[GRAPH_SIZES.length - 1];
        
        String[] methods = {"shortestPath", "pathLength", "getNeighborsRange"};
        String[] methodLabels = {"Shortest Path", "Path Length", "Get Neighbors (Range)"};
        
        for (int i = 0; i < methods.length; i++) {
            String method = methods[i];
            String label = methodLabels[i];
            
            // Add ALGraph data
            Map<Integer, Double> alData = alResults.get(method);
            if (alData.containsKey(largestSize)) {
                dataset.addValue(alData.get(largestSize), "ALGraph", label);
            }
            
            // Add AMGraph data
            Map<Integer, Double> amData = amResults.get(method);
            if (amData.containsKey(largestSize)) {
                dataset.addValue(amData.get(largestSize), "AMGraph", label);
            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Task 3 Algorithm Performance Comparison (" + largestSize + " vertices)",
            "Method",
            "Average Time (nanoseconds)",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );
        
        try {
            ChartUtils.saveChartAsPNG(new File("performance_charts/task3_performance_summary.png"), chart, 800, 600);
            System.out.println("Generated summary chart: task3_performance_summary.png");
        } catch (IOException e) {
            System.err.println("Error saving summary chart: " + e.getMessage());
        }
    }
} 