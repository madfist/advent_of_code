#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <cmath>
#include "../include/utils.h"

typedef std::vector<long> lv_t;

class computer {
public:
  computer(const lv_t& program)
      : memory(program)
      , input_counter(1)
      , halted(false)
      , relative_base(0) {
    ic = memory.begin();
  }

  computer(const lv_t& program, long ph)
      : memory(program)
      , phase(ph)
      , input_counter(0)
      , halted(false)
      , relative_base(0) {
    ic = memory.begin();
  }

  long compute(long signal, bool stop_on_output = false) {
    long output = 0;
    while (ic != memory.end()) {
      if (*ic == 99) {
        halted = true;
        break;
      }

      switch (*ic % 10) {
        case 1: param(3) = param(1) + param(2); ic += 4; break;
        case 2: param(3) = param(1) * param(2); ic += 4; break;
        case 3:
          param(1) = (input_counter) ? signal : phase;
          ++input_counter;
          ic += 2;
          break;
        case 4:
          output = param(1);
          ic += 2;
          if (stop_on_output) {
            return output;
          }
          break;
        case 5: ic = (param(1) != 0) ? memory.begin() + param(2) : ic + 3; break;
        case 6: ic = (param(1) == 0) ? memory.begin() + param(2) : ic + 3; break;
        case 7: param(3) = (param(1) < param(2)) ? 1 : 0; ic += 4; break;
        case 8: param(3) = (param(1) == param(2)) ? 1 : 0; ic += 4; break;
        case 9: relative_base += param(1);  ic += 2; break;
        default: throw std::runtime_error("unknown opcode");
      }
    }
    return output;
  }

  computer& reset(const lv_t &program) {
    memory = program;
    input_counter = 1;
    ic = memory.begin();
    halted = false;
    return *this;
  }

  computer& reset(const lv_t &program, long ph) {
    phase = ph;
    memory = program;
    input_counter = 0;
    ic = memory.begin();
    halted = false;
    return *this;
  }
  bool halted;

private:
  long& param(int pos) {
    int mode = *ic / static_cast<int>(std::pow(10, pos + 1)) % 10;

    switch(mode) {
      case 0:
        if (ic[pos] >= memory.size()) {
          memory.resize(ic[pos] + 1, 0);
        }
        return memory[ic[pos]];
      case 1: return ic[pos];
      case 2:
        if (relative_base + ic[pos] >= memory.size()) {
          memory.resize(relative_base + ic[pos] + 1, 0);
        }
        return memory[relative_base + ic[pos]];
    }
    throw std::runtime_error("unsupported mode");
  }
  long phase;
  long input_counter;
  long relative_base;
  lv_t::iterator ic;
  lv_t memory;
};

lv_t read(std::istream& is) {
  long token;
  char comma;
  std::vector<long> tokens;
  while (is >> token >> comma) {
    tokens.push_back(token);
  }
  is >> token;
  tokens.push_back(token);
  return tokens;
}

int main(int argc, char **argv) {
  lv_t program;
  if (argc < 2) {
    program = std::move(read(std::cin));
  } else {
    std::ifstream inf(argv[2]);
    program = std::move(read(inf));
  }

  computer c1(program);
  std::cout << c1.compute(1) << '\n';
  computer c2(program);
  std::cout << c2.compute(2) << '\n';

  return 0;
}
