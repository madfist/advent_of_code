package dev.madfist.aoc2022;

import java.util.List;

public class Day02 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var points = input.stream()
      .mapToInt(line -> {
        var a = line.charAt(0);
        var b = line.charAt(2);
        switch (a) {
          case 'A':
            switch (b) {
              case 'X': return 4;
              case 'Y': return 8;
              case 'Z': return 3;
            }
          case 'B':
            switch (b) {
              case 'X': return 1;
              case 'Y': return 5;
              case 'Z': return 9;
            }
          case 'C':
            switch (b) {
              case 'X': return 7;
              case 'Y': return 2;
              case 'Z': return 6;
            }
        }
        return -1;
      })
      .sum();
    return Integer.toString(points);
  }

  @Override
  public String solveSecond(List<String> input) {
    var points = input.stream()
      .mapToInt(line -> {
        var a = line.charAt(0);
        var b = line.charAt(2);
        switch (a) {
          case 'A':
            switch (b) {
              case 'X': return 3;
              case 'Y': return 4;
              case 'Z': return 8;
            }
          case 'B':
            switch (b) {
              case 'X': return 1;
              case 'Y': return 5;
              case 'Z': return 9;
            }
          case 'C':
            switch (b) {
              case 'X': return 2;
              case 'Y': return 6;
              case 'Z': return 7;
            }
        }
        return -1;
      })
      .sum();
    return Integer.toString(points);
  }
}
