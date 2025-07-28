package vsp25.pa.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Examples of Java 17 features that students can use in their graph implementations.
 * This file demonstrates modern Java features that make graph algorithms more elegant.
 */
public class Java17Examples {
    
    /**
     * Example 1: Pattern Matching for instanceof (Java 16+)
     * Makes type checking and casting more elegant.
     */
    public static void patternMatchingExample() {
        System.out.println("=== Pattern Matching Example ===");
        
        Object obj = new Vertex(1, "Test");
        
        // Old way (pre-Java 16)
        if (obj instanceof Vertex) {
            Vertex vertex = (Vertex) obj;
            System.out.println("Old way: " + vertex.name());
        }
        
        // New way (Java 16+)
        if (obj instanceof Vertex vertex) {
            System.out.println("New way: " + vertex.name());
        }
        
        // Pattern matching in switch (Java 21+)
        // Note: This requires Java 21, not Java 17
        // String result = switch (obj) {
        //     case Vertex v -> "Vertex: " + v.name();
        //     case Edge<?> e -> "Edge: " + e.length();
        //     case null -> "null";
        //     default -> "Unknown type";
        // };
        // System.out.println("Switch pattern: " + result);
        
        // Pattern matching in switch (Java 21+)
        // Note: This requires Java 21, not Java 17
        // String result = switch (obj) {
        //     case Vertex v -> "Vertex: " + v.name();
        //     case Edge<?> e -> "Edge: " + e.length();
        //     case null -> "null";
        //     default -> "Unknown type";
        // };
        // System.out.println("Switch pattern: " + result);
        
        // Java 17 compatible alternative using instanceof
        String result;
        if (obj instanceof Vertex v) {
            result = "Vertex: " + v.name();
        } else if (obj instanceof Edge<?> e) {
            result = "Edge: " + e.length();
        } else if (obj == null) {
            result = "null";
        } else {
            result = "Unknown type";
        }
        System.out.println("Pattern matching: " + result);
    }
    
    /**
     * Example 2: Enhanced Switch Expressions (Java 14+)
     * More concise and functional switch statements.
     */
    public static void enhancedSwitchExample() {
        System.out.println("\n=== Enhanced Switch Example ===");
        
        String operation = "ADD_VERTEX";
        
        // Old way
        String result1;
        switch (operation) {
            case "ADD_VERTEX":
                result1 = "Adding vertex";
                break;
            case "ADD_EDGE":
                result1 = "Adding edge";
                break;
            case "REMOVE_VERTEX":
                result1 = "Removing vertex";
                break;
            default:
                result1 = "Unknown operation";
        }
        System.out.println("Old switch: " + result1);
        
        // New way (Java 14+)
        String result2 = switch (operation) {
            case "ADD_VERTEX" -> "Adding vertex";
            case "ADD_EDGE" -> "Adding edge";
            case "REMOVE_VERTEX" -> "Removing vertex";
            default -> "Unknown operation";
        };
        System.out.println("New switch: " + result2);
    }
    
    /**
     * Example 3: Text Blocks (Java 15+)
     * Multi-line strings for better readability.
     */
    public static void textBlocksExample() {
        System.out.println("\n=== Text Blocks Example ===");
        
        // Old way
        String oldWay = "Graph {\n" +
                       "  vertices: " + 5 + "\n" +
                       "  edges: " + 10 + "\n" +
                       "}";
        System.out.println("Old way:\n" + oldWay);
        
        // New way (Java 15+)
        String newWay = """
            Graph {
              vertices: %d
              edges: %d
            }""".formatted(5, 10);
        System.out.println("New way:\n" + newWay);
    }
    
    /**
     * Example 4: Records (Java 16+)
     * Immutable data classes with automatic getters, equals, hashCode, toString.
     */
    public static void recordsExample() {
        System.out.println("\n=== Records Example ===");
        
        // Old way - verbose class
        class OldPath {
            private final List<Vertex> vertices;
            private final int length;
            
            public OldPath(List<Vertex> vertices, int length) {
                this.vertices = vertices;
                this.length = length;
            }
            
            public List<Vertex> vertices() { return vertices; }
            public int length() { return length; }
            
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                OldPath oldPath = (OldPath) obj;
                return length == oldPath.length && Objects.equals(vertices, oldPath.vertices);
            }
            
            @Override
            public int hashCode() {
                return Objects.hash(vertices, length);
            }
            
            @Override
            public String toString() {
                return "OldPath{vertices=" + vertices + ", length=" + length + "}";
            }
        }
        
        // New way (Java 16+) - concise record
        record Path(List<Vertex> vertices, int length) {}
        
        List<Vertex> pathVertices = Arrays.asList(new Vertex(1, "A"), new Vertex(2, "B"));
        Path path = new Path(pathVertices, 10);
        System.out.println("Record: " + path);
        System.out.println("Vertices: " + path.vertices());
        System.out.println("Length: " + path.length());
    }
    
    /**
     * Example 5: Sealed Classes (Java 17+)
     * Restrict inheritance to specific classes.
     */
    public static void sealedClassesExample() {
        System.out.println("\n=== Sealed Classes Example ===");
        
        // Using enum instead of sealed classes for this example
        enum GraphAlgorithm {
            DIJKSTRA("Dijkstra's Shortest Path"),
            BFS("Breadth-First Search"),
            DFS("Depth-First Search");
            
            private final String name;
            
            GraphAlgorithm(String name) {
                this.name = name;
            }
            
            String getName() { return name; }
        }
        
        // Pattern matching with enum
        GraphAlgorithm algorithm = GraphAlgorithm.DIJKSTRA;
        String algorithmName = switch (algorithm) {
            case DIJKSTRA -> algorithm.getName();
            case BFS -> algorithm.getName();
            case DFS -> algorithm.getName();
        };
        System.out.println("Algorithm: " + algorithmName);
    }
    
    /**
     * Example 6: Stream API Enhancements (Java 16+)
     * More powerful stream operations.
     */
    public static void streamEnhancementsExample() {
        System.out.println("\n=== Stream Enhancements Example ===");
        
        List<Vertex> vertices = Arrays.asList(
            new Vertex(1, "A"),
            new Vertex(2, "B"),
            new Vertex(3, "C"),
            new Vertex(4, "D")
        );
        
        // Old way
        List<String> names1 = vertices.stream()
            .map(Vertex::name)
            .collect(Collectors.toList());
        System.out.println("Old way: " + names1);
        
        // New way (Java 16+) - toList() method
        List<String> names2 = vertices.stream()
            .map(Vertex::name)
            .toList(); // Immutable list
        System.out.println("New way: " + names2);
        
        // Map multi-grouping (Java 16+)
        Map<String, List<Vertex>> grouped = vertices.stream()
            .collect(Collectors.groupingBy(v -> v.name().length() > 1 ? "Long" : "Short"));
        System.out.println("Grouped: " + grouped);
    }
    
    /**
     * Example 7: Optional Enhancements (Java 9+)
     * Better handling of optional values.
     */
    public static void optionalEnhancementsExample() {
        System.out.println("\n=== Optional Enhancements Example ===");
        
        Optional<Vertex> vertex = Optional.of(new Vertex(1, "Test"));
        
        // Old way
        if (vertex.isPresent()) {
            System.out.println("Old way: " + vertex.get().name());
        }
        
        // New way (Java 9+)
        vertex.ifPresent(v -> System.out.println("New way: " + v.name()));
        
        // Or else with supplier (Java 9+)
        Vertex result = vertex.orElseGet(() -> new Vertex(0, "Default"));
        System.out.println("Or else: " + result.name());
    }
    
    /**
     * Example 8: Collection Factory Methods (Java 9+)
     * Immutable collections with concise syntax.
     */
    public static void collectionFactoriesExample() {
        System.out.println("\n=== Collection Factories Example ===");
        
        // Old way
        Set<String> oldSet = new HashSet<>();
        oldSet.add("A");
        oldSet.add("B");
        oldSet.add("C");
        oldSet = Collections.unmodifiableSet(oldSet);
        
        // New way (Java 9+)
        Set<String> newSet = Set.of("A", "B", "C");
        List<String> newList = List.of("A", "B", "C");
        Map<String, Integer> newMap = Map.of("A", 1, "B", 2, "C", 3);
        
        System.out.println("Old set: " + oldSet);
        System.out.println("New set: " + newSet);
        System.out.println("New list: " + newList);
        System.out.println("New map: " + newMap);
    }
    
    /**
     * Example 9: Local Variable Type Inference (Java 10+)
     * Use 'var' for cleaner code where type is obvious.
     */
    public static void varExample() {
        System.out.println("\n=== Var Example ===");
        
        // Old way
        List<Vertex> vertices = Arrays.asList(new Vertex(1, "A"), new Vertex(2, "B"));
        Map<Vertex, Integer> distances = new HashMap<>();
        for (Vertex vertex : vertices) {
            distances.put(vertex, vertex.id());
        }
        
        // New way (Java 10+)
        var vertices2 = Arrays.asList(new Vertex(1, "A"), new Vertex(2, "B"));
        var distances2 = new HashMap<Vertex, Integer>();
        for (var vertex : vertices2) {
            distances2.put(vertex, vertex.id());
        }
        
        System.out.println("Old way: " + distances);
        System.out.println("New way: " + distances2);
    }
    
    /**
     * Example 10: Practical Graph Implementation with Java 17 Features
     * Shows how to use these features in actual graph code.
     */
    public static void practicalGraphExample() {
        System.out.println("\n=== Practical Graph Example ===");
        
        // Using records for graph components
        record GraphStats(int vertexCount, int edgeCount, int diameter) {}
        
        // Using enum for algorithm results instead of sealed interface
        enum AlgorithmType {
            SHORTEST_PATH, CONNECTED_COMPONENTS, DIAMETER
        }
        
        record ShortestPathResult(List<Vertex> path, int length) {}
        record ConnectedComponentsResult(List<Set<Vertex>> components) {}
        record DiameterResult(int diameter, Vertex center) {}
        
        // Using pattern matching for result handling
        var path = List.of(new Vertex(1, "A"), new Vertex(2, "B"));
        var result = new ShortestPathResult(path, 10);
        
        String description;
        // Deconstruction patterns require Java 21
        // if (result instanceof ShortestPathResult(var p, var l)) {
        //     description = "Shortest path with %d vertices, length %d".formatted(p.size(), l);
        // } else {
        //     description = "Unknown result type";
        // }
        
        // Java 17 compatible alternative
        description = "Shortest path with %d vertices, length %d".formatted(result.path().size(), result.length());
        
        System.out.println("Result: " + description);
        
        // Using records for graph statistics
        var stats = new GraphStats(5, 10, 3);
        System.out.println("Graph stats: " + stats);
    }
    
    /**
     * Run all examples to demonstrate Java 17 features.
     */
    public static void main(String[] args) {
        System.out.println("Java 17 Features for Graph Implementation");
        System.out.println("=========================================");
        
        patternMatchingExample();
        enhancedSwitchExample();
        textBlocksExample();
        recordsExample();
        sealedClassesExample();
        streamEnhancementsExample();
        optionalEnhancementsExample();
        collectionFactoriesExample();
        varExample();
        practicalGraphExample();
        
        System.out.println("\n=== Summary ===");
        System.out.println("""
            These Java 17 features can make your graph implementations:
            • More readable with pattern matching and switch expressions
            • More concise with records and var
            • More type-safe with sealed classes
            • More functional with enhanced streams
            • More immutable with collection factories
            """);
    }
} 