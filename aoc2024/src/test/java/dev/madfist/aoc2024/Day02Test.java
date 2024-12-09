package dev.madfist.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class Day02Test {
  private final String example =
    """
    7 6 4 2 1
    1 2 7 8 9
    9 7 6 2 1
    1 3 2 4 5
    8 6 4 4 1
    1 3 6 7 9
    1 3 3 6 7
    4 7 6 5 4
    """;

  @Test
  public void part1() {
    var day = new Day02();
    assertEquals("2", day.solveFirst(Arrays.asList(example.split("\n"))));
  }

  @Test
  public void part2() {
    var day = new Day02();
    assertEquals("6", day.solveSecond(Arrays.asList(example.split("\n"))));
  }
}
