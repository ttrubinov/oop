package ru.nsu.fit.trubinov;

import java.util.HashMap;

/**
 * Square matrix(vertices x vertices) of booleans,
 * matrix element Aij contains "true" if there is edge between Vi and Vj,
 * "false" otherwise.
 */
public class AdjacencyMatrix implements Graph {
    private final HashMap<Vertex, HashMap<Vertex, Boolean>> matrix = new HashMap<>();

    /**
     * Adding vertex to the graph.
     *
     * @param v vertex to add
     */
    @Override
    public void addVertex(Vertex v) {
        vertices.add(v);
        HashMap<Vertex, Boolean> map = new HashMap<>();
        matrix.put(v, map);
    }

    /**
     * Removing vertex from the graph.
     *
     * @param v vertex to remove
     * @throws IllegalArgumentException if there is no v in matrix
     */
    @Override
    public void removeVertex(Vertex v) {
        if (!matrix.containsKey(v)) {
            throw new IllegalArgumentException();
        }
        vertices.remove(v);
        matrix.remove(v);
        for (Vertex vertex : matrix.keySet()) {
            matrix.get(vertex).remove(v);
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
        if (!matrix.containsKey(v1) || !matrix.containsKey(v2) || v1 == v2) {
            throw new IllegalArgumentException();
        }
        e.sourceVertex = v1;
        e.destVertex = v2;
        edges.add(e);
        matrix.get(v1).put(v2, true);
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
        if (!matrix.containsKey(v1) || !matrix.containsKey(v2) || v1 == v2) {
            throw new IllegalArgumentException();
        }
        edges.remove(e);
        matrix.get(v1).put(v2, false);
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
        if (!matrix.containsKey(v1) || !matrix.containsKey(v2) || v1 == v2) {
            return false;
        }
        return matrix.get(v1).get(v2);
    }
}