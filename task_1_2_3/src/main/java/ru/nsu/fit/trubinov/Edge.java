package ru.nsu.fit.trubinov;

/**
 * Weighted edge of a graph, it contains any object
 * and 2 vertices (source and destination vertex).
 */
public class Edge {
    private Object obj;
    public Vertex sourceVertex;
    public Vertex destVertex;
    private Integer weight;

    public Edge() {
        obj = null;
        weight = null;
    }

    public Object getObject() {
        return obj;
    }

    public void changeObject(Object obj) {
        this.obj = obj;
    }

    public Integer getWeight() {
        return weight;
    }

    public void changeWeight(Integer weight) {
        this.weight = weight;
    }
}
