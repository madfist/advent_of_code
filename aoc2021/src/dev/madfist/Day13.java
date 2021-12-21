package dev.madfist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day13 implements Day {
  private static class Dot {
    int x;
    int y;

    Dot(String input) {
      var parts = input.split(",");
      x = Integer.parseInt(parts[0]);
      y = Integer.parseInt(parts[1]);
    }

    Dot(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public String toString() {
      return "(" + x + ',' + y + ')';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Dot dot = (Dot) o;
      return x == dot.x && y == dot.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }

    void fold(String instruction) {
      if (instruction.charAt(11) == 'x') {
        foldX(Integer.parseInt(instruction.substring(13)));
      }
      if (instruction.charAt(11) == 'y') {
        foldY(Integer.parseInt(instruction.substring(13)));
      }
    }
    private void foldX(int l) {
      if (x < l) {
        return;
      }
      x = 2 * l - x;
    }
    private void foldY(int l) {
      if (y < l) {
        return;
      }
      y = 2 * l - y;
    }
  }
  @Override
  public String solveFirst(List<String> input) {
    List<Dot> dots = new ArrayList<>();
    List<String> folds = new ArrayList<>();
    for (var line : input) {
      if (line.contains(",")) {
        dots.add(new Dot(line));
      }
      if (line.contains("fold")) {
        folds.add(line);
      }
    }
    dots.forEach(d -> d.fold(folds.get(0)));
    return Integer.toString(Set.copyOf(dots).size());
  }

  @Override
  public String solveSecond(List<String> input) {
    List<Dot> dots = new ArrayList<>();
    List<String> folds = new ArrayList<>();
    for (var line : input) {
      if (line.contains(",")) {
        dots.add(new Dot(line));
      }
      if (line.contains("fold")) {
        folds.add(line);
      }
    }
    for (var fold : folds) {
      dots.forEach(d -> d.fold(fold));
    }
    var resultSet = Set.copyOf(dots);
    var maxX = resultSet.stream().mapToInt(d -> d.x).max();
    var maxY = resultSet.stream().mapToInt(d -> d.y).max();
    StringBuilder sb = new StringBuilder("\n");
    if (maxX.isPresent()) {
      for (int j = 0; j <= maxY.getAsInt(); ++j) {
        for (int i = 0; i <= maxX.getAsInt(); ++i) {
          sb.append(resultSet.contains(new Dot(i, j)) ? '#' : ' ');
        }
        sb.append('\n');
      }
    }

    return sb.toString();
  }
}
