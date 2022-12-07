package dev.madfist.aoc2022;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Day05 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var crane = new Crane(input);
    crane.moveOneByOne();
    return crane.getTopCrates();
  }

  @Override
  public String solveSecond(List<String> input) {
    var crane = new Crane(input);
    crane.moveStacks();
    return crane.getTopCrates();
  }

  private static class Move {
    public int from;
    public int to;
    public int amount;
    private static final Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    public Move(String line) {
      var numbers = pattern.matcher(line);
      if (numbers.find()) {
        from = Integer.parseInt(numbers.group(2)) - 1;
        to = Integer.parseInt(numbers.group(3)) - 1;
        amount = Integer.parseInt(numbers.group(1));
      }
    }
  }

  private static class Crane {
    private List<LinkedList<Character>> stacks = new ArrayList<>();
    private List<Move> moves = new ArrayList<>();

    public Crane(List<String> input) {
      for (var line : input) {
        if (!line.startsWith(" 1") && !line.isBlank() && !line.startsWith("move")) {
          var column = line.length() / 4 + 1;
          while (stacks.size() < column) {
            stacks.add(new LinkedList<>());
          }
          for (int i = 0; i < column; ++i) {
            var next = line.charAt(i * 4 + 1);
            if (next != ' ') {
              stacks.get(i).addFirst(next);
            }
          }
        } else if (!line.isBlank() && line.startsWith("move")) {
          moves.add(new Move(line));
        }
      }
    }

    public void moveOneByOne() {
      for (var move : moves) {
        for (int i = 0; i < move.amount; ++i) {
          var crate = stacks.get(move.from).pollLast();
          stacks.get(move.to).addLast(crate);
        }
      }
    }

    public void moveStacks() {
      for (var move : moves) {
        var fromStack = stacks.get(move.from);
        var moveStack = new LinkedList<Character>();
        for (int i = 0; i < move.amount; ++i) {
          moveStack.addFirst(fromStack.pollLast());
        }
        stacks.get(move.to).addAll(moveStack);
      }
    }

    public String getTopCrates() {
      return stacks.stream()
        .map(LinkedList::peekLast)
        .collect(Collector.of(
          StringBuilder::new,
          StringBuilder::append,
          StringBuilder::append,
          StringBuilder::toString));
    }
  }
}
