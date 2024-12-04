package dev.madfist.aoc2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class Day13Test {
  List<String> example = Arrays.asList(
    "[1,1,3,1,1]",
    "[1,1,5,1,1]",
    "",
    "[[1],[2,3,4]]",
    "[[1],4]",
    "",
    "[9]",
    "[[8,7,6]]",
    "",
    "[[4,4],4,4]",
    "[[4,4],4,4,4]",
    "",
    "[7,7,7,7]",
    "[7,7,7]",
    "",
    "[]",
    "[3]",
    "",
    "[[[]]]",
    "[[]]",
    "",
    "[1,[2,[3,[4,[5,6,7]]]],8,9]",
    "[1,[2,[3,[4,[5,6,0]]]],8,9]");

  @Test
  public void nodeTest() {
    Day13.Node root = new Day13.ListNode(null);
    Day13.Node actual = root;
    actual = actual.addChild();
    actual.addValue(1);
    actual.addValue(2);
    actual = actual.back();
    actual.addValue(3);
    actual.addValue(4);
    assertSame(root, actual);
    assertEquals("[[1,2],3,4]", root.toString());
  }

  @Test
  public void compareTest() {
    Day13.Node node1 = new Day13.ListNode(null);
    Day13.Node node2 = new Day13.ListNode(null);
    assertEquals(0, node1.compareTo(node2));
    node2.addChild();
    assertEquals(-1, node1.compareTo(node2));
    node1.addChild();
    assertEquals(0, node1.compareTo(node2));
  }

  @Test
  public void parserTest() {
    var day = new Day13();
    var input = List.of("[[4,4],4,4]", "[[4,4],4,4,4]");
    var pairs = day.parseInput(input);
    System.out.println(pairs);
    assertEquals("[[4,4],4,4]", pairs.get(0).getLeft().toString());
    assertEquals("[[4,4],4,4,4]", pairs.get(0).getRight().toString());
    assertEquals(-1, pairs.get(0).compare());
  }
  @Test
  public void example1() {
    var day = new Day13();
    assertEquals("13", day.solveFirst(example));
  }

  @Test
  public void example2() {
    var day = new Day13();
//    assertEquals("29", day.solveSecond(example));
  }
}
