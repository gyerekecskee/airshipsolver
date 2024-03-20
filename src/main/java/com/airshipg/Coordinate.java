package com.airshipg;

import java.io.Serializable;
import java.util.Objects;

public class Coordinate implements Serializable {
  private int posX;
  private int posY;

  public Coordinate(int posX, int posY) {
    this.posX = posX;
    this.posY = posY;
  }

  public int getX() {
    return posX;
  }

  public void setX(int posX) {
    this.posX = posX;
  }

  public int getY() {
    return posY;
  }

  public void setY(int posY) {
    this.posY = posY;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coordinate that = (Coordinate) o;
    return posX == that.posX && posY == that.posY;
  }

  @Override
  public int hashCode() {
    return Objects.hash(posX, posY);
  }

  @Override
  public String toString() {
    return "(" + posX + ", " + posY + ')';
  }

  public Coordinate add(Coordinate coordinate) {
    return new Coordinate(posX + coordinate.posX, posY + coordinate.posY);
  }
}
