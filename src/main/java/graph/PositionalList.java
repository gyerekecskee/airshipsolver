package graph;

/**
 * PositionalList.
 *
 * @param <E> elements of the positionalList
 */
public interface PositionalList<E> extends Iterable<E> {
  

  /**
   * Returns the number of elements in the list.
   *
   * @return the number of elements in the list
   */
  int size();

  /**
   * Tests whether the list is empty.
   *
   * @return true if the list is empty, false otherwise
   */
  boolean isEmpty();

  /**
   * Returns the first Position in the list (or null, if empty).
   *
   * @return the first Position in the list (or null if empty).
   */
  Position<E> first();

  /**
   * Returns the last Position in the list (or null, if empty).
   *
   * @return the last Position in the list (or null if empty)
   */
  Position<E> last();

  /**
   * Returns the Position immediately before Position p (or null, if p is first).
   *
   * @param p position after our position
   * @return the position before b
   * @throws IllegalArgumentException if p is first
   */
  Position<E> before(Position<E> p) throws IllegalArgumentException;

  /**
   * Returns the Position immediately after Position p (or null, if p is last).
   *
   * @param p position before our position
   * @return the position after our position
   * @throws IllegalArgumentException if p is last
   */
  Position<E> after(Position<E> p) throws IllegalArgumentException;

  /**
   * Inserts element e at the front of the list and returns its new Position.
   *
   * @param e element which we want to add
   * @return the elements position
   */
  Position<E> addFirst(E e);

  /**
   * Inserts element e at the back of the list and returns its new Position.
   *
   * @param e element which we want to add
   * @return the elements position
   */
  Position<E> addLast(E e);

  /**
   * Inserts element e immediately before Position p and returns its new Position.
   *
   * @param p position
   * @param e element
   * @return new position
   * @throws IllegalArgumentException if p position doesn't exist
   */
  Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException;

  /**
   * Inserts element e immediately after Position p and returns its new Position.
   *
   * @param p position
   * @param e element
   * @return the new position
   * @throws IllegalArgumentException if position p doesn't exist
   */
  Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException;

  /**
   * Replaces the element stored at Position p and returns the replaced element.
   *
   * @param p position
   * @param e element
   * @return replaced element
   * @throws IllegalArgumentException if p doesn't exist
   */
  E set(Position<E> p, E e) throws IllegalArgumentException;

  /**
   * Removes the element stored at Position p and returns it (invalidating p).
   *
   * @param p position
   * @return the removed element
   * @throws IllegalArgumentException if p doesn't exist
   */
  E remove(Position<E> p) throws IllegalArgumentException;
}
