#include <iostream>
#include <fstream>
#include <vector>

typedef std::vector<int> iv_t;

std::ostream& operator<<(std::ostream& os, const iv_t &p) {
  for (auto i = p.begin(); i != p.end(); ++i) {
    os << *i;
    if (i != p.end() - 1) os << ',';
  }
  return os;
}

iv_t compute(const iv_t& program, int input) {
  iv_t p(program);
  iv_t ret;
  int output = 0;
  for (auto t = p.begin(); t != p.end();) {
    //std::cout << *t << '-' << p << '\n';
    if (*t == 99) {
      break;
    }
    int a = (*t/100%10) ? t[1] : p[t[1]];
    int b = (*t/1000%10) ? t[2] : p[t[2]];
    int &c = (*t/10000%10) ? t[3] : p[t[3]];
    switch(*t % 10) {
      case 1:
        //std::cout << a << '+' << b << '\n';
        c = a + b; t+=4; break;
      case 2:
        //std::cout << a << '*' << b << '\n';
        c = a * b; t+=4; break;
      case 3:
        //std::cout << "in" << input << '\n';
        p[t[1]] = input; t+=2; break;
      case 4:
        //std::cout << "out" << p[t[1]] << '\n';
        ret.push_back(a); t+=2; break;
      case 5:
        //std::cout << "jump-true" << '\n';
        t = (a != 0) ? p.begin() + b : t+3; break;
      case 6:
        //std::cout << "jump-false" << '\n';
        t = (a == 0) ? p.begin() + b : t+3; break;
      case 7:
        c = (a < b) ? 1 : 0; t+=4; break;
      case 8:
        c = (a == b) ? 1 : 0; t+=4; break;
    }
  }
  return ret;
}

iv_t read(std::istream& is) {
  int token;
  char comma;
  std::vector<int> tokens;
  while (is >> token >> comma) {
    tokens.push_back(token);
  }
  is >> token;
  tokens.push_back(token);
  return tokens;
}

int task2(iv_t& tokens) {
  return 0;
}

int main(int argc, char **argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input> [program]\n";
    return 1;
  }
  if (argc < 3) {
    iv_t data = read(std::cin);
    std::cout << compute(data, std::atoi(argv[1])) << '\n';
    //std::cout << task2(data) << '\n';
  } else {
    std::ifstream inf(argv[2]);
    iv_t data = read(inf);
    std::cout << compute(data, std::atoi(argv[2])) << '\n';
    //std::cout << task2(data) << '\n';
  }
  
  return 0;
}
