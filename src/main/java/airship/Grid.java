package airship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Grid implements Serializable {
  //TODO implement collection
  int size;
  List<List<Tile>> rows;

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

  public void insertTile(Tile tileToBeInserted, Coordinate where) {
    if (where.getX() >= size || where.getY() >= size) {
      throw new IllegalArgumentException("the coordinate " + where + " is outside of the grid");
    }
    rows.get(where.getY()).remove(where.getX());
    rows.get(where.getY()).add(where.getX(), tileToBeInserted);
  }

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

  public Coordinate positionOfEmpty() throws NoSuchElementException {
    for (List<Tile> row : rows) {
      for (Tile tile : row) {
        if (tile.getType().equals(TileType.EMPTY)){
          return tile.getPosition();
        }
      }
    }
    throw new NoSuchElementException("there is no empty tile in the grid");
  }

  public Tile getTile(Coordinate position) {
    return rows.get(position.getY()).get(position.getX());
  }


  public String toDetailedString() {
    String out = "";
    for (int i = 0; i < size; i++) {
      out = out + " |" + (size - i - 1) + "|";
      for (int j = 0; j < size; j++) {
        out = out + " " + rows.get(size - i - 1).get(j).getType() + "; " + rows.get(size - i - 1).get(j).getRelativeObstacles() + " |";
      }
      out = out + "\n";
    }
    out = out + "__________0____________1____________2_";
    return out;
  }

  //TODO refoactor to general formula
  @Override
  public String toString() {
    return " |2| " + rows.get(2).get(0).getType() + " | " + rows.get(2).get(1).getType() + " | " + rows.get(2).get(2).getType() + " | \n"
        +  " |1| " + rows.get(1).get(0).getType() + " | " + rows.get(1).get(1).getType() + " | " + rows.get(1).get(2).getType() + " | \n"
        +  " |0| " + rows.get(0).get(0).getType() + " | " + rows.get(0).get(1).getType() + " | " + rows.get(0).get(2).getType() + " | \n"
        + "__________0____________1____________2_";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Grid grid = (Grid) o;
    return size == grid.size && Objects.equals(rows, grid.rows);
  }

  @Override
  public int hashCode() {
    return Objects.hash(size, rows);
  }
}
