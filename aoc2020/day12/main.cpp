#include <fstream>
#include <iostream>
#include <vector>

#include "../include/utils.h"

const char directions[] = {'N', 'E', 'S', 'W'};

int task1(const std::vector<std::string>& lines) {
  std::pair<int, int> coord(0, 0);
  int dir = 1;
  for (auto line = lines.begin(); line != lines.end(); ++line) {
    char cmd = line->at(0);
    int angle_or_distance = std::atoi(line->substr(1).c_str());
    switch (cmd) {
      case 'L':
        dir -= angle_or_distance/90;
        dir = (dir < 0) ? dir + 4 : dir % 4;
        angle_or_distance = 0;
        cmd = directions[dir];
        break;
      case 'R':
        dir += angle_or_distance/90;
        dir = (dir < 0) ? dir + 4 : dir % 4;
        angle_or_distance = 0;
        cmd = directions[dir];
        break;
      case 'F':
        cmd = directions[dir];
        break;
    }
    switch (cmd) {
      case 'N':
        coord.second += angle_or_distance;
        break;
      case 'S':
        coord.second -= angle_or_distance;
        break;
      case 'E':
        coord.first += angle_or_distance;
        break;
      case 'W':
        coord.first -= angle_or_distance;
        break;
    }
  }
  return std::abs(coord.first) + std::abs(coord.second);
}

int task2(const std::vector<std::string>& lines) {
  std::pair<int, int> coord(0, 0);
  std::pair<int, int> waypoint(10, 1);
  int dir = 1;
  for (auto line = lines.begin(); line != lines.end(); ++line) {
    char cmd = line->at(0);
    int angle_or_distance = std::atoi(line->substr(1).c_str());
    if (*line == "L90" || *line == "R270") {
      std::swap(waypoint.first, waypoint.second);
      waypoint.first = -waypoint.first;
    } else if (*line == "L180" || *line == "R180") {
      waypoint.first = -waypoint.first;
      waypoint.second = -waypoint.second;
    } else if (*line == "L270" || *line == "R90") {
      std::swap(waypoint.first, waypoint.second);
      waypoint.second = -waypoint.second;
    } else {
      switch (cmd) {
        case 'N':
          waypoint.second += angle_or_distance;
          break;
        case 'S':
          waypoint.second -= angle_or_distance;
          break;
        case 'E':
          waypoint.first += angle_or_distance;
          break;
        case 'W':
          waypoint.first -= angle_or_distance;
          break;
        case 'F':
          coord.first += angle_or_distance * waypoint.first;
          coord.second += angle_or_distance * waypoint.second;
          break;
      }
    }
    // std::cout << *line << " ship: " << coord << " wayp: " << waypoint << '\n';
  }
  return std::abs(coord.first) + std::abs(coord.second);
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  std::string line;
  std::vector<std::string> lines;
  while (std::getline(inf, line)) {
    lines.push_back(line);
  }
  std::cout << task1(lines) << '\n';
  std::cout << task2(lines) << '\n';
  return 0;
}
