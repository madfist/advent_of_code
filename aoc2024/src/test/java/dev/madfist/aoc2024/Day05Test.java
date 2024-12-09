package dev.madfist.aoc2024;

import org.junit.jupiter.api.Test;

public class Day05Test {
  DayTest<Day05> dayTest = new DayTest<>(Day05.class) {
    @Override
    public String example() {
      return """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13

        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
        """;
    }

    @Override
    public String expected1() {
      return "143";
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
