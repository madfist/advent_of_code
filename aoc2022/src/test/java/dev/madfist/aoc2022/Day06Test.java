package dev.madfist.aoc2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Test {
  List<String> example1 = List.of(
    "mjqjpqmgbljsphdztnvjfqwrcgsmlb");
  List<String> example2 = List.of(
    "bvwbjplbgvbhsrlpgdmjqwftvncz");
  List<String> example3 = List.of(
    "nppdvjthqldpwncqszvftbrmjlhg");
  List<String> example4 = List.of(
    "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg");
  List<String> example5 = List.of(
    "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw");

  @Test
  public void example1() {
    var day = new Day06();
    assertEquals("7", day.solveFirst(example1));
    assertEquals("5", day.solveFirst(example2));
    assertEquals("6", day.solveFirst(example3));
    assertEquals("10", day.solveFirst(example4));
    assertEquals("11", day.solveFirst(example5));
  }

  @Test
  public void example2() {
    var day = new Day06();
    assertEquals("19", day.solveSecond(example1));
    assertEquals("23", day.solveSecond(example2));
    assertEquals("23", day.solveSecond(example3));
    assertEquals("29", day.solveSecond(example4));
    assertEquals("26", day.solveSecond(example5));
  }
}
