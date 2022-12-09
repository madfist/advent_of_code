package dev.madfist.aoc2022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day08 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var forest = new Forest(input);
    return Integer.toString(forest.size() - forest.countInvisibleTrees());
  }

  @Override
  public String solveSecond(List<String> input) {
    var forest = new Forest(input);
    return Integer.toString(forest.maxScenicScore());
  }

  private static class View {
    final List<Integer> north;
    final List<Integer> south;
    final List<Integer> west;
    final List<Integer> east;

    public View(List<Integer> north, List<Integer> south, List<Integer> west, List<Integer> east) {
      this.north = north;
      this.south = south;
      this.west = west;
      this.east = east;
    }

    public boolean isInvisible(int value) {
      return Collections.max(west) >= value &&
        Collections.max(east) >= value &&
        Collections.max(north) >= value &&
        Collections.max(south) >= value;
    }

    public int scenicScore(int value) {
      var reverseWest = new ArrayList<>(west);
      Collections.reverse(reverseWest);
      var reverseNorth = new ArrayList<>(north);
      Collections.reverse(reverseNorth);
//      System.out.printf("%s%s%s%s%n", reverseWest, east, reverseNorth, south);
      return scenicScore(east, value) *
        scenicScore(south, value) *
        scenicScore(reverseWest, value) *
        scenicScore(reverseNorth, value);
    }

    private int scenicScore(List<Integer> line, int value) {
      for (int i = 0; i < line.size(); ++i) {
        if (line.get(i) >= value) {
          return i + 1;
        }
      }
      return line.size();
    }
  }

  private static class Forest {
    private final List<Integer> data = new ArrayList<>();
    private final int width;
    private final int height;

    public Forest(List<String> input) {
      width = input.get(0).length();
      for (var line: input) {
        data.addAll(line.chars().mapToObj(c -> Integer.parseInt("" + (char) c)).collect(Collectors.toList()));
      }
      height = data.size() / width;
    }

    public int countInvisibleTrees() {
      var count = 0;
      for (int j = 1; j < height - 1; ++j) {
        for (int i = 1; i < width - 1; ++i) {
          if (getView(i, j).isInvisible(get(i, j))) {
            ++count;
          }
        }
      }
      return count;
    }

    public int size() {
      return data.size();
    }

    public int maxScenicScore() {
      var max = 0;
      for (int j = 1; j < height - 1; ++j) {
        for (int i = 1; i < width - 1; ++i) {
//          System.out.printf("(%d,%d)>%d", i, j, getView(i, j).scenicScore(get(i, j)));
          max = Math.max(max, getView(i, j).scenicScore(get(i, j)));
        }
      }
      return max;
    }

    private int get(int x, int y) {
      return data.get(y * width + x);
    }

    private View getView(int x, int y) {
      var column = IntStream.range(x, (height - 1) * width + x + 1)
            .filter(n -> n % width == x)
            .mapToObj(data::get)
            .collect(Collectors.toList());
      return new View(
        column.subList(0, y),
        column.subList(y + 1, height),
        data.subList(y * width, y * width + x),
        data.subList(y * width + x + 1, y * width + height)
      );
    }
  }
}
