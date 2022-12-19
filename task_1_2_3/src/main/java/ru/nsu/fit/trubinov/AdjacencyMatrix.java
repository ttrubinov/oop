package ru.nsu.fit.trubinov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Square matrix(vertices x vertices) of booleans,
 * matrix element Aij contains "true" if there is edge between Vi and Vj,
 * "false" otherwise.
 */
public class AdjacencyMatrix<V, E> extends Graph<V, E> {
    private final HashMap<Vertex<V>, HashMap<Vertex<V>, Boolean>> matrix = new HashMap<>();

    /**
     * Adding vertex to the graph.
     *
     * @param v vertex to add
     */
    @Override
    public void addVertex(Vertex<V> v) {
        vertices.add(v);
        HashMap<Vertex<V>, Boolean> map = new HashMap<>();
        matrix.put(v, map);
    }

    /**
     * Removing vertex from the graph.
     *
     * @param v vertex to remove
     * @throws IllegalArgumentException if there is no v in matrix
     */
    @Override
    public void removeVertex(Vertex<V> v) {
        if (!matrix.containsKey(v)) {
            throw new IllegalArgumentException();
        }
        vertices.remove(v);
        matrix.remove(v);
        for (Vertex<V> vertex : matrix.keySet()) {
            matrix.get(vertex).remove(v);
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
        if (!matrix.containsKey(v1) || !matrix.containsKey(v2) || v1 == v2) {
            throw new IllegalArgumentException();
        }
        e.sourceVertex = v1;
        e.destVertex = v2;
        edges.add(e);
        matrix.get(v1).put(v2, true);
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
        if (!matrix.containsKey(v1) || !matrix.containsKey(v2) || v1 == v2) {
            throw new IllegalArgumentException();
        }
        edges.remove(e);
        matrix.get(v1).put(v2, false);
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
        if (!matrix.containsKey(v1) || !matrix.containsKey(v2) || v1 == v2) {
            return false;
        }
        return matrix.get(v1).get(v2);
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
        int i = 0;
        while ((line = reader.readLine()) != null) {
            String[] weights = line.split(" ");
            int j = 0;
            for (var weight : weights) {
                Vertex<V> v1 = new Vertex<>();
                v1.changeObject((V) (i + ""));
                if (!containsVertex(v1)) {
                    addVertex(v1);
                }
                Vertex<V> v2 = new Vertex<>();
                v2.changeObject((V) (j + ""));
                if (!containsVertex(v2)) {
                    addVertex(v2);
                }
                Edge<V, E> e = new Edge<>();
                if (Integer.parseInt(weight) != 0) {
                    e.changeWeight(Integer.parseInt(weight));
                    addEdge(e, v1, v2);
                }
                j++;
            }
            i++;
        }
    }
}