#include <fstream>
#include <iostream>
#include <vector>
#include <numeric>
#include <algorithm>

#include "../include/utils.h"

template<typename T>
struct matrix {
  matrix(): rows(0), columns(0), data() {}
  matrix(size_t x, size_t y): rows(y), columns(x), data(x * y) {}
  matrix(size_t x, size_t y, const std::vector<T>& v): rows(y), columns(x), data(v.begin(), v.end()) {}

  T& operator()(size_t a, size_t b) {
    return data[b * rows + a];
  }

  const T& operator()(size_t a, size_t b) const {
    return data[b * rows + a];
  }

  bool operator==(const matrix<T>& m) {
    return rows == m.rows && columns == m.columns && data == m.data;
  }

  bool operator!=(const matrix<T>& m) {
    return rows != m.rows || columns != m.columns || data != m.data;
  }

  size_t rows;
  size_t columns;
  std::vector<T> data;
};

template<typename T>
std::ostream& operator<<(std::ostream& os, const matrix<T>& m) {
  for (int j = 0; j < m.rows; ++j) {
    auto begin = m.data.begin() + j * m.columns;
    os << std::vector<T>(begin, begin + m.columns);
    if (j < m.rows - 1) {
      os << '\n';
    }
  }
  return os;
}

int adjacency(const matrix<int>& seats, size_t x, size_t y, bool print = false) {
  int counter = 0;
  size_t r = seats.rows - 1;
  size_t c = seats.columns - 1;

  counter += (x > 0 && y > 0) ? seats(x - 1, y - 1) == 1 : 0;
  counter += (x > 0) ? seats(x - 1, y) == 1 : 0;
  counter += (x > 0 && y < r) ? seats(x - 1, y + 1) == 1 : 0;
  counter += (y > 0) ? seats(x, y - 1) == 1 : 0;
  counter += (y < r) ? seats(x, y + 1) == 1 : 0;
  counter += (x < c && y > 0) ? seats(x + 1, y - 1) == 1 : 0;
  counter += (x < c) ? seats(x + 1, y) == 1 : 0;
  counter += (x < c && y < r) ? seats(x + 1, y + 1) == 1 : 0;
  if (print) {
    std::cout /*<< '(' << x << ',' << y << ')'*/ << counter << ' ';
  }

  return counter;
}

int task1(const matrix<int>& seats) {
  matrix<int> prev(seats);
  matrix<int> tmp(seats);
  int c = 0;
  do {
    prev = tmp;
    // std::cout << "$$$$$$$\n";
    for (size_t j = 0; j < seats.rows; ++j) {
      for (size_t i = 0; i < seats.columns; ++i) {
        // adjacency(prev, i, j, true);
        if (prev(i, j) == 9) {
          tmp(i, j) = 9;
        }
        if (prev(i, j) == 1 && adjacency(prev, i, j) >= 4) {
          tmp(i, j) = 0;
        }
        if (prev(i, j) == 0 && adjacency(prev, i, j) == 0) {
          tmp(i, j) = 1;
        }
      }
      // std::cout << '\n';
    }
    // std::cout<< "--------\n" << tmp << '\n';
  } while (prev != tmp);
  return std::accumulate(prev.data.begin(), prev.data.end(), 0, [](const int a, const int b) {
    if (b == 9) {
      return a;
    }
    return a + b;
  });
}

int task2(matrix<int>& seats) {
  return 0;
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  std::string line;
  std::vector<int> seats;
  int rows = 0;
  int columns = 0;
  while(std::getline(inf, line)) {
    if (rows == 0) {
      rows = line.size();
    }
    for (auto s = line.begin(); s != line.end(); ++s) {
      seats.push_back((*s == 'L') ? 0 : 9);
    }
  }
  columns = seats.size() / rows;
  matrix<int> m(rows, columns, seats);
  // std::cout << m << '\n';
  std::cout << task1(m) << '\n';
  std::cout << task2(m) << '\n';
  return 0;
}
