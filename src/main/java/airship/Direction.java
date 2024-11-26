package airship;

/**
 * Class representing the four directions.
 */
public enum Direction {
  DOWN,
  RIGHT,
  UP,
  LEFT;

  /**
   * Returns the opposite direction.
   *
   * @return the opposite direction
   */
  public Direction opposite() {
    if (this.equals(UP)) {
      return DOWN;
    } else if (this.equals(DOWN)) {
      return UP;
    } else if (this.equals(RIGHT)) {
      return LEFT;
    } else {
      return RIGHT;
    }
  }
}
