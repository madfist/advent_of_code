package dev.madfist.aoc2024;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    return Long.toString(solve(input, false));
  }

  @Override
  public String solveSecond(List<String> input) {
    return Long.toString(solve(input, true));
  }

  private long solve(List<String> input, final boolean canSkip) {
    return input.stream()
      .filter(line -> {
        var levels = Arrays.stream(line.split(" "))
          .map(Integer::parseInt)
          .collect(Collectors.toCollection(LinkedList::new));
        return checkLevels(levels, canSkip);
      })
      .count();
  }

  private boolean checkLevels(List<Integer> levels, boolean canSkip) {
    var increasing = levels.size() > 1 && levels.get(0) < levels.get(1);
    for (int i = 1; i < levels.size(); i++) {
      if (levels.get(i - 1).equals(levels.get(i)) ||
          increasing ^ (levels.get(i - 1) < levels.get(i)) ||
          Math.abs(levels.get(i) - levels.get(i - 1)) > 3) {
        for (int j = 0; canSkip && j < levels.size(); j++) {
          var skippedLevel = new LinkedList<>(levels);
          skippedLevel.remove(j);
          if (checkLevels(skippedLevel, false)) {
            return true;
          }
        }
        return false;
      }
    }
    return true;
  }
}
