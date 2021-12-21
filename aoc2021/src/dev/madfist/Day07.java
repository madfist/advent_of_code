package dev.madfist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Day07 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var positions = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).toList();
    var distance = IntStream.range(Collections.min(positions), Collections.max(positions))
      .map(o -> positions.stream().mapToInt(p -> Math.abs(p - o)).sum()).min();
    return Integer.toString(distance.isPresent() ? distance.getAsInt() : -1);
  }

  @Override
  public String solveSecond(List<String> input) {
    var positions = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).toList();
    var distance = IntStream.range(Collections.min(positions), Collections.max(positions))
      .map(o -> positions.stream().mapToInt(p -> Math.abs(p - o) * (Math.abs(p - o) + 1) / 2).sum()).min();
    return Integer.toString(distance.isPresent() ? distance.getAsInt() : -1);
  }
}
