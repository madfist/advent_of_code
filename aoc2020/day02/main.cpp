#include <iostream>
#include <fstream>
#include <algorithm>

int count_char(char c, const std::string& str) {
  int count = 0;
  std::for_each(str.begin(), str.end(), [&](char ch) {
    if (c == ch) {
      ++count;
    }
  });
  return count;
}

bool validate1(int min, int max, char c, const std::string& pwd) {
  int count = count_char(c, pwd);
  return min <= count && count <= max;
}

bool validate2(int pos1, int pos2, char c, const std::string& pwd) {
  bool v1 = pwd[pos1 - 1] == c;
  bool v2 = pwd[pos2 - 1] == c;
  return (v1 && !v2) || (!v1 && v2);
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  int min, max;
  char dash, c, colon;
  std::string pwd;
  int valid_count1 = 0;
  int valid_count2 = 0;
  while(inf >> min >> dash >> max >> c >> colon >> pwd) {
    if (validate1(min, max, c, pwd)) {
      ++valid_count1;
    }
    if (validate2(min, max, c, pwd)) {
      ++valid_count2;
    }
  }
  std::cout << valid_count1 << '\n';
  std::cout << valid_count2 << '\n';
  return 0;
}
