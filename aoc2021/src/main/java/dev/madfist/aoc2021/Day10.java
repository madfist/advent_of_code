package dev.madfist.aoc2021;

import java.util.*;
import java.util.stream.Collectors;

public class Day10 implements Day {
  private static String OPENERS = "([{<";
  private static String CLOSERS = ")]}>";

  LinkedList<Character> toList(String line) {
    var charList = new LinkedList<Character>();
    for (var c : line.toCharArray()) {
      charList.add(c);
    }
    return charList;
  }

  String toString(List<Character> list) {
    return list.stream().map(String::valueOf).collect(Collectors.joining());
  }

  LinkedList<Character> removePairs(LinkedList<Character> charList) {
    var iter = charList.listIterator();
    while (iter.hasNext()) {
      var c1 = iter.next();
      if (!iter.hasNext()) {
        break;
      }
      var c2 = iter.next();
      if (OPENERS.indexOf(c1) >= 0 && CLOSERS.indexOf(c2) >= 0 && OPENERS.indexOf(c1) == CLOSERS.indexOf(c2)) {
        iter.remove();
        iter.previous();
        iter.remove();
      } else {
        iter.previous();
      }
    }
    return charList;
  }

  @Override
  public String solveFirst(List<String> input) {
    var score = input.stream()
      .map(this::toList)
      .mapToInt(list -> {
        int origSize = 0;
        do {
          origSize = list.size();
          list = removePairs(list);
        } while (list.size() < origSize);
        var invalidChar = list.stream().filter(c -> CLOSERS.indexOf(c) >= 0).findFirst();
        return invalidChar.map(character -> {
          switch (character) {
            case ')': return 3;
            case ']': return 57;
            case '}': return 1197;
            case '>': return 25137;
            default: return 0;
          }
        }).orElse(0);
      }).sum();
    return Integer.toString(score);
  }

  @Override
  public String solveSecond(List<String> input) {
    var scoreArray = input.stream()
      .map(this::toList)
      .map(list -> {
        int origSize = 0;
        do {
          origSize = list.size();
          list = removePairs(list);
        } while (list.size() < origSize);
        return list;
      })
      .filter(list -> list.stream().allMatch(c -> CLOSERS.indexOf(c) == -1))
      .mapToLong(list -> {
        Collections.reverse(list);
        long score = 0;
        for (var c : list) {
          int charScore;
          switch (c) {
            case '(': charScore = 1; break;
            case '[': charScore = 2; break;
            case '{': charScore = 3; break;
            case '<': charScore = 4; break;
            default: charScore = 0; break;
          };
          score = score * 5 + charScore;
        }
        return score;
      }).sorted().toArray();
    return Long.toString(scoreArray[scoreArray.length / 2]);
  }
}
