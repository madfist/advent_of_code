#include <fstream>
#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>

#include "../include/utils.h"

int task1(int timestamp, const std::string& bus_lines) {
  int start = 0;
  std::vector<int> buses;
  for (int i = 0; i < bus_lines.length(); ++i) {
    if (bus_lines[i] == 'x') {
      ++i;
      start += 2;
      continue;
    }
    if (bus_lines[i] == ',') {
      buses.push_back(std::atoi(bus_lines.substr(start, i-start).c_str()));
      start = i+1;
    }
  }
  // std::cout << "ts " << timestamp << " buses " << buses << '\n';
  std::vector<int> mod(buses);
  std::transform(buses.begin(), buses.end(), mod.begin(), [&](int x){ return x - timestamp%x; });
  // std::cout << mod << '\n';
  int it = std::min_element(mod.begin(), mod.end()) - mod.begin();
  // std::cout << mod[it] << 'x' << buses[it] << '=' << buses[it]*mod[it] << '\n';
  return buses[it]*mod[it];
}

long long task2(const std::string& bus_lines) {
  int start = 0;
  int delta = 0;
  std::vector<std::pair<int, int>> buses;
  for (int i = 0; i < bus_lines.length(); ++i) {
    if (bus_lines[i] == 'x') {
      ++i;
      start += 2;
      ++delta;
      continue;
    }
    if (bus_lines[i] == ',') {
      buses.push_back(std::make_pair(std::atoi(bus_lines.substr(start, i-start).c_str()), delta++));
      start = i+1;
    }
  }
  // std::cout << buses << '\n';
  std::vector<int> mod(buses.size());
  long long timestamp = 0;
  long long inc = buses[0].first;
  int sum = 0;
  int last_zeros = 1;
  do {
    timestamp += inc;
    sum = 0;
    for (auto it = buses.begin(); it != buses.end(); ++it) {
      int m = (timestamp + it->second) % it->first;
      sum += m;
      if (m == 0 && inc % it->first != 0) {
        // std::cout << '#' << inc << 'x' << it->first << '\n';
        inc *= it->first;
      }
    }
    // std::cout << 't' << timestamp << '-' << sum << '#' << inc << '\n';
  } while (sum != 0);
  return timestamp;
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  int timestamp;
  inf >> timestamp;
  std::string bus_lines;
  inf >> bus_lines;
  bus_lines.push_back(',');
  std::cout << task1(timestamp, bus_lines) << '\n';
  std::cout << task2(bus_lines) << '\n';
  return 0;
}
