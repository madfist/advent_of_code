package dev.madfist.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day11 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    return Long.toString(solve(input, 20, true));
  }

  @Override
  public String solveSecond(List<String> input) {
    return Long.toString(solve(input, 10000, false));
  }

  List<Monkey> parseInput(List<String> input, boolean managedWorryLevel) {
    var numberFunctionPattern = Pattern.compile("old ([+*]) (\\d+)");
    var quadFunctionPattern = Pattern.compile("old ([+*]) old");
    var monkeys = new ArrayList<Monkey>();
    Monkey monkey = null;
    for (var line: input) {
      if (line.startsWith("Monkey")) {
        monkey = new Monkey(managedWorryLevel);
      }
      if (monkey == null) {
        continue;
      }
      if (line.startsWith("  Starting")) {
        monkey.addItems(Arrays.stream(line.substring(18).split(", "))
          .map(Integer::parseInt).collect(Collectors.toList()));
      }
      if (line.startsWith("  Operation")) {
        var opStr = line.substring(19);
        var opMatcher = numberFunctionPattern.matcher(opStr);
        if (opMatcher.matches()) {
            final var other = Integer.parseInt(opMatcher.group(2));
          if (opMatcher.group(1).equals("+")) {
            monkey.assignOperation((num, div) -> div != 0 ? (num + other) % div : num + other);
          } else {
            monkey.assignOperation((num, div) -> div != 0 ? (num * other) % div : num * other);
          }
        } else {
          var numMatcher = quadFunctionPattern.matcher(opStr);
          if (numMatcher.matches()) {
            monkey.assignOperation(numMatcher.group(1).equals("+")
              ? (num, div) -> div != 0 ? (num + num) % div : num + num
              : (num, div) -> div != 0 ? (num * num) % div : num * num);
          }
        }
      }
      if (line.startsWith("  Test")) {
        monkey.setDivider(Integer.parseInt(line.substring(21)));
      }
      if (line.startsWith("    If true")) {
        monkey.setNextMonkeyIndex1(Integer.parseInt(line.substring(29)));
      }
      if (line.startsWith("    If false")) {
        monkey.setNextMonkeyIndex2(Integer.parseInt(line.substring(30)));
      }
      if (line.isBlank()) {
        monkeys.add(monkey);
      }
    }
    if (monkeys.isEmpty() || !monkey.equals(monkeys.get(monkeys.size() - 1))) {
      monkeys.add(monkey);
    }
    if (!managedWorryLevel) {
      monkeys.forEach(Monkey::modItems);
    }
    return monkeys;
  }

  long solve(List<String> input, int cycles, boolean managedWorryLevel) {
    var monkeys = parseInput(input, managedWorryLevel);
    for (int i = 0; i < cycles; ++i) {
      System.out.println(i + ". " + monkeys);
      for (var m : monkeys) {
        var nextMonkeys = m.turn();
        System.out.println(nextMonkeys);
        for (var nextMonkey : nextMonkeys) {
          m.throwFirstItem(monkeys.get(nextMonkey));
        }
      }
    }
    monkeys.sort(Comparator.comparing(Monkey::getInspections).reversed());
    System.out.println(monkeys.stream().map(Monkey::getInspections).collect(Collectors.toList()));
    return monkeys.get(0).inspections * monkeys.get(1).inspections;
  }

  static class Monkey {
    private final boolean managedWorryLevel;
    List<Integer> items;
    BiFunction<Integer, Integer, Integer> operation;
    long inspections = 0;
    int divider = 0;
    int nextMonkeyIndex1;
    int nextMonkeyIndex2;

    public Monkey(boolean managedWorryLevel) {
      this.managedWorryLevel = managedWorryLevel;
    }

    public void addItems(List<Integer> items) {
      this.items = items;
    }

    public void modItems() {
      this.items = this.items.stream().map(item -> item % divider).collect(Collectors.toList());
    }

    public void assignOperation(BiFunction<Integer, Integer, Integer> operation) {
      this.operation = operation;
    }

    public List<Integer> turn() {
      var itemThrows = new ArrayList<Integer>(items.size());
      for (int i = 0; i < items.size(); ++i) {
        items.set(i, getOperation().apply(items.get(i)));
        itemThrows.add(items.get(0) % divider == 0 ? nextMonkeyIndex1 : nextMonkeyIndex2);
        ++inspections;
      }
      return itemThrows;
    }

    public void throwFirstItem(Monkey other) {
      other.items.add(items.get(0));
      items.remove(0);
    }

    public List<Integer> getItems() {
      return items;
    }

    public Function<Integer, Integer> getOperation() {
      if (managedWorryLevel) {
        return num -> Math.floorDiv(operation.apply(num, 0), 3);
      }
      return num -> operation.apply(num, divider);
    }

    public long getInspections() {
      return inspections;
    }

    @Override
    public String toString() {
      return String.format("%s(%d)", items, inspections);
    }

    public int getDivider() {
      return divider;
    }

    public void setDivider(int divider) {
      this.divider = divider;
    }

    public int getNextMonkeyIndex1() {
      return nextMonkeyIndex1;
    }

    public void setNextMonkeyIndex1(int nextMonkeyIndex1) {
      this.nextMonkeyIndex1 = nextMonkeyIndex1;
    }

    public int getNextMonkeyIndex2() {
      return nextMonkeyIndex2;
    }

    public void setNextMonkeyIndex2(int nextMonkeyIndex2) {
      this.nextMonkeyIndex2 = nextMonkeyIndex2;
    }
  }
}
