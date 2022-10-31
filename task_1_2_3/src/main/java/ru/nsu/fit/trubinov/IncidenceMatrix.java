package ru.nsu.fit.trubinov;

import java.util.HashMap;

@SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
public class IncidenceMatrix<V extends Vertex, E extends Edge> implements Graph<V, E> {
    private final HashMap<Vertex, HashMap<Edge, Integer>> matrix = new HashMap<>();

    @Override
    public boolean addVertex(Vertex v) {
        HashMap<Edge, Integer> map = new HashMap<>();
        for (Edge edge : v.edges) {
            map.put(edge, 1);
            matrix.get(edge.v2).put(edge, -1);
        }
        matrix.put(v, map);
        return true;
    }

    @Override
    public void removeVertex(Vertex v) {
        if (!matrix.containsKey(v)) {
            throw new IllegalArgumentException();
        }
        matrix.remove(v);
        for (Vertex vertex : matrix.keySet()) {
            HashMap<Edge, Integer> map = matrix.get(vertex);
            for (Edge edge : map.keySet()) {
                matrix.get(vertex).remove(edge);
            }
        }
    }

    @Override
    public boolean addEdge(Edge e, Vertex v1, Vertex v2) {
        return false;
    }

    @Override
    public boolean removeEdge(Edge e) {
        return false;
    }

    @Override
    public boolean isEdge(Vertex v1, Vertex v2) {
        return false;
    }
}
