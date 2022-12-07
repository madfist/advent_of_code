package dev.madfist.aoc2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05Test {
  List<String> example = Arrays.asList(
    "    [D]",
    "[N] [C]",
    "[Z] [M] [P]",
    " 1   2   3",
    "",
    "move 1 from 2 to 1",
    "move 3 from 1 to 3",
    "move 2 from 2 to 1",
    "move 1 from 1 to 2");

  @Test
  public void example1() {
    var day = new Day05();
    assertEquals("CMZ", day.solveFirst(example));
  }

  @Test
  public void example2() {
    var day = new Day05();
    assertEquals("MCD", day.solveSecond(example));
  }
}
