package ru.nsu.fit.trubinov;

import java.util.HashMap;

/**
 * Matrix(vertices x edges) of Integers,
 * matrix element Aij contains "1" if Vi is tail of Ej,
 * Aij contains "-1" if Vi is head of Ej,
 * "0" otherwise.
 */
public class IncidenceMatrix implements Graph {
    private final HashMap<Vertex, HashMap<Edge, Integer>> matrix = new HashMap<>();

    /**
     * Adding vertex to the graph.
     *
     * @param v vertex to add
     */
    @Override
    public void addVertex(Vertex v) {
        vertices.add(v);
        HashMap<Edge, Integer> map = new HashMap<>();
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
        for (Vertex vertex : matrix.keySet()) {
            for (Edge edge : matrix.get(v).keySet()) {
                matrix.get(vertex).remove(edge);
            }
        }
        matrix.remove(v);
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
        edges.add(e);
        e.sourceVertex = v1;
        e.destVertex = v2;
        matrix.get(v1).put(e, 1);
        matrix.get(v2).put(e, -1);
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
        for (Vertex vertex : matrix.keySet()) {
            matrix.get(vertex).remove(e);
        }
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
        HashMap<Edge, Integer> map = matrix.get(v1);
        for (Edge edge : map.keySet()) {
            if (matrix.get(v1).get(edge) == 1 && matrix.get(v2).get(edge) == -1) {
                return true;
            }
        }
        return false;
    }
}
