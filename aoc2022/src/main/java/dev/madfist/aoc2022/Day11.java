package dev.madfist.aoc2022;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    return solve(input, 20, true).toString();
  }

  @Override
  public String solveSecond(List<String> input) {
    var monkeyBusiness = parseMonkeyBusiness(input);
    var inspections = monkeyBusiness.doRounds(10000);
    return Long.toString(inspections.get(0) * inspections.get(1));
  }

  List<Monkey> parseMonkeys(List<String> input, boolean managedWorryLevel) {
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
          .map(BigInteger::new).collect(Collectors.toList()));
      }
      if (line.startsWith("  Operation")) {
        var opStr = line.substring(19);
        var opMatcher = numberFunctionPattern.matcher(opStr);
        if (opMatcher.matches()) {
            final var other = new BigInteger(opMatcher.group(2));
          if (opMatcher.group(1).equals("+")) {
            monkey.assignOperation(num -> num.add(other));
          } else {
            monkey.assignOperation(num -> num.multiply(other));
          }
        } else {
          var numMatcher = quadFunctionPattern.matcher(opStr);
          if (numMatcher.matches()) {
            monkey.assignOperation(numMatcher.group(1).equals("+")
              ? num -> num.add(num)
              : num -> num.multiply(num));
          }
        }
      }
      if (line.startsWith("  Test")) {
        monkey.setDivisor(new BigInteger(line.substring(21)));
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
    return monkeys;
  }

  BigInteger solve(List<String> input, int cycles, boolean managedWorryLevel) {
    var monkeys = parseMonkeys(input, managedWorryLevel);
    for (int i = 0; i < cycles; ++i) {
//      System.out.println("s" + i + ". " + monkeys);
      for (var m : monkeys) {
        var nextMonkeys = m.turn();
//      System.out.println("m" + monkeys.indexOf(m) + ". " + m);
//      System.out.println(nextMonkeys);
        for (var nextMonkey : nextMonkeys) {
          m.throwFirstItem(monkeys.get(nextMonkey));
        }
      }
    }
    monkeys.sort(Comparator.comparing(Monkey::getInspections).reversed());
//    System.out.println(monkeys.stream().map(Monkey::getInspections).collect(Collectors.toList()));
    return monkeys.get(0).inspections.multiply(monkeys.get(1).inspections);
  }

  MonkeyBusiness parseMonkeyBusiness(List<String> input) {
    var numberFunctionPattern = Pattern.compile("old ([+*]) (\\d+)");
    var monkeys = new ArrayList<ModMonkey>();
    var monkeyBusiness = new MonkeyBusiness();
    ModMonkey monkey = null;
    for (var line: input) {
      if (line.startsWith("Monkey")) {
        monkey = new ModMonkey(Integer.parseInt(line.substring(7, line.indexOf(':'))));
      }
      if (monkey == null) {
        continue;
      }
      if (line.startsWith("  Starting")) {
        monkeyBusiness.addItems(monkey.index, Arrays.stream(line.substring(18).split(", "))
          .map(Integer::parseInt).collect(Collectors.toList()));
      }
      if (line.startsWith("  Operation")) {
        var opStr = line.substring(19);
        var opMatcher = numberFunctionPattern.matcher(opStr);
        if (opMatcher.matches()) {
          if (opMatcher.group(1).equals("+")) {
            monkeyBusiness.addAddOperation(Integer.parseInt(opMatcher.group(2)));
          } else {
            monkeyBusiness.addMultiplyOperation(Integer.parseInt(opMatcher.group(2)));
          }
        } else {
          monkeyBusiness.addSquareOperation();
        }
      }
      if (line.startsWith("  Test")) {
        monkeyBusiness.addDivisor(Integer.parseInt(line.substring(21)));
      }
      if (line.startsWith("    If true")) {
        monkey.nextMonkeyIndex1 = Integer.parseInt(line.substring(29));
      }
      if (line.startsWith("    If false")) {
        monkey.nextMonkeyIndex2 = Integer.parseInt(line.substring(30));
      }
      if (line.isBlank()) {
        monkeys.add(monkey);
      }
    }
    if (monkeys.isEmpty() || !monkey.equals(monkeys.get(monkeys.size() - 1))) {
      monkeys.add(monkey);
    }
    monkeyBusiness.init(monkeys);
    return monkeyBusiness;
  }

  static class WorryLevelMod {
    List<Integer> modLevels = new ArrayList<>();
    int monkeyIndex;
    WorryLevelMod(int idx, int level) {
      monkeyIndex = idx;
      modLevels.add(level);
    }

    void resize(int size) {
      for (int s = 1; s < size; ++s) {
        modLevels.add(modLevels.get(0));
      }
    }

    void update(Function<Integer, Integer> operation, List<Integer> divisors) {
      modLevels = IntStream.range(0, modLevels.size())
        .mapToObj(i -> operation.apply(modLevels.get(i)) % divisors.get(i))
        .collect(Collectors.toList());
    }

    int get(int idx) {
      return modLevels.get(idx);
    }

    void throwTo(int idx) {
      monkeyIndex = idx;
    }

    @Override
    public String toString() {
      return String.format("%d.%s", monkeyIndex, modLevels);
    }
  }

  static class ModMonkey {
    final int index;
    long inspections;
    int nextMonkeyIndex1;
    int nextMonkeyIndex2;

    ModMonkey(int idx) {
      index = idx;
    }

    int getNext(int level) {
      return level == 0 ? nextMonkeyIndex1 : nextMonkeyIndex2;
    }

    long getInspections() {
      return inspections;
    }

    void increment() {
      inspections += 1;
    }

    @Override
    public String toString() {
      return index + "?" + nextMonkeyIndex1 + ':' + nextMonkeyIndex2;
    }
  }

  static class MonkeyBusiness {
    final List<Integer> divisors = new ArrayList<>();
    final List<Function<Integer, Integer>> operations = new ArrayList<>();
    final List<WorryLevelMod> items = new ArrayList<>();
    List<ModMonkey> monkeys;

    void init(List <ModMonkey> monkeys) {
      for (var item: items) {
        item.resize(monkeys.size());
      }
      this.monkeys = monkeys;
    }

    List<Long> doRounds(int num) {
      for (int n = 0; n < num; ++n) {
        doRound();
      }
      return monkeys.stream()
        .sorted(Comparator.comparingLong(ModMonkey::getInspections).reversed())
        .map(ModMonkey::getInspections)
        .collect(Collectors.toList());
    }

    void doRound() {
      for (var monkey: monkeys) {
        items.stream()
          .filter(item -> item.monkeyIndex == monkey.index)
          .forEach(item -> {
            item.update(operations.get(monkey.index), divisors);
            item.throwTo(monkey.getNext(item.get(monkey.index)));
            monkey.increment();
          });
      }
    }

    void addItems(int idx, List<Integer> worryLevels) {
      items.addAll(worryLevels.stream().map(l -> new WorryLevelMod(idx, l)).collect(Collectors.toList()));
    }

    void addAddOperation(int x) {
      operations.add(num -> num + x);
    }

    void addMultiplyOperation(int x) {
      operations.add(num -> num * x);
    }

    void addSquareOperation() {
      operations.add(num -> num * num);
    }

    void addDivisor(int d) {
      divisors.add(d);
    }

    @Override
    public String toString() {
      return String.format("divs:%s\nmonkeys:%s", divisors, monkeys);
    }
  }

  static class Monkey {
    private final boolean managedWorryLevel;
    List<BigInteger> items;
    Function<BigInteger, BigInteger> operation;
    BigInteger inspections = BigInteger.ZERO;
    BigInteger divisor;
    int nextMonkeyIndex1;
    int nextMonkeyIndex2;

    public Monkey(boolean managedWorryLevel) {
      this.managedWorryLevel = managedWorryLevel;
    }

    public void addItems(List<BigInteger> items) {
      this.items = items;
    }

    public void assignOperation(Function<BigInteger, BigInteger> operation) {
      this.operation = managedWorryLevel
        ? num -> operation.apply(num).divide(new BigInteger("3"))
        : operation;
    }

    public List<Integer> turn() {
      inspections = inspections.add(BigInteger.valueOf(items.size()));
      items = items.stream().map(getOperation()).collect(Collectors.toList());
      return items.stream()
        .map(item ->
          item.mod(divisor).equals(BigInteger.ZERO) ? nextMonkeyIndex1 : nextMonkeyIndex2)
        .collect(Collectors.toList());
    }

    public void throwFirstItem(Monkey other) {
      other.items.add(items.get(0));
      items.remove(0);
    }

    public List<BigInteger> getItems() {
      return items;
    }

    public Function<BigInteger, BigInteger> getOperation() {
      return operation;
    }

    public BigInteger getInspections() {
      return inspections;
    }

    @Override
    public String toString() {
      return String.format("%s(%d)", items, inspections);
    }

    public BigInteger getDivisor() {
      return divisor;
    }

    public void setDivisor(BigInteger divisor) {
      this.divisor = divisor;
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
