package dev.madfist.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day01Test {
  List<String> example = Arrays.asList(
    "3   4",
    "4   3",
    "2   5",
    "1   3",
    "3   9",
    "3   3");

  @Test
  public void part1() {
    var day = new Day01();
    assertEquals("11", day.solveFirst(example));
  }

  @Test
  public void part2() {
    var day = new Day01();
    assertEquals("31", day.solveSecond(example));
  }
}
