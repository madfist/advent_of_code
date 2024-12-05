package dev.madfist.aoc2024;

import java.util.stream.Collector;

public class Utils {
  public static Collector<Object, StringBuilder, String> collectToString() {
    return Collector.of(
          StringBuilder::new,
          StringBuilder::append,
          StringBuilder::append,
          StringBuilder::toString);
  }

  public static <T> T printReturn(T value) {
    System.out.println(value);
    return value;
  }
}
