package dev.madfist.aoc2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test {
  List<String> example = Arrays.asList(
    "vJrwpWtwJgWrhcsFMMfFFhFp",
    "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
    "PmmdzqPrVvPwwTWBwg",
    "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
    "ttgJtRGJQctTZtZT",
    "CrZsJsPPZsGzwwsLwLmpwMDw");

  @Test
  public void example1() {
    var day = new Day03();
    assertEquals("157", day.solveFirst(example));
  }

  @Test
  public void example2() {
    var day = new Day03();
    assertEquals("70", day.solveSecond(example));
  }
}
