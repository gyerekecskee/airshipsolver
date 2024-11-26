package graph;

import java.util.List;

/**
 * Interface for an edge in a graph.
 *
 * @param <V> the elements of the graph
 */
public interface Edge<V> {
  List<Vertex<V>> getEndpoints();
}
