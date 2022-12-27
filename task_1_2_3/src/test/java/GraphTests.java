import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.fit.trubinov.*;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTests {
    public static Stream<Graph<Integer, Integer>> graphRepresentations() {
        return Stream.of(new AdjacencyMatrix<>(), new AdjacencyList<>(), new IncidenceMatrix<>());
    }

    @ParameterizedTest
    @MethodSource("graphRepresentations")
    void testGraphRepresentations(Graph<Integer, Integer> graph) {
        Vertex<Integer> v1 = new Vertex<>();
        Vertex<Integer> v2 = new Vertex<>();
        graph.addVertex(v1);
        graph.addVertex(v2);
        Edge<Integer, Integer> e = new Edge<>();
        e.changeObject(5);
        graph.addEdge(e, v1, v2);
        assertTrue(graph.containsEdge(v1, v2));
        assertTrue(graph.containsEdge(5));
        assertEquals(e, graph.getEdge(v1, v2));
        graph.removeEdge(e, v1, v2);
        assertFalse(graph.containsEdge(v1, v2));
        assertTrue(graph.containsVertex(v1));
        assertTrue(graph.containsVertex(v2));
        graph.addEdge(e, v1, v2);
        graph.removeVertex(v1);
        assertFalse(graph.containsEdge(v1, v2));
        Vertex<Integer> v = new Vertex<>();
        assertFalse(graph.containsEdge(v, v));
        assertFalse(graph.containsEdge(v2, v2));
        assertFalse(graph.containsVertex(v1));
    }

    @ParameterizedTest
    @MethodSource("graphRepresentations")
    void testGraphRepresentationsExceptions(Graph<Integer, Integer> graph) {
        Vertex<Integer> v = new Vertex<>();
        Edge<Integer, Integer> e1 = new Edge<>();
        assertThrows(IllegalArgumentException.class, () -> graph.removeVertex(v));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(e1, v, v));
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge(e1, v, v));
    }

    @Test
    void testVertex() {
        Vertex<Integer> v = new Vertex<>();
        Integer val = 5;
        v.changeObject(val);
        assertEquals(val, v.getObject());
    }

    @Test
    void testEdge() {
        Edge<Integer, Integer> e = new Edge<>();
        Integer val = 5;
        Integer weight = 100500;
        e.changeObject(val);
        assertEquals(val, e.getObject());
        e.changeWeight(weight);
        assertEquals(weight, e.getWeight());
    }

    @ParameterizedTest
    @MethodSource("graphRepresentations")
    void testDijkstra(Graph<Integer, Integer> graph) {
        Vertex<Integer> v1 = new Vertex<>();
        Vertex<Integer> v2 = new Vertex<>();
        Vertex<Integer> v3 = new Vertex<>();
        Vertex<Integer> v4 = new Vertex<>();
        Vertex<Integer> v5 = new Vertex<>();
        Vertex<Integer> v6 = new Vertex<>();
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);                    //  Testing Dijkstra on this graph:
        graph.addVertex(v5);                    //
        graph.addVertex(v6);                    //             V5
        Edge<Integer, Integer> e1 = new Edge<>();        //          /     \
        graph.addEdge(e1, v1, v2);              //       9          6
        e1.changeWeight(7);                     //     /               \
        Edge<Integer, Integer> e2 = new Edge<>();        //    V6 - 2 - V3 - 11 - V4
        graph.addEdge(e2, v2, v3);              //     |      /  \       |
        e2.changeWeight(10);                    //    14     9    10     15
        Edge<Integer, Integer> e3 = new Edge<>();        //     \    /      \     /
        graph.addEdge(e3, v1, v3);              //       V1 --- 7 --- V2
        e3.changeWeight(9);
        Edge<Integer, Integer> e4 = new Edge<>();
        graph.addEdge(e4, v2, v4);
        e4.changeWeight(15);
        Edge<Integer, Integer> e5 = new Edge<>();
        graph.addEdge(e5, v3, v4);
        e5.changeWeight(11);
        Edge<Integer, Integer> e6 = new Edge<>();
        graph.addEdge(e6, v4, v5);
        e6.changeWeight(6);
        Edge<Integer, Integer> e7 = new Edge<>();
        graph.addEdge(e7, v5, v6);
        e7.changeWeight(9);
        Edge<Integer, Integer> e8 = new Edge<>();
        graph.addEdge(e8, v1, v6);
        e8.changeWeight(14);
        Edge<Integer, Integer> e9 = new Edge<>();
        graph.addEdge(e9, v3, v6);
        e9.changeWeight(2);
        Edge<Integer, Integer> e10 = new Edge<>();
        graph.addEdge(e10, v6, v5);
        e10.changeWeight(9);
        HashMap<Vertex<Integer>, Integer> res = graph.shortestPaths(v1);
        assertEquals(0, res.get(v1));
        assertEquals(7, res.get(v2));
        assertEquals(9, res.get(v3));
        assertEquals(20, res.get(v4));
        assertEquals(20, res.get(v5));
        assertEquals(11, res.get(v6));
    }
}
