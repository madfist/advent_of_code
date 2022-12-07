package dev.madfist.aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {
  @Test
  void testRemovePairs() {
    String s = "[({(<(())[]>[[{[]{<()<>>";
    Day10 test = new Day10();
    var list = test.toList(s);
    var l1 = test.removePairs(list);
    assertEquals("[({(<()>[[{{<>", test.toString(l1));
    var l2 = test.removePairs(l1);
    assertEquals("[({(<>[[{{", test.toString(l2));
    var l3 = test.removePairs(l2);
    assertEquals("[({([[{{", test.toString(l3));
  }

  @Test
  void TestSolveSecond() {
    var input = List.of(
        "[({(<(())[]>[[{[]{<()<>>",
        "[(()[<>])]({[<{<<[]>>(",
        "{([(<{}[<>[]}>{[]{[(<()>",
        "(((({<>}<{<{<>}{[]{[]{}",
        "[[<[([]))<([[{}[[()]]]",
        "[{[{({}]{}}([{[{{{}}([]",
        "{<[[]]>}<{[{[{[]{()[[[]",
        "[<(<(<(<{}))><([]([]()",
        "<{([([[(<>()){}]>(<<{{",
        "<{([{{}}[<[[[<>{}]]]>[]]");
    Day10 test = new Day10();
    assertEquals("288957", test.solveSecond(input));
  }
}
