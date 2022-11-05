package ru.nsu.fit.trubinov;

import java.util.HashMap;

@SuppressWarnings("unused")
public class IncidenceMatrix<V extends Vertex, E extends Edge> implements Graph<V, E> {
    private final HashMap<Vertex, HashMap<Edge, Integer>> matrix = new HashMap<>();

    @Override
    public boolean addVertex(Vertex v) {
        HashMap<Edge, Integer> map = new HashMap<>();
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
            for (Edge edge : matrix.get(v).keySet()) {
                matrix.get(vertex).remove(edge);
            }
        }
    }

    @Override
    public boolean addEdge(Edge e, Vertex v1, Vertex v2) {
        if (!matrix.containsKey(v1) || !matrix.containsKey(v2)) {
            throw new IllegalArgumentException();
        }
        matrix.get(v1).put(e, 1);
        matrix.get(v2).put(e, -1);
        return true;
    }

    @Override
    public boolean removeEdge(Edge e, Vertex v1, Vertex v2) {
        if (!matrix.containsKey(v1) || !matrix.containsKey(v2)) {
            throw new IllegalArgumentException();
        }
        for (Vertex vertex : matrix.keySet()) {
            matrix.get(vertex).remove(e);
        }
        return true;
    }

    @Override
    public boolean isEdge(Vertex v1, Vertex v2) {
        HashMap<Edge, Integer> map = matrix.get(v1);
        for (Edge edge : map.keySet()) {
            if (matrix.get(v1).get(edge) == 1 && matrix.get(v2).get(edge) == -1) {
                return true;
            }
        }
        return false;
    }
}
