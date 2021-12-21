package dev.madfist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Day12 implements Day {
  private static class Graph {
    Map<String, List<String>> adj;
    Graph(List<String> input) {
      adj = new HashMap<>();
      var pattern = Pattern.compile("(\\S+)-(\\S+)");
      input.forEach(line -> {
        var matcher = pattern.matcher(line);
        if (matcher.find()) {
          var v1 = matcher.group(1);
          var v2 = matcher.group(2);
          if (!adj.containsKey(v1)) {
            List<String> vertices = new ArrayList<>();
            vertices.add(v2);
            adj.put(v1, vertices);
          } else {
            adj.get(v1).add(v2);
          }
          if (!adj.containsKey(v2)) {
            List<String> vertices = new ArrayList<>();
            vertices.add(v1);
            adj.put(v2, vertices);
          } else {
            adj.get(v2).add(v1);
          }
        }
      });
    }
    int getDistinctRoutes(List<String> route, boolean smallTwice) {
//      System.out.println("STEP " + route);
      if (route == null) {
        route = new ArrayList<>();
        route.add("start");
      }
      if (route.get(route.size() - 1).equals("end")) {
        return 1;
      }
      int count = 0;
      for (var dest : adj.get(route.get(route.size() - 1))) {
        if (dest.equals("start")) {
          continue;
        }
        if (dest.toLowerCase().equals(dest) && route.contains(dest)) {
          if (!smallTwice) {
            var s = new ArrayList<>(route);
            s.add(dest);
            count += getDistinctRoutes(s, true);
          }
          continue;
        }
        var r = new ArrayList<>(route);
        r.add(dest);
        count += getDistinctRoutes(r, smallTwice);
      }
      return count;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (var vertex : adj.entrySet()) {
        sb.append(vertex.getKey()).append(' ');
        for (var v : vertex.getValue()) {
          sb.append("->").append(v);
        }
        sb.append('\n');
      }
      return sb.toString();
    }
  }
  @Override
  public String solveFirst(List<String> input) {
    var graph = new Graph(input);
//    System.out.println(graph);
    return Integer.toString(graph.getDistinctRoutes(null, true));
  }

  @Override
  public String solveSecond(List<String> input) {
    var graph = new Graph(input);
    return Integer.toString(graph.getDistinctRoutes(null, false));
  }
}
