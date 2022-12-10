package dev.madfist.aoc2021;

import java.util.stream.Collector;

public class Utils {
  public static Collector<Object, StringBuilder, String> collectToString() {
    return Collector.of(
          StringBuilder::new,
          StringBuilder::append,
          StringBuilder::append,
          StringBuilder::toString);
  }
}
