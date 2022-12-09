package dev.madfist.aoc2021;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Test {
  List<String> example = Arrays.asList(
    "NNCB",
    "",
    "CH -> B",
    "HH -> N",
    "CB -> H",
    "NH -> C",
    "HB -> C",
    "HC -> B",
    "HN -> C",
    "NN -> C",
    "BH -> H",
    "NC -> B",
    "NB -> B",
    "BN -> B",
    "BB -> N",
    "BC -> B",
    "CC -> N",
    "CN -> C");

  @Test
  public void example1() {
    var day = new Day14();
    assertEquals("1588", day.solveFirst(example));
  }

   @Test
   public void example2() {
     var day = new Day14();
     assertEquals("2188189693529", day.solveSecond(example));
   }
}
