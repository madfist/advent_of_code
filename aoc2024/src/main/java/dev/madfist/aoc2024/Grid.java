package dev.madfist.aoc2024;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Grid<T> {
  private final int width;
  private final int height;
  private final List<T> data;

  public Grid(List<String> input, BiFunction<Coordinate, Character, T> inputMapper) {
    width = input.get(0).length();
    height = input.size();
    data = new ArrayList<>(width * height);
    int j = 0;
    for (var line: input) {
      int i = 0;
      for (var c: line.toCharArray()) {
        data.add(inputMapper.apply(new Coordinate(i++, j), c));
      }
      ++j;
    }
  }

  public T get(int x, int y) {
    return data.get(y * width + x);
  }

  public T get(Coordinate c) {
    return data.get(c.y() * width + c.x());
  }
}
