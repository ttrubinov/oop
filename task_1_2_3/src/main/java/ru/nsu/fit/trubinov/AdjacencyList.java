package ru.nsu.fit.trubinov;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Two-dimensional list,
 * each vertex corresponds to a list of vertices,
 * adjacent to the vertex.
 */
public class AdjacencyList implements Graph {
    private final HashMap<Vertex, Set<Vertex>> list = new HashMap<>();

    /**
     * Adding vertex to the graph.
     *
     * @param v vertex to add
     */
    @Override
    public void addVertex(Vertex v) {
        vertices.add(v);
        Set<Vertex> vertices = new HashSet<>();
        list.put(v, vertices);
    }

    /**
     * Removing vertex from the graph.
     *
     * @param v vertex to remove
     * @throws IllegalArgumentException if there is no v in matrix
     */
    @Override
    public void removeVertex(Vertex v) {
        if (!list.containsKey(v)) {
            throw new IllegalArgumentException();
        }
        vertices.remove(v);
        list.remove(v);
        for (Vertex vertex : list.keySet()) {
            list.get(vertex).remove(v);
        }
    }

    /**
     * Adding edge to the graph.
     *
     * @param e  edge to add
     * @param v1 tail vertex of e
     * @param v2 head vertex of e
     * @throws IllegalArgumentException if there is no v1 or v2 in matrix
     *                                  or v1 and v2 are the same vertex
     */
    @Override
    public void addEdge(Edge e, Vertex v1, Vertex v2) {
        if (!list.containsKey(v1) || !list.containsKey(v2) || v1 == v2) {
            throw new IllegalArgumentException();
        }
        edges.add(e);
        e.sourceVertex = v1;
        e.destVertex = v2;
        list.get(v1).add(v2);
    }

    /**
     * Removing edge from the graph.
     *
     * @param e  edge to remove
     * @param v1 tail vertex of e
     * @param v2 head vertex of e
     * @throws IllegalArgumentException if there is no v1 or v2 in matrix
     *                                  or v1 and v2 are the same vertex
     */
    @Override
    public void removeEdge(Edge e, Vertex v1, Vertex v2) {
        if (!list.containsKey(v1) || !list.containsKey(v2) || v1 == v2) {
            throw new IllegalArgumentException();
        }
        edges.remove(e);
        list.get(v1).remove(v2);
    }

    /**
     * Returns true if the edge from v1 to v2 exists.
     *
     * @param v1 tail vertex
     * @param v2 head vertex
     * @return true if the edge from v1 to v2 exists
     * @throws IllegalArgumentException if there is no v1 or v2 in matrix
     *                                  or v1 and v2 are the same vertex
     */
    @Override
    public boolean isEdge(Vertex v1, Vertex v2) {
        if (!list.containsKey(v1) || !list.containsKey(v2) || v1 == v2) {
            return false;
        }
        return list.get(v1).contains(v2);
    }
}
