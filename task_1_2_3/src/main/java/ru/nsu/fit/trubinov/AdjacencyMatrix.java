package ru.nsu.fit.trubinov;

import java.util.HashMap;

@SuppressWarnings("unused")
public class AdjacencyMatrix<V extends Vertex, E extends Edge> implements Graph<V, E> {
    private final HashMap<Vertex, HashMap<Vertex, Boolean>> matrix = new HashMap<>();

    @Override
    public boolean addVertex(Vertex v) {
        HashMap<Vertex, Boolean> map = new HashMap<>();
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
            matrix.get(vertex).remove(v);
        }
    }

    @Override
    public boolean addEdge(Edge e, Vertex v1, Vertex v2) {
        if (!matrix.containsKey(v1) || !matrix.containsKey(v2)) {
            throw new IllegalArgumentException();
        }
        matrix.get(v1).put(v2, true);
        return true;
    }

    @Override
    public boolean removeEdge(Edge e, Vertex v1, Vertex v2) {
        if (!matrix.containsKey(v1) || !matrix.containsKey(v2)) {
            throw new IllegalArgumentException();
        }
        matrix.get(v1).put(v2, false);
        return false;
    }

    @Override
    public boolean isEdge(Vertex v1, Vertex v2) {
        return matrix.get(v1).get(v2);
    }
}