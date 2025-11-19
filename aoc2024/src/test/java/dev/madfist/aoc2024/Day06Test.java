package dev.madfist.aoc2024;

import org.junit.jupiter.api.Test;

public class Day06Test {
  private DayTest<Day06> dayTest = new DayTest<>(Day06.class) {
    @Override
    public String example() {
      return """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
        """;
    }

    @Override
    public String expected1() {
      return "41";
    }

    @Override
    public String expected2() {
      return "";
    }
  };

  @Test
  public void part1() {
    dayTest.testPart1();
  }

  @Test
  public void part2() {
    dayTest.testPart2();
  }
}
