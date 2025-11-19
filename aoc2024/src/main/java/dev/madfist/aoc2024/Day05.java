package dev.madfist.aoc2024;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day05 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var data = extract(input);
    var validUpdates = validate(data.a(), data.b()).get(true);
    var result = validUpdates.stream()
      .mapToLong(u -> u.get(u.size() / 2))
      .sum();
    return Long.toString(result);
  }

  @Override
  public String solveSecond(List<String> input) {
    var data = extract(input);
    var invalidUpdates = validate(data.a(), data.b()).get(false);
    var result = invalidUpdates.stream()
      .mapToLong(u -> u.stream()
        .sorted((a, b) ->
          a.equals(b)
            ? 0
            : (data.a().containsKey(a) && data.a().get(a).contains(b)
              ? -1
              : 1))
        .toList()
        .get(u.size() / 2))
      .sum();
    return Long.toString(result);
  }

  private Pair<Map<Integer, List<Integer>>, List<List<Integer>>> extract(List<String> input) {
    var rulePattern = Pattern.compile("(\\d+)\\|(\\d+)");
    var rules = new HashMap<Integer, List<Integer>>();
    var reverseRules = new HashMap<Integer, List<Integer>>();
    var updates = new ArrayList<List<Integer>>();
    for (String line : input) {
      if (line.isBlank()) continue;
      Matcher ruleMatcher = rulePattern.matcher(line);
      if (ruleMatcher.find()) {
        rules.compute(Integer.parseInt(ruleMatcher.group(1)), (k, v) -> {
          if (v == null) {
            v = new ArrayList<>();
          }
          v.add(Integer.parseInt(ruleMatcher.group(2)));
          return v;
        });
        reverseRules.compute(Integer.parseInt(ruleMatcher.group(2)), (k, v) -> {
          if (v == null) {
            v = new ArrayList<>();
          }
          v.add(Integer.parseInt(ruleMatcher.group(1)));
          return v;
        });
      } else {
         updates.add(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
      }
    }
    return new Pair<>(rules, updates);
  }

  private Map<Boolean, List<List<Integer>>> validate(Map<Integer, List<Integer>> rules, List<List<Integer>> updates) {
   return updates.stream().collect(Collectors.partitioningBy(update -> {
      var valid = true;
      for (int i = 0; i < update.size(); i++) {
        var current = update.get(i);
        if (i > 0) {
          valid = update.subList(0, i).stream().allMatch(u -> rules.containsKey(u) && rules.get(u).contains(current));
        }
        valid &= update.subList(i + 1, update.size()).stream()
          .noneMatch(u -> rules.containsKey(u) && rules.get(u).contains(current));
        if (!valid) {
          break;
        }
      }
      return valid;
    }));
  }
}
