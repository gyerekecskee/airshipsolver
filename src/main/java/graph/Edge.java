package graph;
import java.util.List;

public interface Edge<V> {
  List<Vertex<V>> getEndpoints();
}
