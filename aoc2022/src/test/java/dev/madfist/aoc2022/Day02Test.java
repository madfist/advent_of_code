package dev.madfist.aoc2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Test {
  List<String> example = Arrays.asList(
    "A Y",
    "B X",
    "C Z");

  @Test
  public void example1() {
    var day = new Day02();
    assertEquals("15", day.solveFirst(example));
  }

  @Test
  public void example2() {
    var day = new Day02();
    assertEquals("12", day.solveSecond(example));
  }
}
