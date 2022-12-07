package dev.madfist.aoc2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Test {
  List<String> example = Arrays.asList(
    "2-4,6-8",
    "2-3,4-5",
    "5-7,7-9",
    "2-8,3-7",
    "6-6,4-6",
    "2-6,4-8");

  @Test
  public void example1() {
    var day = new Day04();
    assertEquals("2", day.solveFirst(example));
  }

  @Test
  public void example2() {
    var day = new Day04();
    assertEquals("4", day.solveSecond(example));
  }
}
