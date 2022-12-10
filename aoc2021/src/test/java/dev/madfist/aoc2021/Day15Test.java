package dev.madfist.aoc2021;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Test {
    List<String> example = Arrays.asList(
      "1163751742",
      "1381373672",
      "2136511328",
      "3694931569",
      "7463417111",
      "1319128137",
      "1359912421",
      "3125421639",
      "1293138521",
      "2311944581");

  @Test
  public void example1() {
    var day = new Day15();
    assertEquals("40", day.solveFirst(example));
  }

   @Test
   public void example2() {
     var day = new Day15();
     assertEquals("315", day.solveSecond(example));
   }
}
