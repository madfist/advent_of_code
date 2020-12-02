#include <iostream>
#include <fstream>
#include <set>

#include "../include/utils.h"

int task1(const std::set<int>& numbers) {
  for (auto n = numbers.begin(); n != numbers.end(); ++n) {
    auto x = numbers.find(2020 - *n);
    if (x != numbers.end()) {
      return (*n) * (*x);
    }
  }
  return -1;
}

int task2(const std::set<int>& numbers) {
  for (auto a = numbers.begin(); a != numbers.end(); ++a) {
    for (auto b = numbers.begin(); b != numbers.end(); ++b) {
      if (a == b) {
        continue;
      }
      auto c = numbers.find(2020 - *a - *b);
      if (c != numbers.end()) {
        return (*a) * (*b) * (*c);
      }
    }
  }
  return -1;
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  std::set<int> numbers;
  int input = 0;
  while(inf >> input) {
    numbers.insert(input);
  }
  std::cout << task1(numbers) << '\n';
  std::cout << task2(numbers) << '\n';
  return 0;
}
