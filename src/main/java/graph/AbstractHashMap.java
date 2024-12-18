package graph;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Random;

/**
 * A custom Map class from the ADS book.
 *
 * @param <K> Key class
 * @param <V> Value class
 */
public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {
  protected int n = 0;
  // number of entries in the dictionary
  protected int capacity;
  // length of the table
  private final int prime;
  // prime factor
  private final long scale;
  private final long shift;
  // the shift and scaling factors

  /**
   * Constructor for AbstractHashMap.
   *
   * @param cap capacity of the map
   * @param p prime number used in determining the scale and shift of the map
   */
  public AbstractHashMap(int cap, int p) {
    prime = p;
    capacity = cap;
    Random rand = new Random();
    scale = rand.nextInt(prime - 1) + 1;
    shift = rand.nextInt(prime);
    createTable();
  }

  /**
   * Constructor without p input.
   *
   * @param cap capacity of the map
   */
  public AbstractHashMap(int cap) {
    this(cap, 109345121);
  }

  /**
   * Constructor with default capacity.
   */
  public AbstractHashMap() {
    this(17);
  }

  /**
   * Getter for size.
   *
   * @return the number of entries in the map
   */
  public int size() {
    return n;
  }

  /**
   * Getter for a value by a key.
   *
   * @param key the key whose associated value is to be returned
   * @return the value associated with the key
   */
  public V get(Object key) {
    return bucketGet(hashValue((K) key), (K) key);
  }

  /**
   * Removes a value from the map.
   *
   * @param key key whose mapping is to be removed from the map
   * @return the removed value
   */
  public V remove(Object key) {
    return bucketRemove(hashValue((K) key), (K) key);
  }

  /**
   * Puts a key value pair into the map.
   *
   * @param key key with which the specified value is to be associated
   * @param value value to be associated with the specified key
   * @return the key-value pair
   */
  public V put(K key, V value) {
    V answer = bucketPut(hashValue(key), key, value);
    if (n > capacity / 2) {
      resize(2 * capacity - 1);
    }
    // keep load factor <= 0.5
    // (or find a nearby prime)
    return answer;
  }

  /**
   * Calculates the hash value of a key.
   *
   * @param key whose hash value we want to know
   * @return the hash value of the key
   */
  private int hashValue(K key) {
    return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
  }

  /**
   * Resizes the map.
   *
   * @param newCap the new capacity which we want to achieve by the resizing
   */
  private void resize(int newCap) {
    ArrayList<Entry<K, V>> buffer = new ArrayList<>(n);
    buffer.addAll(entrySet());
    capacity = newCap;
    createTable();
    // based on updated capacity
    n = 0;
    // will be recomputed while reinserting entries
    for (Entry<K, V> e : buffer) {
      put(e.getKey(), e.getValue());
    }
  }

  protected abstract void createTable();

  protected abstract V bucketGet(int h, K k);

  protected abstract V bucketPut(int h, K k, V v);

  protected abstract V bucketRemove(int h, K k);
}
