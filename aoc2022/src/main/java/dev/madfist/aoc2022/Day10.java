package dev.madfist.aoc2022;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var history = buildHistory(input);
    int sum = 0;
    for (int i = 19; i < 220; i += 40) {
      sum += history.get(i) * (i + 1);
    }
    return Integer.toString(sum);
  }

  @Override
  public String solveSecond(List<String> input) {
    var history = buildHistory(input);
    var screen = IntStream.range(0, 240)
      .mapToObj(b -> false)
      .collect(Collectors.toList());
    for (int i = 0; i < history.size(); ++i) {
      var diff = Math.abs(i % 40 - history.get(i));
      //System.out.println("" + i + ' ' + history.get(i) + ' ' + diff);
      if (diff >= -1 && diff <= 1) {
        screen.set(i, true);
      }
    }
    //System.out.println(printScreen(screen));
    return printScreen(screen);
  }

  private List<Integer> buildHistory(List<String> input) {
    var history = new ArrayList<Integer>();
    var register = 1;
    for (var line: input) {
      if (line.equals("noop")) {
        history.add(register);
      }
      if (line.startsWith("addx")) {
        history.add(register);
        history.add(register);
        register += Integer.parseInt(line.substring(5));
      }
    }
    return history;
  }

  private String printScreen(List<Boolean> screen) {
    var builder = new StringBuilder();
    builder.append('\n');
    for (int i = 0; i < 240; ++i) {
      builder.append(screen.get(i) ? '#' : '.');
      if ((i + 1) % 40 == 0) {
        builder.append('\n');
      }
    }
    return builder.toString();
  }
}
