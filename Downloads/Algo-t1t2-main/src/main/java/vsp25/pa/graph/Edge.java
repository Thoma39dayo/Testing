package vsp25.pa.graph;

import java.util.NoSuchElementException;

/**
 * Represents an undirected edge between two distinct vertices with a non-negative length.
 *
 * @param <V> the type of vertex
 */
public class Edge<V extends Vertex> implements Cloneable {

    private V v1;
    private V v2;
    private int length;

    /**
     * Constructs an edge between two vertices with default length 1.
     *
     * @param v1 one endpoint of the edge (must not be null)
     * @param v2 the other endpoint of the edge (must not be null and not equal to v1)
     * @throws IllegalArgumentException if either vertex is null or both are the same
     */
    public Edge(V v1, V v2) {
        this(v1, v2, 1);
    }

    /**
     * Constructs an edge between two vertices with a specified length.
     *
     * @param v1 one endpoint of the edge (must not be null)
     * @param v2 the other endpoint of the edge (must not be null and not equal to v1)
     * @param length the non-negative length (weight) of the edge
     * @throws IllegalArgumentException if either vertex is null, both are the same, or length is negative
     */
    public Edge(V v1, V v2, int length) {
        if (v1 == null || v2 == null) {
            throw new IllegalArgumentException("Vertices cannot be null");
        }
        if (v1.equals(v2)) {
            throw new IllegalArgumentException("The same vertex cannot be at both ends of an edge");
        }
        if (length < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative");
        }
        this.v1 = v1;
        this.v2 = v2;
        this.length = length;
    }

    /**
     * Returns a deep copy of this edge, cloning both endpoints.
     *
     * @return a new Edge with cloned vertices and the same length
     */
    @Override
    public Edge<V> clone() {
        return new Edge(this.v1.clone(), this.v2.clone(), this.length);
    }

    /**
     * Returns the first endpoint of this edge.
     *
     * @return the first vertex
     */
    public V v1() {
        return v1;
    }

    /**
     * Returns the second endpoint of this edge.
     *
     * @return the second vertex
     */
    public V v2() {
        return v2;
    }

    /**
     * Returns the length (weight) of this edge.
     *
     * @return the non-negative length
     */
    public int length() {
        return length;
    }

    /**
     * Checks if this edge is equal to another object.
     * Two edges are equal if they connect the same pair of vertices (regardless of order).
     *
     * @param o the object to compare
     * @return true if o is an Edge with the same endpoints, false otherwise
     */
    public boolean equals(Object o) {
        if (o instanceof Edge<?>) {
            Edge<?> other = (Edge<?>) o;
            if (other.v1.equals(this.v1) && other.v2.equals(this.v2)) {
                return true;
            }
            if (other.v1.equals(this.v2) && other.v2.equals(this.v1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a hash code for this edge, based on its endpoints.
     *
     * @return the hash code
     */
    public int hashCode() {
        return v1.hashCode() + v2.hashCode();
    }

    /**
     * Determines if the given vertex is incident to this edge.
     *
     * @param v the vertex to check
     * @return true if v is one of the endpoints, false otherwise
     */
    public boolean incident(V v) {
        if (v == null) {
            return false;
        }
        if (v.equals(v1) || v.equals(v2)) {
            return true;
        }
        return false;
    }

    /**
     * Determines if this edge shares an endpoint with another edge.
     *
     * @param e the other edge
     * @return true if the edges share a vertex, false otherwise
     */
    public boolean intersects(Edge<V> e) {
        if (e == null) {
            return false;
        }
        return this.incident(e.v1) || this.incident(e.v2);
    }

    /**
     * Returns the shared vertex between this edge and another edge, if any.
     *
     * @param e the other edge
     * @return the common vertex
     * @throws NoSuchElementException if there is no common vertex
     */
    public V intersection(Edge<V> e) throws NoSuchElementException {
        if (e == null) {
            throw new NoSuchElementException("No common vertex");
        }
        if (this.v1.equals(e.v1) || this.v1.equals(e.v2)) {
            return this.v1;
        }
        if (this.v2.equals(e.v1) || this.v2.equals(e.v2)) {
            return this.v2;
        }
        throw new NoSuchElementException("No common vertex");
    }

    /**
     * Returns the endpoint of this edge that is not the given vertex.
     *
     * @param v one endpoint of this edge
     * @return the other endpoint
     * @throws IllegalArgumentException if v is not an endpoint of this edge
     */
    public V distinctVertex(V v) {
        if (this.v1.equals(v)) {
            return this.v2;
        } else {
            return this.v1;
        }
    }

    /**
     * Returns the endpoint of this edge that is not shared with another edge.
     * If the edges do not share a vertex, returns v1 by default.
     *
     * @param e the other edge
     * @return the endpoint of this edge not shared with e
     * @throws NoSuchElementException if the edges are equal (no distinct vertex)
     */
    public V distinctVertex(Edge<V> e) {
        if (this.equals(e)) {
            throw new NoSuchElementException("No distinct vertex");
        }
        V sv;
        try {
            sv = this.intersection(e);
        }
        catch (NoSuchElementException nse) {
            // when there is no common vertex,
            // return any vertex (deterministic choice of v1 is okay).
            return v1;
        }
        if (v1.equals(sv)) {
            return v2;
        } else {
            return v1;
        }
    }

}
