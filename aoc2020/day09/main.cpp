#include <fstream>
#include <iostream>
#include <vector>
#include <numeric>
#include <algorithm>

#include "../include/utils.h"

bool validate(const std::vector<int>& nums) {
  for (auto i = nums.begin(); i != nums.end() - 2; ++i) {
    for (auto j = i + 1; j != nums.end() - 1; ++j) {
      if (*i + *j == nums.back()) {
        return true;
      }
    }
  }
  return false;
}

int task1(const std::vector<int>& nums, size_t validation_size) {
  for (auto i = nums.begin(); i != nums.end() - validation_size - 1; ++i) {
    if (!validate(std::vector<int>(i, i + validation_size + 1))) {
      return *(i + validation_size);
    }
  }
  return 0;
}

int task2(const std::vector<int>& nums, size_t validation_size) {
  auto invalid_it = nums.end();
  for (auto i = nums.begin(); i != nums.end() - validation_size - 1; ++i) {
    if (!validate(std::vector<int>(i, i + validation_size + 1))) {
      invalid_it = i + validation_size;
      break;
    }
  }
  for (auto i = nums.begin(); i != invalid_it; ++i) {
    int sum = 0;
    int cnt = 1;
    while (sum < *invalid_it) {
      sum = std::accumulate(i, i + cnt++, 0);
    }
    if (sum == *invalid_it) {
      // std::cout << std::vector<int>(i, i + cnt - 1) << '|' << *i << ',' << *(i + cnt - 1) << '\n';
      return *std::min_element(i, i + cnt - 1) + *std::max_element(i, i + cnt - 1);
    }
  }
  return 0;
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input> [validation_size]\n";
  }
  std::ifstream inf(argv[1]);
  size_t validation_size = (argc == 3) ? std::atoi(argv[2]) : 25;
  std::string line;
  std::vector<int> nums;
  while(std::getline(inf, line)) {
    nums.push_back(std::atoi(line.c_str()));
  }
  std::cout << task1(nums, validation_size) << '\n';
  std::cout << task2(nums, validation_size) << '\n';
  return 0;
}
