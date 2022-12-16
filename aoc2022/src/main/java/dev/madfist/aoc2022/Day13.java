package dev.madfist.aoc2022;

import java.util.ArrayList;
import java.util.List;

import static dev.madfist.aoc2022.Utils.collectToString;

public class Day13 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var pairs = parseInput(input);
    return Long.toString(pairs.stream().mapToInt(Pair::compare).filter(n -> n == -1).count());
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
      Node root = new Node(null);
      Node actual = root;
      StringBuilder numberBuilder = new StringBuilder();
      for (var c: line.toCharArray()) {
        if (actual == null) {
          break;
        }
        if (c == '[') {
          actual = actual.addChild();
        } else if (c == ']') {
          if (numberBuilder.length() != 0) {
            actual.setValue(Integer.valueOf(numberBuilder.toString()));
            numberBuilder.setLength(0);
          }
          actual = actual.back();
        } else if (c == ',') {
          if (numberBuilder.length() != 0) {
            actual.setValue(Integer.valueOf(numberBuilder.toString()));
            numberBuilder.setLength(0);
          }
          actual = actual.addSibling();
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
      return left.compareTo(right);
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

  static class Node implements Comparable<Node> {
    private Integer value = null;
    private Node sibling = null;
    private final List<Node> children = new ArrayList<>();
    private final Node parent;

    public Node(Node parent) {
      this.parent = parent;
    }

    public void setValue(Integer value) {
      this.value = value;
    }

    public Node addChild() {
      var child = new Node(this);
      children.add(child);
      return child;
    }

    public Node addSibling() {
      return sibling = parent.addChild();
    }

    public Node back() {
      return parent;
    }

    public Node firstChild() {
      return children.get(0);
    }

    @Override
    public int compareTo(Node other) {
      if (other == null) {
        return 1;
      }
      if (children.isEmpty()) {
        return other.children.isEmpty()
          ? compareValueOrSiblingTo(other)
          : compareValueOrSiblingToChild(other.firstChild());
      } else {
        return other.children.isEmpty()
          ? other.compareValueOrSiblingToChild(firstChild())
          : firstChild().compareValueOrSiblingTo(other.firstChild());
      }
    }

    private int compareValueOrSiblingTo(Node other) {
      if (value != null && other.value != null) {
        var c = value.compareTo(other.value);
        if (c == 0) {
          if (sibling != null) {
            return sibling.compareTo(other.sibling);
          } else {
            return other.sibling != null ? -1 : 0;
          }
        }
        return c;
      } else if (value == null) {
        return other.value == null ? 0 : -1;
      } else {
        return 1;
      }
    }

    private int compareValueOrSiblingToChild(Node other) {
      if (value != null && other.value != null) {
        var c = value.compareTo(other.value);
        if (c == 0) {
          if (sibling != null) {
            return sibling.compareTo(other.sibling);
          } else {
            return other.sibling != null ? -1 : 0;
          }
        }
        return c;
      } else if (value == null) {
        return -1;
      } else {
        return 1;
      }
    }

    @Override
    public String toString() {
      if (children.isEmpty()) {
        return (value != null ? value.toString() : "") +
          (sibling != null ? "," : "");
      } else {
        return "[" + children.stream().map(Node::toString).collect(collectToString()) + (sibling != null ? "]," : ']');
      }
    }
  }
}
