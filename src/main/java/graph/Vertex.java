package graph;

import java.util.List;

/**
 * Class representing a Vertex of a graph.
 *
 * @param <V> value class
 */
public interface Vertex<V> {
  V getElement();

  List<Edge<V>> getEdges();
}
