package ru.nsu.fit.trubinov;

import java.util.HashMap;

public class IncidenceMatrix implements Graph {
    private final HashMap<Vertex, HashMap<Edge, Integer>> matrix = new HashMap<>();

    @Override
    public void addVertex(Vertex v) {
        vertices.add(v);
        HashMap<Edge, Integer> map = new HashMap<>();
        matrix.put(v, map);
    }

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
