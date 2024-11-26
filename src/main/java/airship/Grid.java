package airship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A class representing, an nxn grid of Tiles.
 */
public class Grid implements Serializable {
  //TODO implement collection
  int size;
  List<List<Tile>> rows;

  /**
   * Constructor for Grid.
   *
   * @param size the size of the grid
   */
  public Grid(int size) {
    this.size = size;
    rows = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      List<Tile> row = new ArrayList<>(size);
      for (int j = 0; j < size; j++) {
        row.add(null);
      }
      rows.add(row);
    }
  }

  /**
   * Inserts a tile to th board.
   *
   * @param tileToBeInserted the tile to be inserted
   * @param where the place of the new tile
   * @throws IllegalArgumentException if where is outside the grid
   */
  public void insertTile(Tile tileToBeInserted, Coordinate where) throws IllegalArgumentException {
    if (where.getX() >= size || where.getY() >= size) {
      throw new IllegalArgumentException("the coordinate " + where + " is outside of the grid");
    }
    rows.get(where.getY()).remove(where.getX());
    rows.get(where.getY()).add(where.getX(), tileToBeInserted);
  }

  /**
   * Returns all the except which are not empty.
   *
   * @return the tiles which are not empty
   */
  public List<Tile> getNonEmptyTiles() {
    List<Tile> elements = new ArrayList<>();
    for (List<Tile> row : rows) {
      for (Tile tile : row) {
        if (!tile.getType().equals(TileType.EMPTY)) {
          elements.add(tile);
        }
      }
    }
    return elements;
  }

  /**
   * Returns the position of the empty tile.
   *
   * @return the position ofthe empty tile
   * @throws NoSuchElementException if there is no empty tile
   */
  public Coordinate positionOfEmpty() throws NoSuchElementException {
    for (List<Tile> row : rows) {
      for (Tile tile : row) {
        if (tile.getType().equals(TileType.EMPTY)) {
          return tile.getPosition();
        }
      }
    }
    throw new NoSuchElementException("there is no empty tile in the grid");
  }

  public Tile getTile(Coordinate position) {
    return rows.get(position.getY()).get(position.getX());
  }

  /**
   * A more detailed version of toString().
   *
   * @return a detaile string format of the gri
   */
  public String toDetailedString() {
    StringBuilder out = new StringBuilder();
    for (int i = 0; i < size; i++) {
      out.append(" |").append(size - i - 1).append("|");
      for (int j = 0; j < size; j++) {
        out.append(" ").append(rows.get(size - i - 1).get(j).getType()).append("; ")
            .append(rows.get(size - i - 1).get(j).getRelativeObstacles()).append(" |");
      }
      out.append("\n");
    }
    out.append("__________0____________1____________2_");
    return out.toString();
  }

  //TODO refactor to general formula
  @Override
  public String toString() {
    return " |2| " + rows.get(2).get(0).getType() + " | " + rows.get(2).get(1).getType() + " | "
        + rows.get(2).get(2).getType() + " | \n"
        +  " |1| " + rows.get(1).get(0).getType() + " | " + rows.get(1).get(1).getType() + " | "
        + rows.get(1).get(2).getType() + " | \n"
        +  " |0| " + rows.get(0).get(0).getType() + " | " + rows.get(0).get(1).getType() + " | "
        + rows.get(0).get(2).getType() + " | \n"
        + "__________0____________1____________2_";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Grid grid = (Grid) o;
    return size == grid.size && Objects.equals(rows, grid.rows);
  }

  @Override
  public int hashCode() {
    return Objects.hash(size, rows);
  }
}
