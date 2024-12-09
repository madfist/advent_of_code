package dev.madfist.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class Day04Test {
  private final String example =
    """
    MMMSXXMASM
    MSAMXMSMSA
    AMXSXMAAMM
    MSAMASMSMX
    XMASAMXAMM
    XXAMMXXAMA
    SMSMSASXSS
    SAXAMASAAA
    MAMMMXMMMM
    MXMXAXMASX
    """;

  @Test
  public void part1() {
    var day = new Day04();
    assertEquals("18", day.solveFirst(Arrays.asList(example.split("\n"))));
  }

  @Test
  public void part2() {
    var day = new Day04();
    assertEquals("9", day.solveSecond(Arrays.asList(example.split("\n"))));
  }
}

