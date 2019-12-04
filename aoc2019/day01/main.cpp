#include <iostream>
#include <fstream>

inline int fuel(int mass) {
  return mass / 3 - 2;
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  int sum1 = 0;
  int sum2 = 0;
  int mass = 0;
  while (inf >> mass) {
    sum1 += fuel(mass);
    int m = mass;
    while ((m = fuel(m)) > 0) {
      sum2 += m;
    }
  }
  std::cout << sum1 << '\n' << sum2 << '\n';
  return 0;
}
