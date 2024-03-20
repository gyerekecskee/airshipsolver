package com.airshipg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TileType {
  //TODO maybe large corner and airship can overlay each other
  AIRSHIP(Arrays.asList(new Coordinate(-1, 1), new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(0, 0), new Coordinate(1, 0))),
  SMALL_CORNER(List.of(new Coordinate(0, 0))),
  LARGE_CORNER(Arrays.asList(new Coordinate(-1, -1), new Coordinate(0, -1), new Coordinate(-1, 0), new Coordinate(0, 0))),
  EMPTY(new ArrayList<>()),
  LARGE_BOTTOM(Arrays.asList(new Coordinate(0, -1), new Coordinate(1, -1), new Coordinate(0, 0), new Coordinate(1, 0))),
  DIAGONAL(Arrays.asList(new Coordinate(0, 0), new Coordinate(1, 1))),
  COLUMN(Arrays.asList(new Coordinate(0, 0), new Coordinate(1, 0)));

  private final List<Coordinate> obstacles;

  TileType(List<Coordinate> obstacles) {
    this.obstacles = obstacles;
  }

  public List<Coordinate> getObstacles() {
    return obstacles;
  }
}
