package ru.nsu.fit.trubinov;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Vertex {
    Object obj;
    ArrayList<Edge> edges;

    public Vertex() {
        obj = null;
        edges = new ArrayList<>();
    }

    public Object getObject(Vertex v) {
        return v.obj;
    }

    public void changeObject(Vertex v, Object obj) {
        v.obj = obj;
    }
}
