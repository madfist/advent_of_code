#include <fstream>
#include <iostream>
#include <vector>
#include <map>
#include <numeric>

#include "../include/utils.h"

long long task1(const std::vector<std::string>& lines) {
  long long mask0, mask1;
  std::map<long long, long long> memory;
  for (auto line = lines.begin(); line != lines.end(); ++line) {
    if (line->substr(0,7) == "mask = ") {
      mask1 = 0;
      mask0 = (1ll << 36) - 1;
      for (int i = 0; i < line->size()-7; ++i) {
        // std::cout << line->at(line->size()-i-1);
        switch (line->at(line->size()-i-1)) {
          case '1':
            mask1 += (1ll << i);
            break;
          case '0':
            mask0 -= (1ll << i);
            break;
        }
      }
      // std::cout << '\n' << mask0 << '-' << mask1 << '\n';
    }
    if (line->substr(0,4) == "mem[") {
      int mem_end = line->find_first_of(']');
      long long address = std::atoll(line->substr(4, mem_end).c_str());
      long long data = std::atoll(line->substr(mem_end+3).c_str());
      // std::cout << '>' << address << '=' << data << ' ' << (data | mask1) << ' ' << ((data | mask1) & mask0) << '\n';
      memory[address] = (data | mask1) & mask0;
    }
  }
  // std::cout << memory << '\n';
  return std::accumulate(memory.begin(), memory.end(), 0ll, [](long long sum, std::pair<long long, long long> mem) {
    return sum + mem.second;
  });
}

long long task2(const std::vector<std::string>& lines) {
  return 0;
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  std::string line;
  std::vector<std::string> lines;
  while(std::getline(inf, line)) {
    lines.push_back(line);
  }
  std::cout << task1(lines) << '\n';
  std::cout << task2(lines) << '\n';
  return 0;
}
