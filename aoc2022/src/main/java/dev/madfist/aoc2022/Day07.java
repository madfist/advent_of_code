package dev.madfist.aoc2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Day07 implements Day {
  @Override
  public String solveFirst(List<String> input) {
    var root = buildFileSystem(input);
    var smallDirsSize = root.getSizeChart().stream()
      .filter(size -> size <= 100000)
      .mapToLong(Long::longValue)
      .sum();
    return Long.toString(smallDirsSize);
  }

  @Override
  public String solveSecond(List<String> input) {
    var root = buildFileSystem(input);
    var spaceNeeded = 30000000 - (70000000 - root.getSize());
    var smallestDeletable = root.getSizeChart().stream()
      .filter(size -> size >= spaceNeeded)
      .min(Long::compareTo)
      .orElse(-1L);
    return Long.toString(smallestDeletable);
  }

  private Node buildFileSystem(List<String> input) {
    var root = new Node();
    var pwd = root;
    for (var line: input) {
      if (line.startsWith("$ cd")) {
        var dir = line.substring(5);
        if (dir.equals("/")) {
          pwd = root;
        } else {
          pwd = pwd.cd(dir);
        }
      } else if (!line.startsWith("$ ls")) {
        pwd.addNode(new Node(line, pwd));
      }
    }
    return root;
  }

  private static class Node {
    enum NodeType {
      FILE, DIR
    };
    private String name;
    private NodeType type;
    private long size;
    private Map<String, Node> content;
    private final Node parent;

    private static final Pattern filePattern = Pattern.compile("(\\d+) (.*)");
    private static final Pattern dirPattern = Pattern.compile("dir (\\S+)");

    public Node() {
      this.name = "/";
      this.type = NodeType.DIR;
      this.parent = null;
      this.content = new HashMap<>();
    }

    public Node(String line, Node parent) {
      var fileMatcher = filePattern.matcher(line);
      var dirMatcher = dirPattern.matcher(line);
      if (fileMatcher.matches()) {
        this.name = fileMatcher.group(2);
        this.size = Long.parseLong(fileMatcher.group(1));
        this.type = NodeType.FILE;
      }
      if (dirMatcher.matches()) {
        this.name = dirMatcher.group(1);
        this.size = 0;
        this.type = NodeType.DIR;
      }
      this.parent = parent;
      this.content = new HashMap<>();
    }

    public Node cd(String name) {
      if (name.equals("..")) {
        return parent;
      }
      return content.get(name);
    }

    public void addNode(Node node) {
      this.content.put(node.name, node);
    }

    public long getSize() {
      if (type == NodeType.DIR) {
        return content.values().stream().mapToLong(Node::getSize).sum();
      }
      return size;
    }

    public List<Long> getSizeChart() {
      return getSizeChart(new ArrayList<>());
    }

    public String toString() {
      return toString(1);
    }

    private List<Long> getSizeChart(List<Long> chart) {
      if (type == NodeType.DIR) {
        chart.add(getSize());
        content.values().forEach(node -> node.getSizeChart(chart));
      }
      return chart;
    }

    private String toString(int level) {
      var spaces = " ".repeat(level * 2);
      return (type == NodeType.DIR ? '[' + name + ']' : name)
        + '(' + getSize() + ")\n"
        + content.values().stream().map(node -> spaces + node.toString(level + 1)).collect(Utils.collectToString());
    }
  }
}
