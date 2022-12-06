package dev.madfist.aoc2022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Day01 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    return aggregateCalories(input).stream()
        .max(Integer::compareTo).orElse(-1).toString();
  }

  @Override
  public String solveSecond(List<String> input) {
    var calories = aggregateCalories(input);
    calories.sort(Collections.reverseOrder());
    var threeSum = calories.subList(0, 3);
    return threeSum.stream().reduce(0, Integer::sum).toString();
  }

  private List<Integer> aggregateCalories(List<String> input) {
    var calories = new ArrayList<Integer>();
    calories.add(0);
    input.forEach(line -> {
      if (!line.isBlank()) {
        calories.set(calories.size() - 1, calories.get(calories.size() - 1) + Integer.parseInt(line));
      } else {
        calories.add(0);
      }
    });
    return calories;
  }
}
