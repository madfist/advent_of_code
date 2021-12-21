package dev.madfist;

import java.util.*;
import java.util.stream.IntStream;

public class Day09 implements Day {
  private static class Coordinate {
    int x;
    int y;
    int z;
    Coordinate(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }
  }
  private static class DepthMap {
    int width;
    int height;
    List<Coordinate> coordinates;
    DepthMap(List<String> input) {
      width = input.get(0).length();
      height = input.size();
      var depthMap = input.parallelStream()
      .map(s -> Arrays.stream(s.split("")).map(Integer::parseInt).toArray(Integer[]::new))
      .flatMap(Arrays::stream)
      .toList();
      coordinates = IntStream.range(0, width * height)
        .mapToObj(i -> new Coordinate(i % width, i / height, depthMap.get(i)))
        .toList();
    }
    Coordinate get(int x, int y) {
      return coordinates.get(y * width + x);
    }
    int getZ(int x, int y) {
      return get(x, y).z;
    }
    List<Coordinate> getLowPoints() {
      List<Coordinate> lowPoints = new ArrayList<>();
      for (int i = 0; i < width; ++i) {
        for (int j = 0; j < height; ++j) {
          List<Integer> block = new ArrayList<>();
          var origo = coordinates.get(j * width + i);
          if (i > 0) {
            block.add(getZ(i -1, j));
          }
          if (i < width - 1) {
            block.add(getZ(i + 1, j));
          }
          if (j > 0) {
            block.add(getZ(i, j - 1));
          }
          if (j < height - 1) {
            block.add(getZ(i,j + 1));
          }
          if (block.stream().allMatch(z -> z > origo.z)) {
            lowPoints.add(origo);
          }
        }
      }
      return lowPoints;
    }
    List<Set<Coordinate>> getBasins() {
      return getLowPoints().parallelStream().map(lp -> checkNeighbours(lp.x, lp.y, new HashSet<>())).toList();
    }
    private Set<Coordinate> checkNeighbours(int x, int y, Set<Coordinate> coordinateSet) {
      if (getZ(x, y) == 9) {
        return coordinateSet;
      }
      coordinateSet.add(get(x, y));
      if (x > 0 && getZ(x - 1, y) > getZ(x, y)) {
        coordinateSet.addAll(checkNeighbours(x - 1, y, coordinateSet));
      }
      if (x < width - 1 && getZ(x + 1, y) > getZ(x, y)) {
        coordinateSet.addAll(checkNeighbours(x + 1, y, coordinateSet));
      }
      if (y > 0 && getZ(x, y - 1) > getZ(x, y)) {
        coordinateSet.addAll(checkNeighbours(x, y - 1, coordinateSet));
      }
      if (y < height - 1 && getZ(x, y + 1) > getZ(x, y)) {
        coordinateSet.addAll(checkNeighbours(x, y + 1, coordinateSet));
      }
      return coordinateSet;
    }
  }
  @Override
  public String solveFirst(List<String> input) {
    var depthMap = new DepthMap(input);
    return Integer.toString(depthMap.getLowPoints().stream().mapToInt(c -> c.z + 1).sum());
  }

  @Override
  public String solveSecond(List<String> input) {
    var depthMap = new DepthMap(input);
    var largestBasins = depthMap.getBasins().stream().map(Set::size).sorted(Collections.reverseOrder()).toArray(Integer[]::new);
    return Integer.toString(largestBasins[0] * largestBasins[1] * largestBasins[2]);
  }
}
