package ru.nsu.fit.trubinov;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Integer.MAX_VALUE;

@SuppressWarnings("unused")
public interface Graph<V extends Vertex, E extends Edge> {
    Set<Vertex> vertices = new HashSet<>();
    Set<Edge> edges = new HashSet<>();

    boolean addVertex(Vertex v);

    void removeVertex(Vertex v);

    boolean addEdge(Edge e, Vertex v1, Vertex v2);

    boolean removeEdge(Edge e, Vertex v1, Vertex v2);

    boolean isEdge(Vertex v1, Vertex v2);

    default HashMap<Vertex, Integer> shortestPaths(Vertex source) {
        HashMap<Vertex, Integer> dist = new HashMap<>();
        HashMap<Vertex, Boolean> used = new HashMap<>();
        for (Vertex vertex : vertices) {
            dist.put(vertex, MAX_VALUE);
            used.put(vertex, false);
        }
        dist.replace(source, 0);
        for (int i = 0; i < vertices.size(); i++) {
            Vertex v = null;
            for (Vertex j : vertices) {
                if (!used.get(j) && (v == null || dist.get(j) < dist.get(v))) {
                    v = j;
                }
            }
            used.replace(v, true);
            for (Edge e : edges) {
                if (e.sourceVertex == v) {
                    if (dist.get(v) + e.getWeight() < dist.get(e.destVertex)) {
                        dist.replace(e.destVertex, dist.get(v) + e.getWeight());
                    }
                }
            }
        }
        return dist;
    }
}
