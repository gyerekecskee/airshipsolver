package graph;

//import java.util.Map;

import airship.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A graph represented by an adjacency map.
 *
 * @param <V> values of the graph
 */
public class AdjacencyMapGraph<V> implements Graph<V> {

  private static class InnerVertex<V> implements Vertex<V> {
    private final V element;
    private Position<Vertex<V>> pos;
    private final List<Edge<V>> edges;


    /**
     * Constructs a new InnerVertex instance storing the given element.
     *
     * @param elem element of the node
     */
    public InnerVertex(V elem) {
      element = elem;
      edges = new ArrayList<>();
    }

    ///∗∗ Returns the element associated with the vertex. ∗/
    public V getElement() {
      return element;
    }

    ///∗∗ Stores the position of this vertex within the airshipg.graph's vertex list. ∗/
    public void setPosition(Position<Vertex<V>> p) {
      pos = p;
    }

    ///∗∗ Returns the position of this vertex within the airshipg.graph's vertex list. ∗/
    public Position<Vertex<V>> getPosition() {
      return pos;
    }

    ///∗∗ Returns reference to the underlying map of outgoing edges. ∗/
    public List<Edge<V>> getEdges() {
      return edges;
    }

    public String toString() {
      return "Vertex of: " + element;
    }
  }


  /**
   * An edge between two vertices.
   */
  private class InnerEdge implements Edge<V> {
    private Position<Edge<V>> pos;
    private final List<Vertex<V>> endpoints;


    /**
     * Constructs InnerEdge instance from u to v, storing the given element.
     *
     * @param u the first vertex
     * @param v the second vertex
     */
    public InnerEdge(Vertex<V> u, Vertex<V> v) {
      endpoints = new ArrayList<>(2);
      endpoints.add(u);
      endpoints.add(v);
      // array of length 2
    }

    /**
     * Returns reference to the endpoint array.
     *
     * @return reference to the endpoint array
     */
    public List<Vertex<V>> getEndpoints() {
      return endpoints;
    }

    /**
     * Stores the position of this edge within the airshipg.graph's vertex list.
     *
     * @param p position of this edge
     */
    public void setPosition(Position<Edge<V>> p) {
      pos = p;
    }

    /**
     * Returns the position of this edge within the airshipg.graph's vertex list.
     *
     * @return the position of this edge within the airshipg.graph's vertex list
     */
    public Position<Edge<V>> getPosition() {
      return pos;
    }

    @Override
    public String toString() {
      return "InnerEdge{"
          + ", endpoints=" + endpoints
          + '}';
    }
  }

  // nested InnerVertex and InnerEdge classes defined here...

  private final PositionalList<Vertex<V>> vertices = new LinkedPositionalList<>();

  private final PositionalList<Edge<V>> edges = new LinkedPositionalList<>();

  /**
   * Returns the number of vertices of the airshipg.graph
   *
   * @return the number of vertices of the airshipg.graph
   */
  public int numVertices() {
    return vertices.size();
  }

  /**
   * Returns the vertices of the graph as an iterable collection.
   *
   * @return the vertices of the graph as an iterable collection
   */
  public Iterable<Vertex<V>> vertices() {
    return vertices;
  }

  /**
   * Returns the number of edges of the graph.
   *
   * @return the number of edges of the graph
   */
  public int numEdges() {
    return edges.size();
  }

  /**
   * Returns the edges of the graph as an iterable collection.
   *
   * @return the edges of the graph
   */
  public Iterable<Edge<V>> edges() {
    return edges;
  }

  /**
   * Returns the number of edges for which vertex v is the origin.
   *
   * @param v the origin of the edges
   * @return the degree of v
   */
  public int degree(Vertex<V> v) {
    InnerVertex<V> vert = validate(v);
    return vert.getEdges().size();
  }

  /**
   * Returns an iterable collection of edges for which vertex v is the origin.
   *
   * @param v the origin of the edges
   * @return the edges outgoing from v
   */
  public Iterable<Edge<V>> outgoingEdges(Vertex<V> v) {
    InnerVertex<V> vert = validate(v);
    return vert.getEdges(); // edges are the values in the adjacency map
  }

  /**
   * Returns the edge from u to v, or null if they are not adjacent.
   *
   * @param u the first vertex
   * @param v the second vertex
   * @return the edge going from u to v
   */
  public Edge<V> getEdge(Vertex<V> u, Vertex<V> v) {
    InnerVertex<V> origin = validate(u);

    for (Edge<V> edge : origin.getEdges()) {
      if (edge.getEndpoints().contains(u) && edge.getEndpoints().contains(v)) {
        return edge; // Found the edge that connects u and v
      }
    }

    return null; // No edge found between u and v
  }

  /**
   * Returns the vertices of edge e as an array of length two.
   *
   * @param e the edge whose vertices we want to get
   * @return the two endpoints of e
   */
  public List<Vertex<V>> endVertices(Edge<V> e) {
    InnerEdge edge = validate(e);
    return edge.getEndpoints();
  }

  /**
   * Returns the vertex that is opposite vertex v on edge e.
   *
   * @param v the origin vertex
   * @param e an edge connected to v
   * @return the other vertex on e
   * @throws IllegalArgumentException if ve is not on edge e
   */
  public Vertex<V> opposite(Vertex<V> v, Edge<V> e) throws IllegalArgumentException {
    InnerEdge edge = validate(e);
    List<Vertex<V>> endpoints = edge.getEndpoints();
    if (endpoints.get(0) == v) {
      return endpoints.get(1);
    } else if (endpoints.get(1) == v) {
      return endpoints.get(0);
    } else {
      throw new IllegalArgumentException("v is not incident to this edge");
    }
  }

  /**
   * Inserts and returns a new vertex with the given element.
   *
   * @param element the element which we want to insert
   * @return the vertex with the element inside
   */
  public Vertex<V> insertVertex(V element) {
    InnerVertex<V> v = new InnerVertex<>(element);
    v.setPosition(vertices.addLast(v));
    return v;
  }

  /**
   * Inserts and returns a new edge between u and v, storing given element.
   *
   * @param u the first vertex
   * @param v the second vertex
   * @throws IllegalArgumentException if there is already an edge between them
   */
  public void insertEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
    if (getEdge(u, v) == null) {
      InnerEdge e = new InnerEdge(u, v);
      e.setPosition(edges.addLast(e));
      InnerVertex<V> origin = validate(u);
      InnerVertex<V> dest = validate(v);
      origin.getEdges().add(e);
      dest.getEdges().add(e);
    } else {
      throw new IllegalArgumentException("Edge from u to v already exists");
    }
  }

  /**
   * Removes a vertex and all its incident edges from the graph.
   *
   * @param v the vertex to removed
   */
  public void removeVertex(Vertex<V> v) {
    InnerVertex<V> vert = validate(v);
    // remove all incident edges from the airshipg.graph
    for (Edge<V> e : vert.getEdges()) {
      // remove this vertex from the list of vertices
      removeEdge(e);
    }
    vertices.remove(vert.getPosition());
  }

  /**
   * Validates the position and returns it as a node.
   *
   * @param v the vertex to validate
   * @return the validated vertex
   * @throws IllegalArgumentException if there is no such vertex
   */
  private InnerVertex<V> validate(Vertex<V> v) throws IllegalArgumentException {
    if (!(v instanceof InnerVertex<V>)) {
      throw new IllegalArgumentException("Invalid p");
    }
    return (InnerVertex<V>) v;
  }

  /**
   * Validates an edge, only checks if it is null.
   *
   * @param e the edge to validate
   * @return the validated edge
   * @throws IllegalArgumentException if the edge is null
   */
  private InnerEdge validate(Edge<V> e) throws IllegalArgumentException {
    if (e == null) {
      throw new IllegalArgumentException("Invalid edge");
    }
    // Safe cast:
    return (InnerEdge) e;
  }

  /**
   * Removes an edge.
   *
   * @param edge the edge to remove
   * @throws IllegalArgumentException if edge is null
   */
  public void removeEdge(Edge<V> edge) throws IllegalArgumentException {
    InnerEdge e = validate(edge);

    List<Vertex<V>> vertices = e.getEndpoints();

    // Remove vertices at edge endpoints from their incoming/outgoing list
    InnerVertex<V> v0 = validate(vertices.get(0));
    InnerVertex<V> v1 = validate(vertices.get(1));
    v0.getEdges().remove(v1);
    v1.getEdges().remove(v0);

    // Remove edge from list of edges
    edges.remove((Position<Edge<V>>) e);
  }

  /**
   * Constructs the path between u and w in a tree.
   *
   * @param u one end of the path
   * @param v the other en of the path
   * @param forest a tree containing u and v
   * @return the path between u and v
   */
  public PositionalList<Edge<V>> constructPath(Vertex<V> u, Vertex<V> v,
                                               Map<Vertex<V>, Edge<V>> forest) {
    PositionalList<Edge<V>> path = new LinkedPositionalList<>();
    // v was discovered during the search
    if (forest.get(v) != null) {
      // we construct the path from back to front
      Vertex<V> walk = v;
      while (walk != u) {
        Edge<V> edge = forest.get(walk);
        // add edge to *front* of path
        path.addFirst(edge);
        // repeat with opposite endpoint
        walk = opposite(walk, edge);
      }
    }
    return path;
  }

  /**
   * Does a depth first search through the graph.
   *
   * @param u the start of the search
   * @param known the already visited vertices
   * @param forest the result of the search so far
   */
  public void depthFirstSearch(Vertex<V> u, Set<Vertex<V>> known, Map<Vertex<V>, Edge<V>> forest) {
    // u has been discovered
    known.add(u);
    // for every outgoing edge from u
    for (Edge<V> e : u.getEdges()) {
      Vertex<V> v = this.opposite(u, e);
      if (!known.contains(v)) {
        // e is the tree edge that discovered v
        forest.put(v, e);
        // recursively explore from v
        depthFirstSearch(v, known, forest);
      }
    }
  }

  /**
   * Performs breadth-first search of Graph g starting at Vertex s.
   *
   * @param s the start of the search
   * @param known the already visited vertices
   * @param forest the result of the search
   */
  public void breadthFirstSearch(Vertex<V> s, Set<Vertex<V>> known, Map<Vertex<V>,
      Edge<V>> forest) {
    PositionalList<Vertex<V>> level = new LinkedPositionalList<>();
    known.add(s);
    // first level includes only s
    level.addLast(s);

    while (!level.isEmpty()) {
      PositionalList<Vertex<V>> nextLevel = new LinkedPositionalList<>();
      for (Vertex<V> u : level) {
        for (Edge<V> e : outgoingEdges(u)) {
          Vertex<V> v = opposite(u, e);
          if (!known.contains(v)) {
            known.add(v);
            // e is the tree edge that discovered v
            forest.put(v, e);
            // v will be further considered in next pass
            nextLevel.addLast(v);
          }
        }
      }
      // relabel ’next’ level to become the current
      level = nextLevel;
    }
  }

  /**
   * Checks if the given value is in the graph.
   *
   * @param v the value to check
   * @return true and the vertex of the value if found, false and null otherwise
   */
  public Pair<Boolean, Vertex<V>> contains(V v) {
    for (Vertex<V> vertex : vertices()) {
      if (vertex.getElement().equals(v)) {
        return new Pair<>(true, vertex);
      }
    }
    return new Pair<>(false, null);
  }
}