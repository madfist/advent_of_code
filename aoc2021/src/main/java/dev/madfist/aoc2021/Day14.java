package dev.madfist.aoc2021;

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    return Long.toString(count(input, 10));
  }

  @Override
  public String solveSecond(List<String> input) {
    return Long.toString(count(input, 40));
  }

  private Long count(List<String> input, int cycles) {
    var template = input.get(0);
    var counter = new CharacterCounter(input.subList(2, input.size()), cycles);
    var characterCounts = counter.count(template);
    var maxCount = characterCounts.values().stream().mapToLong(Long::longValue).max().orElse(-1L);
    var minCount = characterCounts.values().stream().mapToLong(Long::longValue).min().orElse(-1L);
    return maxCount - minCount;
  }

  private static class CharacterCounter {
    private final Map<Character, Long> counterMap = new ConcurrentHashMap<>();
    private Map<String, Long> pairOccurrences = new ConcurrentHashMap<>();
    private final int cycles;
    private final Map<String, Character> rules;

    public CharacterCounter(List<String> rules, int cycles) {
      this.rules = rules.stream().collect(Collectors.toMap(rule -> rule.substring(0,2), rule -> rule.charAt(6)));
      this.cycles = cycles;
    }

    public Map<Character, Long> count(String text) {
      for (int i = 0; i < text.length() - 1; ++i) {
        pairOccurrences.merge(text.substring(i, i + 2), 1L, Long::sum);
      }
      for (int i = 0; i < cycles; ++i) {
        Map<String, Long> pairsToAdd = new HashMap<>();
        pairOccurrences.forEach((pair, count) -> {
          var middle = getMiddle(pair);
          pairsToAdd.merge("" + pair.charAt(0) + middle, count, Long::sum);
          pairsToAdd.merge("" + middle + pair.charAt(1), count, Long::sum);
        });
        pairOccurrences = pairsToAdd;
      }
      pairOccurrences.forEach((pair, count) -> {
        counterMap.merge(pair.charAt(0), count, Long::sum);
        counterMap.merge(pair.charAt(1), count, Long::sum);
      });
      counterMap.replaceAll((ch, count) -> count / 2);
      counterMap.merge(text.charAt(0), 1L, Long::sum);
      counterMap.merge(text.charAt(text.length() - 1), 1L, Long::sum);
      return counterMap;
    }

    private Character getMiddle(String pair) {
      for (var key: rules.keySet()) {
        if (key.equals(pair)) {
          return rules.get(key);
        }
      }
      return null;
    }
  }
}
