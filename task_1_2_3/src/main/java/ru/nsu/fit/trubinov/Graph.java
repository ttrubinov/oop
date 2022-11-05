package ru.nsu.fit.trubinov;

@SuppressWarnings("unused")
public interface Graph<V extends Vertex, E extends Edge> {
    boolean addVertex(Vertex v);

    void removeVertex(Vertex v);

    boolean addEdge(Edge e, Vertex v1, Vertex v2);

    boolean removeEdge(Edge e, Vertex v1, Vertex v2);

    boolean isEdge(Vertex v1, Vertex v2);
}
