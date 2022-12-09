package dev.madfist.aoc2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Test {
  List<String> example = Arrays.asList(
    "30373",
    "25512",
    "65332",
    "33549",
    "35390");

  @Test
  public void example1() {
    var day = new Day08();
    assertEquals("21", day.solveFirst(example));
  }

  @Test
  public void example2() {
    var day = new Day08();
    assertEquals("8", day.solveSecond(example));
  }
}
