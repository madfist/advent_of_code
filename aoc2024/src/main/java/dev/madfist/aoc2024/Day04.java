package dev.madfist.aoc2024;

import java.util.List;

public class Day04 implements Day {
  private static final String XMAS = "XMAS";
  private static final String MAS = "MAS";
  private static final String SAM = "SAM";

  @Override
  public String solveFirst(List<String> input) {
    long count = 0;
    for (int j = 0; j < input.size(); j++) {
      for (int i = 0; i < input.get(j).length(); i++) {
        var coordinate = new Coordinate(i, j);
        if (!charAt(input, coordinate).equals("X")) {
          continue;
        }
        count += Coordinate.Direction.stream()
          .filter(d -> checkDirection(input, coordinate, d))
          .count();
      }
    }
    return Long.toString(count);
  }

  @Override
  public String solveSecond(List<String> input) {
    long count = 0;
    for (int j = 0; j < input.size(); j++) {
      for (int i = 0; i < input.get(j).length(); i++) {
        var coordinate = new Coordinate(i, j);
        if (!charAt(input, coordinate).equals("A")) {
          continue;
        }
        count += checkBox(input, coordinate) ? 1 : 0;
      }
    }
    return Long.toString(count);
  }

  private String charAt(List<String> lines, Coordinate coordinate) {
    if (coordinate.x() < 0 ||
        coordinate.x() >= lines.get(0).length() ||
        coordinate.y() < 0 ||
        coordinate.y() >= lines.size()) {
      return "~";
    }
    return lines.get(coordinate.y()).substring(coordinate.x(), coordinate.x() + 1);
  }

  private boolean checkDirection(List<String> lines, Coordinate coordinate, Coordinate.Direction direction) {
    var original = new Coordinate(coordinate);
    var result = XMAS.equals(charAt(lines, coordinate) +
      charAt(lines, coordinate.move(direction)) +
      charAt(lines, coordinate.move(direction)) +
      charAt(lines, coordinate.move(direction)));
    coordinate.set(original);
    return result;
  }

  private boolean checkBox(List<String> lines, Coordinate coordinate) {
    var diag1 = charAt(lines, coordinate.moved(Coordinate.Direction.NW)) +
      charAt(lines, coordinate) +
      charAt(lines, coordinate.moved(Coordinate.Direction.SE));
    var diag2 = charAt(lines, coordinate.moved(Coordinate.Direction.NE)) +
      charAt(lines, coordinate) +
      charAt(lines, coordinate.moved(Coordinate.Direction.SW));
    return (diag1.equals(MAS) || diag1.equals(SAM)) && (diag2.equals(MAS) || diag2.equals(SAM));
  }
}
