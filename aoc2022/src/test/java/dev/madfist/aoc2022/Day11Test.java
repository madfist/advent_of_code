package dev.madfist.aoc2022;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
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
    var monkeysManaged = day.parseMonkeys(singleExample, true);
    assertEquals(1, monkeysManaged.size());
    assertIterableEquals(List.of(BigInteger.valueOf(79), BigInteger.valueOf(98)), monkeysManaged.get(0).getItems());
    assertEquals(BigInteger.valueOf(63), monkeysManaged.get(0).getOperation().apply(BigInteger.valueOf(10)));
    assertEquals(BigInteger.valueOf(23), monkeysManaged.get(0).getDivisor());
    assertEquals(2, monkeysManaged.get(0).getNextMonkeyIndex1());
    assertEquals(3, monkeysManaged.get(0).getNextMonkeyIndex2());
    assertIterableEquals(List.of(3, 3), monkeysManaged.get(0).turn());
    assertIterableEquals(List.of(BigInteger.valueOf(500), BigInteger.valueOf(620)), monkeysManaged.get(0).getItems());
    assertEquals(BigInteger.valueOf(2), monkeysManaged.get(0).getInspections());

    var monkeysUnmanaged = day.parseMonkeys(singleExample, false);
    assertEquals(1, monkeysManaged.size());
    assertEquals(BigInteger.valueOf(23), monkeysManaged.get(0).getDivisor());
    assertEquals(BigInteger.valueOf(190), monkeysUnmanaged.get(0).getOperation().apply(BigInteger.valueOf(10)));
    assertEquals(BigInteger.valueOf(23), monkeysUnmanaged.get(0).getDivisor());
    assertEquals(2, monkeysUnmanaged.get(0).getNextMonkeyIndex1());
    assertEquals(3, monkeysUnmanaged.get(0).getNextMonkeyIndex2());
    assertIterableEquals(List.of(3, 3), monkeysUnmanaged.get(0).turn());
    assertIterableEquals(List.of(BigInteger.valueOf(1501), BigInteger.valueOf(1862)), monkeysUnmanaged.get(0).getItems());
    assertEquals(BigInteger.valueOf(2), monkeysUnmanaged.get(0).getInspections());
  }

  @Test
  public void example1() {
    var day = new Day11();
    assertEquals("10605", day.solveFirst(example));
  }

  @Test
  public void example2() {
    var day = new Day11();
    var monkeyBusiness = day.parseMonkeyBusiness(example);
    assertEquals(List.of(6L, 4L, 3L, 2L), monkeyBusiness.doRounds(1));
    assertEquals(List.of(103L, 99L, 97L, 8L), monkeyBusiness.doRounds(19));
    assertEquals(List.of(5204L, 5192L, 4792L, 199L), monkeyBusiness.doRounds(980));
    assertEquals("2713310158", day.solveSecond(example));
  }
}
