package airship;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class representing tiles on the board.
 */
public class Tile implements Serializable {
  private final TileType type;
  private Coordinate position;
  private List<Coordinate> relativeRotatedObstacles;
  private int rotation;

  public int getRotation() {
    return rotation;
  }

  /**
   * Constructor for Tile.
   *
   * @param type of the tile
   * @param degree of rotations of the tile
   */
  public Tile(TileType type, int degree) {
    if (degree % 90 != 0) {
      throw new IllegalArgumentException("degree must be a multiple of 90");
    }
    this.type = type;
    this.rotation = degree;
    relativeRotatedObstacles = rotate(type.getObstacles(), degree);
  }

  public TileType getType() {
    return type;
  }

  /**
   * Setter for position.
   *
   * @param xPosition the x coordinate of the position
   * @param yPosition the y coordinate of the position
   */
  public void setPositionInGrid(int xPosition, int yPosition) {
    this.position = new Coordinate(xPosition, yPosition);
  }

  public Coordinate getPosition() {
    return position;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tile tile = (Tile) o;
    return type == tile.type && Objects.equals(position, tile.position)
        && Objects.equals(relativeRotatedObstacles, tile.relativeRotatedObstacles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, position, relativeRotatedObstacles);
  }

  @Override
  public String toString() {
    StringBuilder out = new StringBuilder();
    int maxY = Integer.MIN_VALUE;
    int minY = Integer.MAX_VALUE;
    int minX = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    for (Coordinate coordinate : relativeRotatedObstacles) {
      maxY = Math.max(maxY, coordinate.getY());
      minY = Math.min(minY, coordinate.getY());
      maxX = Math.max(maxX, coordinate.getX());
      minX = Math.min(minX, coordinate.getX());
    }
    minY = Math.min(minY, 0);
    maxY = Math.max(maxY, 1);
    minX = Math.min(minX, 0);
    maxX = Math.max(maxX, 1);
    for (int i = maxY; i >= minY; i--) {
      for (int j = minX; j <= maxX; j++) {
        if (relativeRotatedObstacles.contains(new Coordinate(j, i))) {
          out.append("x");
        } else {
          out.append("0");
        }
      }
      out.append("\n");
    }
    out = new StringBuilder(out.substring(0, out.length() - 1));
    return out.toString();
  }

  private List<Coordinate> rotateOnce(List<Coordinate> originalCoordinates) {
    return originalCoordinates.stream().map(pair -> new Coordinate(-pair.getY() + 1,
        pair.getX())).collect(Collectors.toList());

  }

  /**
   * Rotates the tile by some degrees.
   *
   * @param degree the number of degrees which we want to rotate the tile by
   * @throws IllegalArgumentException if degree isn't a multiple of 90
   */
  public void rotate(int degree) throws IllegalArgumentException {
    if (degree % 90 != 0) {
      throw new IllegalArgumentException("the degrees must be a multiple of 90");
    }
    while (degree > 0) {
      relativeRotatedObstacles = rotateOnce(relativeRotatedObstacles);
      degree = degree - 90;
      rotation = rotation + 90;
    }
  }

  private List<Coordinate> rotate(List<Coordinate> originalCoordinates, int degree) {
    while (degree > 0) {
      originalCoordinates = rotateOnce(originalCoordinates);
      degree = degree - 90;
    }
    return originalCoordinates;
  }


  public List<Coordinate> getRelativeObstacles() {
    return relativeRotatedObstacles;
  }

  /**
   * Copies the tile.
   *
   * @return the cope tile
   */
  public Tile copy() {
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(bos);
      out.writeObject(this);
      out.flush();

      ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
      ObjectInputStream in = new ObjectInputStream(bis);
      return (Tile) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
