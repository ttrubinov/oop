package ru.nsu.fit.trubinov;

@SuppressWarnings("unused")
public class Vertex {
    Object obj;

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
