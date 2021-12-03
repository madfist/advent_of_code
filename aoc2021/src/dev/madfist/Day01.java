package dev.madfist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var depths = input.stream().map(Integer::parseInt).toArray(Integer[]::new);
    int prev = Integer.MAX_VALUE;
    int count = 0;
    for (Integer depth : depths) {
      if (depth > prev) {
        count++;
      }
      prev = depth;
    }

    return Integer.toString(count);
  }

  @Override
  public String solveSecond(List<String> input) {
    var depths = input.stream().map(Integer::parseInt).toArray(Integer[]::new);
    var depthOfThree = new ArrayList<Integer>();
    for (int i = 0; i < depths.length; ++i) {
      if (i > 1) {
        depthOfThree.add(depths[i] + depths[i-1] + depths[i-2]);
      }
    }

    return solveFirst(depthOfThree.stream().map(i -> Integer.toString(i)).collect(Collectors.toList()));
  }
}
