package dev.madfist.aoc2024;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
      if (args.length != 1) {
        System.out.println("Usage java Main.java <day No.>");
        System.exit(1);
      }
      var main = new Main();
      var input = main.readResource("inputs/day" + args[0] + ".input");
      List<String> example = null;
      if (new File("example").exists()) {
        example = main.readInput("example");
      }

      try {
        Class<? extends Day> dayClass =
            Class.forName("dev.madfist.aoc2022.Day" + args[0]).asSubclass(Day.class);
        Day day = dayClass.getDeclaredConstructor().newInstance();

        if (example != null) {
          System.out.println("example part 1: " + day.solveFirst(example));
          System.out.println("example part 2: " + day.solveSecond(example));
        } else {
          System.out.println("part 1: " + day.solveFirst(input));
          System.out.println("part 2: " + day.solveSecond(input));
        }
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }

    private List<String> readInput(String filename) {
      var list = new ArrayList<String>();
      try {
        var file = new File(filename);
        var scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
          list.add(scanner.nextLine());
        }
      } catch (Exception e) {
        System.out.println("Cannot open " + filename);
      }
      return list;
    }

    private List<String> readResource(String filename) {
      var list = new ArrayList<String>();
      var classLoader = Main.class.getClassLoader();
      var inputStream = classLoader.getResourceAsStream(filename);
      if (inputStream == null) {
        throw new IllegalArgumentException("Cannot open " + filename);
      }
      var reader = new BufferedReader(new InputStreamReader(inputStream));
      try {
        while (reader.ready()) {
          list.add(reader.readLine());
        }
      } catch (IOException e) {
        System.out.println("Error reading " + filename);
      }
      return list;
    }
}
