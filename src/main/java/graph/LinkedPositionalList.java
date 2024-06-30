package graph;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class LinkedPositionalList<E> implements PositionalList<E> {

  @Override
  public void forEach(Consumer<? super E> action) {
    PositionalList.super.forEach(action);
  }

  @Override
  public Spliterator<E> spliterator() {
    return PositionalList.super.spliterator();
  }



  private class PositionIterator implements Iterator<Position<E>> {
    private Position<E> cursor = first( ); // position of the next element to report
    // position of last reported element
    private Position<E> recent = null;


    /**
     * Tests whether the iterator has a next object.
     * @return
     */
    public boolean hasNext( ) { return (cursor != null); }

    /**
     * Returns the next position in the iterator.
     * @return
     * @throws NoSuchElementException
     */
    public Position<E> next( ) throws NoSuchElementException {
      if (cursor == null) throw new NoSuchElementException("nothing left");
      recent = cursor;
      // element at this position might later be removed
      cursor = after(cursor);
      return recent;
    }

    /**
     * Removes the element returned by most recent call to next.
     * @throws IllegalStateException
     */
    public void remove( ) throws IllegalStateException {
      if (recent == null) throw new IllegalStateException("nothing to remove");
      LinkedPositionalList.this.remove(recent);
      // remove from outer list
      recent = null;
      // do not allow remove again until next is called
    }
  }
  private class PositionIterable implements Iterable<Position<E>> {
    public Iterator<Position<E>> iterator( ) { return new PositionIterator( ); }
  }

  /**
   * Returns an iterable representation of the list's positions.
   * @return
   */
  public Iterable<Position<E>> positions() {
    // create a new instance of the inner class
    return new PositionIterable( );
  }

  /**
   * This class adapts the iteration produced by positions() to return elements.
   */
  private class ElementIterator implements Iterator<E> {
    Iterator<Position<E>> posIterator = new PositionIterator( );
    public boolean hasNext( ) { return posIterator.hasNext( ); }
    public E next( ) { return posIterator.next( ).getElement( ); } // return element!
    public void remove( ) { posIterator.remove( ); }
  }

  /**
   * Returns an iterator of the elements stored in the list.
   * @return
   */
  public Iterator<E> iterator( ) { return new ElementIterator( ); }

  private static class Node<E> implements Position<E> {

    // reference to the element stored at this node
    private E element;


    // reference to the previous node in the list
    private Node<E> prev;


    // reference to the subsequent node in the list
    private Node<E> next;


    public Node(E e, Node<E> p, Node<E> n) {

      element = e;

      prev = p;
      next = n;

    }

    public E getElement( ) throws IllegalStateException {

      if (next == null)
// convention for defunct node

      throw new IllegalStateException("Position no longer valid");

      return element;

    }

    public Node<E> getPrev( ) {

      return prev;

    }

    public Node<E> getNext( ) {

      return next;

    }

    public void setElement(E e) {

      element = e;

    }

    public void setPrev(Node<E> p) {

      prev = p;

    }

    public void setNext(Node<E> n) {

      next = n;

    }

  }


// instance variables of the LinkedPositionalList

  // header sentinel
  private Node<E> header;


  // trailer sentinel
  private Node<E> trailer;


  // number of elements in the list
  private int size = 0;



  /**
   * Constructs a new empty list.
   */
  public LinkedPositionalList( ) {

    header = new Node<>(null, null, null);
// create header

    trailer = new Node<>(null, header, null);
// trailer is preceded by header

    header.setNext(trailer);
// header is followed by trailer

  }
// private utilities

  /**
   * Validates the position and returns it as a node.
   * @param p
   * @return
   * @throws IllegalArgumentException
   */
  private Node<E> validate(Position<E> p) throws IllegalArgumentException {
    if (!(p instanceof Node)) throw new IllegalArgumentException("Invalid p");
    // safe cast
    Node<E> node = (Node<E>) p;
    // convention for defunct node
    if (node.getNext( ) == null)
      throw new IllegalArgumentException("p is no longer in the list");
    return node;
  }

  /**
   * Returns the given node as a Position (or null, if it is a sentinel).
   * @param node
   * @return
   */
  private Position<E> position(Node<E> node) {
    if (node == header || node == trailer)
      return null;
    // do not expose user to the sentinels
    return node;
  }
// public accessor methods

  /**
   * Returns the number of elements in the linked list.
   * @return
   */
  public int size( ) { return size; }

  /**
   * Tests whether the linked list is empty.
   * @return
   */
  public boolean isEmpty( ) { return size == 0; }

  /**
   * Returns the first Position in the linked list (or null, if empty).
   * @return
   */
  public Position<E> first( ) {
    return position(header.getNext( ));
  }

  /**
   * Returns the last Position in the linked list (or null, if empty).
   * @return
   */
  public Position<E> last( ) {
    return position(trailer.getPrev( ));
  }
///∗∗ Returns the Position immediately before Position p (or null, if p is first). ∗/
  public Position<E> before(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    return position(node.getPrev( ));
  }
///∗∗ Returns the Position immediately after Position p (or null, if p is last). ∗/
  public Position<E> after(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    return position(node.getNext( ));
  }
  // private utilities
///∗∗ Adds element e to the linked list between the given nodes. ∗/
  private Position<E> addBetween(E e, Node<E> pred, Node<E> succ) {
    Node<E> newest = new Node<>(e, pred, succ); // create and link a new node
    pred.setNext(newest);
    succ.setPrev(newest);
    size++;
    return newest;
  }
// public update methods
///∗∗ Inserts element e at the front of the linked list and returns its new Position. ∗/
  public Position<E> addFirst(E e) {
    return addBetween(e, header, header.getNext( ));
// just after the header
  }
///∗∗ Inserts element e at the back of the linked list and returns its new Position. ∗/
  public Position<E> addLast(E e) {
    return addBetween(e, trailer.getPrev( ), trailer);
// just before the trailer
  }
///∗∗ Inserts element e immediately before Position p, and returns its new Position.∗/
  public Position<E> addBefore(Position<E> p, E e)
      throws IllegalArgumentException {
    Node<E> node = validate(p);
    return addBetween(e, node.getPrev( ), node);
  }
///∗∗ Inserts element e immediately after Position p, and returns its new Position. ∗/
  public Position<E> addAfter(Position<E> p, E e)
      throws IllegalArgumentException {
    Node<E> node = validate(p);
    return addBetween(e, node, node.getNext( ));
  }
///∗∗ Replaces the element stored at Position p and returns the replaced element. ∗/
  public E set(Position<E> p, E e) throws IllegalArgumentException {
    Node<E> node = validate(p);
    E answer = node.getElement( );
    node.setElement(e);
    return answer;
  }
//  /∗∗ Removes the element stored at Position p and returns it (invalidating p). ∗/

  public E remove(Position<E> p) throws IllegalArgumentException {

    Node<E> node = validate(p);

    Node<E> predecessor = node.getPrev( );

    Node<E> successor = node.getNext( );

    predecessor.setNext(successor);

    successor.setPrev(predecessor);

    size--;

    E answer = node.getElement( );

    node.setElement(null);
// help with garbage collection

    node.setNext(null);
// and convention for defunct node

    node.setPrev(null);

    return answer;

  }

  /**
   * Shouldn't be called lts of time, has O(size) complexity
   * @return
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    Node<E> finger = header.getNext();
    while (finger != trailer) {
      result.append(finger.element);
      finger = finger.getNext();
    }
    return result.toString();
  }
}