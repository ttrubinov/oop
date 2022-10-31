package ru.nsu.fit.trubinov;

import java.util.HashMap;

@SuppressWarnings("unused")
public class AdjacencyMatrix<V extends Vertex, E extends Edge> implements Graph<V, E> {
    private final HashMap<Vertex, HashMap<Vertex, Boolean>> matrix = new HashMap<>();

    @Override
    public boolean addVertex(Vertex v) {
        HashMap<Vertex, Boolean> map = new HashMap<>();
        for (Edge edge : v.edges) {
            map.put(edge.v2, true);
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
            matrix.get(vertex).remove(v);
        }
    }

    @Override
    public boolean addEdge(Edge e, Vertex v1, Vertex v2) {
        if (!matrix.containsKey(e.v1) || !matrix.containsKey(e.v2)) {
            throw new IllegalArgumentException();
        }
        e.v1 = v1;
        e.v2 = v2;
        v1.edges.add(e);
        v2.edges.add(e);
        matrix.get(v1).put(v2, true);
        return true;
    }

    @Override
    public boolean removeEdge(Edge e) {
        if (!matrix.containsKey(e.v1) || !matrix.containsKey(e.v2)) {
            throw new IllegalArgumentException();
        }
        e.v1.edges.remove((e));
        e.v2.edges.remove((e));
        matrix.get(e.v1).put(e.v2, false);
        return false;
    }

    @Override
    public boolean isEdge(Vertex v1, Vertex v2) {
        return matrix.get(v1).get(v2);
    }
}