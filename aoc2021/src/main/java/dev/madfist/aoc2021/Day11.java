package dev.madfist.aoc2021;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 implements Day {
  private static final int WIDTH = 10;
  private static final int HEIGHT = 10;

  private static class Octopus{
    int charge;
    boolean flashed = false;
    Octopus(int c) {
      charge = c;
    }
    void inc() {
      ++charge;
    }
    void flash() {
      flashed = true;
    }
    void zeroFlashed() {
      if (flashed) {
        charge = 0;
        flashed = false;
      }
    }
    public String toString() {
      return flashed ? "*" : Integer.toString(charge);
    }
  }

  private static class DumboOctopusMatrix {
    List<Octopus> values;
    int flashes = 0;
    DumboOctopusMatrix(List<Integer> list) {
      values = list.stream().map(Octopus::new).collect(Collectors.toList());
    }
    void incrementAll() {
      values.forEach(Octopus::inc);
    }

    void zeroFlashed() {
      values.forEach(Octopus::zeroFlashed);
    }

    void flash() {
      for (int i = 0; i < values.size(); ++i) {
        flashes += flash(i);
      }
    }

    boolean allZero() {
      return values.stream().allMatch(c -> c.charge == 0);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("FLASHES = ").append(flashes).append('\n');
      for (int j = 0; j < HEIGHT; ++j) {
        for (int i = 0; i < WIDTH; ++i) {
          sb.append(values.get(j * WIDTH + i)).append('\t');
        }
        sb.append('\n');
      }
      return sb.toString();
    }

    private static final int W = -1;
    private static final int E = 1;
    private static final int N = -WIDTH;
    private static final int S = WIDTH;
    private static final int NW = N + W;
    private static final int SW = S + W;
    private static final int NE = N + E;
    private static final int SE = S + E;

    private void inc(int i) {
      values.get(i).inc();
    }

    private int flash(int i) {
      if (values.get(i).charge >= 10 && !values.get(i).flashed) {
        values.get(i).flash();
        int flashes = 1;
        int x = i % WIDTH;
        int y = i / WIDTH;

        if (x > 0) inc(i + W);
        if (y > 0) inc(i + N);
        if (x < WIDTH - 1) inc(i + E);
        if (y < HEIGHT - 1) inc(i + S);
        if (x > 0 && y > 0) inc(i + NW);
        if (x > 0 && y < HEIGHT - 1) inc(i + SW);
        if (x < WIDTH - 1 && y > 0) inc(i + NE);
        if (x < WIDTH - 1 && y < HEIGHT - 1) inc(i + SE);

//        System.out.println("FLASH(" + (x + 1) + "," + (y + 1) +") ~~~ " + this);

        if (x > 0) flashes += flash(i + W);
        if (y > 0) flashes += flash(i + N);
        if (x < WIDTH - 1) flashes += flash(i + E);
        if (y < HEIGHT - 1) flashes += flash(i + S);
        if (x > 0 && y > 0) flashes += flash(i + NW);
        if (x > 0 && y < HEIGHT - 1) flashes += flash(i + SW);
        if (x < WIDTH - 1 && y > 0) flashes += flash(i + NE);
        if (x < WIDTH - 1 && y < HEIGHT - 1) flashes += flash(i + SE);

        return flashes;
      }
      return 0;
    }
  }

  @Override
  public String solveFirst(List<String> input) {
    var numbers = input.parallelStream()
      .map(s -> Arrays.stream(s.split("")).map(Integer::parseInt).toArray(Integer[]::new))
      .flatMap(Arrays::stream)
      .collect(Collectors.toList());
    var matrix = new DumboOctopusMatrix(numbers);
//    System.out.println(matrix);
    for (int i = 0; i < 100; ++i) {
      matrix.incrementAll();
      matrix.flash();
      matrix.zeroFlashed();
//      System.out.println(matrix);
    }
    return Integer.toString(matrix.flashes);
  }

  @Override
  public String solveSecond(List<String> input) {
    var numbers = input.parallelStream()
      .map(s -> Arrays.stream(s.split("")).map(Integer::parseInt).toArray(Integer[]::new))
      .flatMap(Arrays::stream)
      .collect(Collectors.toList());
    var matrix = new DumboOctopusMatrix(numbers);
//    System.out.println(matrix);
    int step = 0;
    while (!matrix.allZero()) {
      matrix.incrementAll();
      matrix.flash();
      matrix.zeroFlashed();
      ++step;
//      System.out.println(matrix);
    }
    return Integer.toString(step);
  }
}
