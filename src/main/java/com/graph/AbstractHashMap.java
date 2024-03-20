package com.graph;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V> {
  protected int n = 0;
  // number of entries in the dictionary
  protected int capacity;
  // length of the table
  private final int prime;
  // prime factor
  private final long scale;
  private final long shift;
  // the shift and scaling factors
  public AbstractHashMap(int cap, int p) {
    prime = p;
    capacity = cap;
    Random rand = new Random();
    scale = rand.nextInt(prime - 1) + 1;
    shift = rand.nextInt(prime);
    createTable();
  }
  public AbstractHashMap(int cap) { this(cap, 109345121); } // default prime
  // default capacity
  public AbstractHashMap() { this(17); }

  // public methods
  public int size() { return n; }
  public V get(Object key) { return bucketGet(hashValue((K) key), (K) key); }
  public V remove(Object key) { return bucketRemove(hashValue((K) key), (K) key); }
  public V put(K key, V value) {
    V answer = bucketPut(hashValue(key), key, value);
    if (n > capacity / 2)
      // keep load factor <= 0.5
      resize(2 * capacity - 1);
    // (or find a nearby prime)
    return answer;
  }
  // private utilities
  private int hashValue(K key) {
    return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
  }
  private void resize(int newCap) {
    ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);
    buffer.addAll(entrySet());
    capacity = newCap;
    createTable();
    // based on updated capacity
    n = 0;
    // will be recomputed while reinserting entries
    for (Entry<K,V> e : buffer)
      put(e.getKey(), e.getValue());
  }
  // protected abstract methods to be implemented by subclasses
  protected abstract void createTable();
  protected abstract V bucketGet(int h, K k);
  protected abstract V bucketPut(int h, K k, V v);
  protected abstract V bucketRemove(int h, K k);
}
