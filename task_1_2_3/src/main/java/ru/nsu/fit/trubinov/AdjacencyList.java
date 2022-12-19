package ru.nsu.fit.trubinov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Two-dimensional list,
 * each vertex corresponds to a list of vertices,
 * adjacent to the vertex.
 */
public class AdjacencyList<V, E> extends Graph<V, E> {
    private final HashMap<Vertex<V>, Set<Vertex<V>>> list = new HashMap<>();

    /**
     * Adding vertex to the graph.
     *
     * @param v vertex to add
     */
    @Override
    public void addVertex(Vertex<V> v) {
        vertices.add(v);
        Set<Vertex<V>> vertices = new HashSet<>();
        list.put(v, vertices);
    }

    /**
     * Removing vertex from the graph.
     *
     * @param v vertex to remove
     * @throws IllegalArgumentException if there is no v in matrix
     */
    @Override
    public void removeVertex(Vertex<V> v) {
        if (!list.containsKey(v)) {
            throw new IllegalArgumentException();
        }
        vertices.remove(v);
        list.remove(v);
        for (Vertex<V> vertex : list.keySet()) {
            list.get(vertex).remove(v);
        }
    }

    /**
     * Adding edge to the graph.
     *
     * @param e  edge to add
     * @param v1 tail vertex of e
     * @param v2 head vertex of e
     * @throws IllegalArgumentException if there is no v1 or v2 in matrix
     *                                  or v1 and v2 are the same vertex
     */
    @Override
    public void addEdge(Edge<V, E> e, Vertex<V> v1, Vertex<V> v2) {
        if (!list.containsKey(v1) || !list.containsKey(v2) || v1 == v2) {
            throw new IllegalArgumentException();
        }
        edges.add(e);
        e.sourceVertex = v1;
        e.destVertex = v2;
        list.get(v1).add(v2);
    }

    /**
     * Removing edge from the graph.
     *
     * @param e  edge to remove
     * @param v1 tail vertex of e
     * @param v2 head vertex of e
     * @throws IllegalArgumentException if there is no v1 or v2 in matrix
     *                                  or v1 and v2 are the same vertex
     */
    @Override
    public void removeEdge(Edge<V, E> e, Vertex<V> v1, Vertex<V> v2) {
        if (!list.containsKey(v1) || !list.containsKey(v2) || v1 == v2) {
            throw new IllegalArgumentException();
        }
        edges.remove(e);
        list.get(v1).remove(v2);
    }

    /**
     * Returns true if the edge from v1 to v2 exists.
     *
     * @param v1 tail vertex
     * @param v2 head vertex
     * @return true if the edge from v1 to v2 exists
     * @throws IllegalArgumentException if there is no v1 or v2 in matrix
     *                                  or v1 and v2 are the same vertex
     */
    @Override
    public boolean containsEdge(Vertex<V> v1, Vertex<V> v2) {
        if (!list.containsKey(v1) || !list.containsKey(v2) || v1 == v2) {
            return false;
        }
        return list.get(v1).contains(v2);
    }

    /**
     * Parsing input into graph.
     *
     * @param input input stream
     * @throws IOException if I/O error occurs
     */
    @Override
    public void inputParser(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] a = line.split(" => ");
            if (a.length != 2) {
                throw new IOException("Invalid input");
            }
            Vertex<V> v1 = new Vertex<>();
            v1.changeObject((V) a[0]);
            if (!containsVertex(v1)) {
                addVertex(v1);
            }
            String[] values = a[1].split(" ");
            for (var val : values) {
                Vertex<V> v2 = new Vertex<>();
                v2.changeObject((V) val);
                if (!containsVertex(v2)) {
                    addVertex(v2);
                }
                Edge<V, E> e = new Edge<>();
                addEdge(e, v1, v2);
            }
        }
    }
}
