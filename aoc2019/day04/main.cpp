#include <iostream>
#include <cassert>

bool check(int n, bool only_pairs = false) {
  assert(n/100000 > 0);
  int last_digit = n % 10;
  int min_adj = 10;
  int n_adj = 1;
  int divider = 10;
  bool adjacent = false;
  bool monoton = true;
  //std::cout << "N " << n << '\n';
  for (int i = 0; i < 5; ++i) {
    //std::cout << "ch " << n << '\n';
    n /= 10;
    int digit = n % 10;
    adjacent |= (digit == last_digit);
    monoton &= (digit <= last_digit);
    if (digit == last_digit) {
      //std::cout << "SAM " << digit;
      ++n_adj;
    } else if (n_adj > 1) {
      min_adj = std::min(min_adj, n_adj);
      //std::cout << "ADJ " << last_digit << '-' << n_adj << ',';
      n_adj = 1;
    }
    last_digit = digit;
  }
  if (only_pairs) {
    adjacent &= (min_adj == 2 || n_adj == 2);
  }
  return adjacent && monoton;
}

int task(int l, int u, bool task2 = false) {
  int sum = 0;
  for (int i=l; i<=u; ++i) {
    if (check(i, task2)) {
      std::cout << "SUC*" << i << '\n';
      ++sum;
    }
  }
  return sum;
}

int main(int argc, char** argv) {
  if (argc < 3) {
    std::cout << "Usage: " << argv[0] << " <lower> <upper>\n";
    return 1;
  }
  int lower = std::atoi(argv[1]);
  int upper = std::atoi(argv[2]);

  std::cout << task(lower, upper) << '\n';
  std::cout << task(lower, upper, true) << '\n';
  return 0;
}
