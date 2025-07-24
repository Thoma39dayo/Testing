package vsp25.pa.graph;

/**
 * Represents a graph vertex. Each vertex has an associated id and name.
 * No two vertices in the same graph should have the same id.
 */
public class Vertex implements Cloneable {
    private final int id;
    private String name;

    /**
     * Create a new vertex
     *
     * @param id   is a numeric identifier for the vertex
     * @param name is a name for the vertex
     */
    public Vertex(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Vertex clone() {
        return new Vertex(this.id, this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vertex) {
            Vertex other = (Vertex) o;
            if (other.id == this.id && other.name.equals(this.name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + id;
    }

    public int id() {
        return id;
    }

    /**
     * Obtain the vertex name
     *
     * @return the vertex name
     */
    public String name() {
        return name;
    }

    /**
     * Update the vertex name
     *
     * @param name the new vertex name
     */
    public void updateName(String name) {
        this.name = name;
    }

    //// --- any new methods, if necessary, go below this link --- ////

    /**
     * Returns a string representation of this vertex
     * Useful for debugging and testing
     * 
     * @return string representation in format "Vertex(id, name)"
     */
    @Override
    public String toString() {
        return "Vertex(" + id + ", " + name + ")";
    }

    /**
     * Compare this vertex to another based on their IDs
     * Useful for sorting vertices or using them in ordered data structures
     * 
     * @param other the vertex to compare with
     * @return negative if this.id < other.id, 0 if equal, positive if this.id > other.id
     */
    public int compareTo(Vertex other) {
        return Integer.compare(this.id, other.id);
    }

    /**
     * Check if this vertex has the same ID as another vertex
     * Useful for checking vertex identity without considering the name
     * 
     * @param other the vertex to compare with
     * @return true if both vertices have the same ID
     */
    public boolean hasSameId(Vertex other) {
        return this.id == other.id;
    }
}
