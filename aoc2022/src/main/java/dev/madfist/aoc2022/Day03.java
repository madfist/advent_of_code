package dev.madfist.aoc2022;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Day03 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var sum = input.stream()
      .mapToInt(line -> {
        var part1 = line.substring(0, line.length() / 2);
        var part2 = line.substring(line.length() / 2);
        var set1 = part1.chars().mapToObj(c -> (char)c).collect(Collectors.toSet());
        var set2 = part2.chars().mapToObj(c -> (char)c).collect(Collectors.toSet());
        set1.retainAll(set2);
        return set1.stream().mapToInt(this::itemPriority).sum();
      })
      .sum();
    return Integer.toString(sum);
  }

  @Override
  public String solveSecond(List<String> input) {
    var threes = input.stream().collect(blockCollector(3));
    var sum = threes.stream()
      .mapToInt(lines -> {
        var set1 = lines.get(0).chars().mapToObj(c -> (char)c).collect(Collectors.toSet());
        var set2 = lines.get(1).chars().mapToObj(c -> (char)c).collect(Collectors.toSet());
        var set3 = lines.get(2).chars().mapToObj(c -> (char)c).collect(Collectors.toSet());
        set1.retainAll(set2);
        set1.retainAll(set3);
        return set1.stream().mapToInt(this::itemPriority).sum();
      })
      .sum();
    return Integer.toString(sum);
  }

  private int itemPriority(int ascii) {
    if (ascii > 96) {
      return ascii - 96;
    }
    return ascii - 38;
  }

  private static Collector<String, List<List<String>>, List<List<String>>> blockCollector(int blockSize) {
    return Collector.of(
      ArrayList::new,
      (list, value) -> {
        List<String> block = (list.isEmpty() ? null : list.get(list.size() - 1));
        if (block == null || block.size() == blockSize) {
          list.add(block = new ArrayList<>(blockSize));
        }
        block.add(value);
      },
      (r1, r2) -> { throw new UnsupportedOperationException("Parallel processing not supported"); }
    );
}
}
