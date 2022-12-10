package dev.madfist.aoc2021;

import java.util.*;
import java.util.stream.Collectors;

import static dev.madfist.aoc2021.Utils.collectToString;

public class Day15 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var grid = new Grid(input, 1);
    grid.dijsktra(0, 0, grid.width - 1, grid.height - 1);
    //System.out.println(grid);
    return Integer.toString(grid.get(grid.width - 1, grid.height - 1).distance);
  }

  @Override
  public String solveSecond(List<String> input) {
    var grid = new Grid(input, 5);
    //System.out.println(grid);
    grid.dijsktra(0, 0, grid.width - 1, grid.height - 1);
    return Integer.toString(grid.get(grid.width - 1, grid.height - 1).distance);
  }

  private static class CellData {
    final int x;
    final int y;
    final int value;
    int distance;
    boolean seen = false;

//    @Override
//    public boolean equals(Object o) {
//      if (this == o) return true;
//      if (o == null || getClass() != o.getClass()) return false;
//      CellData cellData = (CellData) o;
//      return x == cellData.x && y == cellData.y;
//    }
//
//    @Override
//    public int hashCode() {
//      return Objects.hash(distance, x, y);
//    }

    public CellData(int x, int y, int value, int distance) {
      this.x = x;
      this.y = y;
      this.value = value;
      this.distance = distance;
    }

    public String toString() {
      return String.format("(%d,%d)=[%d-%s]%s", x, y, value, distance == Integer.MAX_VALUE ? "$" : distance, seen ? "#" : "_");
    }

    public void update(CellData source) {
      distance = Math.min(source.distance + value, distance);
    }
  }

  private static class Grid {
    private final int width;
    private final int height;
    private final List<CellData> data;

    public Grid(List<String> input, int multiplier) {
      int w = input.get(0).length();
      int h = input.size();
      width = w * multiplier;
      height = h * multiplier;
      data = new ArrayList<>(width * height);
      int j = 0;
      for (var line: input) {
          for (int a = 0; a < multiplier; ++a) {
            int i = 0;
            for (var c: line.toCharArray()) {
              var value = (Integer.parseInt("" + c) - 1 + a) % 9 + 1;
              data.add(new CellData(i++ + w * a, j, value, Integer.MAX_VALUE));
            }
          }
        ++j;
      }
      for (int m = 1; m < multiplier; ++m) {
        final int mf = m;
        data.addAll(
          data.subList(width * h * (m - 1), width * h * m).stream()
            .map(cd -> new CellData(cd.x,  h + cd.y, cd.value % 9 + 1, Integer.MAX_VALUE))
            .collect(Collectors.toList()));
      }
    }

    public CellData get(int x, int y) {
      return data.get(y * width + x);
    }

    public void dijsktra(int startX, int startY, int endX, int endY) {
      var queue = new HashSet<CellData>();
      get(startX, startY).distance = 0;
      queue.add(get(startX, startY));
      while (!queue.isEmpty()) {
        var actual = queue.stream().min(Comparator.comparing(cd -> cd.distance)).orElse(null);
        queue.remove(actual);
        var i = actual.x;
        var j = actual.y;
        if (i == endX && j == endY) {
          return;
        }
        if (i > 0 && !get(i - 1, j).seen) {
          get(i - 1, j).update(actual);
          queue.add(get(i - 1, j));
        }
        if (i < width - 1 && !get(i + 1, j).seen) {
          get(i + 1, j).update(actual);
          queue.add(get(i + 1, j));
        }
        if (j > 0 && !get(i, j - 1).seen) {
          get(i, j - 1).update(actual);
          queue.add(get(i, j - 1));
        }
        if (j < height - 1 && !get(i, j + 1).seen) {
          get(i, j + 1).update(actual);
          queue.add(get(i, j + 1));
        }
        actual.seen = true;
        //System.out.printf("(%d,%d) %s\n%s", i, j, queue.stream().map(cd -> String.format("[%d,%d,%d] ", cd.x, cd.y, cd.distance)).collect(collectToString()), this);
      }
    }

    public String toString() {
      var builder = new StringBuilder();
      builder.append(String.format("size: %dx%d\n", width, height));
      for (int j = 0; j < height; ++j) {
        for (int i = 0; i < width; ++i) {
          //builder.append(get(i, j).seen ? get(i, j).distance : "#").append(" ");
          builder.append(get(i, j)).append(" ");
        }
        builder.append('\n');
      }
      return builder.toString();
    }
  }
}
