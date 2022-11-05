package ru.nsu.fit.trubinov;

/**
 * Vertex of a graph, it contains any object.
 */
public class Vertex {
    private Object obj;

    public Vertex() {
        obj = null;
    }

    public Object getObject() {
        return obj;
    }

    public void changeObject(Object obj) {
        this.obj = obj;
    }
}
