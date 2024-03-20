package com.airshipg;

/**
 * Container class for Tile.
 */
public class TileContainer {
  private static final TileContainer INSTANCE = new TileContainer();
  private Tile tile;

  private TileContainer() {

  }

  public static TileContainer getInstance() {
    return INSTANCE;
  }

  public void setTile(Tile tile) {
    this.tile = tile;
  }

  public Tile getTile() {
    return tile;
  }
}
