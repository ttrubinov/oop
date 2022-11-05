package ru.nsu.fit.trubinov;

@SuppressWarnings("unused")
public class Vertex {
    Object obj;

    public Vertex() {
        obj = null;
    }

    public Object getObject(Vertex v) {
        return v.obj;
    }

    public void changeObject(Vertex v, Object obj) {
        v.obj = obj;
    }
}
