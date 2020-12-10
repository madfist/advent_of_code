#include <fstream>
#include <iostream>
#include <vector>
#include <algorithm>

int task1(std::vector<int>& nums) {
  std::stable_sort(nums.begin(), nums.end());
  nums.push_back(nums.back() + 3);
  int ones = 0;
  int twos = 0;
  int threes = 0;
  int prev = 0;
  for (auto i = nums.begin(); i != nums.end(); ++i) {
    switch (*i - prev) {
      case 1:
        ++ones;
        break;
      case 2:
        ++twos;
        break;
      case 3:
        ++threes;
        break;
    }
    prev = *i;
  }
  // std::cout << ones << '+' << twos << '+' << threes << '\n';
  return ones * threes;
}

long task2(std::vector<int>& nums) {
  long perm = 1;
  int prev = 0;
  int ones = 0;
  for (auto i = nums.begin(); i != nums.end(); ++i) {
    switch (*i - prev) {
      case 1:
        ++ones;
        break;
      case 3:
        switch(ones) {
          case 2: perm *= 2; break;
          case 3: perm *= 4; break;
          case 4: perm *= 7; break;
        }
        std::cout << "ones " << ones << ' ' << perm << '\n';
        ones = 0;
        break;
    }
    prev = *i;
  }
  std::cout << '\n';
  return perm;
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  std::vector<int> nums;
  int num;
  while(inf >> num) {
    nums.push_back(num);
  }
  std::cout << task1(nums) << '\n';
  std::cout << task2(nums) << '\n';
  return 0;
}
