package dev.madfist.aoc2024;

import java.util.Arrays;
import java.util.stream.Stream;

public class Coordinate {
  private int x;
  private int y;

  public enum Direction {
    N,
    E,
    S,
    W,
    NE,
    SE,
    SW,
    NW;

    public boolean isNorth() {
      return this == NW || this == NE || this == N;
    }

    public boolean isEast() {
      return this == E || this == SE || this == NE;
    }

    public boolean isSouth() {
      return this == SW || this == SE || this == S;
    }

    public boolean isWest() {
      return this == SW || this == NW || this == W;
    }

    public static Stream<Direction> stream() {
      return Arrays.stream(Direction.values());
    }
  }

  public Coordinate() {
    this(0, 0);
  }

  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Coordinate(Coordinate coordinate) {
    this(coordinate.x, coordinate.y);
  }

  public int x() {
    return x;
  }

  public int y() {
    return y;
  }

  public Coordinate move(Direction direction) {
    switch (direction) {
      case N: y -= 1; break;
      case E: x += 1; break;
      case S: y += 1; break;
      case W: x -= 1; break;
      case NE: y -= 1; x += 1; break;
      case SE: y += 1; x += 1; break;
      case SW: y += 1; x -= 1; break;
      case NW: y -= 1; x -= 1; break;
    }
    return this;
  }

  public Coordinate moved(Direction direction) {
    return (new Coordinate(this)).move(direction);
  }

  public void set(Coordinate coordinate) {
    x = coordinate.x;
    y = coordinate.y;
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
