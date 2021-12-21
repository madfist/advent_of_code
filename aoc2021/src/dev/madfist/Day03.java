package dev.madfist;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Day03 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    int size = input.get(0).length();
    return input.parallelStream().collect(new PowerConsumptionDiagnosticCollector(size));
  }

  @Override
  public String solveSecond(List<String> input) {
    int size = input.get(0).length();
    List<String> oxygenInput = new ArrayList<>(input);
    List<String> co2Input = new ArrayList<>(input);
    for (int i = 0; i < size; ++i) {
      int finalI = i;
      if (oxygenInput.size() > 1) {
        var rating = oxygenInput.parallelStream().collect(new LifeSupportDiagnosticCollector(i, LifeSupportRating.O2));
        oxygenInput = oxygenInput.parallelStream().filter(line -> line.charAt(finalI) == rating).toList();
      }
      if (co2Input.size() > 1) {
        var rating = co2Input.parallelStream().collect(new LifeSupportDiagnosticCollector(i, LifeSupportRating.CO2));
        co2Input = co2Input.parallelStream().filter(line -> line.charAt(finalI) == rating).toList();
      }
    }
    int oxygen = Integer.parseInt(oxygenInput.get(0), 2);
    int co2 = Integer.parseInt(co2Input.get(0), 2);
    return Integer.toString(oxygen * co2);
  }
}

class PowerConsumptionDiagnostic {
  private int[] ones;
  private int[] zeros;
  private int size;

  public PowerConsumptionDiagnostic(int size) {
    this.size = size;
    ones = new int[size];
    zeros = new int[size];
  }

  public void update(String line) {
    for (int i = 0; i < size; ++i) {
      if (line.charAt(i) == '1') {
        ones[i]++;
      } else {
        zeros[i]++;
      }
    }
  }

  public PowerConsumptionDiagnostic add(PowerConsumptionDiagnostic d) {
    for (int i = 0; i < size; ++i) {
      ones[i] += d.ones[i];
      zeros[i] += d.zeros[i];
    }
    return this;
  }

  public String compute() {
    char[] gammaStr = new char[size];
    char[] epsilonStr = new char[size];

    for (int i = 0; i < size; ++i) {
      gammaStr[i] = ones[i] >= zeros[i] ? '1' : '0';
      epsilonStr[i] = ones[i] >= zeros[i] ? '0' : '1';
    }

    int gamma = Integer.parseInt(new String(gammaStr), 2);
    int epsilon = Integer.parseInt(new String(epsilonStr), 2);
    return Integer.toString(gamma * epsilon);
  }
}

class PowerConsumptionDiagnosticCollector implements Collector<String, PowerConsumptionDiagnostic, String> {
  private int size;

  PowerConsumptionDiagnosticCollector(int size) {
    this.size = size;
  }

  @Override
  public Supplier<PowerConsumptionDiagnostic> supplier() {
    return () -> new PowerConsumptionDiagnostic(size);
  }

  @Override
  public BiConsumer<PowerConsumptionDiagnostic, String> accumulator() {
    return PowerConsumptionDiagnostic::update;
  }

  @Override
  public BinaryOperator<PowerConsumptionDiagnostic> combiner() {
    return PowerConsumptionDiagnostic::add;
  }

  @Override
  public Function<PowerConsumptionDiagnostic, String> finisher() {
    return PowerConsumptionDiagnostic::compute;
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Set.of(Characteristics.CONCURRENT);
  }
}

enum LifeSupportRating { O2, CO2 }

class LifeSupportDiagnostic {
  private final int position;
  private final LifeSupportRating rating;
  private int ones;
  private int zeros;

  public LifeSupportDiagnostic(int pos, LifeSupportRating r) {
    position = pos;
    rating = r;
  }

  public void update(String line) {
    if (line.charAt(position) == '1') {
      ones++;
    } else {
      zeros++;
    }
  }

  public LifeSupportDiagnostic add(LifeSupportDiagnostic d) {
    ones += d.ones;
    zeros += d.zeros;
    return this;
  }

  public Character compute() {
    return rating == LifeSupportRating.O2 ? (ones >= zeros ? '1' : '0') : (ones >= zeros ? '0': '1');
  }
}

class LifeSupportDiagnosticCollector implements Collector<String, LifeSupportDiagnostic, Character> {
  private final int position;
  private final LifeSupportRating rating;

  public LifeSupportDiagnosticCollector(int pos, LifeSupportRating r) {
    position = pos;
    rating = r;
  }

  @Override
  public Supplier<LifeSupportDiagnostic> supplier() {
    return () -> new LifeSupportDiagnostic(position, rating);
  }

  @Override
  public BiConsumer<LifeSupportDiagnostic, String> accumulator() {
    return LifeSupportDiagnostic::update;
  }

  @Override
  public BinaryOperator<LifeSupportDiagnostic> combiner() {
    return LifeSupportDiagnostic::add;
  }

  @Override
  public Function<LifeSupportDiagnostic, Character> finisher() {
    return LifeSupportDiagnostic::compute;
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Set.of(Characteristics.CONCURRENT);
  }
}
