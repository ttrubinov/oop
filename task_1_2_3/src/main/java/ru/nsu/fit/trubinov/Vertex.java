package ru.nsu.fit.trubinov;

/**
 * Vertex of a graph, it contains any object.
 */
public class Vertex<V> {
    private V value;

    public Vertex() {
        value = null;
    }

    public Object getObject() {
        return value;
    }

    public void changeObject(V value) {
        this.value = value;
    }
}
