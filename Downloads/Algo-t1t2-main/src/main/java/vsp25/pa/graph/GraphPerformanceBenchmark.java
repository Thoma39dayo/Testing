package vsp25.pa.graph;

import java.util.*;
import java.io.*;

public class GraphPerformanceBenchmark {
    private static final int[] SIZES = {100, 1000, 5000}; // You can adjust sizes as needed
    private static final int EDGE_FACTOR = 5; // Average edges per vertex

    public static void main(String[] args) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter("performance_results.csv"))) {
            out.println("GraphType,Method,Size,TimeNs");
            for (int size : SIZES) {
                benchmarkGraph(new ALGraph<>(), "ALGraph", size, out);
                benchmarkGraph(new AMGraph<>(), "AMGraph", size, out);
            }
        }
        System.out.println("Benchmark complete. Results in performance_results.csv");
    }

    private static <V extends Vertex, E extends Edge<V>> void benchmarkGraph(GraphInterface<V, E> graph, String type, int size, PrintWriter out) {
        // Generate vertices
        List<V> vertices = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            vertices.add((V) new Vertex(i, "v" + i));
        }
        // Benchmark addVertex
        long t0 = System.nanoTime();
        for (V v : vertices) graph.addVertex(v);
        long t1 = System.nanoTime();
        out.printf("%s,addVertex,%d,%d\n", type, size, t1 - t0);

        // Generate edges (random pairs, no self-loops, no duplicates)
        Set<String> edgeSet = new HashSet<>();
        List<E> edges = new ArrayList<>();
        Random rand = new Random(42);
        int numEdges = size * EDGE_FACTOR;
        while (edges.size() < numEdges) {
            int i = rand.nextInt(size);
            int j = rand.nextInt(size);
            if (i == j) continue;
            String key = i < j ? i + "," + j : j + "," + i;
            if (edgeSet.contains(key)) continue;
            edgeSet.add(key);
            E e = (E) new Edge<>(vertices.get(i), vertices.get(j), rand.nextInt(10) + 1);
            edges.add(e);
        }
        // Benchmark addEdge
        t0 = System.nanoTime();
        for (E e : edges) graph.addEdge(e);
        t1 = System.nanoTime();
        out.printf("%s,addEdge,%d,%d\n", type, size, t1 - t0);

        // Benchmark hasVertex
        t0 = System.nanoTime();
        for (V v : vertices) graph.hasVertex(v);
        t1 = System.nanoTime();
        out.printf("%s,hasVertex,%d,%d\n", type, size, t1 - t0);

        // Benchmark hasEdge (by edge)
        t0 = System.nanoTime();
        for (E e : edges) graph.hasEdge(e);
        t1 = System.nanoTime();
        out.printf("%s,hasEdge(edge),%d,%d\n", type, size, t1 - t0);

        // Benchmark hasEdge (by vertices)
        t0 = System.nanoTime();
        for (E e : edges) graph.hasEdge(e.v1(), e.v2());
        t1 = System.nanoTime();
        out.printf("%s,hasEdge(vertices),%d,%d\n", type, size, t1 - t0);

        // Benchmark getEdge
        t0 = System.nanoTime();
        for (E e : edges) graph.getEdge(e.v1(), e.v2());
        t1 = System.nanoTime();
        out.printf("%s,getEdge,%d,%d\n", type, size, t1 - t0);

        // Benchmark getEdgeLength
        t0 = System.nanoTime();
        for (E e : edges) graph.getEdgeLength(e.v1(), e.v2());
        t1 = System.nanoTime();
        out.printf("%s,getEdgeLength,%d,%d\n", type, size, t1 - t0);

        // Benchmark getVertices
        t0 = System.nanoTime();
        graph.getVertices();
        t1 = System.nanoTime();
        out.printf("%s,getVertices,%d,%d\n", type, size, t1 - t0);

        // Benchmark getEdges
        t0 = System.nanoTime();
        graph.getEdges();
        t1 = System.nanoTime();
        out.printf("%s,getEdges,%d,%d\n", type, size, t1 - t0);

        // Benchmark getEdges(V)
        t0 = System.nanoTime();
        for (V v : vertices) graph.getEdges(v);
        t1 = System.nanoTime();
        out.printf("%s,getEdges(vertex),%d,%d\n", type, size, t1 - t0);

        // Benchmark getNeighbors
        t0 = System.nanoTime();
        for (V v : vertices) graph.getNeighbors(v);
        t1 = System.nanoTime();
        out.printf("%s,getNeighbors,%d,%d\n", type, size, t1 - t0);

        // Benchmark getTotalEdgeLength
        t0 = System.nanoTime();
        graph.getTotalEdgeLength();
        t1 = System.nanoTime();
        out.printf("%s,getTotalEdgeLength,%d,%d\n", type, size, t1 - t0);

        // Benchmark removeEdge
        t0 = System.nanoTime();
        for (E e : edges) graph.removeEdge(e);
        t1 = System.nanoTime();
        out.printf("%s,removeEdge,%d,%d\n", type, size, t1 - t0);

        // Benchmark remove (vertex)
        t0 = System.nanoTime();
        for (V v : vertices) graph.remove(v);
        t1 = System.nanoTime();
        out.printf("%s,remove(vertex),%d,%d\n", type, size, t1 - t0);
    }
} 