package dev.madfist.aoc2024;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day03 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    long sum = 0;
    for (String line : input) {
      var pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
      var matcher = pattern.matcher(line);
      while (matcher.find()) {
        var a = Long.parseLong(matcher.group(1));
        var b = Long.parseLong(matcher.group(2));
        sum += a * b;
      }

    }
    return Long.toString(sum);
  }

  @Override
  public String solveSecond(List<String> input) {
    long fullSum = 0;
    boolean isDoing = true;
    var doPattern = Pattern.compile("do\\(\\)");
    var dontPattern = Pattern.compile("don't\\(\\)");
    var mulPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
    for (String line : input) {
      var doMatcher = doPattern.matcher(line);
      var dontMatcher = dontPattern.matcher(line);
      List<Integer> dos = new ArrayList<>();
      List<Integer> donts = new ArrayList<>();
      while (doMatcher.find()) {
        dos.add(doMatcher.end());
      }
      while (dontMatcher.find()) {
        donts.add(dontMatcher.start());
      }
      var positionMap = dos.stream().collect(Collectors.toMap(Function.identity(), i -> true, (a, b) -> a, TreeMap::new));
      donts.forEach(i -> {
        if (positionMap.get(i) != null) {
          positionMap.remove(i);
        } else {
          positionMap.put(i, false);
        }
      });
      positionMap.put(0, isDoing);
      int doStart = 0;
      List<String> parts = new ArrayList<>();
      for (var entry : positionMap.entrySet()) {
        if (!isDoing && entry.getValue()) {
          doStart = entry.getKey();
          isDoing = true;
        } else if (isDoing && !entry.getValue()) {
          parts.add(line.substring(doStart, entry.getKey()));
          isDoing = false;
        }
      }
      if (isDoing) {
        parts.add(line.substring(doStart));
      }

      fullSum += parts.stream()
        .mapToLong(part -> {
          var matcher = mulPattern.matcher(part);
          long sum = 0;
          while (matcher.find()) {
            var a = Long.parseLong(matcher.group(1));
            var b = Long.parseLong(matcher.group(2));
            sum += a * b;
          }
          return sum;
        })
        .sum();
    }
    return Long.toString(fullSum);
  }
}
