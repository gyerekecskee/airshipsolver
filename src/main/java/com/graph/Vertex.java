package com.graph;

import java.util.List;

public interface Vertex<V> {
  V getElement();
  List<Edge<V>> getEdges();
}
