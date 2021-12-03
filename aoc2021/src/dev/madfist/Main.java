package dev.madfist;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
      if (args.length < 2) {
        System.out.println("Usage java Main.java <day> <input>");
        System.exit(1);
      }
      var main = new Main();
      var input = main.readInput(args[1]);

      try {
        Class<? extends Day> dayClass = Class.forName("dev.madfist." + args[0]).asSubclass(Day.class);
        Day day = dayClass.getDeclaredConstructor().newInstance();
        System.out.println(day.solveFirst(input));
        System.out.println(day.solveSecond(input));
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
}
