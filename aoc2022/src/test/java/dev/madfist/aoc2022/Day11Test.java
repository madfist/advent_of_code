package dev.madfist.aoc2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Day11Test {
  List<String> example = Arrays.asList(
    "Monkey 0:",
    "  Starting items: 79, 98",
    "  Operation: new = old * 19",
    "  Test: divisible by 23",
    "    If true: throw to monkey 2",
    "    If false: throw to monkey 3",
    "",
    "Monkey 1:",
    "  Starting items: 54, 65, 75, 74",
    "  Operation: new = old + 6",
    "  Test: divisible by 19",
    "    If true: throw to monkey 2",
    "    If false: throw to monkey 0",
    "",
    "Monkey 2:",
    "  Starting items: 79, 60, 97",
    "  Operation: new = old * old",
    "  Test: divisible by 13",
    "    If true: throw to monkey 1",
    "    If false: throw to monkey 3",
    "",
    "Monkey 3:",
    "  Starting items: 74",
    "  Operation: new = old + 3",
    "  Test: divisible by 17",
    "    If true: throw to monkey 0",
    "    If false: throw to monkey 1");

  @Test
  public void inputTest() {
    var singleExample = List.of(
      "Monkey 0:",
      "  Starting items: 79, 98",
      "  Operation: new = old * 19",
      "  Test: divisible by 23",
      "    If true: throw to monkey 2",
      "    If false: throw to monkey 3");
    var day = new Day11();
    var monkeysManaged = day.parseInput(singleExample, true);
    assertEquals(1, monkeysManaged.size());
    assertIterableEquals(List.of(79, 98), monkeysManaged.get(0).getItems());
    assertEquals(63, monkeysManaged.get(0).getOperation().apply(10));
    assertEquals(23, monkeysManaged.get(0).getDivider());
    assertEquals(2, monkeysManaged.get(0).getNextMonkeyIndex1());
    assertEquals(3, monkeysManaged.get(0).getNextMonkeyIndex2());
    assertIterableEquals(List.of(3, 3), monkeysManaged.get(0).turn());
    assertIterableEquals(List.of(500, 620), monkeysManaged.get(0).getItems());
    assertEquals(2, monkeysManaged.get(0).getInspections());

    var monkeysUnmanaged = day.parseInput(singleExample, false);
    assertEquals(1, monkeysManaged.size());
    assertEquals(23, monkeysManaged.get(0).getDivider());
    assertIterableEquals(List.of(10, 6), monkeysUnmanaged.get(0).getItems());
    assertEquals(6, monkeysUnmanaged.get(0).getOperation().apply(10));
    assertEquals(23, monkeysUnmanaged.get(0).getDivider());
    assertEquals(2, monkeysUnmanaged.get(0).getNextMonkeyIndex1());
    assertEquals(3, monkeysUnmanaged.get(0).getNextMonkeyIndex2());
    assertIterableEquals(List.of(3, 3), monkeysUnmanaged.get(0).turn());
    assertIterableEquals(List.of(6, 22), monkeysUnmanaged.get(0).getItems());
    assertEquals(2, monkeysUnmanaged.get(0).getInspections());
  }

  @Test
  public void example1() {
    var day = new Day11();
    assertEquals("10605", day.solveFirst(example));
  }

  @Test
  public void example2() {
    var day = new Day11();
    assertEquals(24L, day.solve(example, 1, false));
    assertEquals(10197L, day.solve(example, 20, false));
    assertEquals("2713310158", day.solveSecond(example));
  }
}
