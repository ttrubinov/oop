package ru.nsu.fit.trubinov;

@SuppressWarnings("unused")
public class Edge {
    private Object obj;
    private Integer weight;

    public Edge() {
        obj = null;
        weight = 0;
    }

    public Object getObject(Edge e) {
        return e.obj;
    }

    public void changeObject(Edge e, Object obj) {
        e.obj = obj;
    }

    public Integer getWeight(Edge e) {
        return e.weight;
    }

    public void changeWeight(Edge e, Integer weight) {
        e.weight = weight;
    }
}
