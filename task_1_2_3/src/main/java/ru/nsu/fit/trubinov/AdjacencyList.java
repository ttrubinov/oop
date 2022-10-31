package ru.nsu.fit.trubinov;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class AdjacencyList<V extends Vertex, E extends Edge> implements Graph<V, E> {
    private final HashMap<Vertex, Set<Vertex>> list = new HashMap<>();

    @Override
    public boolean addVertex(Vertex v) {
        Set<Vertex> vertices = new HashSet<>();
        for (Edge edge : v.edges) {
            vertices.add(edge.v2);
        }
        list.put(v, vertices);
        return true;
    }

    @Override
    public void removeVertex(Vertex v) {
        if (!list.containsKey(v)) {
            throw new IllegalArgumentException();
        }
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
        e.v1 = v1;
        e.v2 = v2;
        v1.edges.add(e);
        v2.edges.add(e);
        list.get(v1).add(v2);
        return true;
    }

    @Override
    public boolean removeEdge(Edge e) {
        if (!list.containsKey(e.v1) || !list.containsKey(e.v2)) {
            throw new IllegalArgumentException();
        }
        e.v1.edges.remove((e));
        e.v2.edges.remove((e));
        list.get(e.v1).remove(e.v2);
        return true;
    }

    @Override
    public boolean isEdge(Vertex v1, Vertex v2) {
        return list.get(v1).contains(v2);
    }
}
