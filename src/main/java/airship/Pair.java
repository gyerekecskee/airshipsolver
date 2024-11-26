package airship;

import java.io.Serializable;

/**
 * A pair class.
 *
 * @param <K> first item
 * @param <V> second item
 * @param first TODO refactor into coordinate
 */
public record Pair<K, V>(K first, V second) implements Serializable {

}
