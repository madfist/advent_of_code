package dev.madfist.aoc2024;

public class Pair<A, B> {
  private final A a;
  private final B b;
  public Pair(A a, B b) {
    this.a = a;
    this.b = b;
  }
  public A a() {
    return a;
  }
  public B b() {
    return b;
  }
}
