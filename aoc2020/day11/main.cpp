#include <fstream>
#include <iostream>
#include <vector>
#include <numeric>
#include <algorithm>
#include <valarray>

#include "../include/utils.h"

template<class C>
class seat_plan {
public:
  seat_plan(const C& ns, int c, int r)
      : column_count(c), row_count(r), data(ns.size(), '.'), non_seats(ns) {}

  seat_plan& set_seats(const C& seats) {
    for (int j = 0; j < row_count; ++j) {
      for (int i = 0; i < column_count; ++i) {
        data[j * column_count + i] = (non_seats[j * column_count + i]) ? '.' : ((seats[j * column_count + i]) ? '#' : 'L');
      }
    }
    return *this;
  }

  friend std::ostream& operator<<(std::ostream& os, const seat_plan& sp) {
    for (int j = 0; j < sp.row_count; ++j) {
      auto b = sp.data.begin() + j * sp.column_count;
      os << std::string(b, b + sp.column_count);
      if (j < sp.row_count - 1) {
        os << '\n';
      }
    }
    return os;
  }
private:
  std::vector<char> data;
  C non_seats;
  int column_count, row_count;
};

int task1(const std::vector<bool>& non_seats, int c, int r) {
  std::vector<bool> seats1(non_seats.size(), false);
  std::vector<bool> seats2(non_seats.size(), false);

  seat_plan<std::vector<bool>> sp(non_seats, c, r);
  do {
    // std::cout << sp.set_seats(seats2) << '\n';
    seats1.swap(seats2);
    for (int j = 1; j < r-1 ; ++j) {
      for (int i = 1; i < c-1; ++i) {
        if (non_seats[j * c + i]) {
          // std::cout << '_';
          continue;
        }
        int counter = 0;

        counter += seats1[(j-1) * c + i-1];
        counter += seats1[j * c + i-1];
        counter += seats1[(j+1) * c + i-1];
        counter += seats1[(j-1) * c + i];
        counter += seats1[(j+1) * c + i];
        counter += seats1[(j-1) * c + i+1];
        counter += seats1[j * c + i+1];
        counter += seats1[(j+1) * c + i+1];

        // std::cout << counter;

        seats2[j * c + i] = (seats1[j * c + i]) ? (counter < 4) : (counter == 0);
      }
      // std::cout << '\n';
    }
  } while(seats1 != seats2);
  return std::accumulate(seats1.begin(), seats1.end(), 0);
}

bool check_line_of_sight(const std::valarray<bool>& non_seats, const std::valarray<bool>& seats, const std::string& s = "") {
  // std::cout << s << '[' << non_seats << 'x' << seats << "]+";
  // std::cout << s << '[' << non_seats.size() << ']';
  for (int i = 0; i < non_seats.size(); ++i) {
    if (!non_seats[i]) {
      // std::cout << seats[i];
      return seats[i];
    }
  }
  // std::cout << false;
  return false;
}

int task2(const std::vector<bool>& non_seats, int c, int r) {
  std::valarray<bool> ns(false, c * r);
  std::valarray<bool> s1(false, c * r);
  std::valarray<bool> s2(false, c * r);
  for (int j = 0; j < r; ++j) {
    for (int i = 0; i < c; ++i) {
      ns[j*c + i] = non_seats[(j+1)*(c+2) + i+1];
    }
  }
  seat_plan<std::valarray<bool>> sp2(ns, c, r);
  do {
    // std::cout << sp2.set_seats(s2) << '\n';
    s1.swap(s2);
    for (int j = 0; j < r; ++j) {
      for (int i = 0; i < c; ++i) {
        if (ns[j*c + i]) {
          // std::cout << '_';
          continue;
        }

        std::slice w(j*c+i-1, i, -1);
        std::slice nw((j-1)*c+i-1, std::min(i, j), -c-1);
        std::slice n((j-1)*c+i, j, -c);
        std::slice ne((j-1)*c+i+1, std::min(c-i-1, j), -c+1);
        std::slice e(j*c+i+1, c-i-1, 1);
        std::slice se((j+1)*c+i+1, std::min(c-i-1, r-j-1), c+1);
        std::slice s((j+1)*c+i, r-j-1, c);
        std::slice sw((j+1)*c+i-1, std::min(i, r-j-1), c-1);

        int counter = 0;
        // std::cout << '{';
        counter += check_line_of_sight(ns[w], s1[w], "w");
        counter += check_line_of_sight(ns[nw], s1[nw], "nw");
        counter += check_line_of_sight(ns[n], s1[n], "n");
        counter += check_line_of_sight(ns[ne], s1[ne], "ne");
        counter += check_line_of_sight(ns[e], s1[e], "e");
        counter += check_line_of_sight(ns[se], s1[se], "se");
        counter += check_line_of_sight(ns[s], s1[s], "s");
        counter += check_line_of_sight(ns[sw], s1[sw], "sw");
        // std::cout << '}';

        // std::cout << counter;

        s2[j * c + i] = (s1[j * c + i]) ? (counter < 5) : (counter == 0);
      }
      // std::cout << "\n------\n";
    }
  } while((s1 != s2).sum());
  // } while(0);
  return std::accumulate(std::begin(s1), std::end(s1), 0);
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  std::vector<bool> non_seats;
  std::string line;
  int row_count = 1, column_count = 0;
  while (std::getline(inf, line)) {
    if (column_count == 0) {
      column_count = line.size();
      non_seats.insert(non_seats.end(), column_count + 2, true);
    }
    non_seats.reserve(++row_count * (column_count + 2));
    non_seats.push_back(true);
    for (auto s = line.begin(); s != line.end(); ++s) {
      non_seats.push_back((*s == 'L') ? false : true);
    }
    non_seats.push_back(true);
  }
  non_seats.insert(non_seats.end(), column_count + 2, true);
  --row_count;

  std::cout << task1(non_seats, column_count + 2, row_count + 2) << '\n';
  std::cout << task2(non_seats, column_count, row_count) << '\n';
  return 0;
}
