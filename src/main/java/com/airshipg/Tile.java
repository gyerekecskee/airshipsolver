package com.airshipg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Tile implements Serializable {
  private final TileType type;
  private Coordinate position;
  private final List<Coordinate> relativeRotatedObstacles;

  public Tile(TileType type, int degree) {
    if (degree % 90 != 0) {
      throw new IllegalArgumentException("degree must be a multiple of 90");
    }
    this.type = type;
    relativeRotatedObstacles = rotate(type.getObstacles(), degree);
  }

  public TileType getType() {
    return type;
  }

  public void setPositionInGrid(int xPosition, int yPosition) {
    this.position = new Coordinate(xPosition, yPosition);
  }

  public Coordinate getPosition() {
    return position;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Tile tile = (Tile) o;
    return type == tile.type && Objects.equals(position, tile.position) && Objects.equals(relativeRotatedObstacles, tile.relativeRotatedObstacles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, position, relativeRotatedObstacles);
  }

  @Override
  public String toString() {
    String out = "";
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
          out = out + "x";
        } else {
          out = out + "0";
        }
      }
      out = out + "\n";
    }
    out = out.substring(0, out.length() - 1);
    return out;
  }

  private List<Coordinate> rotateOnce(List<Coordinate> originalCoordinates) {
    return originalCoordinates.stream().map(pair -> new Coordinate(-pair.getY() + 1,
        pair.getX())).collect(Collectors.toList());

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
