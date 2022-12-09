package dev.madfist.aoc2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test {
  List<String> example1 = Arrays.asList(
    "R 4",
    "U 4",
    "L 3",
    "D 1",
    "R 4",
    "D 1",
    "L 5",
    "R 2");

  List<String> example2 = Arrays.asList(
    "R 2",
    "U 2",
    "L 4",
    "D 4",
    "R 4",
    "D 3",
    "D 3");

  List<String> example3 = Arrays.asList(
    "R 5",
    "U 8",
    "L 8",
    "D 3",
    "R 17",
    "D 10",
    "L 25",
    "U 20");

  @Test
  public void example1() {
    var day = new Day09();
    assertEquals("13", day.solveFirst(example1));
    assertEquals("17", day.solveFirst(example2));
  }

  @Test
  public void example2() {
    var day = new Day09();
    assertEquals("1", day.solveSecond(example1));
    assertEquals("36", day.solveSecond(example3));
  }
}
