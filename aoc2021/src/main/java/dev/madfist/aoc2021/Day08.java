package dev.madfist.aoc2021;

import java.util.*;
import java.util.stream.Collectors;

public class Day08 implements Day {
  private static class Entry {
    final List<String> mappings;
    final List<String> digits;

    private boolean contains(String a, String b) {
      return b.chars().allMatch(c -> a.indexOf(c) >= 0);
    }

    private String ordered(String s) {
      var a = s.toCharArray();
      Arrays.sort(a);
      return new String(a);
    }

    Entry(String input) {
      var data = Arrays.stream(input.split("\\s")).map(this::ordered).toArray(String[]::new);

      mappings = Arrays.asList(Arrays.copyOfRange(data, 0, 10));
      digits = Arrays.asList(Arrays.copyOfRange(data, 11, 15));
    }

    int solve() {
      Map<String, Character> mapping = new HashMap<>();
      var one = mappings.parallelStream().filter(m -> m.length() == 2).findFirst().orElseThrow(RuntimeException::new);
      mapping.put(one, '1');
      var seven = mappings.parallelStream().filter(m -> m.length() == 3).findFirst().orElseThrow(RuntimeException::new);
      mapping.put(seven, '7');
      var four = mappings.parallelStream().filter(m -> m.length() == 4).findFirst().orElseThrow(RuntimeException::new);
      mapping.put(four, '4');
      var fiveParts = mappings.parallelStream().filter(m -> m.length() == 5).collect(Collectors.toList());
      var sixParts = mappings.parallelStream().filter(m -> m.length() == 6).collect(Collectors.toList());
      var eight = mappings.parallelStream().filter(m -> m.length() == 7).findFirst().orElseThrow(RuntimeException::new);
      mapping.put(eight, '8');

      var three = fiveParts.parallelStream().filter(s -> contains(s, one)).findFirst().orElseThrow(RuntimeException::new);
      mapping.put(three, '3');
      var nine = sixParts.parallelStream().filter(s -> contains(s, three)).findFirst().orElseThrow(RuntimeException::new);
      mapping.put(nine, '9');
      var five = fiveParts.parallelStream().filter(s -> contains(nine, s) && !s.equals(three)).findFirst().orElseThrow(RuntimeException::new);
      mapping.put(five, '5');
      var two = fiveParts.parallelStream()
        .filter(s -> !s.equals(three) && !s.equals(five))
        .findFirst()
        .orElseThrow(RuntimeException::new);
      mapping.put(two, '2');
      var six = sixParts.parallelStream().filter(s -> contains(s, five) && !s.equals(nine)).findFirst().orElseThrow(RuntimeException::new);
      mapping.put(six, '6');
      var zero = sixParts.parallelStream().filter(s ->!s.equals(nine) && !s.equals(six)).findFirst().orElseThrow(RuntimeException::new);
      mapping.put(zero, '0');

      var numberStr = digits.parallelStream().map(mapping::get).map(String::valueOf).collect(Collectors.joining());

      return Integer.parseInt(numberStr);
    }
  }
  @Override
  public String solveFirst(List<String> input) {
    return Long.toString(input.stream()
      .map(l -> l.substring(l.indexOf('|') + 2).split("\\s"))
      .flatMap(Arrays::stream)
      .mapToInt(String::length)
      .filter(l -> l == 2 || l == 3 || l == 4 || l == 7)
      .count());
  }

  @Override
  public String solveSecond(List<String> input) {
    return Integer.toString(input.parallelStream().map(Entry::new).mapToInt(Entry::solve).sum());
  }
}
