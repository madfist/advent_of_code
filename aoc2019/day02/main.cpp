#include <iostream>
#include <fstream>
#include <vector>

typedef std::vector<int> iv_t;

int compute(const iv_t& input) {
  iv_t ret(input);
  for (auto t = ret.begin(); t != ret.end(); t+=4) {
    if (*t == 99) {
      break;
    }
    switch(*t) {
      case 1:
        ret[t[3]] = ret[t[1]] + ret[t[2]]; break;
      case 2:
        ret[t[3]] = ret[t[1]] * ret[t[2]]; break;
    }
  }
  return ret[0];
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
  for (int i=0; i<=99; ++i) {
    for (int j=0; j<=99; ++j) {
      tokens[1] = i;
      tokens[2] = j;
      if (compute(tokens) == 19690720) {
        return i*100 + j;
      }
    }
  }
  return -1;
}

int main(int argc, char **argv) {
  if (argc < 2) {
    iv_t data = read(std::cin);
    std::cout << compute(data) << '\n';
    std::cout << task2(data) << '\n';
  } else {
    std::ifstream inf(argv[1]);
    iv_t data = read(inf);
    std::cout << compute(data) << '\n';
    std::cout << task2(data) << '\n';
  }
  
  return 0;
}
