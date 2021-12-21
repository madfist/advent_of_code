package dev.madfist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day06 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var timers = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
    for (int i = 0; i < 80; ++i) {
      AtomicInteger newBorn = new AtomicInteger(0);
      timers = timers.parallelStream().map(timer -> {
        if (timer == 0) {
          newBorn.incrementAndGet();
          return 6;
        }
        return timer - 1;
      }).collect(Collectors.toList());
      var newBorns = new Integer[newBorn.get()];
      Arrays.fill(newBorns, 8);
      Collections.addAll(timers, newBorns);
    }
    return Integer.toString(timers.size());
  }

  @Override
  public String solveSecond(List<String> input) {
    var timers = Arrays.stream(input.get(0).split(","))
      .map(Integer::parseInt)
      .collect(Collectors.groupingBy(t -> t, Collectors.counting()));
    for (int i = 0; i < 256; ++i) {
      Long zeros = timers.get(0) != null ? timers.get(0) : 0;
      for (int j = 0; j < 8; ++j) {
        timers.put(j, timers.get(j + 1) != null ? timers.get(j + 1) : 0);
      }
      timers.put(6, timers.get(6) + zeros);
      timers.put(8, zeros);
    }
    return Long.toString(timers.values().stream().mapToLong(Long::longValue).sum());
  }
}
