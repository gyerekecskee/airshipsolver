package airship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TileType {
  //TODO maybe large corner and airship can overlay each other
  AIRSHIP(Arrays.asList(new Coordinate(-1, 1), new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(0, 0), new Coordinate(1, 0)), "airship.png"),
  SMALL_CORNER(List.of(new Coordinate(0, 0)), "white-corner.jpg"),
  LARGE_CORNER(Arrays.asList(new Coordinate(-1, -1), new Coordinate(0, -1), new Coordinate(-1, 0), new Coordinate(0, 0)), "large-corner.png"),
  EMPTY(new ArrayList<>(), "empty.jpg"),
  LARGE_BOTTOM(Arrays.asList(new Coordinate(0, -1), new Coordinate(1, -1), new Coordinate(0, 0), new Coordinate(1, 0)), "large-bottom.png"),
  DIAGONAL(Arrays.asList(new Coordinate(0, 0), new Coordinate(1, 1)), "diagonal.jpg"),
  COLUMN(Arrays.asList(new Coordinate(0, 0), new Coordinate(1, 0)), "column.jpg");

  private final List<Coordinate> obstacles;
  private final String imageName;

  TileType(List<Coordinate> obstacles, String imageName) {
    this.obstacles = obstacles;
    this.imageName = imageName;
  }

  public List<Coordinate> getObstacles() {
    return obstacles;
  }

  public String getImageName() {
    return imageName;
  }
}
