package ru.nsu.fit.trubinov;

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
