package dev.madfist.aoc2022;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day09 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var head = new Point();
    var tail = new Point();
    var trail = new HashSet<Point>();
    trail.add(new Point(tail));
    for (var instruction: input) {
      var direction = instruction.charAt(0);
      var steps = Integer.parseInt(instruction.substring(2));
      for (int s = 0; s < steps; ++s) {
        head.step(direction);
        tail.follow(head);
        trail.add(new Point(tail));
//        System.out.printf("H%s T%s\n", head, tail);
      }
    }
//    System.out.println(trail);
    return Integer.toString(trail.size());
  }

  @Override
  public String solveSecond(List<String> input) {
    var snake = Stream.generate(Point::new).limit(10).collect(Collectors.toList());
    var head = snake.get(0);
    var tail = snake.get(9);
    var trail = new HashSet<Point>();
    trail.add(new Point(tail));

    for (var instruction: input) {
      var direction = instruction.charAt(0);
      var steps = Integer.parseInt(instruction.substring(2));
      for (int s = 0; s < steps; ++s) {
        head.step(direction);
        for (int i = 1; i < 10; ++i) {
          snake.get(i).follow(snake.get(i - 1));
        }
        trail.add(new Point(tail));
//        System.out.printf("H%s T%s\n", head, tail);
      }
    }
//    System.out.println(trail);
    return Integer.toString(trail.size());
  }

  private static class Point {
    int x;
    int y;

    public Point() {
      x = 0;
      y = 0;
    }

    public Point(Point point) {
      this.x = point.x;
      this.y = point.y;
    }

    public void step(char direction) {
      switch (direction) {
        case 'U': up(); break;
        case 'D': down(); break;
        case 'L': left(); break;
        case 'R': right(); break;
      }
    }

    public void follow(Point point) {
      if (Math.abs(x - point.x) <= 1 && Math.abs(y - point.y) <= 1) {
        return;
      }
      if (x > point.x) {
        left();
      } else if (x < point.x) {
        right();
      }
      if (y > point.y) {
        down();
      } else if (y < point.y) {
        up();
      }
    }

    public String toString() {
      return "(" + x + ',' + y + ')';
    }

    private void left() {
      x -= 1;
    }

    private void right() {
      x += 1;
    }

    private void up() {
      y += 1;
    }

    private void down() {
      y -= 1;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Point point = (Point) o;
      return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }
}
