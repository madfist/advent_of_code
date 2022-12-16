package dev.madfist.aoc2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.madfist.aoc2022.Utils.collectToString;

public class Day12 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var grid = new Grid(input);
    grid.dijsktra();
//    System.out.println(grid.data.stream()
//      .map(cell ->
//        (cell.seen ? "#" : ".") +
//          ((cell.x == grid.width - 1) ? '\n' : ""))
//      .collect(collectToString()));
    return Integer.toString(grid.end().distance);
  }

  @Override
  public String solveSecond(List<String> input) {
    var grid = new Grid(input);
    return Integer.toString(grid.dijsktraForAllStarts());
  }

  private static class CellData {
    final int x;
    final int y;
    final char value;
    int distance;
    boolean seen = false;

    public CellData(int x, int y, char value, int distance) {
      this.x = x;
      this.y = y;
      this.value = value;
      this.distance = distance;
    }

    public CellData(CellData other) {
      this(other.x, other.y, other.value, other.distance);
    }

    @Override
    public String toString() {
      return String.format("(%d,%d)=[%c-%s]%s", x, y, value, distance == Integer.MAX_VALUE ? "$" : distance, seen ? "#" : "_");
    }

    public void update(CellData source) {
      distance = Math.min(source.distance + 1, distance);
    }

    public boolean diffCheck(CellData other) {
      return (int) value - (int) other.value < 2;
    }
  }

  private static class Grid {
    private final int width;
    private final int height;
    private final List<CellData> data;
    private int startIndex;
    private int endIndex;

    public Grid(List<String> input) {
      width = input.get(0).length();
      height = input.size();
      data = new ArrayList<>(width * height);
      int j = 0;
      for (var line: input) {
        int i = 0;
        for (var c: line.toCharArray()) {
          if (c == 'S') {
            startIndex = j * width + i;
            c = 'a';
          }
          if (c == 'E') {
            endIndex = j * width + i;
            c = 'z';
          }
          data.add(new CellData(i++, j, c, Integer.MAX_VALUE));
        }
        ++j;
      }
    }

    public Grid(Grid other) {
      width = other.width;
      height = other.height;
      data = other.data.stream().map(CellData::new).collect(Collectors.toList());
      startIndex = other.startIndex;
      endIndex = other.endIndex;
    }

    public CellData get(int x, int y) {
      return data.get(y * width + x);
    }

    public int dijsktraForAllStarts() {
      return data.stream()
        .filter(cell -> cell.value == 'a')
        .mapToInt(cell -> {
          var grid = new Grid(this);
          grid.dijsktra(cell);
          return grid.end().distance;
        })
        .min()
        .orElse(Integer.MAX_VALUE);
    }

    public CellData start() {
      return data.get(startIndex);
    }

    public CellData end() {
      return data.get(endIndex);
    }

    public void dijsktra() {
      dijsktra(start().x, start().y, end().x, end().y);
    }

    public void dijsktra(CellData start) {
      dijsktra(start.x, start.y, end().x, end().y);
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
        if (i > 0 && get(i - 1, j).diffCheck(actual) && !get(i - 1, j).seen) {
          get(i - 1, j).update(actual);
          queue.add(get(i - 1, j));
        }
        if (i < width - 1 && get(i + 1, j).diffCheck(actual) && !get(i + 1, j).seen) {
          get(i + 1, j).update(actual);
          queue.add(get(i + 1, j));
        }
        if (j > 0 && get(i, j - 1).diffCheck(actual) && !get(i, j - 1).seen) {
          get(i, j - 1).update(actual);
          queue.add(get(i, j - 1));
        }
        if (j < height - 1 && get(i, j + 1).diffCheck(actual) && !get(i, j + 1).seen) {
          get(i, j + 1).update(actual);
          queue.add(get(i, j + 1));
        }
        actual.seen = true;
//        System.out.printf("%s\n\t%s\n", actual, queue);
      }
    }

    @Override
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
