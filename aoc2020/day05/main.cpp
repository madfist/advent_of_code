#include <fstream>
#include <iostream>
#include <vector>
#include <algorithm>

#include "../include/utils.h"

unsigned task1(const std::vector<unsigned>& seats) {
  return *(std::max_element(seats.begin(), seats.end()));
}

unsigned task2(std::vector<unsigned>& seats) {
  std::stable_sort(seats.begin(), seats.end());
  for (auto s = seats.begin(); s != seats.end();) {
    if (*s + 1 != *(++s)) {
      return *s - 1;
    }
  }
  return seats.back();
}

unsigned convert_to_number(const std::string& code) {
  std::pair<unsigned, unsigned> row_range(0, 128);
  std::pair<unsigned, unsigned> column_range(0, 8);
  for (auto l = code.begin(); l != code.end(); ++l) {
    switch (*l) {
      case 'F':
        row_range.second -= (row_range.second - row_range.first) / 2;
        break;
      case 'B':
        row_range.first += (row_range.second - row_range.first) / 2;
        break;
      case 'L':
        column_range.second -= (column_range.second - column_range.first) / 2;
        break;
      case 'R':
        column_range.first += (column_range.second - column_range.first) / 2;
        break;
    }
    // std::cout << *l << ": " << row_range << " # " << column_range << '\n';
  }
  return row_range.first * 8 + column_range.first;
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  unsigned max_seat = 0;
  std::string seat;
  std::vector<unsigned> seats;
  while(inf >> seat) {
    // std::cout << seat << ' ' << convert_to_number(seat) << '\n';
    seats.push_back(convert_to_number(seat));
  }
  std::cout << task1(seats) << '\n';
  std::cout << task2(seats) << '\n';

  return 0;
}
