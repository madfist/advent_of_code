package dev.madfist.aoc2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day01 implements Day{
  @Override
  public String solveFirst(List<String> input) {
    var columns = readInput(input);
    columns.a().sort(Integer::compareTo);
    columns.b().sort(Integer::compareTo);
    int sum = 0;
    for (int i = 0; i < columns.a().size(); i++) {
      sum += Math.abs(columns.a().get(i) - columns.b().get(i));
    }
    return Integer.toString(sum);
  }

  @Override
  public String solveSecond(List<String> input) {
    var columns = readInput(input);
    long sum = 0;
    for (int i = 0; i < columns.a().size(); i++) {
      var x = columns.a().get(i);
      var count = columns.b().stream()
        .filter(y -> Objects.equals(x, y))
        .count();
      sum += x * count;
    }
    return Long.toString(sum);
  }

  private Pair<List<Integer>, List<Integer>> readInput(List<String> input) {
    List<Integer> column1 = new ArrayList<>();
    List<Integer> column2 = new ArrayList<>();
    for (String line : input) {
      String[] split = line.split("\s+");
      column1.add(Integer.parseInt(split[0]));
      column2.add(Integer.parseInt(split[1]));
    }
    return new Pair<>(column1, column2);
  }
}
