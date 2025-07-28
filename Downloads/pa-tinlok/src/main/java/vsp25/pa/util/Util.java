package vsp25.pa.util;

import vsp25.pa.graph.Edge;
import vsp25.pa.graph.Vertex;

import java.util.*;

/**
 * Contains various static utility functions and constants.
 */
public abstract class Util {
    public static final String EARTH_NAME = "Earth";
    public static final String CRASHED_PLANET_NAME = "Kamino";
    public static final String DIRECTORY = System.getProperty("user.dir");

    /**
     * Return true iff any of the given arguments are null.
     *
     * @param objects list of objects to check if any is null.
     * @return true if one or more objects is null and false otherwise.
     */
    public static boolean anyNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the Euclidean distance between (x1, y1) and (x2, y2).
     *
     * @param x1 the first x-coordinate
     * @param y1 the first y-coordinate
     * @param x2 the second x-coordinate
     * @param y2 the second y-coordinate
     * @return the Euclidean distance between (x1, y1) and (x2, y2)
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
    }

    //// --- Additional utility methods for graph operations --- ////

    /**
     * Calculate the Manhattan distance between two points
     * Alternative distance metric that might be useful for certain algorithms
     * 
     * @param x1 x-coordinate of first point
     * @param y1 y-coordinate of first point
     * @param x2 x-coordinate of second point
     * @param y2 y-coordinate of second point
     * @return Manhattan distance between the points
     */
    public static int manhattanDistance(double x1, double y1, double x2, double y2) {
        return (int) (Math.abs(x2 - x1) + Math.abs(y2 - y1));
    }

    /**
     * Find the minimum value in a collection of integers
     * Useful for finding minimum edge weights or distances
     * 
     * @param values collection of integers
     * @return minimum value, or Integer.MAX_VALUE if collection is empty
     */
    public static int min(Collection<Integer> values) {
        return values.stream().mapToInt(Integer::intValue).min().orElse(Integer.MAX_VALUE);
    }

    /**
     * Find the maximum value in a collection of integers
     * Useful for finding maximum edge weights or distances
     * 
     * @param values collection of integers
     * @return maximum value, or Integer.MIN_VALUE if collection is empty
     */
    public static int max(Collection<Integer> values) {
        return values.stream().mapToInt(Integer::intValue).max().orElse(Integer.MIN_VALUE);
    }

    /**
     * Check if a collection is null or empty
     * Useful for defensive programming in graph operations
     * 
     * @param collection the collection to check
     * @return true if collection is null or empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Create a defensive copy of a list
     * Useful for returning collections that shouldn't be modified by clients
     * 
     * @param list the list to copy
     * @return a new list with the same elements
     */
    public static <T> List<T> defensiveCopy(List<T> list) {
        return list != null ? new ArrayList<>(list) : new ArrayList<>();
    }

    /**
     * Create a defensive copy of a set
     * Useful for returning collections that shouldn't be modified by clients
     * 
     * @param set the set to copy
     * @return a new set with the same elements
     */
    public static <T> Set<T> defensiveCopy(Set<T> set) {
        return set != null ? new HashSet<>(set) : new HashSet<>();
    }

    /**
     * Validate that a vertex is not null
     * Useful for input validation in graph operations
     * 
     * @param vertex the vertex to validate
     * @param message error message if vertex is null
     * @throws IllegalArgumentException if vertex is null
     */
    public static void validateVertex(Vertex vertex, String message) {
        if (vertex == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validate that an edge is not null
     * Useful for input validation in graph operations
     * 
     * @param edge the edge to validate
     * @param message error message if edge is null
     * @throws IllegalArgumentException if edge is null
     */
    public static void validateEdge(Edge<?> edge, String message) {
        if (edge == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Calculate the total length of a path given a list of vertices and edge lengths
     * Useful for path length calculations
     * 
     * @param path list of vertices in the path
     * @param edgeLengths function to get edge length between two vertices
     * @return total length of the path
     */
    public static int calculatePathLength(List<Vertex> path, 
                                       java.util.function.BiFunction<Vertex, Vertex, Integer> edgeLengths) {
        if (path == null || path.size() < 2) {
            return 0;
        }
        
        int totalLength = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Vertex current = path.get(i);
            Vertex next = path.get(i + 1);
            Integer length = edgeLengths.apply(current, next);
            if (length == null) {
                throw new IllegalArgumentException("No edge found between " + current + " and " + next);
            }
            totalLength += length;
        }
        return totalLength;
    }
}
