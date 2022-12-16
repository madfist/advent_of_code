package dev.madfist.aoc2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {
  List<String> example = Arrays.asList(
    "Sabqponm",
    "abcryxxl",
    "accszExk",
    "acctuvwj",
    "abdefghi");

  @Test
  public void example1() {
    var day = new Day12();
    assertEquals("31", day.solveFirst(example));
  }

  @Test
  public void example2() {
    var day = new Day12();
    assertEquals("29", day.solveSecond(example));
  }
}
