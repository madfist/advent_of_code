package dev.madfist.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

public abstract class DayTest<D extends Day> {
  private final Class<D> dayClass;
  public DayTest(Class<D> dayClass) {
    this.dayClass = dayClass;
  }
  public abstract String example();
  public abstract String expected1();
  public abstract String expected2();
  public void testPart1() {
    try {
      D day = dayClass.getDeclaredConstructor().newInstance();
      assertEquals(expected1(), day.solveFirst(Arrays.asList(example().split("\n"))));
    } catch (Exception e) {
      System.err.println("ERR " + e);
    }
  }
  public void testPart2() {
    try {
      D day = dayClass.getDeclaredConstructor().newInstance();
      assertEquals(expected2(), day.solveSecond(Arrays.asList(example().split("\n"))));
    } catch (Exception e) {
      System.err.println("ERR " + e);
    }
  }
}
