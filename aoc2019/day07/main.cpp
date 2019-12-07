#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

typedef std::vector<int> iv_t;

std::ostream& operator<<(std::ostream& os, const iv_t &p) {
  for (auto i = p.begin(); i != p.end(); ++i) {
    os << *i;
    if (i != p.end() - 1) os << ',';
  }
  return os;
}

class computer {
public:
  computer(const iv_t& program, int ph)
      : memory(program)
      , phase(ph)
      , input_counter(0)
      , halted(false) {
    ic = memory.begin();
  }

  int compute(int signal, bool stop_on_output = false) {
    int output = 0;
    while (ic != memory.end()) {
      if (*ic == 99) {
        halted = true;
        break;
      }
      int &a = (*ic / 100   % 10) ? ic[1] : memory[ic[1]];
      int  b = (*ic / 1000  % 10) ? ic[2] : memory[ic[2]];
      int &c = (*ic / 10000 % 10) ? ic[3] : memory[ic[3]];

      switch (*ic % 10) {
        case 1: c = a + b; ic += 4; break;
        case 2: c = a * b; ic += 4; break;
        case 3:
          a = (input_counter) ? signal : phase;
          ++input_counter;
          ic += 2;
          break;
        case 4:
          output = a;
          ic += 2;
          if (stop_on_output) {
            return output;
          }
          break;
        case 5: ic = (a != 0) ? memory.begin() + b : ic + 3; break;
        case 6: ic = (a == 0) ? memory.begin() + b : ic + 3; break;
        case 7: c = (a < b) ? 1 : 0; ic += 4; break;
        case 8: c = (a == b) ? 1 : 0; ic += 4; break;
      }
    }
    return output;
  }
  computer& reset(const iv_t &program, int ph) {
    phase = ph;
    memory = program;
    input_counter = 0;
    ic = memory.begin();
    halted = false;
    return *this;
  }
  bool halted;
private:
  int phase;
  int input_counter;
  iv_t::iterator ic;
  iv_t memory;
};

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

int thrusters(iv_t& tokens, int input) {
  iv_t phases({0, 1, 2, 3, 4});
  int max = 0;
  do {
    int signal = input;
    for (auto ph = phases.begin(); ph != phases.end(); ++ph) {
      computer c(tokens, *ph);
      signal = c.compute(signal);
    }
    max = std::max(max, signal);
  } while (std::next_permutation(phases.begin(), phases.end()));
  return max;
}

int feedback(const iv_t &tokens, int input) {
  iv_t phases({5, 6, 7, 8, 9});
  int max = 0;
  std::vector<computer> comps;
  std::transform(phases.begin(), phases.end(), std::back_inserter(comps), [&tokens](int ph) {
    return computer(tokens, ph);
  });
  do {
    int signal = input;
    auto c = comps.begin();
    // std::cout << "phase " << phases << ": ";
    for (; c != comps.end();) {
      if (!c->halted) {
        signal = std::max(signal, c->compute(signal, true));
        // std::cout << signal << ", ";
      }
      if (c == comps.end() - 1 && !c->halted) {
        c = comps.begin();
        // std::cout << "<--|";
      } else {
        ++c;
      }
    }
    // std::cout << "max " << max << '\n';
    max = std::max(signal, max);
    c = comps.begin();
    for (auto ph = phases.begin(); ph != phases.end(); ++ph, ++c) {
      c->reset(tokens, *ph);
    }
    // std::cout << "reset\n";
  } while (std::next_permutation(phases.begin(), phases.end()));
  return max;
}

int main(int argc, char **argv) {
  iv_t program;
  if (argc < 2) {
    program = std::move(read(std::cin));
  } else {
    std::ifstream inf(argv[2]);
    program = std::move(read(inf));
  }

  std::cout << thrusters(program, 0) << '\n';
  std::cout << feedback(program, 0) << '\n';

  return 0;
}
