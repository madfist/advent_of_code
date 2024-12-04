package dev.madfist.aoc2022;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static dev.madfist.aoc2022.Utils.collectToString;
import static dev.madfist.aoc2022.Utils.printReturn;

public class Day13 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var pairs = parseInput(input);
    return Long.toString(IntStream.range(0, pairs.size())
      .map(i -> {System.out.println(pairs.get(i)); return pairs.get(i).compare() < 0 ? i + 1 : 0;})
      .sum());
  }

  @Override
  public String solveSecond(List<String> input) {
    return null;
  }

  public List<Pair<Node>> parseInput(List<String> input) {
    List<Pair<Node>> pairs = new ArrayList<>();
    Pair<Node> pair = null;
    for (var line: input) {
      if (line.isBlank()) {
        continue;
      }
      Node root = new ListNode(null);
      Node actual = null;
      StringBuilder numberBuilder = new StringBuilder();
      for (var c: line.toCharArray()) {
        if (c == '[') {
          actual = actual != null
            ? actual.addChild()
            : root;
        } else if (c == ']') {
          assert actual != null;
          if (numberBuilder.length() != 0) {
            actual.addValue(Integer.valueOf(numberBuilder.toString()));
            numberBuilder.setLength(0);
          }
          actual = actual.back();
        } else if (c == ',') {
          assert actual != null;
          if (numberBuilder.length() != 0) {
            actual.addValue(Integer.valueOf(numberBuilder.toString()));
            numberBuilder.setLength(0);
          }
        } else if (Character.isDigit(c)) {
          numberBuilder.append(c);
        }
      }
      if (pair == null) {
        pair = new Pair<>(root);
      } else {
        pair.setRight(root);
        pairs.add(pair);
        pair = null;
      }
    }
    return pairs;
  }

  static class Pair<T extends Comparable<T>> {
    private final T left;
    private T right;

    public Pair(T left) {
      this.left = left;
    }

    public void setRight(T right) {
      this.right = right;
    }

    public int compare() {
      return printReturn(left.compareTo(right));
    }

    @Override
    public String toString() {
      return "left: " + left + "\nright: " + right;
    }

    public T getLeft() {
      return left;
    }

    public T getRight() {
      return right;
    }
  }

  static abstract class Node implements Comparable<Node> {
    protected Node sibling = null;
    private final Node parent;

    protected Node(Node parent) {
      this.parent = parent;
    }

    public abstract Node addChild();
    public abstract void addValue(Integer value);

    public Node back() {
      return parent;
    }
    public int siblingCheck(Node other) {
//      System.out.println("S " + sibling + ' ' + other.sibling);
      int siblingCompare;
      return sibling != null
        ? (siblingCompare = sibling.compareTo(other.sibling)) == 0
          ? sibling.siblingCheck(other.sibling)
          : siblingCompare
        : other.sibling != null
          ? -1
          : 0;
    }
  }

  static class ValueNode extends Node {
    private final Integer value;
    public ValueNode(Node parent, Integer value) {
      super(parent);
      this.value = value;
    }
    @Override
    public String toString() {
      return (value != null ? value.toString() : "") +
          (sibling != null ? "," : "");
    }

    @Override
    public Node addChild() {
      return this;
    }

    @Override
    public void addValue(Integer value) {
    }
    @Override
    public int compareTo(Node other) {
      System.out.println("Vcompare " + this + " vs " + other);
      if (other == null) {
        return 1;
      }
      if (other instanceof ValueNode) {
        return value.compareTo(((ValueNode) other).value);
      } else {
        var firstChild = ((ListNode) other).firstChild();
        return compareTo(firstChild);
      }
    }
  }

  static class ListNode extends Node {
    private final List<Node> children = new ArrayList<>();
    public ListNode(Node parent) {
      super(parent);
    }
    public Node firstChild() {
      return children.size() > 0 ? children.get(0) : null;
    }
    @Override
    public void addValue(Integer value) {
      var valueChild = new ValueNode(this, value);
      if (!children.isEmpty()) {
        children.get(children.size() - 1).sibling = valueChild;
      }
      children.add(valueChild);
    }
    @Override
    public Node addChild() {
      var child = new ListNode(this);
      if (!children.isEmpty()) {
        children.get(children.size() - 1).sibling = child;
      }
      children.add(child);
      return child;
    }
    @Override
    public int compareTo(Node other) {
      System.out.println("Lcompare " + this + " vs " + other);
      if (other == null) {
        return 1;
      }
      if (other instanceof ValueNode) {
        return firstChild() != null
          ? firstChild().compareTo(other)
          : -1;
      } else {
        var otherChild = ((ListNode) other).firstChild();
        if (firstChild() != null) {
          var childCompare = firstChild().compareTo(otherChild);
          return childCompare == 0
            ? firstChild().siblingCheck(otherChild)
            : childCompare;
        } else {
          return otherChild != null ? -1 : 0;
        }
      }
    }
    @Override
    public String toString() {
      return "[" + children.stream().map(Node::toString).collect(collectToString()) + (sibling != null ? "]," : ']');
    }
  }
}
