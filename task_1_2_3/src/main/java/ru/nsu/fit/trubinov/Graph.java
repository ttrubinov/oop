package ru.nsu.fit.trubinov;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Integer.MAX_VALUE;

/**
 * Interface of weighted oriented graph with the shortest path algorithm.
 */
public interface Graph {
    Set<Vertex> vertices = new HashSet<>();
    Set<Edge> edges = new HashSet<>();

    /**
     * Adding vertex to the graph.
     *
     * @param v vertex to add
     */
    void addVertex(Vertex v);

    /**
     * Removing vertex from the graph.
     *
     * @param v vertex to remove
     */
    void removeVertex(Vertex v);

    /**
     * Adding edge to the graph.
     *
     * @param e  edge to add
     * @param v1 tail vertex of e
     * @param v2 head vertex of e
     */
    void addEdge(Edge e, Vertex v1, Vertex v2);

    /**
     * Removing edge from the graph.
     *
     * @param e  edge to remove
     * @param v1 tail vertex of e
     * @param v2 head vertex of e
     */
    void removeEdge(Edge e, Vertex v1, Vertex v2);

    /**
     * Returns true if the edge from v1 to v2 exists.
     *
     * @param v1 tail vertex
     * @param v2 head vertex
     * @return true if the edge from v1 to v2 exists
     */
    boolean isEdge(Vertex v1, Vertex v2);


    /**
     * Dijkstra's algorithm for finding the shortest path
     * between source vertex and other vertices.
     *
     * @param source source vertex
     * @return hashmap of distances between source vertex and others
     */
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
            if (dist.get(v) == MAX_VALUE) {
                break;
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
