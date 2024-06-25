package airshipg;

public enum Direction {
  DOWN,
  RIGHT,
  UP,
  LEFT;

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
