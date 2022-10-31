package ru.nsu.fit.trubinov;

@SuppressWarnings("unused")
public interface Graph<V extends Vertex, E extends Edge> {
    boolean addVertex(V v);

    void removeVertex(V v);

    boolean addEdge(Edge e, Vertex v1, Vertex v2);

    boolean removeEdge(E e);

    boolean isEdge(V v1, V v2);
}
