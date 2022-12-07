package dev.madfist.aoc2022;

import java.util.List;
import java.util.stream.Collectors;

public class Day06 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    return Integer.toString(findFirstUniqueSequence(input.get(0), 4));
  }

  @Override
  public String solveSecond(List<String> input) {
    return Integer.toString(findFirstUniqueSequence(input.get(0), 14));
  }

  private int findFirstUniqueSequence(String line, int sequenceSize) {
    for (int i = 0; i <= line.length() - sequenceSize; ++i) {
      var starter = line.substring(i, i + sequenceSize);
      var starterSet = starter.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
      if (starterSet.size() == sequenceSize) {
        return i + sequenceSize;
      }
    }
    return -1;
  }
}
