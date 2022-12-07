package dev.madfist.aoc2021;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day05 implements Day {
  private static class Coordinate {
    int x, y;
    Coordinate(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Coordinate that = (Coordinate) o;
      return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }

    @Override
    public String toString() {
      return "(" + x + "," + y + ")";
    }
  }

  private static class Interval {
    int i, j;
    Interval(int i, int j) {
      this.i = Math.min(i, j);
      this.j = Math.max(i, j);
    }
    boolean contains(int n) {
      return i <= n && n <= j;
    }

    IntStream range() {
      return IntStream.range(i, j + 1);
    }
  }

  private static class Line {
    Coordinate a, b;
    Line(int ax, int ay, int bx, int by) {
      this.a = new Coordinate(ax, ay);
      this.b = new Coordinate(bx, by);
    }
    boolean isHorizontal() {
      return a.y == b.y;
    }
    boolean isVertical() {
      return a.x == b.x;
    }
    boolean isDiagonal() {
      return Math.abs(a.x - b.x) == Math.abs(a.y - b.y);
    }
    Interval getHorizontalInterval() {
      return new Interval(a.x, b.x);
    }
    Interval getVerticalInterval() {
      return new Interval(a.y, b.y);
    }
    Set<Coordinate> getPoints() {
      if (isHorizontal()) {
        return getHorizontalInterval().range().mapToObj(i -> new Coordinate(i, a.y)).collect(Collectors.toSet());
      } else if (isVertical()) {
        return getVerticalInterval().range().mapToObj(i -> new Coordinate(a.x, i)).collect(Collectors.toSet());
      } else if (isDiagonal()) {
        var x = Math.min(a.x, b.x);
        var y = a.x < b.x ? a.y : b.y;
        var yd = (y == a.y ? b.y - a.y : a.y - b.y) / Math.abs(a.y - b.y);
        return getHorizontalInterval().range().mapToObj(i -> new Coordinate(i, y + yd * (i - x))).collect(Collectors.toSet());
      }
      return Set.of();
    }
    List<Coordinate> intersect(Line l) {
      var points = getPoints();
      points.retainAll(l.getPoints());
      return new ArrayList<>(points);
    }

    @Override
    public String toString() {
      return "[" + a + "->" + b + "]";
    }
  }

  @Override
  public String solveFirst(List<String> input) {
    return Long.toString(solve(input, l -> l.isHorizontal() || l.isVertical()));
  }

  @Override
  public String solveSecond(List<String> input) {
    return Long.toString(solve(input, l -> l.isHorizontal() || l.isVertical() || l.isDiagonal()));
  }

  private Long solve(List<String> input, Predicate<Line> filter) {
    var pattern = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");
    var lines = input.stream()
      .map(str -> {
        var matcher = pattern.matcher(str);
        if (matcher.find()) {
          return new Line(
            Integer.parseInt(matcher.group(1)),
            Integer.parseInt(matcher.group(2)),
            Integer.parseInt(matcher.group(3)),
            Integer.parseInt(matcher.group(4)));
        }
        return new Line(0, 0, 0, 0);
      })
      .filter(filter)
      .collect(Collectors.toList());
//    System.out.println(lines);
    var intersections = new HashMap<Coordinate, Integer>();
    for (int i = 0; i < lines.size() - 1; ++i) {
      for (int j = i + 1; j < lines.size(); ++j) {
        var intersectingCoordinates = lines.get(i).intersect(lines.get(j));
//        System.out.println("L1: " + lines.get(i) + " L2: " + lines.get(j) + " => " + intersectingCoordinates);
        if (!intersectingCoordinates.isEmpty()) {
          intersectingCoordinates.forEach(is -> intersections.merge(is, 1, Integer::sum));
//          System.out.println("L1: " + lines.get(i) + " L2: " + lines.get(j) + " => " + intersectingCoordinates);
        }
      }
    }
    return intersections.values().stream().filter(n -> n > 0).count();
  }


}
