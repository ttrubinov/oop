package ru.nsu.fit.trubinov;

import java.util.HashMap;

public class AdjacencyMatrix implements Graph {
    private final HashMap<Vertex, HashMap<Vertex, Boolean>> matrix = new HashMap<>();

    @Override
    public void addVertex(Vertex v) {
        vertices.add(v);
        HashMap<Vertex, Boolean> map = new HashMap<>();
        matrix.put(v, map);
    }

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

    @Override
    public void removeEdge(Edge e, Vertex v1, Vertex v2) {
        if (!matrix.containsKey(v1) || !matrix.containsKey(v2) || v1 == v2) {
            throw new IllegalArgumentException();
        }
        edges.remove(e);
        matrix.get(v1).put(v2, false);
    }

    @Override
    public boolean isEdge(Vertex v1, Vertex v2) {
        if (!matrix.containsKey(v1) || !matrix.containsKey(v2) || v1 == v2) {
            return false;
        }
        return matrix.get(v1).get(v2);
    }
}