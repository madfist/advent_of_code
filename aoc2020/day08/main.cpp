#include <fstream>
#include <iostream>
#include <vector>
#include <set>

std::pair<int, size_t> compute(int acc, size_t pc, const std::string& instruction, int step) {
  std::pair<int, size_t> res(acc, pc);
  if (instruction == "nop") {
    ++res.second;
  } else if (instruction == "acc") {
    res.first += step;
    ++res.second;
  } else if (instruction == "jmp") {
    res.second += step;
  }
  return res;
}

void replace(std::vector<std::string>::iterator& it) {
  if (it->substr(0, 3) == "nop") {
    it->replace(0, 3, "jmp");
  } else if (it->substr(0, 3) == "jmp") {
    it->replace(0, 3, "nop");
  }
}

int task1(const std::vector<std::string>& lines) {
  int acc = 0;
  std::set<size_t> instructions_done;
  for (size_t pc = 0; pc < lines.size();) {
    if (instructions_done.find(pc) != instructions_done.end()) {
      return acc;
    }
    instructions_done.insert(pc);
    auto res = compute(acc, pc, lines[pc].substr(0,3), std::atoi(lines[pc].substr(4).c_str()));
    acc = res.first;
    pc = res.second;
  }
  return acc;
}

int task2(std::vector<std::string>& lines) {
  auto prev = lines.end();
  for (auto line = lines.begin(); line != lines.end(); ++line) {
    int acc = 0;
    bool looped = false;
    if (line->substr(0, 3) == "acc") {
      continue;
    }
    replace(line);
    if (prev != lines.end()) {
      replace(prev);
    }
    std::set<size_t> instructions_done;
    for (size_t pc = 0; pc < lines.size();) {
      if (instructions_done.find(pc) != instructions_done.end()) {
        looped = true;
        break;
      }
      instructions_done.insert(pc);
      auto res = compute(acc, pc, lines[pc].substr(0,3), std::atoi(lines[pc].substr(4).c_str()));
      acc = res.first;
      pc = res.second;
    }
    if (!looped) {
      return acc;
    }
    prev = line;
  }
  return 0;
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  std::string line;
  std::vector<std::string> lines;
  while(std::getline(inf, line)) {
    lines.push_back(line);
  }
  std::cout << task1(lines) << '\n';
  std::cout << task2(lines) << '\n';
  return 0;
}
