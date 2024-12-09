package dev.madfist.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class Day03Test {
  private final String example =
    """
    xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
    something_mul(1,1)don't()_mul(2,2)don't()blablabla
    elsemul(9,1)do()mul(1,1)do()mul(1,1)do()mul(1,1)do()mul(1,1)don't()mul(1,1)
    """;

  @Test
  public void part1() {
    var day = new Day03();
    assertEquals("180", day.solveFirst(Arrays.asList(example.split("\n"))));
  }

  @Test
  public void part2() {
    var day = new Day03();
    assertEquals("53", day.solveSecond(Arrays.asList(example.split("\n"))));
  }
}
