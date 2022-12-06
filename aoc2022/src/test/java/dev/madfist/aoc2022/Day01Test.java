package dev.madfist.aoc2022;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class Day01Test {
  List<String> example = Arrays.asList(
    "1000",
    "2000",
    "3000",
    "",
    "4000",
    "",
    "5000",
    "6000",
    "",
    "7000",
    "8000",
    "9000",
    "",
    "10000");

  @Test
  public void example1() {

    var day = new Day01();
    assertEquals("24000", day.solveFirst(example));
  }

  @Test
  public void example2() {

    var day = new Day01();
    assertEquals("45000", day.solveSecond(example));
  }
}
