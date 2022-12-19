package ru.nsu.fit.trubinov;

/**
 * Weighted edge of a graph, it contains any object
 * and 2 vertices (source and destination vertex).
 */
public class Edge<V, E> {
    public Vertex<V> sourceVertex;
    public Vertex<V> destVertex;
    private E value;
    private Integer weight;

    public Edge() {
        value = null;
        weight = null;
    }

    public Object getObject() {
        return value;
    }

    public void changeObject(E value) {
        this.value = value;
    }

    public Integer getWeight() {
        return weight;
    }

    public void changeWeight(Integer weight) {
        this.weight = weight;
    }
}
