package dev.madfist.aoc2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var rulePattern = Pattern.compile("(\\d+)\\|(\\d)+");
    var rules = new HashMap<Integer, List<Integer>>();
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
      } else {
         updates.add(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
      }
    }
    return "";
  }

  @Override
  public String solveSecond(List<String> input) {
    return "";
  }
}
