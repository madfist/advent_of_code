package dev.madfist.aoc2021;

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day14 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    // var template = input.get(0);
    // var rules = input.subList(2, input.size()).stream()
    //   .map(Rule::new)
    //   .collect(Collectors.toList());
    // for (int i = 0; i < 10; ++i) {
    //   var newBuilder = new StringBuilder(template);
    //   for (int j = 0, k = 1; j < template.length() - 1; ++j) {
    //     var pair = template.substring(j, j + 2);
    //     for (var rule: rules) {
    //       if (rule.pattern.equals(pair)) {
    //         newBuilder.insert(j + k++, rule.insert);
    //         break;
    //       }
    //     }
    //   }
    //   template = newBuilder.toString();
    // }
    // var characterCounts = template.chars()
    //   .mapToObj(c -> (char) c)
    //   .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    // var maxCount = characterCounts.values().stream().mapToLong(Long::longValue).max().orElse(-1L);
    // var minCount = characterCounts.values().stream().mapToLong(Long::longValue).min().orElse(-1L);
    // return Long.toString(maxCount - minCount);
    var template = input.get(0);
    var counter = new CharacterCounter(input.subList(2, input.size()), 10);
    var characterCounts = counter.count(template);
    System.out.println(characterCounts);
    var maxCount = characterCounts.values().stream().mapToLong(Long::longValue).max().orElse(-1L);
    var minCount = characterCounts.values().stream().mapToLong(Long::longValue).min().orElse(-1L);
    return Long.toString(maxCount - minCount);
  }

  @Override
  public String solveSecond(List<String> input) {
    var template = input.get(0);
    var counter = new CharacterCounter(input.subList(2, input.size()), 40);
    var characterCounts = counter.count(template);
    var maxCount = characterCounts.values().stream().mapToLong(Long::longValue).max().orElse(-1L);
    var minCount = characterCounts.values().stream().mapToLong(Long::longValue).min().orElse(-1L);
    return Long.toString(maxCount - minCount);
  }

  private static class Rule {
    String pattern;
    char insert;

    public Rule(String line) {
      pattern = line.substring(0,2);
      insert = line.charAt(6);
    }
  }

  private static class CharacterCounter {
    private Map<Character, Long> counterMap = new ConcurrentHashMap<>();
    private final int cycles;
    private Map<String, Character> rules = new HashMap<>();

    public CharacterCounter(List<String> rules, int cycles) {
      this.rules = rules.stream().collect(Collectors.toMap(rule -> rule.substring(0,2), rule -> rule.charAt(6)));
      this.cycles = cycles;
    }

    public Map<Character, Long> count(String text) {
      var initialList = new ArrayList<String>();
      for (int i = 0; i < text.length() - 1; ++i) {
        initialList.add(text.substring(i, i + 2));
      }
      initialList.parallelStream().forEach(pair -> count(pair, 0));
      counterMap.merge(text.charAt(text.length() - 1), 1L, Long::sum);
      return counterMap;
    }

    private void count(String pair, int level) {
      if (level >= cycles) {
        return;
      }
      //System.out.println(pair + ' ' + level);
      var middle = getMiddle(pair);
      if (level == 0) {
        counterMap.merge(pair.charAt(0), 1L, Long::sum);
      }
      if (middle != null) {
        counterMap.merge(middle, 1L, Long::sum);
      }
      count("" + pair.charAt(0) + middle, level + 1);
      count("" + middle + pair.charAt(1), level + 1);
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
