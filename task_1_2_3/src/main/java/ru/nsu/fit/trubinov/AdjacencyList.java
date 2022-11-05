package ru.nsu.fit.trubinov;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class AdjacencyList<V extends Vertex, E extends Edge> implements Graph<V, E> {
    private final HashMap<Vertex, Set<Vertex>> list = new HashMap<>();

    @Override
    public boolean addVertex(Vertex v) {
        vertices.add(v);
        Set<Vertex> vertices = new HashSet<>();
        list.put(v, vertices);
        return true;
    }

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

    @Override
    public boolean addEdge(Edge e, Vertex v1, Vertex v2) {
        if (!list.containsKey(v1) || !list.containsKey(v2)) {
            throw new IllegalArgumentException();
        }
        edges.add(e);
        e.sourceVertex = v1;
        e.destVertex = v2;
        list.get(v1).add(v2);
        return true;
    }

    @Override
    public boolean removeEdge(Edge e, Vertex v1, Vertex v2) {
        if (!list.containsKey(v1) || !list.containsKey(v2)) {
            throw new IllegalArgumentException();
        }
        edges.remove(e);
        list.get(v1).remove(v2);
        return true;
    }

    @Override
    public boolean isEdge(Vertex v1, Vertex v2) {
        return list.get(v1).contains(v2);
    }
}
