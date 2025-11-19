package dev.madfist.aoc2024;

import java.util.List;

public class Day06 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var start = new Coordinate();
    var grid = new Grid<>(input, (c, v) -> {
      if (v.equals('^')) {
        start.set(c);
        return '.';
      }
      return v;
    });
    return "";
  }

  @Override
  public String solveSecond(List<String> input) {
    return "";
  }
}
