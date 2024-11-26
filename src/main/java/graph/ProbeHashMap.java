package graph;

import java.util.HashSet;
import java.util.Set;

/**
 * A map class using probing.
 *
 * @param <K> key class
 * @param <V> value class
 */
public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
  // a fixed array of entries (all initially null)
  private SimpleEntry<K, V>[] table;
  //sentinel
  private final SimpleEntry<K, V> DEFUNCT = new SimpleEntry<>(null, null);

  public ProbeHashMap() {
    super();
  }

  public ProbeHashMap(int cap) {
    super(cap);
  }

  public ProbeHashMap(int cap, int p) {
    super(cap, p);
  }

  /**
   * Creates an empty table having length equal to current capacity.
   */
  protected void createTable() {
    // safe cast
    table = (SimpleEntry<K, V>[ ]) new SimpleEntry[capacity];
  }

  /**
   * Returns true if location is either empty or the "defunct" sentinel.
   *
   * @param j index of location
   * @return true if empty or defunct, false otherwise
   */
  private boolean isAvailable(int j) {
    return (table[j] == null || table[j] == DEFUNCT);
  }

  /**
   * Returns index with key k, or −(a+1) such that k could be added at index a.
   *
   * @param h hash value
   * @param k key
   * @return index with key k
   */
  private int findSlot(int h, K k) {
    int avail = - 1;
    // no slot available (thus far)
    int j = h;
    // index while scanning table
    do {
      if (isAvailable(j)) {
        // may be either empty or defunct
        if (avail == -1) {
          avail = j;
        }
        // this is the first available slot!
        if (table[j] == null) {
          break;
        }
        // if empty, search fails immediately

      } else if (table[j].getKey().equals(k)) {
        return j;
      }
      // successful match
      j = (j + 1) % capacity;
      // keep looking (cyclically)
    } while (j != h);
    // stop if we return to the start
    return -(avail + 1);
    // search has failed
  }

  /**
   * Returns value associated with key k in bucket with hash value h, or else null.
   *
   * @param h hash value
   * @param k key
   * @return value of key k
   */
  protected V bucketGet(int h, K k) {
    int j = findSlot(h, k);
    if (j < 0) {
      return null;
    }
    // no match found
    return table[j].getValue();
  }

  /**
   * Associates key k with value v in bucket with hash value h; returns old value.
   *
   * @param h hash value
   * @param k key
   * @param v value
   * @return the old value
   */
  protected V bucketPut(int h, K k, V v) {
    int j = findSlot(h, k);
    // this key has an existing entry
    if (j >= 0) {
      return table[j].setValue(v);
    }
    // convert to proper index
    table[-(j + 1)] = new SimpleEntry<>(k, v);
    n++;
    return null;
  }

  /**
   * Removes entry having key k from bucket with hash value h (if any).
   *
   * @param h hash
   * @param k key
   * @return the removed value
   */
  protected V bucketRemove(int h, K k) {
    int j = findSlot(h, k);
    if (j < 0) {
      return null;
    }
    // nothing to remove
    V answer = table[j].getValue();
    table[j] = DEFUNCT;
    // mark this slot as deactivated
    n--;
    return answer;
  }

  /**
   * Returns an iterable collection of all key-value entries of the map.
   *
   * @return set of all key-value entries
   */
  public Set<Entry<K, V>> entrySet() {
    Set<Entry<K, V>> buffer = new HashSet<>();
    for (int h = 0; h < capacity; h++) {
      if (!isAvailable(h)) {
        buffer.add(table[h]);
      }
    }
    return buffer;
  }
}