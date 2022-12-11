package dev.madfist.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day11 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var numberFunctionPattern = Pattern.compile("old ([+*]) (\\d+)");
    var quadFunctionPattern = Pattern.compile("old ([+*]) old");
    var monkeys = new ArrayList<Monkey>();
    Monkey monkey = null;
    int divider = 0;
    int trueMonkey = -1;
    for (var line: input) {
      if (line.startsWith("Monkey")) {
        monkey = new Monkey();
      }
      if (line.startsWith("  Starting") && monkey != null) {
        monkey.addItems(Arrays.stream(line.substring(18).split(", "))
          .map(Integer::parseInt).collect(Collectors.toList()));
      }
      if (line.startsWith("  Operation") && monkey != null) {
        var opStr = line.substring(19);
        System.out.println("op " + opStr);
        var opMatcher = numberFunctionPattern.matcher(opStr);
        if (opMatcher.matches()) {
            final var other = Integer.parseInt(opMatcher.group(2));
          if (opMatcher.group(1).equals("+")) {
            monkey.assignOperation(num -> num + other);
          } else {
            monkey.assignOperation(num -> num * other);
          }
        } else {
          var numMatcher = quadFunctionPattern.matcher(opStr);
          if (numMatcher.matches()) {
            monkey.assignOperation(opMatcher.group(1).equals("+")
              ? num -> num + num
              : num -> num * num);
          }
        }
      }
      if (line.startsWith("  Test")) {
        divider = Integer.parseInt(line.substring(21));
      }
      if (line.startsWith("    If true")) {
        trueMonkey = Integer.parseInt(line.substring(29));
      }
      if (line.startsWith("    If false") && monkey != null) {
        final int d = divider;
        final int t = trueMonkey;
        final int f = Integer.parseInt(line.substring(30));
        monkey.assignTest(item -> item % d == 0 ? t : f);
      }
      if (line.isBlank()) {
        monkeys.add(monkey);
      }
    }
    for (int i = 0; i < 20; ++i) {
      for (var m : monkeys) {
        var nextMonkeys = m.turn();
        for (int n = 0; n < nextMonkeys.size(); ++n) {
          m.throwItem(n, monkeys.get(nextMonkeys.get(n)));
        }
      }
    }
    monkeys.sort(Comparator.comparing(m -> m.inspections));
    var result = monkeys.get(0).inspections * monkeys.get(1).inspections;
    return Integer.toString(result);
  }

  @Override
  public String solveSecond(List<String> input) {
    return null;
  }

  private static class Monkey {
    List<Integer> items;
    Function<Integer, Integer> operation;
    Function<Integer, Integer> test;
    int inspections = 0;

    public void addItems(List<Integer> items) {
      this.items = items;
    }

    public void assignOperation(Function<Integer, Integer> operation) {
      this.operation = operation;
    }

    public void assignTest(Function<Integer, Integer> test) {
      this.test = test;
    }

    public List<Integer> turn() {
      var itemThrows = new ArrayList<Integer>(items.size());
      for (var item: items) {
        var newValue = Math.floorDiv(operation.apply(item), 3);
        itemThrows.add(test.apply(newValue));
        ++inspections;
      }
      return itemThrows;
    }

    public void throwItem(int index, Monkey other) {
      other.items.add(items.get(index));
      items.remove(index);
    }
  }
}
