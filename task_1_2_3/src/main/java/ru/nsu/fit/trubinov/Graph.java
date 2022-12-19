package ru.nsu.fit.trubinov;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Integer.MAX_VALUE;

/**
 * Interface of weighted oriented graph with the shortest path algorithm.
 */
public abstract class Graph<V, E> {
    Set<Vertex<V>> vertices = new HashSet<>();
    Set<Edge<V, E>> edges = new HashSet<>();

    /**
     * Adding vertex to the graph.
     *
     * @param v vertex to add
     */
    public abstract void addVertex(Vertex<V> v);

    /**
     * Removing vertex from the graph.
     *
     * @param v vertex to remove
     */
    public abstract void removeVertex(Vertex<V> v);

    /**
     * Adding edge to the graph.
     *
     * @param e  edge to add
     * @param v1 tail vertex of e
     * @param v2 head vertex of e
     */
    public abstract void addEdge(Edge<V, E> e, Vertex<V> v1, Vertex<V> v2);

    /**
     * Removing edge from the graph.
     *
     * @param e  edge to remove
     * @param v1 tail vertex of e
     * @param v2 head vertex of e
     */
    public abstract void removeEdge(Edge<V, E> e, Vertex<V> v1, Vertex<V> v2);

    /**
     * Returns true if the edge from v1 to v2 exists.
     *
     * @param v1 tail vertex
     * @param v2 head vertex
     * @return true if the edge from v1 to v2 exists
     */
    public abstract boolean containsEdge(Vertex<V> v1, Vertex<V> v2);

    /**
     * Returns true if the edge with specified value exists.
     *
     * @param value that edge should have
     * @return true if the edge with specified value exists
     */
    public boolean containsEdge(E value) {
        for (var edge : edges) {
            if (edge.getObject() == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get edge by 2 vertices.
     *
     * @param v1 source vertex
     * @param v2 destination vertex
     * @return the edge
     */
    public Edge<V, E> getEdge(Vertex<V> v1, Vertex<V> v2) {
        for (var edge : edges) {
            if (edge.sourceVertex == v1 && edge.destVertex == v2) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Check if specified vertex exists in graph.
     *
     * @param v vertex to find
     * @return true if specified vertex exists in graph
     */
    public boolean containsVertex(Vertex<V> v) {
        for (var vertex : vertices) {
            if (vertex == v) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parsing input into graph.
     *
     * @param input input stream
     * @throws IOException if I/O error occurs
     */
    public abstract void inputParser(InputStream input) throws IOException;

    /**
     * Dijkstra's algorithm for finding the shortest path
     * between source vertex and other vertices.
     *
     * @param source source vertex
     * @return hashmap of distances between source vertex and others
     */
    public HashMap<Vertex<V>, Integer> shortestPaths(Vertex<V> source) {
        HashMap<Vertex<V>, Integer> dist = new HashMap<>();
        HashMap<Vertex<V>, Boolean> used = new HashMap<>();
        for (Vertex<V> vertex : vertices) {
            dist.put(vertex, MAX_VALUE);
            used.put(vertex, false);
        }
        dist.replace(source, 0);
        for (int i = 0; i < vertices.size(); i++) {
            Vertex<V> v = null;
            for (Vertex<V> j : vertices) {
                if (!used.get(j) && (v == null || dist.get(j) < dist.get(v))) {
                    v = j;
                }
            }
            if (dist.get(v) == MAX_VALUE) {
                break;
            }
            used.replace(v, true);
            for (Edge<V, E> e : edges) {
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
