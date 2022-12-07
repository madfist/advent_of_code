package dev.madfist.aoc2021;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day05Test {

  @Test
  void testFirstExample1() {
    var input = List.of(
      "0,9 -> 5,9", "8,0 -> 0,8",
      "9,4 -> 3,4", "2,2 -> 2,1",
      "7,0 -> 7,4", "6,4 -> 2,0",
      "0,9 -> 2,9", "3,4 -> 1,4",
      "0,0 -> 8,8", "5,5 -> 8,2");

    var day5 = new Day05();
    assertEquals("5", day5.solveFirst(input));
  }

  @Test
  void testFirstExample2() {
    var input = List.of(
      "0,9 -> 5,9", "8,0 -> 0,8",
      "9,4 -> 3,4", "2,2 -> 2,1",
      "7,0 -> 7,4", "6,4 -> 2,0",
      "0,9 -> 2,9", "3,4 -> 1,4",
      "0,0 -> 8,8", "5,5 -> 8,2",
      "4,7 -> 3,7", "4,7 -> 6,7",
      "2,0 -> 2,1", "9,4 -> 3,4");

    var day5 = new Day05();
    assertEquals("12", day5.solveFirst(input));
  }

  @Test
  void testSecondExample1() {
    var input = List.of(
      "0,9 -> 5,9", "8,0 -> 0,8",
      "9,4 -> 3,4", "2,2 -> 2,1",
      "7,0 -> 7,4", "6,4 -> 2,0",
      "0,9 -> 2,9", "3,4 -> 1,4",
      "0,0 -> 8,8", "5,5 -> 8,2");

    var day5 = new Day05();
    assertEquals("12", day5.solveSecond(input));
  }

  @Test
  void testSecondExample2() {
    var input = List.of(
      "0,9 -> 5,9", "8,0 -> 0,8",
      "9,4 -> 3,4", "2,2 -> 2,1",
      "7,0 -> 7,4", "6,4 -> 2,0",
      "0,9 -> 2,9", "3,4 -> 1,4",
      "0,0 -> 8,8", "5,5 -> 8,2",
      "4,7 -> 3,7", "4,7 -> 6,7",
      "2,0 -> 2,1", "9,4 -> 3,4");

    var day5 = new Day05();
    assertEquals("18", day5.solveSecond(input));
  }
}
