package dev.madfist.aoc2021;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Day02 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    return input.parallelStream().collect(new CoordinateCollector());
  }

  @Override
  public String solveSecond(List<String> input) {
    return input.stream().collect(new AimCoordinateCollector());
  }
}

class Coordinate  {
  public int distance = 0;
  public int depth = 0;

  public void update(String line) {
    if (line.contains("forward")) {
      distance += Integer.parseInt(line.substring(8));
    }
    if (line.contains("down")) {
      depth += Integer.parseInt(line.substring(5));
    }
    if (line.contains("up")) {
      depth -= Integer.parseInt(line.substring(3));
    }
  }

  public Coordinate add(Coordinate c) {
    distance += c.distance;
    depth += c.depth;
    return this;
  }

  public String compute() {
    return Integer.toString(distance * depth);
  }
}

class CoordinateCollector implements Collector<String, Coordinate, String> {

  @Override
  public Supplier<Coordinate> supplier() {
    return Coordinate::new;
  }

  @Override
  public BiConsumer<Coordinate, String> accumulator() {
    return Coordinate::update;
  }

  @Override
  public BinaryOperator<Coordinate> combiner() {
    return Coordinate::add;
  }

  @Override
  public Function<Coordinate, String> finisher() {
    return Coordinate::compute;
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Set.of(Characteristics.CONCURRENT);
  }
}

class AimCoordinate extends Coordinate {
  public int aim = 0;

  @Override
  public void update(String line) {
    if (line.contains("forward")) {
      int d = Integer.parseInt(line.substring(8));
      distance += d;
      depth += d * aim;
    }
    if (line.contains("down")) {
      aim += Integer.parseInt(line.substring(5));
    }
    if (line.contains("up")) {
      aim -= Integer.parseInt(line.substring(3));
    }
  }
}

class AimCoordinateCollector implements Collector<String, AimCoordinate, String> {
  @Override
  public Supplier<AimCoordinate> supplier() {
    return AimCoordinate::new;
  }

  @Override
  public BiConsumer<AimCoordinate, String> accumulator() {
    return AimCoordinate::update;
  }

  @Override
  public BinaryOperator<AimCoordinate> combiner() {
    return null;
  }

  @Override
  public Function<AimCoordinate, String> finisher() {
    return AimCoordinate::compute;
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Set.of();
  }
}
