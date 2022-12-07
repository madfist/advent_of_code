package dev.madfist.aoc2022;

import java.util.List;
import java.util.stream.Collectors;

public class Day04 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var containingRanges = input.stream()
      .filter(line -> {
        var elves = line.split(",");
        var range1 = new Range(elves[0]);
        var range2 = new Range(elves[1]);
        return range1.includes(range2) || range2.includes(range1);
      })
      .count();
    return Long.toString(containingRanges);
  }

  @Override
  public String solveSecond(List<String> input) {
    var containingRanges = input.stream()
      .filter(line -> {
        var elves = line.split(",");
        var range1 = new Range(elves[0]);
        var range2 = new Range(elves[1]);
        return range1.overlaps(range2) || range2.includes(range1);
      })
      .count();
    return Long.toString(containingRanges);
  }

  private static class Range {
    public int from;
    public int to;

    public Range(String txt) {
      var parts = txt.split("-");
      from = Integer.parseInt(parts[0]);
      to = Integer.parseInt(parts[1]);
    }

    public boolean includes(Range range) {
      return from <= range.from && range.to <= to;
    }

    public boolean overlaps(Range range) {
      return from <= range.from && range.from <= to ||
        range.from <= from && from <= range.to;
    }
  }
}
