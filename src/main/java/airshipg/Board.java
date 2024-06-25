package airshipg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class Board implements Serializable {
  private final Grid grid;
  private int emptyPosX;
  private int emptyPosY;

  public Board(Tile x0y2, Tile x1y2, Tile x2y2,
               Tile x0y1, Tile x1y1, Tile x2y1,
               Tile x0y0, Tile x1y0, Tile x2y0) throws NoSuchElementException {
    grid = new Grid(3);

    x0y0.setPositionInGrid(0, 0);
    x1y0.setPositionInGrid(1, 0);
    x2y0.setPositionInGrid(2, 0);
    x0y1.setPositionInGrid(0, 1);
    x1y1.setPositionInGrid(1, 1);
    x2y1.setPositionInGrid(2, 1);
    x0y2.setPositionInGrid(0, 2);
    x1y2.setPositionInGrid(1, 2);
    x2y2.setPositionInGrid(2, 2);

    grid.insertTile(x0y0.copy(), new Coordinate(0, 0));
    grid.insertTile(x1y0.copy(), new Coordinate(1, 0));
    grid.insertTile(x2y0.copy(), new Coordinate(2, 0));
    grid.insertTile(x0y1.copy(), new Coordinate(0, 1));
    grid.insertTile(x1y1.copy(), new Coordinate(1, 1));
    grid.insertTile(x2y1.copy(), new Coordinate(2, 1));
    grid.insertTile(x0y2.copy(), new Coordinate(0, 2));
    grid.insertTile(x1y2.copy(), new Coordinate(1, 2));
    grid.insertTile(x2y2.copy(), new Coordinate(2, 2));

    Coordinate posOfEmpty = grid.positionOfEmpty();
    emptyPosY = posOfEmpty.getY();
    emptyPosX = posOfEmpty.getX();
  }

  /**
   * CAN BE IMPROVED BY CHECKING FEWER TILES FOR COLLISIONS
   * this board gets moved and returned
   * moves the empty tile in the specified direction
   */
  public Board moveEmptyTile(Direction direction) throws IllegalArgumentException {
    Tile toBeMoved;
    List<Tile> neighbors;
    int xOffset = 0;
    int yOffset = 0;
    switch (direction) {
      case UP -> {
        if (emptyPosY == 2) {
          throw new IllegalArgumentException("empty tile is already at the top row");
        }
        neighbors = getNeighborsOfEmptyTile();
        yOffset = 1;
      }
      case RIGHT -> {
        if (emptyPosX == 2) {
          throw new IllegalArgumentException("empty tile is already at the rigthmost column");
        }
        neighbors = getNeighborsOfEmptyTile();
        xOffset = 1;
      }
      case LEFT -> {
        if (emptyPosX == 0) {
          throw new IllegalArgumentException("empty tile is already at the leftmost column");
        }
        neighbors = getNeighborsOfEmptyTile();
        xOffset = -1;
      }
      case DOWN -> {
        if (emptyPosY == 0) {
          throw new IllegalArgumentException("empty tile is already at the bottom row");
        }
        neighbors = getNeighborsOfEmptyTile();
        yOffset = -1;

      }
      default -> throw new IllegalArgumentException("this direction doesn't exists : " + direction);
    }
    toBeMoved = checkCollisionWithNeighbors(neighbors, xOffset, yOffset, direction);
    moveTile(toBeMoved, direction.opposite());
    return this;
  }

  public Tile checkCollisionWithNeighbors(List<Tile> neighbors, int xOffset, int yOffset, Direction direction) {
    Tile toBeMoved = grid.getTile(new Coordinate(emptyPosX + xOffset, emptyPosY + yOffset));
    for (Tile tile : neighbors) {
      if (theseCollide(toBeMoved, direction.opposite(), tile)) {
        throw new IllegalArgumentException("tiles would collide");
      }
    }
    return toBeMoved;
  }
//TODO 2 switches for one thing
  public void moveTile(Tile toBeMoved, Direction direction) {
    int yOffSet = 0;
    int xOffset = 0;
    switch (direction) {
      case LEFT -> xOffset = -1;
      case DOWN -> yOffSet = -1;
      case RIGHT -> xOffset = 1;
      case UP -> yOffSet = 1;
      default -> throw new IllegalArgumentException("this is an unknown direction");
    }
    toBeMoved.setPositionInGrid(emptyPosX, emptyPosY);
    Coordinate emptyPosition = new Coordinate(emptyPosX, emptyPosY);

    Tile emptyTile = grid.getTile(emptyPosition);
    emptyTile.setPositionInGrid(emptyPosX - xOffset, emptyPosY - yOffSet);

    grid.insertTile(toBeMoved, emptyPosition);
    grid.insertTile(emptyTile, emptyTile.getPosition());

    emptyPosY = emptyTile.getPosition().getY();
    emptyPosX = emptyTile.getPosition().getX();
  }

  public Board copy() {
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(bos);
      out.writeObject(this);
      out.flush();

      ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
      ObjectInputStream in = new ObjectInputStream(bis);
      return (Board) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * CAN BE IMPROVED BY NOT CHECKING ALL THE TILES ONLY THE NEIGHBORING ONES
   * @return
   */
  private List<Tile> getNeighborsOfEmptyTile() {
    return grid.getNonEmptyTiles();
//    List<Tile> neighbors = new ArrayList<>();
//    for (int i = 0; i < grid.size(); i++) {
//      for (int j = 0; j < grid.get(i).size(); j++) {
//        if (!(Math.abs(i - emptyPosY) == 0 && Math.abs(j - emptyPosX) == 0) && Math.abs(i - emptyPosY) != 2 && Math.abs(j - emptyPosX) != 2) {
//          neighbors.add(grid.get(i).get(j));
//        }
//      }
//    }
//    return neighbors;
  }

  private boolean areNeighbors(Tile tile1, Tile tile2) {
    return !(Math.abs(tile1.getPosition().getY() - tile2.getPosition().getY()) == 0 && Math.abs(tile1.getPosition().getX() - tile2.getPosition().getX()) == 0) && Math.abs(tile1.getPosition().getY() - tile2.getPosition().getY()) != 2 && Math.abs(tile1.getPosition().getX() - tile2.getPosition().getX()) != 2;
  }

  public boolean theseCollide(Tile moved, Direction direction, Tile stationary) {
    if (moved.equals(stationary)) {
      return false;
    }
    switch (direction) {
      case UP -> {
        return checkCollision(moved, stationary, 0, 1, 0, 2);
      }
      case DOWN -> {
        return checkCollision(moved, stationary, 0, -1, 0, -2);
      }
      case LEFT -> {
        return checkCollision(moved, stationary, -1, 0, -2, 0);
      }
      case RIGHT -> {
        return checkCollision(moved, stationary, 1, 0, 2, 0);
      }
    }
    return false;
  }

  public boolean canAirshipEscape() {
    if (grid.getTile(new Coordinate(1, 0)).getType().equals(TileType.AIRSHIP)){
      return !theseCollide(getTile(1, 0), Direction.DOWN, getTile(2, 0))
      && !theseCollide(getTile(1, 0), Direction.DOWN, getTile(0, 0));
    }
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Board board = (Board) o;
    return emptyPosX == board.emptyPosX && emptyPosY == board.emptyPosY && Objects.equals(
        grid, board.grid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(grid, emptyPosX, emptyPosY);
  }

  @Override
  public String toString() {
    return grid.toString();
  }

  public Tile getEmptyTile() {
    return grid.getTile(new Coordinate(emptyPosX, emptyPosY));
  }

  public Tile getTile(int posx, int posy) {
    return grid.getTile(new Coordinate(posx, posy));
  }

  /**
   * Converts the obstacle coordinates of toBeTransformed to a list of coordinates that overlap with the coordinates of the etalon tile
   * @param toBeTransformed
   * @param etalon
   * @return
   */
  public static List<Coordinate> convertCoords(Tile toBeTransformed, Tile etalon) {
    return toBeTransformed.getRelativeObstacles().stream().map(colPosOfToBeTransformed -> new Coordinate(colPosOfToBeTransformed.getX() + (toBeTransformed.getPosition().getX() - etalon.getPosition().getX()) * 2,
        colPosOfToBeTransformed.getY() + (toBeTransformed.getPosition().getY() - etalon.getPosition().getY()) * 2)).toList();
  }

  public static boolean checkCollision(Tile moved, Tile stationary, int xOffset, int yOffset, int movedByINXDirection, int movedByInYDirection) {
    //TODO refactor
    List<Coordinate> coordinatesCenteredOnStationary = convertCoords(moved, stationary);
    for (Coordinate coordinate : coordinatesCenteredOnStationary) {
      if (stationary.getRelativeObstacles().contains(coordinate.add(new Coordinate(movedByINXDirection, movedByInYDirection))) || stationary.getRelativeObstacles().contains(coordinate.add(new Coordinate(-xOffset + movedByINXDirection, -yOffset + movedByInYDirection)))) {
        return true;
      }
    }
    return false;
  }

  public List<Tile> getNonEmptyTiles() {
    return grid.getNonEmptyTiles();
  }

  public List<Board> getAllValidSuccessors() {
    List<Board> validSuccessors = new ArrayList<>();
    for (Direction direction : Direction.values()) {
      Optional<Board> optionalBoard = getValidSuccessorFor(direction);
      optionalBoard.ifPresent(validSuccessors::add);
    }
    return validSuccessors;
  }

  public Optional<Board> getValidSuccessorFor(Direction direction) {
    Optional<Board> result = Optional.empty();
    try {
      result = Optional.of(copy().moveEmptyTile(direction));
    } catch (IllegalArgumentException ignored) {

    }
    return result;
  }
}
