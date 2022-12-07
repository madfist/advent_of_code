package dev.madfist.aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day04 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var draw = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).toArray(Integer[]::new);
    var boards = buildBoards(input);
    Board winner = null;
    int winnerNumber = -1;
    for (int d : draw) {
      boolean win = false;
      for (Board b : boards) {
        b.mark(d);
//        System.out.println("(" + d + ")\n" + b);
        if (b.check()) {
          winner = b;
          winnerNumber = d;
          win = true;
          break;
        }
      }
      if (win) {
//        System.out.println("WIN " + winnerNumber);
//        System.out.println(winner);
        break;
      }
    }

    if (winner != null && winnerNumber > -1) {
      return Integer.toString(winnerNumber * winner.sumUnchecked());
    }
    return null;
  }

  @Override
  public String solveSecond(List<String> input) {
    var draw = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).toArray(Integer[]::new);
    var boards = buildBoards(input);
    AtomicReference<Board> winner = new AtomicReference<>();
    AtomicInteger winnerNumber = new AtomicInteger(-1);
    for (int d : draw) {
      boards.stream().filter(b -> !b.won).forEach(b -> {
        b.mark(d);
        if (b.check()) {
//          System.out.println(d + "\n" + b);
          winner.set(b);
          winnerNumber.set(d);
        }
      });
    }

    if (winner.get() != null && winnerNumber.get() > -1) {
//      System.out.println("WIN " + winnerNumber);
//      System.out.println(winner);
      return Integer.toString(winnerNumber.get() * winner.get().sumUnchecked());
    }
    return null;
  }

  private List<Board> buildBoards(List<String> input) {
    var filteredList = input.stream().filter(Predicate.not(String::isBlank)).collect(Collectors.toList());
    var boards = new ArrayList<Board>();
    StringBuilder builder = new StringBuilder();
    for (int i = 1; i < filteredList.size(); ++i) {
      builder.append(filteredList.get(i)).append(" ");
      if (i % 5 == 0) {
        boards.add(new Board(builder.toString().trim()));
        builder.setLength(0);
      }
    }
    return boards;
  }
}

class Board {
  private static class BoardField {
    public final int number;
    public boolean check;

    public BoardField(int n, boolean c) {
      number = n;
      check = c;
    }
  }
  private final BoardField[] fields;
  public boolean won = false;

  Board(String input) {
    fields = Arrays.stream(input.split("\\s+"))
      .map(s -> new BoardField(Integer.parseInt(s), false))
      .toArray(BoardField[]::new);
  }

  void mark(int num) {
    for (BoardField field : fields) {
      if (field.number == num) {
        field.check = true;
        break;
      }
    }
  }

  boolean check() {
    boolean horizontal = true;
    for (int i = 0; i < fields.length; ++i) {
      horizontal &= fields[i].check;
      if (i % 5 == 4) {
        if (horizontal) {
          won = true;
          return true;
        } else {
          horizontal = true;
        }
      }
    }
    boolean vertical = true;
    for (int i = 0, j = 0; i < fields.length && j < 5; i += 5) {
      vertical &= fields[i].check;
//      System.out.print(i + "_");
      if (i == 20 + j) {
//        System.out.println("#" + j);
        if (vertical) {
          won = true;
          return true;
        } else {
          vertical = true;
          i = ++j - 5;
        }
      }
    }
    return false;
  }

  int sumUnchecked() {
    return Arrays.stream(fields).filter(f -> !f.check).mapToInt(f -> f.number).sum();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < fields.length; ++i) {
      builder.append(fields[i].check ? "[" + fields[i].number + "]" : fields[i].number).append(" ");
      if (i % 5 == 4) {
        builder.append('\n');
      }
    }
    return builder.toString();
  }
}
