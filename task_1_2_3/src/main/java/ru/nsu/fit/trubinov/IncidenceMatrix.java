package ru.nsu.fit.trubinov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Matrix(vertices x edges) of Integers,
 * matrix element Aij contains "1" if Vi is tail of Ej,
 * Aij contains "-1" if Vi is head of Ej,
 * "0" otherwise.
 */
public class IncidenceMatrix<V, E> extends Graph<V, E> {
    private final HashMap<Vertex<V>, HashMap<Edge<V, E>, Integer>> matrix = new HashMap<>();

    /**
     * Adding vertex to the graph.
     *
     * @param v vertex to add
     */
    @Override
    public void addVertex(Vertex<V> v) {
        vertices.add(v);
        HashMap<Edge<V, E>, Integer> map = new HashMap<>();
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
        for (Vertex<V> vertex : matrix.keySet()) {
            for (Edge<V, E> edge : matrix.get(v).keySet()) {
                matrix.get(vertex).remove(edge);
            }
        }
        matrix.remove(v);
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
        edges.add(e);
        e.sourceVertex = v1;
        e.destVertex = v2;
        matrix.get(v1).put(e, 1);
        matrix.get(v2).put(e, -1);
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
        for (Vertex<V> vertex : matrix.keySet()) {
            matrix.get(vertex).remove(e);
        }
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
        HashMap<Edge<V, E>, Integer> map = matrix.get(v1);
        for (Edge<V, E> edge : map.keySet()) {
            if (matrix.get(v1).get(edge) == 1 && matrix.get(v2).get(edge) == -1) {
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
    @Override
    public void inputParser(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line;
        int i = 0;
        while ((line = reader.readLine()) != null) {
            String[] weights = line.split(" ");
            int j = 0;
            for (var weight : weights) {
                Vertex<V> v = new Vertex<>();
                v.changeObject((V) (i + ""));
                if (!containsVertex(v)) {
                    addVertex(v);
                }
                Edge<V, E> e = new Edge<>();
                e.changeObject((E) (j + ""));
                if (Integer.parseInt(weight) > 0) {
                    e.changeWeight(Integer.parseInt(weight));
                    addEdge(e, v, null);
                } else if (Integer.parseInt(weight) < 0) {
                    e.destVertex = v;
                }
                j++;
            }
            i++;
        }
    }
}
