package graph;

import airship.Pair;
import java.util.Map;
import java.util.Set;

/**
 * Graph class.
 *
 * @param <V> the elements of the graphs
 */
public interface Graph<V> {
  Iterable<Vertex<V>> vertices();

  void insertEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException;

  Vertex<V> insertVertex(V element);

  Vertex<V> opposite(Vertex<V> v, Edge<V> e) throws IllegalArgumentException;

  void depthFirstSearch(Vertex<V> u, Set<Vertex<V>> known, Map<Vertex<V>, Edge<V>> forest);

  PositionalList<Edge<V>> constructPath(Vertex<V> u, Vertex<V> v, Map<Vertex<V>, Edge<V>> forest);

  int numVertices();

  Iterable<Edge<V>> outgoingEdges(Vertex<V> v);

  void breadthFirstSearch(Vertex<V> s, Set<Vertex<V>> known, Map<Vertex<V>, Edge<V>> forest);

  Pair<Boolean, Vertex<V>> contains(V v);
}
